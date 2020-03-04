package com.booking.tech.service;

import com.booking.tech.entities.Option;
import com.booking.tech.entities.Ride;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;


@Service
public class TechAppService {

    @Autowired
    private RestTemplate restTemplate;
    private final List<String> SUPPLIERS = new ArrayList<String>(Arrays.asList("eric", "jeff", "dave"));


    /**
     * Make a get request to a supplier api and return the response
     *
     * @param supplier
     * @param pickup
     * @param dropoff
     * @param numberOfPassengers
     * @return response
     */
    public ResponseEntity<String> getResponse(String supplier, String pickup, String dropoff,
                                              @RequestParam(required = false) Integer numberOfPassengers) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://techtest.rideways.com");
            builder.path(supplier);

            builder.queryParam("pickup", pickup);
            builder.queryParam("dropoff", dropoff);
            builder.queryParam("numberOfPassengers", numberOfPassengers);

            HttpEntity entity = new HttpEntity<>(headers);

            // Send get request.
            ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
            return response;
        }
        catch (HttpClientErrorException clientErrorException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request to " + supplier + "'s API");
        }
        catch (HttpServerErrorException serverUnavailableException) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(supplier + "'s API is currently unavailable");
        }
        catch (Exception exception) {
            // Timeout of 2 seconds.
            return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(supplier + "'s API timed out.");
        }
    }


    /**
     * Get the available rides from a given supplier and return a list of options
     *
     * @param supplier
     * @param pickup
     * @param dropoff
     * @param numberOfPassengers
     * @return response
     *
     */
    public List<Option> getCarsFromGivenSupplier(String supplier ,String pickup, String dropoff,
                                        @RequestParam(required = false) Integer numberOfPassengers)
            throws JsonProcessingException {

        // Check the given supplier exists.
        if (!SUPPLIERS.contains(supplier)) {
            System.out.println("Supplier is non-existent.");
            return new ArrayList<Option>();
        }

        HashMap<String, Integer> carTypesMaximumPassengers = getCarTypesMaximumPassengers();

        if(numberOfPassengers == null) numberOfPassengers = 0;

        // Make the request.
        ResponseEntity<String> response = this.getResponse(supplier,pickup, dropoff, numberOfPassengers);

        if (response.getStatusCode() != HttpStatus.OK) {
            if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR
                    || response.getStatusCode() == HttpStatus.BAD_REQUEST)

                System.out.println(supplier + "'s API was unavailable.");
            else if (response.getStatusCode() == HttpStatus.REQUEST_TIMEOUT)
                System.out.println(supplier + "'s API response time run out.");

            return new ArrayList<Option>();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Ride ride = objectMapper.readValue(response.getBody(), Ride.class);

        List<Option> options = new ArrayList<>();

        for (Option option : ride.getOptions()) {
            if (carTypesMaximumPassengers.get(option.getCar_type()) >= numberOfPassengers)
                options.add(new Option(supplier.toUpperCase(), option.getCar_type(), option.getPrice()));
        }
        options.sort(Comparator.comparing(Option::getPrice).reversed());
//        System.out.println(options);
        System.out.println("Ride options displayed.");
        return options;
    }


    /**
     * Get the available rides considering all three suppliers and return a list of options
     *
     * @param pickup
     * @param dropoff
     * @param numberOfPassengers
     * @return response\
     */
    public List<Option> getCarsFromAllSuppliers(String pickup, String dropoff,
                                                @RequestParam(required = false) Integer numberOfPassengers)
            throws JsonProcessingException {

        ArrayList<String> suppliers = new ArrayList<>();
        suppliers.add("dave");
        suppliers.add("eric");
        suppliers.add("jeff");

        HashMap<String, Integer> carTypesMaximumPassengers = getCarTypesMaximumPassengers();
//        System.out.println(carTypesMaximumPassengers);
        HashMap<String,Integer> suppliersPrices = new HashMap<>();
        HashMap<String, HashMap<String,Integer>> carTypeSupplierPrice = new HashMap<>();

        if(numberOfPassengers == null) numberOfPassengers = 0;

        for (String supplier: suppliers) {
            // Execute the get request.
            ResponseEntity<String> response = this.getResponse(supplier,pickup, dropoff, numberOfPassengers);

            if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR || response.getStatusCode() == HttpStatus.BAD_REQUEST){
                System.out.println(supplier + "'s API was unavailable.");
                continue;
            }
            else if (response.getStatusCode() == HttpStatus.REQUEST_TIMEOUT){
                System.out.println(supplier + "'s API response time run out.");
                continue;
            }

            ObjectMapper objectMapper = new ObjectMapper();
            Ride ride = objectMapper.readValue(response.getBody(), Ride.class);
//            System.out.println(ride);

            for (Option option: ride.getOptions()) {
                // if carType is in dict
                if (carTypeSupplierPrice.containsKey(option.getCar_type())) //supplier is in dict
                    carTypeSupplierPrice.get(option.getCar_type()).put(ride.getSupplier_id(), option.getPrice());
                else {  // if carType is not in dictionary
                    suppliersPrices.put(ride.getSupplier_id(), option.getPrice());
                    carTypeSupplierPrice.put(option.getCar_type(), new HashMap<String, Integer>(suppliersPrices));
                    suppliersPrices.clear();
                }
            }
        }

        ArrayList<Option> response = new ArrayList<>();

        for (String carType : carTypeSupplierPrice.keySet()) {
            Map.Entry entry = carTypeSupplierPrice.get(carType).entrySet().stream()
                    .min(Comparator.comparingInt(Map.Entry::getValue)).get();
            if (carTypesMaximumPassengers.get(carType) >= numberOfPassengers)
                response.add(new Option((String) entry.getKey(), carType, (Integer) entry.getValue()));
        }
        response.sort(Comparator.comparing(Option::getPrice).reversed());
//        System.out.println(response);
        System.out.println("Ride options displayed.");
        return response;
    }



    /**
     * Returns a HashMap that stores the maximum capacity of each type of car.
     *
     * @return carTypesMaximumPassengers
     */
    private HashMap<String, Integer> getCarTypesMaximumPassengers() {
        HashMap<String, Integer> carTypesMaximumPassengers = new HashMap<>();

        carTypesMaximumPassengers.put("STANDARD", 4);
        carTypesMaximumPassengers.put("EXECUTIVE", 4);
        carTypesMaximumPassengers.put("LUXURY", 4);
        carTypesMaximumPassengers.put("PEOPLE_CARRIER", 6);
        carTypesMaximumPassengers.put("LUXURY_PEOPLE_CARRIER", 6);
        carTypesMaximumPassengers.put("MINIBUS", 16);

        return carTypesMaximumPassengers;
    }

}

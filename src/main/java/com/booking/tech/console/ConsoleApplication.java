package com.booking.tech.console;

import com.booking.tech.entities.Option;
import com.booking.tech.service.TechAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;


@Component
public class ConsoleApplication implements CommandLineRunner {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private TechAppService service;

    @Override
    public void run(String... args) throws Exception {

        Integer numberOfPassengers = null;
        String supplier = null;

        if (args.length == 0)
            return;

        String pickup = args[0];
        String dropoff = args[1];
        ArrayList<Option> response = null;

        if (args.length == 2)
            response = (ArrayList<Option>) service.getCarsFromAllSuppliers(pickup, dropoff, null);
        else if (args.length == 3) {
            numberOfPassengers = Integer.parseInt(args[2]);
            response = (ArrayList<Option>) service.getCarsFromAllSuppliers(pickup, dropoff, numberOfPassengers);
        } else if (args.length == 4) {
            numberOfPassengers = Integer.parseInt(args[2]);
            supplier = args[3];
            response = (ArrayList<Option>) service.getCarsFromGivenSupplier(supplier, pickup, dropoff, numberOfPassengers);

        }

        // If no ride option available print empty list
        if (response == null) {
            System.out.println(new ArrayList<>());
            return;
        }

        for (Option option : response){
            System.out.println(option.getCar_type() + " - " + option.getSupplier() + " - " + option.getPrice());
        }
    }
}

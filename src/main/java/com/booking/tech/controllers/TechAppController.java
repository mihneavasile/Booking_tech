package com.booking.tech.controllers;

import com.booking.tech.entities.Option;
import com.booking.tech.service.TechAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TechAppController {
    @Autowired
    private TechAppService service;

    /**
     * Get available rides from all suppliers
     *
     * @param pickup
     * @param dropoff
     * @param numberOfPassengers
     * @return response
     */
    @GetMapping("/ride")
    public ResponseEntity<List<Option>> getAvailableRides(@RequestParam String pickup, @RequestParam String dropoff,
                                                          @RequestParam(required = false) Integer numberOfPassengers)
            throws Exception {

        List<Option> response = service.getCarsFromAllSuppliers(pickup, dropoff, numberOfPassengers);
        return ResponseEntity.ok(response);
    }

    /**
     * Get available rides from all suppliers
     *
     * @param supplier
     * @param pickup
     * @param dropoff
     * @param numberOfPassengers
     * @return response
     */
    @GetMapping("/ride/{supplier}")
    public ResponseEntity<List<Option>> getAvailableRidesFromSupplier(@PathVariable String supplier, @RequestParam String pickup, @RequestParam String dropoff,
                                                                      @RequestParam(required = false) Integer numberOfPassengers)
            throws Exception {

        List<Option> response = service.getCarsFromGivenSupplier(supplier, pickup, dropoff, numberOfPassengers);
        return ResponseEntity.ok(response);
    }





}

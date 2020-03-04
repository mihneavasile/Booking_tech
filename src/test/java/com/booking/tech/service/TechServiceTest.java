package com.booking.tech.service;

import com.booking.tech.entities.Option;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;



public class TechServiceTest {

    @Spy
    @InjectMocks
    private TechAppService techAppService;

    private static final String pickup = "47.908141,-24.191402";
    private static final String dropoff = "47.908141,-24.191402";
    private final String DAVE = "DAVE";
    private final String ERIC = "ERIC";
    private final String JEFF = "JEFF";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getCarsFromGivenSupplier_whenSupplierRequestTimeout_thenReturnEmptyOptions() throws JsonProcessingException {

        when(techAppService.getResponse(DAVE, pickup, dropoff, null))
                .thenReturn(ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(DAVE + "'s API timed out."));

        List<Option> response = techAppService.getCarsFromGivenSupplier(DAVE, pickup, dropoff, null);

        for (Option option : response){
            assertNull(option.getSupplier());
            assertNull(option.getCar_type());
            assertNull(option.getPrice());
        }
    }

    @Test
    public void getCarsFromGivenSupplier_whenSupplierUnavailable_thenReturnEmptyOptions() throws JsonProcessingException {

        when(techAppService.getResponse(DAVE, pickup, dropoff, null))
                .thenReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(DAVE + "'s API is currently unavailable"));

        List<Option> response = techAppService.getCarsFromGivenSupplier(DAVE, pickup, dropoff, null);

        for (Option option : response){
            assertNull(option.getSupplier());
            assertNull(option.getCar_type());
            assertNull(option.getPrice());
        }
    }
}

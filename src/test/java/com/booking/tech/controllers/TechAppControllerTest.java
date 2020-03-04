package com.booking.tech.controllers;

import com.booking.tech.entities.Option;
import com.booking.tech.service.TechAppService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@DirtiesContext
@ActiveProfiles("test")
public class TechAppControllerTest {

    private MockMvc mvc;

    @Mock
    private TechAppService techAppService;

    @InjectMocks
    private TechAppController techAppController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(techAppController).build();
    }

    private final String DAVE = "DAVE";
    private final String ERIC = "ERIC";
    private final String JEFF = "JEFF";
    private final String pickup = "47.908141,-24.191402";
    private final String dropoff = "47.908141,-24.191402";



    @Test
    public void getAvailableRidesWithEmptyOptionList() throws Exception{

        when(techAppService.getCarsFromAllSuppliers(pickup, dropoff, null))
                .thenReturn(Collections.<Option> emptyList());

        mvc.perform(get("/ride" + "?pickup=" + pickup + "&dropoff=" + dropoff)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(handler().methodName("getAvailableRides")).andExpect(jsonPath("$.length()", equalTo(0)))
                .andExpect(content().json("[ ]"));

        verify(techAppService).getCarsFromAllSuppliers(pickup, dropoff, null);
    }

    @Test
    public void getAvailableRides_whenNonEmptyOptionList() throws Exception {

        Option option1 = new Option("LUXURY", 214978);
        option1.setSupplier(JEFF);
        Option option2 = new Option("LUXURY_PEOPLE_CARRIER", 701741);
        option2.setSupplier(DAVE);
        Option option3 = new Option("LUXURY_PEOPLE_CARRIER", 101741);
        option3.setSupplier(ERIC);

        when(techAppService.getCarsFromAllSuppliers(pickup, dropoff, null))
                .thenReturn(Arrays.asList(option1, option3));

        mvc.perform(get("/ride" + "?pickup=" + pickup + "&dropoff=" + dropoff)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(handler().methodName("getAvailableRides")).andExpect(jsonPath("$.length()", equalTo(2)))
                .andExpect(content().json("[{'supplier': 'JEFF', 'price': 214978, 'car_type': 'LUXURY'}, "
                        + " {'supplier': 'ERIC', 'price': 101741, 'car_type': 'LUXURY_PEOPLE_CARRIER'}]"));

        verify(techAppService).getCarsFromAllSuppliers(pickup, dropoff, null);
    }

    @Test
    public void getAvailableRides_whenNonEmptyOptionListWithPassengers() throws Exception {

        Option option1 = new Option("LUXURY", 214978);
        option1.setSupplier(JEFF);
        Option option2 = new Option("LUXURY_PEOPLE_CARRIER", 701741);
        option2.setSupplier(DAVE);
        Option option3 = new Option("LUXURY_PEOPLE_CARRIER", 101741);
        option3.setSupplier(ERIC);
        final Integer numberOfPassengers = 2;

        when(techAppService.getCarsFromAllSuppliers(pickup, dropoff, numberOfPassengers))
                .thenReturn(Arrays.asList(option1, option3));

        mvc.perform(get("/ride" + "?pickup=" + pickup + "&dropoff=" + dropoff + "&numberOfPassengers=" + numberOfPassengers)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(handler().methodName("getAvailableRides")).andExpect(jsonPath("$.length()", equalTo(2)))
                .andExpect(content().json("[{'supplier': 'JEFF', 'price': 214978, 'car_type': 'LUXURY'}, "
                        + " {'supplier': 'ERIC', 'price': 101741, 'car_type': 'LUXURY_PEOPLE_CARRIER'}]"));

        verify(techAppService).getCarsFromAllSuppliers(pickup, dropoff, numberOfPassengers);
    }

    @Test
    public void getAvailableRidesFromSupplier_whenNonEmptyOptionList() throws Exception {

        Option option1 = new Option("LUXURY", 214978);
        option1.setSupplier(DAVE);
        Option option2 = new Option("LUXURY_PEOPLE_CARRIER", 701741);
        option2.setSupplier(DAVE);
        Option option3 = new Option("LUXURY_PEOPLE_CARRIER", 101741);
        option3.setSupplier(ERIC);
        final String supplier = DAVE.toLowerCase();

        when(techAppService.getCarsFromGivenSupplier(supplier, pickup, dropoff, null))
                .thenReturn(Arrays.asList(option1, option2));

        mvc.perform(get("/ride/" + supplier + "?pickup=" + pickup + "&dropoff=" + dropoff)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(handler().methodName("getAvailableRidesFromSupplier")).andExpect(jsonPath("$.length()", equalTo(2)))
                .andExpect(content().json("[{'supplier': 'DAVE', 'price': 214978, 'car_type': 'LUXURY'}, "
                        + " {'supplier': 'DAVE', 'price': 701741, 'car_type': 'LUXURY_PEOPLE_CARRIER'}]"));

        verify(techAppService).getCarsFromGivenSupplier(supplier, pickup, dropoff, null);
    }

    @Test
    public void getAvailableRidesFromSupplier_whenInvalidSupplierNonEmptyOptionList() throws Exception {

        Option option1 = new Option("LUXURY", 214978);
        option1.setSupplier(DAVE);
        Option option2 = new Option("LUXURY_PEOPLE_CARRIER", 701741);
        option2.setSupplier(DAVE);
        Option option3 = new Option("LUXURY_PEOPLE_CARRIER", 101741);
        option3.setSupplier(ERIC);
        final String supplier = "ivalid-supplier";

        when(techAppService.getCarsFromGivenSupplier(supplier, pickup, dropoff, null))
                .thenReturn(Arrays.asList());

        mvc.perform(get("/ride/" + supplier + "?pickup=" + pickup + "&dropoff=" + dropoff)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(handler().methodName("getAvailableRidesFromSupplier")).andExpect(jsonPath("$.length()", equalTo(0)))
                .andExpect(content().json("[ ]"));

        verify(techAppService).getCarsFromGivenSupplier(supplier, pickup, dropoff, null);
    }
}

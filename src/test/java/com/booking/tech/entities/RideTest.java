package com.booking.tech.entities;

import com.booking.tech.controllers.TechAppController;
import com.booking.tech.service.TechAppService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class RideTest {

    @Test
    public void RideSettersTest(){
        Ride ride = new Ride();
        ride.setSupplier_id("ERIC");
        ride.setDropoff("132,312");
        ride.setPickup("7632,14");

        assert ride.getSupplier_id() == "ERIC";
        assert ride.getPickup() == "7632,14";
        assert ride.getDropoff() == "132,312";
    }

}

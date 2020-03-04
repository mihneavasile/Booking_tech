package com.booking.tech.entities;

import org.junit.Test;

public class OptionTest {

    @Test
    public void RideSettersTest(){
        Option option = new Option();
        option.setSupplier("ERIC");
        option.setCar_type("STANDARD");
        option.setPrice(796234);

        assert option.getSupplier() == "ERIC";
        assert option.getCar_type() == "STANDARD";
        assert option.getPrice() == 796234;
    }
}

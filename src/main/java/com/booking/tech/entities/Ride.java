package com.booking.tech.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class Ride {

    @JsonProperty("supplier_id")
    private String supplier_id;
    @JsonProperty("pickup")
    private String pickup;
    @JsonProperty("dropoff")
    private String dropoff;
    @JsonProperty("options")
    private List<Option> options;

    public Ride() {
    }

    /**
     * Gonstruct a Ride object using supplier, pickupLocation and dropoffLocation and options
     *
     * @param supplierId
     * @param pickupLocation
     * @param dropoffLocation
     * @param options
     *
     */
    public Ride(String supplierId, String pickupLocation, String dropoffLocation, List<Option> options) {
        this.supplier_id = supplierId;
        this.pickup = pickupLocation;
        this.dropoff = dropoffLocation;
        this.options = options;
    }


    @Override
    public String toString() {
        return "Ride{" +
                "supplier_id='" + supplier_id + '\'' +
                ", pickup='" + pickup + '\'' +
                ", dropoff='" + dropoff + '\'' +
                ", options=" + options +
                '}';
    }

    public String getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(String supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getDropoff() {
        return dropoff;
    }

    public void setDropoff(String dropoff) {
        this.dropoff = dropoff;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }
}

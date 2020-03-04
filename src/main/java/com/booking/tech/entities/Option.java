package com.booking.tech.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Option {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String supplier;

    @JsonProperty("car_type")
    private String car_type;
    @JsonProperty("price")
    private Integer price;

    public Option() {
    }

    /**
     * Gonstruct an Option object using supplier, car_type and price
     *
     * @param supplier
     * @param carType
     * @param price
     *
     */
    public Option(String supplier, String carType, Integer price) {
        this.supplier = supplier;
        this.car_type = carType;
        this.price = price;
    }

    /**
     * Gonstruct an Option object using car_type and price
     *
     * @param carType
     * @param price
     *
     */
    public Option(String carType, Integer price) {
        this.car_type = carType;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Option{" +
                "supplier='" + supplier + '\'' +
                ", car_type='" + car_type + '\'' +
                ", price=" + price +
                '}';
    }

    public String getCar_type() {
        return car_type;
    }

    public String getSupplier() { return supplier; }

    public void setSupplier(String supplier) { this.supplier = supplier; }

    public void setCar_type(String car_type) {
        this.car_type = car_type;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}

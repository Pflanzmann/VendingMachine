package com.vending.models.cakes;

import com.vending.models.Allergen;
import com.vending.models.Manufacturer;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.EnumSet;

public class Covering implements Cake{
    private Cake cake;

    public Covering(Cake cake) {
        this.cake = cake;
    }

    @Override
    public String getManufacturerName() {
        return cake.getManufacturerName();
    }

    @Override
    public BigDecimal getPrice() {
        return cake.getPrice();
    }

    @Override
    public EnumSet<Allergen> getAllergens() {
        return cake.getAllergens();
    }

    @Override
    public int getNutritionalValue() {
        return cake.getNutritionalValue();
    }

    @Override
    public Duration getShelfLife() {
        return cake.getShelfLife();
    }

    @Override
    public String getCakeType() {
        return cake.getCakeType();
    }
}

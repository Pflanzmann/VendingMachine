package com.vending.models.cakes;

import com.vending.models.Allergen;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.EnumSet;

public interface Cake extends Serializable {
    String getManufacturerName();
    BigDecimal getPrice();
    EnumSet<Allergen> getAllergens();
    int getNutritionalValue();
    Duration getShelfLife();
    String getCakeType();
}

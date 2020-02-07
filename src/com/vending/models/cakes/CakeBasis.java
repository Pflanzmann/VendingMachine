package com.vending.models.cakes;

import com.vending.models.Allergen;
import com.vending.models.CoveringType;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.EnumSet;

public class CakeBasis implements Cake {

    private String manufacturerName;
    private BigDecimal price = new BigDecimal(12);
    private EnumSet<Allergen> allergens = EnumSet.of(Allergen.Gluten);
    private int nutritionalValue = 42;
    private Duration shelfLife = Duration.ofDays(16);

    public CakeBasis(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    @Override
    public String getManufacturerName() {
        return manufacturerName;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public EnumSet<Allergen> getAllergens() {
        return allergens;
    }

    @Override
    public int getNutritionalValue() {
        return nutritionalValue;
    }

    @Override
    public Duration getShelfLife() {
        return shelfLife;
    }

    @Override
    public String getCakeType() {
        return "Cake basis with: ";
    }

    @Override
    public ArrayList<CoveringType> getCoverings() {
        ArrayList<CoveringType> covering = new ArrayList<>();
        covering.add(CoveringType.BASE);
        return covering;
    }

    public static class CakeFactory {

        public static Cake addPeanutCovering(Cake cake) {
            return new PeanutCovering(cake);
        }

        public static Cake addHazelnutCovering(Cake cake) {
            return new HazelnutCovering(cake);
        }
    }

}

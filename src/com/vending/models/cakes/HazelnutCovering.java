package com.vending.models.cakes;

import com.vending.models.Allergen;
import com.vending.models.CoveringType;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.EnumSet;

public class HazelnutCovering extends Covering {
    public HazelnutCovering(Cake cake) {
        super(cake);
    }

    @Override
    public BigDecimal getPrice() {
        return super.getPrice().add(new BigDecimal(4.5));
    }

    @Override
    public EnumSet<Allergen> getAllergens() {
        EnumSet<Allergen> temp = super.getAllergens();
        temp.add(Allergen.Hazlenut);
        return temp;
    }

    @Override
    public int getNutritionalValue() {
        return super.getNutritionalValue() + 187;
    }

    @Override
    public Duration getShelfLife() {
        Duration appleShelfLife = Duration.ofDays(15);
        Duration actualShelfLife = super.getShelfLife();

        if (actualShelfLife.getSeconds() < appleShelfLife.getSeconds())
            return actualShelfLife;
        else
            return appleShelfLife;
    }

    @Override
    public String getCakeType() {
        return super.getCakeType() + "Hazelnut, ";
    }

    @Override
    public ArrayList<CoveringType> getCoverings() {
        ArrayList<CoveringType> coverings = super.getCoverings();
        coverings.add(CoveringType.HAZELNUT);
        return coverings;
    }
}

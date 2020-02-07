package com.vending.models.cakes;

import com.vending.models.Allergen;
import com.vending.models.CoveringType;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.EnumSet;

public class PeanutCovering extends Covering {

    public PeanutCovering(Cake cake) {
        super(cake);
    }

    @Override
    public BigDecimal getPrice() {
        return super.getPrice().add(new BigDecimal(3));
    }

    @Override
    public EnumSet<Allergen> getAllergens() {
        EnumSet<Allergen> temp = super.getAllergens();
        temp.add(Allergen.Peanut);
        return temp;
    }

    @Override
    public int getNutritionalValue() {
        return super.getNutritionalValue() + 567;
    }

    @Override
    public Duration getShelfLife() {
        Duration appleShelfLife = Duration.ofDays(7);
        Duration actualShelfLife = super.getShelfLife();

        if(actualShelfLife.getSeconds() < appleShelfLife.getSeconds())
            return actualShelfLife;
        else
            return appleShelfLife;
    }

    @Override
    public String getCakeType(){
        return super.getCakeType() + "Peanutcovering, ";
    }

    @Override
    public ArrayList<CoveringType> getCoverings() {
        ArrayList<CoveringType> coverings = super.getCoverings();
        coverings.add(CoveringType.PEANUT);
        return coverings;
    }
}

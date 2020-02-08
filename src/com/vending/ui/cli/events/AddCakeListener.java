package com.vending.ui.cli.events;

import com.vending.exceptions.ContainsCakeException;
import com.vending.exceptions.ManufacturerNotFoundException;
import com.vending.exceptions.NoSpaceException;
import com.vending.logic.VendingMachine;
import com.vending.models.cakes.Cake;
import com.vending.ui.EventListener;

public class AddCakeListener implements EventListener<Cake> {

    private VendingMachine vendingMachine;

    public AddCakeListener(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void handle(Cake value) {
        try {
            vendingMachine.addCakeGetIndex(value);
        } catch (ManufacturerNotFoundException e) {
            e.printStackTrace();
        } catch (NoSpaceException e) {
            e.printStackTrace();
        } catch (ContainsCakeException e) {
            e.printStackTrace();
        }
    }
}

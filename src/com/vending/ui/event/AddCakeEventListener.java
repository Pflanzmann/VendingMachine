package com.vending.ui.event;

import com.vending.exceptions.ContainsCakeException;
import com.vending.exceptions.ManufacturerNotFoundException;
import com.vending.exceptions.NoSpaceException;
import com.vending.logic.VendingMachine;
import com.vending.models.cakes.Cake;

public class AddCakeEventListener implements EventListener<Cake> {

    private VendingMachine vendingMachine;

    public AddCakeEventListener(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    public void onAddCake(Cake cake) {

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

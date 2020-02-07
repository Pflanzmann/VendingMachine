package com.vending.ui.event.cli;

import com.vending.exceptions.ManufacturerAlreadyExistsException;
import com.vending.logic.VendingMachine;
import com.vending.models.Manufacturer;
import com.vending.ui.event.EventListener;

public class AddManufacturerListener implements EventListener<Manufacturer> {

    private VendingMachine vendingMachine;

    public AddManufacturerListener(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void handle(Manufacturer value) {
        try {
            vendingMachine.addManufacturer(value);
        } catch (ManufacturerAlreadyExistsException e) {

        }
    }
}

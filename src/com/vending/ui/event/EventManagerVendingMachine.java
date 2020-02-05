package com.vending.ui.event;

import com.vending.logic.VendingMachine;
import com.vending.models.Manufacturer;
import com.vending.models.cakes.Cake;
import com.vending.ui.EventHandler;

public class EventManagerVendingMachine {

    private VendingMachine vendingMachine;

    EventHandler<Cake> addCakeEventHandler = new EventHandler<>();
    EventHandler<Manufacturer> addManufacturerEventHandler = new EventHandler<>();
    EventHandler<Integer> deleteCakeEventHandler = new EventHandler<>();
}

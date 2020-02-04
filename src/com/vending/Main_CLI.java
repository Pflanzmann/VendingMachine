package com.vending;

import com.vending.logic.VendingMachine;
import com.vending.models.Manufacturer;
import com.vending.models.cakes.Cake;
import com.vending.ui.CLI;
import com.vending.ui.EventHandler;
import com.vending.ui.event.*;

import java.util.ArrayList;
import java.util.List;

public class Main_CLI {
    public static void main(String[] args) {
        EventHandler<Cake> addCakeEventHandler = new EventHandler<>();
        EventHandler<Manufacturer> addManufacturerEventHandler = new EventHandler<>();
        EventHandler<Integer> deleteCakeEventHandler = new EventHandler<>();

        EventHandler<ArrayList<Cake>> showAllCakesEventHandler = new EventHandler<>();
        EventHandler<List<Manufacturer>> showManufacturerEventHandler = new EventHandler<>();

        VendingMachine vendingMachine = new VendingMachine(10, showAllCakesEventHandler, showManufacturerEventHandler);
        CLI cli = new CLI(System.in, System.out, addCakeEventHandler, addManufacturerEventHandler, deleteCakeEventHandler);

        vendingMachine.addObserverAllergens(cli::setCurrentAllergens);
        vendingMachine.addObserverSlotCount(cli::setCurrentSlotCount);

        addCakeEventHandler.addListener(new AddCakeEventListener(vendingMachine));
        addManufacturerEventHandler.addListener(new AddManufacturerListener(vendingMachine));
        deleteCakeEventHandler.addListener(new DeleteCakeListener(vendingMachine));
        showAllCakesEventHandler.addListener(new ShowAllCakesListener(cli));
        showManufacturerEventHandler.addListener(new ShowAllManufacturersListener(cli));

        cli.start();
    }
}

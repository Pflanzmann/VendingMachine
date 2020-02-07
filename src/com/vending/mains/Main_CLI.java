package com.vending.mains;

import com.vending.logic.VendingMachine;
import com.vending.models.Manufacturer;
import com.vending.models.SerializableAction;
import com.vending.models.cakes.Cake;
import com.vending.ui.CLI;
import com.vending.ui.event.EventHandler;
import com.vending.ui.event.cli.*;

import java.util.List;

public class Main_CLI {
    public static void main(String[] args) {
        EventHandler<Cake> addCakeEventHandler = new EventHandler<>();
        EventHandler<Manufacturer> addManufacturerEventHandler = new EventHandler<>();
        EventHandler<Integer> deleteCakeEventHandler = new EventHandler<>();
        EventHandler<SerializableAction> loadOrStoreEventHandler = new EventHandler<>();

        EventHandler<Cake[]> showAllCakesEventHandler = new EventHandler<>();
        EventHandler<List<Manufacturer>> showManufacturerEventHandler = new EventHandler<>();

        VendingMachine vendingMachine = new VendingMachine(10, showAllCakesEventHandler, showManufacturerEventHandler);
        CLI cli = new CLI(System.in, System.out, addCakeEventHandler, addManufacturerEventHandler, deleteCakeEventHandler, loadOrStoreEventHandler);

        vendingMachine.addObserverAllergens(cli::setCurrentAllergens);
        vendingMachine.addObserverSlotCount(cli::setCurrentSlotCount);

        addCakeEventHandler.addListener(new AddCakeEventListener(vendingMachine));
        addManufacturerEventHandler.addListener(new AddManufacturerListener(vendingMachine));
        deleteCakeEventHandler.addListener(new DeleteCakeListener(vendingMachine));
        loadOrStoreEventHandler.addListener(new LoadOrStoreEventListener(vendingMachine));

        showAllCakesEventHandler.addListener(new ShowAllCakesCLIListener(cli));
        showManufacturerEventHandler.addListener(new ShowAllManufacturersCLIListener(cli));

        cli.start();
    }
}

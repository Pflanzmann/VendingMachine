package com.vending.mains;

import com.vending.logic.VendingMachine;
import com.vending.models.Manufacturer;
import com.vending.models.SerializableAction;
import com.vending.models.cakes.Cake;
import com.vending.ui.CLI;
import com.vending.ui.events.EventHandler;
import com.vending.ui.events.cli.*;

import java.util.List;

import javafx.util.Pair;

public class Main_CLI {
    public static void main(String[] args) {
        EventHandler<Cake> addCakeEventHandler = new EventHandler<>();
        EventHandler<Manufacturer> addManufacturerEventHandler = new EventHandler<>();
        EventHandler<Integer> deleteCakeEventHandler = new EventHandler<>();
        EventHandler<SerializableAction> loadOrStoreEventHandler = new EventHandler<>();
        EventHandler<Pair<Integer, Integer>> swapSlotsEventHandler = new EventHandler<>();

        EventHandler<Cake[]> showAllCakesEventHandler = new EventHandler<>();
        EventHandler<List<Manufacturer>> showManufacturerEventHandler = new EventHandler<>();

        VendingMachine vendingMachine = new VendingMachine(10, showAllCakesEventHandler, showManufacturerEventHandler);
        CLI cli = new CLI(System.in, System.out, addCakeEventHandler, addManufacturerEventHandler, deleteCakeEventHandler, loadOrStoreEventHandler, swapSlotsEventHandler);

        vendingMachine.addObserverAllergens(cli::setCurrentAllergens);
        vendingMachine.addObserverSlotCount(cli::setCurrentSlotCount);

        addCakeEventHandler.addListener(new AddCakeListener(vendingMachine));
        addManufacturerEventHandler.addListener(new AddManufacturerListener(vendingMachine));
        deleteCakeEventHandler.addListener(new DeleteCakeSlotListener(vendingMachine));
        loadOrStoreEventHandler.addListener(new LoadOrStoreListener(vendingMachine));
        swapSlotsEventHandler.addListener(new SwapCakesListener(vendingMachine));
        showAllCakesEventHandler.addListener(new ShowAllCakesCLIListener(cli));
        showManufacturerEventHandler.addListener(new ShowAllManufacturersCLIListener(cli));

        cli.start();
    }
}

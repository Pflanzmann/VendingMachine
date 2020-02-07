package com.vending.ui;

import com.vending.logic.VendingMachine;
import com.vending.models.Manufacturer;
import com.vending.models.SerializableAction;
import com.vending.models.cakes.Cake;
import com.vending.ui.event.EventHandler;
import com.vending.ui.event.cli.*;
import com.vending.ui.event.gui.ShowAllCakesGUIListener;
import com.vending.ui.event.gui.ShowAllManufacturersGUIListener;

import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.PipedInputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class RemoteStartCLI {

    private InputStream input;
    private PrintStream outputPrintStream;
    private ObjectOutputStream objectOutputStreamCakeListener;
    private ObjectOutputStream objectOutputStreamManufacturerListener;

    public RemoteStartCLI(InputStream input, PrintStream outputPrintStream, ObjectOutputStream objectOutputStreamCakeListener, ObjectOutputStream objectOutputStreamManufacturerListener) {
        this.input = input;
        this.outputPrintStream = outputPrintStream;
        this.objectOutputStreamCakeListener = objectOutputStreamCakeListener;
        this.objectOutputStreamManufacturerListener = objectOutputStreamManufacturerListener;
    }

    public void startCli() {
        new Thread() {
            @Override
            public void run() {
                EventHandler<Cake> addCakeEventHandler = new EventHandler<>();
                EventHandler<Manufacturer> addManufacturerEventHandler = new EventHandler<>();
                EventHandler<Integer> deleteCakeEventHandler = new EventHandler<>();
                EventHandler<SerializableAction> loadOrStoreEventHandler = new EventHandler<>();

                EventHandler<ArrayList<Cake>> showAllCakesEventHandler = new EventHandler<>();
                EventHandler<List<Manufacturer>> showManufacturerEventHandler = new EventHandler<>();

                VendingMachine vendingMachine = new VendingMachine(10, showAllCakesEventHandler, showManufacturerEventHandler);
                CLI cli = new CLI(input, outputPrintStream, addCakeEventHandler, addManufacturerEventHandler, deleteCakeEventHandler, loadOrStoreEventHandler);

                vendingMachine.addObserverAllergens(cli::setCurrentAllergens);
                vendingMachine.addObserverSlotCount(cli::setCurrentSlotCount);

                addCakeEventHandler.addListener(new AddCakeEventListener(vendingMachine));
                addManufacturerEventHandler.addListener(new AddManufacturerListener(vendingMachine));
                deleteCakeEventHandler.addListener(new DeleteCakeListener(vendingMachine));
                loadOrStoreEventHandler.addListener(new LoadOrStoreEventListener(vendingMachine));

                showAllCakesEventHandler.addListener(new ShowAllCakesGUIListener(objectOutputStreamCakeListener));
                showManufacturerEventHandler.addListener(new ShowAllManufacturersGUIListener(objectOutputStreamManufacturerListener));
                showAllCakesEventHandler.addListener(new ShowAllCakesCLIListener(cli));
                showManufacturerEventHandler.addListener(new ShowAllManufacturersCLIListener(cli));

                cli.start();
            }
        }.start();
    }
}
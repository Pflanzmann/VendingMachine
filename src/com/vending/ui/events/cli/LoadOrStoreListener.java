package com.vending.ui.events.cli;

import com.vending.logic.VendingMachine;
import com.vending.models.SerializableAction;
import com.vending.ui.events.EventListener;

import java.io.*;

public class LoadOrStoreListener implements EventListener<SerializableAction> {

    private VendingMachine vendingMachine;

    private final String FILE_NAME = "vendingMachineData.txt";

    public LoadOrStoreListener(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void handle(SerializableAction value) {
        if (value == SerializableAction.STORE) {
            try {
                OutputStream os = new FileOutputStream(new File(FILE_NAME));
                vendingMachine.serialize(os);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                InputStream is = new FileInputStream(FILE_NAME);
                vendingMachine.deserialize(is);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

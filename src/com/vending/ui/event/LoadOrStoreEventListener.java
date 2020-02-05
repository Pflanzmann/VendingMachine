package com.vending.ui.event;

import com.vending.logic.VendingMachine;
import com.vending.models.SerializableAction;

import java.io.*;

public class LoadOrStoreEventListener implements EventListener<SerializableAction> {

    private VendingMachine vendingMachine;

    private final String FILE_NAME = "vendingMachineData.txt";

    public LoadOrStoreEventListener(VendingMachine vendingMachine) {
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

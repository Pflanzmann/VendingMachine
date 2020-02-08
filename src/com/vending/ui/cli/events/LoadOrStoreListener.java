package com.vending.ui.cli.events;

import com.vending.logic.VendingMachine;
import com.vending.models.SerializableAction;
import com.vending.ui.EventListener;

import java.io.*;

public class LoadOrStoreListener implements EventListener<SerializableAction> {

    private VendingMachine vendingMachine;

    private final String FILE_NAME = "vendingMachineData.txt";

    public LoadOrStoreListener(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void handle(SerializableAction value) {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        switch (value) {
            case STORE:
                try {
                    OutputStream os = new FileOutputStream(new File(FILE_NAME));
                    vendingMachine.serialize(os);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case LOAD:
                try {
                    InputStream is = new FileInputStream(FILE_NAME);
                    vendingMachine.deserialize(is);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}

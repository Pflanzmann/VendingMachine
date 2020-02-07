package com.vending.ui.event.gui;

import com.vending.models.Manufacturer;
import com.vending.ui.event.EventListener;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class ShowAllManufacturersGUIListener implements EventListener<List<Manufacturer>> {

    private ObjectOutputStream objectOutputStream;

    public ShowAllManufacturersGUIListener(ObjectOutputStream outputStream) {
        this.objectOutputStream = outputStream;
    }

    @Override
    public void handle(List<Manufacturer> value) {
        try {
            objectOutputStream.writeObject(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

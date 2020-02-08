package com.vending.ui.cli.events;

import com.vending.models.Manufacturer;
import com.vending.ui.EventListener;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class ShowAllManufacturersStreamListener implements EventListener<List<Manufacturer>> {

    private ObjectOutputStream objectOutputStream;

    public ShowAllManufacturersStreamListener(ObjectOutputStream outputStream) {
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

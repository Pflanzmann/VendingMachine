package com.vending.ui.cli.events;

import com.vending.models.cakes.Cake;
import com.vending.ui.EventListener;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class ShowAllCakesStreamListener implements EventListener<Cake[]> {

    private ObjectOutputStream objectOutputStream;

    public ShowAllCakesStreamListener(ObjectOutputStream outputStream) {
        this.objectOutputStream = outputStream;
    }

    @Override
    public void handle(Cake[] value) {
        try {
            objectOutputStream.writeObject(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

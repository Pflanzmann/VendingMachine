package com.vending.ui.events.gui;

import com.vending.models.cakes.Cake;
import com.vending.ui.events.EventListener;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class ShowAllCakesGUIListener implements EventListener<Cake[]> {

    private ObjectOutputStream objectOutputStream;

    public ShowAllCakesGUIListener(ObjectOutputStream outputStream) {
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

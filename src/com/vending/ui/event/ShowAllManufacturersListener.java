package com.vending.ui.event;

import com.vending.models.Manufacturer;
import com.vending.ui.CLI;

import java.util.ArrayList;
import java.util.List;

public class ShowAllManufacturersListener implements EventListener<List<Manufacturer>> {
    private CLI cli;

    public ShowAllManufacturersListener(CLI cli) {
        this.cli = cli;
    }

    @Override
    public void handle(List<Manufacturer> value) {
        String output = "";

        for(int i = 0; i < value.size(); i++) {
            output += "\nManufacturer name: " + value.get(i).getName();
        }

        cli.setLatestManufacturers(output);
    }
}

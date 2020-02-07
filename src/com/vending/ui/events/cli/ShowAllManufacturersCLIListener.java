package com.vending.ui.events.cli;

import com.vending.models.Manufacturer;
import com.vending.ui.CLI;
import com.vending.ui.events.EventListener;

import java.util.List;

public class ShowAllManufacturersCLIListener implements EventListener<List<Manufacturer>> {
    private CLI cli;

    public ShowAllManufacturersCLIListener(CLI cli) {
        this.cli = cli;
    }

    @Override
    public void handle(List<Manufacturer> value) {
        String output = "";

        for(int i = 0; i < value.size(); i++) {
            output += "\nManufacturer name: " + value.get(i).getName();
        }

        cli.setLatestManufacturersString(output);
    }
}

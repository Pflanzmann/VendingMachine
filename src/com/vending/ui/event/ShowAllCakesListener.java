package com.vending.ui.event;

import com.vending.models.cakes.Cake;
import com.vending.ui.CLI;

import java.util.ArrayList;

public class ShowAllCakesListener implements EventListener<ArrayList<Cake>> {

    private CLI cli;

    public ShowAllCakesListener(CLI cli) {
        this.cli = cli;
    }

    @Override
    public void handle(ArrayList<Cake> value) {
        String output = "";

        for(int i = 0; i < value.size(); i++) {
            output += "\nSlot: " + i + " | Cake: " + value.get(i).getCakeType();
        }

        cli.setLatestCakes(output);
    }
}

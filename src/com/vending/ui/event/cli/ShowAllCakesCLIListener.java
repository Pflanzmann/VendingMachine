package com.vending.ui.event.cli;

import com.vending.models.cakes.Cake;
import com.vending.ui.CLI;
import com.vending.ui.event.EventListener;

import java.util.ArrayList;

public class ShowAllCakesCLIListener implements EventListener<ArrayList<Cake>> {

    private CLI cli;

    public ShowAllCakesCLIListener(CLI cli) {
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

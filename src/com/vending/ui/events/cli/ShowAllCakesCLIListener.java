package com.vending.ui.events.cli;

import com.vending.models.cakes.Cake;
import com.vending.ui.CLI;
import com.vending.ui.events.EventListener;

public class ShowAllCakesCLIListener implements EventListener<Cake[]> {

    private CLI cli;

    public ShowAllCakesCLIListener(CLI cli) {
        this.cli = cli;
    }

    @Override
    public void handle(Cake[] value) {
        String output = "";

        for (int i = 0; i < value.length; i++) {
            if (value[i] != null)
                output += "\nSlot: " + i + " | Cake: " + value[i].getCakeType();
            else
                output += "\nSlot: " + i + " | Cake: empty";
        }

        cli.setLatestCakesString(output);
    }
}

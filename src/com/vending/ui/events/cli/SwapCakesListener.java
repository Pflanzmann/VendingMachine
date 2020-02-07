package com.vending.ui.events.cli;

import com.vending.logic.VendingMachine;
import com.vending.ui.events.EventListener;

import javafx.util.Pair;

public class SwapCakesListener implements EventListener<Pair<Integer, Integer>> {

    private VendingMachine vendingMachine;

    public SwapCakesListener(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void handle(Pair<Integer, Integer> value) {
        vendingMachine.swapSlots(value.getKey(), value.getValue());
    }
}

package com.vending.ui.cli.events;

import com.vending.exceptions.SwapNotPossibleException;
import com.vending.logic.VendingMachine;
import com.vending.ui.EventListener;

import javafx.util.Pair;

public class SwapCakesListener implements EventListener<Pair<Integer, Integer>> {

    private VendingMachine vendingMachine;

    public SwapCakesListener(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void handle(Pair<Integer, Integer> value) {
        try {
            vendingMachine.swapSlots(value.getKey(), value.getValue());
        } catch (SwapNotPossibleException e) {
            e.printStackTrace();
        }
    }
}

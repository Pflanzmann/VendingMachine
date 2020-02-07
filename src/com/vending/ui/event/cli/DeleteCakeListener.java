package com.vending.ui.event.cli;

import com.vending.logic.VendingMachine;
import com.vending.models.cakes.Cake;
import com.vending.ui.event.EventListener;

public class DeleteCakeListener implements EventListener<Integer> {

    private VendingMachine vendingMachine;

    public DeleteCakeListener(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void handle(Integer value) {
        if (value >= vendingMachine.getAllCakes().size())
            return;
        Cake cake = vendingMachine.getCakeFromSlot(value);
        vendingMachine.removeCake(cake);
    }
}

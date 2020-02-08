package com.vending.ui.cli.events;

import com.vending.logic.VendingMachine;
import com.vending.models.cakes.Cake;
import com.vending.ui.EventListener;

public class DeleteCakeSlotListener implements EventListener<Integer> {

    private VendingMachine vendingMachine;

    public DeleteCakeSlotListener(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void handle(Integer value) {
        Cake cake = vendingMachine.getCakeFromSlot(value);
        vendingMachine.removeCake(cake);
    }
}

package com.vending.ui.event;

import com.vending.logic.VendingMachine;
import com.vending.models.cakes.Cake;

public class DeleteCakeListener implements EventListener<Integer>{

    private VendingMachine vendingMachine;

    public DeleteCakeListener(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void handle(Integer value) {
        Cake cake = vendingMachine.getCakeFromSlot(value);
        vendingMachine.removeCake(cake);
    }
}

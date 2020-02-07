package com.tests.ui.events.cli;

import com.vending.logic.VendingMachine;
import com.vending.ui.events.cli.SwapCakesListener;

import org.junit.jupiter.api.Test;

import javafx.util.Pair;

import static org.mockito.Mockito.*;

class SwapCakesListenerTest {

    @Test
    void test() {
        VendingMachine mockVendingMachine = mock(VendingMachine.class);

        SwapCakesListener swapCakesListener = new SwapCakesListener(mockVendingMachine);

        int testOrigin = 0;
        int testDestination = 0;
        Pair<Integer, Integer> testInput = new Pair(testOrigin, testDestination);

        swapCakesListener.handle(testInput);

        verify(mockVendingMachine, times(1)).swapSlots(testOrigin, testDestination);
    }

}
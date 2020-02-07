package com.tests.ui.events.cli;

import com.vending.exceptions.ContainsCakeException;
import com.vending.exceptions.ManufacturerNotFoundException;
import com.vending.exceptions.NoSpaceException;
import com.vending.logic.VendingMachine;
import com.vending.models.cakes.Cake;
import com.vending.models.cakes.CakeBasis;
import com.vending.ui.events.cli.AddCakeListener;
import com.vending.ui.events.cli.SwapCakesListener;

import org.junit.jupiter.api.Test;

import javafx.util.Pair;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddCakeListenerTest {

    @Test
    void handle() throws ManufacturerNotFoundException, ContainsCakeException, NoSpaceException {
        VendingMachine mockVendingMachine = mock(VendingMachine.class);

        AddCakeListener addCakeListener = new AddCakeListener(mockVendingMachine);

        Cake testInput = mock(Cake.class);
        addCakeListener.handle(testInput);

        verify(mockVendingMachine, times(1)).addCakeGetIndex(testInput);
    }
}
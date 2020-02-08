package tests.ui.cli.events;

import com.vending.exceptions.ContainsCakeException;
import com.vending.exceptions.ManufacturerNotFoundException;
import com.vending.exceptions.NoSpaceException;
import com.vending.logic.VendingMachine;
import com.vending.models.cakes.Cake;
import com.vending.ui.cli.events.AddCakeListener;

import org.junit.jupiter.api.Test;

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
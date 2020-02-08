package tests.ui.cli.events;

import com.vending.exceptions.ManufacturerAlreadyExistsException;
import com.vending.logic.VendingMachine;
import com.vending.models.Manufacturer;
import com.vending.ui.cli.events.AddManufacturerListener;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class AddManufacturerListenerTest {

    @Test
    void handle() throws ManufacturerAlreadyExistsException {
        VendingMachine mockVendingMachine = mock(VendingMachine.class);

        AddManufacturerListener addManufacturerListener = new AddManufacturerListener(mockVendingMachine);

        Manufacturer testInput = mock(Manufacturer.class);
        addManufacturerListener.handle(testInput);

        verify(mockVendingMachine, times(1)).addManufacturer(testInput);
    }
}
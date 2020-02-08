package tests.ui.cli.events;

import com.vending.logic.VendingMachine;
import com.vending.models.cakes.Cake;
import com.vending.ui.cli.events.DeleteCakeSlotListener;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class DeleteCakeSlotListenerTest {

    @Test
    void handle() {
        VendingMachine mockVendingMachine = mock(VendingMachine.class);

        DeleteCakeSlotListener deleteCakeSlotListener = new DeleteCakeSlotListener(mockVendingMachine);

        int testInput = 0;
        deleteCakeSlotListener.handle(testInput);

        verify(mockVendingMachine, times(1)).removeCake(any(Cake.class));
    }
}
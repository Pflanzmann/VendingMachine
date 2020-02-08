package tests.ui.cli.events;

import com.vending.exceptions.SwapNotPossibleException;
import com.vending.logic.VendingMachine;
import com.vending.ui.cli.events.SwapCakesListener;

import org.junit.jupiter.api.Test;

import javafx.util.Pair;

import static org.mockito.Mockito.*;

class SwapCakesListenerTest {

    @Test
    void test() throws SwapNotPossibleException {
        VendingMachine mockVendingMachine = mock(VendingMachine.class);

        SwapCakesListener swapCakesListener = new SwapCakesListener(mockVendingMachine);

        int testOrigin = 0;
        int testDestination = 0;
        Pair<Integer, Integer> testInput = new Pair(testOrigin, testDestination);

        swapCakesListener.handle(testInput);

        verify(mockVendingMachine, times(1)).swapSlots(testOrigin, testDestination);
    }

}
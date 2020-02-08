package tests.simulation.runnables;

import com.vending.exceptions.ContainsCakeException;
import com.vending.exceptions.ManufacturerNotFoundException;
import com.vending.exceptions.NoSpaceException;
import com.vending.logic.VendingMachine;
import com.vending.models.cakes.Cake;
import com.vending.models.cakes.CakeBasis;

import org.junit.jupiter.api.Test;

import java.io.PrintStream;

import simulation.runnables.StoreThread_Limited;

import static org.mockito.Mockito.*;

class StoreThread_LimitedTest {

    private final String TEST_NAME = "Anakin";
    private final String TEST_FACTORY_NAME = "Palpatin";

    @Test
    void run() throws ManufacturerNotFoundException, ContainsCakeException, NoSpaceException {
        VendingMachine mockVendingMachine1 = mock(VendingMachine.class);
        PrintStream mockPrintStream = mock(PrintStream.class);
        Object testLock = new Object();

        Thread notifyThread = new Thread(() -> {
            while (true) {
                synchronized (testLock) {
                    testLock.notifyAll();
                }
            }
        });
        notifyThread.start();

        when(mockVendingMachine1.getAllCakes())
                .thenReturn(new Cake[]{null})
                .thenReturn(new Cake[]{});

        StoreThread_Limited storeThread = new StoreThread_Limited(TEST_NAME, mockPrintStream, new VendingMachine[]{mockVendingMachine1}, TEST_FACTORY_NAME, testLock);
        storeThread.run();

        verify(mockVendingMachine1, times(1)).addCakeGetIndex(any(Cake.class));
        verify(mockPrintStream, times(3)).println(anyString());
    }
}
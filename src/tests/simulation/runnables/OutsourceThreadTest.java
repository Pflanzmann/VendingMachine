package tests.simulation.runnables;

import com.vending.exceptions.ContainsCakeException;
import com.vending.exceptions.ManufacturerNotFoundException;
import com.vending.exceptions.NoSpaceException;
import com.vending.logic.VendingMachine;
import com.vending.models.cakes.Cake;
import com.vending.models.cakes.CakeBasis;

import org.junit.jupiter.api.Test;

import java.io.PrintStream;

import simulation.runnables.OutsourceThread;

import static org.mockito.Mockito.*;

class OutsourceThreadTest {

    private final String TEST_NAME = "Anakin";
    private final String TEST_FACTORY_NAME = "Palpatin";

    @Test
    void testOutsourceOnce_SecondTryFailed_ThenStop() throws ManufacturerNotFoundException, ContainsCakeException, NoSpaceException {
        VendingMachine mockVendingMachineOut = mock(VendingMachine.class);
        VendingMachine mockVendingMachineIn = mock(VendingMachine.class);
        Object testLock = new Object();
        Cake cake1 = new CakeBasis(TEST_FACTORY_NAME);
        PrintStream mockPrintStream = mock(PrintStream.class);

        Thread notifyThread = new Thread(() -> {
            while (true) {
                synchronized (mockVendingMachineOut) {
                    synchronized (testLock) {
                        mockVendingMachineOut.notifyAll();
                        testLock.notifyAll();
                    }
                }
            }
        });
        notifyThread.start();


        when(mockVendingMachineOut.getAllCakes()).thenReturn(new Cake[]{cake1});
        when(mockVendingMachineIn.getAllCakes()).thenReturn(new Cake[]{});
        when(mockVendingMachineIn.addCakeGetIndex(cake1))
                .thenReturn(0)
                .thenThrow(new NoSpaceException());

        OutsourceThread outsourceThread = new OutsourceThread(TEST_NAME, mockPrintStream, mockVendingMachineOut, mockVendingMachineIn, testLock);
        outsourceThread.run();

        verify(mockVendingMachineOut, times(2)).removeCake(cake1);
        verify(mockVendingMachineIn, times(2)).addCakeGetIndex(cake1);
        verify(mockPrintStream, times(3)).println(anyString());
    }

}
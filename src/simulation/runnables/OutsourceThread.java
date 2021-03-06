package simulation.runnables;

import com.vending.exceptions.ContainsCakeException;
import com.vending.exceptions.ManufacturerNotFoundException;
import com.vending.exceptions.NoSpaceException;
import com.vending.logic.VendingMachine;
import com.vending.models.cakes.Cake;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Comparator;

public class OutsourceThread implements Runnable {

    private final String threadName;
    private final VendingMachine outsourceVendingMachine;
    private VendingMachine storeVendingMachine;
    private PrintStream writer;
    private final Object storeLock;

    public OutsourceThread(String threadName, PrintStream writer, VendingMachine outsourceVendingMachine, VendingMachine storeVendingMachine, Object storeLock) {
        this.threadName = threadName;
        this.writer = writer;
        this.outsourceVendingMachine = outsourceVendingMachine;
        this.storeVendingMachine = storeVendingMachine;
        this.storeLock = storeLock;
    }

    @Override
    public synchronized void run() {
        while (true) {
            synchronized (outsourceVendingMachine) {
                try {
                    outsourceVendingMachine.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            synchronized (storeLock) {
                Cake[] cakes = outsourceVendingMachine.getAllCakes();
                Cake oldestCake = Arrays.stream(cakes).min(Comparator.comparing(cake -> outsourceVendingMachine.getRestShelfLifeFromCake(cake))).orElse(null);

                try {
                    outsourceVendingMachine.removeCake(oldestCake);
                    storeVendingMachine.addCakeGetIndex(oldestCake);
                    writer.println("\t\t\t" + threadName + " did outsource");
                    writer.println("\t\t\t" + threadName + " OutCount: " + outsourceVendingMachine.getAllCakes().length + " InCount: " + storeVendingMachine.getAllCakes().length);

                    storeLock.notifyAll();
                } catch (ManufacturerNotFoundException | ContainsCakeException e) {
                    e.printStackTrace();
                } catch (NoSpaceException e) {
                    writer.println("\t\t\t" + threadName + " failed both are full");
                    storeLock.notifyAll();
                    return;
                }
            }
        }
    }
}

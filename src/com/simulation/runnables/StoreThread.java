package com.simulation.runnables;

import com.vending.exceptions.ContainsCakeException;
import com.vending.exceptions.ManufacturerNotFoundException;
import com.vending.exceptions.NoSpaceException;
import com.vending.helper.ArrayHelper;
import com.vending.logic.VendingMachine;
import com.vending.models.cakes.Cake;
import com.vending.models.cakes.CakeBasis;

import java.io.PrintStream;
import java.util.Random;

public class StoreThread implements Runnable {

    private final String threadName;
    private PrintStream writer;
    private VendingMachine[] vendingMachines;
    private final String MANUFACTURER_NAME;
    private final Object storeLock;

    public StoreThread(String threadName, PrintStream writer, VendingMachine[] vendingMachines, String MANUFACTURER_NAME, Object storeLock) {
        this.threadName = threadName;
        this.writer = writer;
        this.vendingMachines = vendingMachines;
        this.MANUFACTURER_NAME = MANUFACTURER_NAME;
        this.storeLock = storeLock;
    }

    private int count = 0;

    @Override
    public synchronized void run() {
        while (true) {
            synchronized (storeLock) {
                boolean notAllFull = false;

                for (VendingMachine vendingMachine : vendingMachines) {
                    if (ArrayHelper.getArrayCount(vendingMachine.getAllCakes()) != 10) {
                        notAllFull = true;
                    }
                }

                if (!notAllFull) {
                    writer.println(threadName + " did that many stores: " + count);
                    writer.println("All vending machines are full");
                    storeLock.notifyAll();
                    return;
                }

                Cake cake = new CakeBasis(MANUFACTURER_NAME);
                int randomVendingMachine = new Random().nextInt(vendingMachines.length);

                try {
                    writer.println(threadName + " tries to add into vending machine number " + randomVendingMachine);
                    vendingMachines[randomVendingMachine].addCakeGetIndex(cake);
                    count++;

                } catch (ManufacturerNotFoundException | ContainsCakeException e) {
                    e.printStackTrace();

                } catch (NoSpaceException e) {
                    writer.println(threadName + " VendingMachine " + randomVendingMachine + " is full");
                    synchronized (vendingMachines[randomVendingMachine]) {
                        vendingMachines[randomVendingMachine].notifyAll();
                    }
                }
                try {
                    storeLock.notify();
                    storeLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

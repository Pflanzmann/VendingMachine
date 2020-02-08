package simulation;

import simulation.runnables.OutsourceThread;
import simulation.runnables.StoreThread_Limited;
import com.vending.exceptions.ManufacturerAlreadyExistsException;
import com.vending.logic.VendingMachine;
import com.vending.models.Manufacturer;
import com.vending.ui.EventHandler;

import java.util.Scanner;

public class Main_Simulation_3VendingMachines {
    public static void main(String[] args) {
        while (true) {
            String manufacturerName = "Apple";
            Manufacturer manufacturer = new Manufacturer(manufacturerName);

            VendingMachine v1 = new VendingMachine(10, new EventHandler<>(), new EventHandler<>());
            VendingMachine v2 = new VendingMachine(10, new EventHandler<>(), new EventHandler<>());
            VendingMachine v3 = new VendingMachine(10, new EventHandler<>(), new EventHandler<>());

            try {
                v1.addManufacturer(manufacturer);
                v2.addManufacturer(manufacturer);
                v3.addManufacturer(manufacturer);
            } catch (ManufacturerAlreadyExistsException e) {
                e.printStackTrace();
            }

            VendingMachine[] vendingMachines = new VendingMachine[]{v1, v2, v3};
            Object storeLock1 = new Object();

            Thread threadOutsource1 = new Thread(new OutsourceThread("OutsourceThread0", System.out, v1, v2, storeLock1));
            Thread threadOutsource2 = new Thread(new OutsourceThread("OutsourceThread1", System.out, v2, v3, storeLock1));
            Thread threadOutsource3 = new Thread(new OutsourceThread("OutsourceThread2", System.out, v3, v1, storeLock1));

            Thread threadStore1 = new Thread(new StoreThread_Limited("StoreThread0", System.out, vendingMachines, manufacturerName, storeLock1));
            Thread threadStore2 = new Thread(new StoreThread_Limited("StoreThread1", System.out, vendingMachines, manufacturerName, storeLock1));

            threadOutsource1.start();
            threadOutsource2.start();
            threadOutsource3.start();

            threadStore1.start();
            threadStore2.start();

            Scanner scan = new Scanner(System.in);
            scan.nextLine();
        }
    }
}

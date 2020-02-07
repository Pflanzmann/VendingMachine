package com.tests.logic;

import com.vending.helper.ArrayHelper;
import com.vending.logic.VendingMachine;
import com.vending.models.Manufacturer;
import com.vending.models.cakes.Cake;
import com.vending.models.cakes.CakeBasis;
import com.vending.ui.event.EventHandler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class VendingMachineEventInvokesTest {

    private final int TEST_SLOTS = 3;
    private final String TEST_FACTORY_NAME1 = "Apple";
    private final String TEST_FACTORY_NAME2 = "Corona";

    @Test
    void addCake_ShowAllCakesEvent_Success() {
        Manufacturer testManufacturer = new Manufacturer(TEST_FACTORY_NAME1);
        Cake testCake1 = new CakeBasis(TEST_FACTORY_NAME1);
        Cake testCake2 = new CakeBasis(TEST_FACTORY_NAME1);

        EventHandler<Cake[]> testEvent = new EventHandler<Cake[]>() {
            boolean firstCall = true;

            @Override
            public void invoke(Cake[] value) {
                Assertions.assertEquals(testCake1, value[0]);
                if (firstCall) {
                    Assertions.assertEquals(TEST_SLOTS, value.length);
                    Assertions.assertEquals(1, ArrayHelper.getArrayCount(value));
                    firstCall = false;
                } else {
                    Assertions.assertEquals(testCake2, value[1]);
                    Assertions.assertEquals(TEST_SLOTS, value.length);
                    Assertions.assertEquals(2, ArrayHelper.getArrayCount(value));
                }
            }
        };

        VendingMachine vendingMachine = new VendingMachine(TEST_SLOTS, testEvent, new EventHandler<>());

        Assertions.assertDoesNotThrow(() -> vendingMachine.addManufacturer(testManufacturer));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(testCake1));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(testCake2));
    }

    @Test
    void addCake_ShowAllManufacturerEvent_Success() {
        Manufacturer testManufacturer1 = new Manufacturer(TEST_FACTORY_NAME1);
        Manufacturer testManufacturer2 = new Manufacturer(TEST_FACTORY_NAME2);

        EventHandler<List<Manufacturer>> testEvent = new EventHandler<List<Manufacturer>>() {
            boolean firstCall = true;

            @Override
            public void invoke(List<Manufacturer> value) {
                Assertions.assertEquals(testManufacturer1, value.get(0));
                if (firstCall) {
                    Assertions.assertEquals(1, value.size());
                    firstCall = false;
                    return;
                }
                Assertions.assertEquals(testManufacturer2, value.get(1));
                Assertions.assertEquals(2, value.size());
            }
        };

        VendingMachine vendingMachine = new VendingMachine(TEST_SLOTS, new EventHandler<>(), testEvent);

        Assertions.assertDoesNotThrow(() -> {
            vendingMachine.addManufacturer(testManufacturer1);
            vendingMachine.addManufacturer(testManufacturer2);
        });
    }
}

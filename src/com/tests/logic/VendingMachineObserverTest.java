package com.tests.logic;

import com.vending.logic.VendingMachine;
import com.vending.models.Manufacturer;
import com.vending.models.cakes.Cake;
import com.vending.models.cakes.CakeBasis;
import com.vending.models.cakes.HazelnutCovering;
import com.vending.models.cakes.PeanutCovering;
import com.vending.ui.events.EventHandler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.atomic.AtomicInteger;

public class VendingMachineObserverTest {

    private VendingMachine vendingMachine;
    private EventHandler mockHandler1;
    private EventHandler mockHandler2;

    private final int TEST_SLOTS = 5;
    private final String TEST_FACTORY_NAME1 = "Apple";

    @BeforeEach
    void setUp() {
        mockHandler1 = Mockito.mock(EventHandler.class);
        mockHandler2 = Mockito.mock(EventHandler.class);

        vendingMachine = new VendingMachine(TEST_SLOTS, mockHandler1, mockHandler2);
    }

    @Test
    void testSlotCount__Success_AddSteady() {
        AtomicInteger expectedSlots = new AtomicInteger(TEST_SLOTS);

        vendingMachine.addObserverSlotCount(value -> {
            Assertions.assertEquals(expectedSlots.getAndDecrement(), value);
        });

        Manufacturer testManufacturer = new Manufacturer(TEST_FACTORY_NAME1);

        Assertions.assertDoesNotThrow(() -> vendingMachine.addManufacturer(testManufacturer));

        while (expectedSlots.get() != 0) {
            Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(new CakeBasis(TEST_FACTORY_NAME1)));
        }
    }

    @Test
    void testSlotCount__Success_RemoveSteady() {
        AtomicInteger expectedSlots = new AtomicInteger(0);

        Manufacturer testManufacturer = new Manufacturer(TEST_FACTORY_NAME1);
        Cake cake0 = new CakeBasis(TEST_FACTORY_NAME1);
        Cake cake1 = new CakeBasis(TEST_FACTORY_NAME1);
        Cake cake2 = new CakeBasis(TEST_FACTORY_NAME1);
        Cake cake3 = new CakeBasis(TEST_FACTORY_NAME1);
        Cake cake4 = new CakeBasis(TEST_FACTORY_NAME1);

        Assertions.assertDoesNotThrow(() -> vendingMachine.addManufacturer(testManufacturer));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(cake0));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(cake1));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(cake2));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(cake3));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(cake4));

        vendingMachine.addObserverSlotCount(value -> {
            Assertions.assertEquals(expectedSlots.getAndIncrement(), value);
        });

        vendingMachine.removeCake(cake0);
        vendingMachine.removeCake(cake1);
        vendingMachine.removeCake(cake2);
        vendingMachine.removeCake(cake3);
        vendingMachine.removeCake(cake4);
    }

    @Test
    void testContainingAllergens__Success_AddSteady() {
        AtomicInteger expectedSlots = new AtomicInteger(0);

        Manufacturer testManufacturer = new Manufacturer(TEST_FACTORY_NAME1);
        Cake cake0 = new CakeBasis(TEST_FACTORY_NAME1);
        Cake cake1 = new HazelnutCovering(cake0);
        Cake cake2 = new PeanutCovering(cake0);

        vendingMachine.addObserverAllergens(value -> {
            Assertions.assertEquals(expectedSlots.getAndIncrement(), value.size());
        });

        Assertions.assertDoesNotThrow(() -> vendingMachine.addManufacturer(testManufacturer));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(cake0));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(cake1));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(cake2));
    }

    @Test
    void testContainingAllergens__Success_RemoveSteady() {
        AtomicInteger expectedSlots = new AtomicInteger(3);

        Manufacturer testManufacturer = new Manufacturer(TEST_FACTORY_NAME1);
        Cake cake0 = new CakeBasis(TEST_FACTORY_NAME1);
        Cake cake1 = new HazelnutCovering(new CakeBasis(TEST_FACTORY_NAME1));
        Cake cake2 = new PeanutCovering(new CakeBasis(TEST_FACTORY_NAME1));


        Assertions.assertDoesNotThrow(() -> vendingMachine.addManufacturer(testManufacturer));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(cake0));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(cake1));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(cake2));

        vendingMachine.addObserverAllergens(value -> {
            Assertions.assertEquals(expectedSlots.get(), value.size());
        });

        Assertions.assertDoesNotThrow(() -> vendingMachine.removeCake(cake0));
        expectedSlots.decrementAndGet();
        Assertions.assertDoesNotThrow(() -> vendingMachine.removeCake(cake1));
        expectedSlots.set(0);
        Assertions.assertDoesNotThrow(() -> vendingMachine.removeCake(cake2));
    }
}

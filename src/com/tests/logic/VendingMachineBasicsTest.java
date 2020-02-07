package com.tests.logic;

import com.vending.exceptions.ContainsCakeException;
import com.vending.exceptions.ManufacturerAlreadyExistsException;
import com.vending.exceptions.ManufacturerNotFoundException;
import com.vending.exceptions.NoSpaceException;
import com.vending.logic.VendingMachine;
import com.vending.models.Allergen;
import com.vending.models.Manufacturer;
import com.vending.models.cakes.Cake;
import com.vending.models.cakes.CakeBasis;
import com.vending.models.cakes.PeanutCovering;
import com.vending.ui.events.EventHandler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VendingMachineBasicsTest {

    private VendingMachine vendingMachine;
    private EventHandler mockHandler1 = Mockito.mock(EventHandler.class);
    private EventHandler mockHandler2 = Mockito.mock(EventHandler.class);

    private final int TEST_SLOTS = 3;
    private final String TEST_FACTORY_NAME1 = "Apple";
    private final String TEST_FACTORY_NAME2 = "Corona";

    @BeforeEach
    void setUp() {
        vendingMachine = new VendingMachine(TEST_SLOTS, mockHandler1, mockHandler2);
    }

    @Test
    void addManufacturerType_Success() {
        Manufacturer manufacturer1 = new Manufacturer(TEST_FACTORY_NAME1);
        Manufacturer manufacturer2 = new Manufacturer(TEST_FACTORY_NAME2);

        Assertions.assertDoesNotThrow(() -> vendingMachine.addManufacturer(manufacturer1));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addManufacturer(manufacturer2));
    }

    @Test
    void addManufacturerType_AddFactoryTwice_ManufacturerAlreadyExistsException() {
        Manufacturer manufacturer1 = new Manufacturer(TEST_FACTORY_NAME1);
        Manufacturer manufacturer2 = new Manufacturer(TEST_FACTORY_NAME2);

        Assertions.assertDoesNotThrow(() -> vendingMachine.addManufacturer(manufacturer1));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addManufacturer(manufacturer2));
        Assertions.assertThrows(ManufacturerAlreadyExistsException.class, () -> vendingMachine.addManufacturer(manufacturer1));
        Assertions.assertThrows(ManufacturerAlreadyExistsException.class, () -> vendingMachine.addManufacturer(manufacturer2));
    }

    @Test
    void getManufacturerFromName() {
        Manufacturer manufacturer1 = new Manufacturer(TEST_FACTORY_NAME1);
        Manufacturer manufacturer2 = new Manufacturer(TEST_FACTORY_NAME2);

        Assertions.assertDoesNotThrow(() -> vendingMachine.addManufacturer(manufacturer1));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addManufacturer(manufacturer2));

        Assertions.assertDoesNotThrow(() -> {
            Manufacturer resultFactory = vendingMachine.getManufacturerFromName(TEST_FACTORY_NAME1);
            Assertions.assertEquals(manufacturer1, resultFactory);
        });

        Assertions.assertDoesNotThrow(() -> {
            Manufacturer resultFactory = vendingMachine.getManufacturerFromName(TEST_FACTORY_NAME2);
            Assertions.assertEquals(manufacturer2, resultFactory);
        });
    }

    @Test
    void getManufacturerFromName_ManufacturerNotFoundException() {
        Assertions.assertThrows(ManufacturerNotFoundException.class, () -> vendingMachine.getManufacturerFromName(TEST_FACTORY_NAME1));
    }

    @Test
    void addCakeGetIndex_Success() {
        Manufacturer Manufacturer = new Manufacturer(TEST_FACTORY_NAME1);

        Cake cake = new CakeBasis(TEST_FACTORY_NAME1);

        Assertions.assertDoesNotThrow(() -> vendingMachine.addManufacturer(Manufacturer));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(cake));
    }

    @Test
    void addCakeGetIndex_ManufacturerNotFoundException() {
        Cake cake = new CakeBasis(TEST_FACTORY_NAME1);

        Assertions.assertThrows(ManufacturerNotFoundException.class, () -> vendingMachine.addCakeGetIndex(cake));
    }

    @Test
    void addCakeGetIndex_ContainsCakeException() {
        Manufacturer Manufacturer = new Manufacturer(TEST_FACTORY_NAME1);

        Cake cake = new CakeBasis(TEST_FACTORY_NAME1);

        Assertions.assertDoesNotThrow(() -> vendingMachine.addManufacturer(Manufacturer));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(cake));
        Assertions.assertThrows(ContainsCakeException.class, () -> vendingMachine.addCakeGetIndex(cake));
    }

    @Test
    void addCakeGetIndex_NoSpaceException() {
        Manufacturer Manufacturer = new Manufacturer(TEST_FACTORY_NAME1);

        Cake cake1 = new CakeBasis(TEST_FACTORY_NAME1);
        Cake cake2 = new CakeBasis(TEST_FACTORY_NAME1);
        Cake cake3 = new CakeBasis(TEST_FACTORY_NAME1);
        Cake cake4 = new CakeBasis(TEST_FACTORY_NAME1);

        Assertions.assertDoesNotThrow(() -> vendingMachine.addManufacturer(Manufacturer));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(cake1));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(cake2));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(cake3));
        Assertions.assertThrows(NoSpaceException.class, () -> vendingMachine.addCakeGetIndex(cake4));
    }


    @Test
    void addCakeGetIndex_GetSlotNumber() {
        Manufacturer Manufacturer = new Manufacturer(TEST_FACTORY_NAME1);

        Cake cake1 = new CakeBasis(TEST_FACTORY_NAME1);
        Cake cake2 = new CakeBasis(TEST_FACTORY_NAME1);
        Cake cake3 = new CakeBasis(TEST_FACTORY_NAME1);

        Assertions.assertDoesNotThrow(() -> vendingMachine.addManufacturer(Manufacturer));
        Assertions.assertDoesNotThrow(() -> {
            int resultNumber = vendingMachine.addCakeGetIndex(cake1);
            Assertions.assertTrue(resultNumber <= TEST_SLOTS && resultNumber >= 0);
        });

        Assertions.assertDoesNotThrow(() -> {
            int resultNumber = vendingMachine.addCakeGetIndex(cake2);
            Assertions.assertTrue(resultNumber <= TEST_SLOTS && resultNumber >= 0);
        });

        Assertions.assertDoesNotThrow(() -> {
            int resultNumber = vendingMachine.addCakeGetIndex(cake3);
            Assertions.assertTrue(resultNumber <= TEST_SLOTS && resultNumber >= 0);
        });
    }

    @Test
    void getAllCakes() {
        Manufacturer Manufacturer = new Manufacturer(TEST_FACTORY_NAME1);

        Cake cake1 = new CakeBasis(TEST_FACTORY_NAME1);
        Cake cake2 = new CakeBasis(TEST_FACTORY_NAME1);
        Cake cake3 = new CakeBasis(TEST_FACTORY_NAME1);

        Assertions.assertDoesNotThrow(() -> vendingMachine.addManufacturer(Manufacturer));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(cake1));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(cake2));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(cake3));

        Assertions.assertDoesNotThrow(() -> {
            Cake[] cakes = vendingMachine.getAllCakes();
            Assertions.assertEquals(cake1, cakes[0]);
            Assertions.assertEquals(cake2, cakes[1]);
            Assertions.assertEquals(cake3, cakes[2]);
        });
    }

    @Test
    void getCakeCountFromManufacturerType() {
        Long expectedCountFactory1 = 2L;
        Long expectedCountFactory2 = 1L;

        Manufacturer manufacturer1 = new Manufacturer(TEST_FACTORY_NAME1);
        Manufacturer manufacturer2 = new Manufacturer(TEST_FACTORY_NAME2);

        Cake cake1 = new CakeBasis(TEST_FACTORY_NAME1);
        Cake cake2 = new CakeBasis(TEST_FACTORY_NAME1);
        Cake cake3 = new CakeBasis(TEST_FACTORY_NAME2);

        Assertions.assertDoesNotThrow(() -> vendingMachine.addManufacturer(manufacturer1));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addManufacturer(manufacturer2));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(cake1));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(cake2));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(cake3));

        Assertions.assertEquals(expectedCountFactory1, vendingMachine.getCakeCountFromManufacturerName(TEST_FACTORY_NAME1));
        Assertions.assertEquals(expectedCountFactory2, vendingMachine.getCakeCountFromManufacturerName(TEST_FACTORY_NAME2));
    }

    @Test
    void getCakeFromSlot_Success() {
        Manufacturer Manufacturer = new Manufacturer(TEST_FACTORY_NAME1);

        Cake cake1 = new CakeBasis(TEST_FACTORY_NAME1);
        Cake cake2 = new CakeBasis(TEST_FACTORY_NAME1);
        Cake cake3 = new CakeBasis(TEST_FACTORY_NAME1);

        Assertions.assertDoesNotThrow(() -> vendingMachine.addManufacturer(Manufacturer));
        Assertions.assertDoesNotThrow(() -> {
            int resultNumber = vendingMachine.addCakeGetIndex(cake1);
            Cake resultCake = vendingMachine.getCakeFromSlot(resultNumber);
            Assertions.assertEquals(cake1, resultCake);
        });

        Assertions.assertDoesNotThrow(() -> {
            int resultNumber = vendingMachine.addCakeGetIndex(cake2);
            Cake resultCake = vendingMachine.getCakeFromSlot(resultNumber);
            Assertions.assertEquals(cake2, resultCake);
        });

        Assertions.assertDoesNotThrow(() -> {
            int resultNumber = vendingMachine.addCakeGetIndex(cake3);
            Cake resultCake = vendingMachine.getCakeFromSlot(resultNumber);
            Assertions.assertEquals(cake3, resultCake);
        });
    }

    @Test
    void getSlotFromCake_Success() {
        Manufacturer Manufacturer = new Manufacturer(TEST_FACTORY_NAME1);

        Cake cake1 = new CakeBasis(TEST_FACTORY_NAME1);
        Cake cake2 = new CakeBasis(TEST_FACTORY_NAME1);
        Cake cake3 = new CakeBasis(TEST_FACTORY_NAME1);

        Assertions.assertDoesNotThrow(() -> vendingMachine.addManufacturer(Manufacturer));
        Assertions.assertDoesNotThrow(() -> {
            int testNumber = vendingMachine.addCakeGetIndex(cake1);
            int resultSlot = vendingMachine.getSlotFromCake(cake1);
            Assertions.assertEquals(testNumber, resultSlot);
        });

        Assertions.assertDoesNotThrow(() -> {
            int testNumber = vendingMachine.addCakeGetIndex(cake2);
            int resultSlot = vendingMachine.getSlotFromCake(cake2);
            Assertions.assertEquals(testNumber, resultSlot);
        });

        Assertions.assertDoesNotThrow(() -> {
            int testNumber = vendingMachine.addCakeGetIndex(cake3);
            int resultSlot = vendingMachine.getSlotFromCake(cake3);
            Assertions.assertEquals(testNumber, resultSlot);
        });
    }

    @Test
    void getCakeFromSlot_null() {
        Assertions.assertDoesNotThrow(() -> {
            Cake resultCake = vendingMachine.getCakeFromSlot(0);
            Assertions.assertNull(resultCake);
        });
    }

    @Test
    void getRestShelfLifeFromCake_Success() {
        long TEST_DURATION = 1;
        long TEST_WAIT = 1000;

        Manufacturer Manufacturer = new Manufacturer(TEST_FACTORY_NAME1);

        Cake cake = new CakeBasis(TEST_FACTORY_NAME1);

        Assertions.assertDoesNotThrow(() -> vendingMachine.addManufacturer(Manufacturer));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(cake));
        Assertions.assertDoesNotThrow(() -> Thread.sleep(TEST_WAIT));

        long expected = cake.getShelfLife().getSeconds() - TEST_DURATION;
        assertEquals(expected, vendingMachine.getRestShelfLifeFromCake(cake).getSeconds());
    }

    @Test
    void removeCake_Success() {
        Manufacturer Manufacturer = new Manufacturer(TEST_FACTORY_NAME1);

        Cake cake1 = new CakeBasis(TEST_FACTORY_NAME1);
        Cake cake2 = new CakeBasis(TEST_FACTORY_NAME1);
        Cake cake3 = new CakeBasis(TEST_FACTORY_NAME1);

        Assertions.assertDoesNotThrow(() -> vendingMachine.addManufacturer(Manufacturer));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(cake1));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(cake2));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(cake3));

        Assertions.assertDoesNotThrow(() -> vendingMachine.removeCake(cake1));
        Assertions.assertDoesNotThrow(() -> vendingMachine.removeCake(cake2));
        Assertions.assertDoesNotThrow(() -> vendingMachine.removeCake(cake3));
    }

    @Test
    void removeCake_NoSuchCake_NothingHappens() {
        Cake cake1 = new CakeBasis(TEST_FACTORY_NAME1);
        Cake cake2 = new CakeBasis(TEST_FACTORY_NAME1);
        Cake cake3 = new CakeBasis(TEST_FACTORY_NAME1);

        Assertions.assertDoesNotThrow(() -> vendingMachine.removeCake(cake1));
        Assertions.assertDoesNotThrow(() -> vendingMachine.removeCake(cake2));
        Assertions.assertDoesNotThrow(() -> vendingMachine.removeCake(cake3));
    }

    @Test
    void getAllAllergens() {
        Manufacturer Manufacturer = new Manufacturer(TEST_FACTORY_NAME1);

        Cake cake1 = new CakeBasis(TEST_FACTORY_NAME1);
        Cake cake2 = new CakeBasis(TEST_FACTORY_NAME1);
        Cake cake3 = new PeanutCovering(new CakeBasis(TEST_FACTORY_NAME1));

        Assertions.assertDoesNotThrow(() -> vendingMachine.addManufacturer(Manufacturer));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(cake1));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(cake2));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(cake3));

        EnumSet<Allergen> resultAllergens = vendingMachine.getAllAllergens();
        Assertions.assertTrue(resultAllergens.contains(Allergen.Gluten));
        Assertions.assertTrue(resultAllergens.contains(Allergen.Peanut));
        Assertions.assertFalse(resultAllergens.contains(Allergen.Hazlenut));
        Assertions.assertFalse(resultAllergens.contains(Allergen.Sesameseeds));
    }

    @Test
    void getMissingAllergens() {
        Manufacturer Manufacturer = new Manufacturer(TEST_FACTORY_NAME1);

        Cake cake1 = new CakeBasis(TEST_FACTORY_NAME1);
        Cake cake2 = new CakeBasis(TEST_FACTORY_NAME1);
        Cake cake3 = new PeanutCovering(new CakeBasis(TEST_FACTORY_NAME1));

        Assertions.assertDoesNotThrow(() -> vendingMachine.addManufacturer(Manufacturer));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(cake1));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(cake2));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(cake3));

        EnumSet<Allergen> resultAllergens = vendingMachine.getMissingAllergens();
        Assertions.assertFalse(resultAllergens.contains(Allergen.Gluten));
        Assertions.assertFalse(resultAllergens.contains(Allergen.Peanut));
        Assertions.assertTrue(resultAllergens.contains(Allergen.Hazlenut));
        Assertions.assertTrue(resultAllergens.contains(Allergen.Sesameseeds));
    }

    @Test
    void searchFor_FilterByNutritionalValue() {
        Manufacturer Manufacturer = new Manufacturer(TEST_FACTORY_NAME1);

        Cake cake1 = new CakeBasis(TEST_FACTORY_NAME1);
        Cake cake2 = new CakeBasis(TEST_FACTORY_NAME1);
        Cake cake3 = new CakeBasis(TEST_FACTORY_NAME1);

        Assertions.assertDoesNotThrow(() -> vendingMachine.addManufacturer(Manufacturer));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(cake1));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(cake2));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(cake3));

        List<Cake> resultCakes = vendingMachine.searchForAll(x -> x.getNutritionalValue() > 30);

        Assertions.assertTrue(resultCakes.contains(cake2));
        Assertions.assertTrue(resultCakes.contains(cake3));
    }

    @Test
    void searchFor_FilterPrice() {
        Manufacturer Manufacturer = new Manufacturer(TEST_FACTORY_NAME1);

        Cake cake1 = new CakeBasis(TEST_FACTORY_NAME1);
        Cake cake2 = new CakeBasis(TEST_FACTORY_NAME1);
        Cake cake3 = new CakeBasis(TEST_FACTORY_NAME1);

        Assertions.assertDoesNotThrow(() -> vendingMachine.addManufacturer(Manufacturer));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(cake1));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(cake2));
        Assertions.assertDoesNotThrow(() -> vendingMachine.addCakeGetIndex(cake3));

        List<Cake> resultCakes = vendingMachine.searchForAll(x -> x.getPrice().floatValue() > 10);

        Assertions.assertTrue(resultCakes.contains(cake2));
        Assertions.assertTrue(resultCakes.contains(cake3));
    }
}
package tests.logic;

import com.vending.helper.ArrayHelper;
import com.vending.logic.VendingMachine;
import com.vending.models.Allergen;
import com.vending.models.Manufacturer;
import com.vending.models.cakes.Cake;
import com.vending.models.cakes.CakeBasis;
import com.vending.models.cakes.HazelnutCovering;
import com.vending.ui.EventHandler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class VendingMachineSerialisationTest {

    private VendingMachine vendingMachine;
    private EventHandler mockHandler1;
    private EventHandler mockHandler2;

    private final int TEST_SLOTS = 3;
    private final String TEST_FACTORY_NAME1 = "Apple";
    private final String TEST_FACTORY_NAME2 = "Corona";

    @BeforeEach
    void setUp() {
        mockHandler1 = Mockito.mock(EventHandler.class);
        mockHandler2 = Mockito.mock(EventHandler.class);

        vendingMachine = new VendingMachine(TEST_SLOTS, mockHandler1, mockHandler2);
    }

    @Test
    void test_Serialize_Success() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream testOutputStream = new ByteArrayOutputStream();

        Manufacturer testManufacturer = new Manufacturer(TEST_FACTORY_NAME1);
        Cake testCake1 = new CakeBasis(TEST_FACTORY_NAME1);
        Cake testCake2 = new CakeBasis(TEST_FACTORY_NAME1);

        Assertions.assertDoesNotThrow(() -> {
            vendingMachine.addManufacturer(testManufacturer);
            vendingMachine.addCakeGetIndex(testCake1);
            vendingMachine.addCakeGetIndex(testCake2);
        });

        Assertions.assertDoesNotThrow(() -> {
            vendingMachine.serialize(testOutputStream);
        });

        ByteArrayInputStream byi = new ByteArrayInputStream(testOutputStream.toByteArray());
        ObjectInputStream testObjectInputStream = new ObjectInputStream(byi);

        Object[] resultObject = (Object[]) testObjectInputStream.readObject();
        Cake[] resultCakes = (Cake[]) resultObject[1];
        Map<String, Manufacturer> resultManufacturers = (HashMap<String, Manufacturer>) resultObject[0];
        Map<Cake, Date> resultDates = (Map<Cake, Date>) resultObject[2];

        Assertions.assertEquals(2, ArrayHelper.getArrayCount(resultCakes));
        Assertions.assertEquals(1, resultManufacturers.size());
        Assertions.assertEquals(2, resultDates.size());
    }

    @Test
    void test_Deserialize_Success() throws IOException {
        Manufacturer testManufacturer1 = new Manufacturer(TEST_FACTORY_NAME1);
        Manufacturer testManufacturer2 = new Manufacturer(TEST_FACTORY_NAME2);
        Cake testCake1 = new HazelnutCovering(new CakeBasis(TEST_FACTORY_NAME1));
        Cake testCake2 = new CakeBasis(TEST_FACTORY_NAME1);
        Cake testCake3 = new CakeBasis(TEST_FACTORY_NAME2);


        Map<String, Manufacturer> manufacturerList = new HashMap<>();
        Cake[] cakeSlots = new Cake[TEST_SLOTS];
        Map<Cake, Date> cakeDate = new HashMap<>();

        Assertions.assertDoesNotThrow(() -> {
            manufacturerList.put(testManufacturer1.getName(), testManufacturer1);
            manufacturerList.put(testManufacturer2.getName(), testManufacturer2);
            cakeSlots[0] = (testCake1);
            cakeSlots[1] = (testCake2);
            cakeSlots[2] = (testCake3);
            cakeDate.put(testCake1, new Date());
            cakeDate.put(testCake2, new Date());
            cakeDate.put(testCake3, new Date());
        });

        Object[] allLists = new Object[]{manufacturerList, cakeSlots, cakeDate};

        ByteArrayOutputStream byo = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(byo);
        oos.writeObject(allLists);

        byte[] something = byo.toByteArray();
        ByteArrayInputStream byi = new ByteArrayInputStream(something);

        Assertions.assertDoesNotThrow(() -> {
            vendingMachine.deserialize(byi);
        });

        Assertions.assertDoesNotThrow(() -> {
            Assertions.assertEquals(2, vendingMachine.getCakeCountFromManufacturerName(TEST_FACTORY_NAME1));
            Assertions.assertEquals(1, vendingMachine.getCakeCountFromManufacturerName(TEST_FACTORY_NAME2));
            Assertions.assertTrue(vendingMachine.getAllAllergens().contains(Allergen.Gluten));
            Assertions.assertTrue(vendingMachine.getAllAllergens().contains(Allergen.Hazlenut));
            Assertions.assertFalse(vendingMachine.getAllAllergens().contains(Allergen.Peanut));
            Assertions.assertFalse(vendingMachine.getAllAllergens().contains(Allergen.Sesameseeds));
        });
    }
}

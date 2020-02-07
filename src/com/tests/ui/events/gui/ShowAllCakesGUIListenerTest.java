package com.tests.ui.events.gui;

import com.vending.models.cakes.Cake;
import com.vending.models.cakes.CakeBasis;
import com.vending.models.cakes.HazelnutCovering;
import com.vending.models.cakes.PeanutCovering;
import com.vending.ui.events.gui.ShowAllCakesGUIListener;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.mockito.Mockito.mock;

class ShowAllCakesGUIListenerTest {

    private String TEST_MANUFACTURER_NAME = "VonKinderFürKinder-Manufacturer";

    @Test
    void handle() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);

        ShowAllCakesGUIListener showAllCakesGUIListener = new ShowAllCakesGUIListener(oos);

        Cake mockCake1 = new CakeBasis(TEST_MANUFACTURER_NAME);
        Cake mockCake2 = new HazelnutCovering(new CakeBasis(TEST_MANUFACTURER_NAME));
        Cake mockCake3 = new PeanutCovering(new CakeBasis(TEST_MANUFACTURER_NAME));
        Cake[] testCakeArray = new Cake[]{mockCake1, mockCake2, mockCake3};

        showAllCakesGUIListener.handle(testCakeArray);

        ByteArrayInputStream bis = new ByteArrayInputStream(os.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);

        Cake[] resultCakes = (Cake[]) ois.readObject();
        Assertions.assertEquals(3, resultCakes.length);
        Assertions.assertNotNull(resultCakes[0]);
        Assertions.assertNotNull(resultCakes[1]);
        Assertions.assertNotNull(resultCakes[2]);
    }
}
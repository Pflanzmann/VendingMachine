package tests.ui.cli.events;

import com.vending.models.Manufacturer;
import com.vending.ui.cli.events.ShowAllManufacturersStreamListener;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class ShowAllManufacturersStreamListenerTest {

    private String TEST_MANUFACTURER_NAME1 = "toss me but don't tell the elf";
    private String TEST_MANUFACTURER_NAME2 = "Sorry I annoyed you with my friendship";
    private String TEST_MANUFACTURER_NAME3 = "I am sorry little one";

    @Test
    void handle() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);

        ShowAllManufacturersStreamListener showAllManufacturersStreamListener = new ShowAllManufacturersStreamListener(oos);

        Manufacturer mockManufacturer1 = new Manufacturer(TEST_MANUFACTURER_NAME1);
        Manufacturer mockManufacturer2 = new Manufacturer(TEST_MANUFACTURER_NAME2);
        Manufacturer mockManufacturer3 = new Manufacturer(TEST_MANUFACTURER_NAME3);

        List<Manufacturer> testManufacturerList = new ArrayList<>();
        testManufacturerList.add(mockManufacturer1);
        testManufacturerList.add(mockManufacturer2);
        testManufacturerList.add(mockManufacturer3);

        showAllManufacturersStreamListener.handle(testManufacturerList);

        ByteArrayInputStream bis = new ByteArrayInputStream(os.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);

        ArrayList<Manufacturer> resultManufacturers = (ArrayList<Manufacturer>) ois.readObject();
        Assertions.assertEquals(3, resultManufacturers.size());
        Assertions.assertEquals(TEST_MANUFACTURER_NAME1, resultManufacturers.get(0).getName());
        Assertions.assertEquals(TEST_MANUFACTURER_NAME2, resultManufacturers.get(1).getName());
        Assertions.assertEquals(TEST_MANUFACTURER_NAME3, resultManufacturers.get(2).getName());
    }

}
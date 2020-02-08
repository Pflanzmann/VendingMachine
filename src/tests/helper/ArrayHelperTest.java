package tests.helper;

import com.vending.helper.ArrayHelper;
import com.vending.models.cakes.Cake;
import com.vending.models.cakes.CakeBasis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ArrayHelperTest {

    @Test
    void getArrayCount() {

        Object[] testArrayObject = new Object[10];
        Cake[] testArrayCake = new Cake[10];
        Integer[] testArrayInt = new Integer[10];

        testArrayObject[0] = new Object();
        testArrayObject[1] = new Object();
        testArrayObject[2] = new Object();
        testArrayObject[3] = new Object();
        testArrayObject[4] = new Object();
        testArrayObject[5] = new Object();
        testArrayObject[6] = new Object();
        testArrayObject[7] = new Object();
        testArrayObject[8] = new Object();
        testArrayObject[9] = new Object();

        testArrayCake[1] = new CakeBasis("name");
        testArrayCake[9] = new CakeBasis("name");
        testArrayCake[4] = new CakeBasis("name");

        Assertions.assertEquals(10, ArrayHelper.getArrayCount(testArrayObject));
        Assertions.assertEquals(3, ArrayHelper.getArrayCount(testArrayCake));
        Assertions.assertEquals(0, ArrayHelper.getArrayCount(testArrayInt));
    }
}
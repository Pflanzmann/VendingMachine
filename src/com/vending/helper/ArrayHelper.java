package com.vending.helper;

public class ArrayHelper {

    public static <T> int getArrayCount(T[] array) {
        int counter = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null)
                counter++;
        }

        return counter;
    }
}

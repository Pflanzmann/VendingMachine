package com.tests.ui.events.cli;

import com.vending.models.Manufacturer;
import com.vending.ui.CLI;
import com.vending.ui.events.cli.ShowAllManufacturersCLIListener;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class ShowAllManufacturersCLIListenerTest {

    @Test
    void handle() {
        String testManufacturer1Name = "hello there";
        String testManufacturer2Name = "you were supposed to destroy them not join them";

        CLI mockCli = mock(CLI.class);
        ShowAllManufacturersCLIListener showAllManufacturersCLIListener = new ShowAllManufacturersCLIListener(mockCli);

        List<Manufacturer> testManufacturerList = new ArrayList<>();
        Manufacturer mockManufacturer1 = mock(Manufacturer.class);
        Manufacturer mockManufacturer2 = mock(Manufacturer.class);

        when(mockManufacturer1.getName()).thenReturn(testManufacturer1Name);
        when(mockManufacturer2.getName()).thenReturn(testManufacturer2Name);

        testManufacturerList.add(mockManufacturer1);
        testManufacturerList.add(mockManufacturer2);

        showAllManufacturersCLIListener.handle(testManufacturerList);
        ArgumentCaptor<String> outputCaptor = ArgumentCaptor.forClass(String.class);

        verify(mockCli, times(1)).setLatestManufacturersString(outputCaptor.capture());
        String outputString = outputCaptor.getValue();

        Assertions.assertTrue(outputString.contains(testManufacturer1Name));
        Assertions.assertTrue(outputString.contains(testManufacturer2Name));
    }
}
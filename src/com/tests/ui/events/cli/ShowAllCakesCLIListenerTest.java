package com.tests.ui.events.cli;

import com.vending.models.cakes.Cake;
import com.vending.ui.CLI;
import com.vending.ui.events.cli.ShowAllCakesCLIListener;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;

class ShowAllCakesCLIListenerTest {

    @Test
    void handle() {
        String testCake1Text = "All your base are belong to us";
        String testCake2Text = "Give up anakin, i have the high ground";
        String testCake3Text = "Our programming 3 prof is the best";

        CLI mockCli = mock(CLI.class);
        ShowAllCakesCLIListener showAllCakesCLIListener = new ShowAllCakesCLIListener(mockCli);

        Cake mockCake1 = mock(Cake.class);
        Cake mockCake2 = mock(Cake.class);
        Cake mockCake3 = mock(Cake.class);
        Cake[] testCakeArray = new Cake[]{mockCake1, mockCake2, mockCake3};

        when(mockCake1.getCakeType()).thenReturn(testCake1Text);
        when(mockCake2.getCakeType()).thenReturn(testCake2Text);
        when(mockCake3.getCakeType()).thenReturn(testCake3Text);

        showAllCakesCLIListener.handle(testCakeArray);

        ArgumentCaptor<String> outputCaptor = ArgumentCaptor.forClass(String.class);

        verify(mockCli, times(1)).setLatestCakesString(outputCaptor.capture());
        verify(mockCake1, times(1)).getCakeType();
        verify(mockCake2, times(1)).getCakeType();
        verify(mockCake3, times(1)).getCakeType();

        String outputString = outputCaptor.getValue();

        Assertions.assertTrue(outputString.contains(testCake1Text));
        Assertions.assertTrue(outputString.contains(testCake2Text));
        Assertions.assertTrue(outputString.contains(testCake3Text));
    }

    @Test
    void handle_d() {
        CLI mockCli = mock(CLI.class);

        ShowAllCakesCLIListener showAllCakesCLIListener = new ShowAllCakesCLIListener(mockCli);

        Cake[] testCakeArray = new Cake[]{null};

        showAllCakesCLIListener.handle(testCakeArray);

        ArgumentCaptor<String> outputCaptor = ArgumentCaptor.forClass(String.class);

        verify(mockCli, times(1)).setLatestCakesString(outputCaptor.capture());
        String outputString = outputCaptor.getValue();

        Assertions.assertTrue(outputString.contains("empty"));
    }
}
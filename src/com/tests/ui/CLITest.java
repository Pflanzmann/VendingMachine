package com.tests.ui;

import com.vending.models.Allergen;
import com.vending.models.CoveringType;
import com.vending.models.Manufacturer;
import com.vending.models.SerializableAction;
import com.vending.models.cakes.Cake;
import com.vending.ui.CLI;
import com.vending.ui.events.EventHandler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.NoSuchElementException;

import javafx.util.Pair;

import static org.mockito.Mockito.*;

class CLITest {

    private CLI cli;

    private PrintStream testWriter;

    private InputStream mockInputStream;
    private PrintStream mockPrintStream;
    private EventHandler mockAddCakeEventHandler;
    private EventHandler mockAddManufacturerEventHandler;
    private EventHandler mockDeleteCakeEventHandler;
    private EventHandler mockSerializeActionEventHandler;
    private EventHandler mockSwapSlotsEventHandler;

    private String TEST_MANUFACTURER_NAME = "Brexit";

    @BeforeEach
    void setUp() throws IOException {
        PipedOutputStream os = new PipedOutputStream();
        testWriter = new PrintStream(os);

        mockInputStream = new PipedInputStream(os);
        mockPrintStream = mock(PrintStream.class);
        mockAddCakeEventHandler = mock(EventHandler.class);
        mockAddManufacturerEventHandler = mock(EventHandler.class);
        mockDeleteCakeEventHandler = mock(EventHandler.class);
        mockSerializeActionEventHandler = mock(EventHandler.class);
        mockSwapSlotsEventHandler = mock(EventHandler.class);

        cli = new CLI(
                mockInputStream,
                mockPrintStream,
                mockAddCakeEventHandler,
                mockAddManufacturerEventHandler,
                mockDeleteCakeEventHandler,
                mockSerializeActionEventHandler,
                mockSwapSlotsEventHandler
        );
    }

    @Test
    void cancleCli() {
        testWriter.println("x");
        testWriter.close();

        cli.start();

        verifyNoMoreInteractions(mockAddManufacturerEventHandler);
        verifyNoMoreInteractions(mockAddCakeEventHandler);
        verifyNoMoreInteractions(mockDeleteCakeEventHandler);
        verifyNoMoreInteractions(mockSerializeActionEventHandler);
        verifyNoMoreInteractions(mockSwapSlotsEventHandler);
    }

    @Test
    void mainMenu_NotValidInput() {
        testWriter.println("dasds");
        testWriter.println("x");
        testWriter.close();

        cli.start();

        verifyNoMoreInteractions(mockAddManufacturerEventHandler);
        verifyNoMoreInteractions(mockAddCakeEventHandler);
        verifyNoMoreInteractions(mockDeleteCakeEventHandler);
        verifyNoMoreInteractions(mockSerializeActionEventHandler);
        verifyNoMoreInteractions(mockSwapSlotsEventHandler);
    }

    @Test
    void addMode_addManufacturer() {
        ArgumentCaptor<Manufacturer> cakeCaptor = ArgumentCaptor.forClass(Manufacturer.class);

        testWriter.println("a");
        testWriter.println("m");
        testWriter.println(TEST_MANUFACTURER_NAME);
        testWriter.println("x");
        testWriter.close();

        cli.start();

        verify(mockAddManufacturerEventHandler, times(1)).invoke(cakeCaptor.capture());

        Manufacturer calledManufacturer = cakeCaptor.getValue();

        Assertions.assertEquals(TEST_MANUFACTURER_NAME, calledManufacturer.getName());
    }

    @Test
    void addMode_addManufacturer_Cancled() {
        testWriter.println("a");
        testWriter.println("m");
        testWriter.println("x");
        testWriter.println("x");
        testWriter.close();

        cli.start();

        verifyNoMoreInteractions(mockAddManufacturerEventHandler);
    }

    @Test
    void addMode_addCake_BasicCake_Only() {
        ArgumentCaptor<Cake> cakeCaptor = ArgumentCaptor.forClass(Cake.class);

        testWriter.println("a");
        testWriter.println("c");
        testWriter.println(TEST_MANUFACTURER_NAME);
        testWriter.println("f");
        testWriter.println("x");
        testWriter.close();

        cli.start();

        verify(mockAddCakeEventHandler, times(1)).invoke(cakeCaptor.capture());

        Cake calledCake = cakeCaptor.getValue();
        ArrayList<CoveringType> resultCoverings = calledCake.getCoverings();

        Assertions.assertEquals(1, resultCoverings.size());
        Assertions.assertEquals(CoveringType.BASE, resultCoverings.get(0));
    }

    @Test
    void addMode_addCake_BasicCake_AddedMultiplePeanut_Only() {
        ArgumentCaptor<Cake> cakeCaptor = ArgumentCaptor.forClass(Cake.class);

        testWriter.println("a");
        testWriter.println("c");
        testWriter.println(TEST_MANUFACTURER_NAME);
        testWriter.println("p");
        testWriter.println("p");
        testWriter.println("p");
        testWriter.println("f");
        testWriter.println("x");
        testWriter.close();

        cli.start();

        verify(mockAddCakeEventHandler, times(1)).invoke(cakeCaptor.capture());

        Cake calledCake = cakeCaptor.getValue();
        ArrayList<CoveringType> resultCoverings = calledCake.getCoverings();

        Assertions.assertEquals(4, resultCoverings.size());
        Assertions.assertEquals(CoveringType.BASE, resultCoverings.get(0));
        Assertions.assertEquals(CoveringType.PEANUT, resultCoverings.get(1));
        Assertions.assertEquals(CoveringType.PEANUT, resultCoverings.get(2));
        Assertions.assertEquals(CoveringType.PEANUT, resultCoverings.get(3));
    }

    @Test
    void addMode_addCake_BasicCake_AddedMultipleHazelnut_Only() {
        ArgumentCaptor<Cake> cakeCaptor = ArgumentCaptor.forClass(Cake.class);

        testWriter.println("a");
        testWriter.println("c");
        testWriter.println(TEST_MANUFACTURER_NAME);
        testWriter.println("h");
        testWriter.println("h");
        testWriter.println("h");
        testWriter.println("f");
        testWriter.println("x");
        testWriter.close();

        cli.start();

        verify(mockAddCakeEventHandler, times(1)).invoke(cakeCaptor.capture());

        Cake calledCake = cakeCaptor.getValue();
        ArrayList<CoveringType> resultCoverings = calledCake.getCoverings();

        Assertions.assertEquals(4, resultCoverings.size());
        Assertions.assertEquals(CoveringType.BASE, resultCoverings.get(0));
        Assertions.assertEquals(CoveringType.HAZELNUT, resultCoverings.get(1));
        Assertions.assertEquals(CoveringType.HAZELNUT, resultCoverings.get(2));
        Assertions.assertEquals(CoveringType.HAZELNUT, resultCoverings.get(3));
    }

    @Test
    void addMode_addCake_BasicCake_AddedMixedCoverings_Only() {
        ArgumentCaptor<Cake> cakeCaptor = ArgumentCaptor.forClass(Cake.class);

        testWriter.println("a");
        testWriter.println("c");
        testWriter.println(TEST_MANUFACTURER_NAME);
        testWriter.println("h");
        testWriter.println("p");
        testWriter.println("p");
        testWriter.println("h");
        testWriter.println("h");
        testWriter.println("p");
        testWriter.println("f");
        testWriter.println("x");
        testWriter.close();

        cli.start();

        verify(mockAddCakeEventHandler, times(1)).invoke(cakeCaptor.capture());

        Cake calledCake = cakeCaptor.getValue();
        ArrayList<CoveringType> resultCoverings = calledCake.getCoverings();

        Assertions.assertEquals(7, resultCoverings.size());
        Assertions.assertEquals(CoveringType.BASE, resultCoverings.get(0));
        Assertions.assertEquals(CoveringType.HAZELNUT, resultCoverings.get(1));
        Assertions.assertEquals(CoveringType.PEANUT, resultCoverings.get(2));
        Assertions.assertEquals(CoveringType.PEANUT, resultCoverings.get(3));
        Assertions.assertEquals(CoveringType.HAZELNUT, resultCoverings.get(4));
        Assertions.assertEquals(CoveringType.HAZELNUT, resultCoverings.get(5));
        Assertions.assertEquals(CoveringType.PEANUT, resultCoverings.get(6));
    }

    @Test
    void addMode_addCake_Cancel_NoEventCall() {
        testWriter.println("a");
        testWriter.println("c");
        testWriter.println(TEST_MANUFACTURER_NAME);
        testWriter.println("x");
        testWriter.println("x");
        testWriter.close();

        cli.start();

        verifyNoMoreInteractions(mockAddCakeEventHandler);
    }

    @Test
    void deleteMode_InvalidInput() {
        testWriter.println("d");
        testWriter.println("ddas");
        testWriter.println("x");
        testWriter.close();

        cli.start();

        verifyNoMoreInteractions(mockAddManufacturerEventHandler);
        verifyNoMoreInteractions(mockAddCakeEventHandler);
        verifyNoMoreInteractions(mockDeleteCakeEventHandler);
        verifyNoMoreInteractions(mockSerializeActionEventHandler);
        verifyNoMoreInteractions(mockSwapSlotsEventHandler);
    }

    @Test
    void deleteMode_Cancel() {
        testWriter.println("d");
        testWriter.println("x");
        testWriter.println("x");
        testWriter.close();

        cli.start();

        verifyNoMoreInteractions(mockAddManufacturerEventHandler);
        verifyNoMoreInteractions(mockAddCakeEventHandler);
        verifyNoMoreInteractions(mockDeleteCakeEventHandler);
        verifyNoMoreInteractions(mockSerializeActionEventHandler);
        verifyNoMoreInteractions(mockSwapSlotsEventHandler);
    }

    @Test
    void deleteMode_Success_1() {
        testWriter.println("d");
        testWriter.println("2");
        testWriter.println("x");
        testWriter.close();

        cli.start();

        verify(mockDeleteCakeEventHandler, times(1)).invoke(2);
    }

    @Test
    void deleteMode_Success_2() {
        testWriter.println("d");
        testWriter.println("-2");
        testWriter.println("x");
        testWriter.close();

        cli.start();

        verify(mockDeleteCakeEventHandler, times(1)).invoke(-2);
    }

    @Test
    void deleteMode_Success_1337() {
        testWriter.println("d");
        testWriter.println("1337");
        testWriter.println("x");
        testWriter.close();

        cli.start();

        verify(mockDeleteCakeEventHandler, times(1)).invoke(1337);
    }

    @Test
    void storageMode_Cancel() {
        testWriter.println("s");
        testWriter.println("x");
        testWriter.println("x");
        testWriter.close();

        cli.start();

        verifyNoMoreInteractions(mockAddManufacturerEventHandler);
        verifyNoMoreInteractions(mockAddCakeEventHandler);
        verifyNoMoreInteractions(mockDeleteCakeEventHandler);
        verifyNoMoreInteractions(mockSerializeActionEventHandler);
        verifyNoMoreInteractions(mockSwapSlotsEventHandler);
    }

    @Test
    void storageMode_Load_Success() {
        testWriter.println("ls");
        testWriter.println("l");
        testWriter.println("x");
        testWriter.close();

        cli.start();

        verify(mockSerializeActionEventHandler, times(1)).invoke(SerializableAction.LOAD);
    }

    @Test
    void storageMode_Store_Success() {
        testWriter.println("ls");
        testWriter.println("s");
        testWriter.println("x");
        testWriter.close();

        cli.start();

        verify(mockSerializeActionEventHandler, times(1)).invoke(SerializableAction.STORE);
    }

    @Test
    void storageMode_SwapSlots_Success() {
        final int TEST_INPUT_1 = 1;
        final int TEST_INPUT_2 = 31;
        ArgumentCaptor<Pair> pairCaptor = ArgumentCaptor.forClass(Pair.class);

        testWriter.println("ls");
        testWriter.println("sw");
        testWriter.println(TEST_INPUT_1);
        testWriter.println(TEST_INPUT_2);
        testWriter.println("x");
        testWriter.println("x");
        testWriter.close();

        cli.start();

        verify(mockSwapSlotsEventHandler, times(1)).invoke(pairCaptor.capture());

        Pair<Integer, Integer> calledPair = pairCaptor.getValue();

        Assertions.assertEquals(TEST_INPUT_1, calledPair.getKey());
        Assertions.assertEquals(TEST_INPUT_2, calledPair.getValue());
    }

    @Test
    void storageMode_SwapSlots_Input1_Wrong() {
        final String TEST_INPUT_1 = "dasdfsa";
        final int TEST_INPUT_2 = 31;

        testWriter.println("ls");
        testWriter.println("sw");
        testWriter.println(TEST_INPUT_1);
        testWriter.println(TEST_INPUT_2);
        testWriter.println("x");
        testWriter.println("x");
        testWriter.close();

        cli.start();

        verifyNoMoreInteractions(mockSwapSlotsEventHandler);
    }

    @Test
    void storageMode_SwapSlots_Input2_Wrong() {
        final int TEST_INPUT_1 = 31;
        final String TEST_INPUT_2 = "dasdfsa";

        testWriter.println("ls");
        testWriter.println("sw");
        testWriter.println(TEST_INPUT_1);
        testWriter.println(TEST_INPUT_2);
        testWriter.println("x");
        testWriter.println("x");
        testWriter.close();

        cli.start();

        verifyNoMoreInteractions(mockSwapSlotsEventHandler);
    }

    @Test
    void storageMode_SwapSlots_Input1_Cancel() {
        final String TEST_INPUT_1 = "";
        final int TEST_INPUT_2 = 31;

        testWriter.println("ls");
        testWriter.println("sw");
        testWriter.println(TEST_INPUT_1);
        testWriter.println(TEST_INPUT_2);
        testWriter.println("x");
        testWriter.close();

        cli.start();

        verifyNoMoreInteractions(mockSwapSlotsEventHandler);
    }

    @Test
    void storageMode_SwapSlots_Input2_Cancel() {
        final int TEST_INPUT_1 = 31;
        final String TEST_INPUT_2 = "";

        testWriter.println("ls");
        testWriter.println("sw");
        testWriter.println(TEST_INPUT_1);
        testWriter.println(TEST_INPUT_2);
        testWriter.println("x");
        testWriter.close();

        cli.start();

        verifyNoMoreInteractions(mockSwapSlotsEventHandler);
    }

    @Test
    void setCurrentSlotCount() {
        int Test_NUMBER = 42;

        Assertions.assertDoesNotThrow(() -> cli.setCurrentSlotCount(Test_NUMBER));
    }

    @Test
    void setCurrentAllergens() {
        Assertions.assertDoesNotThrow(() -> cli.setCurrentAllergens(EnumSet.allOf(Allergen.class)));
    }

    @Test
    void setLatestCakes() {
        Assertions.assertDoesNotThrow(() -> cli.setLatestCakesString("CakeString"));
    }

    @Test
    void setLatestManufacturers() {
        Assertions.assertDoesNotThrow(() -> cli.setLatestManufacturersString("ManufacturerString"));
    }

    @Test
    void start_StartListening_CheckThroughError() {
        testWriter.close();

        Assertions.assertThrows(NoSuchElementException.class, () -> cli.start());
    }
}
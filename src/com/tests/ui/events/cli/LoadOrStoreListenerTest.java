package com.tests.ui.events.cli;

import com.vending.logic.VendingMachine;
import com.vending.models.SerializableAction;
import com.vending.ui.events.cli.LoadOrStoreListener;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

class LoadOrStoreListenerTest {

    @Test
    void handle_serialize() throws IOException, ClassNotFoundException {
        VendingMachine mockVendingMachine = mock(VendingMachine.class);

        LoadOrStoreListener loadOrStoreListener = new LoadOrStoreListener(mockVendingMachine);

        loadOrStoreListener.handle(SerializableAction.LOAD);

        verify(mockVendingMachine, times(1)).deserialize(any(InputStream.class));
    }

    @Test
    void handle_deserialize() throws IOException {
        VendingMachine mockVendingMachine = mock(VendingMachine.class);

        LoadOrStoreListener loadOrStoreListener = new LoadOrStoreListener(mockVendingMachine);

        loadOrStoreListener.handle(SerializableAction.STORE);

        verify(mockVendingMachine, times(1)).serialize(any(OutputStream.class));
    }
}
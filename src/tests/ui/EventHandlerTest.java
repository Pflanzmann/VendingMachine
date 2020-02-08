package tests.ui;

import com.vending.ui.EventHandler;
import com.vending.ui.EventListener;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class EventHandlerTest {

    @Test
    void addListener_Success_NothingThrows() {
        EventHandler<String> eventHandler = new EventHandler<String>();

        Assertions.assertDoesNotThrow(() ->  eventHandler.addListener(value -> {}));
        Assertions.assertDoesNotThrow(() ->  eventHandler.addListener(value -> {}));
        Assertions.assertDoesNotThrow(() ->  eventHandler.addListener(value -> {}));
        Assertions.assertDoesNotThrow(() ->  eventHandler.addListener(value -> {}));
        Assertions.assertDoesNotThrow(() ->  eventHandler.addListener(value -> {}));
    }

    @Test
    void removeListener_Success() {
        EventHandler<String> eventHandler = new EventHandler<String>();

        EventListener<String> eventListener1 = value -> {};

        Assertions.assertDoesNotThrow(() ->  eventHandler.addListener(eventListener1));
        Assertions.assertDoesNotThrow(() ->  eventHandler.removeListener(eventListener1));
    }

    @Test
    void removeListener_Success_NothingInside() {
        EventHandler<String> eventHandler = new EventHandler<String>();

        EventListener<String> eventListener1 = value -> {};

        Assertions.assertDoesNotThrow(() ->  eventHandler.removeListener(eventListener1));
    }

    @Test
    void invoke_InvokedAll() {
        EventHandler<String> eventHandler = new EventHandler<>();

        String testValue = "you should have aimed for the head";

        EventListener<String> mockEventListener1 = Mockito.mock(EventListener.class);
        EventListener<String> mockEventListener2 = Mockito.mock(EventListener.class);
        EventListener<String> mockEventListener3 = Mockito.mock(EventListener.class);

        Assertions.assertDoesNotThrow(() ->  eventHandler.addListener(mockEventListener1));
        Assertions.assertDoesNotThrow(() ->  eventHandler.addListener(mockEventListener2));
        Assertions.assertDoesNotThrow(() ->  eventHandler.addListener(mockEventListener3));

        Assertions.assertDoesNotThrow(() ->  eventHandler.invoke(testValue));

        Mockito.verify(mockEventListener1, Mockito.times(1)).handle(testValue);
        Mockito.verify(mockEventListener2, Mockito.times(1)).handle(testValue);
        Mockito.verify(mockEventListener3, Mockito.times(1)).handle(testValue);
    }

    @Test
    void invoke_CallTwice_OneRemoved_Success() {
        EventHandler<String> eventHandler = new EventHandler<>();

        String testValue = "42";

        EventListener<String> mockEventListener1 = Mockito.mock(EventListener.class);
        EventListener<String> mockEventListener2 = Mockito.mock(EventListener.class);
        EventListener<String> mockEventListener3 = Mockito.mock(EventListener.class);

        Assertions.assertDoesNotThrow(() ->  eventHandler.addListener(mockEventListener1));
        Assertions.assertDoesNotThrow(() ->  eventHandler.addListener(mockEventListener2));
        Assertions.assertDoesNotThrow(() ->  eventHandler.addListener(mockEventListener3));

        Assertions.assertDoesNotThrow(() ->  eventHandler.invoke(testValue));

        Assertions.assertDoesNotThrow(() ->  eventHandler.removeListener(mockEventListener2));
        Assertions.assertDoesNotThrow(() ->  eventHandler.invoke(testValue));

        Mockito.verify(mockEventListener1, Mockito.times(2)).handle(testValue);
        Mockito.verify(mockEventListener2, Mockito.times(1)).handle(testValue);
        Mockito.verify(mockEventListener3, Mockito.times(2)).handle(testValue);
    }

    @Test
    void invoke_NoListener_NoThrow() {
        EventHandler<String> eventHandler = new EventHandler<>();

        String testValue = "42";

        Assertions.assertDoesNotThrow(() ->  eventHandler.invoke(testValue));
    }
}
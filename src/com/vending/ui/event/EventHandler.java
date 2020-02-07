package com.vending.ui.event;

import com.vending.ui.event.EventListener;

import java.util.ArrayList;
import java.util.List;

public class EventHandler<T> {
    private List<EventListener<T>> listenerList = new ArrayList<EventListener<T>>();

    public void addListener(EventListener<T> listener) {
        this.listenerList.add(listener);
    }

    public void removeListener(EventListener<T> listener) {
        this.listenerList.remove(listener);
    }

    public void invoke(T value) {
        for (EventListener<T> listener : listenerList) listener.handle(value);
    }
}

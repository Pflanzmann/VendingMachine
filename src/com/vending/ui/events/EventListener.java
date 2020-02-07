package com.vending.ui.events;

public interface EventListener<T> {
    void handle(T value);
}

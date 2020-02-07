package com.vending.ui.event;

public interface EventListener<T> {
    void handle(T value);
}

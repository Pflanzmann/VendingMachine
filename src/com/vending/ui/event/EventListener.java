package com.vending.ui.event;

public interface EventListener<T> {
    public void handle(T value);
}

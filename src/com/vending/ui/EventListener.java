package com.vending.ui;

/***
 * A generic interface to use with EventHandler.
 *
 * @param <T> Type of the needed input from the EventHandler
 */
public interface EventListener<T> {

    /**
     * Handle the invoked event.
     *
     * @param value Parameter of the Event
     */
    void handle(T value);
}

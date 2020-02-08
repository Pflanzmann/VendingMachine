package com.vending.ui.cli.observer;

/**
 * A generic interface to use with Observables.
 *
 * @param <T> Type of the observed object
 */
public interface MyObserver<T> {

    /**
     * Method to handle the ObserverUpdate.
     *
     * @param newValue new Value of the observed value
     */
    void update(T newValue);
}

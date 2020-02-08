package com.vending.logic;

import com.vending.models.Allergen;
import com.vending.ui.cli.observer.MyObserver;

import java.util.EnumSet;

/**
 * A interface for a VendingMachine to add Observers for the SlotCount and the containing Allergens.
 */
public interface VendingMachineObservable {

    /**
     * Adds generic MyObservers from type Integer to observe the SlotCount.
     *
     * @param observer The observer you want to add
     */
    void addObserverSlotCount(MyObserver<Integer> observer);

    /**
     * removes generic MyObservers from type Integer which observed the SlotCount.
     *
     * @param observer The observer you want to remove
     */
    void removeObserverSlotCount(MyObserver<Integer> observer);

    /**
     * Adds generic MyObservers from type EnumSet<Allergen> to observe all containing Allergens.
     *
     * @param observer The observer you want to add
     */
    void addObserverAllergens(MyObserver<EnumSet<Allergen>> observer);


    /**
     * removes generic MyObservers from type EnumSet<Allergen> which observed the containing Allergens.
     *
     * @param observer The observer you want to remove
     */
    void removeObserverAllergens(MyObserver<EnumSet<Allergen>> observer);
}

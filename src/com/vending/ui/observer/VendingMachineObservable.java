package com.vending.ui.observer;

import com.vending.models.Allergen;
import com.vending.ui.observer.MyObserver;

import java.util.EnumSet;

public interface VendingMachineObservable {
    void addObserverSlotCount(MyObserver<Integer> observer);

    void addObserverAllergens(MyObserver<EnumSet<Allergen>> observer);
}

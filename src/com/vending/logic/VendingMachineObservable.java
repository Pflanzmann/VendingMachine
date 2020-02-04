package com.vending.logic;

import com.vending.models.Allergen;
import com.vending.ui.MyObserver;

import java.util.EnumSet;

public interface VendingMachineObservable {
    void addObserverSlotCount(MyObserver<Integer> observer);

    void addObserverAllergens(MyObserver<EnumSet<Allergen>> observer);
}

package com.vending.ui.gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class CakeDataModel {

    private final SimpleIntegerProperty slot;
    private final SimpleStringProperty manufacturer;
    private final SimpleLongProperty shelfLife;
    private final SimpleStringProperty coveringTypes;

    public CakeDataModel(SimpleIntegerProperty slot, SimpleStringProperty manufacturer, SimpleLongProperty shelfLife, SimpleStringProperty coveringTypes) {
        this.slot = slot;
        this.manufacturer = manufacturer;
        this.shelfLife = shelfLife;
        this.coveringTypes = coveringTypes;
    }

    public int getSlot() {
        return slot.get();
    }

    public void setSlot(int slot) {
        this.slot.set(slot);
    }

    public String getManufacturer() {
        return manufacturer.get();
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer.set(manufacturer);
    }

    public Long getShelfLife() {
        return shelfLife.get();
    }

    public void setShelfLife(Long shelfLife) {
        this.shelfLife.set(shelfLife);
    }

    public String getCoveringTypes() {
        return coveringTypes.get();
    }

    public void setCoveringTypes(String coveringTypes) {
        this.coveringTypes.set(coveringTypes);
    }
}

package com.vending.models;

import java.io.Serializable;

public class Manufacturer implements Serializable {

    private String name;

    public Manufacturer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

package com.company.models.mitarbeiter;

import java.io.Serializable;

public class HerstellerClass implements Hersteller, Serializable {

    private String name;

    public HerstellerClass(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return null;
    }
}

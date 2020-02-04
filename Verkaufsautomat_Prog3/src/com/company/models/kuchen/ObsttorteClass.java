package com.company.models.kuchen;

import com.company.models.mitarbeiter.Hersteller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;

public class ObsttorteClass implements Obsttorte, Serializable {

    private String kremsorte;
    private String obstsorte;
    private BigDecimal preis;
    private Hersteller hersteller;
    private ArrayList<Allergen> allergene;
    private int naehrwerte;
    private Duration haltbarkeit;

    public ObsttorteClass(String kremsorte, String obstsorte, BigDecimal preis, Hersteller hersteller, ArrayList<Allergen> allergene, int naehrwerte, Duration haltbarkeit) {
        this.kremsorte = kremsorte;
        this.obstsorte = obstsorte;
        this.preis = preis;
        this.hersteller = hersteller;
        this.allergene = allergene;
        this.naehrwerte = naehrwerte;
        this.haltbarkeit = haltbarkeit;
    }

    @Override
    public String getKremsorte() {
        return kremsorte;
    }

    @Override
    public String getObstsorte() {
        return obstsorte;
    }

    @Override
    public BigDecimal getPreis() {
        return preis;
    }

    @Override
    public Hersteller getHersteller() {
        return hersteller;
    }

    @Override
    public Collection<Allergen> getAllergene() {
        return allergene;
    }

    @Override
    public int getNaehrwert() {
        return naehrwerte;
    }

    @Override
    public Duration getHaltbarkeit() {
        return haltbarkeit;
    }
}

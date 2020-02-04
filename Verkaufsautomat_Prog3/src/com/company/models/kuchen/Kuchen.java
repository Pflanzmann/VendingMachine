package com.company.models.kuchen;

import com.company.models.mitarbeiter.Hersteller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;

public interface Kuchen extends Serializable {
    BigDecimal getPreis();
    Hersteller getHersteller();
    Collection<Allergen> getAllergene();
    int getNaehrwert();
    Duration getHaltbarkeit();
}

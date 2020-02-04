package com.company.logic;

import com.company.exception.*;
import com.company.models.kuchen.Allergen;
import com.company.models.kuchen.Kremkuchen;
import com.company.models.kuchen.Kuchen;
import com.company.models.kuchen.Obstkuchen;
import com.company.models.mitarbeiter.Hersteller;

import java.io.Serializable;
import java.time.Duration;
import java.util.*;

public class Verkaufsautomat implements Serializable {

    private Map<String, Hersteller> herstellerListe = new HashMap<String, Hersteller>();
    private Map<Integer, Kuchen> kuchenFaecher = new HashMap<Integer, Kuchen>();
    private Map<Kuchen, Date> kuchenDate = new HashMap<Kuchen, Date>();

    private int hasErdnuss = 0;
    private int hasGluten = 0;
    private int hasHaselnuss = 0;
    private int hasSesamsamen = 0;

    private int anzahlFreierPlaetze;
    private int maxSlots;

    public Verkaufsautomat(int Platzanzahl) {

        this.anzahlFreierPlaetze = Platzanzahl;
        this.maxSlots = Platzanzahl;
    }

    public void addHersteller(Hersteller hersteller) throws HerstellerExistsException {
        if (herstellerListe.containsKey(hersteller.getName()))
            throw new HerstellerExistsException();
        else
            herstellerListe.put(hersteller.getName(), hersteller);
    }

    public Hersteller getHerstellerFromName(String herstellerName) throws NoHerstellerAvailableException {
        if (!herstellerListe.containsKey(herstellerName)) {
            throw new NoHerstellerAvailableException();
        }
        return herstellerListe.get(herstellerName);
    }

    public int addKuchenGetFachnummer(Kuchen kuchen) throws NoHerstellerAvailableException, NoSpaceException, ContainsKuchenException {
        if (!herstellerListe.containsKey(kuchen.getHersteller().getName())) {
            throw new NoHerstellerAvailableException();

        } else if (kuchenFaecher.containsValue(kuchen)) {
            throw new ContainsKuchenException();

        } else if (anzahlFreierPlaetze <= 0) {
            throw new NoSpaceException();

        } else {
            Random random = new Random();
            int fachnummer = 0;

            do {
                fachnummer = random.nextInt(maxSlots);
            } while (kuchenFaecher.containsKey(fachnummer));

            anzahlFreierPlaetze--;
            kuchenFaecher.put(fachnummer, kuchen);
            kuchenDate.put(kuchen, new Date());

            ArrayList<Allergen> tempList = new ArrayList<Allergen>();
            tempList.addAll(kuchen.getAllergene());

            addAllergene(tempList);

            return fachnummer;
        }
    }

    private void addAllergene(ArrayList<Allergen> allergene) {
        for (int i = 0; i < allergene.size(); i++) {

            switch (allergene.get(i)) {
                case Gluten:
                    hasGluten++;
                    break;

                case Erdnuss:
                    hasErdnuss++;
                    break;

                case Haselnuss:
                    hasHaselnuss++;
                    break;

                case Sesamsamen:
                    hasSesamsamen++;
                    break;
            }
        }
    }

    public ArrayList<Kuchen> getAllKuchen() {
        return new ArrayList<Kuchen>(kuchenFaecher.values());
    }

    public ArrayList<Kuchen> getKuchenfromType(boolean isKremkuchen, boolean isObstkuchen) {

        ArrayList<Kuchen> kuchenList = new ArrayList<Kuchen>(kuchenFaecher.values());
        ArrayList<Kuchen> tempList = new ArrayList<Kuchen>();

        for (int i = 0; i < kuchenList.size(); i++) {
            if (isKremkuchen && kuchenList.get(i) instanceof Kremkuchen)
                tempList.add(kuchenList.get(i));
            else if (isObstkuchen && kuchenList.get(i) instanceof Obstkuchen)
                tempList.add(kuchenList.get(i));
        }

        return tempList;
    }

    public int getKuchenCountFromHersteller(Hersteller hersteller) {

        ArrayList<Kuchen> kuchenList = new ArrayList<Kuchen>(kuchenFaecher.values());
        int count = 0;

        for (int i = 0; i < kuchenList.size(); i++) {
            if (kuchenList.get(i).getHersteller() == hersteller)
                count++;
        }

        return count;
    }

    public Kuchen getKuchenfromFachnummer(int fachnummer) throws NoKuchenFoundException {
        if (!kuchenFaecher.containsKey(fachnummer))
            throw new NoKuchenFoundException();

        return kuchenFaecher.get(fachnummer);
    }

    public Duration getRestHalbarkeitFromKuchen(Kuchen kuchen) {

        Duration duration = kuchen.getHaltbarkeit();

        long diffInSeconds = (new Date().getSeconds() - kuchenDate.get(kuchen).getSeconds());

        duration = duration.minusSeconds(diffInSeconds);

        return duration;
    }

    public void removeKuchen(Kuchen kuchen) throws NoKuchenFoundException {
        if (!kuchenFaecher.containsValue(kuchen))
            throw new NoKuchenFoundException();

        kuchenDate.remove(kuchen);

        while (kuchenFaecher.values().remove(kuchen)) ;

        anzahlFreierPlaetze++;

        ArrayList<Allergen> tempList = new ArrayList<Allergen>();
        tempList.addAll(kuchen.getAllergene());

        removeAllergene(tempList);
    }

    private void removeAllergene(ArrayList<Allergen> allergene) {
        for (int i = 0; i < allergene.size(); i++) {

            switch (allergene.get(i)) {
                case Gluten:
                    hasGluten--;
                    break;

                case Erdnuss:
                    hasErdnuss--;
                    break;

                case Haselnuss:
                    hasHaselnuss--;
                    break;

                case Sesamsamen:
                    hasSesamsamen--;
                    break;
            }
        }
    }

    public ArrayList<Allergen> getAllAllergene() {

        ArrayList<Allergen> tempList = new ArrayList<Allergen>();

        if (hasSesamsamen > 0)
            tempList.add(Allergen.Sesamsamen);
        if (hasHaselnuss > 0)
            tempList.add(Allergen.Haselnuss);
        if (hasErdnuss > 0)
            tempList.add(Allergen.Erdnuss);
        if (hasGluten > 0)
            tempList.add(Allergen.Gluten);

        return tempList;
    }

    public ArrayList<Allergen> getMissingAllergene() {

        ArrayList<Allergen> tempList = new ArrayList<Allergen>();

        if (hasSesamsamen == 0)
            tempList.add(Allergen.Sesamsamen);
        if (hasHaselnuss == 0)
            tempList.add(Allergen.Haselnuss);
        if (hasErdnuss == 0)
            tempList.add(Allergen.Erdnuss);
        if (hasGluten == 0)
            tempList.add(Allergen.Gluten);

        return tempList;
    }
}

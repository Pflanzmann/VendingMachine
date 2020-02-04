package com.company.ui;

import com.company.logic.eventLisstener.AddHerstellerEventListener;
import com.company.logic.eventLisstener.AddKuchenEventListener;
import com.company.models.kuchen.Kuchen;
import com.company.models.mitarbeiter.Hersteller;

import java.util.ArrayList;
import java.util.List;

public class InputEventHandler {
    private List<AddKuchenEventListener> addKuchenListenerList = new ArrayList<AddKuchenEventListener>();
    private List<AddHerstellerEventListener> AddHerstellerListenerList = new ArrayList<AddHerstellerEventListener>();

    public void addAddKuchenLisstener(AddKuchenEventListener listener) {
        this.addKuchenListenerList.add(listener);
    }

    public void removeAddKuchenLisstener(AddKuchenEventListener listener) {
        this.addKuchenListenerList.remove(listener);
    }

    public void handleAddKuchen(Kuchen kuchen) {
        for (AddKuchenEventListener listener : addKuchenListenerList) listener.onEvent(kuchen);
    }

    //AddHersteller
    public void addAddHerstellerLisstener(AddHerstellerEventListener listener) {
        this.AddHerstellerListenerList.add(listener);
    }

    public void removeAddHerstellerLisstener(AddHerstellerEventListener listener) {
        this.AddHerstellerListenerList.remove(listener);
    }

    public void handleAddHersteller(Hersteller hersteller) {
        for (AddHerstellerEventListener listener : AddHerstellerListenerList) listener.onEvent(hersteller);
    }
}

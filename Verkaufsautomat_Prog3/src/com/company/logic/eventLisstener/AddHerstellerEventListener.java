package com.company.logic.eventLisstener;

import com.company.exception.HerstellerExistsException;
import com.company.logic.Verkaufsautomat;
import com.company.models.mitarbeiter.Hersteller;

import java.util.EventListener;

public class AddHerstellerEventListener implements EventListener {

    private Verkaufsautomat verkaufsautomat;

    public AddHerstellerEventListener(Verkaufsautomat verkaufsautomat) {
        this.verkaufsautomat = verkaufsautomat;
    }

    public void onEvent(Hersteller hersteller) {

        try {
            verkaufsautomat.addHersteller(hersteller);
            System.out.println("Log: (AddHerstellerEventListener) -> AddHersteller");
        } catch (HerstellerExistsException e) {
            System.out.println("\033[0;31m" + "Log: (AddHerstellerEventListener) -> HerstellerExistsException" + "\033[0m");
        }

    }
}

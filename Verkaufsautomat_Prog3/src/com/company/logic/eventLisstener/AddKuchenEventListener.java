package com.company.logic.eventLisstener;

import com.company.exception.ContainsKuchenException;
import com.company.exception.NoHerstellerAvailableException;
import com.company.exception.NoSpaceException;
import com.company.logic.Verkaufsautomat;
import com.company.models.kuchen.Kuchen;

import java.util.EventListener;

public class AddKuchenEventListener implements EventListener {

    private Verkaufsautomat verkaufsautomat;

    public AddKuchenEventListener(Verkaufsautomat verkaufsautomat) {
        this.verkaufsautomat = verkaufsautomat;
    }

    public void onEvent(Kuchen kuchen) {

        try {
            verkaufsautomat.addKuchenGetFachnummer(kuchen);
            System.out.println("Log: (AddKuchenEventListener) -> AddKuchen");
        } catch (NoHerstellerAvailableException e) {
            System.out.println("\033[0;31m"  + "Log: (AddKuchenEventListener) -> NoHerstellerAvailableException" + "\033[0m");
        } catch (NoSpaceException e) {
            System.out.println("\033[0;31m" + "Log: (AddKuchenEventListener) -> NoSpaceException" + "\033[0m");
        } catch (ContainsKuchenException e) {
            System.out.println("\033[0;31m" + "Log: (AddKuchenEventListener) -> ContainsKuchenException" + "\033[0m");
        }

    }
}

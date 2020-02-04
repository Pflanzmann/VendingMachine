package com.company.ui;

import com.company.Database.VerkaufsautomatSpeicherung;
import com.company.logic.Verkaufsautomat;
import com.company.logic.eventLisstener.AddHerstellerEventListener;
import com.company.logic.eventLisstener.AddKuchenEventListener;

import java.io.InputStream;
import java.io.PrintStream;

public class Main {

    public static void main(String[] args) {

        PrintStream writeTo = System.out;
        InputStream inputFrom = System.in;
        Verkaufsautomat verkaufsautomat = new Verkaufsautomat(5);

        InputEventHandler inputEventHandler = new InputEventHandler();
        AddHerstellerEventListener herstellerEventListener = new AddHerstellerEventListener(verkaufsautomat);
        AddKuchenEventListener addKuchenEventListener = new AddKuchenEventListener(verkaufsautomat);

        inputEventHandler.addAddHerstellerLisstener(herstellerEventListener);
        inputEventHandler.addAddKuchenLisstener(addKuchenEventListener);

        //LogikController cli = new LogikController(inputFrom, writeTo, inputEventHandler, verkaufsautomat, new VerkaufsautomatSpeicherung());
    }
}

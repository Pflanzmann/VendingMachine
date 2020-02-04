package Tests.Simulation;

import com.company.logic.Verkaufsautomat;
import com.company.logic.eventLisstener.AddHerstellerEventListener;
import com.company.logic.eventLisstener.AddKuchenEventListener;
import com.company.ui.InputEventHandler;

import java.io.PrintStream;


public class Simulation {

    public static void main(String[] args) {
        PrintStream writeTo = System.out;
        Verkaufsautomat verkaufsautomat = new Verkaufsautomat(5);

        InputEventHandler inputEventHandler = new InputEventHandler();
        AddHerstellerEventListener herstellerEventListener = new AddHerstellerEventListener(verkaufsautomat);
        AddKuchenEventListener addKuchenEventListener = new AddKuchenEventListener(verkaufsautomat);

        inputEventHandler.addAddHerstellerLisstener(herstellerEventListener);
        inputEventHandler.addAddKuchenLisstener(addKuchenEventListener);

        Einlagerungsthread thread = new Einlagerungsthread(writeTo, inputEventHandler);
        thread.run();
    }
}

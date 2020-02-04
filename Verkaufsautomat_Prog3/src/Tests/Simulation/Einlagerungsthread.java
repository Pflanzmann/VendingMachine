package Tests.Simulation;

import com.company.models.kuchen.Allergen;
import com.company.models.kuchen.KremkuchenClass;
import com.company.models.kuchen.ObstkuchenClass;
import com.company.models.kuchen.ObsttorteClass;
import com.company.models.mitarbeiter.HerstellerClass;
import com.company.ui.InputEventHandler;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Random;

public class Einlagerungsthread implements Runnable {

    private InputEventHandler inputEventHandler;
    private PrintStream writer;

    public Einlagerungsthread(PrintStream writer, InputEventHandler inputEventHandler) {
        this.writer = writer;
        this.inputEventHandler = inputEventHandler;
    }

    @Override
    public void run() {
        writer.println("\n-------------------------------");
        writer.println("---- start ----------------------");
        HerstellerClass herstellerClass = new HerstellerClass("TestHersteller");

        ArrayList<Allergen> allergenArrayList1 = new ArrayList<Allergen>();
        allergenArrayList1.add(Allergen.Erdnuss);
        allergenArrayList1.add(Allergen.Haselnuss);

        ArrayList<Allergen> allergenArrayList2 = new ArrayList<Allergen>();
        allergenArrayList2.add(Allergen.Gluten);
        allergenArrayList2.add(Allergen.Sesamsamen);

        inputEventHandler.handleAddHersteller(herstellerClass);
        Random random = new Random();


        while (true) {
            writer.println("\n-------------------------------");
            writer.println("---------- new pass -----------");

            int randomCake = random.nextInt(3);

            switch (randomCake) {
                case 0:
                    KremkuchenClass cake1 = new KremkuchenClass(
                            "Sahne",
                            new BigDecimal(2.50),
                            herstellerClass,
                            allergenArrayList1,
                            24,
                            Duration.ofDays(2));

                    inputEventHandler.handleAddKuchen(cake1);
                    writer.println("Added new Kremkuchen");
                    break;

                case 1:
                    ObstkuchenClass cake2 = new ObstkuchenClass(
                            "Erdbeere",
                            new BigDecimal(30),
                            herstellerClass,
                            allergenArrayList2,
                            242,
                            Duration.ofDays(31));

                    inputEventHandler.handleAddKuchen(cake2);
                    writer.println("Added new Obstkuchen");
                    break;

                case 2:
                    ObsttorteClass cake3 = new ObsttorteClass(
                            "Bananenkrem",
                            "Banane",
                            new BigDecimal(42),
                            herstellerClass,
                            allergenArrayList1,
                            2,
                            Duration.ofDays(187));

                    inputEventHandler.handleAddKuchen(cake3);
                    writer.println("Added new Obsttorte");
                    break;
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

package com.company.ui;

import com.company.models.kuchen.Allergen;
import com.company.models.kuchen.KremkuchenClass;
import com.company.models.kuchen.ObstkuchenClass;
import com.company.models.mitarbeiter.HerstellerClass;

import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Scanner;

public class CLI {

    public CLI(InputStream reader, PrintStream writer, InputEventHandler handler) {
        this.reader = reader;
        this.writer = writer;
        this.handler = handler;
    }

    private InputStream reader;
    private PrintStream writer;
    private InputEventHandler handler;

    Boolean isStarted = true;

    public void startInterface() {
        Scanner scanner = new Scanner(reader);
        HerstellerClass herstellerClass = new HerstellerClass("TestHersteller");

        ArrayList<Allergen> allergenArrayList1 = new ArrayList<Allergen>();
        allergenArrayList1.add(Allergen.Erdnuss);
        allergenArrayList1.add(Allergen.Haselnuss);

        ArrayList<Allergen> allergenArrayList2 = new ArrayList<Allergen>();
        allergenArrayList2.add(Allergen.Gluten);
        allergenArrayList2.add(Allergen.Sesamsamen);

        KremkuchenClass cake1 = new KremkuchenClass(
                "Sahne",
                new BigDecimal(2.50),
                herstellerClass,
                allergenArrayList1,
                24,
                Duration.ofDays(2));

        ObstkuchenClass cake2 = new ObstkuchenClass(
                "Erdbeere",
                new BigDecimal(30),
                herstellerClass,
                allergenArrayList2,
                242,
                Duration.ofDays(31));

        while (isStarted) {
            writer.println("\n-------------------------------");
            writer.println("What do you want to do?");
            writer.println("add manufacturer| add creamcake | add fruitcake");

            String input = scanner.nextLine();

            switch (input) {
                case "add manufacturer":
                    handler.handleAddHersteller(herstellerClass);
                    break;

                case "add creamcake":
                    handler.handleAddKuchen(cake1);
                    break;

                case "add fruitcake":
                    handler.handleAddKuchen(cake2);
                    break;
            }
        }
    }
}

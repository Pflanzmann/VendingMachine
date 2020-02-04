package com.company.Controller;

import com.company.Database.VerkaufsautomatSpeicherung;
import com.company.exception.ContainsKuchenException;
import com.company.exception.HerstellerExistsException;
import com.company.exception.NoHerstellerAvailableException;
import com.company.exception.NoSpaceException;
import com.company.logic.Verkaufsautomat;
import com.company.models.kuchen.Allergen;
import com.company.models.kuchen.Kuchen;
import com.company.models.kuchen.ObstkuchenClass;
import com.company.models.mitarbeiter.HerstellerClass;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LogikController {

    public LogikController(InputStream reader, OutputStream outStream, VerkaufsautomatSpeicherung verkaufsautomatSpeicherung) {
        this.reader = reader;
        this.verkaufsautomat = new Verkaufsautomat(100);
        this.verkaufsautomatSpeicherung = verkaufsautomatSpeicherung;
        this.outStream = outStream;
    }

    private Verkaufsautomat verkaufsautomat;
    private InputStream reader;
    private OutputStream outStream;
    private ObjectOutputStream writer;
    private VerkaufsautomatSpeicherung verkaufsautomatSpeicherung;

    Boolean isStarted = true;

    public void startInterface() throws IOException {
        writer = new ObjectOutputStream(outStream);

        Scanner scanner = new Scanner(reader);
        HerstellerClass herstellerClass = new HerstellerClass("TestHersteller");

        ArrayList<Allergen> allergenArrayList1 = new ArrayList<Allergen>();
        allergenArrayList1.add(Allergen.Erdnuss);
        allergenArrayList1.add(Allergen.Haselnuss);

        ArrayList<Allergen> allergenArrayList2 = new ArrayList<Allergen>();
        allergenArrayList2.add(Allergen.Gluten);
        allergenArrayList2.add(Allergen.Sesamsamen);
        while (isStarted) {
            ObstkuchenClass cake1 = new ObstkuchenClass(
                    "Apfel",
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

            String input = scanner.nextLine();

            System.out.println(input);

            switch (input) {
                case "add manufacturer":
                    try {
                        verkaufsautomat.addHersteller(herstellerClass);
                    } catch (HerstellerExistsException e) {
                        e.printStackTrace();
                    }
                    break;

                case "add fruitcake1":
                    try {
                        verkaufsautomat.addKuchenGetFachnummer(cake1);
                    } catch (ContainsKuchenException | NoSpaceException | NoHerstellerAvailableException e) {
                        e.printStackTrace();
                    }
                    break;

                case "add fruitcake2":
                    try {
                        verkaufsautomat.addKuchenGetFachnummer(cake2);
                    } catch (ContainsKuchenException | NoSpaceException | NoHerstellerAvailableException e) {
                        e.printStackTrace();
                    }
                    break;

                case "store":
                    verkaufsautomatSpeicherung.store(verkaufsautomat);
                    break;
                case "load":
                    try {
                        verkaufsautomat = verkaufsautomatSpeicherung.load();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;

                default:
                    break;
            }

            List<Kuchen> kuchen = verkaufsautomat.getAllKuchen();

            System.out.println(kuchen.size());

            if (kuchen.size() != 0)
                try {
                    writer.writeObject(kuchen);
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}

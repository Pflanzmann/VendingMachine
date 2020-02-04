package com.company.Database;

import com.company.logic.Verkaufsautomat;

import java.io.*;

public class VerkaufsautomatSpeicherung {

    final String FILENAME = "Verkaufsautomat.txt";

    public void store(Verkaufsautomat verkaufsautomat) throws IOException {

        FileOutputStream fileOutputStream = new FileOutputStream(new File(FILENAME));
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeObject(verkaufsautomat);
    }

    public Verkaufsautomat load() throws IOException, ClassNotFoundException {
        FileInputStream inF = new FileInputStream(FILENAME);
        ObjectInputStream inO = new ObjectInputStream(inF);

        return (Verkaufsautomat) inO.readObject();
    }
}

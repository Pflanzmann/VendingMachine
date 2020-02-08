package com.vending.mains;

import com.vending.ui.gui.GuiController;
import com.vending.helper.RemoteStartCLI;

import java.io.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main_GUI extends Application {

    private GuiController controller;
    private FXMLLoader loader;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        PipedInputStream writerOutput = new PipedInputStream();
        PipedOutputStream outputStream = new PipedOutputStream();
        writerOutput.connect(outputStream);
        PrintStream writer = new PrintStream(outputStream);

        PipedInputStream isCakes = new PipedInputStream();
        PipedInputStream isManufacturers = new PipedInputStream();

        ObjectOutputStream objectOutputStreamCakeListener = new ObjectOutputStream(new PipedOutputStream(isCakes));
        ObjectOutputStream objectOutputStreamManufacturerListener = new ObjectOutputStream(new PipedOutputStream(isManufacturers));

        ObjectInputStream objectInputStreamCakes = new ObjectInputStream(isCakes);
        ObjectInputStream objectInputStreamManufacturers = new ObjectInputStream(isManufacturers);

        RemoteStartCLI remoteStartCLI = new RemoteStartCLI(writerOutput, System.out, objectOutputStreamCakeListener, objectOutputStreamManufacturerListener);
        remoteStartCLI.startCli();

        loader = new FXMLLoader(getClass().getResource("/com/vending/ui/vending_machine_scene.fxml"));

        AnchorPane root = loader.load();
        primaryStage.setTitle("Vending Machine");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        controller = loader.getController();
        controller.injectValues(writer, objectInputStreamCakes, objectInputStreamManufacturers);
    }
}

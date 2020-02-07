package com.vending.mains;

import com.vending.ui.GuiController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main_TCP_Client extends Application {

    private GuiController controller;
    private FXMLLoader loader;

    @Override
    public void start(Stage primaryStage) throws IOException {
        String ip = "127.0.0.1";
        int port0 = 13370;
        int port1 = 13371;
        int port2 = 13372;

        Socket socket0 = new Socket(ip, port0);
        Socket socket1 = new Socket(ip, port1);
        Socket socket2 = new Socket(ip, port2);

        OutputStream outputStream = socket0.getOutputStream();
        PrintStream writer = new PrintStream(outputStream);

        ObjectInputStream objectInputStreamCakes = new ObjectInputStream(socket1.getInputStream());
        ObjectInputStream objectInputStreamManufacturers = new ObjectInputStream(socket2.getInputStream());

        loader = new FXMLLoader(getClass().getResource("/com/vending/ui/vending_machine_scene.fxml"));
        AnchorPane root = loader.load();
        primaryStage.setTitle("Vending Machine");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        loader.<GuiController>getController().injectValues(writer, objectInputStreamCakes, objectInputStreamManufacturers);
    }
}

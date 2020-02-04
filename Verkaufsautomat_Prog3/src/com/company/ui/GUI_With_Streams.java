package com.company.ui;

import com.company.Controller.LogikController;
import com.company.Database.VerkaufsautomatSpeicherung;
import com.company.models.kuchen.Kuchen;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GUI_With_Streams extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    Button buttonAddHersteller;
    Button buttonAddKuchen1;
    Button buttonAddKuchen2;
    Button buttonStore;
    Button buttonLoad;

    Boolean isRunning = true;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Krasse App um zur Prüfung zugelassen zu werden");

        PipedInputStream is1 = new PipedInputStream();
        PipedOutputStream os1 = new PipedOutputStream();

        PipedInputStream is2 = new PipedInputStream();
        PipedOutputStream os2 = new PipedOutputStream();

        try {
            is1.connect(os1);
            is2.connect(os2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        PrintStream writer = new PrintStream(os1);

        new Thread() {
            @Override
            public void run() {
                super.run();
                System.out.println("Logik started");

                LogikController logikController = new LogikController(is1, os2, new VerkaufsautomatSpeicherung());
                try {
                    logikController.startInterface();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        //TableView
        TableView tableView = new TableView();
        TableColumn<String, Kuchen> column1 = new TableColumn<>("Sorte");
        column1.setCellValueFactory(new PropertyValueFactory<>("obstsorte"));
        tableView.getColumns().add(column1);
        tableView.setLayoutX(125);
        tableView.setLayoutY(150);


        new Thread() {
            @Override
            public void run() {
                super.run();
                System.out.println("Logik-listening started");
                ObjectInputStream ois = null;
                try {
                    ois = new ObjectInputStream(is2);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                while (isRunning) {
                    try {
                        List<Kuchen> kuchens = (ArrayList) ois.readObject();

                        Platform.runLater(() -> tableView.getItems().clear());
                        Platform.runLater(() -> tableView.getItems().addAll(kuchens));

                        tableView.refresh();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        //Buttons
        buttonStore = new Button("Store");
        buttonStore.setLayoutX(20);
        buttonStore.setLayoutY(100);
        buttonStore.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                writer.println("store");
            }
        });

        //Buttons
        buttonLoad = new Button("Load");
        buttonLoad.setLayoutX(210);
        buttonLoad.setLayoutY(100);
        buttonLoad.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                writer.println("load");
            }
        });
        //Buttons
        buttonAddHersteller = new Button("Hersteller hinzufügen");
        buttonAddHersteller.setLayoutX(20);
        buttonAddHersteller.setLayoutY(50);
        buttonAddHersteller.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                writer.println("add manufacturer");
            }
        });

        buttonAddKuchen1 = new Button("Apfelkuchen hinzufügen");
        buttonAddKuchen1.setLayoutX(400);
        buttonAddKuchen1.setLayoutY(50);
        buttonAddKuchen1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    writer.println("add fruitcake1");
                } catch (Exception exception) {

                }
            }
        });


        buttonAddKuchen2 = new Button("Erdbeerkuchen hinzufügen");
        buttonAddKuchen2.setLayoutX(210);
        buttonAddKuchen2.setLayoutY(50);
        buttonAddKuchen2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    writer.println("add fruitcake2");
                } catch (Exception exception) {

                }
            }
        });

        //Layout
        Pane layout = new Pane();
        layout.getChildren().add(tableView);
        layout.getChildren().add(buttonAddHersteller);
        layout.getChildren().add(buttonAddKuchen1);
        layout.getChildren().add(buttonAddKuchen2);
        layout.getChildren().add(buttonStore);
        layout.getChildren().add(buttonLoad);

        Scene scene = new Scene(layout, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

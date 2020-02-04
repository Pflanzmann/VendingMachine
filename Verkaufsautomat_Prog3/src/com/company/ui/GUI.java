package com.company.ui;

import com.company.logic.Verkaufsautomat;
import com.company.models.kuchen.Allergen;
import com.company.models.kuchen.KremkuchenClass;
import com.company.models.kuchen.Kuchen;
import com.company.models.mitarbeiter.HerstellerClass;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;

public class GUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    Button buttonAdd;
    Button buttonDelete;
    TextField sorteText;
    TextField removeRowText;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Title of the Window");

        //Verkaufsautomat
        Verkaufsautomat verkaufsautomat = new Verkaufsautomat(5);

        //Hersteller
        HerstellerClass herstellerClass = new HerstellerClass("Nike");

        //Alles für Kuchen1
        ArrayList<Allergen> allergenArrayList1 = new ArrayList<Allergen>();
        allergenArrayList1.add(Allergen.Erdnuss);
        allergenArrayList1.add(Allergen.Haselnuss);

        //TableView
        TableView tableView = new TableView();
        TableColumn<String, Kuchen> column1 = new TableColumn<>("Sorte");
        column1.setCellValueFactory(new PropertyValueFactory<>("kremsorte"));
        tableView.getColumns().add(column1);
        tableView.setLayoutX(125);
        tableView.setLayoutY(150);

        //Buttons
        buttonAdd = new Button("Einfügen");
        buttonAdd.setLayoutX(100);
        buttonAdd.setLayoutY(50);
        buttonAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Kuchen exampleCake = new KremkuchenClass(
                        sorteText.getText(),
                        new BigDecimal(2.5),
                        herstellerClass,
                        allergenArrayList1,
                        24,
                        Duration.ofDays(2));

                tableView.getItems().add(exampleCake);
            }
        });

        buttonDelete = new Button("Löschen");
        buttonDelete.setLayoutX(325);
        buttonDelete.setLayoutY(50);
        buttonDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    tableView.getItems().remove(Integer.parseInt(removeRowText.getText()));
                } catch (Exception exception) {
                    removeRowText.setText("Falsche Eingabe");
                }
            }
        });

        //TextView
        sorteText = new TextField("Kuchensorte");
        sorteText.setLayoutX(100);
        sorteText.setLayoutY(100);

        removeRowText = new TextField("Reihe zu Entfernen");
        removeRowText.setLayoutX(300);
        removeRowText.setLayoutY(100);

        //Layout
        Pane layout = new Pane();
        layout.getChildren().add(tableView);
        layout.getChildren().add(sorteText);
        layout.getChildren().add(removeRowText);
        layout.getChildren().add(buttonAdd);
        layout.getChildren().add(buttonDelete);

        Scene scene = new Scene(layout, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

package com.vending.ui.gui;

import com.vending.models.CoveringType;
import com.vending.models.Manufacturer;
import com.vending.models.cakes.Cake;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class GuiController implements Initializable {

    @FXML
    private TextField textViewManufacturerInput;
    @FXML
    private ChoiceBox<String> choiceBoxManufacturerNames;

    @FXML
    private TextField textFieldDeleteIndex;
    @FXML
    private ListView listViewAllManufacturers;

    @FXML
    private TableView tableViewAllCakes;
    @FXML
    private TableColumn<CakeDataModel, Integer> tableColumnSlot;
    @FXML
    private TableColumn<CakeDataModel, String> tableColumnManufacturer;
    @FXML
    private TableColumn<CakeDataModel, Long> tableColumnShelfLife;
    @FXML
    private TableColumn<CakeDataModel, List<CoveringType>> tableColumnType;

    @FXML
    private TextField textFieldSwapFrom;
    @FXML
    private TextField textFieldSwapTo;

    private PrintStream writer;
    private ObjectInputStream objectInputStreamCakes;
    private ObjectInputStream objectInputStreamManufacturers;

    private List<CoveringType> coveringList = new ArrayList<>();
    private Cake[] cakesCache;
    private List<Manufacturer> manufacturersCache;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableColumnSlot.setCellValueFactory(new PropertyValueFactory<CakeDataModel, Integer>("slot"));
        tableColumnShelfLife.setCellValueFactory(new PropertyValueFactory<CakeDataModel, Long>("shelfLife"));
        tableColumnManufacturer.setCellValueFactory(new PropertyValueFactory<CakeDataModel, String>("manufacturer"));
        tableColumnType.setCellValueFactory(new PropertyValueFactory<CakeDataModel, List<CoveringType>>("coveringTypes"));
    }

    public void injectValues(PrintStream writer,
                             ObjectInputStream objectInputStreamCakes,
                             ObjectInputStream objectInputStreamManufacturers) {
        this.writer = writer;
        this.objectInputStreamCakes = objectInputStreamCakes;
        this.objectInputStreamManufacturers = objectInputStreamManufacturers;

        startListener();
    }

    public void startListener() {
        new Service<Void>() {
            @Override
            protected Task createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() {
                        boolean running = true;
                        while (running) {
                            updateCakes();
                        }
                        return null;
                    }
                };
            }
        }.start();

        new Service<Void>() {
            @Override
            protected Task createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() {
                        boolean running = true;
                        while (running) {
                            updateManufacturers();
                        }
                        return null;
                    }
                };
            }
        }.start();
    }

    @FXML
    private void setButtonAddManufacturer() {
        writer.println("a");
        writer.println("m");
        writer.println(textViewManufacturerInput.getText());
    }

    @FXML
    private void setButtonAddPeanutCovering() {
        coveringList.add(CoveringType.PEANUT);
    }

    @FXML
    private void setButtonAddHazelnutCovering() {
        coveringList.add(CoveringType.HAZELNUT);
    }

    @FXML
    private void setButtonResetCake() {
        coveringList = new ArrayList<>();
    }

    @FXML
    private void setButtonFinishCake() {
        writer.println("a");
        writer.println("c");
        writer.println(choiceBoxManufacturerNames.getSelectionModel().getSelectedItem());
        for (CoveringType type : coveringList) {
            switch (type) {
                case PEANUT:
                    writer.println("p");
                    break;
                case HAZELNUT:
                    writer.println("h");
                    break;
            }
        }
        writer.println("f");
    }

    @FXML
    private void setButtonDeleteCake() {
        writer.println("d");
        writer.println(textFieldDeleteIndex.getText());
    }

    @FXML
    private void setButtonStore() {
        writer.println("ls");
        writer.println("s");
    }

    @FXML
    private void setButtonLoad() {
        writer.println("ls");
        writer.println("l");
    }

    @FXML
    private void setButtonSwap() {
        writer.println("ls");
        writer.println("sw");
        writer.println(textFieldSwapFrom.getText());
        writer.println(textFieldSwapTo.getText());
    }

    private void updateCakes() {
        try {
            cakesCache = (Cake[]) objectInputStreamCakes.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        Platform.runLater(() -> {
            tableViewAllCakes.getItems().clear();
            for (int i = 0; i < cakesCache.length; i++) {
                if (cakesCache[i] != null) {
                    CakeDataModel model = new CakeDataModel(
                            new SimpleIntegerProperty(i),
                            new SimpleStringProperty(cakesCache[i].getManufacturerName()),
                            new SimpleLongProperty(cakesCache[i].getShelfLife().toDays()),
                            new SimpleStringProperty(cakesCache[i].getCakeType()));
                    tableViewAllCakes.getItems().add(model);
                } else {
                    CakeDataModel model = new CakeDataModel(
                            new SimpleIntegerProperty(i),
                            new SimpleStringProperty(),
                            new SimpleLongProperty(),
                            new SimpleStringProperty());
                    tableViewAllCakes.getItems().add(model);
                }
            }
        });
    }

    private void updateManufacturers() {
        try {
            manufacturersCache = (List<Manufacturer>) objectInputStreamManufacturers.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        Platform.runLater(() -> {
            choiceBoxManufacturerNames.getItems().clear();
            listViewAllManufacturers.getItems().clear();
            for (Manufacturer manufacturer : manufacturersCache) {
                listViewAllManufacturers.getItems().add(manufacturer.getName());
                choiceBoxManufacturerNames.getItems().add(manufacturer.getName());
            }
        });
    }
}

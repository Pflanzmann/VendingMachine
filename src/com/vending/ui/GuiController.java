package com.vending.ui;

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
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;

public class GuiController implements Initializable {

    @FXML
    private TextField textViewManufacturerInput;
    @FXML
    private Button buttonAddManufacturer;

    @FXML
    private Button buttonFinishCake;
    @FXML
    private Button buttonAddPeanutCovering;
    @FXML
    private Button buttonAddHazelnutCovering;
    @FXML
    private Button buttonResetCake;
    @FXML
    private ChoiceBox<String> choiceBoxManufacturerNames;

    @FXML
    private TextField textFieldDeleteIndex;
    @FXML
    private Button buttonDeleteCake;

    @FXML
    private Button buttonStore;
    @FXML
    private Button buttonLoad;

    @FXML
    private ListView listViewAllManufacturers;
    @FXML
    private ListView listViewAllCakes;

    private PrintStream writer;
    private ObjectInputStream objectInputStreamCakes;
    private ObjectInputStream objectInputStreamManufacturers;

    private List<CoveringType> coveringList = new ArrayList<>();
    private Cake[] cakesCache;
    private List<Manufacturer> manufacturersCache;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
    public void setButtonAddManufacturer() {
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

    private void updateCakes() {
        try {
            cakesCache = (Cake[]) objectInputStreamCakes.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        Platform.runLater(() -> {
            listViewAllCakes.getItems().clear();
            for (int i = 0; i < cakesCache.length; i++) {
                if (cakesCache[i] != null)
                    listViewAllCakes.getItems().add(i + " | " + cakesCache[i].getCakeType());
                else
                    listViewAllCakes.getItems().add(i + " | empty");
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

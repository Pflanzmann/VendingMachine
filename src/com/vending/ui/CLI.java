package com.vending.ui;

import com.vending.models.Allergen;
import com.vending.models.Manufacturer;
import com.vending.models.SerializableAction;
import com.vending.models.cakes.Cake;
import com.vending.models.cakes.CakeBasis;
import com.vending.ui.events.EventHandler;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.EnumSet;
import java.util.Scanner;

import javafx.util.Pair;


public class CLI {

    public CLI(InputStream reader, PrintStream writer, EventHandler<Cake> addCakeEventHandler, EventHandler<Manufacturer> addManufacturerEventHandler, EventHandler<Integer> deleteCakeEventHandler, EventHandler<SerializableAction> loadOrStoreEventHandler, EventHandler<Pair<Integer, Integer>> swapSlotsEventHandler) {
        this.scanner = new Scanner(reader);
        this.writer = writer;
        this.addCakeEventHandler = addCakeEventHandler;
        this.addManufacturerEventHandler = addManufacturerEventHandler;
        this.deleteCakeEventHandler = deleteCakeEventHandler;
        this.loadOrStoreEventHandler = loadOrStoreEventHandler;
        this.swapSlotsEventHandler = swapSlotsEventHandler;
    }

    private Scanner scanner;
    private PrintStream writer;
    private EventHandler<Cake> addCakeEventHandler;
    private EventHandler<Manufacturer> addManufacturerEventHandler;
    private EventHandler<Integer> deleteCakeEventHandler;
    private EventHandler<SerializableAction> loadOrStoreEventHandler;
    private EventHandler<Pair<Integer, Integer>> swapSlotsEventHandler;

    private Integer currentSlotCount = 0;
    private EnumSet<Allergen> currentAllergens = EnumSet.noneOf(Allergen.class);
    private String currentTitle = "";
    private String currentMenu = "";
    private String latestResult = "";
    private String latestCakes = "";
    private String latestManufacturers = "";

    private final String INVALID_INPUT_MESSAGE = "This is not a valid input";

    public void setCurrentSlotCount(Integer slots) {
        currentSlotCount = slots;
    }

    public void setCurrentAllergens(EnumSet<Allergen> allergens) {
        currentAllergens = allergens;
    }

    public void setLatestCakesString(String result) {
        latestCakes = result;
    }

    public void setLatestManufacturersString(String result) {
        latestManufacturers = result;
    }

    public void start() {
        while (true) {
            currentTitle = "Main menu";
            currentMenu = "What do you want to do?";
            currentMenu += "\n[a] -> add";
            currentMenu += "\n[d] -> delete";
            currentMenu += "\n[s] -> show";
            currentMenu += "\n[ls] -> load or store";
            currentMenu += "\n[x] -> exit";
            printScreen();

            String input = scanner.nextLine();

            switch (input) {
                case "a":
                    addMode();
                    break;

                case "d":
                    deleteMode();
                    break;

                case "s":
                    showMode();
                    break;

                case "ls":
                    storageMode();
                    break;

                case "x":
                    return;

                default:
                    latestResult = INVALID_INPUT_MESSAGE;
                    break;
            }
        }
    }

    private void addMode() {
        while (true) {
            currentTitle = "Add mode";
            currentMenu = "What do you want to add?";
            currentMenu += "\n[c] -> cake";
            currentMenu += "\n[m] -> manufacturer";
            currentMenu += "\n[x] -> exit mode";
            printScreen();

            String input = scanner.nextLine();

            switch (input) {
                case "c":
                    Cake cake = buildCake();
                    if (cake != null) {
                        addCakeEventHandler.invoke(cake);
                        latestResult = "Cake got created";
                    }
                    return;

                case "m":
                    currentTitle = "Create manufacturer";
                    currentMenu = "What is the manufacturers name?";
                    currentMenu += "\n[x] -> exit mode";
                    printScreen();
                    String manufacturerName = scanner.nextLine();
                    if (manufacturerName.equals("x"))
                        return;
                    addManufacturerEventHandler.invoke(new Manufacturer(manufacturerName));
                    latestResult = "Manufacturer got created";
                    return;

                case "x":
                    return;

                default:
                    latestResult = INVALID_INPUT_MESSAGE;
                    break;
            }
        }
    }

    private Cake buildCake() {
        currentTitle = "Create cake";
        currentMenu = "What is the manufacturers name?";
        printScreen();

        String factoryName = scanner.nextLine();
        Cake cakeToAdd = new CakeBasis(factoryName);

        while (true) {
            currentMenu = "What do you want to add?";
            currentMenu += "\n[p] -> peanut covering";
            currentMenu += "\n[h] -> hazlenut covering";
            currentMenu += "\n[f] -> finished";
            currentMenu += "\n[x] -> exit mode";
            printScreen();

            String adding = scanner.nextLine();

            switch (adding) {
                case "p":
                    cakeToAdd = CakeBasis.CakeFactory.addPeanutCovering(cakeToAdd);
                    break;

                case "h":
                    cakeToAdd = CakeBasis.CakeFactory.addHazelnutCovering(cakeToAdd);
                    break;

                case "f":
                    return cakeToAdd;

                case "x":
                    return null;

                default:
                    latestResult = INVALID_INPUT_MESSAGE;
                    break;
            }
        }
    }

    private void showMode() {
        while (true) {
            currentTitle = "Show mode";
            currentMenu = "What do you want to show?";
            currentMenu += "\n[c] -> all cakes";
            currentMenu += "\n[m] -> all manufacturer";
            currentMenu += "\n[x] -> exit mode";
            printScreen();

            String input = scanner.nextLine();

            switch (input) {
                case "c":
                    latestResult = "Cakes:";
                    latestResult += latestCakes;
                    return;

                case "m":
                    latestResult = "Manufacturers:";
                    latestResult += latestManufacturers;
                    return;

                case "x":
                    return;

                default:
                    latestResult = INVALID_INPUT_MESSAGE;
                    break;
            }
        }
    }

    private void deleteMode() {
        currentTitle = "Delete mode";
        currentMenu = "Which slot do you want to empty?";
        currentMenu += "\n[x] -> exit";
        latestResult = "";
        printScreen();

        String input = scanner.nextLine();

        if (input.equals("x"))
            return;

        int index = -1;
        try {
            index = Integer.parseInt(input);
        } catch (Exception e) {
            latestResult = INVALID_INPUT_MESSAGE + ": " + input;
            return;
        }
        latestResult = "The slot is now empty";
        deleteCakeEventHandler.invoke(index);
    }


    private void storageMode() {
        while (true) {
            currentTitle = "Load or Store mode";
            currentMenu = "What do you want to do?";
            currentMenu += "\n[l] -> load";
            currentMenu += "\n[s] -> store";
            currentMenu += "\n[sw] -> swap";
            currentMenu += "\n[x] -> exit";
            printScreen();


            String input = scanner.nextLine();

            switch (input) {
                case "l":
                    loadOrStoreEventHandler.invoke(SerializableAction.LOAD);
                    latestResult = "The vending machine got loaded";
                    return;

                case "s":
                    loadOrStoreEventHandler.invoke(SerializableAction.STORE);
                    latestResult = "The vending machine got stored";
                    return;

                case "sw":
                    swapMode();
                    return;

                case "x":
                    return;

                default:
                    latestResult = INVALID_INPUT_MESSAGE;
                    break;
            }
        }
    }

    private void swapMode() {
        currentTitle = "Delete mode";
        currentMenu = "Which slots do you want to swap?";
        currentMenu += "\n[x] -> exit";
        latestResult = "";
        printScreen();

        String input1 = scanner.nextLine();
        String input2 = scanner.nextLine();

        if (input1.equals("x") || input2.equals("x"))
            return;

        int origin = -1;
        int destination = -1;

        try {
            origin = Integer.parseInt(input1);
            destination = Integer.parseInt(input2);
        } catch (Exception e) {
            latestResult = INVALID_INPUT_MESSAGE + ": " + input1 + " or " + input2;
            return;
        }
        latestResult = "The slots got swapped";
        swapSlotsEventHandler.invoke(new Pair(origin, destination));
    }

    private void printScreen() {
        writer.println("\n\n\n\n\n\n\n\n\n\n\n");
        writer.println(latestResult);
        writer.println();
        writer.println("Latest result");
        writer.println("------------------------------------------------------------");
        writer.println(currentTitle);
        writer.println("Slots: [" + currentSlotCount + "] | Current allergens: " + currentAllergens);
        writer.println();
        writer.println(currentMenu);
    }
}

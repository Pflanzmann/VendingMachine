package com.vending.logic;

import com.vending.exceptions.*;
import com.vending.models.Allergen;
import com.vending.models.Manufacturer;
import com.vending.models.cakes.Cake;
import com.vending.ui.EventHandler;
import com.vending.ui.MyObserver;

import java.io.*;
import java.time.Duration;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class VendingMachine implements VendingMachineObservable {

    private EventHandler<ArrayList<Cake>> showAllCakesHandler;
    private EventHandler<List<Manufacturer>> showAllManufacturersHandler;

    private Map<String, Manufacturer> manufacturerList = new HashMap<>();
    private Map<Cake, Date> cakeDate = new HashMap<>();
    private ArrayList<Cake> cakeSlots;

    private EnumSet<Allergen> containingAllergens = EnumSet.noneOf(Allergen.class);

    private int freeSlots;

    List<MyObserver<EnumSet<Allergen>>> observersAllergen = new ArrayList<>();
    List<MyObserver<Integer>> observersSlotCount = new ArrayList<>();

    public VendingMachine(int slots, EventHandler<ArrayList<Cake>> showAllCakesHandler, EventHandler<List<Manufacturer>> showAllManufacturersHandler) {
        this.showAllCakesHandler = showAllCakesHandler;
        this.showAllManufacturersHandler = showAllManufacturersHandler;
        this.freeSlots = slots;
        cakeSlots = new ArrayList<>(slots);
        cakeSlots.ensureCapacity(slots);

        updateAllergenObservable();
        updateSlotCountObservable();
    }

    public void addManufacturer(Manufacturer ManufacturerType) throws ManufacturerAlreadyExistsException {
        if (manufacturerList.containsKey(ManufacturerType.getName()))
            throw new ManufacturerAlreadyExistsException();

        manufacturerList.put(ManufacturerType.getName(), ManufacturerType);
        showAllManufacturersHandler.invoke(new ArrayList<>(manufacturerList.values()));
    }

    public Manufacturer getManufacturerFromName(String ManufacturerTypeName) throws ManufacturerNotFoundException {
        if (!manufacturerList.containsKey(ManufacturerTypeName)) {
            throw new ManufacturerNotFoundException();
        }
        return manufacturerList.get(ManufacturerTypeName);
    }

    public int addCakeGetIndex(Cake cake) throws ManufacturerNotFoundException, NoSpaceException, ContainsCakeException {
        if (!manufacturerList.containsKey(cake.getManufacturerName())) {
            throw new ManufacturerNotFoundException();

        } else if (cakeSlots.contains(cake)) {
            throw new ContainsCakeException();

        } else if (freeSlots <= 0) {
            throw new NoSpaceException();

        } else {
            freeSlots--;
            cakeSlots.add(cake);
            cakeDate.put(cake, new Date());
            containingAllergens.addAll(cake.getAllergens());

            updateSlotCountObservable();
            updateAllergenObservable();
            showAllCakesHandler.invoke(cakeSlots);
            return cakeSlots.indexOf(cake);
        }
    }

    public ArrayList<Cake> getAllCakes() {
        return new ArrayList<>(cakeSlots);
    }

    public long getCakeCountFromManufacturerName(String ManufacturerName) {
        return cakeSlots.stream().filter(x -> x.getManufacturerName().equals(ManufacturerName)).count();
    }

    public List<Cake> searchFor(Predicate<Cake> predicate) {
        return cakeSlots.stream().filter(predicate).collect(Collectors.toList());
    }

    public Cake getCakeFromSlot(int slot) {
        try {
            return cakeSlots.get(slot);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public int getSlotFromCake(Cake cake) throws NoCakeFoundException {
        if (!cakeSlots.contains(cake))
            throw new NoCakeFoundException();

        return cakeSlots.indexOf(cake);
    }

    public Duration getRestShelfLifeFromCake(Cake Cake) {
        Duration duration = Cake.getShelfLife();
        long diffInSeconds = (new Date().getSeconds() - cakeDate.get(Cake).getSeconds());
        duration = duration.minusSeconds(diffInSeconds);
        return duration;
    }

    public void removeCake(Cake cake) {
        cakeDate.remove(cake);
        cakeSlots.remove(cake);

        freeSlots++;
        updateSlotCountObservable();

        containingAllergens.removeAll(cake.getAllergens());
        reCalculateAllAllergens();

        updateSlotCountObservable();
        updateAllergenObservable();

        showAllCakesHandler.invoke(cakeSlots);
    }

    public EnumSet<Allergen> getAllAllergens() {
        return containingAllergens;
    }

    public EnumSet<Allergen> getMissingAllergens() {
        return EnumSet.complementOf(containingAllergens);
    }

    private void reCalculateAllAllergens() {
        for (Cake cake : cakeSlots) {
            containingAllergens.addAll(cake.getAllergens());
        }
    }

    private void updateAllergenObservable() {
        for (MyObserver observer : observersAllergen) {
            observer.handle(containingAllergens);
        }
    }

    private void updateSlotCountObservable() {
        for (MyObserver observer : observersSlotCount) {
            observer.handle(freeSlots);
        }
    }

    @Override
    public void addObserverAllergens(MyObserver<EnumSet<Allergen>> observer) {
        observersAllergen.add(observer);
        updateAllergenObservable();
    }

    @Override
    public void addObserverSlotCount(MyObserver<Integer> observer) {
        observersSlotCount.add(observer);
        updateSlotCountObservable();
    }

    public void serialize(OutputStream fileOutputStream) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        Object[] allLists = new Object[]{manufacturerList, cakeSlots, cakeDate};

        objectOutputStream.writeObject(allLists);
    }

    public void deserialize(InputStream fileInputStream) throws IOException, ClassNotFoundException {
        ObjectInputStream inO = new ObjectInputStream(fileInputStream);

        Object[] allLists = (Object[]) inO.readObject();

        this.manufacturerList = (HashMap<String, Manufacturer>) allLists[0];
        this.cakeSlots = (ArrayList<Cake>) allLists[1];
        this.cakeDate = (HashMap<Cake, Date>) allLists[2];

        reCalculateAllAllergens();
        updateAllergenObservable();
        updateSlotCountObservable();
        showAllCakesHandler.invoke(cakeSlots);
        showAllManufacturersHandler.invoke(new ArrayList<>(manufacturerList.values()));
    }
}

package com.vending.logic;

import com.vending.exceptions.*;
import com.vending.models.Allergen;
import com.vending.models.Manufacturer;
import com.vending.models.cakes.Cake;
import com.vending.ui.event.EventHandler;
import com.vending.ui.observer.MyObserver;
import com.vending.ui.observer.VendingMachineObservable;

import java.io.*;
import java.time.Duration;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class VendingMachine implements VendingMachineObservable {

    private EventHandler<Cake[]> showAllCakesHandler;
    private EventHandler<List<Manufacturer>> showAllManufacturersHandler;

    private Map<String, Manufacturer> manufacturerList = new HashMap<>();
    private Map<Cake, Date> cakeDate = new HashMap<>();
    private Cake[] cakeSlots;

    private EnumSet<Allergen> containingAllergens = EnumSet.noneOf(Allergen.class);

    private int freeSlots;
    private int maxSlots;

    List<MyObserver<EnumSet<Allergen>>> observersAllergen = new ArrayList<>();
    List<MyObserver<Integer>> observersSlotCount = new ArrayList<>();

    public VendingMachine(int slots, EventHandler<Cake[]> showAllCakesHandler, EventHandler<List<Manufacturer>> showAllManufacturersHandler) {
        this.showAllCakesHandler = showAllCakesHandler;
        this.showAllManufacturersHandler = showAllManufacturersHandler;
        cakeSlots = new Cake[slots];
        this.freeSlots = slots;
        this.maxSlots = slots;

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

    public synchronized int addCakeGetIndex(Cake cake) throws ManufacturerNotFoundException, NoSpaceException, ContainsCakeException {
        if (!manufacturerList.containsKey(cake.getManufacturerName())) {
            throw new ManufacturerNotFoundException();

        } else if (containsCake(cake)) {
            throw new ContainsCakeException();

        } else if (freeSlots <= 0) {
            throw new NoSpaceException();

        } else {
            for (int i = 0; i < cakeSlots.length; i++) {
                if (cakeSlots[i] == null) {
                    cakeSlots[i] = cake;
                    break;
                }
            }

            cakeDate.put(cake, new Date());

            reCalculateAllAllergens();

            updateSlotCountObservable();
            updateAllergenObservable();

            showAllCakesHandler.invoke(cakeSlots.clone());

            try {
                return getSlotFromCake(cake);
            } catch (NoCakeFoundException e) {
                return -1;
            }
        }
    }

    public synchronized Cake[] getAllCakes() {
        return cakeSlots;
    }

    public long getCakeCountFromManufacturerName(String ManufacturerName) {
        return Arrays.stream(cakeSlots).filter(x -> x.getManufacturerName().equals(ManufacturerName)).count();
    }

    public List<Cake> searchForAll(Predicate<Cake> predicate) {
        return Arrays.stream(cakeSlots).filter(predicate).collect(Collectors.toList());
    }

    public Cake getCakeFromSlot(int slot) {
        try {
            return cakeSlots[slot];
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public int getSlotFromCake(Cake cake) throws NoCakeFoundException {
        for (int i = 0; i < cakeSlots.length; i++) {
            if (cakeSlots[i] != null && cakeSlots[i] == cake)
                return i;
        }
        throw new NoCakeFoundException();
    }

    public Duration getRestShelfLifeFromCake(Cake Cake) {
        Duration duration = Cake.getShelfLife();
        long diffInSeconds = (new Date().getSeconds() - cakeDate.get(Cake).getSeconds());
        duration = duration.minusSeconds(diffInSeconds);
        return duration;
    }

    public synchronized void removeCake(Cake cake) {
        cakeDate.remove(cake);

        for (int i = 0; i < cakeSlots.length; i++)
            if (cakeSlots[i] == cake)
                cakeSlots[i] = null;

        reCalculateAllAllergens();

        updateSlotCountObservable();
        updateAllergenObservable();

        showAllCakesHandler.invoke(cakeSlots.clone());
    }

    public EnumSet<Allergen> getAllAllergens() {
        return containingAllergens;
    }

    public EnumSet<Allergen> getMissingAllergens() {
        return EnumSet.complementOf(containingAllergens);
    }

    private boolean containsCake(Cake cake) {
        for (int i = 0; i < cakeSlots.length; i++)
            if (cakeSlots[i] != null && cakeSlots[i] == cake)
                return true;

        return false;
    }

    private synchronized void reCalculateAllAllergens() {
        freeSlots = maxSlots;
        containingAllergens = EnumSet.noneOf(Allergen.class);

        for (int i = 0; i < cakeSlots.length; i++)
            if (cakeSlots[i] != null) {
                containingAllergens.addAll(cakeSlots[i].getAllergens());
                freeSlots--;
            }
    }

    private void updateAllergenObservable() {
        for (MyObserver<EnumSet<Allergen>> observer : observersAllergen) {
            observer.handle(containingAllergens);
        }
    }

    private void updateSlotCountObservable() {
        for (MyObserver<Integer> observer : observersSlotCount) {
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

    public synchronized void serialize(OutputStream fileOutputStream) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        Object[] allLists = new Object[]{manufacturerList, cakeSlots, cakeDate};

        objectOutputStream.writeObject(allLists);
    }

    public synchronized void deserialize(InputStream fileInputStream) throws IOException, ClassNotFoundException {
        ObjectInputStream inO = new ObjectInputStream(fileInputStream);

        Object[] allLists = (Object[]) inO.readObject();

        this.manufacturerList = (HashMap<String, Manufacturer>) allLists[0];
        this.cakeSlots = (Cake[]) allLists[1];
        this.cakeDate = (HashMap<Cake, Date>) allLists[2];

        reCalculateAllAllergens();
        updateAllergenObservable();
        updateSlotCountObservable();
        showAllCakesHandler.invoke(cakeSlots.clone());
        showAllManufacturersHandler.invoke(new ArrayList<>(manufacturerList.values()));
    }
}

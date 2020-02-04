package Tests;

import com.company.Database.VerkaufsautomatSpeicherung;
import com.company.exception.*;
import com.company.logic.Verkaufsautomat;
import com.company.models.kuchen.*;
import com.company.models.mitarbeiter.Hersteller;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class VerkaufsautomatTest {

    private final int PLAETZE = 3;
    private final String TEST_HERSTELLER_NAME1 = "Nike";
    private final String TEST_HERSTELLER_NAME2 = "Apple";
    private final String TEST_Wrong_NAME = "Wrong";
    private final long TEST_HALTBARKEIT = 10;

    private Verkaufsautomat testObj;

    private Hersteller her1;
    private Hersteller her2;

    private Kuchen kuchen1;
    private Kuchen kuchen2;
    private Kuchen kuchen3;
    private Kuchen kuchen4;


    @BeforeEach
    public void setUp() {
        testObj = new Verkaufsautomat(PLAETZE);

        her1 = Mockito.mock(Hersteller.class);

        kuchen1 = Mockito.mock(Obstkuchen.class);
        kuchen2 = Mockito.mock(Kremkuchen.class);
        kuchen3 = Mockito.mock(Obsttorte.class);
        kuchen4 = Mockito.mock(Kuchen.class);

        Mockito.when(her1.getName()).thenReturn(TEST_HERSTELLER_NAME1);

        Mockito.when(kuchen1.getHersteller()).thenReturn(her1);
        Mockito.when(kuchen2.getHersteller()).thenReturn(her1);
        Mockito.when(kuchen3.getHersteller()).thenReturn(her1);
        Mockito.when(kuchen4.getHersteller()).thenReturn(her1);
    }


    @org.junit.jupiter.api.Test
    void addHersteller() {
        her2 = Mockito.mock(Hersteller.class);

        //Fügt zwei Hersteller hinzu
        //Keine Exception erwartet
        Assertions.assertDoesNotThrow(() -> {
            testObj.addHersteller(her1);
            testObj.addHersteller(her2);
        });

        //Fügt Hersteller eins erneut hinzu
        //HerstellerExistsException erwartet
        assertThrows(HerstellerExistsException.class, () -> {
            testObj.addHersteller(her1);
        });
    }

    @org.junit.jupiter.api.Test
    void getHerstellerFromName() {

        //Fügt Hersteller hinzu und testet getHerstellerFromName
        //Keine Exception erwartet
        Assertions.assertDoesNotThrow(() -> {
            testObj.addHersteller(her1);
            assertTrue(testObj.getHerstellerFromName(TEST_HERSTELLER_NAME1) == her1);
        });

        //Testet getHerstellerFromName mit einem nicht vorhandenen Namen
        //NoHerstellerAvailableException erwratet
        assertThrows(NoHerstellerAvailableException.class, () -> {
            testObj.getHerstellerFromName(TEST_Wrong_NAME);
        });
    }

    @org.junit.jupiter.api.Test
    void addKuchenGetFachnummer() {

        //Fügt Kuchen ohne Hersteller hinzufügen
        //NoHerstellerAvailableException erwartet
        Assertions.assertThrows(NoHerstellerAvailableException.class, () -> {
            testObj.addKuchenGetFachnummer(kuchen1);
        });

        //Fügt Kuchen mit Hersteller hinzufügen
        //Prüft ob Obstkuchen, Obsttorte und Kremkuchen als Kuchen zählen
        //Keine Exception erwartet
        Assertions.assertDoesNotThrow(() -> {
            testObj.addHersteller(her1);
            testObj.addKuchenGetFachnummer(kuchen1);
            testObj.addKuchenGetFachnummer(kuchen2);
            testObj.addKuchenGetFachnummer(kuchen3);
        });

        //Fügt zu viele Kuchen hinzu
        //NoSpaceException erwartet
        Assertions.assertThrows(NoSpaceException.class, () -> {
            testObj.addKuchenGetFachnummer(kuchen4);
        });

        //Fügt eine Kucheninstanz ein zweites Mal hinzu
        //ContainsKuchenException erwartet
        Assertions.assertThrows(ContainsKuchenException.class, () -> {
            testObj.addKuchenGetFachnummer(kuchen1);
        });
    }

    @org.junit.jupiter.api.Test
    void getAllKuchen() {

        //Fügt Kuchen mit Hersteller hinzufügen
        //Keine Exception erwartet
        Assertions.assertDoesNotThrow(() -> {
            testObj.addHersteller(her1);
            testObj.addKuchenGetFachnummer(kuchen1);
            testObj.addKuchenGetFachnummer(kuchen2);
        });

        //Testet getAllKuchen und ob alle hinzugefügten Kuchen enthalten sind
        ArrayList<Kuchen> list = testObj.getAllKuchen();
        assertTrue(list.contains(kuchen1));
        assertTrue(list.contains(kuchen2));
    }

    @org.junit.jupiter.api.Test
    void getKuchenfromType() {

        //Fügt Kuchen mit Hersteller hinzufügen
        //Keine Exception erwartet
        Assertions.assertDoesNotThrow(() -> {
            testObj.addHersteller(her1);
            testObj.addKuchenGetFachnummer(kuchen1);
            testObj.addKuchenGetFachnummer(kuchen2);
            testObj.addKuchenGetFachnummer(kuchen3);
        });

        //Testet getKuchenfromType und ob alle ausgegeben Kuchen vom passenden Typ sind
        ArrayList<Kuchen> list = testObj.getKuchenfromType(true, false);
        assertTrue(list.contains(kuchen2));
        assertTrue(list.contains(kuchen3));

        //Kuchen1 als Obstkuchen bekommen und Kuchen 3 als Obsttorte
        list = testObj.getKuchenfromType(false, true);
        assertTrue(list.contains(kuchen1));
        assertTrue(list.contains(kuchen3));
    }

    @org.junit.jupiter.api.Test
    void getKuchenCountFromHersteller() {

        her2 = Mockito.mock(Hersteller.class);
        Mockito.when(her2.getName()).thenReturn(TEST_HERSTELLER_NAME2);

        Mockito.when(kuchen3.getHersteller()).thenReturn(her2);


        //Fügt stätig Kuchen mit Hersteller hinzufügen
        //Testet ob die Anzahl der Kuchen der jeweiligen Hersteller stimmt
        //Keine Exception erwartet
        Assertions.assertDoesNotThrow(() -> {
            testObj.addHersteller(her1);
            testObj.addHersteller(her2);

            testObj.addKuchenGetFachnummer(kuchen1);
            assertEquals(1, testObj.getKuchenCountFromHersteller(her1));

            testObj.addKuchenGetFachnummer(kuchen2);
            assertEquals(2, testObj.getKuchenCountFromHersteller(her1));

            testObj.addKuchenGetFachnummer(kuchen3);
            assertEquals(2, testObj.getKuchenCountFromHersteller(her1));
        });
    }

    @org.junit.jupiter.api.Test
    void getKuchenfromFachnummer() {

        //Fügt Kuchen mit Hersteller hinzufügen
        //Testet ob die Kuchen mit der passenden Fachnummer ausgegeben werden
        //Keine Exception erwartet
        Assertions.assertDoesNotThrow(() -> {
            testObj.addHersteller(her1);
            int num1 = testObj.addKuchenGetFachnummer(kuchen1);
            int num2 = testObj.addKuchenGetFachnummer(kuchen2);

            assertEquals(kuchen1, testObj.getKuchenfromFachnummer(num1));
            assertEquals(kuchen2, testObj.getKuchenfromFachnummer(num2));
        });

        //sucht nach nicht vorhandener Kuchenfachnummer
        //NoKuchenFoundException erwartet
        Assertions.assertThrows(NoKuchenFoundException.class, () -> {
            testObj.getKuchenfromFachnummer(42);
        });
    }

    @org.junit.jupiter.api.Test
    void getRestHalbarkeitFromKuchen() {
        Mockito.when(kuchen1.getHaltbarkeit()).thenReturn(Duration.ofSeconds(TEST_HALTBARKEIT));

        //Fügt Kuchen mit Hersteller hinzufügen
        //wartet eine Sekunde um die Haltbarkeit zu prüfen
        //Keine Exception erwartet
        Assertions.assertDoesNotThrow(() -> {
            testObj.addHersteller(her1);
            testObj.addKuchenGetFachnummer(kuchen1);

            Thread.sleep(1000);
        });

        //Berechnet die RestHaltbarkeit
        assertEquals(9, testObj.getRestHalbarkeitFromKuchen(kuchen1).getSeconds());
    }

    @org.junit.jupiter.api.Test
    void removeKuchen() {

        //Fügt Kuchen mit Hersteller hinzufügen
        //Entfernt einen Kuchen davon wieder
        //Keine Exception erwartet
        Assertions.assertDoesNotThrow(() -> {
            testObj.addHersteller(her1);
            testObj.addKuchenGetFachnummer(kuchen1);
            testObj.addKuchenGetFachnummer(kuchen2);

            testObj.removeKuchen(kuchen1);
        });

        //Versucht nicht vorhandenen Kuchen zu entfernen
        //NoKuchenFoundException erwartet
        Assertions.assertThrows(NoKuchenFoundException.class, () -> {
            testObj.removeKuchen(kuchen1);
        });
    }

    @org.junit.jupiter.api.Test
    void getAllAllergene() {
        ArrayList<Allergen> list1 = new ArrayList<Allergen>();
        list1.add(Allergen.Erdnuss);
        list1.add(Allergen.Gluten);

        ArrayList<Allergen> list2 = new ArrayList<Allergen>();
        list1.add(Allergen.Erdnuss);
        list1.add(Allergen.Sesamsamen);

        Mockito.when(kuchen1.getAllergene()).thenReturn(list1);
        Mockito.when(kuchen2.getAllergene()).thenReturn(list2);

        //Fügt Kuchen mit Hersteller hinzufügen
        //Keine Exception erwartet
        Assertions.assertDoesNotThrow(() -> {
            testObj.addHersteller(her1);
            testObj.addKuchenGetFachnummer(kuchen1);
            testObj.addKuchenGetFachnummer(kuchen2);
        });

        //Prüft ob alle benötigten Allergene vorhanden sind
        ArrayList<Allergen> resultList1 = testObj.getAllAllergene();
        assertTrue(resultList1.contains(Allergen.Erdnuss));
        assertTrue(resultList1.contains(Allergen.Gluten));
        assertTrue(resultList1.contains(Allergen.Sesamsamen));
        assertFalse(resultList1.contains(Allergen.Haselnuss));
    }

    @org.junit.jupiter.api.Test
    void getMissingAllergene() {
        ArrayList<Allergen> list1 = new ArrayList<Allergen>();
        list1.add(Allergen.Erdnuss);
        list1.add(Allergen.Gluten);

        ArrayList<Allergen> list2 = new ArrayList<Allergen>();
        list1.add(Allergen.Erdnuss);
        list1.add(Allergen.Sesamsamen);

        Mockito.when(kuchen1.getAllergene()).thenReturn(list1);
        Mockito.when(kuchen2.getAllergene()).thenReturn(list2);

        //Fügt Kuchen mit Hersteller hinzufügen
        //Keine Exception erwartet
        Assertions.assertDoesNotThrow(() -> {
            testObj.addHersteller(her1);
            testObj.addKuchenGetFachnummer(kuchen1);
            testObj.addKuchenGetFachnummer(kuchen2);
        });

        //Prüft ob alle benötigten Allergene vorhanden sind
        ArrayList<Allergen> resultList1 = testObj.getMissingAllergene();
        assertFalse(resultList1.contains(Allergen.Erdnuss));
        assertFalse(resultList1.contains(Allergen.Gluten));
        assertFalse(resultList1.contains(Allergen.Sesamsamen));
        assertTrue(resultList1.contains(Allergen.Haselnuss));
    }
}
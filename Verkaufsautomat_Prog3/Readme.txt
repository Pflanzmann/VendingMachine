s0569420
Ronny Brzeski

Programmieren 3 - Verkaufsautomat

Erreichter Stand Aufgabe 6
- Geschäftslogik und Ui kommuniziern über die UI
- Die Klasse GUI_With_Streams und die Klasse LogikController repräsentieren die Hausaufgaben
- Da in der Aufgabe stand, dass wir Übung 2 oder 4 nachstellen sollen habe ich die Übung 2 genommen
- Aus testzwecken habe ich den LogikController in der GUI_With_Streams gestartet, da es mir auf die schnelle anders
nicht möglich war
- Der LogikController ist die CLI der vorherigen Übungen, mit minimalen anpassungen
- Die UI komuniziert mit der Logik indem sie Befehle wie "add manufacturer" oder "store" an diese sendet
- Der LogikController würde genauso auch mit einem printstream funktionieren
- Die UI erhält über einen ObjectStream alle Kuchen, welche dann angezeigt werden
- Store und Load sind auch möglich und funktionieren
-
- Ich weiß, dass das keine schöne Abgabe ist, aber ich hoffe sie können drüber hinweg sehen,
da ich neben dem Arbeiten auch noch für 2 Klausuren am 20/21.1.2020 lernen muss und somit wieder nicht all meine Energie
aufwenden konnte.



---------------------------------------------------------------------------------------

Erreichter Stand Aufgabe 5
- Verkaufsautomaten werden persistent gespeichert und können auch wieder geladen werden
- VA werden in "Verkaufsautomaten.txt" gespeichert
- Tests sind nicht vorhanden, weil sie nicht gefordert waren für 1 Punkt
- zum probieren der funktionalität des "Speichern der Geschäftslogik" ist eine neue CLI vorhanden



---------------------------------------------------------------------------------------

Erreichter Stand Aufgabe 4
- Kuchen werden eingefügt und entfernt von einem Table
- Kein Produktionscode in dem GUI element
- Verkaufsautomat wurde nicht eingebunden, da bei der Aufgabe 2 nur "hersteller einfügen" und "Kuchen einfügen"
als Events definiert wurden und ich deswegen neue events hinzufügen müsste um den Verkaufsautomaten perfekt einzubinden


---------------------------------------------------------------------------------------

Erreichter Stand Aufgabe 3
- Simulation der Einlagerung über eine Einlagerungsthread
- Einlagerungsthread fügt über Events dem Verkausautomaten einen Hersteller hinzu
- Einlagerungsthread wählt einen zufälligen von 3 Kuchen
- Einlagerungsthread fügt über Events dem Verkaufsautomaten den zufällig gewählten Kuchen hinzu
- Einlagerungsthread gibt auf über den Output aus, was für Kuchen hinzugefügt wurde
- Einlagerungsthread wartet 3 Sekunden
- Einlagerungsthread beginnt von vorne
- Sollte der Verkaufsautomat voll sein wird eine Exception ausgegeben, aber nicht gestoppt


---------------------------------------------------------------------------------------

Erreichter Stand Aufgabe 2
- Darstellungslog ist von der Geschaeftslogik getrennt
- Kommunikation der CLI mit der Logik über Events
- Prototypische CLI Loesung implementiert
- Hinzufuegen eines Herstellers
- Hinzufuegen eines vordefinierten Kremkuchens
- Hinzufuegen eines vordefinierten Obstkuchens

- Da Punkt 7. aussagt "Beobachterentwurfsmuster und events realisiert" bin ich davon ausgegangen,
dass dies fuer 1 Punkt nicht noetig sei.
- Da keine Tests der CLI erwaehnt wurden habe ich diese nicht gemacht.

Anmerkung:
Auf Grund eines Zeitmangels und meiner fehlenden zweiten Praesenzaufgabe habe ich mich entschieden, die Events nur
dürftig mit einzubeziehen, da dies auch nicht explizit erwaehnt wurde fuer einen Punkt. Sollte dies ein Problem sein,
kann ich dies gerne nachreichen oder zur Aufgabe 3 mit hinzufuegen.

Ich bitte um Entschuldigung für diese schnell zusammengepfuschte Aufgabe.


---------------------------------------------------------------------------------------

Erreichter Stand Aufgabe 1:
- JUnit5 und Java8 wurden benutzt
- Tests sind von der Logik abgekapselt
- alle geforderten Funktionalitaeten sind im Verkaufsautomat enthalten
- alle geforderten Funktionalitaeten des Verkaufsautomats sind auf jeden Fall getestet
- viele Funktionen haben die Moeglichkeit eine Exception zu werfen, welche auch getestet werden
- alle Tests haben Mockito implementiert
- "don't repeat yourself" wurde bestmoeglichst umgesetzt
- alles was noetig war um die Geschaeftslogik umzusetzen wurde erstellt und getestet
- eine Main Methode wurde nicht implementiert, da explizit nach der Gesch�ftslogik verlangt wurde und dies kein
Teil davon darstellt
- vorgegebene Interfaces wurden nicht getestet da diese vorgegeben waren und somit vom Ursprung der Interfaces
getestet werden sollten
- als Quellen wurden nur die JavaDocs benutzt: "https://docs.oracle.com/javase/8/docs/api/"
- Tests wurden bestmoeglich nach dem Arrange, Act, Assert pattern programmiert
- multiple Tests wurd
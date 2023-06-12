// Autorzy: Julia Kulczycka, Maciej Szałasz
// Nazwa pliku: Main.java
// Data ukończenia: 12.06.2023
// Opis:
// Klasa główna.


package Main;

/** Klasa główna */
public class Main {
    public static void main(String[] args) {
        // Uruchomienie programu
        MainProgram program = MainProgram.getInstance();
        Runtime.getRuntime().addShutdownHook(new Thread(program::saveData));
    }
}

package Main;

/** Klasa główna */
public class Main {
    public static void main(String[] args) {
        // Uruchomienie programu
        MainProgram program = MainProgram.getInstance();
        Runtime.getRuntime().addShutdownHook(new Thread(program::saveData));
    }
}

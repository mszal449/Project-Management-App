import java.sql.SQLException;

// Główna klasa programu
public class MainProgram {
    Window window; // Okno programu
    User logged_user; // Aktualnie zalogowany użytkownik
    DatabaseManager dbManager;

    //TODO: Dane programu


    MainProgram() throws SQLException {
        // połączenie z bazą danych
        dbManager = DatabaseManager.getInstance();
        dbManager.connectToDatabase();
        // Utworzenie głównego okna programu
        window = new Window(this);
    }
}

import javax.swing.*;

// Główna klasa programu
public class MainProgram {
    Window window; // Okno programu

    //TODO: Dane programu
    DefaultListModel<User> users; // lista wszystkich użytkowników
    DefaultListModel<Project> projects; // lista wszystkich projektów

    MainProgram(DefaultListModel<User> users, DefaultListModel<Project> projects) {
        // TODO: Wczytanie danych
        this.users = users;
        this.projects = projects;
        // Utworzenie głównego okna programu
        window = new Window(users, projects);
    }
}

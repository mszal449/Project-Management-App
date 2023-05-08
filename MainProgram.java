import javax.swing.*;

// Główna klasa programu
// zastosowany wzorzec projektowy: https://pl.wikipedia.org/wiki/Singleton_(wzorzec_projektowy)
public class MainProgram {
    private static MainProgram app_instance; // (jedyna) instancja klasy MainProgram
    Window window; // Okno programu
    User logged_user; // aktualnie zalogowany użytkownik
    DefaultListModel<User> users; // lista wszystkich użytkowników
    DefaultListModel<Project> projects; // lista wszystkich projektów

    private MainProgram(DefaultListModel<User> users, DefaultListModel<Project> projects) {
        // Wczytanie danych
        this.users = users;
        this.projects = projects;
        // Utworzenie głównego okna programu
        window = new Window();
    }

    // dostęp lub utworzenie app_instance
    public static MainProgram getInstance(DefaultListModel<User> users, DefaultListModel<Project> projects) {
        if (app_instance == null) {
            app_instance = new MainProgram(users, projects);
        }
        return app_instance;
    }

    // dostęp do zawartości okna
    public static Window getWindow() {
        return app_instance.window;
    }
    public static void setWindow(JPanel scene) {
        app_instance.window.setScene(scene);
    }

    // dostęp do listy użytkowników aplikacji
    public static DefaultListModel<User> getUsers() {
        return app_instance.users;
    }
    public static void addUser(User user) {
        app_instance.users.addElement(user);
    }
    public static void deleteUser(User user) {
        app_instance.users.removeElement(user);
    }

    // dostęp do listy projektów
    public static DefaultListModel<Project> getProjects() {
        return app_instance.projects;
    }
    public static void addProject(Project project) {
        app_instance.projects.addElement(project);
    }
    public static void deleteProject(Project project) {
        app_instance.projects.removeElement(project);
    }

    // dostęp do informacji o aktualnie zalogowanym użytkowniku
    public static User getLoggedUser() {
        return app_instance.logged_user;
    }
    public static void setLoggedUser(User user) {
        app_instance.logged_user = user;
    }
}

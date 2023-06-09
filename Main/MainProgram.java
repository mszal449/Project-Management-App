// Autorzy: Julia Kulczycka, Maciej Szałasz
// Nazwa pliku: MainProgram.java
// Data ukończenia: 12.06.2023
// Opis:
// Główna klasa programu.



package Main;

import Classes.Project;
import Classes.Task;
import Classes.User;

import javax.swing.*;
import java.io.*;

/** Główna klasa programu */
public class MainProgram implements Serializable {

    /** (jedyna) instancja klasy Main.MainProgram */
    private static MainProgram app_instance;
    /** Okno programu */
    private final Window window;
    /** aktualnie zalogowany użytkownik */
    private User logged_user;
    /** lista wszystkich użytkowników */
    private  DefaultListModel<User> users;
    /** lista wszystkich projektów */
    private  DefaultListModel<Project> projects;


    // ---------------    INSTANCJA PROGRAMU    ---------------

    /** konstruktor */
    private MainProgram() {
        users = new DefaultListModel<>();
        projects = new DefaultListModel<>();

        loadData();
        window = new Window();
    }

    /** konstruktor */
    private MainProgram(DefaultListModel<User> users,
                        DefaultListModel<Project> projects) {
        // wczytanie danych
        this.users = users;
        this.projects = projects;
        // utworzenie głównego okna programu
        window = new Window();
    }

    /** dostęp lub utworzenie app_instance */
    public static MainProgram getInstance(DefaultListModel<User> users,
                                          DefaultListModel<Project> projects) {
        if (app_instance == null) {
            app_instance = new MainProgram(users, projects);
        }
        return app_instance;
    }

    /** dostęp lub utworzenie app_instance */
    public static MainProgram getInstance() {
        if (app_instance == null) {
            app_instance = new MainProgram();
        }
        return app_instance;
    }

    /** dostęp do zawartości okna */
    public static Window getWindow() {
        return app_instance.window;
    }
    /** zmiana zawartości okna */
    public static void setWindow(String scene_name, Object ... args) {
        app_instance.window.setScene(scene_name, args);
    }

    // ------------- MODYFIKOWANIE DANYCH PROJEKTU -------------

    /** dodanie użytkownika */
    public static void addUser(User user) {
        app_instance.users.addElement(user);
    }
    /** usunięcie użytkownika */
    public static void deleteUser(User user) {
        app_instance.users.removeElement(user);
    }
    /** dodanie projektu */
    public static void addProject(Project project) {
        app_instance.projects.addElement(project);
    }
    /** usunięcie projektu */
    public static void deleteProject(Project project) {
        app_instance.projects.removeElement(project);
    }
    /** zmiana aktualnie zalogowanego użytkownika */
    public static void setLoggedUser(User user) {
        app_instance.logged_user = user;
    }



    // ---------------  DOSTĘP DO DANYCH   ---------------

    /** dostęp do listy wszystkich użytkowników aplikacji */
    public static DefaultListModel<User> getUsers() {
        return app_instance.users;
    }

    /** dostęp do informacji o aktualnie zalogowanym użytkowniku */
    public static User getLoggedUser() {
        return app_instance.logged_user;
    }

    /** dostęp do listy projektów */
    public static DefaultListModel<Project> getProjects() {
        return app_instance.projects;
    }

    /** dostęp do listy projektów użytkownika */
    public static DefaultListModel<Project> getProjects(User user) {
        DefaultListModel<Project> users_projects = new DefaultListModel<>();
        DefaultListModel<Project> all_projects = getProjects();

        for (int i = 0; i < all_projects.size(); i++) {
            Project current = all_projects.getElementAt(i);
            if (current.getParticipants().containsKey(user)) {
                users_projects.addElement(current);
            }
        }

        return users_projects;
    }

    /** dostęp do listy zadań wszystkich projektów */
    public static DefaultListModel<Task> getTasks() {
        DefaultListModel<Task> tasks = new DefaultListModel<>();
        DefaultListModel<Project> projects = getProjects();

        for (int i = 0; i < projects.size(); i++) {
            DefaultListModel<Task> temp_tasks = projects.getElementAt(i).getTasks();
            for (int j = 0; j < temp_tasks.size(); j++) {
                tasks.addElement(temp_tasks.getElementAt(j));
            }
        }

        return tasks;
    }

    /** dostęp do listy zadań użytkownika */
    public static DefaultListModel<Task> getTasks(User user) {
        // Lista znalezionych zadań użytkownika
        DefaultListModel<Task> users_tasks = new DefaultListModel<>();
        DefaultListModel<Task> all_tasks = getTasks();

        for (int i = 0; i < all_tasks.size(); i++) {
            DefaultListModel<User> task_assignees = all_tasks.getElementAt(i).getAssignees();
            if (task_assignees.contains(user)) {
                users_tasks.addElement(all_tasks.getElementAt(i));
            }
        }
        return users_tasks;
    }


    // ---------------  SERIALIZACJA DANYCH   ---------------

    /** metoda do wczytania danych użytkowników z pliku */
    private void loadData() {
        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream("data.ser"))) {
            app_instance = (MainProgram) in.readObject();
            this.users = app_instance.users;
            this.projects = app_instance.projects;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Nie znaleziono danych");
        }
    }

    /** metoda do zapisywania danych użytkowników i projektów przy zamknięciu aplikacji */
    public void saveData() {
        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream("data.ser"))) {
            out.writeObject(MainProgram.getInstance());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


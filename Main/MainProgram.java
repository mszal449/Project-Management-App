package Main;

import Classes.Project;
import Classes.Task;
import Classes.User;

import javax.swing.*;

// Główna klasa programu
// zastosowany wzorzec projektowy: https://pl.wikipedia.org/wiki/Singleton_(wzorzec_projektowy)
public class MainProgram {
    private static MainProgram app_instance;    // (jedyna) instancja klasy Main.MainProgram
    Window window;                              // Okno programu
    User logged_user;                           // aktualnie zalogowany użytkownik
    Project chosen_project;                     // Aktualny projekt
    DefaultListModel<User> users;               // lista wszystkich użytkowników
    DefaultListModel<Project> projects;         // lista wszystkich projektów

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
    public static void setWindow(String scene_name) {
        app_instance.window.setScene(scene_name);
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


    // dodanie projektu
    public static void addProject(Project project) {
        app_instance.projects.addElement(project);
    }

    // usunięcie projektu
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

    public static Project getChosenProject() {
        return app_instance.chosen_project;
    }

    public static void setChosenProject(Project project) {
        app_instance.chosen_project = project;
    }


    // dostęp do listy projektów
    public static DefaultListModel<Project> getProjects() {
        return app_instance.projects;
    }

    // dostęp do listy projektów użytkownika
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

    // dostęp do listy zadań wszystkich projektów
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

    // dostęp do listy zadań użytkownika
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


}

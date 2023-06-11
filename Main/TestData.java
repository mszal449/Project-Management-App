package Main;

import Classes.*;

import javax.swing.*;
import java.time.LocalDate;

// Aby uruchomić program z danymi testowymi (zamiast tych wczytanych z pliku data.ser)
// należy w metodzie main klasy Main wywołać MainProgram.getInstance(users, projects);
// podając jako argumenty atrybuty klasy TestData.

/** Klasa zawierająca dane testowe */
public class TestData {
    /** Lista projektów istniejących w aplikacji */
    DefaultListModel<Project> test_projects;
    /** Lista użytkowników aplikacji */
    DefaultListModel<User> test_users;

    /** Konstruktor */
    public TestData() {
        test_projects = makeProjects();
        test_users = makeUsers();
        addParticipants();
        addTasks();
        assignTasks();
    }

    /** Utworzenie projektów */
    private DefaultListModel<Project> makeProjects() {
        DefaultListModel<Project> projects = new DefaultListModel<>();
        projects.addElement(new Project("Projekt z programowania obiektowego",
                LocalDate.of(2023, 6, 20)));
        projects.addElement(new Project("Wyjazd wakacyjny",
                LocalDate.of(2023, 10, 1)));
        return projects;
    }

    /** Utworzenie kont użytkowników */
    private DefaultListModel<User> makeUsers() {
        DefaultListModel<User> users = new DefaultListModel<>();
        users.addElement(new User("Test", "test@mail.com", "haslo1"));
        users.addElement(new User("Kasia", "kasia@mail.com", "haslo2"));
        users.addElement(new User("Asia", "asia@mail.com", "haslo3"));
        users.addElement(new User("Lusia", "lusia@mail.com", "haslo4"));
        users.addElement(new User("Basia", "basia@mail.com", "haslo5"));
        return users;
    }

    /** Dodanie użytkowników do projektów */
    private void addParticipants() {
        // projekt, do którego użytkownik Test ma uprawnienia administratora
        test_projects.get(0).addParticipant(test_users.get(0));
        test_projects.get(0).addParticipant(test_users.get(1));
        test_projects.get(0).addParticipant(test_users.get(2));
        test_projects.get(0).addParticipant(test_users.get(3));
        test_projects.get(0).setPrivileges(test_users.get(0), true);
        test_projects.get(0).setPrivileges(test_users.get(1), true);
        // projekt, do którego użytkownik Test nie ma uprawnień
        test_projects.get(1).addParticipant(test_users.get(0));
        test_projects.get(1).addParticipant(test_users.get(2));
        test_projects.get(1).addParticipant(test_users.get(4));
        test_projects.get(0).setPrivileges(test_users.get(2), true);
    }

    /** Dodanie zadań do projektów */
    private void addTasks() {
        // dodanie zadań do pierwszego projektu
        test_projects.get(0).addTask(new Planned("Napisać testy",
                20, 6, 2023, 11, 6, 2023));
        test_projects.get(0).addTask(new Planned("Dokumentacja!",
                20, 6, 2023, 8, 6, 2023));
        test_projects.get(0).addTask(
                new Current("Implementacja funkcjonalności", 30, 5, 2023));
        test_projects.get(0).addTask(new Done("Diagram UML", 14, 5, 2023));
        // dodanie zadań do drugiego projektu
        test_projects.get(1).addTask(new Current("Poszukać noclegu", 30, 6, 2023));
        test_projects.get(1).addTask(new Done("Zarezerwować lot", 1, 6, 2023));
    }

    /** Przypisanie użytkowników do zadań */
    private void assignTasks() {
        Project project1 = test_projects.get(0);
        Object[] participants1 = project1.getParticipants().keySet().toArray();

        Task task0 = project1.getTasks().get(0);
        task0.addAssignee((User) participants1[0]);
        task0.addAssignee((User) participants1[1]);

        Task task1 = project1.getTasks().get(1);

        Task task2 = project1.getTasks().get(2);
        task2.addAssignee((User) participants1[1]);
        task2.addAssignee((User) participants1[2]);
        task2.addAssignee((User) participants1[3]);

        Task task3 = project1.getTasks().get(3);
        task3.addAssignee((User) participants1[0]);

        Project project2 = test_projects.get(1);
        Object[] participants2 = project2.getParticipants().keySet().toArray();

        Task task4 = project2.getTasks().get(0);
        task4.addAssignee((User) participants2[0]);
        task4.addAssignee((User) participants2[1]);

        Task task5 = project2.getTasks().get(1);
        task5.addAssignee((User) participants2[2]);
    }
}

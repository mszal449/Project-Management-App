// klasa reprezentująca "cały" projekt z jego wszystkimi zadaniami i uczestnikami

import javax.swing.*;
import java.time.LocalDate;

public class Project {
    LocalDate deadline; // termin ukończenia projektu
    Boolean done; // czy projekt jest ukończony?
    DefaultListModel<Task> tasks; // lista zadań
    DefaultListModel<User> participants; // lista uczestników projektu

    // konstruktor
    Project(int day, int month, int year) {
        tasks = new DefaultListModel<>();
        participants = new DefaultListModel<>();
        done = false;
        deadline = LocalDate.of(year, month, day);
    }

    // dodawanie uczestnika do projektu
    void addParticipant(User user) {
        participants.addElement(user);
    }

    // dodawanie zadania do listy
    void addTask(Task task) {
        tasks.addElement(task);
    }
}

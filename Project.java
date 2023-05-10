// klasa reprezentująca "cały" projekt z jego wszystkimi zadaniami i uczestnikami

import javax.swing.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class Project implements Serializable {
    String name;                            // nazwa projektu
    LocalDate deadline;                     // termin ukończenia projektu
    Boolean is_done;                        // czy projekt jest ukończony?
    DefaultListModel<Task> tasks;           // lista zadań
    DefaultListModel<User> participants;    // lista uczestników projektu
    @Serial
    private static final long serialVersionUID = 1L;

    // konstruktor
    Project(String name, int day, int month, int year) {
        this.name = name;
        tasks = new DefaultListModel<>();
        participants = new DefaultListModel<>();
        is_done = false;
        deadline = LocalDate.of(year, month, day);
    }

    // dodawanie uczestnika do projektu
    void addParticipant(User user) {
        participants.addElement(user);
    }

    // usuwanie uczestnika z projektu
    void deleteParticipant(User user) {
        participants.removeElement(user);
    }

    // dodawanie zadania do projektu
    void addTask(Task task) {
        task.project = this;
        if (!tasks.contains(task)) {
            tasks.addElement(task);
        }
    }

    // usuwanie zadania z projektu
    public void deleteTask(Task task) {
        tasks.removeElement(task);
    }

    // zmiana daty końcowej
    public void setDeadline(int day, int month, int year) {
        deadline = LocalDate.of(year, month, day);
    }

    // oznczanie projektu jako ukończonego
    public void setDone() {
        is_done = true;
    }

    // metoda pomocnicza napis z informacjami o projekcie
    public void getInfo() {
        System.out.println(name);
        System.out.println(deadline);
        System.out.println(participants);
        System.out.println(tasks);
        System.out.println(is_done);
        System.out.println();
    }

    // metoda pomocnicza zwracająca nazwę projektu
    public String toString() {
        return name;
    }

    // dostęp do listy zadań projektu
    public DefaultListModel<Task> getTasks() {
        return tasks;
    }

    // dostęp do listy użytkowników w projekcie
    public DefaultListModel<User> getParticipants() {
        return participants;
    }
}

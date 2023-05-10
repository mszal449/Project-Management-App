// klasa reprezentująca zadanie do wykonania w ramach projektu

import javax.swing.*;
import java.io.*;
import java.time.LocalDate;

public abstract class  Task implements Serializable {
    Project project;                        // projekt, do którego należy zadanie
    String name;                            // nazwa zadania
    String description;                     // opis zadania
    DefaultListModel<User> assignees;       // osoby odpowiedzialne
    LocalDate deadline;                     // planowana data ukończenia
    @Serial
    private static final long serialVersionUID = 1L;

    // używany w "zwykłych" konstruktorach Done i Current
    public Task(String name, DefaultListModel<User> assignees, int day, int month, int year) {
        this.name = name;
        this.assignees = assignees;
        description = "";
        deadline = LocalDate.of(year, month, day);
    }

    // konstruktor "częściowo kopiujący" używany przy zmianie statusu zadania
    public Task(Task task) {
        this.project = task.project;
        this.name = task.name;
        this.assignees = task.assignees;
        description = task.description;
        deadline = task.deadline;
        task.project.addTask(this);
    }

    // zmiana nazwy zadania
    public void setName(String name) {
        this.name = name;
    }

    // zmiana opisu zadania
    public void setDescription(String description) {
        this.description = description;
    }

    // zmiana daty końcowej
    public void setDeadline(int day, int month, int year) {
        deadline = LocalDate.of(year, month, day);
    }

    // dodanie osoby do listy
    public void addAssignee(User assignee) {
        assignees.addElement(assignee);
    }

    // usunięcie osoby z listy
    public void deleteAssignee(User assignee) {
        assignees.removeElement(assignee);
    }

    // metoda pomocniczna zwracająca napis rezprezentujący zadanie
    public String toString() {
        return name + " " + deadline;
    }

    // metoda pomocnicza wypisująca informacje o zadaniu do konsoli
    public void getInfo() {
        System.out.println(this);
        System.out.println(assignees);
    }

    // dostęp do listy osób przypisanych do zadania
    public DefaultListModel<User> getAssignees() {
        return assignees;
    }

}

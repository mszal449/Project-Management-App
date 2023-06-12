// Autorzy: Julia Kulczycka, Maciej Szałasz
// Nazwa pliku: Task.java
// Data ukończenia: 12.06.2023
// Opis:
// Klasa reprezentująca zadanie do wykonania w ramach projektu.


package Classes;

import Main.MainProgram;

import javax.swing.*;
import java.io.*;
import java.time.LocalDate;

/** Klasa reprezentująca zadanie do wykonania w ramach projektu */
public abstract class Task implements Serializable {
    /** projekt, do którego należy zadanie */
    Project project;
    /** nazwa zadania */
    String name;
    /** opis zadania */
    String description;
    /** osoby odpowiedzialne */
    DefaultListModel<User> assignees;
    /** planowana data ukończenia */
    LocalDate deadline;

    /** konstruktor używany do tworzenia nowego zadania z poziomu aplikacji */
    public Task(Project project) {
        name = "Nowe zadanie";
        description = "";
        deadline = LocalDate.now().plusDays(14);
        assignees = new DefaultListModel<>();
        assignees.addElement(MainProgram.getLoggedUser());
        this.project = project;
    }

    /** "zwykły" konstruktor */
    public Task(String name, int day, int month, int year) {
        this.name = name;
        this.assignees = new DefaultListModel<>();
        description = "";
        deadline = LocalDate.of(year, month, day);
    }

    /** konstruktor "częściowo kopiujący" używany
     * przy zmianie statusu zadania */
    public Task(Task task) {
        this.project = task.project;
        this.name = task.name;
        this.assignees = task.assignees;
        description = task.description;
        deadline = task.deadline;
        task.project.addTask(this);
    }

    /** dostęp do projektu, do którego należy zadanie */
    public Project getProject() {
        return project;
    }

    /** dostęp do nazwy zadania */
    public String getName() {
        return name;
    }
    /** zmiana nazwy zadania */
    public void setName(String name) {
        this.name = name;
    }

    /** dostęp do opisu zadania */
    public String getDescription() {
        return description;
    }
    /** zmiana opisu zadania */
    public void setDescription(String description) {
        this.description = description;
    }

    /** dostęp do daty końcowej */
    public LocalDate getDeadline() {
        return deadline;
    }
    /** zmiana daty końcowej */
    public void setDeadline(LocalDate date) {
        deadline = date;
    }

    /** dostęp do listy osób przypisanych do zadania */
    public DefaultListModel<User> getAssignees() {
        return assignees;
    }
    /** zmiana listy osób przypisanych do zadania */
    public void setAssignees(DefaultListModel<User> assignees) {
        this.assignees = assignees;
    }
    /** dodanie osoby do listy */
    public void addAssignee(User assignee) {
        assignees.addElement(assignee);
    }
    /** usunięcie osoby z listy */
    public void deleteAssignee(User assignee) {
        assignees.removeElement(assignee);
    }

    /** napis rezprezentujący zadanie */
    public String toString() {
        return name + " " + deadline;
    }

    /** metoda pomocnicza wypisująca informacje o zadaniu do konsoli */
    public void getInfo() {
        System.out.println(this);
        System.out.println(assignees);
    }

}

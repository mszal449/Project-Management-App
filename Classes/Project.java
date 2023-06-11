package Classes;

import javax.swing.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

/** Klasa reprezentująca "cały" projekt z jego wszystkimi zadaniami i uczestnikami */
public class Project implements Serializable {
    /** nazwa projektu */
    private String name;
    /** termin ukończenia projektu */
    private LocalDate deadline;
    /** czy projekt jest ukończony? */
    private Boolean is_done;
    /** lista zadań */
    private  DefaultListModel<Task> tasks;
    /** lista uczestników projektu wraz z uprawnieniami */
    private Map<User, Boolean> participants;
    @Serial
    private static final long serialVersionUID = 1L;

    // konstruktory
    /** konstruktor */
    public Project(String name, LocalDate date) {
        this.name = name;
        tasks = new DefaultListModel<>();
        participants = new HashMap<>();
        is_done = false;
        deadline = date;
    }
    /** konstruktor używany przy tworzeniu nowego projektu */
    public Project(User loggedUser) {
        this("Nowy projekt", LocalDate.now().plusMonths(1));
        this.participants.put(loggedUser, true);
    }

    // nazwa projektu
    /** zmiana nazwy projektu */
    public void setName(String new_name) {
        name = new_name;
    }
    /** zwrócenie nazwy projektu */
    public String getName() {
        return name;
    }

    // słownik uczestników z uprawnieniami
    /** zwrócenie słownika uczestników */
    public Map<User, Boolean> getParticipants() {
        return participants;
    }
    /** aktualizacja słownika uczestników */
    public void setPrivileges(Map<User, Boolean> participantsDictCopy) {
        participants = participantsDictCopy;
    }
    /** dodawanie uczestnika do projektu */
    public void addParticipant(User user) {
        participants.putIfAbsent(user, false);
    }
    /** usuwanie uczestnika z projektu */
    public void deleteParticipant(User user) {
        participants.remove(user);
    }
    /** zmiana uprawnień użytkownika */
    public void setPrivileges(User user, Boolean privileges) {
        participants.put(user, privileges);
    }
    /** odczytanie uprawnień użytkownika */
    public boolean getPrivileges(User user) {
        return participants.get(user);
    }

    // lista zadań
    /** dostęp do listy zadań projektu */
    public DefaultListModel<Task> getTasks() {
        return tasks;
    }
    /** aktualizowanie listy zadań projektu */
    public void setTasks(DefaultListModel<Task> tasks_list_copy) {
        tasks = tasks_list_copy;
    }
    /** dodawanie zadania do projektu */
    public void addTask(Task task) {
        task.project = this;
        if (!tasks.contains(task)) {
            tasks.addElement(task);
        }
    }
    /** usuwanie zadania z projektu */
    public void deleteTask(Task task) {
        tasks.removeElement(task);
    }

    // data końcowa
    /** zwrócenie daty końcowej */
    public LocalDate getDeadline() {
        return deadline;
    }
    /** zmiana daty końcowej */
    public void setDeadline(LocalDate new_deadline) {
        deadline = new_deadline;
    }

    // stan projektu
    /** oznczanie projektu jako ukończonego */
    public void setDone() {
        is_done = true;
    }
    /** odczytanie stanu */
    public Boolean getStatus()  {
        return is_done;
    }
    /** ustawianie stanu */
    public void setStatus(boolean val) {
        is_done = val;
    }
    /** metoda pomocnicza zwracająca nazwę projektu */
    public String toString() {
        return name;
    }
}

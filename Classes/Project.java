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

    /** zmiana daty końcowej */
    public void setDeadline(int day, int month, int year) {
        deadline = LocalDate.of(year, month, day);
    }

    /** oznczanie projektu jako ukończonego */
    public void setDone() {
        is_done = true;
    }

    // FIXME: usunąć
    /** metoda pomocnicza napis z informacjami o projekcie */
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
    public Map<User, Boolean> getParticipants() {
        return participants;
    }

    // Zmiana nazwy projektu
    public void setName(String new_name) {
        name = new_name;
    }

    // zwrócenie nazwy projektu
    public String getName() {
        return name;
    }

    // Zmiana planowanej ukończenia daty projektu
    public void setDeadline(LocalDate new_deadline) {
        deadline = new_deadline;
    }

    public Boolean getStatus()  {
        return is_done;
    }

    public void setStatus(boolean val) {
        is_done = val;
    }

    // zwrócenie planowanej daty projektu
    public LocalDate getDeadline() {
        return deadline;
    }

    public void setTasks(DefaultListModel<Task> tasks_list_copy) {
        tasks = tasks_list_copy;
    }

    public void setPrivileges(Map<User, Boolean> participantsDictCopy) {
        participants = participantsDictCopy;
    }

}

package Classes;

import javax.swing.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

// klasa reprezentująca "cały" projekt z jego wszystkimi zadaniami i uczestnikami
public class Project implements Serializable, Cloneable {
    private String name;                            // nazwa projektu
    private LocalDate deadline;                     // termin ukończenia projektu
    private Boolean is_done;                        // czy projekt jest ukończony?
    private  DefaultListModel<Task> tasks;           // lista zadań
    private Map<User, Boolean> participants; // lista uczestników projektu wraz z uprawnieniami
    @Serial
    private static final long serialVersionUID = 1L;

    // konstruktor
    public Project(String name, int day, int month, int year) {
        this.name = name;
        tasks = new DefaultListModel<>();
        participants = new HashMap<>();
        is_done = false;
        deadline = LocalDate.of(year, month, day);
    }

    // dodawanie uczestnika do projektu
    public void addParticipant(User user) {
        participants.putIfAbsent(user, false);
    }

    // usuwanie uczestnika z projektu
    public void deleteParticipant(User user) {
        participants.remove(user);
    }

    // zmiana uprawnień użytkownika
    public void setPrivileges(User user, Boolean privileges) {
        participants.put(user, privileges);
    }

    // odczytanie uprawnień użytkownika
    public boolean getPrivileges(User user) {
        return participants.get(user);
    }


    // dodawanie zadania do projektu
    public void addTask(Task task) {
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
    // FIXME: usunąć
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

    // klonowanie instancji
    @Override
    public Project clone() throws CloneNotSupportedException {
        Project cloned_project = (Project) super.clone();
        cloned_project.tasks = new DefaultListModel<>();
        for (int i = 0; i < tasks.getSize(); i++) {
            Task task = tasks.getElementAt(i);
            cloned_project.tasks.addElement(task.clone());
        }
        cloned_project.participants = new HashMap<>();
        for (Map.Entry<User, Boolean> entry : participants.entrySet()) {
            User user = entry.getKey();
            Boolean privileges = entry.getValue();
            cloned_project.participants.put(user.clone(), privileges);
        }
        return cloned_project;
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

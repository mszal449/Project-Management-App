// klasa reprezentująca zadanie do wykonania w ramach projektu

import java.io.*;
import java.time.LocalDate;

public class Task implements Serializable {
    String name; // nazwa zadania
    String description; // opis zadania
    User responsible; // osoba odpowiedzialna
    LocalDate deadline; // planowana data ukończenia
    @Serial
    private static final long serialVersionUID = 1L;

    // FIXME: być może do wywalenia
    // używany w "zwykłych" konstruktorach Done i Current
    public Task(String name, User user, int day, int month, int year) {
        this.name = name;
        responsible = user;
        description = "";
        deadline = LocalDate.of(year, month, day);
    }

    // używany przy zmianie statusu zadania
    public Task(Task task) {
        this.name = task.name;
        responsible = task.responsible;
        description = task.description;
        deadline = task.deadline;
    }
}

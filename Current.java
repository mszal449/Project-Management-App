// klasa reprezentująca zadanie w trakcie wykonywania

import javax.swing.*;
import java.time.LocalDate;
import java.util.Arrays;

public class Current extends Task{
    // poziomy postępu zadania
    // są ułożone kolejno, od najbardziej
    static String[] progress_level = {
            "Rozpoczęto",
            "W trakcie",
            "Wysłano pierwszą wersję",
            "W trakcie poprawy",
            "Czeka na zatwierdzenie"};

    LocalDate start_date; // data rozpoczęcia
    String progress; // poziom postępu

    // konstruktor tworzący trwające zadanie
    public Current(String name, DefaultListModel<User> assignees, int day, int month, int year) {
        super(name, assignees, day, month, year);
        start_date = LocalDate.now();
        progress = progress_level[0];
    }

    // konstruktor tworzący trwające zadanie na podstawie zaplanowanego zadania
    public Current(Planned planned_task) {
        super(planned_task);
        start_date = LocalDate.now();
        progress = progress_level[0];
    }

    // metoda reprezentująca robienie postępu w zadaniu
    public void makeProgress() {
        int index = Arrays.asList(progress_level).indexOf(progress);
        // jeżeli zadanie nie jest jeszcze na ostatnim poziomie
        if (index < progress_level.length - 1 ) {
            // przenosimy je na wyższy poziom
            progress = progress_level[index + 1];
        }
        // w przeciwnym wypadku
        else {
            // tworzymy nowy obiekt reprezentujący wykonane zadanie
            project.addTask(new Done(this));
            // i usuwamy stary
            project.deleteTask(this);
        }
    }

    // metoda pomocniczna zwracająca napis rezprezentujący zadanie
    public String toString() {
        return super.toString() + " : aktualne";
    }

    // metoda pomocnicza wypisująca informacje o zadaniu do konsoli
    public void getInfo() {
        super.getInfo();
        System.out.println("status : " + progress);
    }
}

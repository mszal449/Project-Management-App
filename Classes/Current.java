package Classes;// klasa reprezentująca zadanie w trakcie wykonywania

import Classes.User;

import javax.swing.*;
import java.time.LocalDate;
import java.util.Arrays;

public class Current extends Task {
    // poziomy postępu zadania
    private static final String[] progress_level = {
            "Rozpoczęto",
            "W trakcie",
            "Wysłano pierwszą wersję",
            "W trakcie poprawy",
            "Czeka na zatwierdzenie"};

    private final LocalDate start_date; // data rozpoczęcia
    private String progress; // aktualny poziom postępu

    // konstruktor tworzący trwające zadanie
    public Current(String name, int day, int month, int year) {
        super(name, day, month, year);
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

    // dostęp do informacji o statusie postępu
    public String getProgressLevel() {
        return progress;
    }

    // dostęp do daty rozpoczęcia zadania
    public LocalDate getStartdate() {
        return start_date;
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

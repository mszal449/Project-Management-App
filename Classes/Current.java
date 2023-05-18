package Classes;// klasa reprezentująca zadanie w trakcie wykonywania

import Classes.User;

import javax.swing.*;
import java.time.LocalDate;
import java.util.Arrays;

public class Current extends Task {
    // poziomy postępu zadania
    private static final String[] progress_levels = {
            "Rozpoczęto",
            "W trakcie",
            "Wysłano pierwszą wersję",
            "W trakcie poprawy",
            "Czeka na zatwierdzenie"};

    private final LocalDate start_date; // data rozpoczęcia
    private int cur_level; // aktualny poziom postępu

    // konstruktor tworzący trwające zadanie
    public Current(String name, int day, int month, int year) {
        super(name, day, month, year);
        start_date = LocalDate.now();
        cur_level = 0;
    }

    // konstruktor tworzący trwające zadanie na podstawie zaplanowanego zadania
    public Current(Planned planned_task) {
        super(planned_task);
        start_date = LocalDate.now();
        cur_level = 0;
    }

    // dostęp do informacji o statusie postępu
    public String getProgressLevel() {
        return progress_levels[cur_level];
    }
    public void setProgressLevel(int level) {
        if (level < 5 && level >= 0) {
            cur_level = level;
        }
    }
    public Done setDone() {
        Done new_task = new Done(this);
        project.addTask(new_task);
        project.deleteTask(this);
        return new_task;
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
        System.out.println("status : " + progress_levels[cur_level]);
    }
}

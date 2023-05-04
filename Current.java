// klasa reprezentująca zadanie w trakcie wykonywania

import java.lang.reflect.Array;
import java.time.LocalDate;

public class Current extends Task{
    static String[] progress_level = {
            "Rozpoczęto",
            "W trakcie",
            "Wysłano pierwszą wersję",
            "W trakcie poprawy"};

    LocalDate start_date; // data rozpoczęcia
    String progress; // poziom postępu

    // konstruktor tworzący ukończone zadanie
    public Current(String name, User user, int day, int month, int year) {
        super(name, user, day, month, year);
        start_date = LocalDate.now();
        progress = progress_level[0];
    }

    // konstruktor tworzący trwające zadanie na podstawie zaplanowanego zadania
    public Current(Planned planned_task) {
        super(planned_task);
        start_date = LocalDate.now();
        progress = progress_level[0];
    }
}

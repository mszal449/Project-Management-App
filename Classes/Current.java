package Classes;

import java.io.Serializable;
import java.time.LocalDate;

/** Klasa reprezentująca zadanie w trakcie wykonywania */
public class Current extends Task {
    /** poziomy postępu (stany) zadania */
    public static final String[] progress_levels = {
            "Rozpoczęto",
            "W trakcie",
            "Wysłano pierwszą wersję",
            "W trakcie poprawy",
            "Czeka na zatwierdzenie"};
    /** data rozpoczęcia */
    private final LocalDate start_date;
    /** aktualny poziom postępu */
    private int cur_level;

    /** konstruktor tworzący trwające zadanie */
    public Current(String name, int day, int month, int year) {
        super(name, day, month, year);
        start_date = LocalDate.now();
        cur_level = 0;
    }

    /** konstruktor tworzący trwające zadanie na podstawie zaplanowanego zadania */
    public Current(Planned planned_task) {
        super(planned_task);
        start_date = LocalDate.now();
        cur_level = 0;
    }

    /** konstruktor tworzący trwające zadanie na podstawie zakończonego zadania
    * (sytuacja, kiedy uznano, że zadanie jednka nie jest wykonane) */
    public Current(Done done_task) {
        super(done_task);
        start_date = LocalDate.now();
        cur_level = 0;
    }

    /** dostęp do informacji o stanie */
    public String getProgressState() {
        return progress_levels[cur_level];
    }
    /** dostęp do informacji o poziomie postępu */
    public int getProgressLevel() {
        return cur_level;
    }
    /** zmiana poziomu postępu */
    public void setProgressLevel(int level) {
        if (level < 5 && level >= 0) {
            cur_level = level;
        }
    }
    /** oznaczenie zadania jako wykonanego */
    public Done setDone() {
        Done new_task = new Done(this);
        project.addTask(new_task);
        project.deleteTask(this);
        return new_task;
    }

    /** dostęp do daty rozpoczęcia zadania */
    public LocalDate getStartdate() {
        return start_date;
    }

    /** metoda pomocniczna zwracająca napis rezprezentujący zadanie */
    public String toString() {
        return super.toString() + " : aktualne";
    }

    /** metoda pomocnicza wypisująca informacje o zadaniu do konsoli */
    public void getInfo() {
        super.getInfo();
        System.out.println("status : " + progress_levels[cur_level]);
    }
}

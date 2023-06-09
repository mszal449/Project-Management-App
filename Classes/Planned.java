// Autorzy: Julia Kulczycka, Maciej Szałasz
// Nazwa pliku: Planned.java
// Data ukończenia: 12.06.2023
// Opis:
// Klasa reprezentująca zadanie, które jeszcze nie zostało rozpoczęte.


package Classes;

import java.time.LocalDate;

/** Klasa reprezentująca zadanie, które jeszcze nie zostało rozpoczęte */
public class Planned extends Task {
    /** zaplanowana data rozpoczęcia */
    private LocalDate start_date;

    /** konstruktor */
    public Planned(String name,
                   int day,
                   int month,
                   int year,
                   int s_day,
                   int s_month,
                   int s_year) {
        super(name, day, month, year);
        start_date = LocalDate.of(s_year, s_month, s_day);
    }

    /** konstruktor używany do tworzenia nowego zadania z poziomu aplikacji */
    public Planned(Project project) {
        super(project);
        start_date = LocalDate.now();
    }

    /** zmiana planowanej daty rozpoczęcia */
    public void setStartdate(int day, int month, int year) {
        start_date = LocalDate.of(year, month, day);
    }
    /** zmiana planowanej daty rozpoczęcia */
    public void setStartdate(LocalDate date) {
        start_date = date;
    }
    /** dostęp do planowanej daty rozpoczęcia */
    public LocalDate getStartdate() {
        return start_date;
    }

    /** metoda reprezentująca rozpoczęcie zadania */
    public Current start() {
        Current new_task = new Current(this);
        project.addTask(new_task);
        project.deleteTask(this);
        return new_task;
    }

    /** metoda pomocniczna zwracająca napis rezprezentujący zadanie */
    public String toString() {
        return super.toString() + " : planowane";
    }

    /** metoda pomocnicza wypisująca informacje o zadaniu do konsoli */
    public void getInfo() {
        super.getInfo();
        System.out.println("start : " + start_date);
    }

}

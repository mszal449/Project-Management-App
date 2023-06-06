package Classes;

import java.time.LocalDate;

/**  Klasa reprezentująca wykonane zadanie */
public class Done extends Task {
    private final LocalDate end_date; // data zakończenia */

    /**  konstruktor tworzący ukończone zadanie */
    public Done(String name, int day, int month, int year) {
        super(name, day, month, year);
        end_date = LocalDate.now();
    }

    /**  konstruktor tworzący ukończone zadanie na podstawie trwającego zadania */
    public Done(Current current_task) {
        super(current_task);
        end_date = LocalDate.now();
    }

    /**  dostęp do daty zakończenia */
    public LocalDate getEndDate() {
        return end_date;
    }

    /**  zmienienie statusu zadania na aktywny */
    public Current makeCurrent() {
        Current new_task = new Current(this);
        project.addTask(new_task);
        project.deleteTask(this);
        return new_task;
    }

    /**  metoda pomocniczna zwracająca napis rezprezentujący zadanie */
    public String toString() {
        return super.toString() + " : zakończono";
    }

    /**  metoda pomocnicza wypisująca informacje o zadaniu do konsoli */
    public void getInfo() {
        super.getInfo();
        System.out.println("zakończono : " + end_date);
    }
}

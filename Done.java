// klasa reprezentująca wykonane zadanie

import java.time.LocalDate;

public class Done extends Task{
    LocalDate end_date; // data zakończenia

    // konstruktor tworzący ukończone zadanie
    public Done(String name, User user, int day, int month, int year) {
        super(name, user, day, month, year);
        end_date = LocalDate.now();
    }

    // konstruktor tworzący ukończone zadanie na podstawie trwającego zadania
    public Done(Current current_task) {
        super(current_task);
        end_date = LocalDate.now();
    }

}

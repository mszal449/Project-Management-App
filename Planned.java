// klasa reprezentująca zadanie, które jeszcze nie zostało rozpoczęte

import java.time.LocalDate;

public class Planned extends Task{
    LocalDate start_date; // zaplanowana data rozpoczęcia

    public Planned(String name, User user, int day, int month, int year, int s_day, int s_month, int s_year) {
        super(name, user, day, month, year);
        start_date = LocalDate.of(s_year, s_month, s_day);
    }

}

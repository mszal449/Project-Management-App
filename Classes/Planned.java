package Classes;// klasa reprezentująca zadanie, które jeszcze nie zostało rozpoczęte

import Classes.Current;
import Classes.User;

import javax.swing.*;
import java.time.LocalDate;

public class Planned extends Task {
    LocalDate start_date; // zaplanowana data rozpoczęcia

    // konstruktor
    public Planned(String name, DefaultListModel<User> assignees, int day, int month, int year, int s_day, int s_month, int s_year) {
        super(name, assignees, day, month, year);
        start_date = LocalDate.of(s_year, s_month, s_day);
    }

    // zmiana planowanej daty rozpoczęcia
    public void setStartdate(int day, int month, int year) {
        start_date = LocalDate.of(year, month, day);
    }

    // metoda reprezentująca rozpoczęcie zadania
    public void start() {
        project.addTask(new Current(this));
        project.deleteTask(this);
    }

    // metoda pomocniczna zwracająca napis rezprezentujący zadanie
    public String toString() {
        return super.toString() + " : planowane";
    }

    // metoda pomocnicza wypisująca informacje o zadaniu do konsoli
    public void getInfo() {
        super.getInfo();
        System.out.println("start : " + start_date);
    }

}

// klasa reprezentująca użytkownika aplikacji

import javax.swing.*;
import java.util.Objects;

public class User {
    String name; // nazwa użytkownika
    String email; // email
    String password; // hasło

    User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String toString() {
        return name;
    }

    // statyczna metoda do logowania
    public static boolean logIn(String email, String password) {
        User user = findUser(email);
        if (user == null) {
            System.out.println("Nie istnieje taki użytkownik");
            return false;
        }
        else if (Objects.equals(user.password, password)) {
            System.out.println("Logowanie zakończyło się sukcesem :)");
            MainProgram.setLoggedUser(user);
            return true;
        }
        else {
            System.out.println("Niepoprawne hasło - spróbuj jeszcze raz");
            return false;
        }
    }

    public static boolean signUp(String name, String email, String password) {
        if (findUser(email) != null) {
            System.out.println("Użytkownik o podanym adresie już istnieje - zaloguj się");
            return false;
        }
        else {
            User user = new User(name, email, password);
            MainProgram.addUser(user);
            System.out.println("Rejestracja powiodła się");
            MainProgram.setLoggedUser(user);
            System.out.println("Zalogowano");
            return true;
        }
    }

    // metoda pomocnicza - znajdowanie użytkowanika o danej nazwie na liscie użytkowników
    private static User findUser(String email) {
        DefaultListModel<User> users = MainProgram.getUsers();
        for (int i = 0; i < users.getSize(); i++) {
            User user = users.getElementAt(i);
            if (Objects.equals(user.email, email)) {
                return user;
            }
        }
        return null;
    }

}

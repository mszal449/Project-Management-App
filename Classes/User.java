package Classes;
import Main.MainProgram;

import javax.swing.*;
import java.util.Objects;

/** Klasa reprezentująca użytkownika aplikacji */
public class User {
    /** nazwa użytkownika */
    private final String name;
    /** email */
    private final String email;
    /** hasło */
    private final String password;

    /** konstruktor */
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /** zamiana na napis */
    public String toString() {
        return name;
    }

    /** dostęp do adresu e-mail użytkownika */
    public String getEmail() {
        return email;
    }

    /** statyczna metoda do logowania */
    public static boolean logIn(String email, String password) {
        User user = findUser(email);
        if (user == null) {
            System.out.println("Nie istnieje taki użytkownik");
            return false;
        }
        else if (Objects.equals(user.password, password)) {
            MainProgram.setLoggedUser(user);
            return true;
        }
        else {
            System.out.println("Niepoprawne hasło - spróbuj jeszcze raz");
            return false;
        }
    }

    /** konstruktor */
    public static void signUp(String name, String email, String password) {
        User user = new User(name, email, password);
        MainProgram.addUser(user);
        MainProgram.setLoggedUser(user);
    }

    /** metoda pomocnicza - znajdowanie użytkowanika o danej nazwie na liscie użytkowników */
    public static User findUser(String email) {
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

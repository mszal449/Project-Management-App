// klasa reprezentująca użytkownika aplikacji

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
}

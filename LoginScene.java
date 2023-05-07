import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Objects;

// TODO:
//  Action listener przycisku
//  Przycisk rejestracji
//  Layout
//  Procedura Logowania (w kontrolerze)


// Scena logowania
public class LoginScene extends JPanel {
    MainProgram app;
    DefaultListModel<User> users_list;
    JTextField login_text;          // Pole tekstowe na login
    JPasswordField password_text;   // Pole tekstowe na hasło
    JButton login_button;           // Przycisk logowania się

    // Konstruktor sceny
    public LoginScene(MainProgram app) {
        this.app = app;
        CreateLoginScene();
    }

    // Utworzenie sceny
    private void CreateLoginScene() {
        new JPanel();
        setLayout(new GridLayout(3, 1));
        addElements();
    }

    // Dodanie elementów logowania do sceny
    private void addElements() {

        // Utworzenie elementów sceny
        login_text = new JTextField(40);
        password_text = new JPasswordField(40);
        login_button = new JButton("Log in");

        // Dodanie elementów do sceny
        add(login_text);
        add(password_text);
        add(login_button);

        // Action Listener przycisku
        login_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = login_text.getText();
                String password = new String(password_text.getPassword());
                User user = findUser(login);
                if (user == null) {
                    System.out.println("Nie istnieje taki użytkownik");
                }
                else if (Objects.equals(user.login_data.password, password)) {
                    System.out.println("Logowanie zakończyło się sukcesem :)");
                    app.logged_user = user;
                    ProjectsScene projects = new ProjectsScene(app);
                    app.window.setScene(projects);
                }
                else {
                    System.out.println("Niepoprawne hasło - spróbuj jeszcze raz");
                }
            }
        });

    }

    // matoda pomocicza - znajdowanie użytkowanika o danej nazwie na liscie użytkowników
    private User findUser(String email) {
        for (int i = 0; i < app.users.getSize(); i++) {
            User user = app.users.getElementAt(i);
            if (Objects.equals(user.login_data.email, email)) {
                return user;
            }
        }
        return null;
    }

    // Ukrycie sceny
    public void hideScene() {
        setVisible(false);
    }

    // Wyświetlenie sceny
    public void showScene() {
        setVisible(true);
    }
}

package Scenes;
import Classes.User;
import Main.MainProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// TODO:
//  Przycisk rejestracji
//  Layout


// Scena logowania
public class LoginScene extends JPanel {
    JTextField login_text;          // Pole tekstowe na login
    JPasswordField password_text;   // Pole tekstowe na hasło
    JButton login_button;           // Przycisk logowania się
    JButton signup_button;          // Przycisk przejścia na stronę rejestracji

    // Konstruktor sceny
    public LoginScene() {
        CreateLoginScene();
    }

    // Utworzenie sceny
    private void CreateLoginScene() {
        new JPanel();
        setLayout(new GridLayout(4, 1));
        addElements();
    }

    // Dodanie elementów logowania do sceny
    private void addElements() {

        // Utworzenie elementów sceny
        login_text = new JTextField(40);
        password_text = new JPasswordField(40);
        login_button = new JButton("Zaloguj się");
        signup_button = new JButton("Zarejestruj się");

        // FIXME: Usunięcie automatycznego wprowadzania danych
        login_text.setText("basia@mail.com");
        password_text.setText("haslo1");

        // Dodanie elementów do sceny
        add(login_text);
        add(password_text);
        add(login_button);
        add(signup_button);

        // Action Listener przycisku
        login_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = login_text.getText();
                String password = new String(password_text.getPassword());
                if (User.logIn(login, password)) {
                    // TODO: zapewne lepsze wyświetlanie scen z tym hide and show
                    ProjectsScene projects_scene = new ProjectsScene();
                    MainProgram.setWindow(projects_scene);
                }
            }
        });

        signup_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SignupScene scene = new SignupScene();
                MainProgram.setWindow(scene);
            }
        });

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

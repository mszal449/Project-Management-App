import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Objects;

// TODO:
//  Przycisk rejestracji
//  Layout


// Scena logowania
public class SignupScene extends JPanel {
    JTextField name_text;           // Pole tekstowe na nazwę
    JTextField email_text;          // Pole tekstowe na email
    JPasswordField password_text;   // Pole tekstowe na hasło
    JButton signup_button;          // Przycisk rejestrowania się
    JButton back_to_login_button;   // Przycisk powrotu do ekranu logowania

    // Konstruktor sceny
    public SignupScene() {
        CreateSignupScene();
    }

    // Utworzenie sceny
    private void CreateSignupScene() {
        new JPanel();
        setLayout(new GridLayout(5, 1));
        addElements();
    }

    // Dodanie elementów logowania do sceny
    private void addElements() {

        // Utworzenie elementów sceny
        name_text = new JTextField(40);
        email_text = new JTextField(40);
        password_text = new JPasswordField(40);
        signup_button = new JButton("Zarejestruj się");
        back_to_login_button = new JButton("Wróć do logowania");

        // Dodanie elementów do sceny
        add(name_text);
        add(email_text);
        add(password_text);
        add(signup_button);
        add(back_to_login_button);

        // Action Listener przycisku rejestracji
        signup_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = name_text.getText();
                String email = email_text.getText();
                String password = new String(password_text.getPassword());
                if (User.signUp(name, email, password)) {
                    ProjectsScene projects_scene = new ProjectsScene();
                    MainProgram.setWindow(projects_scene);
                }
            }
        });

        // Action Listener przycisku powrotu do ekranu logowania
        back_to_login_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginScene scene = new LoginScene();
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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// TODO:
//  Action listener przycisku
//  Przycisk rejestracji
//  Layout
//  Procedura Logowania (w kontrolerze)


// Scena logowania
public class LoginScene extends JPanel {
    JTextField login_text;          // Pole tekstowe na login
    JPasswordField password_text;   // Pole tekstowe na hasło
    JButton login_button;           // Przycisk logowania się

    // Konstruktor sceny
    public LoginScene() {
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

        // Action Listener przycisku
        login_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Funkcjonalność w kontrolerze
                System.out.println("Log in pressed");
            }
        });

        // Dodanie elementów do sceny
        add(login_text);
        add(password_text);
        add(login_button);
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

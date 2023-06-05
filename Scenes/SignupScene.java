package Scenes;

import Classes.User;
import Main.MainProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import static Classes.User.findUser;

// TODO:
//  Layout


// Scena logowania
public class SignupScene extends JPanel {
    JTextField name_text;           // Pole tekstowe na nazwę
    JTextField email_text;          // Pole tekstowe na email
    JPasswordField password_text;   // Pole tekstowe na hasło
    JButton signup_button;          // Przycisk rejestrowania się
    JButton back_to_login_button;   // Przycisk powrotu do ekranu logowania


    // ---------------    SCENA    ---------------

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


    // ---------------    ELEMENTY SCENY    ---------------

    // Elementy systemu logowania
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
        signup_button.addActionListener(e -> {
            String name = name_text.getText();
            String email = email_text.getText();
            String password = new String(password_text.getPassword());
            // sprawdzanie poprawności danych
            if (Objects.equals(name, "")
                    || Objects.equals(email, "")
                    || Objects.equals(password, "")) {
                showEmptyFieldsDialog();
                return;
            }
            // sprawdzanie, czy adres e-mail jest unikatowy
            if (findUser(email) != null) {
                    int result = showLogInDialog();
                    // przejście do ekranu logowania
                    if (result == JOptionPane.YES_OPTION) {
                        MainProgram.setWindow("login_scene");
                    // wyczyszczenie pola z adresem e-mail
                    } else if (result == JOptionPane.NO_OPTION) {
                        email_text.setText("");
                    }
                    return;
            }
            User.signUp(name, email, password);
            MainProgram.setWindow("projects_scene");
        });

        // Action Listener przycisku powrotu do ekranu logowania
        back_to_login_button.addActionListener(e -> MainProgram.setWindow("login_scene"));
    }

    private int showEmptyFieldsDialog() {
        return JOptionPane.showConfirmDialog(
                null,
                "Pola nie mogą być puste",
                "Pola puste",
                JOptionPane.DEFAULT_OPTION
        );
    }

    // okno dialogowe wyświetlające się, gdy dany adres e-mail nie jest unikatowy
    private int showLogInDialog() {
        return JOptionPane.showOptionDialog(
                null,
                "Konto o podanym adresie e-mail już istnieje",
                "Logowanie",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{"Zaloguj się", "Podaj inny adres"},
                "Zaloguj się"
        );
    }
}

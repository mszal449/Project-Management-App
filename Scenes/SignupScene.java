package Scenes;

import Classes.User;
import Main.MainProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;

import static Classes.User.findUser;

/** Scena rejestrowania konta */
public class SignupScene extends JPanel {
    /** pole tekstowe na nazwę użytkownika */
    JTextField name_text;
    /** pole tekstowe na adres email */
    JTextField email_text;
    /** pole tekstowe na hasło */
    JPasswordField password_text;
    /** przycisk rejestracji nowego użytkownika */
    JButton signup_button;
    /** przycisk powrotu do ekranu logowania */
    JButton back_to_login_button;


    // ---------------    SCENA    ---------------

    /** konstruktor sceny */
    public SignupScene() {
        CreateSignupScene();
    }

    /** utworzenie zawartości sceny */
    private void CreateSignupScene() {
        new JPanel();
        setLayout(new GridLayout(5, 1, 20, 20));
        setBorder(Styles.MAIN_BORDER);
        addElements();
    }


    // ---------------    ELEMENTY SCENY    ---------------

    /** elementy systemu logowania */
    private void addElements() {
        // pole tekstowe na nazwę użytkownika
        name_text = new JTextField(40);
        name_text.setFont(Styles.LOGIN_FONT);
        name_text.setBorder(Styles.ELEMENT_BORDER);

        // pole tekstowe na adres email
        email_text = new JTextField(40);
        email_text.setFont(Styles.LOGIN_FONT);
        email_text.setBorder(Styles.ELEMENT_BORDER);

        // pole tekstowe na hasło
        password_text = new JPasswordField(40);
        password_text.setFont(Styles.LOGIN_FONT);
        password_text.setBorder(Styles.BUTTON_BORDER);

        // przycisk rejestracji
        signup_button = new JButton("Zarejestruj się");
        signup_button.setFont(Styles.BUTTON_FONT);
        signup_button.setBorder(Styles.BUTTON_BORDER);

        // przycisk powrotu do sceny logowania
        back_to_login_button = new JButton("Wróć do logowania");
        back_to_login_button.setFont(Styles.BUTTON_FONT);
        back_to_login_button.setBorder(Styles.BUTTON_BORDER);

        // Dodanie elementów do sceny
        add(name_text);
        add(email_text);
        add(password_text);
        add(signup_button);
        add(back_to_login_button);

        // akcja rejestracji
        signup_button.addActionListener(signupActionListener());
        // akcja powrotu do logowania
        back_to_login_button.addActionListener(backToLoginActionListener());
    }

    // --------------- OBSŁUGA ZDARZEŃ ---------------
    /** akcja rejestracji */
    private ActionListener signupActionListener() {
        return e -> {
            String name = name_text.getText();
            String email = email_text.getText();
            String password = new String(password_text.getPassword());
            // sprawdzanie, czy pola nie są puste
            if (Objects.equals(name, "")
                    || Objects.equals(email, "")) {
                showEmptyFieldsDialog();
            }
            // sprawdzanie, czy hasło nie jest za krótkie
            else if (password.length() < 6) {
                showPasswordTooShortDialog();
                password_text.setText("");
            }
            // sprawdzanie, czy adres e-mail jest unikatowy
            else if (findUser(email) != null) {
                int result = showLogInDialog();
                // przejście do ekranu logowania
                if (result == JOptionPane.YES_OPTION) {
                    MainProgram.setWindow("login_scene");
                    // wyczyszczenie pola z adresem e-mail
                } else if (result == JOptionPane.NO_OPTION) {
                    email_text.setText("");
                }
            } else {
                User.signUp(name, email, password);
                MainProgram.setWindow("projects_scene");
            }
        };
    }

    /** powrót do sceny logowania */
    private ActionListener backToLoginActionListener() {
        return e -> MainProgram.setWindow("login_scene");
    }

    // --------------- OKNA DIALOGOWE ---------------
    /** okno dialogowe informujące o pustych polach */
    private int showEmptyFieldsDialog() {
        return JOptionPane.showConfirmDialog(
                null,
                "Pola nie mogą być puste",
                "Pola puste",
                JOptionPane.DEFAULT_OPTION
        );
    }

    /** okno dialogowe informujące o zbyt krótkim haśle */
    private int showPasswordTooShortDialog() {
        return JOptionPane.showConfirmDialog(
                null,
                "Hasło musi mieć przynajmniej 6 znaków",
                "Hasło zbyt krótkie",
                JOptionPane.DEFAULT_OPTION
        );
    }

    /** okno dialogowe wyświetlające się, gdy dany adres e-mail nie jest unikatowy */
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

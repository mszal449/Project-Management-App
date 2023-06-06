package Scenes;

import Classes.User;
import Main.MainProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// scena logowania
public class SignupScene extends JPanel {
    JTextField name_text;           // pole tekstowe na nazwę użytkownika
    JTextField email_text;          // pole tekstowe na adres email
    JPasswordField password_text;   // pole tekstowe na hasło
    JButton signup_button;          // przycisk rejestracji nowego użytkownika
    JButton back_to_login_button;   // przycisk powrotu do ekranu logowania


    // ---------------    SCENA    ---------------

    // konstruktor sceny
    public SignupScene() {
        CreateSignupScene();
    }

    // utworzenie zawartości sceny
    private void CreateSignupScene() {
        new JPanel();
        setLayout(new GridLayout(5, 1, 20, 20));
        setBorder(Styles.MAIN_BORDER);
        addElements();
    }


    // ---------------    ELEMENTY SCENY    ---------------

    // elementy systemu logowania
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

        // FIXME: przenieść poza metodę?
        // Action Listener przycisku rejestracji
        signup_button.addActionListener(signupButtonListener());

        // Action Listener przycisku powrotu do ekranu logowania
        back_to_login_button.addActionListener(backToLoginActionListener());
    }

    // ---------------    ACTION LISTENER'Y    ---------------

    private ActionListener signupButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = name_text.getText();
                String email = email_text.getText();
                String password = new String(password_text.getPassword());
                if (User.signUp(name, email, password)) {
                    MainProgram.setWindow("projects_scene");
                }
            }
        };
    }

    private ActionListener backToLoginActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainProgram.setWindow("login_scene");
            }
        };
    }
}

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
        signup_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = name_text.getText();
                String email = email_text.getText();
                String password = new String(password_text.getPassword());
                if (User.signUp(name, email, password)) {
                    MainProgram.setWindow("projects_scene");
                }
            }
        });

        // Action Listener przycisku powrotu do ekranu logowania
        back_to_login_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainProgram.setWindow("login_scene");
            }
        });

    }

}

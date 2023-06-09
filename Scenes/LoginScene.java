// Autorzy: Julia Kulczycka, Maciej Szałasz
// Nazwa pliku: LoginScene.java
// Data ukończenia: 12.06.2023
// Opis:
// Scena logowania.


package Scenes;
import Classes.User;
import Main.MainProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


/** Scena logowania */
public class LoginScene extends JPanel {
    /** miejsce na nazwę użytkownika */
    private final JTextField login_text;
    /** miejsce na hasło */
    private JPasswordField password_text;
    // ---------------    SCENA    ---------------

    /** konstruktor */
    public LoginScene() {
        login_text = new JTextField(40);
        password_text = new JPasswordField(40);
        CreateLoginScene();
    }

    /** utworzenie sceny */
    private void CreateLoginScene() {
        new JPanel();
        setLayout(new GridLayout(4, 1, 20, 20));
        setBorder(Styles.MAIN_BORDER);
        addElements();
    }


    // ---------------    ELEMENTY SCENY    ---------------

    /** dodanie elementów logowania do sceny */
    private void addElements() {
        JPanel login_panel = new JPanel();
        login_panel.setLayout(new BorderLayout());

        // pole tekstowe na identyfikator użytkownika

        JLabel login_label = new JLabel("Email");
        login_label.setHorizontalAlignment(JLabel.CENTER);
        login_label.setLayout(new BorderLayout());

        login_label.setFont(Styles.LOGIN_FONT);
        login_text.setHorizontalAlignment(JTextField.CENTER);
        login_text.setFont(Styles.LOGIN_FONT);
        login_text.setBorder(Styles.ELEMENT_BORDER);

        login_panel.add(login_label, BorderLayout.NORTH);
        login_panel.add(login_text, BorderLayout.CENTER);



        // pole tekstowe na hasło użytkownika
        JPanel password_panel = new JPanel();
        password_panel.setLayout(new BorderLayout());
        JLabel password_label = new JLabel("Hasło");
        password_label.setHorizontalAlignment(JLabel.CENTER);
        password_label.setFont(Styles.LOGIN_FONT);

        password_text = new JPasswordField(40);
        password_text.setHorizontalAlignment(JPasswordField.CENTER);
        password_text.setFont(Styles.LOGIN_FONT);
        password_text.setBorder(Styles.ELEMENT_BORDER);

        password_panel.add(password_label, BorderLayout.NORTH);
        password_panel.add(password_text, BorderLayout.CENTER);


        // przycisk logowania
        JButton login_button = new JButton("Zaloguj się");
        login_button.setFont(Styles.BUTTON_FONT);
        login_button.setBorder(Styles.BUTTON_BORDER);

        // przycisk rejestracji nowego użytkownika
        JButton signup_button = new JButton("Zarejestruj się");
        signup_button.setFont(Styles.BUTTON_FONT);
        signup_button.setBorder(Styles.BUTTON_BORDER);

        // dodanie elementów do sceny
        add(login_panel);
        add(password_panel);
        add(login_button);
        add(signup_button);

        // akcje przycisków
        login_button.addActionListener(loginActionListener());
        signup_button.addActionListener(e -> MainProgram.setWindow("signup_scene"));
    }

    // ---------------    NASŁUCHIWANIE ZDARZEŃ    ---------------
    /** akcja logowania */
    private ActionListener loginActionListener() {
        return e -> {
            String login = login_text.getText();
            String password = new String(password_text.getPassword());
            if (User.logIn(login, password)) {
                MainProgram.setWindow("projects_scene");}
            else {
                JOptionPane.showMessageDialog
                        (this, "Wprowadzono niepoprawne dane");
            }
        };
    }
}

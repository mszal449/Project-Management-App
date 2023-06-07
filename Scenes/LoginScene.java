package Scenes;
import Classes.User;
import Main.MainProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;



/** Scena logowania */
public class LoginScene extends JPanel {
    /** miejsce na nazwę użytkownika */
    private JTextField login_text;
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
        // pole tekstowe na identyfikator użytkownika
        login_text.setHorizontalAlignment(JTextField.CENTER);
        login_text.setFont(Styles.LOGIN_FONT);
        login_text.setBorder(Styles.ELEMENT_BORDER);

        // pole tekstowe na hasło użytkownika
        password_text = new JPasswordField(40);
        password_text.setHorizontalAlignment(JPasswordField.CENTER);
        password_text.setFont(Styles.LOGIN_FONT);
        password_text.setBorder(Styles.ELEMENT_BORDER);

        // przycisk logowania
        JButton login_button = new JButton("Zaloguj się");
        login_button.setFont(Styles.BUTTON_FONT);
        login_button.setBorder(Styles.BUTTON_BORDER);

        // przycisk rejestracji nowego użytkownika
        JButton signup_button = new JButton("Zarejestruj się");
        signup_button.setFont(Styles.BUTTON_FONT);
        signup_button.setBorder(Styles.BUTTON_BORDER);


        // FIXME: Usunięcie automatycznego wprowadzania danych
        login_text.setText("basia@mail.com");
        password_text.setText("haslo1");

        // dodanie elementów do sceny
        add(login_text);
        add(password_text);
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
            System.out.println(login);
            String password = new String(password_text.getPassword());
            System.out.println(password);
            if (User.logIn(login, password)) {
                MainProgram.setWindow("projects_scene");}
        };
    }
}

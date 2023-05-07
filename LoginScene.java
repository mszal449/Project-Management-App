import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

// TODO:
//  Action listener przycisku
//  Przycisk rejestracji
//  Layout
//  Procedura Logowania (w kontrolerze)


// Scena logowania
public class LoginScene extends JPanel {
    MainProgram app;                // TODO: to się wyekstraktuje do nadklasy I guess
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
                // pobranie danych z pól
                String email = login_text.getText();
                char[] password = password_text.getPassword();
                try {
                    if (app.dbManager.checkCredentials(email, password)) {
                        System.out.println("log in succeed! :)");
                        WelcomeScene welcomeScene = new WelcomeScene(app);
                        app.window.setScene(welcomeScene);
                    }
                    else {
                        System.out.println("bad credentials");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println("Log in pressed");
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

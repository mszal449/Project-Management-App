// ona jest do wywalenia, tylko sprawdzam, czy zmienianie działa

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class WelcomeScene extends JPanel {
    MainProgram app;
    JLabel welcome_text;
    JButton back_button;

    public WelcomeScene(MainProgram app) {
        this.app = app;
        CreateWelcomeScene();
    }

    private void CreateWelcomeScene() {
        new JPanel();
        setLayout(new GridLayout(2, 1));
        addElements();
    }

    private void addElements() {
        welcome_text = new JLabel("Zwariuję z tym projektem XD");
        back_button = new JButton("cofnij");
        add(welcome_text);
        add(back_button);
        back_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginScene loginScene = new LoginScene(app);
                app.window.setScene(loginScene);
            }
        });
    }
}

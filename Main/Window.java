package Main;

import Classes.Project;
import Classes.Task;
import Scenes.*;

import javax.swing.*;
import java.util.Objects;

public class Window extends JFrame{



    // Konstruktor okna
    public Window() {
        // Utworzenie okna
        CreateWindow();

        // Dodanie scen do okna i przekazanie im danych
        setScene("login_scene");

        // Wyświetlenie okna
        setVisible(true);
    }

    // Utworzenie okna programu
    private void CreateWindow() {
        // Utworzenie nowego okna
        new JFrame();

        // Konfiguracja parametrów okna
        setTitle("Projekt");
        setSize(1100, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // Wyświetlenie jednej ze scen i ukrycie reszty
    public void setScene(String scene_name, Object ... args) {
        // Ukrycie scen
        getContentPane().removeAll();
        JPanel scene;
        // Pokazanie wybranej sceny
        if(Objects.equals(scene_name, "login_scene")) {
            scene = new LoginScene();
        }
        else if (Objects.equals(scene_name, "signup_scene")) {
            scene = new SignupScene();
        }
        else if (Objects.equals(scene_name, "projects_scene")) {
            scene = new ProjectsScene();
        }
        else if (Objects.equals(scene_name, "project_preview_scene")) {
            try {
                scene = new ProjectPreviewScene((Project) args[0]);
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
        else if (Objects.equals(scene_name, "task_preview_scene")) {
            scene = new TaskPreviewScene((Task) args[0]);
        }
        else if (Objects.equals(scene_name, "task_edit_scene")) {
            scene = new TaskEditorScene((Task) args[0]);
        }
        else {
            System.out.println("Nie znaleziono okna");
            return;
        }
        add(scene);
        // Odświeżenie okna
        System.out.println("Scena wczytana");
        revalidate();
        repaint();
    }
}

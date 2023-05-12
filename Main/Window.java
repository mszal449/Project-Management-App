package Main;

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
    public void setScene(String scene_name) {
        // Ukrycie scen


        // Pokazanie wybranej sceny
        if(Objects.equals(scene_name, "login_scene")) {
            getContentPane().removeAll();
            JPanel login_scene = new LoginScene();
            add(login_scene);
        }
        else if (Objects.equals(scene_name, "signup_scene")) {
            getContentPane().removeAll();
            JPanel signup_scene = new SignupScene();
            add(signup_scene);
        }
        else if (Objects.equals(scene_name, "projects_scene")) {
            getContentPane().removeAll();
            JPanel projects_scene = new ProjectsScene();
            add(projects_scene);
        }
        else if (Objects.equals(scene_name, "project_preview_scene")) {
            try {
                getContentPane().removeAll();
                JPanel project_preview = new ProjectPreviewScene(MainProgram.getChosenProject());
                add(project_preview);
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            System.out.println("Nie znaleziono okna");
        }

        // Odświeżenie okna
        System.out.println("Scena wczytana");
        revalidate();
        repaint();
    }
}

import javax.swing.*;

public class Window extends JFrame{
    private LoginScene login_scene;         // Scena logowania
    private JPanel account_scene;           // Listy projektów i zadań konta
    private JPanel project_overview_scene;  // Podgląd projektu
    private JPanel project_edit_scene;      // Scena edytowania projektu? (zmiana opisu, dedline'u, itp.)
    private JPanel task_editor;             // Scena dodawania i edytowania zadań


    // Konstruktor okna
    public Window() {
        // Utworzenie okna
        CreateWindow();

        // Dodanie scen do okna
        AddScenes();

        // Wyświetlenie okna
        setVisible(true);
    }

    // Utworzenie okna programu
    private void CreateWindow() {
        // Utworzenie nowego okna
        new JFrame();

        // Konfiguracja parametrów okna
        setTitle("Projekt");
        setSize(800, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // TODO: Layout
    }

    // Dodanie scen do okna
    private void AddScenes() {
        // TODO: Dodanie wszystkich scen

        // Utworzenie scen
        login_scene = new LoginScene();


        // Dodanie scen do okna
        add(login_scene);
    }

    // TODO: Wygodne wyświetlenie jednej ze scen i ukrycie reszty
}

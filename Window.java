import javax.swing.*;

public class Window extends JFrame{
    private LoginScene login_scene;         // Scena logowania
    private WelcomeScene welcome_scene;         // Strona domowa
    private JPanel account_scene;           // Listy projektów i zadań konta
    private JPanel project_overview_scene;  // Podgląd projektu
    private JPanel project_edit_scene;      // Scena edytowania projektu? (zmiana opisu, dedline'u, itp.)
    private JPanel task_editor;             // Scena dodawania i edytowania zadań


    // Konstruktor okna
    public Window(MainProgram app) {
        // Utworzenie okna
        CreateWindow();

        // Dodanie scen do okna
        AddScenes(app);

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
    private void AddScenes(MainProgram app) {
        // TODO: Dodanie wszystkich scen

        // Utworzenie scen
        login_scene = new LoginScene(app);
        welcome_scene = new WelcomeScene(app);

        // Dodanie scen do okna
        add(welcome_scene);
        add(login_scene);

    }

    // TODO: Wygodne wyświetlenie jednej ze scen i ukrycie reszty
    public void setScene(JPanel scene) {
        getContentPane().removeAll();
        getContentPane().add(scene);
        revalidate();
        repaint();
    }
}

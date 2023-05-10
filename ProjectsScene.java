import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ProjectsScene extends JPanel {
    JList<Project> Jusers_projects;     // Lista wszystkich projektów
    JList<Task> Jusers_tasks;           // Lista wszystkich zadań

    JButton open_project_button;        // Przycisk wybrania projektu
    JButton new_project_button;         // Przycisk dodania projektu

    JButton chose_task_button;          // Przycisk wybrania zadania
    // TODO: new task/delete task button?

    // Wygląd okna
    static int LOGGED_USER_FONT_SIZE = 24;      // Czcionka informacji o zalogowanym użytkowniku
    static int MAIN_PADDING_H = 20;             // Odległość zawartości od granicy okna
    static int MAIN_PADDING_V = 20;             // FIXME: Nie działa na napis zalogowanego użytkownika? xd
    static int LIST_HEADER_FONT_SIZE = 20;      // Wielkość czcionki nagłówków "Projects" i "Tasks"
    static int LIST_ELEMENT_FONT_SIZE = 16;     // Wielkość czcionki elementów listy
    static int BUTTON_FONT_SIZE = 20;           // Wielkość czcionki przycisków

    // Konstruktor sceny
    public ProjectsScene() {
        // wczytanie projektów użytkownika
        Jusers_projects = new JList<>(MainProgram.getProjects(MainProgram.getLoggedUser()));
        // wczytanie zadań użytkownika
        Jusers_tasks = new JList<>(MainProgram.getTasks(MainProgram.getLoggedUser()));

        // Utworzenie czcionki list
        Font font = new Font("Arial", Font.PLAIN, LIST_ELEMENT_FONT_SIZE);
        Jusers_projects.setFont(font);
        Jusers_tasks.setFont(font);

        CreateProjectsScene();
    }

    // Utworzenie panelu sceny
    private void CreateProjectsScene() {
        // Utworzenie panelu
        new JPanel();

        // Konfiguracja panelu
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(MAIN_PADDING_V, MAIN_PADDING_H, MAIN_PADDING_V, MAIN_PADDING_H));

        // Dodanie elementów do panelu
        addElements();
    }

    // Dodanie elementów do sceny
    private void addElements() {
        // Dodanie napisu z nazwą użytkownika
        User logged_user = MainProgram.getLoggedUser();
        Label logged_user_label = new Label("Zalogowano jako " + logged_user);
        Font font = new Font("Arial", Font.PLAIN, LOGGED_USER_FONT_SIZE);
        logged_user_label.setFont(font);
        add(logged_user_label, BorderLayout.NORTH);

        // Utworzenie panelu przechowującego listy projektów i zadań
        JPanel lists_box = new JPanel();
        lists_box.setLayout(new GridLayout(1, 2, 10, 0));
        lists_box.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Dodanie panelu z obsługującego projekty użytkownika
        lists_box.add(createProjectsPanel());

        // Dodanie panelu obsługującego zadania użytkownika
        lists_box.add(createTasksPanel());

        add(lists_box);
    }

    // Utworzenie panelu z listą projektów
    private JPanel createProjectsPanel() {
        // Utworzenie panelu
        JPanel panel = new JPanel();

        // Konfiguracja panelu
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.black));

        // Utworzenie nagłówka listy projektów
        Label list_header_label= new Label("Projekty:");
        Font font = new Font("Arial", Font.PLAIN, LIST_HEADER_FONT_SIZE);
        list_header_label.setFont(font);
        panel.add( list_header_label, BorderLayout.NORTH);

        // Dodanie listy do okna
        panel.add(Jusers_projects, BorderLayout.CENTER);
        Jusers_projects.addMouseListener(createProjectsListListener());

        // Dodanie przycisków sekcji
        panel.add(createProjectsButtonPanel(), BorderLayout.SOUTH);

        return panel;
    }

    // Utworzenie przycisków listy projektów
    private JPanel createProjectsButtonPanel() {
        // Utworzenie panelu
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,2));
        Font button_font = new Font("Arial", Font.PLAIN, BUTTON_FONT_SIZE);

        // Utworzenie przycisków
        open_project_button = new JButton("Otwórz projekt");
        // TODO: Listener przycisku

        new_project_button = new JButton("Nowy projekt");
        // TODO: Listener przycisku

        open_project_button.setFont(button_font);
        new_project_button.setFont(button_font);


        // Dodanie przycisków do okna
        panel.add(new_project_button);
        panel.add(open_project_button);

        return panel;
    }

    // Utworzenie panelu z listą zadań
    private JPanel createTasksPanel() {
        // Utworzenie panelu
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.black));

        // Utworzenie nagłówka listy zadań
        Label list_header_label= new Label("Zadania:");
        Font font = new Font("Arial", Font.PLAIN, LIST_HEADER_FONT_SIZE);
        list_header_label.setFont(font);
        panel.add( list_header_label, BorderLayout.NORTH);

        // Dodanie listy do okna
        panel.add(Jusers_tasks, BorderLayout.CENTER);
        Jusers_tasks.addMouseListener(createTasksListListener());

        // Dodanie przycisków sekcji
        panel.add(createTasksButtonPanel(), BorderLayout.SOUTH);
        return panel;
    }

    // Utworzenie przycisków listy zadań
    private JPanel createTasksButtonPanel() {
        // Utworzenie panelu
        JPanel panel = new JPanel();


        // Utworzenie przycisku
        panel.setLayout(new GridLayout(1,1));
        Font button_font = new Font("Arial", Font.PLAIN, BUTTON_FONT_SIZE);

        chose_task_button = new JButton("Otwórz zadanie");
        // TODO: Listener przycisku

        chose_task_button.setFont(button_font);

        // Dodanie przycisku do okna
        panel.add(chose_task_button);

        return panel;
    }

    // Listener listy projektów
    private MouseAdapter createProjectsListListener() {
        // Nadanie dostępu do instancji wszystich projektów
        DefaultListModel<Project> all_projects = MainProgram.getProjects();

        // Zwrócenie nowego listenera listy
        return new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) { // jeżeli kilknięto 2 razy...
                    // pobranie numeru indeksu
                    int index = Jusers_projects.locationToIndex(evt.getPoint());
                    // wybranie wpisu o danym indeksie
                    Project selectedProject = all_projects.get(index);
                    System.out.println("Wybrano projekt " + selectedProject);
                    // TODO: Otwieranie projektu po 2 kliknięciach
                }
                // TODO: Wybór projektu po 1 kliknięciu
            }
        };
    }

    // Listener listy zadań
    private MouseAdapter createTasksListListener() {
        // Nadanie dostępu do instancji wszystich zadań
        DefaultListModel<Task> all_tasks = MainProgram.getTasks();

        // Zwrócenie nowego listenera listy
        return new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) { // jeżeli kilknięto 2 razy...
                    // pobranie numeru indeksu
                    int index = Jusers_tasks.locationToIndex(evt.getPoint());
                    // wybranie wpisu o danym indeksie
                    Task selectedTask = all_tasks.get(index);
                    System.out.println("Wybrano zadanie " + selectedTask);
                    // TODO: Otwieranie zadania po 2 kliknięciach
                }
                // TODO: Wybór zadania po 1 kliknięciu
            }
        };
    }
}

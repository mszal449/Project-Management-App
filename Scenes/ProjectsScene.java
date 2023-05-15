package Scenes;
import Classes.Project;
import Classes.Task;
import Classes.User;
import Main.Main;
import Main.MainProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProjectsScene extends JPanel {
    JList<Project> Jusers_projects;     // Lista wszystkich projektów
    JList<Task> Jusers_tasks;           // Lista wszystkich zadań
    Project chosen_project;             // wybrany projekt
    JButton open_project_button;        // przycisk otwarcia projektu


    // Wygląd okna
    static int LOGGED_USER_FONT_SIZE = 24;      // Czcionka informacji o zalogowanym użytkowniku
    static int MAIN_PADDING_H = 20;             // Odległość zawartości od granicy okna
    static int MAIN_PADDING_V = 20;             // FIXME: Nie działa na napis zalogowanego użytkownika? xd
    static int LIST_HEADER_FONT_SIZE = 20;      // Wielkość czcionki nagłówków "Projects" i "Tasks"
    static int LIST_ELEMENT_FONT_SIZE = 16;     // Wielkość czcionki elementów listy

    // ---------------   CZCIONKI   ---------------

    static Font HEADER_FONT = new Font("Arial", Font.PLAIN, 26);
    static Font LIST_ELEMENT_FONT = new Font("Arial", Font.PLAIN, 20);
    static Font BUTTON_FONT = new Font("Arial", Font.PLAIN, 20);


    // ---------------    SCENA    ---------------

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

        CreateScene();
    }

    // Utworzenie panelu sceny
    private void CreateScene() {
        // Utworzenie panelu
        new JPanel();

        // Konfiguracja panelu
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(MAIN_PADDING_V,
                                                  MAIN_PADDING_H,
                                                  MAIN_PADDING_V,
                                                  MAIN_PADDING_H));

        // Dodanie elementów do panelu
        addElements();
    }

    // Dodanie elementów do sceny
    private void addElements() {
        // Dodanie napisu z nazwą użytkownika
        User logged_user = MainProgram.getLoggedUser();
        Label logged_user_label = new Label("Zalogowano jako " + logged_user);
        logged_user_label.setFont(HEADER_FONT);
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


    //  --------------- PANELE SCENY ----------------

    // Utworzenie panelu z listą projektów
    private JPanel createProjectsPanel() {
        // Utworzenie panelu
        JPanel panel = new JPanel();

        // Konfiguracja panelu
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.black));

        // Utworzenie nagłówka listy projektów
        Label list_header_label= new Label("Projekty:");
        list_header_label.setFont(LIST_ELEMENT_FONT);
        panel.add( list_header_label, BorderLayout.NORTH);

        // Dodanie listy do okna
        panel.add(Jusers_projects, BorderLayout.CENTER);
        Jusers_projects.addMouseListener(ProjectsListListener());

        // Dodanie przycisków sekcji
        panel.add(createProjectsButtonPanel(), BorderLayout.SOUTH);

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
        Jusers_tasks.addMouseListener(TasksListListener());

        // Dodanie przycisków sekcji
        panel.add(createTasksButtonPanel(), BorderLayout.SOUTH);
        return panel;
    }


    //  --------------- PANELE PRZYCISKÓW ---------------

    // Utworzenie przycisków listy zadań
    private JPanel createTasksButtonPanel() {
        // Utworzenie panelu
        JPanel panel = new JPanel();


        // Utworzenie przycisku
        panel.setLayout(new GridLayout(1,1));

        JButton chose_task_button = new JButton("Otwórz zadanie");
        // TODO: Listener przycisku

        chose_task_button.setFont(BUTTON_FONT);

        // Dodanie przycisku do okna
        panel.add(chose_task_button);

        return panel;
    }

    // Utworzenie przycisków listy projektów
    private JPanel createProjectsButtonPanel() {
        // Utworzenie panelu
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,2));

        // Utworzenie przycisków
        open_project_button = new JButton("Otwórz projekt");
        open_project_button.setEnabled(false);
        open_project_button.addActionListener(OpenProjectListener());

        JButton new_project_button = new JButton("Nowy projekt");
        // TODO: Listener przycisku

        open_project_button.setFont(BUTTON_FONT);
        new_project_button.setFont(BUTTON_FONT);


        // Dodanie przycisków do okna
        panel.add(new_project_button);
        panel.add(open_project_button);

        return panel;
    }


    //  --------------- ACTION LISTENERS  ---------------

    // Wybór projektu z listy
    private MouseAdapter ProjectsListListener() {
        // Nadanie dostępu do instancji wszystich projektów
        DefaultListModel<Project> all_projects = MainProgram.getProjects();

        // Zwrócenie nowego listenera listy
        return new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 1) { // jeżeli kilknięto 1 raz...
                    // pobranie numeru indeksu
                    int index = Jusers_projects.locationToIndex(evt.getPoint());
                    // wybranie wpisu o danym indeksie
                    chosen_project = all_projects.get(index);
                    // ustawienie możliwości otwarcia projektu
                    open_project_button.setEnabled(true);

//                    MainProgram.setChosenProject(selectedProject);
//                    MainProgram.getChosenProject().getInfo();
                }
                // TODO: Otwieranie projektu po 2 kliknięciach

            }
        };
    }

    // Przycisk otwierania projektu
    private ActionListener OpenProjectListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainProgram.setWindow("project_preview_scene", chosen_project);
            }
        };
    }

    // Wybór zadania z listy
    private MouseAdapter TasksListListener() {
        // Nadanie dostępu do instancji wszystich zadań
        DefaultListModel<Task> all_tasks = MainProgram.getTasks();

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

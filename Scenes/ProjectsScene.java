package Scenes;
import Classes.Project;
import Classes.Task;
import Classes.User;
import Main.MainProgram;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/** Scena wyboru projektów i podglądu zadań użytkownika */
public class ProjectsScene extends JPanel {
    /** lista wszystkich projektów użytkownika */
    private final JList<Project> users_projects;
    /** lista wszystkich zadań użytkownika */
    private final JList<Task> users_tasks;
    /** wybrany projekt */
    private Project chosen_project;
    /** wybrane zadanie */
    private Task chosen_task;
    /** przycisk otwarcia projektu */
    private JButton open_project_button;
    /** przycisk otwarcia zadania */
    private JButton open_task_button;


    // ---------------    SCENA    ---------------

    /** konstruktor sceny */
    public ProjectsScene() {
        // wczytanie projektów użytkownika
        users_projects = new JList<>(MainProgram.getProjects(MainProgram.getLoggedUser()));

        // wczytanie zadań użytkownika
        users_tasks = new JList<>(MainProgram.getTasks(MainProgram.getLoggedUser()));

        CreateScene();
    }

    /** utworzenie panelu sceny */
    private void CreateScene() {
        // nowy panel
        new JPanel();
        setLayout(new BorderLayout());
        setBorder(Styles.MAIN_BORDER);

        // dodanie elementów do panelu
        addElements();
    }

    /** dodanie elementów do sceny */
    private void addElements() {
        // dodanie nagłówka
        add(makeHeader(), BorderLayout.NORTH);

        // utworzenie panelu przechowującego listy projektów i zadań
        JPanel lists_box = new JPanel();
        lists_box.setLayout(new GridLayout(1, 2, 20, 20));

        // dodanie panelu zawierającego projekty użytkownika
        lists_box.add(createProjectsPanel());

        // dodanie panelu obsługującego zadania użytkownika
        lists_box.add(createTasksPanel());
        add(lists_box);
    }


    //  --------------- PANELE SCENY ----------------

    /** nagłówek - nazwa użytkownika i przycisk wylogowywania */
    private JPanel makeHeader() {
        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        // napis nagłówkowy
        User logged_user = MainProgram.getLoggedUser();
        Label logged_user_label = new Label("Zalogowano jako " + logged_user);
        logged_user_label.setAlignment(Label.CENTER);
        logged_user_label.setFont(Styles.HEADER_FONT);
        header.add(logged_user_label, BorderLayout.CENTER);
        // przycisk wylogowywania
        JButton logout_button = new JButton("Wyloguj się");
        logout_button.setBorder(Styles.BUTTON_BORDER);
        logout_button.setFont(Styles.BUTTON_FONT);
        logout_button.addActionListener(logoutListener());
        header.add(logout_button, BorderLayout.EAST);
        return header;
    }

    /** utworzenie panelu z listą projektów */
    private JPanel createProjectsPanel() {
        // utworzenie panelu
        JPanel panel = new JPanel();

        // konfiguracja panelu
        panel.setLayout(new BorderLayout());
        panel.setBorder(new CompoundBorder(
            BorderFactory.createEmptyBorder(10,10,10,10),
            BorderFactory.createLineBorder(Color.BLACK, 2)));

        // utworzenie nagłówka listy projektów
        Label list_header_label= new Label("Projekty:");
        list_header_label.setFont(Styles.LIST_HEADER_FONT);
        panel.add( list_header_label, BorderLayout.NORTH);

        // dodanie listy do okna
        users_projects.setFont(Styles.LIST_ELEMENT_FONT);
        panel.add(new JScrollPane(users_projects), BorderLayout.CENTER);
        users_projects.addMouseListener(ProjectsListListener());

        // dodanie przycisków sekcji
        panel.add(createProjectsButtonPanel(), BorderLayout.SOUTH);

        return panel;
    }


    /** utworzenie panelu z listą zadań */
    private JPanel createTasksPanel() {
        // utworzenie panelu
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new CompoundBorder(
                BorderFactory.createEmptyBorder(10,10,10,10),
                BorderFactory.createLineBorder(Color.BLACK, 2)));

        // utworzenie nagłówka listy zadań
        Label list_header_label= new Label("Zadania:");
        list_header_label.setFont(Styles.LIST_HEADER_FONT);
        panel.add(list_header_label, BorderLayout.NORTH);

        // dodanie listy do okna
        users_tasks.setFont(Styles.LIST_ELEMENT_FONT);
        panel.add(new JScrollPane(users_tasks), BorderLayout.CENTER);
        users_tasks.addMouseListener(TasksListListener());

        // dodanie przycisków sekcji
        panel.add(createTasksButtonPanel(), BorderLayout.SOUTH);
        return panel;
    }


    //  --------------- PANELE PRZYCISKÓW ---------------

    /** utworzenie przycisków listy zadań */
    private JPanel createTasksButtonPanel() {
        // Utworzenie panelu
        JPanel panel = new JPanel();

        // Utworzenie przycisku
        panel.setLayout(new GridLayout(1,1));

        open_task_button = new JButton("Otwórz zadanie");
        open_task_button.setFont(Styles.BUTTON_FONT);
        open_task_button.addActionListener(openTaskButtonListener());
        open_task_button.setEnabled(false);
        open_task_button.setFont(Styles.BUTTON_FONT);

        // Dodanie przycisku do okna
        panel.add(open_task_button);

        return panel;
    }

    /** utworzenie przycisków listy projektów */
    private JPanel createProjectsButtonPanel() {
        // utworzenie panelu
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,2));

        // przycisk wyboru projektu
        open_project_button = new JButton("Otwórz projekt");
        open_project_button.setEnabled(false);
        open_project_button.addActionListener(OpenProjectListener());

        // przycisk utworzenia nowego projektu
        JButton new_project_button = new JButton("Nowy projekt");
        new_project_button.addActionListener(addProjectButtonListener());

        open_project_button.setFont(Styles.BUTTON_FONT);
        new_project_button.setFont(Styles.BUTTON_FONT);

        // Dodanie przycisków do okna
        panel.add(new_project_button);
        panel.add(open_project_button);

        return panel;
    }


    //  --------------- NASŁUCHIWACZE ZDARZEŃ ---------------

    /** Wybór projektu z listy */
    private MouseAdapter ProjectsListListener() {
        return new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 1) { // jeżeli kilknięto 1 raz...
                    // pobranie numeru indeksu
                    chosen_project = users_projects.getSelectedValue();
                    // ustawienie możliwości otwarcia projektu
                    open_project_button.setEnabled(true);
                }
                else if (evt.getClickCount() == 2) { // jeżeli kilknięto 2 razy...
                    // wybieramy projekt z listy
                    Project selectedProject = users_projects.getSelectedValue();
                    if (selectedProject == null) {
                        System.out.println("Nie wybrano projektu.");
                    }
                    else {
                        MainProgram.setWindow("project_preview_scene", selectedProject);
                    }
                }
            }
        };
    }

    /** Wylogowywanie */
    private ActionListener logoutListener() {
        return e -> {
            MainProgram.setWindow("login_scene");
            MainProgram.setLoggedUser(null);
        };
    }

    /** Otwieranie projektu */
    private ActionListener OpenProjectListener() {
        return e -> MainProgram.setWindow("project_preview_scene", chosen_project);
    }

    /** Dodawanie nowego projektu */
    private ActionListener addProjectButtonListener() {
        return e -> {
            Project new_project = new Project(MainProgram.getLoggedUser());
            MainProgram.addProject(new_project);
            MainProgram.setWindow("project_preview_scene", new_project);
        };
    }

    /** Wybór zadania z listy */
    private MouseAdapter TasksListListener() {
        return new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 1) { // jeżeli kilknięto 1 raz...
                    // pobranie numeru indeksu
                    chosen_task = users_tasks.getSelectedValue();
                    // ustawienie możliwości otwarcia zadania
                    open_task_button.setEnabled(true);
                }
                else if (evt.getClickCount() == 2) { // jeżeli kilknięto 2 razy...
                    // wybieramy projekt z listy
                    Task selected = users_tasks.getSelectedValue();
                    if (selected == null) {
                        System.out.println("Nie wybrano zadania.");
                    }
                    else {
                        MainProgram.setWindow("task_preview_scene", selected);
                    }
                }
            }
        };
    }

    /** Przycisk otwierania zadania */
    private ActionListener openTaskButtonListener() {
        return e -> {
            Task selected_task = users_tasks.getSelectedValue();
            MainProgram.setWindow("task_preview_scene", selected_task);
        };
    }
}

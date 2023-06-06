package Scenes;
import Classes.Project;
import Classes.Task;
import Classes.User;
import Main.MainProgram;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/** Scena wyboru projektów i podglądu zadań użytkownika */
public class ProjectsScene extends JPanel {
    /** lista wszystkich projektów */
    JList<Project> users_projects;
    /** lista wszystkich zadań */
    JList<Task> users_tasks;
    /** wybrany projekt */
    Project chosen_project;
    /** przycisk otwarcia projektu */
    JButton open_project_button;


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
        // dodanie napisu z nazwą użytkownika
        User logged_user = MainProgram.getLoggedUser();
        Label logged_user_label = new Label("Zalogowano jako " + logged_user);
        logged_user_label.setAlignment(Label.CENTER);
        logged_user_label.setFont(Styles.HEADER_FONT);

        // utworzenie panelu przechowującego listy projektów i zadań
        JPanel lists_box = new JPanel();
        lists_box.setLayout(new GridLayout(1, 2, 20, 20));

        // dodanie panelu z obsługującego projekty użytkownika
        lists_box.add(createProjectsPanel());

        // dodanie panelu obsługującego zadania użytkownika
        lists_box.add(createTasksPanel());

        add(logged_user_label, BorderLayout.NORTH);
        add(lists_box);
    }


    //  --------------- PANELE SCENY ----------------

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
        panel.add(users_projects, BorderLayout.CENTER);
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
        panel.add(users_tasks, BorderLayout.CENTER);
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

        JButton chose_task_button = new JButton("Otwórz zadanie");
        chose_task_button.setFont(Styles.BUTTON_FONT);
        chose_task_button.addActionListener(openTaskButtonListener());


        chose_task_button.setFont(Styles.BUTTON_FONT);

        // Dodanie przycisku do okna
        panel.add(chose_task_button);

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


    //  --------------- ACTION LISTENERS  ---------------

    /** Wybór projektu z listy */
    private MouseAdapter ProjectsListListener() {
        // Nadanie dostępu do instancji wszystich projektów
        DefaultListModel<Project> all_projects = MainProgram.getProjects();

        // Zwrócenie nowego listenera listy
        return new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 1) { // jeżeli kilknięto 1 raz...
                    // pobranie numeru indeksu
                    int index = users_projects.locationToIndex(evt.getPoint());
                    // wybranie wpisu o danym indeksie
                    chosen_project = all_projects.get(index);
                    // ustawienie możliwości otwarcia projektu
                    open_project_button.setEnabled(true);
                }
                else if (evt.getClickCount() == 2) { // jeżeli kilknięto 2 razy...
                    // pobranie numeru indeksu
                    int index = users_projects.locationToIndex(evt.getPoint());
                    // wybranie wpisu o danym indeksie
                    Project selectedProject = all_projects.get(index);
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


    //  --------------- NASŁUCHIWACZE ZDARZEŃ ---------------

    /** Przycisk otwierania projektu */
    private ActionListener OpenProjectListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainProgram.setWindow("project_preview_scene", chosen_project);
            }
        };
    }

    /** dodawanie nowego projektu */
    private ActionListener addProjectButtonListener() {
        return e -> {
            Project new_project = new Project(MainProgram.getLoggedUser());
            MainProgram.getProjects().addElement(new_project);
            MainProgram.setWindow("project_preview_scene", new_project);
            System.out.println("new project added");
        };
    }

    /** Wybór zadania z listy */
    private MouseAdapter TasksListListener() {
        // Nadanie dostępu do instancji wszystich zadań
        DefaultListModel<Task> all_tasks = MainProgram.getTasks();

        return new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) { // jeżeli kilknięto 2 razy...
                    // pobranie numeru indeksu
                    int index = users_tasks.locationToIndex(evt.getPoint());
                    // wybranie wpisu o danym indeksie
                    Task selected_task = all_tasks.get(index);
                    System.out.println("Wybrano zadanie " + selected_task);
                    MainProgram.setWindow("task_preview_scene", selected_task);
                }
            }
        };
    }

    /** Przycisk otwierania zadania */
    private ActionListener openTaskButtonListener() {
        // Nadanie dostępu do instancji wszystich zadań
        DefaultListModel<Task> all_tasks = MainProgram.getTasks();
        return e -> {
            // wybranie wpisu o danym indeksie
            Task selected_task = all_tasks.getElementAt(users_tasks.getSelectedIndex());
            System.out.println("Wybrano zadanie " + selected_task);
            MainProgram.setWindow("task_preview_scene", selected_task);
        };
    }
}

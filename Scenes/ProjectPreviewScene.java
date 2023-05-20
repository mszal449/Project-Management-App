package Scenes;
import Classes.Project;
import Classes.Task;
import Main.MainProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ProjectPreviewScene extends JPanel {
    private Project project;                // Skopiowanie oryginalnej instancji projektu
    private Project original_project;       // Oryginalna instancja projektu (potrzebne do zapisania zmian)

    private JList Jusers;                   // Lista użytkowników
    private JList Jtasks;                   // Lista zadań


    static int MAIN_PADDING_H = 20;         // Odległość zawartości od granicy okna
    static int MAIN_PADDING_V = 20;


    // ---------------   CZCIONKI   ---------------

    static Font HEADER_FONT = new Font("Arial", Font.PLAIN, 26);
    static Font LIST_ELEMENT_FONT = new Font("Arial", Font.PLAIN, 20);
    static Font BUTTON_FONT = new Font("Arial", Font.PLAIN, 16);


    // ---------------    SCENA    ---------------

    // Konstruktor sceny
    public ProjectPreviewScene(Project project) throws CloneNotSupportedException {
        this.project = (Project) project.clone();

        // Wczytanie listy zadań projektu
        Jtasks = new JList(project.getTasks());

        // Wczytanie listy członków projektu
        Jusers = new JList(project.getParticipants().keySet().toArray());

        // utworzenie sceny
        createScene();
    }

    // Dodanie zawartości sceny
    private void createScene() {
        new JPanel();
        setLayout(new GridLayout(1,3, 20, 20));
        setBorder(BorderFactory.createEmptyBorder(MAIN_PADDING_H, MAIN_PADDING_V, MAIN_PADDING_H, MAIN_PADDING_V));

        add(createInfoPanel());
        add(createTaskPanel());
        add(createAsigneePanel());

        setVisible(true);
    }


    //  --------------- PANELE SCENY ----------------

    // Panel informacjami o projekcie
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        Label project_name_label = new Label("Nazwa projektu");
        project_name_label.setFont(HEADER_FONT);
        panel.add(project_name_label, BorderLayout.NORTH);

        JTextArea project_info_text = new JTextArea();
        project_info_text.setFont(LIST_ELEMENT_FONT);
        panel.add(project_info_text, BorderLayout.CENTER);

        JPanel project_button_box = createProjectInfoButtons();
        panel.add(project_button_box, BorderLayout.SOUTH);

        return panel;
    }

    // Panel z zadaniami
    private JPanel createTaskPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        Label project_name_label = new Label("Zadania");
        project_name_label.setFont(HEADER_FONT);
        panel.add(project_name_label, BorderLayout.NORTH);

        panel.add(Jtasks, BorderLayout.CENTER);
        Jtasks.addMouseListener(chooseTaskListener());

        JPanel tasks_button_box = createTasksButtons();
        panel.add(tasks_button_box, BorderLayout.SOUTH);

        return panel;
    }

    // Panel członkami projektu
    private JPanel createAsigneePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        Label project_name_label = new Label("Użytkownicy");
        project_name_label.setFont(HEADER_FONT);
        panel.add(project_name_label, BorderLayout.NORTH);

        panel.add(Jusers, BorderLayout.CENTER);

        JPanel users_button_box = createTasksButtons();
        panel.add(users_button_box, BorderLayout.SOUTH);

        return panel;
    }


    //  --------------- PANELE PRZYCISKÓW ---------------


    // Przyciski panelu z informacjami o projekcie
    private JPanel createProjectInfoButtons() {
        JPanel button_panel = new JPanel();
        button_panel.setLayout(new GridLayout(1,3, 10, 10));
        button_panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton edit_project_button = new JButton("Edytuj");
        edit_project_button.setFont(BUTTON_FONT);
        edit_project_button.addActionListener(editProjectButtonListener());
        button_panel.add(edit_project_button);

        JButton save_project_button = new JButton("Zapisz");
        save_project_button.setFont(BUTTON_FONT);
        save_project_button.addActionListener(saveButtonListener());
        button_panel.add(save_project_button);

        JButton return_button = new JButton("Powrót");
        return_button.setFont(BUTTON_FONT);
        return_button.addActionListener(returnButtonListener());
        button_panel.add(return_button);


        return button_panel;
    }

    // Przyciski panelu z zadaniami
    private JPanel createTasksButtons() {
        JPanel button_panel = new JPanel();


        button_panel.setLayout(new GridLayout(1,2, 10, 10));
        button_panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton edit_task_button = new JButton("Edytuj");
        edit_task_button.setFont(BUTTON_FONT);
        edit_task_button.addActionListener(editTaskButtonListener());
        button_panel.add(edit_task_button);

        JButton delete_task_button = new JButton("Usuń");
        delete_task_button.setFont(BUTTON_FONT);
        delete_task_button.addActionListener(deleteTaskButtonListener());
        button_panel.add(delete_task_button);

        return button_panel;
    }

    // Przyciski panelu z członkami projektu
    private JPanel createAsigneeTasksButtons() {
        JPanel button_panel = new JPanel();


        button_panel.setLayout(new GridLayout(1,3, 10, 10));
        button_panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton premissions_button = new JButton("Uprawnienia");
        premissions_button.setFont(BUTTON_FONT);
        premissions_button.addActionListener(permissionsButtonListener());
        button_panel.add(premissions_button);

        JButton add_user_button = new JButton("Dodaj");
        add_user_button.setFont(BUTTON_FONT);
        add_user_button.addActionListener(addUserListener());
        button_panel.add(add_user_button);

        JButton delete_participant_button= new JButton("Usuń");
        delete_participant_button.setFont(BUTTON_FONT);
        delete_participant_button.addActionListener(deleteUserButtonListener());
        button_panel.add(delete_participant_button);

        return button_panel;
    }


    //  --------------- ACTION LISTENERS  ---------------

    // Przycisk do edycji projektu
    private ActionListener editProjectButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainProgram.setWindow("edit_project_scene", project);
            }
        };
    }

    // Przycisk zapisywania projektu
    private ActionListener saveButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Save button");
            }
        };
    }

    // Przycisk powrotu do wyboru porjektu
    private ActionListener returnButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Return button");
            }
        };
    }

    // Przycisk edytowania zadania
    private ActionListener editTaskButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Edit Task");
            }
        };
    }

    // Przycisk usuwania zadania
    private ActionListener deleteTaskButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Delete Task");
            }
        };
    }

    // Przycisk edytowania zadania
    private ActionListener permissionsButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Edit Permissions");
            }
        };
    }

    // Przycisk edytowania zadania
    private ActionListener addUserListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Add User");
            }
        };
    }

    // Przycisk usuwania użytkownika z projektu
    private ActionListener deleteUserButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Delete User");
            }
        };
    }

    // TODO: listenery list

    // wejście do podglądu zadania po podwójnym kliknięciu
    private MouseAdapter chooseTaskListener() {
        return new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int index = Jtasks.locationToIndex(evt.getPoint());
                    Task selected = project.getTasks().get(index);
                    MainProgram.setWindow("task_preview_scene", selected);
                }
            }
        };
    }
}

package Scenes;
import Classes.Planned;
import Classes.Project;
import Classes.Task;
import Classes.User;
import Main.MainProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

// okno podglądu zadania
public class ProjectPreviewScene extends JPanel {
    private Project project;                // projekt, który edytujemy
    private Map<User, Boolean> participants_dict_copy; // kopia listy uczestników
    private DefaultListModel<Task> tasks_list_copy; // kopia listy uczestników

    private final JList<Object> Jparticipants;          // lista uczestników projektu
    private final JList<Task> Jtasks;                   // lista zadań
    private final JComboBox<User> all_users_combobox;   // lista wszystkich użytkowników aplikacji
    private final User[] users_array;                   // lista wszystkich użytkowników aplikacji
    private final boolean is_admin;                     // czy zalogowany użytkownik jest administratorem projektu?

    // ---------------   STYL   ---------------

    static Font HEADER_FONT = new Font("Arial", Font.PLAIN, 26);
    static Font LIST_ELEMENT_FONT = new Font("Arial", Font.PLAIN, 20);
    static Font BUTTON_FONT = new Font("Arial", Font.PLAIN, 16);

    static int MAIN_PADDING_H = 20;         // odległość zawartości od granicy okna
    static int MAIN_PADDING_V = 20;

    // ---------------    SCENA    ---------------

    // Konstruktor sceny
    public ProjectPreviewScene(Project project) {
        this.project = project;

        // utworzenie kopii listy zadań i listy użytkowników
        tasks_list_copy = new DefaultListModel<>();
        for (Object element : project.getTasks().toArray()) {
            tasks_list_copy.addElement((Task) element);
        }
        participants_dict_copy = new HashMap<>();
        for (Map.Entry<User, Boolean> entry : project.getParticipants().entrySet()) {
            User user = entry.getKey();
            Boolean privileges = entry.getValue();
            participants_dict_copy.put(user, privileges);
        }

        // Wczytanie listy zadań projektu
        Jtasks = new JList<>(tasks_list_copy);

        // Wczytanie listy członków projektu
        Jparticipants = new JList<>(participants_dict_copy.keySet().toArray());
        Jparticipants.setCellRenderer(new UserListCellRenderer());

        // utworzenie listy wszystkich użytkowników aplikacji
        DefaultListModel<User> usersListModel = MainProgram.getUsers();
        users_array = new User[usersListModel.size()];
        usersListModel.copyInto(users_array);
        all_users_combobox = createUsersCombobox();

        // wczytanie uprawnień zalogowanego użytkownika
        is_admin = project.getPrivileges(MainProgram.getLoggedUser());

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
        add(createAssigneePanel());

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
    private JPanel createAssigneePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        Label project_name_label = new Label("Uczestnicy");
        project_name_label.setFont(HEADER_FONT);
        panel.add(project_name_label, BorderLayout.NORTH);

        panel.add(Jparticipants, BorderLayout.CENTER);
        
        panel.add(editingParticipantsListPanel(), BorderLayout.SOUTH);

        return panel;
    }
    
    // przyciski i wybór użytkownika z listy
    private JPanel editingParticipantsListPanel() {
        JPanel buttons_and_list = new JPanel();
        buttons_and_list.setLayout(new BorderLayout());
        // panel przycisków
        JPanel users_button_box = createAssigneeButtons();
        buttons_and_list.add(users_button_box, BorderLayout.CENTER);
        // lista użytkowników
        all_users_combobox.setVisible(false);
        buttons_and_list.add(all_users_combobox, BorderLayout.NORTH);
        return buttons_and_list;
    }

    // lista wszystkich użytkowników aplikacji
    private JComboBox<User> createUsersCombobox() {
        JComboBox<User> comboBox = new JComboBox<>(users_array);
        comboBox.insertItemAt(null, 0);
        comboBox.setSelectedIndex(0);
        return comboBox;
    }


    //  --------------- PANELE PRZYCISKÓW ---------------

    // przyciski panelu z informacjami o projekcie
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

        // użytkownik nie będący administratorem nie może edytować projektu
        if (!is_admin) {
            edit_project_button.setEnabled(false);
            save_project_button.setEnabled(false);
        }

        return button_panel;
    }

    // Przyciski panelu z zadaniami
    private JPanel createTasksButtons() {
        JPanel button_panel = new JPanel();

        button_panel.setLayout(new GridLayout(1,2, 10, 10));
        button_panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton add_task_button = new JButton("Dodaj");
        add_task_button.setFont(BUTTON_FONT);
        add_task_button.addActionListener(addTaskButtonListener());
        button_panel.add(add_task_button);

        JButton delete_task_button = new JButton("Usuń");
        delete_task_button.setFont(BUTTON_FONT);
        delete_task_button.addActionListener(deleteTaskButtonListener());
        button_panel.add(delete_task_button);

        // użytkownik nie będący administratorem nie może edytować listy zadań
        if (!is_admin) {
            add_task_button.setEnabled(false);
            delete_task_button.setEnabled(false);
        }

        return button_panel;
    }

    // Przyciski panelu z członkami projektu
    private JPanel createAssigneeButtons() {
        JPanel button_panel = new JPanel();

        button_panel.setLayout(new GridLayout(1,3, 10, 10));
        button_panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton premissions_button = new JButton("Zmień uprawnienia");
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

        // użytkownik nie będący administratorem nie może edytować listy uczestników
        if (!is_admin) {
            premissions_button.setEnabled(false);
            add_user_button.setEnabled(false);
            delete_participant_button.setEnabled(false);
        }

        return button_panel;
    }


    //  --------------- ACTION LISTENERY  ---------------

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
    private void saveProject() {
        project.setTasks(tasks_list_copy);
        project.setPrivileges(participants_dict_copy);
    }
    private ActionListener saveButtonListener() {
        return e -> {
            saveProject();
            System.out.println("Save button");
        };
    }

    // Przycisk powrotu do wyboru projektu
    private ActionListener returnButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainProgram.setWindow("projects_scene");
            }
        };
    }

    // ------------------------------ EDYCJA LISTY ZADAŃ ------------------------------
    // Przycisk edytowania zadania
    private ActionListener addTaskButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Planned new_task = new Planned(project);
                tasks_list_copy.addElement(new_task);
                System.out.println("Edit Task");
            }
        };
    }

    // Przycisk usuwania zadania
    private ActionListener deleteTaskButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selected_index = Jtasks.getSelectedIndex();
                if (selected_index != -1) {
                    tasks_list_copy.remove(selected_index);
                }
                System.out.println("Delete Task: " + selected_index);
            }
        };
    }

    // wejście do podglądu zadania po podwójnym kliknięciu
    private MouseAdapter chooseTaskListener() {
        return new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int index = Jtasks.locationToIndex(evt.getPoint());
                    Task selected = tasks_list_copy.get(index);
                    if (!is_admin) MainProgram.setWindow("task_preview_scene", selected);
                    else {
                        int result = showSaveProjectDialog();
                        // zapisanie projektu i przejście do podglądu zadania
                        if (result == JOptionPane.YES_OPTION) {
                            saveProject();
                            MainProgram.setWindow("task_preview_scene", selected);
                        // przejście do podglądu zadania bez zapisywania
                        } else if (result == JOptionPane.NO_OPTION) {
                            MainProgram.setWindow("task_preview_scene", selected);
                        }
                        // jeżeli zamkniemy okno dialogowe, nic się nie stanie
                        // w szczególności nie przejdziemy do podglądu zadania
                    }
                }
            }
        };
    }

    // okno dialogowe z pytaniem o zapisanie projektu
    private int showSaveProjectDialog() {
        return JOptionPane.showOptionDialog(
                null,
                "Czy chcesz zapisać projekt przed przejściem do podglądu zadania?",
                "Zapisz",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[]{"Zapisz", "Nie"},
                "Zapisz"
        );
    }

    // ------------------------------ EDYCJA LISTY UCZESTNIKÓW ------------------------------
    // Przycisk edytowania uprawnień
    private ActionListener permissionsButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // wybór użytkownika z listy
                User user = (User) Jparticipants.getSelectedValue();
                // zapisanie aktualnych uprawnień
                boolean cur_privileges = participants_dict_copy.get(user);
                // zmiana uprawnień na "przeciwne"
                participants_dict_copy.put(user, !cur_privileges);
                // odświeżenie sceny po zmianie
                revalidate();
                repaint();
                System.out.println("Edit Permissions for " + user);
            }
        };
    }

    // Przycisk dodania użytkownika do listy uczestników projektu
    private ActionListener addUserListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!all_users_combobox.isVisible()) {
                    all_users_combobox.setSelectedIndex(0);
                    all_users_combobox.setVisible(true);
                } else {
                    int selected_index = all_users_combobox.getSelectedIndex();
                    if (selected_index > 0) {
                        User selected_user = users_array[selected_index - 1];
                        if (!participants_dict_copy.containsKey(selected_user)) {
                            participants_dict_copy.put(selected_user, false);
                            System.out.println("Add participant: " + selected_user);
                        }
                    }
                    Jparticipants.setListData(participants_dict_copy.keySet().toArray());
                    all_users_combobox.setVisible(false);
                }
            }
        };
    }

    // Przycisk usuwania użytkownika z listy uczestników projektu
    private ActionListener deleteUserButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // wybranie użytkownika z listy
                User user = (User) Jparticipants.getSelectedValue();
                // usunięcie użytkownika z aktualnego projektu
                participants_dict_copy.remove(user);
                // zaktualizowanie listy uczestników
                Jparticipants.setListData(participants_dict_copy.keySet().toArray());
                System.out.println("Delete User: " + user);
            }
        };
    }

    // wyświetlanie listy użytkowników
    private class UserListCellRenderer extends DefaultListCellRenderer {
        @Serial
        private static final long serialVersionUID = 1L;

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            User user = (User) value;
            Boolean is_admin = participants_dict_copy.get(user);

            if (is_admin != null) {
                if (is_admin) {
                    label.setText(user + " (administrator)");
                } else {
                    label.setText(user + " (uczestnik)");
                }
            }

            return label;
        }
    }
}

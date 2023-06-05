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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// okno podglądu zadania
public class ProjectPreviewScene extends JPanel {
    private final Project project;                              // projekt, który edytujemy
    private final Map<User, Boolean> participants_dict_copy;    // kopia listy uczestników
    private final DefaultListModel<Task> tasks_list_copy;       // kopia listy uczestników

    private final JList<Object> Jparticipants;                  // lista uczestników projektu
    private final JList<Task> Jtasks;                           // lista zadań
    private final JComboBox<User> all_users_combobox;           // lista wszystkich użytkowników aplikacji
    private final User[] users_array;                           // lista wszystkich użytkowników aplikacji
    private final boolean is_admin;                             // czy zalogowany użytkownik jest administratorem projektu?
    private boolean is_editor_open;

    private JPanel project_info_section;
    private JTextField name_text_field;
    private JCheckBox status_checkbox;
    protected JSpinner deadline_spinner;                        // pole wyboru daty


    // ---------------   STYL   ---------------

    static Font HEADER_FONT = new Font("Arial", Font.PLAIN, 28);
    static Font CONTENT_FONT = new Font("Arial", Font.PLAIN, 20);
    static Font LABEL_FONT = new Font("Arial", Font.PLAIN, 24);
    static Font BUTTON_FONT = new Font("Arial", Font.PLAIN, 16);

    static int MAIN_PADDING_H = 20;         // odległość zawartości od granicy okna
    static int MAIN_PADDING_V = 20;

    // ---------------   SCENA   ---------------

    // Konstruktor sceny
    public ProjectPreviewScene(Project project) {
        User[] users_array1;
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

        is_editor_open = false;

        // utworzenie sceny
        createScene();
    }

    // Dodanie zawartości sceny
    private void createScene() {
        new JPanel();
        setLayout(new GridLayout(1,3, 20, 20));
        setBorder(BorderFactory.createEmptyBorder(MAIN_PADDING_H, MAIN_PADDING_V, MAIN_PADDING_H, MAIN_PADDING_V));

        project_info_section = createProjectInfoSection();
        add(project_info_section);
        add(createTaskPanel());
        add(createAssigneePanel());

        setVisible(true);
    }


    //  --------------- PANELE SCENY ----------------

    // Panel informacjami o projekcie
    private JPanel createProjectInfoSection() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        Label project_name_label = new Label("Informacje o projekcie");
        project_name_label.setFont(HEADER_FONT);
        panel.add(project_name_label, BorderLayout.NORTH);

        JPanel info_panel_content = createInfoPanel();
        info_panel_content.setBackground(Color.WHITE);
        panel.add(info_panel_content, BorderLayout.CENTER);

        JPanel project_button_box = createProjectInfoButtons();
        panel.add(project_button_box, BorderLayout.SOUTH);

        return panel;
    }

    // wnętrze panelu
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,1));

        // nazwa projektu
        JPanel name_panel = new JPanel();
        name_panel.setBackground(Color.WHITE);
        name_panel.setLayout(new BorderLayout());

        JLabel name_label = new JLabel("Nazwa:");
        name_label.setFont(LABEL_FONT);

        JLabel name_content = new JLabel(project.getName());
        name_content.setFont(LABEL_FONT);

        name_panel.add(name_label, BorderLayout.NORTH);
        name_panel.add(name_content, BorderLayout.CENTER);


        // data końcowa projektu
        JPanel date_panel = new JPanel();
        date_panel.setBackground(Color.WHITE);
        date_panel.setLayout(new BorderLayout());

        JLabel date_label = new JLabel("Data końcowa:");
        date_label.setFont(LABEL_FONT);

        JLabel date_content = new JLabel(String.valueOf(project.getDeadline()));
        date_content.setFont(LABEL_FONT);

        date_panel.add(date_label, BorderLayout.NORTH);
        date_panel.add(date_content, BorderLayout.CENTER);


        // status projektu
        JPanel status_panel = new JPanel();
        status_panel.setBackground(Color.WHITE);
        status_panel.setLayout(new BorderLayout());

        JLabel status_label = new JLabel("Status projektu:");
        status_label.setFont(LABEL_FONT);

        JLabel status_content = new JLabel();
        status_content.setFont(LABEL_FONT);
        if (project.getStatus()) {
            status_content.setText("Ukończono");
        } else {
            status_content.setText("W trakcie realizacji");
        }

        status_panel.add(status_label, BorderLayout.NORTH);
        status_panel.add(status_content, BorderLayout.CENTER);

        panel.add(name_panel);
        panel.add(date_panel);
        panel.add(status_panel);

        return panel;
    }

    // edytor infomacji o projekcie
    private JPanel createEditorPanel() {
        JPanel main_panel = new JPanel();
        main_panel.setLayout(new BorderLayout());

        Label project_name_label = new Label("Edycja projektu");
        project_name_label.setFont(HEADER_FONT);
        main_panel.add(project_name_label, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,1));

        // nazwa projektu
        JPanel name_panel = new JPanel();
        name_panel.setBackground(Color.WHITE);
        name_panel.setLayout(new BorderLayout());

        JLabel name_label = new JLabel("Nazwa:");
        name_label.setFont(LABEL_FONT);

        name_text_field = new JTextField(project.getName());
        name_text_field.setFont(LABEL_FONT);

        name_panel.add(name_label, BorderLayout.NORTH);
        name_panel.add(name_text_field, BorderLayout.CENTER);


        // data końcowa projektu
        JPanel date_panel = new JPanel();
        date_panel.setBackground(Color.WHITE);
        date_panel.setLayout(new BorderLayout());

        JLabel date_label = new JLabel("Data końcowa:");
        date_label.setFont(LABEL_FONT);

        deadline_spinner = createDeadlineField();

        date_panel.add(date_label, BorderLayout.NORTH);
        date_panel.add(deadline_spinner, BorderLayout.CENTER);


        // status projektu
        JPanel status_panel = new JPanel();
        status_panel.setBackground(Color.WHITE);
        status_panel.setLayout(new BorderLayout());

        JLabel status_label = new JLabel("Status projektu:");
        status_label.setFont(LABEL_FONT);

        status_checkbox = new JCheckBox("Ukończono");
        status_checkbox.setFont(LABEL_FONT);
        status_checkbox.setBackground(Color.WHITE);

        status_panel.add(status_label, BorderLayout.NORTH);
        status_panel.add(status_checkbox, BorderLayout.CENTER);

        panel.add(name_panel);
        panel.add(date_panel);
        panel.add(status_panel);

        main_panel.add(panel, BorderLayout.CENTER);
        main_panel.add(createProjectEditorButtons(), BorderLayout.SOUTH);
        return main_panel;
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

    private JPanel createProjectEditorButtons() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,2, 20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(MAIN_PADDING_H, MAIN_PADDING_V, MAIN_PADDING_H, MAIN_PADDING_V));

        JButton save_button = new JButton("Zapisz");
        save_button.setFont(BUTTON_FONT);
        save_button.addActionListener(saveButtonListener());


        JButton cancel_button = new JButton("Anuluj");
        cancel_button.setFont(BUTTON_FONT);
        cancel_button.addActionListener(cancelProjectInformationListener());

        panel.add(save_button);
        panel.add(cancel_button);

        return panel;
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


    //  --------------- FUNKCJONALNOŚĆ  ---------------

    private void editProject() {
        is_editor_open = true;

        JPanel updatedPanel = createEditorPanel();
        project_info_section.removeAll();
        project_info_section.add(updatedPanel);

        project_info_section.revalidate(); // Ponowne walidowanie zawartości
        project_info_section.repaint();
    }

    // pole z datą końcową zadania
    private JSpinner createDeadlineField() {
        // utworzenie elementu wyboru daty
        SpinnerModel spinnerModel = new SpinnerDateModel();
        JSpinner spinner = new JSpinner(spinnerModel);
        spinner.setFont(LABEL_FONT);

        // konfiguracja elementu
        JSpinner.DateEditor dateEditor =
                new JSpinner.DateEditor(spinner, "dd/MM/yyyy");
        spinner.setEditor(dateEditor);
        LocalDate localDate = project.getDeadline();
        Instant instant = localDate.atStartOfDay()
                .atZone(ZoneId.systemDefault()).toInstant();
        spinner.setValue(Date.from(instant));


        return spinner;
    }


    //  --------------- ACTION LISTENERY  ---------------

    // Przycisk do edycji projektu
    private ActionListener editProjectButtonListener() {
        return e -> editProject();
    }

    private ActionListener cancelProjectInformationListener() {
        return e -> {
            MainProgram.setWindow("project_preview_scene", project);
        };
    }

    // Przycisk zapisywania projektu
    private void saveProject() {
        project.setTasks(tasks_list_copy);
        project.setPrivileges(participants_dict_copy);
        if(is_editor_open) {
            project.setName(name_text_field.getText());
            project.setDeadline(((java.util.Date)deadline_spinner.getValue())
                    .toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
            project.setStatus(status_checkbox.isSelected());
        }
    }

    private ActionListener saveButtonListener() {
        return e -> {
            saveProject();
            MainProgram.setWindow("project_preview_scene", project);
        };
    }

    // Przycisk powrotu do wyboru projektu
    private ActionListener returnButtonListener() {
        return e -> MainProgram.setWindow("projects_scene");
    }

    // ------------------------------ EDYCJA LISTY ZADAŃ ------------------------------
    // Przycisk dodawania zadania
    private ActionListener addTaskButtonListener() {
        return e -> {
            Planned new_task = new Planned(project);
            tasks_list_copy.addElement(new_task);
            System.out.println("add task");
        };
    }

    // Przycisk usuwania zadania
    private ActionListener deleteTaskButtonListener() {
        return e -> {
            int selected_index = Jtasks.getSelectedIndex();
            if (selected_index != -1) {
                tasks_list_copy.remove(selected_index);
            }
            System.out.println("Delete Task: " + selected_index);
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

    // wyświetlanie listy uczestników projektu
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

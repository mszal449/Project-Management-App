package Scenes;
import Classes.Task;
import Classes.User;
import Main.MainProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

// okno edycji zadania
public class TaskEditorScene extends JPanel{
    Task task;
    DefaultListModel<User> assignees_list_copy; // kopia listy osób aktualnie przydzielonych do zadania
    User[] participants; // lista wszystkich uczestników projektu

    JTextField name_field;
    JScrollPane description_field;
    JSpinner deadline_spinner;
    JList<User> assignees_jlist;
    JComboBox<User> all_participants_combobox;

    public TaskEditorScene(Task task) {
        this.task = task;
        CreateTaskEditorScene();
    }

    private void CreateTaskEditorScene() {
        // skopiowanie listy osób odpowiedzialnych za zadanie
        assignees_list_copy = new DefaultListModel<>();
        for (Object element : task.getAssignees().toArray()) {
            assignees_list_copy.addElement((User) element);
        }
        // lista wszystkich uczestników projektu
        participants
                = task.getProject().getParticipants().keySet().toArray(new User[0]);
        // utworzenie panelu
        new JPanel();
        setLayout(new GridLayout(5, 2));
        name_field = new JTextField(task.getName());
        description_field = createDescriptionField();
        deadline_spinner = createDeadlineField();
        all_participants_combobox = createParticipantsCombobox();
        assignees_jlist = new JList<>(assignees_list_copy);
        addElements();
    }

    private void addElements() {
        add(new JLabel("Nazwa: "));
        add(name_field);
        add(new JLabel("Opis:"));
        add(description_field);
        add(new JLabel("Data końcowa:"));
        add(deadline_spinner);
        add(assigneesListPanel());
        add(new JLabel());
        // dodanie przycisków
        JButton save_button = new JButton("Zapisz");
        save_button.addMouseListener(saveButtonListener());
        add(save_button);
        JButton cancel_button = new JButton("Anuluj");
        cancel_button.addMouseListener(cancelButtonListener());
        add(cancel_button);
    }

    // pole opisu zadania
    private JScrollPane createDescriptionField() {
        JTextArea text = new JTextArea( task.getDescription(), 2,  50);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        return new JScrollPane(text);
    }

    // pole z datą końcową projektu
    private JSpinner createDeadlineField() {
        SpinnerModel spinnerModel = new SpinnerDateModel();
        JSpinner spinner = new JSpinner(spinnerModel);
        JSpinner.DateEditor dateEditor =
                new JSpinner.DateEditor(spinner, "dd/MM/yyyy");
        spinner.setEditor(dateEditor);
        LocalDate localDate = task.getDeadline();
        Instant instant = localDate.atStartOfDay()
                .atZone(ZoneId.systemDefault()).toInstant();
        spinner.setValue(Date.from(instant));
        return spinner;
    }

    // panel listy użytkowników odpowiedzialnych za zadanie
    private JPanel assigneesListPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JLabel("Osoby odpowiedzialne"), BorderLayout.NORTH);
        panel.add(assignees_jlist, BorderLayout.CENTER);
        panel.add(editAssigneesListOptions(), BorderLayout.SOUTH);
        return panel;
    }

    // opcje edycji listy osób odpowiedzialnych za zadanie
    private JPanel editAssigneesListOptions() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(all_participants_combobox, BorderLayout.NORTH);
        all_participants_combobox.setVisible(false);
        JPanel buttons_panel = new JPanel();
        buttons_panel.setLayout(new GridLayout(1, 2));
        // pzycisk usuwania osoby z listy
        JButton delete_assignee = new JButton("Usuń");
        delete_assignee.addActionListener(e -> {
            int selected_index = assignees_jlist.getSelectedIndex();
            if (selected_index != -1) {
                assignees_list_copy.remove(selected_index);
            }
        });
        // pzycisk dodawania osoby do listy
        JButton add_assignee = new JButton("Dodaj");
        add_assignee.addActionListener(makeParticipantAssigneeListener());
        // dodanie przycisków
        buttons_panel.add(delete_assignee);
        buttons_panel.add(add_assignee);
        panel.add(buttons_panel, BorderLayout.CENTER);
        return panel;
    }

    // lista wszystkich uczestników projektu, z której możemy wybierać osobę, którą chcemy dodać do zadania
    private JComboBox<User> createParticipantsCombobox() {
        JComboBox<User> users_combo_box
                = new JComboBox<>(participants);
        users_combo_box.insertItemAt(null, 0);
        users_combo_box.setSelectedIndex(0);
        return users_combo_box;
    }

    // listener dodania wybranego uczestnika do listy osób odpowiedzialnych za zadanie
    private ActionListener makeParticipantAssigneeListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!all_participants_combobox.isVisible()) {
                    all_participants_combobox.setSelectedIndex(0);
                    all_participants_combobox.setVisible(true);
                }
                else {
                    int selected_index = all_participants_combobox.getSelectedIndex();
                    System.out.println(selected_index);
                    if (selected_index > 0) {
                        User selected_user = participants[selected_index - 1];
                        if (!assignees_list_copy.contains(selected_user)) {
                            assignees_list_copy.addElement(participants[selected_index - 1]);
                        }
                    }
                    all_participants_combobox.setVisible(false);
                }
            }
        };
    }

    // zapis zmian
    private MouseAdapter saveButtonListener() {
        return new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 1) {
                    // zapis nazwy zadania
                    task.setName(name_field.getText());
                    // zapis opisu zadania
                    JTextArea note_text = (JTextArea) description_field.getViewport().getView();
                    task.setDescription(note_text.getText());
                    // zapis daty ukończenia
                    task.setDeadline(((java.util.Date)deadline_spinner.getValue())
                            .toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
                    // zapisanie listy osób
                    task.setAssignees(assignees_list_copy);
                    // powrót do widoku
                    MainProgram.setWindow("task_preview_scene", task);
                }
            }
        };
    }

    // przycisk anulowania zmian
    private MouseAdapter cancelButtonListener() {
        return new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 1) {
                    MainProgram.setWindow("task_preview_scene", task);
                }
            }
        };
    }
}

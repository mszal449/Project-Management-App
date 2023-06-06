package Scenes;
import Classes.Planned;
import Classes.Project;
import Classes.Task;
import Classes.User;
import Main.MainProgram;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
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
public abstract class TaskEditorScene extends JPanel{
    protected Task task;                                    // edytowane zadanie
    protected DefaultListModel<User> assignees_list_copy;   // kopia listy osób aktualnie przydzielonych do zadania
    protected User[] participants;                          // lista wszystkich uczestników projektu

    protected JTextField        name_field;                 // pole tekstowe nazwy zadania
    protected JScrollPane       description_field;          // pole tekstowe opisu zadania
    protected JSpinner          deadline_spinner;           // pole wyboru daty
    protected JList<User>       assignees_jlist;            // lista uczestników zadania
    protected JComboBox<User>   all_participants_combobox;  // lista wszystkich użytkowników


    // ---------------    SCENA    ---------------

    // konstruktor
    public TaskEditorScene(Task task) {
        this.task = task;
        initializeAttributes();
        createMainPanel();
        addElements();
    }

    // utworzenie wszystkich atrybutów klasy
    protected void initializeAttributes() {
        // skopiowanie listy osób odpowiedzialnych za zadanie
        assignees_list_copy = new DefaultListModel<>();
        for (Object element : task.getAssignees().toArray()) {
            assignees_list_copy.addElement((User) element);
        }

        // lista wszystkich uczestników projektu
        participants
                = task.getProject().getParticipants().keySet().toArray(new User[0]);

        // utworzenie pól edytowalnych
        name_field = new JTextField(task.getName());
        name_field.setFont(Styles.LABEL_FONT);

        description_field = createDescriptionField();
        deadline_spinner = createDeadlineField();

        all_participants_combobox = createParticipantsCombobox();
        all_participants_combobox.setFont(Styles.LABEL_FONT);

        assignees_jlist = new JList<>(assignees_list_copy);
        assignees_jlist.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        assignees_jlist.setFont(Styles.LABEL_FONT);
    }


    //  --------------- PANELE SCENY ----------------

    // tworzenie panelu głównego
    protected abstract void createMainPanel();

    // dodanie elementów do panelu głównego
    protected abstract void addElements();

    // panel edycji nazwy zadania
    protected JPanel namePanel() {
        // utworzenie nowego panelu
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));

        // dodanie podpisu pola
        JLabel name_text = new JLabel("Nazwa: ", SwingConstants.RIGHT);
        name_text.setFont(Styles.LABEL_FONT);
        name_text.setBorder(Styles.MAIN_BORDER);

        // dodanie elementów do panelu
        panel.add(name_text);
        panel.add(name_field);

        return panel;
    }

    // panel edycji opisu zadania
    protected JPanel descriptionPanel() {
        // utworzenie nowego panelu
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));

        // utworzenie podpisu pola
        JLabel description_text_label = new JLabel("Opis: ", SwingConstants.RIGHT);
        description_text_label.setFont(Styles.LABEL_FONT);
        description_text_label.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));


        // dodanie elementów do panelu
        panel.add(description_text_label);
        panel.add(description_field);

        return panel;
    }

    //  panel edycji daty końcowej zadania
    protected JPanel deadlinePanel() {
        // utworzenie nowego panelu
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));

        // utworzenie podpisu pola
        JLabel deadline_text = new JLabel("Data końcowa: ", SwingConstants.RIGHT);
        deadline_text.setFont(Styles.LABEL_FONT);
        deadline_text.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        // dodanie elementów do panelu
        panel.add(deadline_text);
        panel.add(deadline_spinner);

        return panel;
    }

    // panel listy użytkowników odpowiedzialnych za zadanie
    protected JPanel assigneesListPanel() {
        // utworzenie nowego panelu
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));

        // dodanie opisu pola i przycisków
        JPanel name_and_buttons = new JPanel();
        name_and_buttons.setLayout(new GridLayout(2, 1, 10, 10));

        // utworzenie podpisu pola
        JLabel asignee_label = new JLabel("Osoby odpowiedzialne: ", SwingConstants.RIGHT);
        asignee_label.setFont(Styles.LABEL_FONT);

        name_and_buttons.add(asignee_label);
        name_and_buttons.setBorder(BorderFactory.createEmptyBorder(0,0, 0, 20));
        name_and_buttons.add(editAssigneesListOptions());
        panel.add(name_and_buttons);

        // dodanie elementów do panelu
        panel.add(assignees_jlist);

        return panel;
    }


    //  --------------- ELEMENTY PANELI ----------------

    // pole opisu zadania
    private JScrollPane createDescriptionField() {
        JTextArea text = new JTextArea(task.getDescription(), 2,  50);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setFont(Styles.LABEL_FONT);
        return new JScrollPane(text);
    }

    // pole z wyborem daty końcowej zadania
    private JSpinner createDeadlineField() {
        // utworzenie elementu wyboru daty
        SpinnerModel spinnerModel = new SpinnerDateModel();
        JSpinner spinner = new JSpinner(spinnerModel);
        spinner.setFont(Styles.LABEL_FONT);

        // konfiguracja elementu
        JSpinner.DateEditor dateEditor =
                new JSpinner.DateEditor(spinner, "dd/MM/yyyy");
        spinner.setEditor(dateEditor);
        LocalDate localDate = task.getDeadline();
        Instant instant = localDate.atStartOfDay()
                .atZone(ZoneId.systemDefault()).toInstant();
        spinner.setValue(Date.from(instant));

        return spinner;
    }

    // lista wyboru uczestników z projektu, których chcemy dodać do zadania
    private JComboBox<User> createParticipantsCombobox() {
        JComboBox<User> combobox
                = new JComboBox<>(participants);

        combobox.insertItemAt(null, 0);
        combobox.setSelectedIndex(0);

        return combobox;
    }

    // opcje edycji listy osób odpowiedzialnych za zadanie
    private JPanel editAssigneesListOptions() {
        // utworzenie nowego panelu
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // TODO: dodanie tutaj panelu do scrollowania?
        // dodanie listy wyboru użytkowników
        panel.add(all_participants_combobox, BorderLayout.NORTH);
        all_participants_combobox.setVisible(false);

        // dodanie panelu z przyciskamio
        JPanel buttons_panel = new JPanel();
        buttons_panel.setLayout(new GridLayout(1, 2));

        // przycisk usuwania osoby z listy
        JButton delete_assignee = new JButton("Usuń");
        delete_assignee.setFont(Styles.LABEL_FONT);
        delete_assignee.addActionListener(e -> {
            int selected_index = assignees_jlist.getSelectedIndex();
            if (selected_index != -1) {
                assignees_list_copy.remove(selected_index);
            }
        });

        // przycisk dodawania osoby do listy
        JButton add_assignee = new JButton("Dodaj");
        add_assignee.setFont(Styles.LABEL_FONT);
        add_assignee.addActionListener(assignParticipantListener());

        // dodanie przycisków do sceny
        buttons_panel.add(delete_assignee);
        buttons_panel.add(add_assignee);
        panel.add(buttons_panel, BorderLayout.CENTER);

        return panel;
    }


    //  -------------- FUNCKJONALNOŚĆ SCENY ---------------

    // zapisanie zmian
    protected void saveChanges() {
        // zapis nazwy zadania
        task.setName(name_field.getText());

        // zapis opisu zadania
        JTextArea note_text = (JTextArea) description_field.getViewport().getView();
        task.setDescription(note_text.getText());

        // zapis daty ukończenia
        task.setDeadline(((java.util.Date)deadline_spinner.getValue())
                .toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());

        // zapis listy osób
        task.setAssignees(assignees_list_copy);
    }


    //  --------------- PANELE PRZYCISKÓW ---------------

    // panel przycisków okna
    protected JPanel buttonsPanel() {
        // utworzenie nowego panelu
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.setBorder(Styles.ELEMENT_BORDER);

        // zapisywanie
        JButton save_button = new JButton("Zapisz");
        save_button.setFont(Styles.BUTTON_FONT);
        save_button.addMouseListener(saveButtonListener());
        panel.add(save_button);

        // anulowanie
        JButton cancel_button = new JButton("Anuluj");
        cancel_button.setFont(Styles.BUTTON_FONT);
        cancel_button.addMouseListener(cancelButtonListener());
        panel.add(cancel_button);

        return panel;
    }


    //  --------------- NASŁUCHIWACZE WYDARZEŃ  ---------------

    // akcja zapisu zmian
    private MouseAdapter saveButtonListener() {
        return new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 1) {
                    saveChanges();
                    MainProgram.setWindow("task_preview_scene", task);
                }
            }
        };
    }

    // akcja anulowania zmian
    protected MouseAdapter cancelButtonListener() {
        return new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 1) {
                    MainProgram.setWindow("task_preview_scene", task);
                }
            }
        };
    }

    // dodanie wybranego uczestnika do listy osób odpowiedzialnych za zadanie
    private ActionListener assignParticipantListener() {
        return e -> {
            if (!all_participants_combobox.isVisible()) {
                all_participants_combobox.setSelectedIndex(0);
                all_participants_combobox.setVisible(true);
            }
            else {
                int selected_index = all_participants_combobox.getSelectedIndex();
                if (selected_index > 0) {
                    User selected_user = participants[selected_index - 1];
                    if (!assignees_list_copy.contains(selected_user)) {
                        assignees_list_copy.addElement(participants[selected_index - 1]);
                    }
                }
                all_participants_combobox.setVisible(false);
            }
        };
    }
}


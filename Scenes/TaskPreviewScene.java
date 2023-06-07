package Scenes;

import Classes.*;
import Main.MainProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/** scena podglądu projektu */
public class TaskPreviewScene extends JPanel {
    /** wybrane zadanie */
    protected Task task;
    final int LABEL_ALIGNEMENT = SwingConstants.RIGHT;


    /** ---------------    SCENA    ---------------

    /** konstruktor sceny */
    public TaskPreviewScene(Task task) {
        this.task = task;
        CreateTaskPreviewScene();
    }

    /** utworzenie sceny */
    private void CreateTaskPreviewScene() {
        new JPanel();
        setLayout(new GridLayout(6, 1, 20, 20));
        setBorder(Styles.MAIN_BORDER);
        addElements();
    }

    /** dodanie elementów do sceny */
    private void addElements() {
        add(namePanel());
        add(descriptionPanel());
        add(statePanel());
        add(deadlinePanel());
        add(assigneesPanel());
        add(buttonsPanel());
    }

    /** sprawdzenie uprawnień użytkownika */
    private boolean checkUserPermissions() {
        Map<User, Boolean> project_permissions = task.getProject().getParticipants();
        User logged_user = MainProgram.getLoggedUser();
        return (project_permissions.get(logged_user)
                || task.getAssignees().contains(logged_user));
    }


    /**  --------------- PANELE SCENY ----------------

    /** nazwa zadania */
    private JPanel namePanel() {
        // utworzenie nowego panelu
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.setBorder(Styles.ELEMENT_SPACING_BORDER);

        // utworzenie podpisu pola z nazwą zadania
        JLabel name_field_label = new JLabel("Nazwa: ", LABEL_ALIGNEMENT);
        name_field_label.setFont(Styles.LABEL_FONT);
        panel.add(name_field_label);

        // utworzenie napisu z nazwą zadania
        JLabel name_label = new JLabel(task.getName());
        name_label.setFont(Styles.LABEL_FONT);
        panel.add(name_label);

        return panel;
    }

    /** opis zadania */
    private JPanel descriptionPanel() {
        // utworzenie nowego panelu
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.setBorder(Styles.ELEMENT_SPACING_BORDER);

        // utworzenie podpisu z pola z opisem zadania
        JLabel description_text_label = new JLabel("Opis: ", LABEL_ALIGNEMENT);
        description_text_label.setFont(Styles.LABEL_FONT);

        // utworzenie napisu z zawartością pola
        JTextArea description_text_area = new JTextArea(task.getDescription());
        description_text_area.setBorder(null);
        description_text_area.setFont(Styles.CONTENT_FONT);
        description_text_area.setEditable(false);
        description_text_area.setLineWrap(true);
        description_text_area.setWrapStyleWord(true);
        JScrollPane description_scroll_pane = new JScrollPane(description_text_area);

        panel.add(description_text_label);
        panel.add(description_scroll_pane);

        return panel;
    }

    /** data końcowa */
    private JPanel deadlinePanel() {
        // utworzenie nowego panelu
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.setBorder(Styles.ELEMENT_SPACING_BORDER);

        // utworzenie podpisu pola z datą końcową zadania
        JLabel deadline_label = new JLabel("Data końcowa: ", LABEL_ALIGNEMENT);
        deadline_label.setFont(Styles.LABEL_FONT);

        // utworzenie napisu z datą końcową
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        JLabel date_content = new JLabel(task.getDeadline().format(dateTimeFormatter));
        date_content.setFont(Styles.LABEL_FONT);

        panel.add(deadline_label);
        panel.add(date_content);

        return panel;
    }

    /** status zadania */
    private JPanel statePanel() {
        // utworzenie nowego panelu
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.setBorder(Styles.ELEMENT_SPACING_BORDER);

        // utworzenie podpisu pola ze statusem zadania
        JLabel status_label = new JLabel("Status: ", LABEL_ALIGNEMENT);
        status_label.setFont(Styles.LABEL_FONT);
        panel.add(status_label);

        // utworzenie napisu ze statusem zadania
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        if (task instanceof Planned) {
            JLabel date_label = new JLabel("planowane rozpoczęcie "
                    + ((Planned) task).getStartdate().format(dateTimeFormatter));
            date_label.setFont(Styles.LABEL_FONT);
            panel.add(date_label);

        } else {
            JLabel current_progrss_label;
            if (task instanceof Current) {
                current_progrss_label = new JLabel(((Current) task).getProgressState());

            } else {
                current_progrss_label = new JLabel("zakończono "
                        + ((Done) task).getEndDate().format(dateTimeFormatter));

            }
            current_progrss_label.setFont(Styles.LABEL_FONT);
            panel.add(current_progrss_label);
        }

        return panel;
    }

    /** lista osób odpowiedzialnych za realizację zadania */
    private JPanel assigneesPanel() {
        // utworzenie nowego panelu
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.setBorder(Styles.ELEMENT_SPACING_BORDER);

        // utworzenie podpisu pola z listą uczestnikó zadania
        JLabel participants_label = new JLabel("Uczestnicy: ", LABEL_ALIGNEMENT);
        participants_label.setFont(Styles.LABEL_FONT);
        panel.add(participants_label);

        // dodanie listy uczestników do pola
        JList<User> asignee_list= new JList<>(task.getAssignees());
        asignee_list.setFont(Styles.LABEL_FONT);
        panel.add(new JScrollPane(asignee_list));
        return panel;
    }

    /** panel z przyciskami */
    private JPanel buttonsPanel() {
        // utworzenie nowego panelu
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.setBorder(Styles.ELEMENT_SPACING_BORDER);

        // przycisk wejścia do trybu edycji zadania
        JButton edit_button = new JButton("Edytuj");
        edit_button.setFont(Styles.BUTTON_FONT);
        edit_button.addActionListener(openEditorListener());
        panel.add(edit_button);

        // przycisk zmiany statusu zadania
        JButton action_button = new JButton();
        action_button.setFont(Styles.BUTTON_FONT);

        // przycisk rozpoczęcia zadania
        if (task instanceof Planned) {
            action_button.setText("Rozpocznij zadanie");
            action_button.addActionListener(startTaskListener());
        }
        // przycisk wykonania zadania
        else if (task instanceof Current) {
            action_button.setText("Oznacz jako wykonane");
            action_button.addActionListener(finishTaskListener());
        }
        // przycisk ustawienia zadania na aktualne
        else if (task instanceof Done) {
            action_button.setText("Otwórz ponownie");
            action_button.addActionListener(openTaskListener());
        }
        panel.add(action_button);
        // zablokowanie możliwości edytowania przez osoby bez uprawnień
        if (!checkUserPermissions()) {
            edit_button.setEnabled(false);
            edit_button.setToolTipText
                    ("Nie posiadasz uprawnień do edycji tego zadania");
            action_button.setEnabled(false);
            action_button.setToolTipText
                    ("Nie posiadasz uprawnień do zmiany statusu tego zadania");
        }
        // przycisk powrotu
        JButton return_button = new JButton("Wróć");
        return_button.setFont(Styles.BUTTON_FONT);
        return_button.addActionListener(returnButtonListener());
        panel.add(return_button);
        return panel;
    }


    //  --------------- NASŁUCHIWACZE ZDARZEŃ  ---------------

    /** rozpoczęcie zadania */
    private ActionListener startTaskListener() {
        return e -> {
            Current cur_task = ((Planned)task).start();
            MainProgram.setWindow("task_preview_scene", cur_task);
        };
    }

    /** ponowne otwarcie zadania */
    private ActionListener openTaskListener() {
        return e -> {
            Current cur_task = ((Done)task).makeCurrent();
            MainProgram.setWindow("task_preview_scene", cur_task);
        };
    }

    /** wykonanie zadania */
    private ActionListener finishTaskListener() {
        return e -> {
            Done done_task = ((Current)task).setDone();
            MainProgram.setWindow("task_preview_scene", done_task);
        };
    }

    /** przycisk otwierający edytor zadania */
    private ActionListener openEditorListener() {
        return e -> MainProgram.setWindow("task_edit_scene", task);
    }

    /** przycisk powrotu */
    private ActionListener returnButtonListener() {
        return e -> MainProgram.setWindow("project_preview_scene", task.getProject());
    }
}

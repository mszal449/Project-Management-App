package Scenes;

import Classes.*;
import Main.MainProgram;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.Map;

// scena podglądu projektu
public class TaskPreviewScene extends JPanel {
    Task task; // oglądane zadanie


    // ---------------    STYL    ---------------

    Border MAIN_BORDER = BorderFactory.createEmptyBorder(20,20,20,20);
    Border ELEMENT_SPACING_BORDER = new CompoundBorder(
            BorderFactory.createEmptyBorder(10,30,10,30),
            BorderFactory.createLineBorder(Color.GRAY, 1));

    Font LABEL_FONT = new Font("Arial", Font.PLAIN, 20);
    Font CONTENT_FONT = new Font("Arial", Font.PLAIN, 18);
    int LABEL_ALIGNEMENT = SwingConstants.RIGHT;

    // ---------------    SCENA    ---------------

    public TaskPreviewScene(Task task) {
        this.task = task;
        CreateTaskPreviewScene();
    }

    private void CreateTaskPreviewScene() {
        new JPanel();
        setLayout(new GridLayout(6, 1, 20, 20));
        setBorder(MAIN_BORDER);
        addElements();
    }

    private void addElements() {
        add(namePanel());
        add(descriptionPanel());
        add(statePanel());
        add(deadlinePanel());
        add(assigneesPanel());
        add(buttonsPanel());
    }

    private boolean checkUserPermissions() {
        Map<User, Boolean> project_permissions = task.getProject().getParticipants();
        User logged_user = MainProgram.getLoggedUser();
        return (project_permissions.get(logged_user)
                || task.getAssignees().contains(logged_user));
    }


    //  --------------- PANELE SCENY ----------------

    // panel z nazwą zadania
    private JPanel namePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.setBorder(ELEMENT_SPACING_BORDER);

        JLabel name_field_label = new JLabel("Nazwa: ", LABEL_ALIGNEMENT);
        name_field_label.setFont(LABEL_FONT);
        panel.add(name_field_label);
        JLabel name_label = new JLabel(task.getName());
        name_label.setFont(LABEL_FONT);
        panel.add(name_label);
        return panel;
    }

    // panel z opisem zadania
    private JPanel descriptionPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.setBorder(ELEMENT_SPACING_BORDER);

        JLabel description_text_label = new JLabel("Opis: ", LABEL_ALIGNEMENT);
        description_text_label.setFont(LABEL_FONT);

        panel.add(description_text_label);
        panel.add(new JLabel(task.getDescription()));

        return panel;
    }

    // panel z datą końcową
    private JPanel deadlinePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.setBorder(ELEMENT_SPACING_BORDER);

        JLabel deadline_label = new JLabel("Data końcowa: ", LABEL_ALIGNEMENT);
        deadline_label.setFont(LABEL_FONT);
        panel.add(deadline_label);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        JLabel date_content = new JLabel(task.getDeadline().format(dateTimeFormatter));
        date_content.setFont(LABEL_FONT);
        panel.add(date_content);
        return panel;
    }

    // panel ze statusem zadania
    private JPanel statePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.setBorder(ELEMENT_SPACING_BORDER);

        JLabel status_label = new JLabel("Status: ", LABEL_ALIGNEMENT);
        status_label.setFont(LABEL_FONT);
        panel.add(status_label);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        if (task instanceof Planned) {
            JLabel date_label = new JLabel("planowane rozpoczęcie "
                    + ((Planned) task).getStartdate().format(dateTimeFormatter));
            date_label.setFont(LABEL_FONT);
            panel.add(date_label);
        } else if (task instanceof Current) {
            panel.add(new JLabel(((Current) task).getProgressState()));
        } else {
            panel.add(new JLabel("zakończono "
                    + ((Done) task).getEndDate().format(dateTimeFormatter)));
        }

        return panel;
    }

    // panel z listą osób odpowiedzialnych za realizację zadania
    private JPanel assigneesPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.setBorder(ELEMENT_SPACING_BORDER);

        // utworzenie podpisu pola
        JLabel participants_label = new JLabel("Uczestnicy: ", LABEL_ALIGNEMENT);
        participants_label.setFont(LABEL_FONT);
        panel.add(participants_label);

        // dodanie listy uczestników
        JList asignee_list= new JList<>(task.getAssignees());
        asignee_list.setFont(LABEL_FONT);
        panel.add(asignee_list);

        return panel;
    }

    // panel z przyciskami
    private JPanel buttonsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.setBorder(ELEMENT_SPACING_BORDER);

        // przycisk wejścia do trybu edycji zadania
        JButton edit_button = new JButton("Edytuj");
        edit_button.addActionListener(openEditorListener());
        panel.add(edit_button);

        // przycisk zmiany statusu zadania
        JButton action_button = new JButton();
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
        return_button.addActionListener(returnButtonListener());
        panel.add(return_button);
        return panel;
    }


    //  --------------- ACTION LISTENER'Y  ---------------

    // listener rozpoczęcia zadania
    private ActionListener startTaskListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Current cur_task = ((Planned)task).start();
                MainProgram.setWindow("task_preview_scene", cur_task);
            }
        };
    }

    // listener wykonania zadania
    private ActionListener openTaskListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Current cur_task = ((Done)task).makeCurrent();
                MainProgram.setWindow("task_preview_scene", cur_task);
            }
        };
    }

    // listener wykonania zadania
    private ActionListener finishTaskListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Done done_task = ((Current)task).setDone();
                MainProgram.setWindow("task_preview_scene", done_task);
            }
        };
    }

    // listener otwierający edytor zadania
    // TODO: uprawnienia - żeby tylko osoby przydzielone do zadania mogły je edytować?
    private ActionListener openEditorListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainProgram.setWindow("task_edit_scene", task);
            }
        };
    }

    // listener przycisku powrotu
    private ActionListener returnButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainProgram.setWindow("project_preview_scene", task.getProject());
            }
        };
    }
}

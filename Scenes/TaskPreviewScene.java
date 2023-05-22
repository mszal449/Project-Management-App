package Scenes;

import Classes.*;
import Main.MainProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.Map;

// scena podglądu projektu
public class TaskPreviewScene extends JPanel {
    Task task; // oglądane zadanie

    public TaskPreviewScene(Task task) {
        this.task = task;
        CreateTaskPreviewScene();
    }

    private boolean checkUserPermissions() {
        Map<User, Boolean> project_permissions = task.getProject().getParticipants();
        User logged_user = MainProgram.getLoggedUser();
        return (project_permissions.get(logged_user)
                || task.getAssignees().contains(logged_user));
    }

    private void CreateTaskPreviewScene() {
        new JPanel();
        setLayout(new GridLayout(6, 1));
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

    // panel z nazwą zadania
    private JPanel namePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.add(new JLabel("Nazwa: "));
        panel.add(new JLabel(task.getName()));
        return panel;
    }

    // panel z opisem zadania
    private JPanel descriptionPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.add(new JLabel("Opis: "));
        panel.add(new JLabel(task.getDescription()));
        return panel;
    }

    // panel z datą końcową
    private JPanel deadlinePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.add(new JLabel("Data końcowa: "));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        panel.add(new JLabel(task.getDeadline().format(dateTimeFormatter)));
        return panel;
    }

    // panel ze statusem zadania
    private JPanel statePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.add(new JLabel("Status: "));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        if (task instanceof Planned) {
            panel.add(new JLabel("planowane rozpoczęcie "
                    + ((Planned) task).getStartdate().format(dateTimeFormatter)));
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
        panel.add(new JLabel("Uczestnicy: "));
        panel.add(new JList<>(task.getAssignees()));
        return panel;
    }

    // panel z przyciskami
    private JPanel buttonsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
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

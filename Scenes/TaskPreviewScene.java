package Scenes;

import Classes.*;
import Main.MainProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;

// scena podglądu projektu
public class TaskPreviewScene extends JPanel {
    Task task;

    public TaskPreviewScene(Task task) {
        this.task = task;
        CreateTaskPreviewScene();
    }

    private void CreateTaskPreviewScene() {
        new JPanel();
        setLayout(new GridLayout(4, 1));
        addElements();
    }

    private void addElements() {
        add(namePanel());
        add(statePanel());
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
            panel.add(new JLabel(((Current) task).getProgressLevel()));
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
        // przycisk wykonania zadania
        if (task instanceof Current) {
            JButton done_button = new JButton("Oznacz jako wykonane");
            done_button.addActionListener(finishTaskListener());
            panel.add(done_button);
        }
        // przycisk rozpoczęcia zadania
        else if (task instanceof Planned) {
            JButton start_button = new JButton("Rozpocznij zadanie");
            start_button.addActionListener(startTaskListener());
            panel.add(start_button);
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

package Scenes;

import Classes.*;
import Main.MainProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
        setLayout(new GridLayout(4, 2));
        addElements();
    }

    private void addElements() {
        add(new JLabel("Nazwa: "));
        add(new JLabel(task.getName()));
        add(new JLabel("Status: "));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        if (task instanceof Planned) {
            add(new JLabel("planowane rozpoczęcie "
                    + ((Planned) task).getStartdate().format(dateTimeFormatter)));
        } else if (task instanceof Current) {
            add(new JLabel(((Current) task).getProgressLevel()));
        } else {
            add(new JLabel("zakończono "
                    + ((Done) task).getEndDate().format(dateTimeFormatter)));
        }
        add(new JLabel("Uczestnicy: "));
        add(new JList<>(task.getAssignees()));
        // przycisk wejścia do trybu edycji zadania
        // TODO: dodać uprawnienia
        JButton edit_button = new JButton("Edytuj");
        edit_button.addActionListener(openEditorListener());
        add(edit_button);
        // przycisk powrotu
        JButton return_button = new JButton("Wróć");
        return_button.addActionListener(returnButtonListener());
        add(return_button);
    }


    // przycisk otwierający edytor zadania
    // TODO: uprawnienia - żeby tylko osoby przydzielone do zadania mogły je edytować?
    private ActionListener openEditorListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainProgram.setWindow("task_edit_scene", task);
            }
        };
    }

    private ActionListener returnButtonListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainProgram.setWindow("project_preview_scene", task.getProject());
            }
        };
    }
}

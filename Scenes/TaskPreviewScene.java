package Scenes;

import Classes.Task;
import Main.MainProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// scena podglądu projektu
public class TaskPreviewScene extends JPanel {
    Task task;
    JButton edit_button;

    public TaskPreviewScene(Task task) {
        this.task = task;
        CreateTaskPreviewScene();
    }

    private void CreateTaskPreviewScene() {
        new JPanel();
        setLayout(new GridLayout(2, 1));
        addElements();
    }

    private void addElements() {
        add(new JLabel(task.getName()));
        edit_button = new JButton("Edytuj");
        edit_button.addActionListener(openEditorListener());
        add(edit_button);
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
}

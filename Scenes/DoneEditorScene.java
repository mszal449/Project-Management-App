package Scenes;

import Classes.Done;

import javax.swing.*;
import java.awt.*;

public class DoneEditorScene extends TaskEditorScene{

    public DoneEditorScene(Done task) {
        super(task);
    }

    @Override
    protected void createMainPanel() {
        new JPanel();
        setLayout(new GridLayout(6, 1));

    }

    @Override
    protected void addElements() {
        add(namePanel());
        add(descriptionPanel());
        add(deadlinePanel());
        add(assigneesListPanel());
        add(buttonsPanel());
    }
}

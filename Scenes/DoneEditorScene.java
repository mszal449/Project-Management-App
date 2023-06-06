package Scenes;

import Classes.Done;

import javax.swing.*;
import java.awt.*;

public class DoneEditorScene extends TaskEditorScene{

    // konstruktor sceny
    public DoneEditorScene(Done task) {
        super(task);
    }

    //
    @Override
    protected void createMainPanel() {
        new JPanel();
        setLayout(new GridLayout(5, 1, 20, 20));
        setBorder(Styles.MAIN_BORDER);
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

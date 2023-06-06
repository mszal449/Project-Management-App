package Scenes;

import Classes.Done;

import javax.swing.*;
import java.awt.*;

/** Edytor zakończonego zadania*/
public class DoneEditorScene extends TaskEditorScene{

    /** konstruktor */
    public DoneEditorScene(Done task) {
        super(task);
    }

    /** utworzenie głównego panelu */
    @Override
    protected void createMainPanel() {
        new JPanel();
        setLayout(new GridLayout(5, 1, 20, 20));
        setBorder(Styles.MAIN_BORDER);
    }

    /** dodanie elementów do sceny */
    @Override
    protected void addElements() {
        add(namePanel());
        add(descriptionPanel());
        add(deadlinePanel());
        add(assigneesListPanel());
        add(buttonsPanel());
    }
}

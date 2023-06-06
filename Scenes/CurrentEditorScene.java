package Scenes;

import Classes.Current;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;

public class CurrentEditorScene extends TaskEditorScene{
    private JComboBox<String> progress_level_combobox;


    // ---------------    SCENA    ---------------

    public CurrentEditorScene(Current task) {
        super(task);
    }

    // konstruktor zadania
    @Override
    protected void initializeAttributes() {
        super.initializeAttributes();
        progress_level_combobox = createLevelCombobox();
    }

    // utworzenie sceny
    @Override
    protected void createMainPanel() {
        new JPanel();
        setLayout(new GridLayout(6, 1, 20, 20));
        setBorder(Styles.MAIN_BORDER);
    }

    // dodanie elementów do sceny
    @Override
    protected void addElements() {
        add(namePanel());
        add(descriptionPanel());
        add(chooseLevelPanel());
        add(deadlinePanel());
        add(assigneesListPanel());
        add(buttonsPanel());
    }


    // ---------------   PANELE SCENY  ---------------

    // panel wyboru statusu zadania
    protected JPanel chooseLevelPanel() {
        // utworzenie nowego panelu
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));

        // utworzenie okna wybory statusu
        JLabel status_label = new JLabel("Status:", SwingConstants.RIGHT);
        status_label.setFont(Styles.LABEL_FONT);
        status_label.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        panel.add(status_label);
        panel.add(progress_level_combobox);

        return panel;
    }

    // utworzenie pola wyboru poziomu ukończenia zadania
    private JComboBox<String> createLevelCombobox() {
        JComboBox<String> combobox = new JComboBox<>(Current.progress_levels);
        combobox.setBackground(Color.WHITE);
        combobox.setFont(Styles.LABEL_FONT);
        combobox.setSelectedIndex(((Current)task).getProgressLevel());
        return combobox;
    }


    // ---------------   FUNKCJONALNOŚĆ SCENY  ---------------

    // zapisywanie zmian
    @Override
    protected void saveChanges() {
        super.saveChanges();
        // zapis statusu
        int selected_index = progress_level_combobox.getSelectedIndex();
        System.out.println(selected_index);
        ((Current) task).setProgressLevel(selected_index);
    }
}

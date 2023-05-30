package Scenes;

import Classes.Current;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;

public class CurrentEditorScene extends TaskEditorScene{
    private JComboBox<String> progress_level_combobox;


    // ---------------    STYL    ---------------

    Border MAIN_BORDER = BorderFactory.createEmptyBorder(20,20,20,20);
    Border ELEMENT_SPACING_BORDER = new CompoundBorder(
            BorderFactory.createEmptyBorder(10,30,10,30),
            BorderFactory.createLineBorder(Color.GRAY, 1));


    // ---------------    SCENA    ---------------

    public CurrentEditorScene(Current task) {
        super(task);
    }

    @Override
    protected void initializeAttributes() {
        super.initializeAttributes();
        progress_level_combobox = createLevelCombobox();
    }

    @Override
    protected void createMainPanel() {
        new JPanel();
        setLayout(new GridLayout(6, 1, 20, 20));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    }

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
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        JLabel status_label = new JLabel("Status:", SwingConstants.RIGHT);
        status_label.setFont(FONT);
        status_label.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        panel.add(status_label);
        panel.add(progress_level_combobox);
        return panel;
    }

    // TODO: opis
    private JComboBox<String> createLevelCombobox() {
        JComboBox<String> combobox = new JComboBox<>(Current.progress_levels);
        combobox.setSelectedIndex(((Current)task).getProgressLevel());
        return combobox;
    }


    // ---------------   FUNKCJONALNOŚĆ SCENY  ---------------

    // zapisywanie
    @Override
    protected void saveChanges() {
        super.saveChanges();
        // zapis statusu
        int selected_index = progress_level_combobox.getSelectedIndex();
        System.out.println(selected_index);
        ((Current) task).setProgressLevel(selected_index);
    }
}

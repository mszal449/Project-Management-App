package Scenes;

import Classes.Current;

import javax.swing.*;
import java.awt.*;

public class CurrentEditorScene extends TaskEditorScene{
    private JComboBox<String> progress_level_combobox;

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
        setLayout(new GridLayout(6, 1));
        setBorder(MAIN_BORDER);
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

    // ------------- panel wyboru statusu zadania -------------
    protected JPanel chooseLevelPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        JLabel status_label = new JLabel("Status:", SwingConstants.RIGHT);
        status_label.setFont(LABEL_FONT);
        status_label.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        panel.add(status_label);
        panel.add(progress_level_combobox);
        return panel;
    }

    private JComboBox<String> createLevelCombobox() {
        JComboBox<String> combobox = new JComboBox<>(Current.progress_levels);
        combobox.setSelectedIndex(((Current)task).getProgressLevel());
        return combobox;
    }

    // ------------- zapisywanie -------------
    @Override
    protected void saveChanges() {
        super.saveChanges();
        // zapis statusu
        int selected_index = progress_level_combobox.getSelectedIndex();
        System.out.println(selected_index);
        ((Current) task).setProgressLevel(selected_index);
    }
}

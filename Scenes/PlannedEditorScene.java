package Scenes;

import Classes.Planned;
import Classes.Project;

import javax.swing.*;
import java.awt.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class PlannedEditorScene extends TaskEditorScene{
    JSpinner start_date_spinner;

    public PlannedEditorScene(Planned task) {
        super(task);
    }

    public PlannedEditorScene(Project project) {
        super(new Planned(project));
    }

    @Override
    protected void initializeAttributes() {
        super.initializeAttributes();
        start_date_spinner = createStartDateField();
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
        add(startDatePanel());
        add(deadlinePanel());
        add(assigneesListPanel());
        add(buttonsPanel());
    }

    // ------------- panel edycji daty początkowej zadania -------------
    private JPanel startDatePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.add(new JLabel("Planowana data rozpoczęcia:"));
        panel.add(start_date_spinner);
        return panel;
    }

    // pole z datą
    private JSpinner createStartDateField() {
        SpinnerModel spinnerModel = new SpinnerDateModel();
        JSpinner spinner = new JSpinner(spinnerModel);
        JSpinner.DateEditor dateEditor =
                new JSpinner.DateEditor(spinner, "dd/MM/yyyy");
        spinner.setEditor(dateEditor);
        LocalDate localDate = ((Planned)task).getStartdate();
        Instant instant = localDate.atStartOfDay()
                .atZone(ZoneId.systemDefault()).toInstant();
        spinner.setValue(Date.from(instant));
        return spinner;
    }

    // ------------- zapisywanie -------------
    @Override
    protected void saveChanges() {
        super.saveChanges();
        // zapis planowanej daty rozpoczęcia zadania
        ((Planned) task).setStartdate(((java.util.Date)start_date_spinner.getValue())
                .toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
    }

}

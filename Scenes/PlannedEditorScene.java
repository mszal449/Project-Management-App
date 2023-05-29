package Scenes;

import Classes.Planned;
import Classes.Project;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class PlannedEditorScene extends TaskEditorScene{
    JSpinner start_date_spinner;


    // ---------------    STYL    ---------------
    Border MAIN_BORDER = BorderFactory.createEmptyBorder(20,20,20,20);
    Border ELEMENT_SPACING_BORDER = new CompoundBorder(
            BorderFactory.createEmptyBorder(10,30,10,30),
            BorderFactory.createLineBorder(Color.GRAY, 1));

    // ---------------    SCENA    ---------------

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


    //  --------------- PANELE SCENY ----------------

    @Override
    protected void createMainPanel() {
        new JPanel();
        setLayout(new GridLayout(6, 1, 20, 20));
        setBorder(MAIN_BORDER);
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

    // panel edycji daty początkowej zadania
    private JPanel startDatePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        JLabel name_text = new JLabel("Planowana data rozpoczęcia: ", SwingConstants.RIGHT);
        name_text.setFont(LABEL_FONT);
        name_text.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        panel.add(name_text);
        panel.add(start_date_spinner);
        return panel;
    }

    //  --------------- FUNKCJONALNOŚC SCENY ----------------

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

    // zapisanie zmian
    @Override
    protected void saveChanges() {
        super.saveChanges();
        // zapis planowanej daty rozpoczęcia zadania
        ((Planned) task).setStartdate(((java.util.Date)start_date_spinner.getValue())
                .toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
    }

}

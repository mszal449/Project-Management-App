// Autorzy: Julia Kulczycka, Maciej Szałasz
// Nazwa pliku: PlannedEditorScene.java
// Data ukończenia: 12.06.2023
// Opis:
// Edytor zadania, które jest w fazie planów.


package Scenes;

import Classes.Planned;
import Classes.Project;
import Main.MainProgram;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/** Edytor zadania, które jest w fazie planów */
public class PlannedEditorScene extends TaskEditorScene{
    /** czy tworzymy nowe zadanie? */
    private final boolean is_new;
    /** pole wyboru nowej daty rozpoczęcia */
    private JSpinner start_date_spinner;


    // ---------------    STYL    ---------------
    Border MAIN_BORDER = BorderFactory.createEmptyBorder(20,20,20,20);
    Border ELEMENT_SPACING_BORDER = new CompoundBorder(
            BorderFactory.createEmptyBorder(10,30,10,30),
            BorderFactory.createLineBorder(Color.GRAY, 1));

    // ---------------    SCENA    ---------------

    /** konstruktor */
    public PlannedEditorScene(Planned task) {
        super(task);
        is_new = false;
    }

    /** utworzenie atrybutów */
    @Override
    protected void initializeAttributes() {
        super.initializeAttributes();
        start_date_spinner = createStartDateField();
    }


    //  --------------- PANELE SCENY ----------------

    /** utworzenie głównego panelu */
    @Override
    protected void createMainPanel() {
        new JPanel();
        setLayout(new GridLayout(6, 1, 20, 20));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    }

    /** dodanie elementów do sceny */
    @Override
    protected void addElements() {
        add(namePanel());
        add(descriptionPanel());
        add(startDatePanel());
        add(deadlinePanel());
        add(assigneesListPanel());
        add(buttonsPanel());
    }

    /** panel edycji daty początkowej zadania */
    private JPanel startDatePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        JLabel name_text = new JLabel("Planowana data rozpoczęcia: ", SwingConstants.RIGHT);
        name_text.setFont(Styles.LABEL_FONT);
        name_text.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        panel.add(name_text);
        panel.add(start_date_spinner);
        return panel;
    }

    //  --------------- FUNKCJONALNOŚC SCENY ----------------

    /**  element wyboru daty */
    private JSpinner createStartDateField() {
        SpinnerModel spinnerModel = new SpinnerDateModel();
        JSpinner spinner = new JSpinner(spinnerModel);
        spinner.setFont(Styles.LABEL_FONT);

        JSpinner.DateEditor dateEditor =
                new JSpinner.DateEditor(spinner, "dd/MM/yyyy");
        spinner.setEditor(dateEditor);
        LocalDate localDate = ((Planned)task).getStartdate();
        Instant instant = localDate.atStartOfDay()
                .atZone(ZoneId.systemDefault()).toInstant();
        spinner.setValue(Date.from(instant));
        return spinner;
    }

    /**  zapisanie zmian */
    @Override
    protected void saveChanges() {
        super.saveChanges();
        // zapis planowanej daty rozpoczęcia zadania
        ((Planned) task)
                .setStartdate(((java.util.Date)start_date_spinner
                    .getValue())
                .toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDate());
    }

    /** anulowanie zmian */
    @Override
    protected MouseAdapter cancelButtonListener() {
        return new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 1) {
                    if (!is_new) {
                        MainProgram.setWindow("task_preview_scene",task);
                    }
                    else {
                        Project project = task.getProject();
                        project.deleteTask(task);
                        MainProgram.setWindow("project_preview_scene",
                                              project);
                    }
                }
            }
        };
    }

}

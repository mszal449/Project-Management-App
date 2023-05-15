package Scenes;
import Classes.Task;
import Main.MainProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

// okno edycji zadania
public class TaskEditorScene extends JPanel{
    Task task;
    JTextField name_field;
    JScrollPane description_field;
    JSpinner deadline_spinner;
    JButton save_button;
    JButton cancel_button;

    public TaskEditorScene(Task task) {
        this.task = task;
        CreateTaskEditorScene();
    }

    private void CreateTaskEditorScene() {
        new JPanel();
        setLayout(new GridLayout(4, 2));
        name_field = createNameField();
        description_field = createDescriptionField();
        deadline_spinner = createDeadlineField();
        save_button = createSaveButton();
        cancel_button = createCancelButton();
        addElements();
    }

    private void addElements() {
        add(new JLabel("Nazwa: "));
        add(name_field);
        add(new JLabel("Opis:"));
        add(description_field);
        add(new JLabel("Data końcowa:"));
        add(deadline_spinner);
        add(save_button);
        add(cancel_button);
    }

    // pole z nazwą zadania
    private JTextField createNameField() {
        return new JTextField(task.getName());
    }

    // pole opisu zadania
    private JScrollPane createDescriptionField() {
        JTextArea text = new JTextArea( task.getDescription(), 2,  50);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        return new JScrollPane(text);
    }

    // pole z datą końcową projektu
    private JSpinner createDeadlineField() {
        SpinnerModel spinnerModel = new SpinnerDateModel();
        JSpinner spinner = new JSpinner(spinnerModel);
        JSpinner.DateEditor dateEditor =
                new JSpinner.DateEditor(spinner, "dd/MM/yyyy");
        spinner.setEditor(dateEditor);
        LocalDate localDate = task.getDeadline();
        Instant instant = localDate.atStartOfDay()
                .atZone(ZoneId.systemDefault()).toInstant();
        spinner.setValue(Date.from(instant));
        return spinner;
    }

    // przycisk zapisu
    private JButton createSaveButton() {
        JButton button = new JButton("Zapisz");
        button.addMouseListener(saveButtonListener());
        return button;
    }

    private MouseAdapter saveButtonListener() {
        return new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 1) {
                    // zapis nazwy zadania
                    task.setName(name_field.getText());
                    // zapis opisu zadania
                    JTextArea note_text = (JTextArea) description_field.getViewport().getView();
                    task.setDescription(note_text.getText());
                    // zapis daty ukończenia
                    task.setDeadline(((java.util.Date)deadline_spinner.getValue())
                            .toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
                    // powrót do widoku
                    MainProgram.setWindow("task_preview_scene", task);
                }
            }
        };
    }

    // przycisk powrotu do widoku zadania bez zapisania zmian
    private JButton createCancelButton() {
        JButton button = new JButton("Anuluj");
        button.addMouseListener(cancelButtonListener());
        return button;
    }

    private MouseAdapter cancelButtonListener() {
        return new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 1) {
                    MainProgram.setWindow("task_preview_scene", task);
                }
            }
        };
    }
}

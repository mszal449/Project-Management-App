package Scenes;
import Classes.Project;
import Main.MainProgram;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;


public class EditProjectScene extends JPanel {
    private Project project;                // Edytowanay projekt

    private JTextField name_text_field;     // Pole tekstowe nazwy
    private JTextField day_text_field;      // Pole tekstowe dnia
    private JTextField month_text_field;    // Pole tekstowe miesiąca
    private JTextField year_text_field;     // Pole tekstowe roku

    static int MAIN_PADDING_H = 20;         // Odległość zawartości od granicy okna
    static int MAIN_PADDING_V = 20;


    // ---------------   CZCIONKI   ---------------

    static Font HEADER_FONT = new Font("Arial", Font.PLAIN, 26);
    static Font LABEL_FONT = new Font("Arial", Font.PLAIN, 22);
    static Font BUTTON_FONT = new Font("Arial", Font.PLAIN, 20);


    // ---------------    SCENA    ---------------

    // konsturktor sceny
    public EditProjectScene(Project project)  {
        this.project = project;

        createScene();
    }


    // utworzenie zawartości sceny
    private void createScene() {
        new JPanel();
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(MAIN_PADDING_H,
                                                  MAIN_PADDING_V,
                                                  MAIN_PADDING_H,
                                                  MAIN_PADDING_V));

        JLabel windowTitle = new JLabel("Edycja informacji o projekcie");
        windowTitle.setFont(HEADER_FONT);
        add(windowTitle, BorderLayout.NORTH);
        add(createMainPanel(), BorderLayout.CENTER);



        setVisible(true);
    }

    //  ---------------   PANELE SCENY   ----------------

    // główny panel sceny
    private JPanel createMainPanel() {
        JPanel main_panel = new JPanel();
        main_panel.setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,1, 20, 40));
        main_panel.setBorder(new CompoundBorder(BorderFactory.createEmptyBorder( 20, 20, 20, 20),
                                                BorderFactory.createLineBorder(Color.black, 1)));

        panel.add(createNameField());
        panel.add(createDateField());

        main_panel.add(panel, BorderLayout.CENTER);
        main_panel.add(createButtonPanel(), BorderLayout.SOUTH);

        return main_panel;
    }

    // panel edycji nazwy projektu
    private JPanel createNameField() {
        // Utworzenie panelu
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(40, 10, 40 ,10));

        // Utworzenie podpisu pola tekstowego
        JLabel name_label = new JLabel("Nowa nazwa:");
        name_label.setFont(LABEL_FONT);

        // Utworzenie pola tekstowego
        name_text_field = new JTextField(60);
        name_text_field.setText(project.getName());

        // Dodanie elementów do panelu
        panel.add(name_label, BorderLayout.NORTH);
        panel.add(name_text_field, BorderLayout.CENTER);

        // Zwrócenie panelu
        return panel;
    }

    // panel edycji planowanej daty ukończenia projektu
    private JPanel createDateField() {
        // utworzenie panelu
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(40, 10, 40 ,10));


        // utworzenie podpisu pola
        JLabel deadline_label = new JLabel("Nowa data ukończenia");
        deadline_label.setFont(LABEL_FONT);

        // Utworzenie pól tekstowych
        day_text_field = new JTextField(2);
        month_text_field = new JTextField(2);
        year_text_field = new JTextField(2);

        // Wypełnienei pól danymi projektu
        day_text_field.setText(Integer.toString(project.getDeadline().getDayOfMonth()));
        day_text_field.setMaximumSize(new Dimension(10, 40));

        month_text_field.setText(Integer.toString(project.getDeadline().getMonthValue()));
        year_text_field.setText(Integer.toString(project.getDeadline().getYear()));

        // Dodanie pól do panelu
        JPanel input_panel = new JPanel();
        input_panel.setLayout(new GridLayout(1, 3, 20, 20));
        input_panel.add(day_text_field);
        input_panel.add(month_text_field);
        input_panel.add(year_text_field);


        panel.add(deadline_label, BorderLayout.NORTH);
        panel.add(input_panel, BorderLayout.CENTER);

        return panel;
    }


    //  --------------- PANELE PRZYCISKÓW ---------------

    // panel przyciskow okna
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2, 20, 20));

        JButton save_button = new JButton("Zapisz");
        save_button.setFont(BUTTON_FONT);
        save_button.addActionListener(saveButtonActionListener());

        JButton exit_button = new JButton("Wyjście");
        exit_button.setFont(BUTTON_FONT);
        exit_button.addActionListener(exitButtonActionListener());

        panel.add(exit_button);
        panel.add(save_button);

        return panel;
    }

    //  --------------- ACTION LISTENERS  ---------------

    private ActionListener saveButtonActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Save button");
            }
        };
    }

    private ActionListener exitButtonActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainProgram.setWindow("project_preview_scene", project);
            }
        };
    }

}
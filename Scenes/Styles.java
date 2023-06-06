package Scenes;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.Color;
import java.awt.Font;

public class Styles {
    // Borders
    public static final Border MAIN_BORDER = BorderFactory.createEmptyBorder(20, 20, 20, 20);
    public static final Border ELEMENT_SPACING_BORDER = BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 30, 10, 30),
            BorderFactory.createLineBorder(Color.decode("#2b2b2b"), 1));
    public static final Border CLEAR_ELEMENT_SPACING_BORDER = BorderFactory.createEmptyBorder(10, 30, 10, 30);

    public static final Border BUTTON_BORDER = BorderFactory.createLineBorder(Color.decode("#2b2b2b"), 1);
    public static final Border ELEMENT_BORDER = BorderFactory.createLineBorder(Color.decode("#2b2b2b"), 1);

    private static final String MAIN_FONT_NAME = "Arial";
    // Fonts
    public static final Font HEADER_FONT = new Font(MAIN_FONT_NAME, Font.PLAIN, 32);
    public static final Font LOGIN_FONT = new Font(MAIN_FONT_NAME, Font.PLAIN, 24);
    public static final Font LIST_HEADER_FONT = new Font(MAIN_FONT_NAME, Font.PLAIN, 26);
    public static final Font LIST_ELEMENT_FONT = new Font(MAIN_FONT_NAME, Font.PLAIN, 22);

    public static final Font LABEL_FONT = new Font(MAIN_FONT_NAME, Font.PLAIN, 24);
    public static final Font CONTENT_FONT = new Font(MAIN_FONT_NAME, Font.PLAIN, 18);
    public static final Font BUTTON_FONT = new Font(MAIN_FONT_NAME, Font.BOLD, 22);
}
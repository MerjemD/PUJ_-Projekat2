package utils;

import javax.swing.*;
import java.awt.*;

public class ThemeManager {


    private static final Font globalFont = new Font("Monospaced", Font.PLAIN, 14);

    public static void applyTheme(JPanel panel, String theme) {
        Color bgColor;
        Color buttonColor;
        Color fgColor;

        switch (theme) {
            case "Blue" -> {
                bgColor = new Color(70, 130, 180);
                buttonColor = new Color(30, 80, 140);
                fgColor = Color.WHITE;
            }
            case "Dark" -> {
                bgColor = new Color(64, 64, 64);
                buttonColor = new Color(40, 40, 40);
                fgColor = Color.WHITE;
            }
            case "Pink" -> {
                bgColor = new Color(255, 182, 193);
                buttonColor = new Color(220, 120, 150);
                fgColor = Color.BLACK;
            }
            case "Green" -> {
                bgColor = new Color(144, 238, 144);
                buttonColor = new Color(60, 179, 60);
                fgColor = Color.BLACK;
            }
            case "Orange" -> {
                bgColor = new Color(255, 200, 0);
                buttonColor = new Color(255, 140, 0);
                fgColor = Color.BLACK;
            }
            default -> {
                bgColor = Color.LIGHT_GRAY;
                buttonColor = Color.GRAY;
                fgColor = Color.BLACK;
            }
        }


        panel.setBackground(bgColor);


        setThemeRecursively(panel, theme, bgColor, buttonColor, fgColor);
    }

    private static void setThemeRecursively(Component comp, String theme, Color bgColor, Color buttonColor, Color fgColor) {
        comp.setFont(globalFont);

        if (comp instanceof JTextField || comp instanceof JPasswordField) {
            comp.setBackground(Color.WHITE);
            comp.setForeground(Color.BLACK);
        } else if (comp instanceof JButton) {
            comp.setBackground(buttonColor);
            comp.setForeground(fgColor);
        } else if (comp instanceof JLabel) {
            comp.setForeground(fgColor);
        } else if (comp instanceof JPanel) {
            comp.setBackground(bgColor);
        }


        if (comp instanceof Container container) {
            for (Component child : container.getComponents()) {
                setThemeRecursively(child, theme, bgColor, buttonColor, fgColor);
            }


            if (comp instanceof JComboBox<?> combo) {
                combo.setFont(globalFont);
                if (combo.getEditor() != null) {
                    setThemeRecursively(combo.getEditor().getEditorComponent(), theme, bgColor, buttonColor, fgColor);
                }
            }


            if (comp instanceof JScrollPane scrollPane) {
                JViewport viewport = scrollPane.getViewport();
                if (viewport != null && viewport.getView() != null) {
                    setThemeRecursively(viewport.getView(), theme, bgColor, buttonColor, fgColor);
                }
            }
        }
    }
}



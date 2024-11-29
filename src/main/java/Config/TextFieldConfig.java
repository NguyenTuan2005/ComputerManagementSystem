package Config;

import view.Style;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class TextFieldConfig {

    public static JTextField createStyledTextField() {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Style.MODEL_PRIMARY_COLOR, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return textField;
    }

    public static JTextField createStyledTextField(String text) {
        JTextField textField = new JTextField(text,20);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Style.MODEL_PRIMARY_COLOR, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return textField;
    }

    public static JTextField createStyledTextField(Font font, Color textColor, Color borderColor, Dimension size) {
        JTextField field = new JTextField();
        field.setFont(font);
        field.setForeground(textColor);
        field.setPreferredSize(size);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        field.setBackground(Color.WHITE);
        return field;
    }

    public static JPasswordField createStyledJPasswordField(Font font, Color borderColor, Dimension size) {
        JPasswordField passwdField = new JPasswordField();
        passwdField.setEchoChar('*');
        passwdField.setFont(font);
        passwdField.setPreferredSize(size);
        passwdField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        return passwdField;
    }
}

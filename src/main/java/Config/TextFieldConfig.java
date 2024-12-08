package Config;

import view.Style;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

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

    public static JTextField createUneditableTextField(String label) {
        JTextField textField = TextFieldConfig.createStyledTextField(
                Style.FONT_TEXT_CUSTOMER,
                Color.BLACK,
                Style.MEDIUM_BLUE,
                new Dimension(350, 40)
        );

        textField.setForeground(Color.GRAY);
        textField.setEditable(false);

        return textField;
    }

    public static JTextField createTextFieldWithPlaceHolder(String text, Font font, Color textColor, Dimension size) {
        JTextField field = new JTextField(text);
        field.setForeground(textColor);
        field.setPreferredSize(size);
        field.setFont(font);
        field.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1));
        addFocusListenerForTextField(field, text);
        return field;
    }

    public static void addFocusListenerForTextField(JTextField field, String originText) {
        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setForeground(Color.BLACK);
                if (field.getText().equals(originText)) {
                    field.setText("");
                }
                field.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 4));
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Khi người dùng rời khỏi JTextField mà chưa nhập gì, sẽ hiển thị lại chữ giống originText
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(originText);
                }
                field.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1));
            }
        });
    }

    public static void resetTextField(JTextField that, String placeHolder) {
        that.setText(placeHolder);
        that.setForeground(Color.GRAY);
    }
}

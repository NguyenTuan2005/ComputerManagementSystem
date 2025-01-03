package Config;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.*;
import javax.swing.border.LineBorder;
import view.Style;

public class TextFieldConfig {

  public static JTextField createStyledTextField() {
    JTextField textField = new JTextField(15);
    textField.setFont(new Font("Arial", Font.PLAIN, 14));
    textField.setBorder(
        BorderFactory.createCompoundBorder(
            new LineBorder(Style.MODEL_PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    return textField;
  }


  public static JTextField createStyledTextField(String text) {
    JTextField textField = new JTextField(text, 20);
    textField.setFont(new Font("Arial", Font.PLAIN, 14));
    textField.setBorder(
        BorderFactory.createCompoundBorder(
            new LineBorder(Style.MODEL_PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    return textField;
  }

  public static JTextField createStyledTextField(
      Font font, Color textColor, Color borderColor, Dimension size) {
    JTextField field = new JTextField();
    field.setFont(font);
    field.setForeground(textColor);
    field.setPreferredSize(size);
    field.setBorder(
        BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(borderColor),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    field.setBackground(Color.WHITE);
    return field;
  }

  public static JTextField createUneditableTextField(String label) {
    JTextField textField =
        TextFieldConfig.createStyledTextField(
            Style.FONT_PLAIN_18, Color.BLACK, Style.MEDIUM_BLUE, new Dimension(350, 40));

    textField.setForeground(Color.GRAY);
    textField.setEditable(false);

    return textField;
  }

  public static JTextField createTextField(
      String text, Font font, Color textColor, Dimension size) {
    JTextField field = new JTextField(text);
    field.setForeground(textColor);
    field.setPreferredSize(size);
    field.setFont(font);
    field.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1));
    addFocusListenerForTextField(field, text, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
    return field;
  }

  public static JTextField createTextField(
      String text,
      Font font,
      Color textColor,
      Color backgroundColor,
      Color borderColor,
      Dimension size,
      boolean editable) {
    JTextField field = new JTextField(text);
    field.setForeground(textColor);
    field.setPreferredSize(size);
    field.setFont(font);
    field.setBackground(backgroundColor);
    field.setEditable(editable);
    field.setBorder(BorderFactory.createLineBorder(borderColor, 1));
    addFocusListenerForTextField(field, borderColor);
    return field;
  }

  public static void addFocusListenerForTextField(
      JTextField field, String originText, Color borderColor) {
    field.addFocusListener(
        new FocusListener() {
          @Override
          public void focusGained(FocusEvent e) {
            field.setForeground(Color.BLACK);
            if (field.getText().equals(originText)) {
              field.setText("");
            }
            field.setBorder(BorderFactory.createLineBorder(borderColor, 4));
          }

          @Override
          public void focusLost(FocusEvent e) {
            if (field.getText().isEmpty()) {
              field.setForeground(Color.GRAY);
              field.setText(originText);
            }
            field.setBorder(BorderFactory.createLineBorder(borderColor, 1));
          }
        });
  }

  public static void addFocusListenerForTextField(JTextField field, Color borderColor) {
    field.addFocusListener(
        new FocusListener() {
          @Override
          public void focusGained(FocusEvent e) {
            field.setBorder(BorderFactory.createLineBorder(borderColor, 4));
          }

          @Override
          public void focusLost(FocusEvent e) {
            field.setBorder(BorderFactory.createLineBorder(borderColor, 1));
          }
        });
  }

  public static void resetTextField(JTextField that, String placeHolder) {
    that.setText(placeHolder);
    that.setForeground(Color.GRAY);
  }
}

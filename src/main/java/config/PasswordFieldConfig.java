package config;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.*;
import view.Style;

public class PasswordFieldConfig {

  public static JPasswordField createPasswordField(
      String text, Font font, Color textColor, Dimension size) {
    JPasswordField pwField = new JPasswordField(text);
    pwField.setForeground(textColor);
    pwField.setPreferredSize(size);
    pwField.setFont(font);
    pwField.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1));
    pwField.setEchoChar('*');
    addFocusListenerPasswdField(pwField);
    return pwField;
  }

  public static void addFocusListenerPasswdField(JPasswordField that) {
    that.addFocusListener(
        new FocusListener() {
          @Override
          public void focusGained(FocusEvent e) {
            that.setBorder(
                BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 4));
          }

          @Override
          public void focusLost(FocusEvent e) {
            that.setBorder(
                BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1));
          }
        });
  }

  public static JPasswordField createPasswordFieldWithPlaceHolder(
      String text, Font font, Color textColor, Dimension size) {
    JPasswordField pwField = new JPasswordField(text);
    pwField.setForeground(textColor);
    pwField.setPreferredSize(size);
    pwField.setFont(font);
    pwField.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1));
    pwField.setEchoChar((char) 0);
    addFocusListenerPasswdField(pwField, text);
    return pwField;
  }

  public static JPasswordField createStyledJPasswordField(
      Font font, Color borderColor, Dimension size) {
    JPasswordField passwdField = new JPasswordField();
    passwdField.setEchoChar('*');
    passwdField.setFont(font);
    passwdField.setPreferredSize(size);
    passwdField.setBorder(
        BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(borderColor),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));

    return passwdField;
  }

  public static void addFocusListenerPasswdField(JPasswordField that, String originText) {
    that.addFocusListener(
        new FocusListener() {
          @Override
          public void focusGained(FocusEvent e) {
            String passwd = new String(that.getPassword());
            that.setForeground(Color.BLACK);
            if (passwd.equals(originText)) {
              that.setText("");
              that.setEchoChar('*');
            }
            that.setBorder(
                BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 4));
          }

          @Override
          public void focusLost(FocusEvent e) {
            String passwd = new String(that.getPassword());
            if (passwd.isEmpty()) {
              that.setText(originText);
              that.setEchoChar((char) 0);
              that.setForeground(Color.GRAY);
            }
            that.setBorder(
                BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1));
          }
        });
  }

  public static void resetPasswordField(JPasswordField that, String placeHolder) {
    that.setText(placeHolder);
    that.setForeground(Color.GRAY);
    that.setEchoChar((char) 0);
  }
}

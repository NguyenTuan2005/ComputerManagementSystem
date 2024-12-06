package Config;

import view.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class PasswordFieldConfig {

    public static JPasswordField createPasswordField(String text, Font font, Color textColor, Dimension size) {
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
        that.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                that.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 4));
            }
            @Override
            public void focusLost(FocusEvent e) {
                that.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1));
            }
        });
    }


    public static JPasswordField createPasswordFieldWithPlaceHolder(String text, Font font, Color textColor, Dimension size) {
        JPasswordField pwField = new JPasswordField(text);
        pwField.setForeground(textColor);
        pwField.setPreferredSize(size);
        pwField.setFont(font);
        pwField.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1));
        pwField.setEchoChar((char) 0);
        addFocusListenerPasswdField(pwField, text);
        return pwField;
    }



    public static void addFocusListenerPasswdField(JPasswordField that, String originText) {
        that.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                String passwd = new String(that.getPassword());
                // Khi người dùng nhấn vào JTextField, nếu vẫn là chữ giống originText, nó sẽ biến mất
                that.setForeground(Color.BLACK);
                if (passwd.equals(originText)) {
                    that.setText("");
                    that.setEchoChar('*');
                }
                that.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 4));

            }

            @Override
            public void focusLost(FocusEvent e) {
                String passwd = new String(that.getPassword());
                // Khi người dùng rời khỏi JTextField mà chưa nhập gì, sẽ hiển thị lại chữ giống originText
                if (passwd.isEmpty()) {
                    that.setText(originText);
                    that.setEchoChar((char) 0);
                    that.setForeground(Color.GRAY);
                }
                that.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1));
            }
        });
    }

    public static void resetPasswordField(JPasswordField that, String placeHolder) {
        that.setText(placeHolder);
        that.setForeground(Color.GRAY);
        that.setEchoChar((char) 0);
    }
}

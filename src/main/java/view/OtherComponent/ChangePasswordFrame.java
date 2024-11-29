package view.OtherComponent;

import Config.ButtonConfig;
import Config.TextFieldConfig;
import view.Style;

import javax.swing.*;
import java.awt.*;

public class ChangePasswordFrame extends JFrame {
    JPasswordField oldPasswordField, newPasswordField, confirmPasswordField;
    JButton showOldPasswd, showNewPasswd, showRetypeNewPasswd;

    public ChangePasswordFrame() {
        setTitle("Change Password");
        setSize(600, 350);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setIconImage(new ImageIcon("src/main/java/Icon/logo.png").getImage());
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel oldPasswdLabel = new JLabel("Old Password: ");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        add(oldPasswdLabel, gbc);

        oldPasswordField = TextFieldConfig.createStyledJPasswordField(Style.FONT_TEXT_CUSTOMER, Style.MEDIUM_BLUE, new Dimension(350, 40));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        add(oldPasswordField, gbc);

        showOldPasswd = ButtonConfig.createShowPasswdButton(oldPasswordField);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        add(showOldPasswd, gbc);

        JLabel newPasswdLabel = new JLabel("New Password: ");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(newPasswdLabel, gbc);

        newPasswordField = TextFieldConfig.createStyledJPasswordField(Style.FONT_TEXT_CUSTOMER, Style.MEDIUM_BLUE, new Dimension(350, 40));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(newPasswordField, gbc);

        showNewPasswd = ButtonConfig.createShowPasswdButton(newPasswordField);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(showNewPasswd, gbc);

        JLabel confirmPasswdLabel = new JLabel("Confirm Password: ");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(confirmPasswdLabel, gbc);

        confirmPasswordField = TextFieldConfig.createStyledJPasswordField(Style.FONT_TEXT_CUSTOMER, Style.MEDIUM_BLUE, new Dimension(350, 40));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(confirmPasswordField, gbc);

        showRetypeNewPasswd = ButtonConfig.createShowPasswdButton(confirmPasswordField);
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(showRetypeNewPasswd, gbc);
    }

    public static void main(String[] args) {
        new ChangePasswordFrame().showVisible();
    }

    public void showVisible() {
        setVisible(true);
    }
}

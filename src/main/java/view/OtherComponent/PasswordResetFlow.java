package view.OtherComponent;

import javax.swing.*;
import java.awt.*;

public class PasswordResetFlow extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Color primaryBlue = new Color(52, 152, 219);
    private Font titleFont = new Font("Arial", Font.BOLD, 24);
    private Font labelFont = new Font("Arial", Font.PLAIN, 14);

    // Tạo các icon
    private ImageIcon userIcon = new ImageIcon("src/main/java/Icon/lock_icon.png");
    private ImageIcon lockIcon = new ImageIcon("src/main/java/Icon/lock_icon.png");
    private ImageIcon emailIcon = new ImageIcon("src/main/java/Icon/lock_icon.png");

    public PasswordResetFlow() {
        // Constructor code remains the same
        setTitle("Password Reset");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createEmailPanel(), "email");
        mainPanel.add(createOTPPanel(), "otp");
        mainPanel.add(createNewPasswordPanel(), "newPassword");

        add(mainPanel);
    }

    // Helper method to create input field with icon
    private JPanel createInputWithIcon(JTextField textField, ImageIcon icon, int y, String labelText) {
        JPanel inputPanel = new JPanel(null);
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBounds(50, y, 300, 60);

        // Label
        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        label.setBounds(0, 0, 300, 20);
        inputPanel.add(label);

        // Icon
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setBounds(5, 25, 20, 20);
        inputPanel.add(iconLabel);

        // Text field with adjusted position
        textField.setBounds(30, 25, 270, 30);
        inputPanel.add(textField);

        return inputPanel;
    }

    private JPanel createEmailPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        // Logo
        ImageIcon logo = new ImageIcon("path/to/your/logo.png");
        JLabel logoLabel = new JLabel(logo);
        logoLabel.setBounds(150, 30, 100, 100);
        panel.add(logoLabel);

        // Title
        JLabel titleLabel = new JLabel("Reset Password", SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(primaryBlue);
        titleLabel.setBounds(0, 150, 400, 30);
        panel.add(titleLabel);

        // Email field with icon
        JTextField emailField = new JTextField();
        panel.add(createInputWithIcon(emailField, emailIcon, 200, "Email Address"));

        // Send Code Button
        JButton sendButton = new JButton("Send Code");
        sendButton.setBounds(50, 280, 300, 40);
        sendButton.setBackground(primaryBlue);
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);
        sendButton.addActionListener(e -> cardLayout.show(mainPanel, "otp"));
        panel.add(sendButton);

        return panel;
    }

    private JPanel createOTPPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        // Title
        JLabel titleLabel = new JLabel("Enter Verification Code", SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(primaryBlue);
        titleLabel.setBounds(0, 50, 400, 30);
        panel.add(titleLabel);

        // OTP field with icon
        JTextField otpField = new JTextField();
        panel.add(createInputWithIcon(otpField, lockIcon, 100, "Verification Code"));

        // Verify Button
        JButton verifyButton = new JButton("Verify Code");
        verifyButton.setBounds(50, 180, 300, 40);
        verifyButton.setBackground(primaryBlue);
        verifyButton.setForeground(Color.WHITE);
        verifyButton.setFocusPainted(false);
        verifyButton.addActionListener(e -> cardLayout.show(mainPanel, "newPassword"));
        panel.add(verifyButton);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 230, 300, 40);
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(primaryBlue);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "email"));
        panel.add(backButton);

        return panel;
    }

    private JPanel createNewPasswordPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        // Title
        JLabel titleLabel = new JLabel("Set New Password", SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(primaryBlue);
        titleLabel.setBounds(0, 50, 400, 30);
        panel.add(titleLabel);

        // New Password field with icon
        JPasswordField newPassField = new JPasswordField();
        panel.add(createInputWithIcon(newPassField, lockIcon, 100, "New Password"));

        // Confirm Password field with icon
        JPasswordField confirmField = new JPasswordField();
        panel.add(createInputWithIcon(confirmField, lockIcon, 170, "Confirm Password"));

        // Show Password Checkbox
        JCheckBox showPassCheckbox = new JCheckBox("Show Password");
        showPassCheckbox.setBounds(50, 240, 300, 20);
        showPassCheckbox.setBackground(Color.WHITE);
        showPassCheckbox.addActionListener(e -> {
            if (showPassCheckbox.isSelected()) {
                newPassField.setEchoChar((char) 0);
                confirmField.setEchoChar((char) 0);
            } else {
                newPassField.setEchoChar('•');
                confirmField.setEchoChar('•');
            }
        });
        panel.add(showPassCheckbox);

        // Reset Button
        JButton resetButton = new JButton("Reset Password");
        resetButton.setBounds(50, 280, 300, 40);
        resetButton.setBackground(primaryBlue);
        resetButton.setForeground(Color.WHITE);
        resetButton.setFocusPainted(false);
        resetButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(panel, "Password reset successfully!");
            dispose();
        });
        panel.add(resetButton);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 330, 300, 40);
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(primaryBlue);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "otp"));
        panel.add(backButton);

        return panel;
    }
}
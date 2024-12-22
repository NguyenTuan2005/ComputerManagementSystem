package view.OtherComponent;

import Config.ButtonConfig;
import Config.CurrentUser;
import Config.PasswordFieldConfig;
import controller.AccountController;
import controller.CustomerController;
import java.awt.*;
import javax.swing.*;
import view.Style;

public class ChangePasswordFrame extends JFrame {
  private JPasswordField[] passwordFields;
  private JButton[] showPasswordButtons;
  private String role;

  public ChangePasswordFrame(String role) {
    this.role = role;
    initializeFrame();
    getRootPane()
        .setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Style.MODEL_PRIMARY_COLOR, 3),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
    createTitlePanel();
    createPasswordPanel();
    createButtonPanel();
  }

  private void initializeFrame() {
    setTitle("Change Password");
    setSize(600, 300);
    setLocationRelativeTo(null);
    setUndecorated(true);
    setIconImage(new ImageIcon("src/main/java/Icon/logo.png").getImage());
    setLayout(new BorderLayout());
  }

  private void createTitlePanel() {
    JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JLabel titleLabel = new JLabel("CHANGE PASSWORD");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    titleLabel.setForeground(Style.MODEL_PRIMARY_COLOR);
    titlePanel.add(titleLabel);
    add(titlePanel, BorderLayout.NORTH);
  }

  private void createPasswordPanel() {
    String[] labels = {"Old Password: ", "New Password: ", "Confirm Password: "};

    PasswordInputPanel passwordInputPanel = new PasswordInputPanel(labels);
    add(passwordInputPanel, BorderLayout.CENTER);
  }

  private void createButtonPanel() {
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

    JButton cancelButton = ButtonConfig.createStyledButton("Cancel");
    cancelButton.setForeground(Style.WORD_COLOR_BLACK);
    ButtonConfig.addButtonHoverEffect(
        cancelButton, ButtonConfig.BUTTON_HOVER_COLOR, ButtonConfig.BUTTON_COLOR);
    JButton confirmButton = ButtonConfig.createStyledButton("Confirm");
    confirmButton.setForeground(Style.WORD_COLOR_BLACK);
    ButtonConfig.addButtonHoverEffect(
        confirmButton, ButtonConfig.BUTTON_HOVER_COLOR, ButtonConfig.BUTTON_COLOR);

    cancelButton.addActionListener(e -> dispose());
    confirmButton.addActionListener(e -> changePassword());

    buttonPanel.add(cancelButton);
    buttonPanel.add(confirmButton);

    add(buttonPanel, BorderLayout.SOUTH);
  }

  private void changePassword() {
    String oldPassword = new String(passwordFields[0].getPassword());
    String newPassword = new String(passwordFields[1].getPassword());
    String confirmPassword = new String(passwordFields[2].getPassword());

    if (!validateInputFields(oldPassword, newPassword, confirmPassword)) {
      return;
    }

    try {
      if (!CurrentUser.CURRENT_MANAGER.authenticateOldPassword(oldPassword)
          || !CurrentUser.CURRENT_CUSTOMER.authenticateOldPassword(oldPassword)) {
        JOptionPane.showMessageDialog(
            this, "Incorrect old password.", "Authentication Error", JOptionPane.ERROR_MESSAGE);
        return;
      }

      boolean passwordChanged = updatePassword(newPassword);

      if (passwordChanged) {
        JOptionPane.showMessageDialog(
            this, "Password successfully changed.", "Success", JOptionPane.INFORMATION_MESSAGE);
        dispose();
      } else {
        JOptionPane.showMessageDialog(
            this,
            "Failed to update password. Please try again.",
            "Update Error",
            JOptionPane.ERROR_MESSAGE);
      }

    } catch (Exception e) {
      JOptionPane.showMessageDialog(
          this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      e.printStackTrace();
    }
  }

  private boolean validateInputFields(
      String oldPassword, String newPassword, String confirmPassword) {
    if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
      JOptionPane.showMessageDialog(
          this,
          "All password fields must be filled.",
          "Validation Error",
          JOptionPane.WARNING_MESSAGE);
      return false;
    }

    if (!newPassword.equals(confirmPassword)) {
      JOptionPane.showMessageDialog(
          this,
          "New password and confirm password do not match.",
          "Password Mismatch",
          JOptionPane.WARNING_MESSAGE);
      return false;
    }

    if (oldPassword.equals(newPassword)) {
      JOptionPane.showMessageDialog(
          this,
          "New password cannot be the same as the old password.",
          "Password Error",
          JOptionPane.WARNING_MESSAGE);
      return false;
    }
    return true;
  }

  private boolean updatePassword(String newPassword) {
    try {
      switch (role) {
        case ("Customer") -> new CustomerController()
            .updatePassword(newPassword, CurrentUser.CURRENT_CUSTOMER.getId());
        case ("Manager") -> new AccountController()
            .updatePassword(newPassword, CurrentUser.CURRENT_MANAGER.getAccountId());
        default -> JOptionPane.showMessageDialog(
            this, "Invalid role", "Error", JOptionPane.ERROR_MESSAGE);
      }

      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  private class PasswordInputPanel extends JPanel {
    public PasswordInputPanel(String[] labels) {
      setLayout(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();

      passwordFields = new JPasswordField[labels.length];
      showPasswordButtons = new JButton[labels.length];

      gbc.insets = new Insets(5, 5, 5, 5);
      gbc.fill = GridBagConstraints.HORIZONTAL;

      for (int i = 0; i < labels.length; i++) {
        JLabel label = new JLabel(labels[i]);
        gbc.gridx = 0;
        gbc.gridy = i;
        gbc.gridwidth = 1;
        add(label, gbc);

        passwordFields[i] =
            PasswordFieldConfig.createStyledJPasswordField(
                Style.FONT_PLAIN_18, Style.MEDIUM_BLUE, new Dimension(350, 40));
        gbc.gridx = 1;
        gbc.gridy = i;
        add(passwordFields[i], gbc);

        showPasswordButtons[i] = ButtonConfig.createShowPasswdButton(passwordFields[i]);
        gbc.gridx = 2;
        gbc.gridy = i;
        add(showPasswordButtons[i], gbc);
      }
    }
  }

  public void showVisible() {
    setVisible(true);
  }
}

package view.OverrideComponent;

import Config.ButtonConfig;
import Model.Supplier;
import dao.SupplierDAO;
import view.Style;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;

public class AddSupplierFrame extends JFrame {
    private JTextField companyNameField, emailField, phoneNumberField, addressField, dateField;
    private JButton clearButton, saveButton, cancelButton;

    private SupplierDAO supplierDAO;

    public static void main(String[] args) {
        new AddSupplierFrame().showFrame();
    }

    public AddSupplierFrame() {
        setTitle("Add Supplier");
        setSize(900, 750);
        setLocationRelativeTo(null);
        setUndecorated(true);

        // Create the main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add components using GridBagLayout
        // Company Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Company Name:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        companyNameField = new JTextField(20);
        mainPanel.add(companyNameField, gbc);

        // email
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        mainPanel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        emailField = new JTextField(20);
        mainPanel.add(emailField, gbc);

        // Phone Number
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        mainPanel.add(new JLabel("Phone Number:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        phoneNumberField = new JTextField(20);
        mainPanel.add(phoneNumberField, gbc);

        // Address
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        mainPanel.add(new JLabel("Address:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        addressField = new JTextField(20);
        mainPanel.add(addressField, gbc);

        // Contract Date
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        mainPanel.add(new JLabel("Contract Date:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        dateField = new JTextField(20);
        mainPanel.add(dateField, gbc);

        supplierDAO = new SupplierDAO();

        // Button Panel
        ButtonPanel buttonPanel = new ButtonPanel();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.PAGE_END;
        mainPanel.add(buttonPanel, gbc);

        // Add the main panel to frame
        add(mainPanel);
    }

    class ButtonPanel extends JPanel {
        public ButtonPanel() {
            setLayout(new FlowLayout(FlowLayout.RIGHT));

            clearButton = ButtonConfig.createStyledButton("Clear All");
            clearButton.setForeground(Style.WORD_COLOR_BLACK);
            ButtonConfig.addButtonHoverEffect(clearButton, ButtonConfig.BUTTON_HOVER_COLOR, ButtonConfig.BUTTON_COLOR);
            saveButton = ButtonConfig.createStyledButton("Save");
            saveButton.setForeground(Style.WORD_COLOR_BLACK);
            ButtonConfig.addButtonHoverEffect(saveButton, ButtonConfig.BUTTON_HOVER_COLOR, ButtonConfig.BUTTON_COLOR);
            cancelButton = ButtonConfig.createStyledButton("Cancel");
            cancelButton.setForeground(Style.WORD_COLOR_BLACK);
            ButtonConfig.addButtonHoverEffect(cancelButton, ButtonConfig.BUTTON_HOVER_COLOR, ButtonConfig.BUTTON_COLOR);

            add(clearButton);
            add(cancelButton);
            add(saveButton);

            // Add action listeners
            clearButton.addActionListener(e -> clearFields());

            cancelButton.addActionListener(e -> dispose());

            saveButton.addActionListener(e -> {
                String result = saveSupplier();
                switch (result) {
                    case "success" -> dispose();
                    case "phone" -> phoneNumberField.requestFocus();
                    case "date" -> dateField.requestFocus();
                }
            });
        }

        private void clearFields() {
            companyNameField.setText("");
            emailField.setText("");
            phoneNumberField.setText("");
            addressField.setText("");
            dateField.setText("");
        }

        private String saveSupplier() {
            try {
                //Get all the values
                Supplier supplier = new Supplier();
                supplier.setCompanyName(companyNameField.getText());
                supplier.setEmail(emailField.getText());
                supplier.setPhoneNumber(checkPhoneNum(phoneNumberField.getText()));
                supplier.setAddress(addressField.getText());
                supplier.setContractDate(checkDate(dateField.getText()));

                supplierDAO.save(supplier);
                clearFields();
                return "success";
            } catch (IllegalArgumentException e) {
                showMessageDialog(e.getMessage());

                if (e.getMessage().contains("Phone number")) {
                    return "phone";
                } else if (e.getMessage().contains("date")) {
                    return "date";
                }
                return "error";
            }
        }

        private String checkPhoneNum(String phoneNum) {
            //Check if phone number is at least 10 characters
            if (phoneNum.length() != 10)
                throw new IllegalArgumentException("Phone number must be 10 digits");

            //Check if all characters are digits
            if (!phoneNum.chars().allMatch(Character::isDigit))
                throw new IllegalArgumentException("Phone number must contain only digits");
            return phoneNum;
        }

        private Date checkDate(String dateStr) {
            try {
                return Date.valueOf(dateStr);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Wrong date format. Please enter the date in YYYY-MM-DD format.");
            }
        }

        private void showMessageDialog(String message) {
            JOptionPane.showMessageDialog(null, message, "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to show the frame
    public void showFrame() {
        setVisible(true);
    }
}

package view.OverrideComponent;

import javax.swing.*;
import java.awt.*;

public class AddSupplierFrame extends JFrame {
    private JTextField companyNameField, emailField, phoneNumberField, addressField;
    private JButton saveButton, cancelButton, clearButton;

    public static void main(String[] args) {
        new AddSupplierFrame();
    }
    public AddSupplierFrame() {
        setTitle("Add Supplier");
        setSize(400, 300);
        setLocationRelativeTo(null); // Center on screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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

        // Email
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

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        clearButton = new JButton("Clear All");
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        buttonPanel.add(clearButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.PAGE_END;
        mainPanel.add(buttonPanel, gbc);

        // Add action listeners
        clearButton.addActionListener(e -> clearFields());

        cancelButton.addActionListener(e -> dispose());

        saveButton.addActionListener(e -> {
            // Add your save logic here
            saveSupplier();
            dispose();
        });

        // Add the main panel to frame
        add(mainPanel);
        setVisible(true);
    }

    private void clearFields() {
        companyNameField.setText("");
        emailField.setText("");
        phoneNumberField.setText("");
        addressField.setText("");
    }

    private void saveSupplier() {
        // Get all the values
        String companyName = companyNameField.getText();
        String email = emailField.getText();
        String phoneNumber = phoneNumberField.getText();
        String address = addressField.getText();

        // Add your save logic here
        System.out.println("Saving supplier:");
        System.out.println("Company Name: " + companyName);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phoneNumber);
        System.out.println("Address: " + address);
    }

    // Method to show the frame
    public void showFrame() {
        setVisible(true);
    }
}

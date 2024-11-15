package view.OtherComponent;

import Config.ButtonConfig;
import Config.TextFieldConfig;
import Model.Supplier;
import dao.SupplierDAO;
import view.Style;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class AddSupplierFrame extends JFrame {
    private JTextField companyNameField, emailField, phoneNumberField, addressField, dateField;
    private JButton clearButton, saveButton, cancelButton;

    private SupplierDAO supplierDAO;
    private Runnable updateCallback;

    public AddSupplierFrame(Runnable updateCallback) {
        this.updateCallback = updateCallback;
        initializeFrame();
    }

    // Initialize frame properties and add components
    private void initializeFrame() {
        setTitle("Adding Supplier");
        setSize(600, 350);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setIconImage(new ImageIcon("src/main/java/Icon/logo.png").getImage());

        // Main panel with a border
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Style.MODEL_PRIMARY_COLOR, Style.WORD_COLOR_BLACK));

        TitlePanel titlePanel = new TitlePanel();
        mainPanel.add(titlePanel);

        // Center panel with form fields
        CenterPanel centerPanel = new CenterPanel();
        mainPanel.add(centerPanel);

        // Button Panel
        ButtonPanel buttonPanel = new ButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the main panel to the frame
        add(mainPanel);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        supplierDAO = new SupplierDAO();
    }

    private class TitlePanel extends JPanel {
        public TitlePanel() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
            JLabel titleLabel = new JLabel("SUPPLIER INFORMATION");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            titleLabel.setForeground(Style.MODEL_PRIMARY_COLOR);
            add(titleLabel);
        }
    }

    // Inner class to manage form fields
    private class CenterPanel extends JPanel {
        public CenterPanel() {
            setLayout(new GridBagLayout());
            setBorder(BorderFactory.createCompoundBorder(
                    null,
                    BorderFactory.createEmptyBorder(20, 20, 20, 20)
            ));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(5, 5, 5, 5);

            // Form labels and fields
            String[] labels = {"Company Name:", "Email:", "Phone Number:", "Address:", "Contract Date:"};
            JTextField[] fields = {
                    companyNameField = TextFieldConfig.createStyledTextField(),
                    emailField = TextFieldConfig.createStyledTextField(),
                    phoneNumberField = TextFieldConfig.createStyledTextField(),
                    addressField = TextFieldConfig.createStyledTextField(),
                    dateField = TextFieldConfig.createStyledTextField()
            };

            // Add form components using a loop
            for (int i = 0; i < labels.length; i++) {
                gbc.gridx = 0;
                gbc.gridy = i;
                JLabel label = new JLabel(labels[i]);
                label.setFont(new Font("Arial", Font.BOLD, 14));
                label.setForeground(Style.MODEL_PRIMARY_COLOR);
                add(label, gbc);

                gbc.gridx = 1;
                gbc.weightx = 1.0;
                add(fields[i], gbc);
            }
        }
    }

    private class ButtonPanel extends JPanel {
        public ButtonPanel() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));

            clearButton = ButtonConfig.createStyledButton("CLEAR ALL");
            clearButton.setForeground(Style.WORD_COLOR_BLACK);
            ButtonConfig.addButtonHoverEffect(clearButton, ButtonConfig.BUTTON_HOVER_COLOR, ButtonConfig.BUTTON_COLOR);
            saveButton = ButtonConfig.createStyledButton("SAVE");
            saveButton.setForeground(Style.WORD_COLOR_BLACK);
            ButtonConfig.addButtonHoverEffect(saveButton, ButtonConfig.BUTTON_HOVER_COLOR, ButtonConfig.BUTTON_COLOR);
            cancelButton = ButtonConfig.createStyledButton("CANCEL");
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
                    case "success" -> {
                        dispose();
                        if (updateCallback != null) {
                            updateCallback.run();// Trigger table update in main frame
                        }
                    }
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
                supplier.setPhoneNumber(Supplier.checkPhoneNum(phoneNumberField.getText()));
                supplier.setAddress(addressField.getText());
                supplier.setContractDate(Supplier.checkDate(dateField.getText()));

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

        private void showMessageDialog(String message) {
            JOptionPane.showMessageDialog(null, message, "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to show the frame
    public void showFrame() {
        setVisible(true);
    }
}

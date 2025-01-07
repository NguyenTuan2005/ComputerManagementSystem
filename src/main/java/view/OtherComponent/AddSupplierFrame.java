package view.otherComponent;

import config.ButtonConfig;
import config.TextFieldConfig;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.BevelBorder;

import entity.Supplier;
import view.LoginFrame;
import view.Style;
import view.overrideComponent.ToastNotification;

public class AddSupplierFrame extends JFrame {
    private JTextField companyNameField, emailField, phoneNumberField, addressField, dateField;
    private JButton clearButton, saveButton, cancelButton;
    private  Supplier newSupplier;

    private Runnable updateCallback;

    public AddSupplierFrame(Runnable updateCallback) {
        this.updateCallback = updateCallback;
        initializeFrame();
    }

    private void initializeFrame() {
        setTitle("Adding Supplier");
        setSize(600, 350);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setIconImage(new ImageIcon("src/main/java/Icon/logo.png").getImage());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(
                BorderFactory.createBevelBorder(
                        BevelBorder.RAISED, Style.MODEL_PRIMARY_COLOR, Style.WORD_COLOR_BLACK));

        TitlePanel titlePanel = new TitlePanel();
        mainPanel.add(titlePanel);

        CenterPanel centerPanel = new CenterPanel();
        mainPanel.add(centerPanel);

        ButtonPanel buttonPanel = new ButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }


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


    private class CenterPanel extends JPanel {
        public CenterPanel() {
            setLayout(new GridBagLayout());
            setBorder(
                    BorderFactory.createCompoundBorder(
                            null, BorderFactory.createEmptyBorder(20, 20, 20, 20)));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(5, 5, 5, 5);

            String[] labels = {"Company Name:", "Email:", "Phone Number:", "Address:", "Contract Date:"};
            JTextField[] fields = {
                    companyNameField = TextFieldConfig.createStyledTextField(),
                    emailField = TextFieldConfig.createStyledTextField(),
                    phoneNumberField = TextFieldConfig.createStyledTextField(),
                    addressField = TextFieldConfig.createStyledTextField(),
                    dateField = TextFieldConfig.createStyledTextField()
            };


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
            ButtonConfig.addButtonHoverEffect(clearButton, Style.BUTTON_HOVER_COLOR, Style.BUTTON_COLOR);
            saveButton = ButtonConfig.createStyledButton("SAVE");
            saveButton.setForeground(Style.WORD_COLOR_BLACK);
            ButtonConfig.addButtonHoverEffect(
                    saveButton, ButtonConfig.BUTTON_HOVER_COLOR, ButtonConfig.BUTTON_COLOR);
            cancelButton = ButtonConfig.createStyledButton("CANCEL");
            cancelButton.setForeground(Style.WORD_COLOR_BLACK);
            ButtonConfig.addButtonHoverEffect(
                    cancelButton, ButtonConfig.BUTTON_HOVER_COLOR, ButtonConfig.BUTTON_COLOR);

            add(clearButton);
            add(cancelButton);
            add(saveButton);

            clearButton.addActionListener(e -> clearFields());

            cancelButton.addActionListener(e -> dispose());

            saveButton.addActionListener(
                    e -> {
                        var sup = saveSupplier();
                        if(sup != null) {
                            dispose();
                        }

                        if (updateCallback != null) {
                            updateCallback.run();
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

        private Supplier saveSupplier() {

            if (isValidDate(dateField.getText())) {
                newSupplier = Supplier.builder()
                        .id(1).companyName(companyNameField.getText())
                        .email(emailField.getText())
                        .phoneNumber(phoneNumberField.getText())
                        .address(addressField.getText())
                        .contractDate(LocalDate.parse(dateField.getText()))
                        .isActive(true).build();
                LoginFrame.COMPUTER_SHOP.addSupplier(newSupplier);
                ToastNotification.showToast("OK",3000,30,-1,-1);
                System.out.println( LoginFrame.COMPUTER_SHOP.getAllSupplier());
            }
            clearFields();

            return newSupplier;
        }

        private boolean isValidDate(String inputDate) {
            String dateFormat = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            sdf.setLenient(false);
            try {
                Date date = sdf.parse(inputDate);
                return !date.after(new Date());
            } catch (ParseException e) {
                showMessageDialog("format: yyyy-MM-dd");
                return false;
            }
        }

        private void showMessageDialog(String message) {
            JOptionPane.showMessageDialog(null, message, "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void showFrame() {
        setVisible(true);
    }
}

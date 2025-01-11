package view.otherComponent;

import config.ButtonConfig;
import config.TextFieldConfig;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import model.Supplier;
import view.LoginFrame;
import view.Style;

public class ModifySupplierFrame extends JFrame {

  private JButton cancelButton;
  private JButton saveButton;
  private JButton undoButton;
  private JTextField dateField;
  private JTextField addressField;
  private JTextField phoneNumberField;
  private JTextField emailField;
  private JTextField companyNameField;

  private CenterPanel centerPanel;
  private ButtonPanel buttonPanel;

  private Runnable updateCallback;
  private Supplier supplier;

  private Map<JTextField, Stack<String>> fieldHistoryMap = new HashMap<>();
  private Stack<JTextField> modificationOrder = new Stack<>();

  public ModifySupplierFrame(Runnable updateCallback, Supplier supplier) {
    this.updateCallback = updateCallback;
    this.supplier = supplier;
    initializeFrame();
  }

  // Initialize frame properties and add components
  private void initializeFrame() {
    setTitle("Modifying Supplier");
    setSize(600, 350);
    setLocationRelativeTo(null);
    setUndecorated(true);
    setIconImage(new ImageIcon("src/main/java/Icon/logo.png").getImage());

    // Main panel with a border
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBorder(
        BorderFactory.createBevelBorder(
            BevelBorder.RAISED, Style.MODEL_PRIMARY_COLOR, Style.WORD_COLOR_BLACK));

    TitlePanel titlePanel = new TitlePanel();
    mainPanel.add(titlePanel, BorderLayout.NORTH);

    // Center panel with form fields
    centerPanel = new CenterPanel();
    mainPanel.add(centerPanel, BorderLayout.CENTER);

    // Button Panel
    buttonPanel = new ButtonPanel();
    mainPanel.add(buttonPanel, BorderLayout.SOUTH);

    // Add the main panel to the frame
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
      JLabel titleLabel = new JLabel("MODIFYING INFORMATION");
      titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
      titleLabel.setForeground(Style.MODEL_PRIMARY_COLOR);
      add(titleLabel);
    }
  }

  // Inner class to manage form fields
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
        companyNameField = TextFieldConfig.createStyledTextField(supplier.getCompanyName()),
        emailField = TextFieldConfig.createStyledTextField(supplier.getEmail()),
        phoneNumberField = TextFieldConfig.createStyledTextField(supplier.getPhoneNumber()),
        addressField = TextFieldConfig.createStyledTextField(supplier.getAddress()),
        dateField =
            TextFieldConfig.createStyledTextField(String.valueOf(supplier.getContractDate()))
      };
      emailField.setEnabled(false);

      // Initialize history for each field
      for (JTextField field : fields) {
        fieldHistoryMap.put(
            field,
            new Stack<>() {
              {
                push(field.getText());
              }
            });
        saveFieldState(field, modificationOrder); // Save the initial state
      }

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

        fields[i].addFocusListener(new SaveFieldListener(fields[i]));
      }
    }

    private class SaveFieldListener implements FocusListener {
      private JTextField field;

      public SaveFieldListener(JTextField field) {
        this.field = field;
      }

      @Override
      public void focusGained(FocusEvent e) {}

      @Override
      public void focusLost(FocusEvent e) {
        saveFieldState(field, modificationOrder);
      }
    }

    private void saveFieldState(JTextField field, Stack<JTextField> modificationOrder) {
      Stack<String> history = fieldHistoryMap.get(field);
      if (history.size() > 1 || !history.peek().equals(field.getText())) {
        history.push(field.getText());
        modificationOrder.add(field);
      }
    }
  }

  private class ButtonPanel extends JPanel {
    public ButtonPanel() {
      setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));

      undoButton = ButtonConfig.createStyledButton("UNDO");
      undoButton.setForeground(Style.WORD_COLOR_BLACK);
      ButtonConfig.addButtonHoverEffect(
          undoButton, ButtonConfig.BUTTON_HOVER_COLOR, ButtonConfig.BUTTON_COLOR);
      saveButton = ButtonConfig.createStyledButton("SAVE");
      saveButton.setForeground(Style.WORD_COLOR_BLACK);
      ButtonConfig.addButtonHoverEffect(
          saveButton, ButtonConfig.BUTTON_HOVER_COLOR, ButtonConfig.BUTTON_COLOR);
      cancelButton = ButtonConfig.createStyledButton("CANCEL");
      cancelButton.setForeground(Style.WORD_COLOR_BLACK);
      ButtonConfig.addButtonHoverEffect(
          cancelButton, ButtonConfig.BUTTON_HOVER_COLOR, ButtonConfig.BUTTON_COLOR);

      add(undoButton);
      add(cancelButton);
      add(saveButton);

      // Add action listeners
      undoButton.addActionListener(e -> undoFields());

      cancelButton.addActionListener(e -> dispose());

      saveButton.addActionListener(
          e -> {
            String result = saveSupplier();
            switch (result) {
              case "success" -> {
                dispose();
                if (updateCallback != null) {
                  updateCallback.run(); // Trigger table update in main frame
                }
              }
              case "phone" -> phoneNumberField.requestFocus();
              case "date" -> dateField.requestFocus();
            }
          });
    }

    // Undo the most recent modification for the last modified field
    private void undoFields() {
      if (!modificationOrder.isEmpty()) {
        JTextField field = modificationOrder.pop();
        Stack<String> history = fieldHistoryMap.get(field);
        if (history.size() > 1) {
          history.pop();
          field.setText(history.peek()); // Restore to the previous state
        }
      }
    }

    private String saveSupplier() {
      try {
        if (isValidDate(dateField.getText())) {
          supplier.setCompanyName(companyNameField.getText());
          supplier.setEmail(emailField.getText());
          supplier.setPhoneNumber(phoneNumberField.getText());
          supplier.setAddress(addressField.getText());
          supplier.setContractDate(LocalDate.parse(dateField.getText()));
          System.out.println("update " + supplier);
          LoginFrame.COMPUTER_SHOP.updateSupplier(supplier);
          return "success";
        }
      } catch (IllegalArgumentException e) {
        showMessageDialog(e.getMessage());

        if (e.getMessage().contains("Phone number")) {
          return "phone";
        } else if (e.getMessage().contains("date")) {
          return "date";
        }
        return "error";
      }
      return "";
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

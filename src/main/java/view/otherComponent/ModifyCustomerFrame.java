package view.otherComponent;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import model.Customer;
import view.LoginFrame;
import view.Style;


public class ModifyCustomerFrame extends JFrame {

  private JTextField fullNameField, emailField, addressField;
  private JLabel avatarLabel;
  private String contextPath = "";
  boolean cancel = true;
  private Customer customer;
  private Runnable callMethodReload;


  private static final Color DARK_BLUE = new Color(0, 75, 150);
  private static final Color MEDIUM_BLUE = new Color(51, 153, 255);
  private static final Color LIGHT_BLUE = new Color(235, 245, 255);
  private static final Color HOVER_BLUE = new Color(30, 144, 255);

  public ModifyCustomerFrame(Customer customer,Runnable callMethodReload) {
    this.customer = customer;
    this.callMethodReload = callMethodReload;
    this.contextPath = customer.getAvatarImg();
    System.out.println(" before :" + customer);
    setTitle("Customer Information");
    setSize(500, 650);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setUndecorated(true);

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout(15, 15));
    mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
    mainPanel.setBackground(LIGHT_BLUE);

    JPanel formPanel = new JPanel(new GridLayout(6, 2, 15, 15));
    formPanel.setBackground(LIGHT_BLUE);

    fullNameField = createStyledTextField(customer.getFullName());
    emailField = createStyledTextField(customer.getEmail());
    addressField = createStyledTextField(customer.getAddress());

    // Create password panel with toggle button
    JPanel passwordPanel = new JPanel(new BorderLayout(5, 0));
    passwordPanel.setBackground(LIGHT_BLUE);

    // Create toggle password button
    JToggleButton togglePassword =
        new JToggleButton(new ImageIcon("src/main/java/img/eye-closed.png"));
    togglePassword.setPreferredSize(new Dimension(30, 30));
    togglePassword.setBackground(MEDIUM_BLUE);
    togglePassword.setBorder(BorderFactory.createLineBorder(MEDIUM_BLUE));
    togglePassword.setFocusPainted(false);

    // Add hover effect for toggle button
    togglePassword.addMouseListener(
        new java.awt.event.MouseAdapter() {
          public void mouseEntered(java.awt.event.MouseEvent evt) {
            togglePassword.setBackground(HOVER_BLUE);
          }

          public void mouseExited(java.awt.event.MouseEvent evt) {
            togglePassword.setBackground(MEDIUM_BLUE);
          }
        });

    JLabel[] labels = {
      createStyledLabel("Full Name:"),
      createStyledLabel("Email:"),
      createStyledLabel("Address:"),
      createStyledLabel("Avatar Image:")
    };

    avatarLabel = new JLabel("Drag and drop an image here", SwingConstants.CENTER);
    avatarLabel.setPreferredSize(new Dimension(150, 150));
    avatarLabel.setBackground(Color.WHITE);
    avatarLabel.setOpaque(true);
    avatarLabel.setBorder(BorderFactory.createLineBorder(MEDIUM_BLUE, 2));
    avatarLabel.setTransferHandler(new ImageTransferHandler());

    formPanel.add(labels[0]);
    formPanel.add(fullNameField);
    formPanel.add(labels[1]);
    formPanel.add(emailField);
    formPanel.add(labels[2]);
    formPanel.add(addressField);

    JPanel avatarPanel = new JPanel(new BorderLayout());
    avatarPanel.setBackground(LIGHT_BLUE);
    avatarPanel.add(labels[3], BorderLayout.NORTH);
    avatarPanel.add(avatarLabel, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(LIGHT_BLUE);
    buttonPanel.setBorder(new EmptyBorder(15, 0, 0, 0));

    JButton saveButton = createStyledButton("UPDATE");
    JButton clearButton = createStyledButton("UNDO");
    JButton cancelButton = createStyledButton("CANCEL");

    saveButton.addActionListener(e -> handleSave());
    clearButton.addActionListener(e -> handleClear());
    cancelButton.addActionListener(e -> handleCancel());

    buttonPanel.add(saveButton);
    buttonPanel.add(Box.createHorizontalStrut(10));
    buttonPanel.add(clearButton);
    buttonPanel.add(Box.createHorizontalStrut(10));
    buttonPanel.add(cancelButton);

    JPanel southPanel = new JPanel(new BorderLayout());
    southPanel.setBackground(LIGHT_BLUE);
    southPanel.add(avatarPanel, BorderLayout.CENTER);
    southPanel.add(buttonPanel, BorderLayout.SOUTH);

    mainPanel.add(formPanel, BorderLayout.CENTER);
    mainPanel.add(southPanel, BorderLayout.SOUTH);

    JLabel titleLabel = new JLabel("Customer Modify", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    titleLabel.setForeground(DARK_BLUE);
    titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
    mainPanel.add(titleLabel, BorderLayout.NORTH);
    // Set default image
    setDefaultImage();

    setContentPane(mainPanel);
    setLocationRelativeTo(null);
    this.setVisibleA(true);
  }

  public void setVisibleA(boolean visible) {
    this.setVisible(visible);
  }

  private JButton createStyledButton(String text) {
    JButton button = new JButton(text);
    button.setPreferredSize(new Dimension(100, 35));
    button.setBackground(MEDIUM_BLUE);
    button.setForeground(Color.BLACK);
    button.setFont(new Font("Arial", Font.BOLD, 14));
    button.setFocusPainted(false);
    button.setBorder(BorderFactory.createRaisedBevelBorder());

    // Add hover effect
    button.addMouseListener(
        new java.awt.event.MouseAdapter() {
          public void mouseEntered(java.awt.event.MouseEvent evt) {
            button.setBackground(HOVER_BLUE);
          }

          public void mouseExited(java.awt.event.MouseEvent evt) {
            button.setBackground(MEDIUM_BLUE);
          }
        });

    return button;
  }

  private Customer createCustomer() {

    this.customer.changeAddress(addressField.getText());
    this.customer.changeFullName(fullNameField.getText());
    this.customer.changeAvatarImg(contextPath);
    this.customer.changeEmail(emailField.getText());
    System.out.println("update " + customer);
    return this.customer;
  }

  private void handleSave() {
    // Add your save logic here
//    JOptionPane.showMessageDialog(this, "Update button clicked!");
    LoginFrame.COMPUTER_SHOP.updateUserInfor(createCustomer());
    System.out.println("update :" + createCustomer());
//    JOptionPane.showMessageDialog(this, "Updated successfully");
    if(callMethodReload!= null) callMethodReload.run();
    setVisible(false);
  }

  private void handleClear() {
    fullNameField.setText(this.customer.getFullName());
    emailField.setText(this.customer.getEmail());
    addressField.setText(this.customer.getAddress());
    setDefaultImage();
  }

  private void handleCancel() {
    int result =
        JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to cancel?",
            "Confirm Cancel",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

    if (result == JOptionPane.YES_OPTION) {
      dispose();
    }
  }

  private JTextField createStyledTextField(String text) {
    JTextField field = new JTextField(text);
    field.setFont(Style.FONT_PLAIN_13);
    field.setPreferredSize(new Dimension(200, 30));
    field.setBorder(
        BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(MEDIUM_BLUE),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    field.setBackground(Color.WHITE);
    return field;
  }

  private JPasswordField createStyledPasswordField() {
    JPasswordField field = new JPasswordField(" null");
    field.setPreferredSize(new Dimension(200, 30));
    field.setBorder(
        BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(MEDIUM_BLUE),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    field.setBackground(Color.WHITE);
    return field;
  }

  private JLabel createStyledLabel(String text) {
    JLabel label = new JLabel(text);
    label.setFont(new Font("Arial", Font.BOLD, 14));
    label.setForeground(DARK_BLUE);
    return label;
  }

  private void setDefaultImage() {
    try {
      Path defaultImagePath = Paths.get(this.customer.getAvatarImg());
      ImageIcon defaultIcon = new ImageIcon(defaultImagePath.toString());
      Image scaledImage = defaultIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
      avatarLabel.setIcon(new ImageIcon(scaledImage));
      avatarLabel.setText(""); // Clear text when default image is set
    } catch (Exception e) {
      System.out.println("Default image not found in src/img/");
      e.printStackTrace();
    }
  }

  private class ImageTransferHandler extends TransferHandler {
    @Override
    public boolean canImport(TransferSupport support) {
      return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
    }

    @Override
    public boolean importData(TransferSupport support) {
      if (!canImport(support) && !cancel) return false;

      try {
        Transferable transferable = support.getTransferable();
        java.util.List<File> files =
            (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
        if (!files.isEmpty()) {
          File file = files.get(0);
          String fileName = file.getName();
          String nameImg = String.valueOf(files.hashCode());
          contextPath = "src/main/java/img/" + nameImg + fileName;
          System.out.println("contextPart " + contextPath);
          Path targetPath = Paths.get(contextPath);

          Files.createDirectories(targetPath.getParent());
          Files.copy(file.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);

          ImageIcon avatarIcon = new ImageIcon(targetPath.toString());
          Image scaledImage = avatarIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
          avatarLabel.setIcon(new ImageIcon(scaledImage));
          avatarLabel.setText("");

          return true;
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
      return false;
    }
  }
}

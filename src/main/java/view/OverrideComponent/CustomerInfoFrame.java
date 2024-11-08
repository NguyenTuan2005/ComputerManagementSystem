package view.OverrideComponent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class CustomerInfoFrame extends JFrame {

    private JTextField idField, fullNameField, emailField, addressField;
    private JPasswordField passwordField;
    private JLabel avatarLabel;
    String contextPath= "";
    boolean cancel = true; // coi code chay ko

    // Define blue color scheme
    private static final Color DARK_BLUE = new Color(0, 75, 150);
    private static final Color MEDIUM_BLUE = new Color(51, 153, 255);
    private static final Color LIGHT_BLUE = new Color(235, 245, 255);
    private static final Color HOVER_BLUE = new Color(30, 144, 255);

    public CustomerInfoFrame() {
        setTitle("Customer Information");
        setSize(500, 650); // Increased height for buttons
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(LIGHT_BLUE);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 15, 15));
        formPanel.setBackground(LIGHT_BLUE);

        // Initialize and style fields
        idField = createStyledTextField();
        fullNameField = createStyledTextField();
        emailField = createStyledTextField();
        addressField = createStyledTextField();
        passwordField = createStyledPasswordField();

        // Style labels
        JLabel[] labels = {
                createStyledLabel("ID:"),
                createStyledLabel("Full Name:"),
                createStyledLabel("Email:"),
                createStyledLabel("Address:"),
                createStyledLabel("Password:"),
                createStyledLabel("Avatar Image:")
        };

        // Avatar panel
        avatarLabel = new JLabel("Drag and drop an image here", SwingConstants.CENTER);
        avatarLabel.setPreferredSize(new Dimension(150, 150));
        avatarLabel.setBackground(Color.WHITE);
        avatarLabel.setOpaque(true);
        avatarLabel.setBorder(BorderFactory.createLineBorder(MEDIUM_BLUE, 2));
        avatarLabel.setTransferHandler(new ImageTransferHandler());

        // Add components to form panel
        formPanel.add(labels[0]); formPanel.add(idField);
        formPanel.add(labels[1]); formPanel.add(fullNameField);
        formPanel.add(labels[2]); formPanel.add(emailField);
        formPanel.add(labels[3]); formPanel.add(addressField);
        formPanel.add(labels[4]); formPanel.add(passwordField);

        // Create avatar panel
        JPanel avatarPanel = new JPanel(new BorderLayout());
        avatarPanel.setBackground(LIGHT_BLUE);
        avatarPanel.add(labels[5], BorderLayout.NORTH);
        avatarPanel.add(avatarLabel, BorderLayout.CENTER);

        // Create buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(LIGHT_BLUE);
        buttonPanel.setBorder(new EmptyBorder(15, 0, 0, 0));

        // Create and style buttons
        JButton saveButton = createStyledButton("Save");
        JButton clearButton = createStyledButton("Clear");
        JButton cancelButton = createStyledButton("Cancel");

        // Add button actions
        saveButton.addActionListener(e -> handleSave());
        clearButton.addActionListener(e -> handleClear());
        cancelButton.addActionListener(e -> handleCancel());

        // Add buttons to panel with spacing
        buttonPanel.add(saveButton);
        buttonPanel.add(Box.createHorizontalStrut(10)); // Add space between buttons
        buttonPanel.add(clearButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(cancelButton);

        // Create a container panel for avatar and buttons
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBackground(LIGHT_BLUE);
        southPanel.add(avatarPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add panels to main panel
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        // Add title panel
        JLabel titleLabel = new JLabel("Customer Information", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(DARK_BLUE);
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Add main panel to frame
        setContentPane(mainPanel);

        // Center frame on screen
        setLocationRelativeTo(null);
        setVisible(true);
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
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(HOVER_BLUE);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(MEDIUM_BLUE);
            }
        });

        return button;
    }

    private void handleSave() {
        // Add your save logic here
        JOptionPane.showMessageDialog(this, "Save button clicked!");
    }

    private void handleClear() {
        idField.setText("");
        fullNameField.setText("");
        emailField.setText("");
        addressField.setText("");
        passwordField.setText("");
        avatarLabel.setIcon(null);
        avatarLabel.setText("Drag and drop an image here");
    }

    private void handleCancel() {
        int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to cancel?",
                "Confirm Cancel",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(200, 30));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(MEDIUM_BLUE),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        field.setBackground(Color.WHITE);
        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setPreferredSize(new Dimension(200, 30));
        field.setBorder(BorderFactory.createCompoundBorder(
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
                List<File> files = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
                if (!files.isEmpty()) {
                    File file = files.get(0);
                    String fileName = file.getName();
                    String nameImg = String.valueOf(files.hashCode());
                    String contextPath = "src/main/java/img/" + nameImg + fileName;
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

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(CustomerInfoFrame::new);
    }
}
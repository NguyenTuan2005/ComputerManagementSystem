package view;
import Model.Product;
import Model.Supplier;
import dao.ProductDAO;
import dao.SupplierDAO;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ProductInputForm extends JFrame {
    private JTextField txtId;
    private JComboBox<String> cmbSupplierId; // Thay txtSupplierId thành JComboBox
    private JTextField txtName;
    private JTextField txtQuality;
    private JTextField txtPrice;
    private JTextField txtGenre;
    private JTextField txtBrand;
    private JTextField txtOS;
    private JTextField txtCPU;
    private JTextField txtMemory;
    private JTextField txtRAM;
    private JTextField txtMadeIn;
    private JComboBox<String> cmbStatus;
    private JButton btnSave;
    private JButton btnClear;
    private SupplierDAO supplierDAO;

    // Định nghĩa các màu sắc
    private final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private final Color PANEL_BACKGROUND = Color.WHITE;
    private final Color BUTTON_COLOR = new Color(41, 128, 185);
    private final Color BUTTON_HOVER_COLOR = new Color(52, 152, 219);

    public ProductInputForm() {
        // Thiết lập JFrame
        setTitle("QUẢN LÝ SẢN PHẨM");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND_COLOR);

        // Tạo panel chính
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel tiêu đề
        JPanel titlePanel = createTitlePanel();

        // Panel nội dung
        JPanel contentPanel = createContentPanel();

        // Panel nút
        JPanel buttonPanel = createButtonPanel();

        // Thêm các panel vào panel chính
        mainPanel.add(titlePanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(contentPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(buttonPanel);

        // Thêm panel chính vào JFrame
        add(new JScrollPane(mainPanel));

        // Cài đặt look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JPanel createTitlePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND_COLOR);

        JLabel titleLabel = new JLabel("THÔNG TIN SẢN PHẨM");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);
        panel.add(titleLabel);

        return panel;
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(PANEL_BACKGROUND);
        panel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(PRIMARY_COLOR, 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Khởi tạo components với style
        initializeStyledComponents();

        // Thêm components vào panel với layout
        addStyledComponents(panel, gbc);

        return panel;
    }

    private void initializeStyledComponents() {
        // Khởi tạo các JTextField còn lại
        txtId = createStyledTextField();
        txtName = createStyledTextField();
        txtQuality = createStyledTextField();
        txtPrice = createStyledTextField();
        txtGenre = createStyledTextField();
        txtBrand = createStyledTextField();
        txtOS = createStyledTextField();
        txtCPU = createStyledTextField();
        txtMemory = createStyledTextField();
        txtRAM = createStyledTextField();
        txtMadeIn = createStyledTextField();
        supplierDAO = new SupplierDAO();

        // Khởi tạo JComboBox cho Supplier ID với các lựa chọn
        ArrayList<Supplier> suppliers = supplierDAO.getAll();

        String[] supplierOptions = {"Supplier1", "Supplier2", "Supplier3"};

        cmbSupplierId = new JComboBox<>(supplierOptions);
        cmbSupplierId.setFont(new Font("Arial", Font.PLAIN, 14));


        // Khởi tạo ComboBox trạng thái
        String[] statusOptions = {"Đang bán", "Ngừng bán", "Hết hàng"};
        cmbStatus = new JComboBox<>(statusOptions);
        cmbStatus.setFont(new Font("Arial", Font.PLAIN, 14));

        // Khởi tạo buttons
        btnSave = createStyledButton("Lưu");
        btnClear = createStyledButton("Xóa trắng");

        // Thêm hiệu ứng hover cho buttons
        addButtonHoverEffect(btnSave);
        addButtonHoverEffect(btnClear);
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(PRIMARY_COLOR, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return textField;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(BUTTON_COLOR);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void addButtonHoverEffect(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(BUTTON_HOVER_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(BUTTON_COLOR);
            }
        });
    }

    private void addStyledComponents(JPanel panel, GridBagConstraints gbc) {
        Object[][] components = {
                {"Mã sản phẩm:", txtId},
                {"Mã nhà cung cấp:", cmbSupplierId}, // Sử dụng JComboBox
                {"Tên sản phẩm:", txtName},
                {"Chất lượng:", txtQuality},
                {"Giá:", txtPrice},
                {"Thể loại:", txtGenre},
                {"Thương hiệu:", txtBrand},
                {"Hệ điều hành:", txtOS},
                {"CPU:", txtCPU},
                {"Bộ nhớ:", txtMemory},
                {"RAM:", txtRAM},
                {"Nơi sản xuất:", txtMadeIn},
                {"Trạng thái:", cmbStatus}
        };

        int gridy = 0;
        for (Object[] comp : components) {
            gbc.gridx = 0;
            gbc.gridy = gridy;
            gbc.weightx = 0.3;
            JLabel label = new JLabel(comp[0].toString());
            label.setFont(new Font("Arial", Font.BOLD, 14));
            label.setForeground(PRIMARY_COLOR);
            panel.add(label, gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.7;
            panel.add((Component) comp[1], gbc);

            gridy++;
        }
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panel.setBackground(BACKGROUND_COLOR);

        btnSave.addActionListener(e -> saveProduct());
        btnClear.addActionListener(e -> clearForm());

        panel.add(btnSave);
        panel.add(btnClear);

        return panel;
    }

    private void saveProduct() {
        try {
            Product product = new Product();
            product.setId(Integer.parseInt(txtId.getText()));
            product.setSuppliersId(Integer.parseInt(cmbSupplierId.getSelectedItem().toString()));
            product.setName(txtName.getText());
            product.setQuality(Integer.parseInt(txtQuality.getText()));
            product.setPrice(Integer.parseInt(txtPrice.getText()));
            product.setGenre(txtGenre.getText());
            product.setBrand(txtBrand.getText());
            product.setOperatingSystem(txtOS.getText());
            product.setCpu(txtCPU.getText());
            product.setMemory(txtMemory.getText());
            product.setRam(txtRAM.getText());
            product.setMadeIn(txtMadeIn.getText());
            product.setStatus(cmbStatus.getSelectedItem().toString());

            showSuccessDialog("Đã lưu thông tin sản phẩm thành công!");
            clearForm();
        } catch (NumberFormatException ex) {
            showErrorDialog("Vui lòng nhập đúng định dạng số!");
        }
    }

    private void clearForm() {
        JPanel mainPanel = (JPanel) ((JScrollPane) getContentPane().getComponent(0)).getViewport().getView();
        for (Component panel : mainPanel.getComponents()) {
            if (panel instanceof JPanel) {
                for (Component comp : ((JPanel) panel).getComponents()) {
                    if (comp instanceof JTextField) {
                        ((JTextField) comp).setText("");
                    } else if (comp instanceof JComboBox) {
                        ((JComboBox<?>) comp).setSelectedIndex(0);
                    }
                }
            }
        }
    }

    private void showSuccessDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Thành công", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ProductInputForm().setVisible(true);
        });
    }
}

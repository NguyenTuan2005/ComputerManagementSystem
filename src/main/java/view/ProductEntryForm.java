package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProductEntryForm extends JFrame {
    private JTextField suppliersIdField;
    private JTextField idField, nameField, quantityField, priceField, genreField, brandField,
            operatingSystemField, cpuField, memoryField, ramField, madeInField, statusField;
    private JButton saveButton;
    private JTable suppliersTable;

    public ProductEntryForm() {
        setTitle("Product Entry Form");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tạo bảng hiển thị danh sách các nhà cung cấp
        String[] columnNames = {"Supplier ID", "Supplier Name"};
        Object[][] supplierData = {
                {"1", "Supplier A"},
                {"2", "Supplier B"},
                {"3", "Supplier C"}
        }; // Dữ liệu mẫu cho bảng nhà cung cấp

        DefaultTableModel tableModel = new DefaultTableModel(supplierData, columnNames);
        suppliersTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(suppliersTable);
        scrollPane.setPreferredSize(new Dimension(200, 400));

        // Đặt bảng ở cột thứ hai
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 15;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);

        gbc.gridheight = 1; // Reset lại chiều cao cho các thành phần tiếp theo

        // Thêm các JTextField vào cột đầu tiên
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = 0;
        add(new JLabel("ID:"), gbc);
        idField = new JTextField(20);
        gbc.gridx = 1;
        add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Suppliers ID:"), gbc);
        suppliersIdField = new JTextField(20);
        gbc.gridx = 1;
        add(suppliersIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Name:"), gbc);
        nameField = new JTextField(20);
        gbc.gridx = 1;
        add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Quantity:"), gbc);
        quantityField = new JTextField(20);
        gbc.gridx = 1;
        add(quantityField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Price:"), gbc);
        priceField = new JTextField(20);
        gbc.gridx = 1;
        add(priceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Genre:"), gbc);
        genreField = new JTextField(20);
        gbc.gridx = 1;
        add(genreField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new JLabel("Brand:"), gbc);
        brandField = new JTextField(20);
        gbc.gridx = 1;
        add(brandField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        add(new JLabel("Operating System:"), gbc);
        operatingSystemField = new JTextField(20);
        gbc.gridx = 1;
        add(operatingSystemField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        add(new JLabel("CPU:"), gbc);
        cpuField = new JTextField(20);
        gbc.gridx = 1;
        add(cpuField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        add(new JLabel("Memory:"), gbc);
        memoryField = new JTextField(20);
        gbc.gridx = 1;
        add(memoryField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        add(new JLabel("RAM:"), gbc);
        ramField = new JTextField(20);
        gbc.gridx = 1;
        add(ramField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 11;
        add(new JLabel("Made In:"), gbc);
        madeInField = new JTextField(20);
        gbc.gridx = 1;
        add(madeInField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 12;
        add(new JLabel("Status:"), gbc);
        statusField = new JTextField(20);
        gbc.gridx = 1;
        add(statusField, gbc);

        // Nút Save
        saveButton = new JButton("Save");
        gbc.gridx = 0;
        gbc.gridy = 13;
        gbc.gridwidth = 2;
        add(saveButton, gbc);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProductEntryForm::new);
    }
}

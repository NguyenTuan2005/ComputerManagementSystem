package view;

import Config.ButtonConfig;
import Config.CustomerExporter;
import Config.ExcelConfig;
import Model.Customer;
import Model.Product;
import Model.Supplier;
import com.toedter.calendar.JCalendar;
import controller.CustomerController;
import controller.ManagerController;
import controller.ProductController;
import controller.SupplierController;
import dao.SupplierDAO;
import dto.CustomerOrderDTO;
import dto.ManagerInforDTO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import view.OtherComponent.*;
import view.OverrideComponent.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static dto.CustomerOrderDTO.toBillsString;


public class ManagerMainPanel extends JPanel {
    LoginFrame loginFrame;
    CardLayout cardLayout = new CardLayout();
    WelcomePanel welcomePanel;
    ProductPanel productPanel = new ProductPanel();
    SupplierPanel supplierPanel = new SupplierPanel();
    CustomerPanel customerPanel = new CustomerPanel();
    InventoryPanel inventoryPanel = new InventoryPanel();
    AccManagementPanel accManagePanel = new AccManagementPanel();
    NotificationPanel notificationPanel = new NotificationPanel();
    ChangeInformationPanel changeInformationPanel = new ChangeInformationPanel();

    //create constraint to switch between panels
    static final String WELCOME_CONSTRAINT = "welcome";
    static final String PRODUCT_CONSTRAINT = "product";
    static final String SUPPLIER_CONSTRAINT = "supplier";
    static final String CUSTOMER_CONSTRAINT = "customer";
    static final String INVENTORY_CONSTRAINT = "inventory";
    static final String INVENTORY_CONTROL_CONSTRAINT = "inventoryControl";
    static final String IMPORT_CONSTRAINT = "import";
    static final String EXPORT_CONSTRAINT = "export";
    static final String ACC_MANAGEMENT_CONSTRAINT = "accManagement";
    static final String NOTIFICATION_CONSTRAINT = "notification";
    static final String CHANGE_INFORMATION_CONSTRAINT = "changeInformation";

    // create data for column Names
    static final String[] columnNamesPRODUCT = {"Serial Number", "ProductID", "Product Name", "Quantity", "Unit Price", "Type of Device", "Brand",
            "Operating System", "CPU", "Storage", "RAM", "Made In", "Status", "Disk", "Weight", "Monitor", "Card"};
    static final String[] columnNamesSUPPLIER = {"Supplier ID:", "Supplier Name:", "Address", "Phone number:", "Email:", "Contract Start Date:"};

    //main constructor
    public ManagerMainPanel() {
        //tao giao dien
        welcomePanel = new WelcomePanel();
        setLayout(cardLayout);
        add(welcomePanel, WELCOME_CONSTRAINT);
        add(productPanel, PRODUCT_CONSTRAINT);
        add(supplierPanel, SUPPLIER_CONSTRAINT);
        add(customerPanel, CUSTOMER_CONSTRAINT);
        add(inventoryPanel, INVENTORY_CONSTRAINT);
        add(accManagePanel, ACC_MANAGEMENT_CONSTRAINT);
        add(notificationPanel, NOTIFICATION_CONSTRAINT);
        add(changeInformationPanel, CHANGE_INFORMATION_CONSTRAINT);

        cardLayout.show(this, WELCOME_CONSTRAINT);

    }

    public void showPanel(String panelName) {
        cardLayout.show(this, panelName);
    }

    // Hoang's Code
    class WelcomePanel extends JPanel {
        JLabel welcomeLabel;

        public WelcomePanel() {
            setLayout(new BorderLayout());
            welcomeLabel = new JLabel("           Welcome Manager:)");
            welcomeLabel.setFont(new Font("Arial", Font.BOLD, 60));
            welcomeLabel.setForeground(Style.BACKGROUND_COLOR);
            add(welcomeLabel, BorderLayout.CENTER);
        }
    }

    //duy    //Hoang's code
    class ProductPanel extends JPanel {
        ToolPanel toolPanel = new ToolPanel();
        JButton addBt, modifyBt, deleteBt, sortBt, exportExcelBt, importExcelBt, searchBt, reloadBt;
        JTextField findText;

        TablePanel tablePanel = new TablePanel();
        JTable tableProduct;
        DefaultTableModel modelProductTable;
        JScrollPane scrollPaneProductTable;
        JTabbedPane tabbedPaneProductTable;
        JPanel sortPanel;
        JLabel sortLabel;

        JPanel searchPanel, applicationPanel, mainPanel;
        private static ProductController productController = new ProductController();
        private static ArrayList<Product> productsAll = reloadData(productController);

        // reload method
        private static ArrayList<Product> reloadData(ProductController productController) {
            return productController.getAll();
        }

        public static ArrayList<Product> reloadProducts() {
            return reloadData(productController);
        }

        public static void upDataProducts(ArrayList<Product> products, DefaultTableModel modelProductTable) {
            String[][] rowData = Product.getDateOnTable(products);
            TablePanel.removeDataTable(modelProductTable);
            for (int i = 0; i < rowData.length; i++) {
                modelProductTable.addRow(rowData[i]);
            }
        }

        public ProductPanel() {
            setLayout(new BorderLayout());
            toolPanel.setBorder(BorderFactory.createTitledBorder("Tools"));
            add(toolPanel, BorderLayout.NORTH);
            add(tablePanel, BorderLayout.CENTER);

        }

        public class ToolPanel extends JPanel {

            public ToolPanel() {
                setLayout(new BorderLayout());
                setBackground(Style.WORD_COLOR_WHITE);
                addBt = new JButton("Add");
                ButtonConfig.addButtonHoverEffect(addBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                setStyleButton(addBt, Style.FONT_SIZE_MIN_PRODUCT, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(80, 80));
                ButtonConfig.setIconBigButton("src/main/java/Icon/database-add-icon.png", addBt);
                addBt.setHorizontalTextPosition(SwingConstants.CENTER); // Chữ ở giữa theo chiều ngang
                addBt.setVerticalTextPosition(SwingConstants.BOTTOM);
                addBt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new ProductInputForm();
                    }
                });

                modifyBt = new JButton("Modify");
                ButtonConfig.addButtonHoverEffect(modifyBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                setStyleButton(modifyBt, Style.FONT_SIZE_MIN_PRODUCT, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(80, 80));
                ButtonConfig.setIconBigButton("src/main/java/Icon/modify.png", modifyBt);
                modifyBt.setHorizontalTextPosition(SwingConstants.CENTER); // Chữ ở giữa theo chiều ngang
                modifyBt.setVerticalTextPosition(SwingConstants.BOTTOM);
                modifyBt.addActionListener(e -> {
                    int selectedRow = tableProduct.getSelectedRow();
                    int columnIndex = 1;
                    if (selectedRow != -1) {
                        SwingUtilities.invokeLater(() -> {
                            new ProductModifyForm(productsAll.get(selectedRow)).setVisible(true);
                        });

                    }
                });

                deleteBt = new JButton("Delete");
                ButtonConfig.addButtonHoverEffect(deleteBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                setStyleButton(deleteBt, Style.FONT_SIZE_MIN_PRODUCT, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(80, 80));
                ButtonConfig.setIconBigButton("src/main/java/Icon/delete-icon-removebg-preview.png", deleteBt);
                deleteBt.setHorizontalTextPosition(SwingConstants.CENTER); // Chữ ở giữa theo chiều ngang
                deleteBt.setVerticalTextPosition(SwingConstants.BOTTOM);
                deleteBt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int selectedRow = tableProduct.getSelectedRow();
                        int columnIndex = 1;
                        if (selectedRow != -1) {
                            Object value = tableProduct.getValueAt(selectedRow, columnIndex);

                            int productId = Integer.parseInt(value.toString());
                            productController.setDeleteRow(productId, false);
                            modelProductTable.removeRow(selectedRow);
                        }
                    }
                });
                sortBt = new JButton("Sort");
                ButtonConfig.addButtonHoverEffect(sortBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                setStyleButton(sortBt, Style.FONT_SIZE_MIN_PRODUCT, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(80, 80));
                ButtonConfig.setIconBigButton("src/main/java/Icon/sort.256x204.png", sortBt);
                sortBt.setHorizontalTextPosition(SwingConstants.CENTER); // Chữ ở giữa theo chiều ngang
                sortBt.setVerticalTextPosition(SwingConstants.BOTTOM);

                exportExcelBt = new JButton("Export");
                ButtonConfig.addButtonHoverEffect(exportExcelBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                setStyleButton(exportExcelBt, Style.FONT_SIZE_MIN_PRODUCT, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(80, 80));
                ButtonConfig.setIconBigButton("src/main/java/Icon/icons8-file-excel-32.png", exportExcelBt);
                exportExcelBt.setHorizontalTextPosition(SwingConstants.CENTER); // Chữ ở giữa theo chiều ngang
                exportExcelBt.setVerticalTextPosition(SwingConstants.BOTTOM);
                exportExcelBt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String fileName = JOptionPane.showInputDialog(null, "Enter file name excel:", "Input file", JOptionPane.QUESTION_MESSAGE);
                        if (fileName != null && !fileName.trim().isEmpty()) {
                            // Thêm phần mở rộng .xlsx nếu người dùng không nhập
                            if (!fileName.toLowerCase().endsWith(".xlsx")) {
                                fileName += ".xlsx";
                            }
                            //update data truoc khi export
                            productsAll = reloadData(productController);
                            ExcelConfig.exportToExcel(productsAll, fileName, columnNamesPRODUCT);
                            if (productsAll.isEmpty())
                                JOptionPane.showMessageDialog(null, "Not found data", "Notify", JOptionPane.WARNING_MESSAGE);
                            JOptionPane.showMessageDialog(null, "Created file :" + fileName, "Notify", JOptionPane.WARNING_MESSAGE);
                        }
                        JOptionPane.showMessageDialog(null, "Are you sure ", "Exit", JOptionPane.ERROR_MESSAGE);

                    }
                });

                importExcelBt = new JButton("Import");
                ButtonConfig.addButtonHoverEffect(importExcelBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                setStyleButton(importExcelBt, Style.FONT_SIZE_MIN_PRODUCT, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(80, 80));
                ButtonConfig.setIconBigButton("src/main/java/Icon/icons8-export-excel-50.png", importExcelBt);
                importExcelBt.setHorizontalTextPosition(SwingConstants.CENTER); // Chữ ở giữa theo chiều ngang
                importExcelBt.setVerticalTextPosition(SwingConstants.BOTTOM);
                // api cho phan Import excel
                importExcelBt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

                        int result = fileChooser.showOpenDialog(new JFrame("File Chooser"));
                        if (result == JFileChooser.APPROVE_OPTION) {
                            File selectedFile = fileChooser.getSelectedFile();
                            ArrayList<Product> products = ExcelConfig.importFromExcel(selectedFile, Product.class);
                            productController.saves(products);
                            String url = selectedFile.getAbsolutePath();
                            JOptionPane.showMessageDialog(null, "Read file " + url, "Notify", JOptionPane.WARNING_MESSAGE);

                        } else {
                            System.out.println("Chọn file đã bị hủy.");
                        }
                    }
                });
                findText = new JTextField("Search by name");
                findText.setForeground(Color.GRAY);
                formatTextField(findText, new Font("Arial", Font.PLAIN, 24), Style.WORD_COLOR_BLACK, new Dimension(250, 45));
                findText.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        // Khi người dùng nhấn vào JTextField, nếu vẫn là chữ "Search", nó sẽ biến mất
                        if (findText.getText().equals("Search by name")) {
                            findText.setText("");
                            findText.setForeground(Color.BLACK);
                        }
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        // Khi người dùng rời khỏi JTextField mà chưa nhập gì, sẽ hiển thị lại chữ "Search"
                        if (findText.getText().isEmpty()) {
                            findText.setForeground(Color.GRAY);
                            findText.setText("Search by name");
                        }
                    }
                });

                searchBt = new JButton();
                ButtonConfig.addButtonHoverEffect(searchBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                setStyleButton(searchBt, Style.FONT_SIZE_MIN_PRODUCT, Color.BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(40, 45));
                ButtonConfig.setIconSmallButton("src/main/java/Icon/106236_search_icon.png", searchBt);

                reloadBt = new JButton("reload");
                ButtonConfig.addButtonHoverEffect(reloadBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                setStyleButton(reloadBt, Style.FONT_SIZE_MIN_PRODUCT, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(80, 80));
                ButtonConfig.setIconBigButton("src/main/java/Icon/reload-icon.png", reloadBt);
                reloadBt.setHorizontalTextPosition(SwingConstants.CENTER); // Chữ ở giữa theo chiều ngang
                reloadBt.setVerticalTextPosition(SwingConstants.BOTTOM);

                searchPanel = new JPanel(new FlowLayout());
                searchPanel.add(findText);
                searchPanel.add(searchBt);
                searchPanel.setBackground(Style.WORD_COLOR_WHITE);

                applicationPanel = new JPanel(new FlowLayout());
                applicationPanel.add(addBt);
                applicationPanel.add(deleteBt);
                applicationPanel.add(ButtonConfig.createVerticalSeparator());
                applicationPanel.add(modifyBt);
                String[] sortOptions = {"NAME", "MEMORY", "PRICE", "RAM"};
                JComboBox<String> sortComboBox = new JComboBox<>(sortOptions);
                sortComboBox.setPreferredSize(new Dimension(74, 59));
                sortComboBox.setBackground(Style.WORD_COLOR_WHITE);
                sortComboBox.setForeground(Style.WORD_COLOR_BLACK);
                sortComboBox.setFont(Style.FONT_SIZE_BUTTON);
                sortComboBox.setRenderer(new DefaultListCellRenderer() {
                    @Override
                    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                        if (isSelected) {
                            c.setBackground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);  // Màu nền khi mục được chọn
                            c.setForeground(Color.WHITE); // Màu chữ khi mục được chọn
                        } else {
                            c.setBackground(Color.WHITE); // Màu nền khi mục không được chọn
                            c.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE); // Màu chữ khi mục không được chọn
                        }
                        return c;
                    }
                });
                sortComboBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String item = (String) sortComboBox.getSelectedItem();
                        switch (item) {
                            case ("PIRCE"): {
                                productsAll = (ArrayList<Product>) productsAll.stream()
                                        .sorted((p1, p2) -> Integer.compare(p2.getPrice(), p1.getPrice()))
                                        .collect(Collectors.toList());
                                break;
                            }

                            case ("MEMORY"): {
                                productsAll = (ArrayList<Product>) productsAll.stream()
                                        .sorted((p1, p2) -> p2.getMemory().compareTo(p1.getMemory()))
                                        .collect(Collectors.toList());
                                break;
                            }
                            case ("NAME"): {
                                productsAll = (ArrayList<Product>) productsAll.stream()
                                        .sorted((p1, p2) -> p2.getName().compareTo(p1.getName()))
                                        .collect(Collectors.toList());
                                break;
                            }
                            case ("RAM"): {
                                productsAll = (ArrayList<Product>) productsAll.stream()
                                        .sorted((p1, p2) -> p2.getRam().compareTo(p1.getRam()))
                                        .collect(Collectors.toList());
                                break;
                            }

                        }
                        upDataProducts(productsAll, modelProductTable);

                    }
                });


                sortPanel = new JPanel(new BorderLayout());
                sortLabel = new JLabel("Sort");
                sortLabel.setHorizontalAlignment(SwingConstants.CENTER); // Căn ngang giữa
                sortLabel.setVerticalAlignment(SwingConstants.CENTER);
                sortLabel.setFont(Style.FONT_SIZE_MIN_PRODUCT);
                JLabel none = new JLabel("");
                none.setFont(Style.FONT_SIZE_MIN_PRODUCT);
                none.setHorizontalAlignment(SwingConstants.CENTER); // Căn ngang giữa
                none.setVerticalAlignment(SwingConstants.CENTER);


                sortPanel.add(sortComboBox, BorderLayout.CENTER);
                sortPanel.add(none, BorderLayout.NORTH);
                sortPanel.add(sortLabel, BorderLayout.SOUTH);
                sortPanel.setBackground(Style.WORD_COLOR_WHITE);


                applicationPanel.add(sortPanel);

//                applicationPanel.add(sortBt);
                applicationPanel.add(ButtonConfig.createVerticalSeparator());
                applicationPanel.add(exportExcelBt);
//                applicationPanel.add(ButtonConfig.createVerticalSeparator());
                applicationPanel.add(importExcelBt);
                applicationPanel.add(ButtonConfig.createVerticalSeparator());
                applicationPanel.add(reloadBt);
                applicationPanel.setBackground(Style.WORD_COLOR_WHITE);

// layout
                mainPanel = new JPanel(new GridBagLayout());
                mainPanel.setBackground(Style.BACKGROUND_COLOR);
                GridBagConstraints gbc = new GridBagConstraints();

                // Cấu hình cho panel trái (căn về bên trái)
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.weightx = 1;
                gbc.anchor = GridBagConstraints.WEST;
                mainPanel.add(applicationPanel, gbc);

                gbc.gridx = 1;
                gbc.gridy = 0;
                gbc.weightx = 0;
                gbc.anchor = GridBagConstraints.EAST;
                mainPanel.add(searchPanel, gbc);
                mainPanel.setBackground(Style.WORD_COLOR_WHITE);

                add(mainPanel);

                // find application
                searchBt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (findText.getText().trim().isEmpty())
                            return;
                        ArrayList<Product> products = productController.find(findText.getText().trim());

                        if (products.isEmpty()) {
                            JOptionPane.showMessageDialog(tablePanel, "có caái ni");
                            return;
                        }
                        upDataProducts(products, modelProductTable);

                    }
                });

                // reload application
                reloadBt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        productsAll = reloadData(productController);
                        upDataProducts(productsAll, modelProductTable);
                    }
                });
            }


        }

        //table
        public class TablePanel extends JPanel {
            public TablePanel() {
                setLayout(new BorderLayout());
                setBackground(Style.WORD_COLOR_WHITE);
                productController = new ProductController();
                // data test thử:))

//                String [] product =(String) products.toArray();

                tableProduct = createTable(modelProductTable, columnNamesPRODUCT);
                tableProduct.setRowHeight(30);
                resizeColumnWidth(tableProduct, 150);

                modelProductTable = (DefaultTableModel) tableProduct.getModel();

                ArrayList<Product> productsDemo = productController.getAll();

                upDataProducts(productsDemo, modelProductTable);


                scrollPaneProductTable = new JScrollPane(tableProduct);
                tabbedPaneProductTable = createTabbedPane(scrollPaneProductTable, "Product for Sales", Style.FONT_HEADER_ROW_TABLE);

                add(tabbedPaneProductTable, BorderLayout.CENTER);
            }

            public static void removeDataTable(DefaultTableModel modelProductTable) {
                modelProductTable.setRowCount(0);
            }

        }
    }

    // Hoang's code // tuan/
    class SupplierPanel extends JPanel {
        JButton addBt, modifyBt, deleteBt, exportExcelBt, importExcelBt, searchBt;
        JTextField findText;
        private JTable tableSupplier;
        private DefaultTableModel modelSupplier;

        ToolPanel toolPanel = new ToolPanel();
        TablePanel tablePanel = new TablePanel();

        private static SupplierController supplierController = new SupplierController();
        private static ArrayList<Supplier> suppliers;
        private String selectedOption = "ALL";

        private SupplierDAO supplierDAO = new SupplierDAO();

        public SupplierPanel() {
            setLayout(new BorderLayout());
            add(toolPanel, BorderLayout.NORTH);
            add(tablePanel, BorderLayout.CENTER);
        }

        public class ToolPanel extends JPanel {
            public ToolPanel() {
                setLayout(new BorderLayout());
                setBackground(Style.WORD_COLOR_WHITE);

                // Add Button
                {
                    addBt = new JButton("Add");
                    ButtonConfig.setStyleButton(addBt, Style.FONT_BUTTON_CUSTOMER, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(90, 80));
                    ButtonConfig.setIconBigButton("src/main/java/Icon/database-add-icon.png", addBt);
                    ButtonConfig.addButtonHoverEffect(addBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                    addBt.setHorizontalTextPosition(SwingConstants.CENTER);
                    addBt.setVerticalTextPosition(SwingConstants.BOTTOM);
                    addBt.addActionListener(e -> {
                        AddSupplierFrame addSupplierFrame = new AddSupplierFrame(() -> updateSuppliers(selectedOption));
                        addSupplierFrame.showFrame();
                    });
                }

                // Modify Button
                {
                    modifyBt = new JButton("Modify");
                    ButtonConfig.setStyleButton(modifyBt, Style.FONT_BUTTON_CUSTOMER, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(90, 80));
                    ButtonConfig.setIconBigButton("src/main/java/Icon/modify.png", modifyBt);
                    ButtonConfig.addButtonHoverEffect(modifyBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                    modifyBt.setHorizontalTextPosition(SwingConstants.CENTER);
                    modifyBt.setVerticalTextPosition(SwingConstants.BOTTOM);
                    modifyBt.addActionListener(e -> modifyHandle());
                }

                // Delete Button
                {
                    deleteBt = new JButton("Delete");
                    ButtonConfig.setStyleButton(deleteBt, Style.FONT_BUTTON_CUSTOMER, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(90, 80));
                    ButtonConfig.setIconBigButton("src/main/java/Icon/delete-icon-removebg-preview.png", deleteBt);
                    ButtonConfig.addButtonHoverEffect(deleteBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                    deleteBt.setHorizontalTextPosition(SwingConstants.CENTER);
                    deleteBt.setVerticalTextPosition(SwingConstants.BOTTOM);
                    deleteBt.addActionListener(e -> deleteHandle());
                }

                // Export Excel Button
                {
                    exportExcelBt = new JButton("Export");
                    ButtonConfig.setStyleButton(exportExcelBt, Style.FONT_BUTTON_CUSTOMER, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(90, 80));
                    ButtonConfig.setIconBigButton("src/main/java/Icon/icons8-file-excel-32.png", exportExcelBt);
                    ButtonConfig.addButtonHoverEffect(exportExcelBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                    exportExcelBt.setHorizontalTextPosition(SwingConstants.CENTER);
                    exportExcelBt.setVerticalTextPosition(SwingConstants.BOTTOM);
                    exportExcelBt.addActionListener(e -> {
                        String fileName = JOptionPane.showInputDialog("Enter the name of the Excel file:");
                        if (fileName != null && !fileName.trim().isEmpty()) {
                            fileName = fileName.trim().endsWith(".xlsx") ? fileName.trim() : fileName.trim() + ".xlsx";
                            ExcelConfig.exportToExcel(suppliers, fileName, columnNamesSUPPLIER);
                            JOptionPane.showMessageDialog(null, "Exported to " + fileName);
                        } else {
                            JOptionPane.showMessageDialog(null, "File name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    });
                }

                // Import Excel Button
                {
                    importExcelBt = new JButton("Import");
                    ButtonConfig.setStyleButton(importExcelBt, Style.FONT_BUTTON_CUSTOMER, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(90, 80));
                    ButtonConfig.setIconBigButton("src/main/java/Icon/icons8-export-excel-50.png", importExcelBt);
                    ButtonConfig.addButtonHoverEffect(importExcelBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                    importExcelBt.setHorizontalTextPosition(SwingConstants.CENTER);
                    importExcelBt.setVerticalTextPosition(SwingConstants.BOTTOM);
                    importExcelBt.addActionListener(e -> {
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setDialogTitle("Select Excel file to import");

                        int result = fileChooser.showOpenDialog(null);
                        if (result == JFileChooser.APPROVE_OPTION) {
                            File selectedFile = fileChooser.getSelectedFile();
                            if (selectedFile != null) {
                                // Use ExcelConfig to import suppliers from the selected file
                                ArrayList<Supplier> importedSuppliers = ExcelConfig.importFromExcel(selectedFile, Supplier.class);

                                // Clear current suppliers and update with imported data
                                suppliers.clear();
                                suppliers.addAll(importedSuppliers);
                                updateSuppliers(selectedOption); // Refresh the table to show imported data
                            }
                        }
                    });

                }

                // Search Text Field
                {
                    findText = new JTextField("Search by name");
                    findText.setForeground(Color.GRAY);
                    formatTextField(findText, new Font("Arial", Font.PLAIN, 24), Style.WORD_COLOR_BLACK, new Dimension(250, 45));
                    findText.addFocusListener(new FocusListener() {
                        @Override
                        public void focusGained(FocusEvent e) {
                            if (findText.getText().equals("Search by name")) {
                                findText.setText("");
                                findText.setForeground(Color.BLACK);
                            }
                        }

                        @Override
                        public void focusLost(FocusEvent e) {
                            if (findText.getText().isEmpty()) {
                                findText.setForeground(Color.GRAY);
                                findText.setText("Search by name");
                                updateSuppliers(selectedOption);
                            }
                        }
                    });
                }

                // Search Button
                searchBt = new JButton();
                ButtonConfig.setStyleButton(searchBt, Style.FONT_SIZE, Color.BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(40, 45));
                ButtonConfig.setIconSmallButton("src/main/java/Icon/106236_search_icon.png", searchBt);
                ButtonConfig.addButtonHoverEffect(searchBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                searchBt.addActionListener(e -> {
                    if (!findText.getText().isBlank()) {
                        String text = findText.getText();
                        suppliers = supplierController.find(text);
                        updateSuppliers(selectedOption, text);
                    } else {
                        updateSuppliers(selectedOption);
                    }
                });

                // Create panels
                JPanel searchPanel = new JPanel(new FlowLayout());
                searchPanel.add(findText);
                searchPanel.add(searchBt);
                searchPanel.setBackground(Style.WORD_COLOR_WHITE);

                String[] sortOptions = {"ALL", "NAME", "EMAIL", "PHONE NUMBER", "ADDRESS", "DATE"};
                JComboBox<String> sortComboBox = new JComboBox<>(sortOptions);
                sortComboBox.setPreferredSize(new Dimension(130, 59));
                sortComboBox.setBackground(Style.WORD_COLOR_WHITE);
                sortComboBox.setForeground(Style.WORD_COLOR_BLACK);
                sortComboBox.setFont(Style.FONT_SIZE_BUTTON);
                sortComboBox.addActionListener(e -> {
                    selectedOption = (String) sortComboBox.getSelectedItem();
                    sortTable(selectedOption);
                });

                JPanel applicationPanel = new JPanel(new FlowLayout());
                applicationPanel.add(addBt);
                applicationPanel.add(deleteBt);
                applicationPanel.add(ButtonConfig.createVerticalSeparator());
                applicationPanel.add(modifyBt);
                applicationPanel.add(sortComboBox);
                applicationPanel.add(ButtonConfig.createVerticalSeparator());
                applicationPanel.add(exportExcelBt);
                applicationPanel.add(importExcelBt);
                applicationPanel.setBackground(Style.WORD_COLOR_WHITE);

                // Main panel with GridBagLayout
                JPanel mainPanel = new JPanel(new GridBagLayout());
                mainPanel.setBackground(Style.BACKGROUND_COLOR);
                mainPanel.setBorder(BorderFactory.createTitledBorder("Tools"));
                GridBagConstraints gbc = new GridBagConstraints();

                // Configure left panel
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.weightx = 1;
                gbc.anchor = GridBagConstraints.WEST;
                mainPanel.add(applicationPanel, gbc);

                // Configure right panel
                gbc.gridx = 1;
                gbc.gridy = 0;
                gbc.weightx = 0;
                gbc.anchor = GridBagConstraints.EAST;
                mainPanel.add(searchPanel, gbc);
                mainPanel.setBackground(Style.WORD_COLOR_WHITE);

                add(mainPanel);
            }

            private void sortTable(String selectedOption) {
                updateSuppliers(selectedOption);
            }

            private void modifyHandle() {
                int selectedRow = tableSupplier.getSelectedRow();
                if (selectedRow != -1) {
                    int supplierId = Integer.parseInt((String) modelSupplier.getValueAt(selectedRow, 1));
                    ModifySupplierFrame modifySupplierFrame = new ModifySupplierFrame(() -> updateSuppliers(selectedOption), supplierDAO.findById(supplierId));
                    modifySupplierFrame.showFrame();
                } else {
                    ToastNotification.showToast("Please select a row to modify.", 3000, 400, 50);
                }
            }

            private void deleteHandle() {
                int selectedRow = tableSupplier.getSelectedRow();

                if (selectedRow != -1) {
                    int supplierId = Integer.parseInt((String) modelSupplier.getValueAt(selectedRow, 1));

                    supplierDAO.setDeleteRow(supplierId, true);

                    // Remove the row from the table model
                    modelSupplier.removeRow(selectedRow);

                    ToastNotification.showToast("Supplier marked as deleted successfully.", 3000, 600, 50);
                } else {
                    ToastNotification.showToast("Please select a row to delete.", 3000, 400, 50);
                }
            }
        }


        public class TablePanel extends JPanel {
            public TablePanel() {
                setLayout(new BorderLayout());
                setBackground(Style.WORD_COLOR_WHITE);

                tableSupplier = createTable(modelSupplier, columnNamesSUPPLIER);
                tableSupplier.setRowHeight(40);
                resizeColumnWidth(tableSupplier, 200);
                JScrollPane scrollPaneSupplier = new JScrollPane(tableSupplier);
                modelSupplier = (DefaultTableModel) tableSupplier.getModel();
                suppliers = supplierController.reloadData();

                upDataTable(suppliers, modelSupplier);

                JTabbedPane tabbedPaneSupplier = createTabbedPane(scrollPaneSupplier, "Supplier List", Style.FONT_HEADER_ROW_TABLE);
                add(tabbedPaneSupplier, BorderLayout.CENTER);
            }

        }

        // Update method for the table
        private void updateSuppliers(String column) {
            modelSupplier.setRowCount(0);
            suppliers = supplierController.sortByColumn(column);
            upDataTable(suppliers, modelSupplier);
        }

        private void updateSuppliers(String column, String text) {
            modelSupplier.setRowCount(0);
            suppliers = supplierController.find(text);
            //..To be continue...
            upDataTable(suppliers, modelSupplier);
        }

        // Method to populate the table with data
        public static void upDataTable(ArrayList<Supplier> suppliers, DefaultTableModel modelSupplier) {
            String[][] rowData = Supplier.getData(suppliers);
            for (String[] strings : rowData) {
                modelSupplier.addRow(strings);
            }
        }

        public static ArrayList<Supplier> reloadSuppliers() {
            return supplierController.reloadData();
        }
    }

    //james
    class CustomerPanel extends JPanel {
        final String[] customerColumnNames = {"Serial number", "Customer ID", "Customer Name", "Email", "Address", "Password", "Avata"};

        private JTable tableCustomer;
        private DefaultTableModel modelCustomer;
        private JScrollPane scrollPaneCustomer;
        private JTabbedPane tabbedPaneCustomer;
        private ToolPanel toolPanel = new ToolPanel();
        private TableCustomerPanel tableCustomerPanel = new TableCustomerPanel();

        private JButton addCustomerBt, modifyCustomerBt, exportCustomerExcelBt, searchCustomerBt, reloadCustomerBt, blockCustomer, writeToFileTXT;
        private JTextField findCustomerText;

        private TextDisplayPanel billTextDisplayPanal;
        private int index =0;
        private final int TAB_DATA_CUSTOMER =0;
        private final int TAB_SCHEMAS =1;
        private final int TAB_BILL =2;



        private static CustomerController customerController = new CustomerController();
        private static ArrayList<Customer> customers = new ArrayList<>();
        private  ArrayList<CustomerOrderDTO> bills= new ArrayList<>();

        private JPanel searchPanel, applicationPanel, mainPanel;

        public CustomerPanel() {
            setLayout(new BorderLayout());
            toolPanel.setBorder(BorderFactory.createTitledBorder("Tool"));
            add(toolPanel, BorderLayout.NORTH);
            add(tableCustomerPanel, BorderLayout.CENTER);
        }

        public class ToolPanel extends JPanel {
            public ToolPanel() {
                setLayout(new BorderLayout());
                setBackground(Style.WORD_COLOR_WHITE);
                addCustomerBt = new JButton("Add");
                ButtonConfig.addButtonHoverEffect(addCustomerBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                setStyleButton(addCustomerBt, Style.FONT_SIZE_MIN_PRODUCT, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(80, 80));
                ButtonConfig.setIconBigButton("src/main/java/Icon/database-add-icon.png", addCustomerBt);
                addCustomerBt.setHorizontalTextPosition(SwingConstants.CENTER); // Chữ ở giữa theo chiều ngang
                addCustomerBt.setVerticalTextPosition(SwingConstants.BOTTOM);
                addCustomerBt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        SwingUtilities.invokeLater(CustomerInfoFrame::new);
                        reload();
                    }
                });

                modifyCustomerBt = new JButton("Modify");
                ButtonConfig.addButtonHoverEffect(modifyCustomerBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                setStyleButton(modifyCustomerBt, Style.FONT_SIZE_MIN_PRODUCT, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(80, 80));
                ButtonConfig.setIconBigButton("src/main/java/Icon/modify.png", modifyCustomerBt);
                modifyCustomerBt.setHorizontalTextPosition(SwingConstants.CENTER); // Chữ ở giữa theo chiều ngang
                modifyCustomerBt.setVerticalTextPosition(SwingConstants.BOTTOM);
                modifyCustomerBt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int selectedRow = tableCustomer.getSelectedRow();
                        int columnIndex = 0;
                        if (selectedRow != -1) {
                            Object value = tableCustomer.getValueAt(selectedRow, columnIndex);

                            int customerId = Integer.parseInt(value.toString());
                            SwingUtilities.invokeLater(() -> {
                                System.out.println(customerId);
                                ModifyCustomerFrame modifyCustomerFrame = new ModifyCustomerFrame(customers.get(customerId - 1));
                                reload();

                            });

                        }
                    }
                });

                // block customer
                blockCustomer = new JButton("Block customer");
                ButtonConfig.addButtonHoverEffect(blockCustomer, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                setStyleButton(blockCustomer, Style.FONT_SIZE_MIN_PRODUCT, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(80, 80));
                ButtonConfig.setIconBigButton("src/main/java/Icon/modify.png", blockCustomer);
                blockCustomer.setHorizontalTextPosition(SwingConstants.CENTER); // Chữ ở giữa theo chiều ngang
                blockCustomer.setVerticalTextPosition(SwingConstants.BOTTOM);
                blockCustomer.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        int selectedRow = tableCustomer.getSelectedRow();
                        int columnIndex = 1;
                        int fullName =2;
                        if (selectedRow != -1) {
                            Object value = tableCustomer.getValueAt(selectedRow, columnIndex);
                            int customerId = Integer.parseInt(value.toString());
                            String customername = (String) tableCustomer.getValueAt(selectedRow, fullName);
                            if (customername.contains("*")){
                                JOptionPane.showMessageDialog(null, "Unblock customerId : " + customerId);
                                customerController.block(false, customerId);
                                JOptionPane.showMessageDialog(null, "Customer" + tableCustomer.getValueAt(selectedRow, fullName) + "Unblocked! ");
                            } else {
                                JOptionPane.showMessageDialog(null, "Block customerId : " + customerId);
                                customerController.block(true, customerId);
                                JOptionPane.showMessageDialog(null, "Customer" + tableCustomer.getValueAt(selectedRow, fullName) + "is blocked! ");
                            }
                            reload();
                        }
                    }
                });

                exportCustomerExcelBt = new JButton("Export");
                ButtonConfig.addButtonHoverEffect(exportCustomerExcelBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                setStyleButton(exportCustomerExcelBt, Style.FONT_SIZE_MIN_PRODUCT, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(80, 80));
                ButtonConfig.setIconBigButton("src/main/java/Icon/icons8-file-excel-32.png", exportCustomerExcelBt);
                exportCustomerExcelBt.setHorizontalTextPosition(SwingConstants.CENTER); // Chữ ở giữa theo chiều ngang
                exportCustomerExcelBt.setVerticalTextPosition(SwingConstants.BOTTOM);
                exportCustomerExcelBt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String fileName = JOptionPane.showInputDialog(null, "Enter file name excel:", "Input file", JOptionPane.QUESTION_MESSAGE);
                        if (customers.isEmpty())
                            JOptionPane.showMessageDialog(null, "Not found data", "Notify", JOptionPane.WARNING_MESSAGE);
                        else {
                            JOptionPane.showMessageDialog(null, "Created file :" + fileName, "Notify", JOptionPane.WARNING_MESSAGE);
                            fileName = fileName.trim().endsWith(".xlsx") ? fileName.trim() : fileName.trim() + ".xlsx";
                            ExcelConfig.exportToExcel(customers, fileName, customerColumnNames);

                            JOptionPane.showMessageDialog(null, "Created !!! ", "Message", JOptionPane.ERROR_MESSAGE);
                            reload();
                        }


                    }
                });

                writeToFileTXT = new JButton("to file.txt");
                ButtonConfig.addButtonHoverEffect(writeToFileTXT, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                setStyleButton(writeToFileTXT, Style.FONT_SIZE_MIN_PRODUCT, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(80, 80));
                ButtonConfig.setIconBigButton("src/main/java/Icon/bill.png",writeToFileTXT);
                writeToFileTXT.setHorizontalTextPosition(SwingConstants.CENTER); // Chữ ở giữa theo chiều ngang
                writeToFileTXT.setVerticalTextPosition(SwingConstants.BOTTOM);
                writeToFileTXT.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (bills.isEmpty())
                            JOptionPane.showMessageDialog(null, "Not found data", "Notify", JOptionPane.WARNING_MESSAGE);
                        else {
                            String fileName = JOptionPane.showInputDialog(null, "Enter bill file name :", "Input file", JOptionPane.QUESTION_MESSAGE);
                            JOptionPane.showMessageDialog(null, "Created file :" + fileName, "Notify", JOptionPane.WARNING_MESSAGE);
                            CustomerExporter.writeBillToFile(toBillsString(bills), fileName);
                            JOptionPane.showMessageDialog(null, "Created !!! ", "Message", JOptionPane.ERROR_MESSAGE);
                            reload();
                        }
                    }
                });



                findCustomerText = new JTextField("Search by name");
                findCustomerText.setForeground(Color.GRAY);
                formatTextField(findCustomerText, new Font("Arial", 0, 24), Style.WORD_COLOR_BLACK, new Dimension(250, 45));
                findCustomerText.addFocusListener( new FocusListener() {
                                                       @Override
                                                       public void focusGained(FocusEvent e) {
                                                           if (findCustomerText.getText().equals("Search by name")) {
                                                               findCustomerText.setText("");
                                                               findCustomerText.setForeground(Color.BLACK);
                                                           }
                                                       }

                                                       @Override
                                                       public void focusLost(FocusEvent e) {
                                                           findCustomerText.setForeground(Color.GRAY);
                                                           findCustomerText.setText("Search by name");
                                                       }
                                                   }

                );

                searchCustomerBt = new JButton();
                ButtonConfig.addButtonHoverEffect(searchCustomerBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                setStyleButton(searchCustomerBt, Style.FONT_SIZE_MIN_PRODUCT, Color.BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(40, 45));
                ButtonConfig.setIconSmallButton("src/main/java/Icon/106236_search_icon.png", searchCustomerBt);
                searchCustomerBt.addActionListener(
                        new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {

                                switch (getIndexSelectedTab()) {

                                    case TAB_DATA_CUSTOMER : {
                                        if (findCustomerText.getText().trim().isEmpty())
                                            return;
                                        ArrayList<Customer> cuss = customerController.find(findCustomerText.getText().trim());
                                        if (cuss.isEmpty()) {
                                            JOptionPane.showMessageDialog(tableCustomerPanel, "có caái ni");
                                            return;
                                        }
                                        upDataTable(cuss, modelCustomer, tableCustomer);
                                        break;
                                    }
                                    case TAB_BILL :{
                                        if (findCustomerText.getText().trim().isEmpty())
                                            return;
                                        try {
                                            int customerId = Integer.parseInt(findCustomerText.getText());
                                            bills = customerController.findCustomerOrderById(customerId);
                                            if( bills.isEmpty())
                                                JOptionPane.showMessageDialog(null,"No information available");
                                            billTextDisplayPanal.setText(toBillsString(bills));
                                            billTextDisplayPanal.setTextEditable(false);

                                        } catch (Exception ex){
                                            JOptionPane.showMessageDialog(null,"You must enter the ID Customer");
                                            findCustomerText.setText("");
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                );

                reloadCustomerBt = new JButton("reload");
                ButtonConfig.addButtonHoverEffect(reloadCustomerBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                setStyleButton(reloadCustomerBt, Style.FONT_SIZE_MIN_PRODUCT, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(80, 80));
                ButtonConfig.setIconBigButton("src/main/java/Icon/reload-icon.png", reloadCustomerBt);
                reloadCustomerBt.setHorizontalTextPosition(SwingConstants.CENTER); // Chữ ở giữa theo chiều ngang
                reloadCustomerBt.setVerticalTextPosition(SwingConstants.BOTTOM);
                reloadCustomerBt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        reload();

                    }
                });

                searchPanel = new JPanel(new FlowLayout());
                searchPanel.add(findCustomerText);
                searchPanel.add(searchCustomerBt);
                searchPanel.setBackground(Style.WORD_COLOR_WHITE);

                applicationPanel = new JPanel(new FlowLayout());
                applicationPanel.add(addCustomerBt);

                applicationPanel.add(ButtonConfig.createVerticalSeparator());
                applicationPanel.add(modifyCustomerBt);
                applicationPanel.add(blockCustomer);

                JLabel none = new JLabel("");
                none.setFont(Style.FONT_SIZE_MIN_PRODUCT);
                none.setHorizontalAlignment(SwingConstants.CENTER); // Căn ngang giữa
                none.setVerticalAlignment(SwingConstants.CENTER);


                applicationPanel.add(ButtonConfig.createVerticalSeparator());
                applicationPanel.add(exportCustomerExcelBt);
                applicationPanel.add(writeToFileTXT);

                applicationPanel.add(ButtonConfig.createVerticalSeparator());
                applicationPanel.add(reloadCustomerBt);
                applicationPanel.setBackground(Style.WORD_COLOR_WHITE);

                mainPanel = new JPanel(new GridBagLayout());
                mainPanel.setBackground(Style.BACKGROUND_COLOR);
                GridBagConstraints gbc = new GridBagConstraints();

                // Cấu hình cho panel trái (căn về bên trái)
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.weightx = 1;
                gbc.anchor = GridBagConstraints.WEST;
                mainPanel.add(applicationPanel, gbc);

                gbc.gridx = 1;
                gbc.gridy = 0;
                gbc.weightx = 0;
                gbc.anchor = GridBagConstraints.EAST;
                mainPanel.add(searchPanel, gbc);
                mainPanel.setBackground(Style.WORD_COLOR_WHITE);

                add(mainPanel);


            }
        }

        public class TableCustomerPanel extends JPanel {
            public TableCustomerPanel() {
                setLayout(new BorderLayout());
                setBackground(Style.WORD_COLOR_WHITE);
                /**
                 *   private DefaultTableModel modelCustomer;
                 *         private JTableHeader headerCustomer;
                 *         private JScrollPane scrollPaneCustomer;
                 *         private JTabbedPane tabbedPaneCustomer;
                 */
                tableCustomer = createTable(modelCustomer, customerColumnNames);
                // Thiết lập renderer cho cột ảnh
                tableCustomer.getColumnModel().getColumn(customerColumnNames.length - 1).setCellRenderer(new ImageInJTable.ImageRenderer());
                tableCustomer.setRowHeight(100);
                resizeColumnWidth(tableCustomer, 219);
                modelCustomer = (DefaultTableModel) tableCustomer.getModel();

                customers = customerController.getAll();
                upDataTable(customers, modelCustomer, tableCustomer);


                scrollPaneCustomer = new JScrollPane(tableCustomer);
                tabbedPaneCustomer = createTabbedPane(scrollPaneCustomer, "Customer", Style.FONT_HEADER_ROW_TABLE);
                tabbedPaneCustomer.add("Sales Chart",new Schemas());

                billTextDisplayPanal= new TextDisplayPanel();
                tabbedPaneCustomer.add("Customer Bill",billTextDisplayPanal);
                add(tabbedPaneCustomer, BorderLayout.CENTER);


            }
        }
        private void reload(){
            customers = customerController.getAll();
            upDataTable(customers, modelCustomer, tableCustomer);
            billTextDisplayPanal.setText("You should continue to find the customer Id!!!");
        }
        public int getIndexSelectedTab(){
            return tabbedPaneCustomer.getSelectedIndex();
        }

        private class Schemas extends JPanel {
            public Schemas() {

                // Tạo biểu đồ với dữ liệu
                JFreeChart barChart = ChartFactory.createBarChart(
                        "Purchase Quantity by Customer",
                        "Customer",
                        "Number of purchased",
                        createDataset(),
                        PlotOrientation.VERTICAL,
                        true, true, false);

                // Tạo panel chứa biểu đồ
                ChartPanel chartPanel = new ChartPanel(barChart);
                chartPanel.setPreferredSize(new Dimension(800, 600));

                // Đặt layout cho JPanel và thêm ChartPanel vào
                this.setLayout(new BorderLayout());
                this.add(chartPanel, BorderLayout.CENTER);
            }



            private CategoryDataset createDataset() {
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                // Dữ liệu mẫu: Thay thế bằng dữ liệu từ database
                Map<String, Integer> topCustomers = new HashMap<>();

                consertCustomerToMap(customers, topCustomers);

                // Thêm dữ liệu vào dataset
                for (Map.Entry<String, Integer> entry : topCustomers.entrySet()) {
                    dataset.addValue(entry.getValue(), "Số lượng mua", entry.getKey());
                }

                return dataset;
            }

            private void consertCustomerToMap(ArrayList<Customer> customers, Map<String, Integer> map) {
                for (Customer customer : customers) {
                    map.put(customer.getFullName(), customer.getNumberOfPurchased());
                }
            }
        }

        public static void upDataTable(ArrayList<Customer> customers, DefaultTableModel modelCustomerTable ,JTable tableCustomer) {
            Object[][] rowData = Customer.getDataOnTable(customers);
            ProductPanel.TablePanel.removeDataTable(modelCustomerTable);
            for (int i = 0; i < rowData.length; i++) {
                modelCustomerTable.addRow(rowData[i]);
            }
        }


    }

    // Hoang's Code // Tuan
    class InventoryPanel extends JPanel {
        private JTable tableInventory, tableImport, tableExport;
        InventoryControlPanel inventoryControlPanel;
        ImportPanel importPanel;
        ExportPanel exportPanel;
        CardLayout cardLayoutInventory;

        private DefaultTableModel modelInventory, modelImport, modelExport;
        private ArrayList<Supplier> suppliers = SupplierPanel.reloadSuppliers();
        private ArrayList<Product> products;

        InventoryPanel() {
            cardLayoutInventory = new CardLayout();
            setLayout(cardLayoutInventory);

            importPanel = new ImportPanel();
            exportPanel = new ExportPanel();
            inventoryControlPanel = new InventoryControlPanel();

            add(inventoryControlPanel, INVENTORY_CONTROL_CONSTRAINT);
            add(importPanel, IMPORT_CONSTRAINT);
            add(exportPanel, EXPORT_CONSTRAINT);

            cardLayoutInventory.show(this, INVENTORY_CONTROL_CONSTRAINT);
        }

        public void showPanelInInventory(String panelName) {
            cardLayoutInventory.show(this, panelName); // method chuyển đổi giữa các panel
        }

        private void reloadProducts() {
            products = ProductPanel.reloadProducts();
        }

        private void reloadProducts(String status) {
            reloadProducts();
            products.removeIf(product -> !(status.equals(product.getStatus())));
        }

        private void upDataProducts(DefaultTableModel tableModel) {
            reloadProducts();
            ProductPanel.upDataProducts(products, tableModel);
        }

        private void upDataProductsByStatus(DefaultTableModel tableModel, String status) {
            reloadProducts(status);
            ProductPanel.upDataProducts(products, tableModel);
        }

        private void updateProduct() {
            upDataProducts(modelInventory);
            upDataProductsByStatus(modelImport, Product.IN_STOCK);
            upDataProductsByStatus(modelExport, Product.AVAILABLE);
        }
        // panel chứa các chức năng tương tác của inventory

        public class InventoryControlPanel extends JPanel {

            private JTabbedPane tabbedPaneMain;
            private JTextField searchTextField;
            private JButton searchBt;

            InventoryControlPanel() {
                setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.insets = new Insets(5, 5, 5, 5);
                gbc.gridy = 0;
                gbc.weightx = 1.0;

                // Add button panel
                ButtonPanel buttonPanel = new ButtonPanel();
                gbc.gridwidth = 6;
                gbc.gridx = 0;
                add(buttonPanel, gbc);

                // Add search field and search button
                gbc.gridy = 1;
                gbc.gridwidth = 2;
                add(createSearchField(), gbc);
                gbc.gridx = 4;
                gbc.gridwidth = 1;
                add(createSearchButton(), gbc);

                // Add table panel
                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.gridwidth = 6;
                gbc.fill = GridBagConstraints.BOTH;
                gbc.weightx = 1.0;
                gbc.weighty = 1.0;
                add(new TablePanel(), gbc);
            }

            private JTextField createSearchField() {
                searchTextField = new JTextField("Search");
                searchTextField.setPreferredSize(new Dimension(250, 40));
                searchTextField.setFont(Style.FONT_TEXT_CUSTOMER);
                searchTextField.setForeground(Color.GRAY);
                searchTextField.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        if (searchTextField.getText().equals("Search")) {
                            searchTextField.setText("");
                            searchTextField.setForeground(Color.BLACK);
                        }
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        if (searchTextField.getText().isEmpty()) {
                            searchTextField.setForeground(Color.GRAY);
                            searchTextField.setText("Search");
                        }
                    }
                });
                return searchTextField;
            }

            private JButton createSearchButton() {
                searchBt = new JButton();
                setStyleButton(searchBt, Style.FONT_TEXT_CUSTOMER, Style.WORD_COLOR_WHITE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, SwingConstants.CENTER, new Dimension(68, 38));
                setIconSmallButton("src/main/java/Icon/search_Icon.png", searchBt);
                return searchBt;
            }

            private class ButtonPanel extends JPanel {
                public ButtonPanel() {
                    setLayout(new GridBagLayout());
                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.fill = GridBagConstraints.HORIZONTAL;
                    gbc.insets = new Insets(5, 5, 5, 5);
                    gbc.weightx = 1.0;


                    //Add to Sale
                    {
                        JButton addToSaleListBt = new JButton("Add To Sale");
                        gbc.gridx = 0;
                        add(addToSaleListBt, gbc);
                    }

                    //Import Product
                    {
                        JButton importProduct = new JButton("Import Product");
                        importProduct.addActionListener(e -> showPanelInInventory(IMPORT_CONSTRAINT));
                        gbc.gridx = 1;
                        add(importProduct, gbc);
                    }

                    //Export Product
                    {
                        JButton exportProduct = new JButton("Export Product");
                        exportProduct.addActionListener(e -> showPanelInInventory(EXPORT_CONSTRAINT));
                        gbc.gridx = 2;
                        add(exportProduct, gbc);
                    }

                    //Export Excel
                    {
                        JButton exportExcelBt = new JButton("Export Excel");
                        gbc.gridx = 3;
                        add(exportExcelBt, gbc);
                    }

                    //Modify
                    {
                        JButton modifyBt = new JButton("Modify");
                        modifyBt.addActionListener(e -> modifyTable());
                        gbc.gridx = 4;
                        add(modifyBt, gbc);
                    }

                    //Delete
                    {
                        JButton deleteBt = new JButton("Delete");
                        gbc.gridx = 5;
                        add(deleteBt, gbc);
                    }
                }

                private void modifyTable() {
                    int index = tabbedPaneMain.getSelectedIndex();

                    JTable selectedTable = switch (index) {
                        case 0 -> {
                            reloadProducts();
                            yield tableInventory;
                        }
                        case 1 -> {
                            reloadProducts(Product.IN_STOCK);
                            yield tableImport;
                        }
                        case 2 -> {
                            reloadProducts(Product.AVAILABLE);
                            yield tableExport;
                        }
                        default -> null;
                    };

                    if (selectedTable != null && selectedTable.getSelectedRow() != -1) {
                        int selectedRow = selectedTable.getSelectedRow();
                        SwingUtilities.invokeLater(() -> {
                            new ProductModifyForm(products.get(selectedRow), InventoryPanel.this::updateProduct).setVisible(true);
                        });
                    } else {
                        ToastNotification.showToast("Please select a row to modify.", 3000, 400, 50);
                    }
                }
            }

            private class TablePanel extends JPanel {
                public TablePanel() {
                    setLayout(new BorderLayout());

                    // Create tables
                    tableInventory = createTable(modelInventory, columnNamesPRODUCT);
                    tableImport = createTable(modelImport, columnNamesPRODUCT);
                    tableExport = createTable(modelExport, columnNamesPRODUCT);

                    modelInventory = (DefaultTableModel) tableInventory.getModel();
                    upDataProducts(modelInventory);
                    modelImport = (DefaultTableModel) tableImport.getModel();
                    upDataProductsByStatus(modelImport, Product.IN_STOCK);
                    modelExport = (DefaultTableModel) tableExport.getModel();
                    upDataProductsByStatus(modelExport, Product.AVAILABLE);

                    // Add tables to tabbed pane
                    tabbedPaneMain = new JTabbedPane();
                    tabbedPaneMain.setFont(Style.FONT_HEADER_ROW_TABLE);
                    tabbedPaneMain.addTab("Inventory", new JScrollPane(tableInventory));
                    tabbedPaneMain.addTab("In Stock Products", new JScrollPane(tableImport));
                    tabbedPaneMain.addTab("Available For Sale Products", new JScrollPane(tableExport));

                    add(tabbedPaneMain, BorderLayout.CENTER);
                }
            }

            // Helper methods for setting button styles
            private void setStyleButton(JButton button, Font font, Color textColor, Color bgColor, int alignment, Dimension size) {
                button.setFont(font);
                button.setForeground(textColor);
                button.setBackground(bgColor);
                button.setHorizontalAlignment(alignment);
                button.setPreferredSize(size);
            }
        }

        class ImportPanel extends JPanel {
            LeftPn leftPn;
            RightPn rightPn;
            private JTextField SupplierIDTF, nameSupplierTF, addressSupplierTF, phoneSupplierTF, emailSupplierTF, cooperationDayTF,
                    productIDTF, productNameTF, amountTF, priceTF, typeTF, brandTF, operatingSystemTF, cpuTF, memoryTF, ramTF, madeInTF, statusTF, diskTF, weightTF, monitorTF, cardTF;
            // tôi đã thêm :"Disk","Weight","Monitor","Card"
            private JTextField[] supplierTFArray = {SupplierIDTF, nameSupplierTF, addressSupplierTF, phoneSupplierTF, emailSupplierTF, cooperationDayTF};
            private final JTextField[] productTFArray = {productIDTF, productNameTF, amountTF, priceTF, typeTF, brandTF, operatingSystemTF, cpuTF, memoryTF, ramTF, madeInTF, statusTF, diskTF, weightTF, monitorTF, cardTF};

            ImportPanel() {
                setLayout(new GridLayout(1, 2));
                leftPn = new LeftPn();
                rightPn = new RightPn();
                add(leftPn);
                add(rightPn);

            }

            // panel chung chứa ( panel thông tin nhà cung cấp | panel mã đơn hàng, người đăt )
            class LeftPn extends JPanel {
                SupplierPn supplierPn;
                OrderInfo orderInfo;

                LeftPn() {
                    setLayout(new BorderLayout());
                    supplierPn = new SupplierPn();
                    orderInfo = new OrderInfo();
                    add(supplierPn, BorderLayout.CENTER);
                    add(orderInfo, BorderLayout.SOUTH);
                }
            }

            class SupplierPn extends JPanel {
                AddNewSupplierPn addNewSupplierPn;
                AddSupplierFromListPn addSupplierFromListPn;
                CardLayout cardLayoutSupplierPn;

                JButton addFromListBt, clearAllBt, addBt, backBt;

                static final String ADD_NEW_SUPPLIER_CONSTRAINT = "addNewSupplier";
                static final String ADD_SUPPLIER_FROM_LIST_CONSTRAINT = "addSupplierFromList";

                SupplierPn() {
                    Border border = BorderFactory.createTitledBorder(
                            BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 3), // Đường viền
                            "Supplier", // Tiêu đề
                            TitledBorder.LEFT, // Canh trái
                            TitledBorder.TOP, // Canh trên
                            new Font("Arial", Font.BOLD, 20), // Phông chữ và kiểu chữ
                            Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE // Màu chữ
                    );
                    setBorder(border);

                    addNewSupplierPn = new AddNewSupplierPn();
                    addSupplierFromListPn = new AddSupplierFromListPn();

                    cardLayoutSupplierPn = new CardLayout();
                    setLayout(cardLayoutSupplierPn);

                    cardLayoutSupplierPn.show(this, ADD_NEW_SUPPLIER_CONSTRAINT);

                    add(addNewSupplierPn, ADD_NEW_SUPPLIER_CONSTRAINT);
                    add(addSupplierFromListPn, ADD_SUPPLIER_FROM_LIST_CONSTRAINT);

                }

                public void showPanelAddSupplier(String panelName) {
                    cardLayoutSupplierPn.show(this, panelName); // method chuyển đổi giữa các panel
                }

                class AddNewSupplierPn extends JPanel {
                    AddNewSupplierPn() {
                        setLayout(new GridBagLayout());
                        GridBagConstraints gbc = new GridBagConstraints();
                        gbc.insets = new Insets(15, 5, 15, 5);
                        gbc.gridx = 0; // Cột đầu tiên
                        gbc.gridy = 0; // Dòng đầu tiên
                        gbc.gridwidth = 1; // Mỗi nút bấm chiếm 1 cột
                        gbc.fill = GridBagConstraints.BOTH; // Nút sẽ lấp đầy cả không gian
                        gbc.weightx = 0.5; // Phân bổ chiều rộng đều cho cả hai nút
                        gbc.weighty = 0.0;

                        clearAllBt = createClearAllButton(this);
                        addFromListBt = new JButton("Add From Supplier List");
                        addFromListBt.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                showPanelAddSupplier(ADD_SUPPLIER_FROM_LIST_CONSTRAINT);
                            }
                        });

                        gbc.gridx = 0;
                        gbc.gridy = 0;
                        gbc.gridwidth = 1; // mỗi nút bấm chiếm 1 cột
                        add(clearAllBt, gbc);

                        gbc.gridx = 1;
                        gbc.gridy = 0;
                        add(addFromListBt, gbc);

                        // Thiết lập GridBagConstraints cho các label và text field
                        gbc.fill = GridBagConstraints.HORIZONTAL;
                        gbc.gridwidth = 1;
                        gbc.weightx = 1.0; // Cân bằng không gian theo chiều ngang
                        gbc.weighty = 0.1;
                        gbc.gridy = 1;

                        for (int i = 0; i < columnNamesSUPPLIER.length; i++) {
                            gbc.gridx = 0; // Cột 1: Label
                            // Bắt đầu từ dòng thứ 2
                            gbc.gridwidth = 1;
                            gbc.fill = GridBagConstraints.BOTH;
                            gbc.anchor = GridBagConstraints.WEST;// cân nhắc

                            JLabel lb = new JLabel(columnNamesSUPPLIER[i]);
                            lb.setFont(Style.FONT_BUTTON_CUSTOMER);
                            lb.setPreferredSize(new Dimension(150, 30));
                            add(lb, gbc);

                            gbc.gridx = 1; // Cột 2: TextField
                            gbc.anchor = GridBagConstraints.EAST;
                            supplierTFArray[i] = new JTextField();
                            supplierTFArray[i].setPreferredSize(new Dimension(280, 30));
                            supplierTFArray[i].setFont(Style.FONT_TEXT_LOGIN_FRAME);
                            add(supplierTFArray[i], gbc);

                            gbc.gridy++;
                        }
                    }
                }

                class AddSupplierFromListPn extends JPanel {
                    JTable tableGetDataSupplier;
                    DefaultTableModel modelGetDataSupplier;
                    JScrollPane scrollPaneGetDataSupplierTable;
                    JTabbedPane tabbedPaneAddSupplier;

                    AddSupplierFromListPn() {
                        setLayout(new GridBagLayout());
                        GridBagConstraints gbc = new GridBagConstraints();
                        gbc.insets = new Insets(5, 5, 5, 5);

                        gbc.gridx = 0; // Cột đầu tiên
                        gbc.gridy = 0; // Dòng đầu tiên
                        gbc.gridwidth = 1; // Mỗi nút bấm chiếm 1 cột
                        gbc.fill = GridBagConstraints.BOTH; // Nút sẽ lấp đầy cả không gian
                        gbc.weightx = 0.5; // Phân bổ chiều rộng đều cho cả hai nút
                        gbc.weighty = 0.0;

                        backBt = new JButton("Back");// nút quay trở lại form thêm mới sản phẩm
                        backBt.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                showPanelAddSupplier(ADD_NEW_SUPPLIER_CONSTRAINT);
                            }
                        });
                        add(backBt, gbc);

                        gbc.gridx = 1; // Cột thứ hai
                        gbc.fill = GridBagConstraints.BOTH;
                        addBt = new JButton("Add to Form");// nút thêm nhà cung cấp từ bảng vào form
                        add(addBt, gbc);

                        // thêm bảng chứa dữ liệu supplier vào để trích xuất ra form
                        tableGetDataSupplier = createTable(modelGetDataSupplier, columnNamesSUPPLIER);
                        resizeColumnWidth(tableGetDataSupplier, 100);
                        scrollPaneGetDataSupplierTable = new JScrollPane(tableGetDataSupplier);

                        tabbedPaneAddSupplier = createTabbedPane(scrollPaneGetDataSupplierTable, "Supplier List", Style.FONT_HEADER_ROW_TABLE);

                        gbc.gridx = 0; // Quay lại cột đầu tiên
                        gbc.gridy = 1; // Dòng thứ hai
                        gbc.gridwidth = 2; // JTable chiếm cả 2 cột
                        gbc.fill = GridBagConstraints.BOTH; // JTable lấp đầy cả chiều rộng và chiều cao
                        gbc.weightx = 1.0; // Phân bổ chiều rộng cho JTable
                        gbc.weighty = 1.0;
                        add(tabbedPaneAddSupplier, gbc);
                    }
                }
            }

            class OrderInfo extends JPanel {
                JLabel receiptNoteCode, receiptNoteCreator;
                JTextField receiptNoteCodeTF, receiptNoteCreatorTF;

                OrderInfo() {
                    setLayout(new GridLayout(2, 2));
                    setPreferredSize(new Dimension(300, 80));

                    receiptNoteCode = new JLabel("Receipt Note Code:");
                    formatLabel(receiptNoteCode, Style.FONT_TEXT_LOGIN_FRAME, Color.black);

                    receiptNoteCreator = new JLabel("Receipt Note Creator:");
                    formatLabel(receiptNoteCreator, Style.FONT_TEXT_LOGIN_FRAME, Color.black);

                    receiptNoteCodeTF = new JTextField(20);
                    formatTextField(receiptNoteCodeTF, Style.FONT_TEXT_LOGIN_FRAME, Color.black, new Dimension(200, 30));

                    receiptNoteCreatorTF = new JTextField(20);
                    formatTextField(receiptNoteCreatorTF, Style.FONT_TEXT_LOGIN_FRAME, Color.black, new Dimension(200, 30));

                    add(receiptNoteCode);
                    add(receiptNoteCodeTF);
                    add(receiptNoteCreator);
                    add(receiptNoteCreatorTF);
                }
            }


            //panel chứa thông tin sản phẩm cần mua, xác nhận thanh toán
            class RightPn extends JPanel {
                ProductPn productPn;
                PaymentConfirmationPn paymentConfirmationPn;

                RightPn() {
                    setLayout(new BorderLayout());
                    productPn = new ProductPn();// thông tin sản phẩm cần mua
                    paymentConfirmationPn = new PaymentConfirmationPn(); //xác nhận thanh toán
                    add(productPn, BorderLayout.CENTER);
                    add(paymentConfirmationPn, BorderLayout.SOUTH);
                }
            }

            class ProductPn extends JPanel {
                ProductListPn productList;
                AddProductFromList addProductFromList;
                AddNewProductPn addNewProductPn;
                CardLayout cardLayout = new CardLayout();
                final String PRODUCT_LIST = "ProductList";
                final String ADD_NEW_PRODUCT = "addNewProduct";
                final String ADD_PRODUCT_FROM_LIST = "addProductFromList";

                ProductPn() {
                    Border border = BorderFactory.createTitledBorder(
                            BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 3), // Đường viền
                            "Product", // Tiêu đề
                            TitledBorder.LEFT, // Canh trái
                            TitledBorder.TOP, // Canh trên
                            new Font("Arial", Font.BOLD, 20), // Phông chữ và kiểu chữ
                            Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE // Màu chữ
                    );
                    setBorder(border);
                    setLayout(cardLayout);

                    productList = new ProductListPn();
                    addProductFromList = new AddProductFromList();
                    addNewProductPn = new AddNewProductPn();

                    add(addProductFromList, ADD_PRODUCT_FROM_LIST);
                    add(productList, PRODUCT_LIST);
                    add(addNewProductPn, ADD_NEW_PRODUCT);

                    cardLayout.show(this, PRODUCT_LIST);
                }

                public void showPanelImportProduct(String panelName) {
                    cardLayout.show(this, panelName); // method chuyển đổi giữa các panel
                }


                class ProductListPn extends JPanel {
                    JButton addNewBt, addFromListBt, deleteProduct;

                    JTable tableImportProductList;
                    DefaultTableModel modelImportProductList;
                    JTableHeader tableHeaderImportProductList;
                    JScrollPane scrollPaneImportProductList;
                    JTabbedPane tabbedPaneImportProductList;

                    ProductListPn() {
                        setLayout(new GridBagLayout());
                        GridBagConstraints gbc = new GridBagConstraints();
                        gbc.insets = new Insets(5, 5, 5, 5);
                        gbc.gridx = 0;
                        gbc.gridy = 0;
                        gbc.gridwidth = 1;
                        gbc.fill = GridBagConstraints.BOTH;
                        gbc.weightx = 0.5;
                        gbc.weighty = 0.0;

                        addNewBt = new JButton("Add new Product");// button add product to list
                        addNewBt.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                showPanelImportProduct(ADD_NEW_PRODUCT);
                            }
                        });
                        add(addNewBt, gbc);

                        gbc.gridx = 1;
                        gbc.fill = GridBagConstraints.BOTH;
                        addFromListBt = new JButton("Add product from list ");
                        addFromListBt.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                showPanelImportProduct(ADD_PRODUCT_FROM_LIST);
                            }
                        });
                        add(addFromListBt, gbc);

                        gbc.gridx = 2;
                        gbc.fill = GridBagConstraints.BOTH;
                        deleteProduct = new JButton("Delete product");
                        add(deleteProduct, gbc);

                        tableImportProductList = createTable(modelImportProductList, columnNamesPRODUCT);
                        resizeColumnWidth(tableImportProductList, 100);
                        scrollPaneImportProductList = new JScrollPane(tableImportProductList);

                        tabbedPaneImportProductList = createTabbedPane(scrollPaneImportProductList, "Inventory Import List", Style.FONT_HEADER_ROW_TABLE);
                        gbc.gridx = 0; // Quay lại cột đầu tiên
                        gbc.gridy = 1; // Dòng thứ hai
                        gbc.gridwidth = 3; // JTable chiếm cả 2 cột
                        gbc.fill = GridBagConstraints.BOTH; // JTable lấp đầy cả chiều rộng và chiều cao
                        gbc.weightx = 1.0;
                        gbc.weighty = 1.0;
                        add(tabbedPaneImportProductList, gbc);
                    }
                }

                // bug
                class AddNewProductPn extends JPanel {
                    JButton backBt, clearAllBt, addBt;

                    AddNewProductPn() {
                        setLayout(new GridBagLayout());
                        GridBagConstraints gbc = new GridBagConstraints();

                        gbc.gridx = 0;
                        gbc.gridy = 0;
                        gbc.gridwidth = 2;
                        gbc.fill = GridBagConstraints.HORIZONTAL;
                        gbc.weightx = 1.0;
                        gbc.weighty = 0.0;

                        backBt = new JButton("Back");
                        backBt.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                showPanelImportProduct(PRODUCT_LIST);
                            }
                        });
                        clearAllBt = createClearAllButton(this);

                        addBt = new JButton("Add to List");
                        JPanel buttonPanel = new JPanel();
                        buttonPanel.add(backBt);
                        buttonPanel.add(clearAllBt);
                        buttonPanel.add(addBt);
                        add(buttonPanel, gbc);

                        gbc.gridwidth = 1;
                        gbc.fill = GridBagConstraints.HORIZONTAL;
                        gbc.weightx = 1.0;
                        gbc.weighty = 0.1;
                        gbc.gridy = 1;
                        // add label and textfield to panel
                        int index = 0;
                        for (int i = 1; i < columnNamesPRODUCT.length; i++) {
                            gbc.gridx = 0; // Cột 1: Label
                            gbc.gridwidth = 1;
                            gbc.fill = GridBagConstraints.BOTH;
                            gbc.anchor = GridBagConstraints.WEST;// cân nhắc

                            JLabel lb = new JLabel(columnNamesPRODUCT[i]);
                            lb.setFont(Style.FONT_BUTTON_CUSTOMER);
                            lb.setPreferredSize(new Dimension(150, 30));
                            add(lb, gbc);

                            gbc.gridx = 1; // Cột 2: TextField
                            gbc.anchor = GridBagConstraints.EAST;
                            productTFArray[index] = new JTextField();
                            productTFArray[index].setPreferredSize(new Dimension(250, 30));
                            productTFArray[index].setFont(Style.FONT_TEXT_LOGIN_FRAME);
                            add(productTFArray[index], gbc);

                            index++;
                            gbc.gridy++;
                        }
                    }
                }

                class AddProductFromList extends JPanel {
                    JButton backBt, addBt;

                    JTable tableAddProductFromList;
                    DefaultTableModel modelAddProductFromList;
                    JScrollPane scrollPaneAddProductFromList;
                    JTabbedPane tabbedPaneAddProductFromList;

                    AddProductFromList() {
                        setLayout(new GridBagLayout());
                        GridBagConstraints gbc = new GridBagConstraints();
                        gbc.insets = new Insets(5, 5, 5, 5);

                        gbc.gridx = 0; // Cột đầu tiên
                        gbc.gridy = 0; // Dòng đầu tiên
                        gbc.gridwidth = 1; // Mỗi nút bấm chiếm 1 cột
                        gbc.fill = GridBagConstraints.BOTH; // Nút sẽ lấp đầy cả không gian
                        gbc.weightx = 0.5; // Phân bổ chiều rộng đều cho cả hai nút
                        gbc.weighty = 0.0;

                        backBt = new JButton("Back");
                        backBt.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                showPanelImportProduct(PRODUCT_LIST);
                            }
                        });
                        add(backBt, gbc);

                        gbc.gridx = 1; // Cột thứ hai
                        gbc.fill = GridBagConstraints.BOTH;
                        addBt = new JButton("Add to Form");
                        add(addBt, gbc);

                        // thêm bảng chứa dữ liệu vào để trích xuất ra form
                        tableAddProductFromList = createTable(modelAddProductFromList, columnNamesPRODUCT);
                        resizeColumnWidth(tableAddProductFromList, 100);
                        scrollPaneAddProductFromList = new JScrollPane(tableAddProductFromList);
                        tabbedPaneAddProductFromList = createTabbedPane(scrollPaneAddProductFromList, "Inventory", Style.FONT_HEADER_ROW_TABLE);
                        gbc.gridx = 0;
                        gbc.gridy = 1;
                        gbc.gridwidth = 3;
                        gbc.fill = GridBagConstraints.BOTH;
                        gbc.weightx = 1.0;
                        gbc.weighty = 1.0;
                        add(tabbedPaneAddProductFromList, gbc);
                    }
                }
            }

            class PaymentConfirmationPn extends JPanel {
                JLabel totalPrice, totalPriceValue;
                JButton cancelBt, importBt;

                PaymentConfirmationPn() {
                    setLayout(new GridLayout(2, 2));
                    setPreferredSize(new Dimension(300, 80));

                    totalPrice = new JLabel("Total Price:");
                    formatLabel(totalPrice, Style.FONT_TEXT_LOGIN_FRAME, Color.black);
                    totalPriceValue = new JLabel(" $1000000000");
                    formatLabel(totalPriceValue, Style.FONT_BUTTON_LOGIN_FRAME, Color.RED);


                    cancelBt = new JButton("Cancel");
                    setStyleButton(cancelBt, Style.FONT_BUTTON_LOGIN_FRAME, Color.white, Style.DELETE_BUTTON_COLOR_RED, SwingConstants.CENTER, new Dimension(200, 40));
                    cancelBt.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            showPanelInInventory(INVENTORY_CONTROL_CONSTRAINT);
                        }
                    });

                    importBt = new JButton("Import");
                    setStyleButton(importBt, Style.FONT_BUTTON_LOGIN_FRAME, Color.white, Style.CONFIRM_BUTTON_COLOR_GREEN, SwingConstants.CENTER, new Dimension(200, 40));

                    add(totalPrice);
                    add(totalPriceValue);
                    add(cancelBt);
                    add(importBt);
                }
            }


        }

        class ExportPanel extends JPanel {
            LeftPn leftPn;
            RightPn rightPn;

            private JTextField customerIDTF, customerNameTF, customerPhoneNumberTF, customerEmailTF, customerAddressTF, orderDateTF, productIDTF, productNameTF, amountTF,
                    priceTF, typeTF, brandTF, operatingSystemTF, cpuTF, memoryTF, ramTF, madeInTF, statusTF, diskTF, weightTF, monitorTF, cardTF;
            private final JTextField[] customerTFArray = {customerIDTF, customerNameTF, customerPhoneNumberTF, customerEmailTF, customerAddressTF, orderDateTF};
            private final JTextField[] productTFArray = {productIDTF, productNameTF, amountTF, priceTF, typeTF, brandTF, operatingSystemTF, cpuTF, memoryTF, ramTF, madeInTF, statusTF, diskTF, weightTF, monitorTF, cardTF};

            ExportPanel() {
                setLayout(new GridLayout(1, 2));
                leftPn = new LeftPn();
                rightPn = new RightPn();
                add(leftPn);
                add(rightPn);

            }

            // panel chung chứa ( panel thông tin khách hàng | panel mã đơn hàng, người đăt )
            class LeftPn extends JPanel {
                CustomerPn customerPn;
                OrderInfo orderInfo;

                LeftPn() {
                    setLayout(new BorderLayout());
                    customerPn = new CustomerPn();
                    orderInfo = new OrderInfo();
                    add(customerPn, BorderLayout.CENTER);
                    add(orderInfo, BorderLayout.SOUTH);

                }

            }

            class CustomerPn extends JPanel {
                AddNewCustomerPn addNewCustomerPn;
                AddCustomerFromListPn addCustomerFromListPn;
                CardLayout cardLayoutCustomerPn;

                JButton addFromListBt, clearAllBt, addBt, backBt;

                static final String ADD_NEW_CUSTOMER_CONSTRAINT = "addNewCustomer";
                static final String ADD_CUSTOMER_FROM_LIST_CONSTRAINT = "addCustomerFromList";

                CustomerPn() {
                    Border border = BorderFactory.createTitledBorder(
                            BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 3), // Đường viền
                            "Customer", // Tiêu đề
                            TitledBorder.LEFT, // Canh trái
                            TitledBorder.TOP, // Canh trên
                            new Font("Arial", Font.BOLD, 20), // Phông chữ và kiểu chữ
                            Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE // Màu chữ
                    );
                    setBorder(border);
                    cardLayoutCustomerPn = new CardLayout();
                    setLayout(cardLayoutCustomerPn);

                    addNewCustomerPn = new AddNewCustomerPn();
                    addCustomerFromListPn = new AddCustomerFromListPn();

                    cardLayoutCustomerPn.show(this, ADD_NEW_CUSTOMER_CONSTRAINT);

                    add(addNewCustomerPn, ADD_NEW_CUSTOMER_CONSTRAINT);
                    add(addCustomerFromListPn, ADD_CUSTOMER_FROM_LIST_CONSTRAINT);
                }


                public void showPanelAddSupplier(String panelName) {
                    cardLayoutCustomerPn.show(this, panelName); // method chuyển đổi giữa các panel
                }

                class AddNewCustomerPn extends JPanel {
                    AddNewCustomerPn() {
                        setLayout(new GridBagLayout());
                        GridBagConstraints gbc = new GridBagConstraints();
                        gbc.insets = new Insets(15, 5, 15, 5);
                        gbc.gridx = 0; // Cột đầu tiên
                        gbc.gridy = 0; // Dòng đầu tiên
                        gbc.gridwidth = 1; // Mỗi nút bấm chiếm 1 cột
                        gbc.fill = GridBagConstraints.BOTH; // Nút sẽ lấp đầy cả không gian
                        gbc.weightx = 0.5; // Phân bổ chiều rộng đều cho cả hai nút
                        gbc.weighty = 0.0;

                        clearAllBt = createClearAllButton(this);
                        addFromListBt = new JButton("Add From Supplier List");
                        addFromListBt.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                showPanelAddSupplier(ADD_CUSTOMER_FROM_LIST_CONSTRAINT);
                            }
                        });

                        gbc.gridx = 0;
                        gbc.gridy = 0;
                        gbc.gridwidth = 1; // mỗi nút bấm chiếm 1 cột
                        add(clearAllBt, gbc);

                        gbc.gridx = 1;
                        gbc.gridy = 0;
                        add(addFromListBt, gbc);

                        // Thiết lập GridBagConstraints cho các label và text field
                        gbc.fill = GridBagConstraints.HORIZONTAL;
                        gbc.gridwidth = 1;
                        gbc.weightx = 1.0; // Cân bằng không gian theo chiều ngang
                        gbc.weighty = 0.1;
                        gbc.gridy = 1;

                        for (int i = 0; i < columnNamesSUPPLIER.length; i++) {
                            gbc.gridx = 0; // Cột 1: Label
                            // Bắt đầu từ dòng thứ 2
                            gbc.gridwidth = 1;
                            gbc.fill = GridBagConstraints.BOTH;
                            gbc.anchor = GridBagConstraints.WEST;// cân nhắc

                            JLabel lb = new JLabel(columnNamesSUPPLIER[i]);
                            lb.setFont(Style.FONT_BUTTON_CUSTOMER);
                            lb.setPreferredSize(new Dimension(150, 30));
                            add(lb, gbc);

                            gbc.gridx = 1; // Cột 2: TextField
                            gbc.anchor = GridBagConstraints.EAST;
                            customerTFArray[i] = new JTextField();
                            customerTFArray[i].setPreferredSize(new Dimension(280, 30));
                            customerTFArray[i].setFont(Style.FONT_TEXT_LOGIN_FRAME);
                            add(customerTFArray[i], gbc);

                            gbc.gridy++;
                        }
                    }
                }

                class AddCustomerFromListPn extends JPanel {
                    JTable tableGetDataSupplier;
                    DefaultTableModel modelGetDataSupplier;
                    JScrollPane scrollPaneGetDataSupplierTable;
                    JTabbedPane tabbedPaneAddSupplier;

                    AddCustomerFromListPn() {
                        setLayout(new GridBagLayout());
                        GridBagConstraints gbc = new GridBagConstraints();
                        gbc.insets = new Insets(5, 5, 5, 5);

                        gbc.gridx = 0; // Cột đầu tiên
                        gbc.gridy = 0; // Dòng đầu tiên
                        gbc.gridwidth = 1; // Mỗi nút bấm chiếm 1 cột
                        gbc.fill = GridBagConstraints.BOTH; // Nút sẽ lấp đầy cả không gian
                        gbc.weightx = 0.5; // Phân bổ chiều rộng đều cho cả hai nút
                        gbc.weighty = 0.0;

                        backBt = new JButton("Back");// nút quay trở lại form thêm mới sản phẩm
                        backBt.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                showPanelAddSupplier(ADD_NEW_CUSTOMER_CONSTRAINT);
                            }
                        });
                        add(backBt, gbc);

                        gbc.gridx = 1; // Cột thứ hai
                        gbc.fill = GridBagConstraints.BOTH;
                        addBt = new JButton("Add to Form");// nút thêm nhà cung cấp từ bảng vào form
                        add(addBt, gbc);

                        // thêm bảng chứa dữ liệu supplier vào để trích xuất ra form
                        tableGetDataSupplier = createTable(modelGetDataSupplier, columnNamesSUPPLIER);
                        resizeColumnWidth(tableGetDataSupplier, 100);
                        scrollPaneGetDataSupplierTable = new JScrollPane(tableGetDataSupplier);

                        tabbedPaneAddSupplier = createTabbedPane(scrollPaneGetDataSupplierTable, "Supplier List", Style.FONT_HEADER_ROW_TABLE);

                        gbc.gridx = 0; // Quay lại cột đầu tiên
                        gbc.gridy = 1; // Dòng thứ hai
                        gbc.gridwidth = 2; // JTable chiếm cả 2 cột
                        gbc.fill = GridBagConstraints.BOTH; // JTable lấp đầy cả chiều rộng và chiều cao
                        gbc.weightx = 1.0; // Phân bổ chiều rộng cho JTable
                        gbc.weighty = 1.0;
                        add(tabbedPaneAddSupplier, gbc);

                    }

                }

            }

            class OrderInfo extends JPanel {
                JLabel receiptNoteCode, receiptNoteCreator;
                JTextField receiptNoteCodeTF, receiptNoteCreatorTF;

                OrderInfo() {
                    setLayout(new GridLayout(2, 2));
                    setPreferredSize(new Dimension(300, 80));

                    receiptNoteCode = new JLabel("Receipt Note Code:");
                    formatLabel(receiptNoteCode, Style.FONT_TEXT_LOGIN_FRAME, Color.black);

                    receiptNoteCreator = new JLabel("Receipt Note Creator:");
                    formatLabel(receiptNoteCreator, Style.FONT_TEXT_LOGIN_FRAME, Color.black);

                    receiptNoteCodeTF = new JTextField(20);
                    formatTextField(receiptNoteCodeTF, Style.FONT_TEXT_LOGIN_FRAME, Color.black, new Dimension(200, 30));

                    receiptNoteCreatorTF = new JTextField(20);
                    formatTextField(receiptNoteCreatorTF, Style.FONT_TEXT_LOGIN_FRAME, Color.black, new Dimension(200, 30));

                    add(receiptNoteCode);
                    add(receiptNoteCodeTF);
                    add(receiptNoteCreator);
                    add(receiptNoteCreatorTF);
                }
            }


            //panel chứa thông tin sản phẩm cần mua, xác nhận thanh toán
            class RightPn extends JPanel {
                ProductPn productPn;
                PaymentConfirmationPn paymentConfirmationPn;

                RightPn() {
                    setLayout(new BorderLayout());
                    productPn = new ProductPn();
                    paymentConfirmationPn = new PaymentConfirmationPn();

                    add(productPn, BorderLayout.CENTER);
                    add(paymentConfirmationPn, BorderLayout.SOUTH);
                }
            }

            class ProductPn extends JPanel {
                ProductListPn productList;
                AddProductFromList addProductFromList;
                AddNewProductPn addNewProductPn;
                CardLayout cardLayout = new CardLayout();
                final String PRODUCT_LIST = "ProductList";
                final String ADD_NEW_PRODUCT = "addNewProduct";
                final String ADD_PRODUCT_FROM_LIST = "addProductFromList";

                ProductPn() {
                    Border border = BorderFactory.createTitledBorder(
                            BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 3), // Đường viền
                            "Product", // Tiêu đề
                            TitledBorder.LEFT, // Canh trái
                            TitledBorder.TOP, // Canh trên
                            new Font("Arial", Font.BOLD, 20), // Phông chữ và kiểu chữ
                            Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE // Màu chữ
                    );
                    setBorder(border);
                    setLayout(cardLayout);

                    productList = new ProductListPn();
                    addProductFromList = new AddProductFromList();
                    addNewProductPn = new AddNewProductPn();

                    add(addProductFromList, ADD_PRODUCT_FROM_LIST);
                    add(productList, PRODUCT_LIST);
                    add(addNewProductPn, ADD_NEW_PRODUCT);

                    cardLayout.show(this, PRODUCT_LIST);
                }

                public void showPanelImportProduct(String panelName) {
                    cardLayout.show(this, panelName); // method chuyển đổi giữa các panel
                }


                class ProductListPn extends JPanel {
                    JButton addNewBt, addFromListBt, deleteProduct;

                    JTable tableImportProductList;
                    DefaultTableModel modelImportProductList;
                    JScrollPane scrollPaneImportProductList;
                    JTabbedPane tabbedPaneImportProductList;

                    ProductListPn() {
                        setLayout(new GridBagLayout());
                        GridBagConstraints gbc = new GridBagConstraints();
                        gbc.insets = new Insets(5, 5, 5, 5);
                        gbc.gridx = 0;
                        gbc.gridy = 0;
                        gbc.gridwidth = 1;
                        gbc.fill = GridBagConstraints.BOTH;
                        gbc.weightx = 0.5;
                        gbc.weighty = 0.0;

                        addNewBt = new JButton("Add new Product");// button add product to list
                        addNewBt.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                showPanelImportProduct(ADD_NEW_PRODUCT);
                            }
                        });
                        add(addNewBt, gbc);

                        gbc.gridx = 1;
                        gbc.fill = GridBagConstraints.BOTH;
                        addFromListBt = new JButton("Add product from list ");
                        addFromListBt.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                showPanelImportProduct(ADD_PRODUCT_FROM_LIST);
                            }
                        });
                        add(addFromListBt, gbc);

                        gbc.gridx = 2;
                        gbc.fill = GridBagConstraints.BOTH;
                        deleteProduct = new JButton("Delete product");
                        add(deleteProduct, gbc);

                        tableImportProductList = createTable(modelImportProductList, columnNamesPRODUCT);
                        resizeColumnWidth(tableImportProductList, 100);
                        scrollPaneImportProductList = new JScrollPane(tableImportProductList);

                        tabbedPaneImportProductList = createTabbedPane(scrollPaneImportProductList, "Inventory Import List", Style.FONT_HEADER_ROW_TABLE);
                        gbc.gridx = 0; // Quay lại cột đầu tiên
                        gbc.gridy = 1; // Dòng thứ hai
                        gbc.gridwidth = 3; // JTable chiếm cả 2 cột
                        gbc.fill = GridBagConstraints.BOTH; // JTable lấp đầy cả chiều rộng và chiều cao
                        gbc.weightx = 1.0;
                        gbc.weighty = 1.0;
                        add(tabbedPaneImportProductList, gbc);
                    }
                }

                class AddNewProductPn extends JPanel {
                    JButton backBt, clearAllBt, addBt;

                    AddNewProductPn() {
                        setLayout(new GridBagLayout());
                        GridBagConstraints gbc = new GridBagConstraints();

                        gbc.gridx = 0;
                        gbc.gridy = 0;
                        gbc.gridwidth = 2;
                        gbc.fill = GridBagConstraints.HORIZONTAL;
                        gbc.weightx = 1.0;
                        gbc.weighty = 0.0;

                        backBt = new JButton("Back");
                        backBt.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                showPanelImportProduct(PRODUCT_LIST);
                            }
                        });
                        clearAllBt = createClearAllButton(this);

                        addBt = new JButton("Add to List");
                        JPanel buttonPanel = new JPanel();
                        buttonPanel.add(backBt);
                        buttonPanel.add(clearAllBt);
                        buttonPanel.add(addBt);
                        add(buttonPanel, gbc);

                        gbc.gridwidth = 1;
                        gbc.fill = GridBagConstraints.HORIZONTAL;
                        gbc.weightx = 1.0;
                        gbc.weighty = 0.1;
                        gbc.gridy = 1;
                        // add label and textfield to panel
                        int index = 0;
                        for (int i = 1; i < columnNamesPRODUCT.length; i++) {
                            gbc.gridx = 0; // Cột 1: Label
                            gbc.gridwidth = 1;
                            gbc.fill = GridBagConstraints.BOTH;
                            gbc.anchor = GridBagConstraints.WEST;// cân nhắc

                            JLabel lb = new JLabel(columnNamesPRODUCT[i]);
                            lb.setFont(Style.FONT_BUTTON_CUSTOMER);
                            lb.setPreferredSize(new Dimension(150, 30));
                            add(lb, gbc);

                            gbc.gridx = 1; // Cột 2: TextField
                            gbc.anchor = GridBagConstraints.EAST;
                            productTFArray[index] = new JTextField();
                            productTFArray[index].setPreferredSize(new Dimension(250, 30));
                            productTFArray[index].setFont(Style.FONT_TEXT_LOGIN_FRAME);
                            add(productTFArray[index], gbc);

                            index++;
                            gbc.gridy++;
                        }
                    }
                }

                class AddProductFromList extends JPanel {
                    JButton backBt, addBt;

                    JTable tableAddProductFromList;
                    DefaultTableModel modelAddProductFromList;
                    JScrollPane scrollPaneAddProductFromList;
                    JTabbedPane tabbedPaneAddProductFromList;

                    AddProductFromList() {
                        setLayout(new GridBagLayout());
                        GridBagConstraints gbc = new GridBagConstraints();
                        gbc.insets = new Insets(5, 5, 5, 5);

                        gbc.gridx = 0; // Cột đầu tiên
                        gbc.gridy = 0; // Dòng đầu tiên
                        gbc.gridwidth = 1; // Mỗi nút bấm chiếm 1 cột
                        gbc.fill = GridBagConstraints.BOTH; // Nút sẽ lấp đầy cả không gian
                        gbc.weightx = 0.5; // Phân bổ chiều rộng đều cho cả hai nút
                        gbc.weighty = 0.0;

                        backBt = new JButton("Back");
                        backBt.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                showPanelImportProduct(PRODUCT_LIST);
                            }
                        });
                        add(backBt, gbc);

                        gbc.gridx = 1; // Cột thứ hai
                        gbc.fill = GridBagConstraints.BOTH;
                        addBt = new JButton("Add to Form");
                        add(addBt, gbc);

                        // thêm bảng chứa dữ liệu vào để trích xuất ra form
                        tableAddProductFromList = createTable(modelAddProductFromList, columnNamesPRODUCT);
                        resizeColumnWidth(tableAddProductFromList, 100);
                        scrollPaneAddProductFromList = new JScrollPane(tableAddProductFromList);
                        tabbedPaneAddProductFromList = createTabbedPane(scrollPaneAddProductFromList, "Inventory", Style.FONT_HEADER_ROW_TABLE);
                        gbc.gridx = 0;
                        gbc.gridy = 1;
                        gbc.gridwidth = 3;
                        gbc.fill = GridBagConstraints.BOTH;
                        gbc.weightx = 1.0;
                        gbc.weighty = 1.0;
                        add(tabbedPaneAddProductFromList, gbc);
                    }
                }
            }

            class PaymentConfirmationPn extends JPanel {
                JLabel totalPrice, totalPriceValue;
                JButton cancelBt, importBt;

                PaymentConfirmationPn() {
                    setLayout(new GridLayout(2, 2));
                    setPreferredSize(new Dimension(300, 80));

                    totalPrice = new JLabel("Total Price:");
                    formatLabel(totalPrice, Style.FONT_TEXT_LOGIN_FRAME, Color.black);


                    totalPriceValue = new JLabel(" $1000000000");
                    formatLabel(totalPriceValue, Style.FONT_BUTTON_LOGIN_FRAME, Color.RED);


                    cancelBt = new JButton("Cancel");
                    setStyleButton(cancelBt, Style.FONT_BUTTON_LOGIN_FRAME, Color.white, Style.DELETE_BUTTON_COLOR_RED, SwingConstants.CENTER, new Dimension(200, 40));
                    cancelBt.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            showPanelInInventory(INVENTORY_CONTROL_CONSTRAINT);
                        }
                    });

                    importBt = new JButton("Export");
                    setStyleButton(importBt, Style.FONT_BUTTON_LOGIN_FRAME, Color.white, Style.CONFIRM_BUTTON_COLOR_GREEN, SwingConstants.CENTER, new Dimension(200, 40));

                    add(totalPrice);
                    add(totalPriceValue);
                    add(cancelBt);
                    add(importBt);
                }
            }


        }
    }
    class AccManagementPanel extends JPanel {
        private final String[] accountColumnNames = {"Serial Number", "ManagerID", "Fullname","Address","Birth Day", "Phone Number", "AccountID"," User Name","Password","Email", "Account creation date","Avata"};

        private JTable tableAccManager;
        private DefaultTableModel modelAccManager;
        private JTableHeader headerCustomer;
        private JScrollPane scrollPaneAccManager;
        private JTabbedPane tabbedPaneAccManager;
        private ToolPanel toolPanel = new ToolPanel();
        private TableCustomerPanel tableAccManagerPanel = new TableCustomerPanel();

        private JButton addAccBt, modifyAccBt, exportAccExcelBt, searchAccBt, reloadAccBt, blockCustomer, writeToFileTXT;
        private JTextField textField;


        private JPanel searchPanel, applicationPanel, mainPanel;

        private ArrayList<ManagerInforDTO> managerInfors = new ArrayList<>();
        private ManagerController managerController = new ManagerController();

        public AccManagementPanel() {
            setLayout(new BorderLayout());
            toolPanel.setBorder(BorderFactory.createTitledBorder("Tool"));
            add(toolPanel, BorderLayout.NORTH);
            add(tableAccManagerPanel, BorderLayout.CENTER);
        }

        public class ToolPanel extends JPanel {
            public ToolPanel() {
                setLayout(new BorderLayout());
                setBackground(Style.WORD_COLOR_WHITE);
                addAccBt = new JButton("Add");
                ButtonConfig.addButtonHoverEffect(addAccBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                setStyleButton(addAccBt, Style.FONT_SIZE_MIN_PRODUCT, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(80, 80));
                ButtonConfig.setIconBigButton("src/main/java/Icon/database-add-icon.png", addAccBt);
                addAccBt.setHorizontalTextPosition(SwingConstants.CENTER); // Chữ ở giữa theo chiều ngang
                addAccBt.setVerticalTextPosition(SwingConstants.BOTTOM);
                addAccBt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                });

                modifyAccBt = new JButton("Modify");
                ButtonConfig.addButtonHoverEffect(modifyAccBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                setStyleButton(modifyAccBt, Style.FONT_SIZE_MIN_PRODUCT, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(80, 80));
                ButtonConfig.setIconBigButton("src/main/java/Icon/modify.png", modifyAccBt);
                modifyAccBt.setHorizontalTextPosition(SwingConstants.CENTER); // Chữ ở giữa theo chiều ngang
                modifyAccBt.setVerticalTextPosition(SwingConstants.BOTTOM);
                modifyAccBt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    }
                });

                // block customer
                blockCustomer = new JButton("Block customer");
                ButtonConfig.addButtonHoverEffect(blockCustomer, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                setStyleButton(blockCustomer, Style.FONT_SIZE_MIN_PRODUCT, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(80, 80));
                ButtonConfig.setIconBigButton("src/main/java/Icon/modify.png", blockCustomer);
                blockCustomer.setHorizontalTextPosition(SwingConstants.CENTER); // Chữ ở giữa theo chiều ngang
                blockCustomer.setVerticalTextPosition(SwingConstants.BOTTOM);
                blockCustomer.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {



                    }
                });

                exportAccExcelBt = new JButton("Export");
                ButtonConfig.addButtonHoverEffect(exportAccExcelBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                setStyleButton(exportAccExcelBt, Style.FONT_SIZE_MIN_PRODUCT, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(80, 80));
                ButtonConfig.setIconBigButton("src/main/java/Icon/icons8-file-excel-32.png", exportAccExcelBt);
                exportAccExcelBt.setHorizontalTextPosition(SwingConstants.CENTER); // Chữ ở giữa theo chiều ngang
                exportAccExcelBt.setVerticalTextPosition(SwingConstants.BOTTOM);
                exportAccExcelBt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {



                    }
                });

                writeToFileTXT = new JButton("to file.txt");
                ButtonConfig.addButtonHoverEffect(writeToFileTXT, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                setStyleButton(writeToFileTXT, Style.FONT_SIZE_MIN_PRODUCT, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(80, 80));
                ButtonConfig.setIconBigButton("src/main/java/Icon/bill.png",writeToFileTXT);
                writeToFileTXT.setHorizontalTextPosition(SwingConstants.CENTER); // Chữ ở giữa theo chiều ngang
                writeToFileTXT.setVerticalTextPosition(SwingConstants.BOTTOM);
                writeToFileTXT.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                });



                textField = new JTextField("Search by name");
                textField.setForeground(Color.GRAY);
                formatTextField(textField, new Font("Arial", 0, 24), Style.WORD_COLOR_BLACK, new Dimension(250, 45));
                textField.addFocusListener( new FocusListener() {
                                                  @Override
                                                  public void focusGained(FocusEvent e) {

                                                  }

                                                  @Override
                                                  public void focusLost(FocusEvent e) {

                                                  }
                                              }

                );

                searchAccBt = new JButton();
                ButtonConfig.addButtonHoverEffect(searchAccBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                setStyleButton(searchAccBt, Style.FONT_SIZE_MIN_PRODUCT, Color.BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(40, 45));
                ButtonConfig.setIconSmallButton("src/main/java/Icon/106236_search_icon.png", searchAccBt);
                searchAccBt.addActionListener(
                        new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {

                            }
                        }
                );

                reloadAccBt = new JButton("reload");
                ButtonConfig.addButtonHoverEffect(reloadAccBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                setStyleButton(reloadAccBt, Style.FONT_SIZE_MIN_PRODUCT, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(80, 80));
                ButtonConfig.setIconBigButton("src/main/java/Icon/reload-icon.png", reloadAccBt);
                reloadAccBt.setHorizontalTextPosition(SwingConstants.CENTER); // Chữ ở giữa theo chiều ngang
                reloadAccBt.setVerticalTextPosition(SwingConstants.BOTTOM);
                reloadAccBt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        reload();

                    }
                });

                searchPanel = new JPanel(new FlowLayout());
                searchPanel.add(textField);
                searchPanel.add(searchAccBt);
                searchPanel.setBackground(Style.WORD_COLOR_WHITE);

                applicationPanel = new JPanel(new FlowLayout());
                applicationPanel.add(addAccBt);

                applicationPanel.add(ButtonConfig.createVerticalSeparator());
                applicationPanel.add(modifyAccBt);
                applicationPanel.add(blockCustomer);

                JLabel none = new JLabel("");
                none.setFont(Style.FONT_SIZE_MIN_PRODUCT);
                none.setHorizontalAlignment(SwingConstants.CENTER); // Căn ngang giữa
                none.setVerticalAlignment(SwingConstants.CENTER);


                applicationPanel.add(ButtonConfig.createVerticalSeparator());
                applicationPanel.add(exportAccExcelBt);
                applicationPanel.add(writeToFileTXT);

                applicationPanel.add(ButtonConfig.createVerticalSeparator());
                applicationPanel.add(reloadAccBt);
                applicationPanel.setBackground(Style.WORD_COLOR_WHITE);

                mainPanel = new JPanel(new GridBagLayout());
                mainPanel.setBackground(Style.BACKGROUND_COLOR);
                GridBagConstraints gbc = new GridBagConstraints();

                // Cấu hình cho panel trái (căn về bên trái)
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.weightx = 1;
                gbc.anchor = GridBagConstraints.WEST;
                mainPanel.add(applicationPanel, gbc);

                gbc.gridx = 1;
                gbc.gridy = 0;
                gbc.weightx = 0;
                gbc.anchor = GridBagConstraints.EAST;
                mainPanel.add(searchPanel, gbc);
                mainPanel.setBackground(Style.WORD_COLOR_WHITE);

                add(mainPanel);


            }
        }

        public class TableCustomerPanel extends JPanel {
            ModifyManager modifyManager;

            public TableCustomerPanel() {
                setLayout(new BorderLayout());
                setBackground(Style.WORD_COLOR_WHITE);
                tableAccManager = createTable(modelAccManager, accountColumnNames);
                // Thiết lập renderer cho cột ảnh
                tableAccManager.getColumnModel().getColumn(accountColumnNames.length - 1).setCellRenderer(new ImageInJTable.ImageRenderer());
                tableAccManager.setRowHeight(100);
                resizeColumnWidth(tableAccManager, 219);
                modelAccManager = (DefaultTableModel) tableAccManager.getModel();
                managerController= new ManagerController();
                managerInfors = managerController.getManagerInforDTO();
                upDataTable(managerInfors, modelAccManager, tableAccManager);



                scrollPaneAccManager = new JScrollPane(tableAccManager);
                tabbedPaneAccManager = createTabbedPane(scrollPaneAccManager, "Customer", Style.FONT_HEADER_ROW_TABLE);
                modifyManager = new ModifyManager();
                tabbedPaneAccManager.add("Modify Manager",modifyManager);

                add(tabbedPaneAccManager, BorderLayout.CENTER);

            }
        }
        class ModifyManager extends JPanel {
            ChangeInfo changeInfo;
            Avatar avatar;
            ModifyManager() {
                setLayout(new GridLayout(2,1));
                changeInfo = new ChangeInfo();
                avatar = new Avatar();
                add(changeInfo);
                add(avatar);
            }
            public static void addFocusListenerForTextField(JTextField textField) {
                textField.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        textField.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,4));
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        textField.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,1));
                    }
                });
            }


            class ChangeInfo extends JPanel {
                LeftPn rightPn;
                RightPn leftPn;
                ChangeInfo() {
                    setLayout(new GridLayout(1, 2));
                    rightPn = new LeftPn();
                    leftPn = new RightPn();
                    add(rightPn);
                    add(leftPn);
                }

                class LeftPn extends JPanel {
                    JTextField txtFullName,txtAddress, txtBirthday,txtPhoneNumber;
                    LeftPn() {
                        setLayout(new GridBagLayout());
                        setBackground(Color.WHITE);
                        setPreferredSize(new Dimension(400,150));
                        Border border = BorderFactory.createTitledBorder(
                                BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 3),
                                "Personal Information",
                                TitledBorder.LEFT,
                                TitledBorder.TOP,
                                Style.FONT_TITLE_PRODUCT_18,
                                Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE
                        );
                        setBorder(border);

                        GridBagConstraints gbc = new GridBagConstraints();
                        gbc.insets = new Insets(5, 5, 5, 5);  // Thiết lập khoảng cách giữa các thành phần

                        // Khởi tạo các thành phần giao diện
                        JLabel lblFullName = new JLabel("Full Name:");
                        txtFullName = createStyledTextField(Style.FONT_PLAIN_16,Color.BLACK,Style.MEDIUM_BLUE,new Dimension(285, 35));
                        addFocusListenerForTextField(txtFullName);


                        JLabel lblAddress = new JLabel("Address:");
                        txtAddress = createStyledTextField(Style.FONT_PLAIN_16,Color.BLACK,Style.MEDIUM_BLUE,new Dimension(285, 35));
                        addFocusListenerForTextField(txtAddress);

                        JLabel lblBirthday = new JLabel("Birthday:");
                        txtBirthday = createStyledTextField(Style.FONT_PLAIN_16,Color.BLACK,Style.MEDIUM_BLUE,new Dimension(250, 35));
                        addFocusListenerForTextField(txtBirthday);
                        txtBirthday.setEditable(false);
                        JButton btnCalendar = new JButton();
                        btnCalendar.setPreferredSize(new Dimension(35,35));
                        btnCalendar.setFocusable(false);
                        btnCalendar.setBackground(Color.WHITE);
                        btnCalendar.setIcon(new ImageIcon("src/main/java/Icon/calendarIcon.png"));

                        // Tạo JDialog chứa JCalendar
                        JDialog calendarDialog = new JDialog((Frame) null, "Select Date", true);
                        calendarDialog.setSize(400, 400);
                        calendarDialog.setLayout(new BorderLayout());
                        calendarDialog.setLocation(700, 200);
                        JCalendar calendar = new JCalendar();
                        calendar.setBackground(Color.WHITE);
                        calendar.setFont(Style.FONT_SIZE_MENU_BUTTON);

                        calendar.setMaxSelectableDate(new java.util.Date());


                        calendarDialog.add(calendar, BorderLayout.CENTER);

                        JButton btnSelect = new JButton("Select");
                        btnSelect.setBackground(Color.WHITE);
                        btnSelect.setFocusable(false);
                        btnSelect.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
                        calendarDialog.add(btnSelect, BorderLayout.SOUTH);

                        // Sự kiện khi nhấn nút chọn ngày
                        btnCalendar.addActionListener(e -> calendarDialog.setVisible(true));
                        btnSelect.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                Date selectedDate = calendar.getDate();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                txtBirthday.setText(dateFormat.format(selectedDate));
                                calendarDialog.setVisible(false);
                            }
                        });

                        JLabel lblPhoneNumber = new JLabel("Phone Number:");
                        txtPhoneNumber = createStyledTextField(Style.FONT_PLAIN_16,Color.BLACK,Style.MEDIUM_BLUE,new Dimension(285, 35));
                        addFocusListenerForTextField(txtPhoneNumber);

                        // Cài đặt GridBagConstraints cho các thành phần
                        gbc.gridx = 0;
                        gbc.gridy = 0;
                        gbc.anchor = GridBagConstraints.WEST;
                        add(lblFullName, gbc);

                        gbc.gridx = 1;
                        add(txtFullName, gbc);

                        gbc.gridx = 0;
                        gbc.gridy = 1;
                        add(lblAddress, gbc);

                        gbc.gridx = 1;
                        add(txtAddress, gbc);

                        gbc.gridx = 0;
                        gbc.gridy = 2;
                        add(lblBirthday, gbc);

                        // Sử dụng một JPanel để chứa cả TextField và Button
                        JPanel birthdayPanel = new JPanel(new BorderLayout());
                        birthdayPanel.setBackground(Color.WHITE);
                        birthdayPanel.add(txtBirthday, BorderLayout.CENTER);
                        birthdayPanel.add(btnCalendar, BorderLayout.EAST);

                        gbc.gridx = 1;
                        add(birthdayPanel, gbc);

                        gbc.gridx = 0;
                        gbc.gridy = 3;
                        add(lblPhoneNumber, gbc);

                        gbc.gridx = 1;
                        add(txtPhoneNumber, gbc);
                    }
                }
                class RightPn extends JPanel {
                    JTextField usernameField,emailField;
                    JPasswordField passwordField;
                    RightPn() {
                        setLayout(new GridBagLayout());
                        setBackground(Color.WHITE);
                        setPreferredSize(new Dimension(400,150));
                        Border border = BorderFactory.createTitledBorder(
                                BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 3), // Đường viền
                                "Account Information", // Tiêu đề
                                TitledBorder.LEFT, // Canh trái
                                TitledBorder.TOP, // Canh trên
                                Style.FONT_TITLE_PRODUCT_18, // Phông chữ và kiểu chữ
                                Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE // Màu chữ
                        );
                        setBorder(border);
                        GridBagConstraints gbc = new GridBagConstraints();
                        gbc.insets = new Insets(5,5,5,5);

                        gbc.fill = GridBagConstraints.HORIZONTAL;

                        // Cột 1 (JLabel)
                        gbc.gridx = 0;
                        gbc.gridy = 0;
                        add(new JLabel("User name:"), gbc);

                        gbc.gridy = 1;
                        add(new JLabel("Password:"), gbc);

                        gbc.gridy = 2;
                        add(new JLabel("Email:"), gbc);

                        // Cột 2 (JTextField, JPasswordField, JTextField)
                        gbc.gridx = 1;
                        gbc.gridy = 0;
                        usernameField = createStyledTextField(Style.FONT_PLAIN_16,Color.BLACK,Style.MEDIUM_BLUE,new Dimension(295, 35));
                        addFocusListenerForTextField(usernameField);
                        add(usernameField, gbc);

                        gbc.gridy = 1;
                        JPanel passwdPanel = new JPanel(new BorderLayout());
                        passwordField = createStyledJPasswordField(Style.FONT_PLAIN_16,Style.MEDIUM_BLUE,new Dimension(250, 35));
                        passwordField.addFocusListener(new FocusListener() {
                            @Override
                            public void focusGained(FocusEvent e) {
                                passwordField.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,4));
                            }
                            @Override
                            public void focusLost(FocusEvent e) {
                                passwordField.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,1));
                            }
                        });

                        JButton togglePasswordButton = createShowPasswdButton(passwordField);
                        togglePasswordButton.setPreferredSize(new Dimension(45,35));
                        passwdPanel.setBackground(Color.WHITE);
                        passwdPanel.add(passwordField, BorderLayout.CENTER);
                        passwdPanel.add(togglePasswordButton, BorderLayout.EAST);

                        add(passwdPanel, gbc);

                        gbc.gridy = 2;
                        emailField = createStyledTextField(Style.FONT_PLAIN_16,Color.BLACK,Style.MEDIUM_BLUE,new Dimension(295, 35));
                        addFocusListenerForTextField(emailField);
                        add(emailField, gbc);

                    }
                }
            }


            class Avatar extends JPanel {
                CustomButton importImage, undoBt;
                JLabel label;
                Avatar() {
                    setLayout(new BorderLayout());
                    setBackground(Color.WHITE);
                    label = new JLabel("Drop your image here",SwingConstants.CENTER);
                    label.setBackground(Color.WHITE);
                    Border dashedBorder = BorderFactory.createDashedBorder(Style.CONFIRM_BUTTON_COLOR_GREEN, 2,10, 20,true);
                    Border margin = BorderFactory.createEmptyBorder(5, 10, 5, 10);
                    Border compoundBorder = BorderFactory.createCompoundBorder(margin, dashedBorder);
                    label.setBorder(compoundBorder);
                    label.setPreferredSize(new Dimension(400,300));

                    JPanel uploadImagePn = new JPanel();
                    uploadImagePn.setBackground(Color.WHITE);
                    importImage = new CustomButton("Upload Image from your computer");
                    importImage.setDrawBorder(false);
                    importImage.setPreferredSize(new Dimension(300, 40));

                    undoBt = new CustomButton("Undo");
                    undoBt.setPreferredSize(new Dimension(100, 40));
                    undoBt.setDrawBorder(false);

                    uploadImagePn.add(undoBt);
                    uploadImagePn.add(importImage);

                    add(label, BorderLayout.CENTER);
                    add(uploadImagePn, BorderLayout.SOUTH);
                }
            }

        }

        private void reload(){
//            customers = customerController.getAll();
//            upDataTable(customers, modelCustomer, tableCustomer);
//            billTextDisplayPanal.setText("You should continue to find the customer Id!!!");
        }
        public int getIndexSelectedTab(){
            return tabbedPaneAccManager.getSelectedIndex();
        }

        private CategoryDataset createDataset() {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            // Dữ liệu mẫu: Thay thế bằng dữ liệu từ database
            Map<String, Integer> topCustomers = new HashMap<>();

            // Thêm dữ liệu vào dataset
            for (Map.Entry<String, Integer> entry : topCustomers.entrySet()) {
                dataset.addValue(entry.getValue(), "Số lượng mua", entry.getKey());
            }

            return dataset;
        }

        private void consertCustomerToMap(ArrayList<Customer> customers, Map<String, Integer> map) {
            for (Customer customer : customers) {
                map.put(customer.getFullName(), customer.getNumberOfPurchased());
            }
        }


        public static void upDataTable(ArrayList<ManagerInforDTO> managerInforDTOS, DefaultTableModel modelCustomerTable ,JTable tableCustomer) {
            Object[][] rowData = ManagerInforDTO.getDataOnTable(managerInforDTOS);
            ProductPanel.TablePanel.removeDataTable(modelCustomerTable);
            for (int i = 0; i < rowData.length; i++) {
                modelCustomerTable.addRow(rowData[i]);
            }
        }

    }

    class NotificationPanel extends JPanel {
        public NotificationPanel() {
        }

    }


    class ChangeInformationPanel extends JPanel {
        ChangeAvatarPanel changeAvatarPanel = new ChangeAvatarPanel();
        ChangeInfo changeInfo = new ChangeInfo();
        CustomButton updateBt, cancelBt, changeAvaBt;

        public ChangeInformationPanel() {
            setLayout(new BorderLayout());

            JPanel ChangePn = new JPanel(new GridLayout(1, 2));
            ChangePn.add(changeAvatarPanel);
            ChangePn.add(changeInfo);
            add(ChangePn, BorderLayout.CENTER);

            cancelBt = new CustomButton("Cancel");
            cancelBt.setGradientColors(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Color.GRAY);
            cancelBt.setBackgroundColor(Style.LIGHT_BlUE);
            cancelBt.setBorderRadius(20);
            cancelBt.setPreferredSize(new Dimension(200,50));
            cancelBt.setFont(Style.FONT_TITLE_PRODUCT);

            updateBt = new CustomButton("Update");
            updateBt.setGradientColors(new Color(58, 106, 227), Color.GREEN);
            updateBt.setBackgroundColor(Style.LIGHT_BlUE);
            updateBt.setBorderRadius(20);
            updateBt.setPreferredSize(new Dimension(200,50));
            updateBt.setFont(Style.FONT_TITLE_PRODUCT);
            updateBt.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showConfirmDialog(null,"Confirm Your Change?", "Confirm",JOptionPane.YES_NO_OPTION );
                }
            });


            JPanel updatePn = new JPanel(new FlowLayout(FlowLayout.CENTER));
            updatePn.add(cancelBt);
            updatePn.add(updateBt);
            add(updatePn, BorderLayout.SOUTH);


        }

        class ChangeAvatarPanel extends JPanel {

            ChangeAvatarPanel() {
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                setBorder(BorderFactory.createEmptyBorder(20, 50, 30, 50));
                CircularImage avatar = new CircularImage("src/main/java/Icon/fit_nlu_logo.jpg", 300, 300, false);
                avatar.setAlignmentX(Component.CENTER_ALIGNMENT);


                changeAvaBt = new CustomButton("Upload new image from Computer");
                changeAvaBt.setGradientColors(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Color.GREEN);
                changeAvaBt.setBackgroundColor(Style.LIGHT_BlUE);
                changeAvaBt.setBorderRadius(20);
                changeAvaBt.setPreferredSize(new Dimension(450, 40));
                changeAvaBt.setFont(Style.FONT_BUTTON_CUSTOMER);
                changeAvaBt.setHorizontalAlignment(SwingConstants.CENTER);
                changeAvaBt.setAlignmentX(Component.CENTER_ALIGNMENT);

                add(Box.createVerticalGlue());
                add(avatar);
                add(Box.createRigidArea(new Dimension(0, 50)));
                add(changeAvaBt);
                add(Box.createVerticalGlue());
            }

        }

        class ChangeInfo extends JPanel {
            JTextField emailField, fullNameField, addressField;
            JPasswordField oldPasswordField, newPasswordField, confirmPasswordField;
            JButton showOldPasswd, showNewPasswd, showRetypeNewPasswd;

            ChangeInfo() {
                setLayout(new GridBagLayout());

                // Tạo layout constraints
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(5, 5, 5, 5);
                gbc.fill = GridBagConstraints.HORIZONTAL;

                JLabel title = new JLabel("Customer Information", SwingConstants.CENTER);
                title.setFont(Style.FONT_TITLE_PRODUCT);
                title.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.gridwidth = 2;
                add(title, gbc);

                JLabel emailLabel = new JLabel("Email: ");
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.gridwidth = 2;
                add(emailLabel, gbc);

                emailField = createStyledTextField(Style.FONT_TEXT_CUSTOMER,Color.BLACK,Style.MEDIUM_BLUE,new Dimension(350, 40));
                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.gridwidth = 1;
                add(emailField, gbc);

                // Thêm Label 2, TextField 2, và Button 2
                JLabel nameLabel = new JLabel("Name: ");
                gbc.gridx = 0;
                gbc.gridy = 3;
                gbc.gridwidth = 2;
                add(nameLabel, gbc);

                fullNameField = createStyledTextField(Style.FONT_TEXT_CUSTOMER,Color.BLACK,Style.MEDIUM_BLUE,new Dimension(350, 40));
                gbc.gridx = 0;
                gbc.gridy = 4;
                gbc.gridwidth = 1;
                add(fullNameField, gbc);

                JButton button2 = createEditFieldButton(fullNameField);
                gbc.gridx = 1;
                gbc.gridy = 4;
                add(button2, gbc);

                // Thêm Label 3, TextField 3, và Button 3
                JLabel addressLabel = new JLabel("Address: ");
                gbc.gridx = 0;
                gbc.gridy = 5;
                gbc.gridwidth = 2;
                add(addressLabel, gbc);

                addressField = createStyledTextField(Style.FONT_TEXT_CUSTOMER,Color.BLACK,Style.MEDIUM_BLUE,new Dimension(350, 40));
                gbc.gridx = 0;
                gbc.gridy = 6;
                gbc.gridwidth = 1;
                add(addressField, gbc);

                JButton button3 = createEditFieldButton(addressField);
                gbc.gridx = 1;
                gbc.gridy = 6;
                add(button3, gbc);

                JLabel oldPasswdLabel = new JLabel("Old Password: ");
                gbc.gridx = 0;
                gbc.gridy = 7;
                gbc.gridwidth = 2;
                add(oldPasswdLabel, gbc);

                oldPasswordField = createStyledJPasswordField(Style.FONT_TEXT_CUSTOMER,Style.MEDIUM_BLUE,new Dimension(350, 40));
                gbc.gridx = 0;
                gbc.gridy = 8;
                gbc.gridwidth = 1;
                add(oldPasswordField, gbc);

                showOldPasswd = createShowPasswdButton(oldPasswordField);
                gbc.gridx = 1;
                gbc.gridy = 8;
                gbc.gridwidth = 1;
                add(showOldPasswd, gbc);

                JLabel newPasswdLabel = new JLabel("New Password: ");
                gbc.gridx = 0;
                gbc.gridy = 9;
                gbc.gridwidth = 2;
                add(newPasswdLabel, gbc);

                newPasswordField = createStyledJPasswordField(Style.FONT_TEXT_CUSTOMER,Style.MEDIUM_BLUE,new Dimension(350, 40));
                gbc.gridx = 0;
                gbc.gridy = 10;
                gbc.gridwidth = 1;
                add(newPasswordField, gbc);

                showNewPasswd = createShowPasswdButton(newPasswordField);
                gbc.gridx = 1;
                gbc.gridy = 10;
                gbc.gridwidth = 1;
                add(showNewPasswd, gbc);

                JLabel confirmPasswdLabel = new JLabel("Confirm Password: ");
                gbc.gridx = 0;
                gbc.gridy = 11;
                gbc.gridwidth = 2;
                add(confirmPasswdLabel, gbc);

                confirmPasswordField = createStyledJPasswordField(Style.FONT_TEXT_CUSTOMER,Style.MEDIUM_BLUE,new Dimension(350, 40));
                gbc.gridx = 0;
                gbc.gridy = 12;
                gbc.gridwidth = 1;
                add(confirmPasswordField, gbc);

                showRetypeNewPasswd = createShowPasswdButton(confirmPasswordField);
                gbc.gridx = 1;
                gbc.gridy = 12;
                gbc.gridwidth = 1;
                add(showRetypeNewPasswd, gbc);
            }


            private JButton createEditFieldButton(JTextField textField) {
                JButton editBt = new JButton();
                editBt.setIcon(new ImageIcon("src/main/java/Icon/penIcon1.png"));
                editBt.setPreferredSize(new Dimension(45, 40));

                Color hoverBackground = new Color(130, 180, 230); // Màu sáng hơn khi hover

                editBt.setBackground(Style.LIGHT_BlUE);
                editBt.setFocusPainted(false);
                editBt.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 2));
                editBt.setContentAreaFilled(true); // Đảm bảo nền được vẽ

                // Thêm hiệu ứng hover
                editBt.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent evt) {
                        editBt.setBackground(hoverBackground);
                    }

                    public void mouseExited(MouseEvent evt) {
                        editBt.setBackground(Style.LIGHT_BlUE);
                    }
                });

                // Thêm hiệu ứng click để làm sáng lên
                editBt.addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent evt) {
                        editBt.setBackground(hoverBackground.darker()); // Tối hơn khi nhấn
                    }

                    public void mouseReleased(MouseEvent evt) {
                        editBt.setBackground(hoverBackground); // Quay lại màu hover sau khi thả chuột
                    }
                });

                editBt.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        textField.setEditable(true);
                    }
                });

                return editBt;
            }

            // nút ẩn hiện cho mật khẩu
            private JButton createShowPasswdButton(JPasswordField passwordField) {
                JButton toggleButton = new JButton();
                toggleButton.setBackground(Style.LIGHT_BlUE);
                toggleButton.setFocusPainted(false);
                toggleButton.setFocusable(false);
                toggleButton.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 2));
                ImageIcon showPasswd = new ImageIcon("src/main/java/Icon/showPasswd_Icon.png");
                ImageIcon hidePasswd = new ImageIcon("src/main/java/Icon/hidePasswd_Icon.png");

                toggleButton.setIcon(showPasswd);
                toggleButton.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent evt) {
                        toggleButton.setBackground(new Color(130, 180, 230));
                    }

                    public void mouseExited(MouseEvent evt) {
                        toggleButton.setBackground(Style.LIGHT_BlUE);
                    }
                });

                toggleButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (passwordField.getEchoChar() != '\u0000') {
                            passwordField.setEchoChar('\u0000');
                            toggleButton.setIcon(hidePasswd);
                        } else {
                            // Ẩn mật khẩu
                            passwordField.setEchoChar('*');
                            toggleButton.setIcon(showPasswd);
                        }
                    }
                });
                return toggleButton;
            }

        }
    }

    private static void formatLabel(JLabel that, Font font, Color color) {
        that.setFont(font);
        that.setForeground(color);
    }

    public static void formatTextField(JTextField that, Font font, Color color, Dimension size) {
        that.setFont(font);
        that.setForeground(color);
        that.setPreferredSize(size);
    }
    private JTextField createStyledTextField(Font font, Color textColor, Color borderColor, Dimension size) {
        JTextField field = new JTextField();
        field.setFont(font);
        field.setForeground(textColor);
        field.setPreferredSize(size);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        field.setBackground(Color.WHITE);
        return field;
    }

    private JPasswordField createStyledJPasswordField(Font font, Color borderColor, Dimension size) {
        JPasswordField passwdField = new JPasswordField();
        passwdField.setEchoChar('*');
        passwdField.setFont(font);
        passwdField.setPreferredSize(size);
        passwdField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        return passwdField;
    }


    private static void setStyleButton(JButton that, Font font, Color textColor, Color backgroundColor, int textPosition, Dimension size) {
        that.setFont(font);
        that.setForeground(textColor);
        that.setBackground(backgroundColor);
        that.setHorizontalAlignment(textPosition);
        that.setBorderPainted(false);
        that.setFocusable(false);
        that.setPreferredSize(size);
    }

    private void setIconSmallButton(String url, JButton that) {
        ImageIcon iconButton = new ImageIcon(url);
        Image image = iconButton.getImage(); // Lấy Image từ ImageIcon
        Dimension buttonSize = that.getPreferredSize();
        Image resizedImage = image.getScaledInstance(buttonSize.height - 10, buttonSize.height - 10, java.awt.Image.SCALE_SMOOTH); // Resize
        that.setIcon(new ImageIcon(resizedImage));
    }

    // Phương thức tạo nút Clear All
    public JButton createClearAllButton(JPanel panel) {
        JButton clearAllButton = new JButton("Clear All");
        clearAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearTextFields(panel);
            }
        });

        return clearAllButton;
    }

    private static  JButton createShowPasswdButton(JPasswordField passwordField) {
        JButton toggleButton = new JButton();
        toggleButton.setBackground(Style.LIGHT_BlUE);
        toggleButton.setFocusPainted(false);
        toggleButton.setFocusable(false);
        toggleButton.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 2));
        ImageIcon showPasswd = new ImageIcon("src/main/java/Icon/showPasswd_Icon.png");
        ImageIcon hidePasswd = new ImageIcon("src/main/java/Icon/hidePasswd_Icon.png");

        toggleButton.setIcon(showPasswd);
        toggleButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                toggleButton.setBackground(new Color(130, 180, 230));
            }

            public void mouseExited(MouseEvent evt) {
                toggleButton.setBackground(Style.LIGHT_BlUE);
            }
        });

        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (passwordField.getEchoChar() != '\u0000') {
                    passwordField.setEchoChar('\u0000');
                    toggleButton.setIcon(hidePasswd);
                } else {
                    // Ẩn mật khẩu
                    passwordField.setEchoChar('*');
                    toggleButton.setIcon(showPasswd);
                }
            }
        });
        return toggleButton;
    }

    // Phương thức xóa các JTextField trong panel
    private void clearTextFields(JPanel panel) {
        // Lặp qua tất cả các thành phần trong panel để kiếm textfield cần xóa
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JTextField) {
                ((JTextField) comp).setText("");
            }
        }
    }

    // phương thức tạo ra một bảng cho việc nhập sản phẩm
    public JTable createTable(DefaultTableModel model, String[] columnNames) {
        // Thiết lập model cho bảng
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Thiết lập bảng
        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setRowHeight(40);
        table.setFont(Style.FONT_TEXT_TABLE);
        resizeColumnWidth(table, 200);

        // Thiết lập table header
        JTableHeader header = table.getTableHeader();
        header.setBackground(Style.MENU_BUTTON_COLOR_GREEN);
        header.setForeground(Color.BLACK);
        header.setReorderingAllowed(false);
        header.setFont(Style.FONT_HEADER_ROW_TABLE);
        header.setResizingAllowed(true);

        return table;
    }
    // thay đổi kích thước của cột trong bảng
    public void resizeColumnWidth(JTable table, int width) {
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(width);
            table.getColumnModel().getColumn(0).setPreferredWidth(100);
            table.getColumnModel().getColumn(1).setPreferredWidth(120);
        }
    }
    // create a TabbedPane
    public JTabbedPane createTabbedPane(JScrollPane scrollPane, String title, Font font) {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(font);
        tabbedPane.addTab(title, scrollPane);
        tabbedPane.setFocusable(false);
        return tabbedPane;
    }
}

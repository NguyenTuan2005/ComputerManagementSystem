package view;

import Config.ButtonConfig;
import Config.ProductConfig;
import Model.Product;
import Model.Supplier;
import controller.ProductController;
import view.OverrideComponent.ProductInputForm;

import view.OverrideComponent.ProductModifyForm;


import controller.SupplierController;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.util.ArrayList;


public class ManagerMainPanel extends JPanel {
    OldLoginFrame loginFrame;
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
            "Operating System", "CPU", "Storage", "RAM", "Origin"};
    static final String[] columnNamesSUPPLIER = {"Supplier ID:", "Supplier Name:", "Address", "Phone number:", "Email:", "Contract Start Date:"};
    static final String[] columnNamesCUSTOMER = {"Customer ID:", "Customer Name:", "Phone number:", "Email", "Address:", "Order Date:"};

    //main constructor
//    public ManagerMainPanel(LoginFrame loginFrame) {
    public ManagerMainPanel() {
        //tao giao dien
        this.loginFrame = loginFrame;
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
        JTableHeader tableProductHeader;
        JScrollPane scrollPaneProductTable;
        JTabbedPane tabbedPaneProductTable;

        JPanel searchPanel, applicationPanel, mainPanel;
        private static ProductController productController = new ProductController();
        private static ArrayList<Product> productsAll = reloadData(productController);

        //       String [] sortsStyle = {"SORT BY PRICE", "SORT BY RAM", "SORT BY MEMORY", "SORT BY NAME"};
        // reload method
        private static ArrayList<Product> reloadData(ProductController productController) {
            return productController.getAll();
        }


        public ProductPanel() {
            setLayout(new BorderLayout());
            toolPanel.setBorder(BorderFactory.createTitledBorder("Tool"));
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
                modifyBt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int selectedRow = tableProduct.getSelectedRow();
                        int columnIndex = 1;
                        if (selectedRow != -1) {
                            Object value = tableProduct.getValueAt(selectedRow, columnIndex);

                            int productId = Integer.parseInt(value.toString());
                            System.out.println("delete row : " + productId);
                            SwingUtilities.invokeLater(() -> {
                                new ProductModifyForm(productsAll.get(selectedRow)).setVisible(true);
                            });

                        }
                    }
                });
                deleteBt = new JButton("Delete");
                ButtonConfig.addButtonHoverEffect(deleteBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                setStyleButton(deleteBt, Style.FONT_SIZE_MIN_PRODUCT, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(80, 80));
                ButtonConfig.setIconBigButton("src/main/java/Icon/delete-icon-removebg-preview.png", deleteBt);
                deleteBt.setHorizontalTextPosition(SwingConstants.CENTER); // Chữ ở giữa theo chiều ngang
                deleteBt.setVerticalTextPosition(SwingConstants.BOTTOM);
                // api for delete row
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


                //demo
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
                            productsAll= reloadData(productController);
                            if (productsAll.isEmpty())
                                JOptionPane.showMessageDialog(null, "Not found data", "Notify", JOptionPane.WARNING_MESSAGE);
                            ProductConfig.exportToExcel(productsAll,fileName);
                            JOptionPane.showMessageDialog(null, "Created file :"+fileName, "Notify", JOptionPane.WARNING_MESSAGE);
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
                            String url = selectedFile.getAbsolutePath();
//                            System.out.println("Đã chọn file: " + selectedFile.getAbsolutePath());
                            ArrayList<Product> products = (ArrayList<Product>) ProductConfig.readExcelFile(url);
//                            System.out.println("print " + products);
                            productController.saves(products);
                            JOptionPane.showMessageDialog(null, "Read file "+ url, "Notify", JOptionPane.WARNING_MESSAGE);

                        } else {
                            System.out.println("Chọn file đã bị hủy.");
                        }
                    }
                });


                findText = new JTextField();
                formatTextField(findText, new Font("Arial", 0, 24), Style.WORD_COLOR_BLACK, new Dimension(250, 45));
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
//                applicationPanel.add(ButtonConfig.createVerticalSeparator());
                applicationPanel.add(deleteBt);
                applicationPanel.add(ButtonConfig.createVerticalSeparator());
                applicationPanel.add(modifyBt);
//                applicationPanel.add(ButtonConfig.createVerticalSeparator());
//                sortDropListDown = new JComboBox<>(sortsStyle);


//                applicationPanel.add(sortBt);

                applicationPanel.add(sortBt);
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
//                        System.out.println(findText.getText());
                        if (findText.getText().trim().isEmpty())
                            return;
                        ArrayList<Product> products = productController.find(findText.getText().trim());

                        if (products.isEmpty()) {
                            JOptionPane.showMessageDialog(tablePanel, "có caái ni");
                            return;
                        }
                        TablePanel.upDataTable(products, modelProductTable);

                    }
                });

                // reload application
                reloadBt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        productsAll = reloadData(productController);
                        TablePanel.upDataTable(productsAll, modelProductTable);
                    }
                });
            }


        }

        public class TablePanel extends JPanel {
            public TablePanel() {
                setLayout(new BorderLayout());
                setBackground(Style.WORD_COLOR_WHITE);
                productController = new ProductController();
                // data test thử:))

//                String [] product =(String) products.toArray();

                tableProduct = createTable(modelProductTable, tableProductHeader, columnNamesPRODUCT);
                tableProduct.setRowHeight(30);
                resizeColumnWidth(tableProduct, 150);

                modelProductTable = (DefaultTableModel) tableProduct.getModel();

                ArrayList<Product> productsDemo = productController.getAll();

                upDataTable(productsDemo, modelProductTable);


                scrollPaneProductTable = new JScrollPane(tableProduct);
                tabbedPaneProductTable = createTabbedPane(scrollPaneProductTable, "Product for Sales", Style.FONT_HEADER_ROW_TABLE);

                add(tabbedPaneProductTable, BorderLayout.CENTER);
            }

            public static void removeDataTable(DefaultTableModel modelProductTable) {
                modelProductTable.setRowCount(0);
            }

            public static void upDataTable(ArrayList<Product> products, DefaultTableModel modelProductTable) {
                String[][] rowData = Product.getDateOnTable(products);
                TablePanel.removeDataTable(modelProductTable);
                for (int i = 0; i < rowData.length; i++) {
                    modelProductTable.addRow(rowData[i]);
//                    System.out.println(products.get(i));
                }
            }
        }
    }

    // Hoang's code // tuan
    class SupplierPanel extends JPanel {
        JButton addBt, modifyBt, deleteBt, sortBt, exportExcelBt, importExcelBt, searchBt;
        JTextField findText;
        private final String[] columnNamesSUPPLIER = {"Serial Number", "Supplier ID:", "Supplier Name:", "Address", "Phone number:", "Email:", "Contract Start Date:"};
        private JTable tableSupplier;
        private DefaultTableModel modelSupplier;
        private JTableHeader headerSupplier;
        private JScrollPane scrollPaneSupplier;
        private JTabbedPane tabbedPaneSupplier;

        ToolPanel toolPanel = new ToolPanel();
        TablePanel tablePanel = new TablePanel();

        private static SupplierController supplierController = new SupplierController();
        private static ArrayList<Supplier> suppliers = supplierController.reloadData();

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
                addBt = new JButton("Add");
                ButtonConfig.setStyleButton(addBt, Style.FONT_BUTTON_CUSTOMER, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(80, 80));
                ButtonConfig.setIconBigButton("src/main/java/Icon/database-add-icon.png", addBt);
                addBt.setHorizontalTextPosition(SwingConstants.CENTER);
                addBt.setVerticalTextPosition(SwingConstants.BOTTOM);

                // Modify Button
                modifyBt = new JButton("Modify");
                ButtonConfig.setStyleButton(modifyBt, Style.FONT_BUTTON_CUSTOMER, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(80, 80));
                ButtonConfig.setIconBigButton("src/main/java/Icon/database-add-icon.png", modifyBt);
                modifyBt.setHorizontalTextPosition(SwingConstants.CENTER);
                modifyBt.setVerticalTextPosition(SwingConstants.BOTTOM);

                // Delete Button
                deleteBt = new JButton("Delete");
                ButtonConfig.setStyleButton(deleteBt, Style.FONT_BUTTON_CUSTOMER, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(80, 80));
                ButtonConfig.setIconBigButton("src/main/java/Icon/delete-icon-removebg-preview.png", deleteBt);
                deleteBt.setHorizontalTextPosition(SwingConstants.CENTER);
                deleteBt.setVerticalTextPosition(SwingConstants.BOTTOM);

                // Sort Button
                sortBt = new JButton("Sort Asc");
                ButtonConfig.setStyleButton(sortBt, Style.FONT_BUTTON_CUSTOMER, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(80, 80));
                ButtonConfig.setIconBigButton("src/main/java/Icon/request-quote.238x256.png", sortBt);
                sortBt.setHorizontalTextPosition(SwingConstants.CENTER);
                sortBt.setVerticalTextPosition(SwingConstants.BOTTOM);

                // Export Excel Button
                exportExcelBt = new JButton("Export Excel");
                ButtonConfig.setStyleButton(exportExcelBt, Style.FONT_BUTTON_CUSTOMER, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(80, 80));
                ButtonConfig.setIconBigButton("src/main/java/Icon/icons8-file-excel-32.png", exportExcelBt);
                exportExcelBt.setHorizontalTextPosition(SwingConstants.CENTER);
                exportExcelBt.setVerticalTextPosition(SwingConstants.BOTTOM);

                // Import Excel Button
                importExcelBt = new JButton("Import Excel");
                ButtonConfig.setStyleButton(importExcelBt, Style.FONT_BUTTON_CUSTOMER, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(80, 80));
                ButtonConfig.setIconBigButton("src/main/java/Icon/icons8-export-excel-50.png", importExcelBt);
                importExcelBt.setHorizontalTextPosition(SwingConstants.CENTER);
                importExcelBt.setVerticalTextPosition(SwingConstants.BOTTOM);

                // Search Text Field
                findText = new JTextField();
                formatTextField(findText, new Font("Arial", 0, 24), Style.WORD_COLOR_BLACK, new Dimension(250, 45));
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
                        }
                    }
                });

                // Search Button
                searchBt = new JButton();
                ButtonConfig.setStyleButton(searchBt, Style.FONT_SIZE, Color.BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(40, 45));
                ButtonConfig.setIconSmallButton("src/main/java/Icon/106236_search_icon.png", searchBt);

                // Create panels
                JPanel searchPanel = new JPanel(new FlowLayout());
                searchPanel.add(findText);
                searchPanel.add(searchBt);
                searchPanel.setBackground(Style.WORD_COLOR_WHITE);

                JPanel applicationPanel = new JPanel(new FlowLayout());
                applicationPanel.add(addBt);
                applicationPanel.add(deleteBt);
                applicationPanel.add(ButtonConfig.createVerticalSeparator());
                applicationPanel.add(modifyBt);
                applicationPanel.add(sortBt);
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
        }

        public class TablePanel extends JPanel {
            public TablePanel() {
                setLayout(new BorderLayout());
                setBackground(Style.WORD_COLOR_WHITE);

                tableSupplier = createTable(modelSupplier, headerSupplier, columnNamesSUPPLIER);
                tableSupplier.setRowHeight(40);
                resizeColumnWidth(tableSupplier, 200);
                scrollPaneSupplier = new JScrollPane(tableSupplier);
                modelSupplier = (DefaultTableModel) tableSupplier.getModel();

                upDataTable(suppliers, modelSupplier);

                tabbedPaneSupplier = createTabbedPane(scrollPaneSupplier, "Supplier List", Style.FONT_HEADER_ROW_TABLE);
                add(tabbedPaneSupplier, BorderLayout.CENTER);
            }

            private void upDataTable(ArrayList<Supplier> suppliers, DefaultTableModel modelSupplier) {
                String[][] rowData = Supplier.getData(suppliers);
                for (String[] strings : rowData) {
                    modelSupplier.addRow(strings);
                }
            }
        }
    }


    class CustomerPanel extends JPanel {
        JButton addBt, deleteBt, editBt, importExcelBt, exportExcelBt, sortAscBt, sortDescBt, searchBt;
        JTextField searchTextField;

        final String[] customerColumnNames = {"Serial Number", "Customer ID:", "Customer Name:", "Phone number:", "Email", "Address:", "Order Date:"};

        private JTable tableCustomer;
        private DefaultTableModel modelCustomer;
        private JTableHeader headerCustomer;
        private JScrollPane scrollPaneCustomer;
        private JTabbedPane tabbedPaneCustomer;

        public CustomerPanel() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.gridy = 0;  // hàng đầu tiên
            gbc.weightx = 1.0;
            //row 1; 6 tool button
            addBt = new JButton("Add New Customer");
            addBt.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                }
            });
            gbc.gridx = 0;
            add(addBt, gbc);

            deleteBt = new JButton("Delete Customer");
            deleteBt.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                }
            });
            gbc.gridx = 1;
            add(deleteBt, gbc);

            editBt = new JButton("Edit Info");
            editBt.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                }
            });
            gbc.gridx = 2;
            add(editBt, gbc);

            importExcelBt = new JButton("Import Excel");
            importExcelBt.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                }
            });
            gbc.gridx = 3;
            add(importExcelBt, gbc);

            exportExcelBt = new JButton("Export Excel");
            exportExcelBt.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                }
            });
            gbc.gridx = 4;
            add(exportExcelBt, gbc);

            sortAscBt = new JButton("Sort Ascending");
            sortAscBt.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                }
            });
            gbc.gridx = 5;
            add(sortAscBt, gbc);

            sortDescBt = new JButton("Sort descending");
            sortDescBt.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                }
            });
            gbc.gridx = 6;
            add(sortDescBt, gbc);

            // row 2: textfield and search button
            gbc.gridx = 2;
            gbc.gridy = 1;
            gbc.gridwidth = 2;  // TextField chiếm 3 cột (cột 2, 3, 4)
            gbc.weightx = 1.0;  // giúp TextField giãn khi khung phóng to
            gbc.anchor = GridBagConstraints.CENTER;
            searchTextField = new JTextField("Search");
            searchTextField.setPreferredSize(new Dimension(250, 40));
            searchTextField.setFont(Style.FONT_TEXT_CUSTOMER);
            searchTextField.setForeground(Color.GRAY);
            // Thêm FocusListener để kiểm soát khi người dùng nhấn và rời khỏi JTextField
            searchTextField.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    // Khi người dùng nhấn vào JTextField, nếu vẫn là chữ "Search", nó sẽ biến mất
                    if (searchTextField.getText().equals("Search")) {
                        searchTextField.setText("");
                        searchTextField.setForeground(Color.BLACK);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    // Khi người dùng rời khỏi JTextField mà chưa nhập gì, sẽ hiển thị lại chữ "Search"
                    if (searchTextField.getText().isEmpty()) {
                        searchTextField.setForeground(Color.GRAY);
                        searchTextField.setText("Search");
                    }
                }
            });
            add(searchTextField, gbc);

            searchBt = new JButton();
            setStyleButton(searchBt, Style.FONT_TEXT_CUSTOMER, Style.WORD_COLOR_WHITE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, SwingConstants.CENTER, new Dimension(68, 38));
            setIconSmallButton("src/main/java/Icon/search_Icon.png", searchBt);
            searchBt.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                }
            });
            gbc.gridx = 4;  // cột 5 (do GridBagLayout tính từ 0)
            gbc.gridwidth = 1;  // chỉ chiếm 1 cột
            add(searchBt, gbc);
            // add table
            tableCustomer = createTable(modelCustomer, headerCustomer, customerColumnNames);
            tableCustomer.setRowHeight(40);
            resizeColumnWidth(tableCustomer, 200);
            scrollPaneCustomer = new JScrollPane(tableCustomer);
            modelCustomer = (DefaultTableModel) tableCustomer.getModel();
//            modelCustomer.addRow(rowData);


            tabbedPaneCustomer = createTabbedPane(scrollPaneCustomer, "Customer List", Style.FONT_HEADER_ROW_TABLE);
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 7;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 1.0;  // bảng giãn theo chiều ngang
            gbc.weighty = 1.0;
            add(tabbedPaneCustomer, gbc);
        }
    }

    // Hoang's Code
    class InventoryPanel extends JPanel {
        InventoryControlPanel inventoryControlPanel;
        ImportPanel importPanel;
        ExportPanel exportPanel;
        CardLayout cardLayoutInventory;

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

        // panel chứa các chức năng tương tác của inventory
        class InventoryControlPanel extends JPanel {
            JButton importProduct, exportProduct, addToSaleListBt, exportExcelBt, modifyBt, deleteBt, searchBt;
            JTextField searchTextField;

            private JTable tableInventory, tableImport, tableExport;
            private DefaultTableModel modelInventory, modelImport, modelExport;
            private JTableHeader headerInventory, headerImport, headerExport;
            private JScrollPane scrollPaneInventory, scrollPaneImport, scrollPaneExport;
            private JTabbedPane tabbedPaneMain;

            InventoryControlPanel() {
                setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.insets = new Insets(5, 5, 5, 5);
                gbc.gridy = 0;  // hàng đầu tiên
                gbc.weightx = 1.0;
                //row 1; 6 tool button
                addToSaleListBt = new JButton("Add To Sale");
                addToSaleListBt.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                    }
                });
                gbc.gridx = 0;
                add(addToSaleListBt, gbc);

                importProduct = new JButton("Import Product");
                importProduct.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        showPanelInInventory(IMPORT_CONSTRAINT);
                    }
                });
                gbc.gridx = 1;
                add(importProduct, gbc);

                exportProduct = new JButton("Export Product");
                exportProduct.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        showPanelInInventory(EXPORT_CONSTRAINT);
                    }
                });
                gbc.gridx = 2;
                add(exportProduct, gbc);

                exportExcelBt = new JButton("Export Excel");
                exportExcelBt.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                    }
                });
                gbc.gridx = 3;
                add(exportExcelBt, gbc);

                modifyBt = new JButton("Modify");
                modifyBt.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                    }
                });
                gbc.gridx = 4;
                add(modifyBt, gbc);
                deleteBt = new JButton("Delete");
                deleteBt.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                    }
                });
                gbc.gridx = 5;
                add(deleteBt, gbc);
                // row 2: textfield and search button
                gbc.gridx = 2;
                gbc.gridy = 1;
                gbc.gridwidth = 2;  // TextField chiếm 3 cột (cột 2, 3, 4)
                gbc.weightx = 1.0;  // giúp TextField giãn khi khung phóng to
                gbc.anchor = GridBagConstraints.CENTER;
                searchTextField = new JTextField("Search");
                searchTextField.setPreferredSize(new Dimension(250, 40));
                searchTextField.setFont(Style.FONT_TEXT_CUSTOMER);
                searchTextField.setForeground(Color.GRAY);
                // Thêm FocusListener để kiểm soát khi người dùng nhấn và rời khỏi JTextField
                searchTextField.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        // Khi người dùng nhấn vào JTextField, nếu vẫn là chữ "Search", nó sẽ biến mất
                        if (searchTextField.getText().equals("Search")) {
                            searchTextField.setText("");
                            searchTextField.setForeground(Color.BLACK);
                        }
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        // Khi người dùng rời khỏi JTextField mà chưa nhập gì, sẽ hiển thị lại chữ "Search"
                        if (searchTextField.getText().isEmpty()) {
                            searchTextField.setForeground(Color.GRAY);
                            searchTextField.setText("Search");
                        }
                    }
                });
                add(searchTextField, gbc);

                searchBt = new JButton();
                setStyleButton(searchBt, Style.FONT_TEXT_CUSTOMER, Style.WORD_COLOR_WHITE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, SwingConstants.CENTER, new Dimension(68, 38));
                setIconSmallButton("src/main/java/Icon/search_Icon.png", searchBt);
                searchBt.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                    }
                });
                gbc.gridx = 4;  // cột 5 (do GridBagLayout tính từ 0)
                gbc.gridwidth = 1;  // chỉ chiếm 1 cột
                add(searchBt, gbc);
                //row 3: table
                String[] rowData = {"1", "LP20", "Laptop ASUS TUF Gaming FX516PE", String.valueOf(650), String.valueOf(23990000L), "Laptop", "China", "Window", "Intel Core i5 12450H", String.valueOf(512), "8 GB", "Vietnam"};

                tableInventory = createTable(modelInventory, headerInventory, columnNamesPRODUCT);
                tableInventory.setRowHeight(40);
                resizeColumnWidth(tableInventory, 200);
                scrollPaneInventory = new JScrollPane(tableInventory);
                modelInventory = (DefaultTableModel) tableInventory.getModel();
                modelInventory.addRow(rowData);

                tableImport = createTable(modelImport, headerImport, columnNamesPRODUCT);
                tableImport.setRowHeight(40);
                resizeColumnWidth(tableImport, 200);
                scrollPaneImport = new JScrollPane(tableImport);
                modelImport = (DefaultTableModel) tableImport.getModel();

                tableExport = createTable(modelExport, headerExport, columnNamesPRODUCT);
                tableExport.setRowHeight(40);
                resizeColumnWidth(tableExport, 200);
                scrollPaneExport = new JScrollPane(tableExport);
                modelExport = (DefaultTableModel) tableExport.getModel();

                tabbedPaneMain = createTabbedPane(scrollPaneInventory, "Inventory", Style.FONT_HEADER_ROW_TABLE);
                tabbedPaneMain.addTab("Product Received List ", scrollPaneImport);
                tabbedPaneMain.addTab("Product Dispatch List", scrollPaneExport);
                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.gridwidth = 6;
                gbc.fill = GridBagConstraints.BOTH;
                gbc.weightx = 1.0;  // bảng giãn theo chiều ngang
                gbc.weighty = 1.0;
                add(tabbedPaneMain, gbc);
            }
        }

        class ImportPanel extends JPanel {
            LeftPn leftPn;
            RightPn rightPn;
            private JTextField SupplierIDTF, nameSupplierTF, addressSupplierTF, phoneSupplierTF, emailSupplierTF, cooperationDayTF,
                    productIDTF, productNameTF, amountTF, priceTF, typeTF, brandTF, operatingSystemTF, cpuTF, memoryTF, ramTF, madeInTF;

            private JTextField[] supplierTFArray = {SupplierIDTF, nameSupplierTF, addressSupplierTF, phoneSupplierTF, emailSupplierTF, cooperationDayTF};
            private final JTextField[] productTFArray = {productIDTF, productNameTF, amountTF, priceTF, typeTF, brandTF, operatingSystemTF, cpuTF, memoryTF, ramTF, madeInTF};

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
                    JTableHeader tableHeaderGetDataSupplier;
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
                        tableGetDataSupplier = createTable(modelGetDataSupplier, tableHeaderGetDataSupplier, columnNamesSUPPLIER);
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

                        tableImportProductList = createTable(modelImportProductList, tableHeaderImportProductList, columnNamesPRODUCT);
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
                    JTableHeader tableHeaderAddProductFromList;
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
                        tableAddProductFromList = createTable(modelAddProductFromList, tableHeaderAddProductFromList, columnNamesPRODUCT);
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
                    priceTF, typeTF, brandTF, operatingSystemTF, cpuTF, memoryTF, ramTF, madeInTF;
            private final JTextField[] customerTFArray = {customerIDTF, customerNameTF, customerPhoneNumberTF, customerEmailTF, customerAddressTF, orderDateTF};
            private final JTextField[] productTFArray = {productIDTF, productNameTF, amountTF, priceTF, typeTF, brandTF, operatingSystemTF, cpuTF, memoryTF, ramTF, madeInTF};

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
                    JTableHeader tableHeaderGetDataSupplier;
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
                        tableGetDataSupplier = createTable(modelGetDataSupplier, tableHeaderGetDataSupplier, columnNamesSUPPLIER);
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

                        tableImportProductList = createTable(modelImportProductList, tableHeaderImportProductList, columnNamesPRODUCT);
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
                    JTableHeader tableHeaderAddProductFromList;
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
                        tableAddProductFromList = createTable(modelAddProductFromList, tableHeaderAddProductFromList, columnNamesPRODUCT);
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
        JButton addBt, editBt, deleteBt, sortBt, exportExcelBt, importExcelBt, searchBt;
        JTextField searchTextField;
        JComboBox<String> filterCombo;
        private final String[] accountColumnNames = {"Serial Number", "Role", "Account Name", "Phone Number", "Email", "Account creation date"};

        private JTable tableAccount;
        private DefaultTableModel modelAccount;
        private JTableHeader headerAccount;
        private JScrollPane scrollPaneAccount;
        private JTabbedPane tabbedPaneAccount;

        public AccManagementPanel() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.gridy = 0;  // hàng đầu tiên
            gbc.weightx = 1.0;
            //row 1; 6 tool button
            addBt = new JButton("Add New Account");
            addBt.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                }
            });
            gbc.gridx = 0;
            add(addBt, gbc);

            deleteBt = new JButton("Delete Customer");
            deleteBt.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                }
            });
            gbc.gridx = 1;
            add(deleteBt, gbc);

            editBt = new JButton("Edit Info");
            editBt.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                }
            });
            gbc.gridx = 2;
            add(editBt, gbc);

            importExcelBt = new JButton("Import Excel");
            importExcelBt.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                }
            });
            gbc.gridx = 3;
            add(importExcelBt, gbc);

            exportExcelBt = new JButton("Export Excel");
            exportExcelBt.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                }
            });
            gbc.gridx = 4;
            add(exportExcelBt, gbc);

//            sortAscBt = new JButton("Sort Ascending");
//            sortAscBt.addActionListener(new ActionListener() {
//                public void actionPerformed(ActionEvent e) {
//
//                }
//            });
//            gbc.gridx =5;
//            add(sortAscBt,gbc);
//
//            sortDescBt = new JButton("Sort descending");
//            sortDescBt.addActionListener(new ActionListener() {
//                public void actionPerformed(ActionEvent e) {
//
//                }
//            });
//            gbc.gridx =6;
//            add(sortDescBt,gbc);

            // row 2: textfield and search button
            gbc.gridx = 1;
            gbc.gridy = 1;
            gbc.gridwidth = 2;  // TextField chiếm 3 cột (cột 2, 3, 4)
            gbc.weightx = 1.0;  // giúp TextField giãn khi khung phóng to
            gbc.anchor = GridBagConstraints.CENTER;
            searchTextField = new JTextField("Search");
            searchTextField.setPreferredSize(new Dimension(250, 40));
            searchTextField.setFont(Style.FONT_TEXT_CUSTOMER);
            searchTextField.setForeground(Color.GRAY);
            // Thêm FocusListener để kiểm soát khi người dùng nhấn và rời khỏi JTextField
            searchTextField.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    // Khi người dùng nhấn vào JTextField, nếu vẫn là chữ "Search", nó sẽ biến mất
                    if (searchTextField.getText().equals("Search")) {
                        searchTextField.setText("");
                        searchTextField.setForeground(Color.BLACK);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    // Khi người dùng rời khỏi JTextField mà chưa nhập gì, sẽ hiển thị lại chữ "Search"
                    if (searchTextField.getText().isEmpty()) {
                        searchTextField.setForeground(Color.GRAY);
                        searchTextField.setText("Search");
                    }
                }
            });
            add(searchTextField, gbc);

            searchBt = new JButton();
            setStyleButton(searchBt, Style.FONT_TEXT_CUSTOMER, Style.WORD_COLOR_WHITE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, SwingConstants.CENTER, new Dimension(68, 38));
            setIconSmallButton("src/main/java/Icon/search_Icon.png", searchBt);
            searchBt.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                }
            });
            gbc.gridx = 3;  // cột 5 (do GridBagLayout tính từ 0)
            gbc.gridwidth = 1;  // chỉ chiếm 1 cột
            add(searchBt, gbc);
            // add table
            tableAccount = createTable(modelAccount, headerAccount, accountColumnNames);
            tableAccount.setRowHeight(40);
            resizeColumnWidth(tableAccount, 200);
            scrollPaneAccount = new JScrollPane(tableAccount);
            modelAccount = (DefaultTableModel) tableAccount.getModel();
//            modelCustomer.addRow(rowData);


            tabbedPaneAccount = createTabbedPane(scrollPaneAccount, "Account List", Style.FONT_HEADER_ROW_TABLE);
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 7;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 1.0;  // bảng giãn theo chiều ngang
            gbc.weighty = 1.0;
            add(tabbedPaneAccount, gbc);


        }

    }

    class NotificationPanel extends JPanel {
        public NotificationPanel() {
        }
    }

    //nut thong tin
    class ChangeInformationPanel extends JPanel {
        public ChangeInformationPanel() {
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

    private static void setStyleButton(JButton that, Font font, Color textColor, Color backgroundColor, int textPosition, Dimension size) {
        that.setFont(font);
        that.setForeground(textColor);
        that.setBackground(backgroundColor);
        that.setHorizontalAlignment(textPosition);
        that.setBorderPainted(false);
        that.setFocusable(false);
        that.setPreferredSize(size);
    }

    // thay đổi kích thước của cột trong bảng
    public void resizeColumnWidth(JTable table, int width) {
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(width);
            table.getColumnModel().getColumn(0).setPreferredWidth(100);
            table.getColumnModel().getColumn(1).setPreferredWidth(120);
        }
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

        // Lắng nghe sự kiện khi nút được nhấn
        clearAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearTextFields(panel);
            }
        });

        return clearAllButton;
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
    public JTable createTable(DefaultTableModel model, JTableHeader tableHeader, String[] columnNames) {
        // Thiết lập bảng
        JTable table = new JTable();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getTableHeader().setResizingAllowed(true);
        table.setFont(Style.FONT_TEXT_TABLE);

        // Thiết lập model cho bảng
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setModel(model);

        // Thiết lập table header
        tableHeader = table.getTableHeader();
        tableHeader.setBackground(Style.MENU_BUTTON_COLOR_GREEN);
        tableHeader.setForeground(Color.BLACK);
        tableHeader.setReorderingAllowed(false);
        tableHeader.setFont(Style.FONT_HEADER_ROW_TABLE);

        return table;
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

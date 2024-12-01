package view;

import Config.*;
import Enum.TableStatus;
import Model.*;
import Verifier.*;
import com.toedter.calendar.JCalendar;
import controller.*;
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
import view.OverrideComponent.CircularImage;
import view.OverrideComponent.CustomButton;
import view.OverrideComponent.RoundedBorder;
import view.OverrideComponent.ToastNotification;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.Image;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static Model.Account.getCurrentDate;
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

    JPanel notificationContainer;
    //create constraint to switch between panels
    static final String WELCOME_CONSTRAINT = "welcome";
    static final String PRODUCT_CONSTRAINT = "product";
    static final String SUPPLIER_CONSTRAINT = "supplier";
    static final String CUSTOMER_CONSTRAINT = "customer";
    static final String INVENTORY_CONSTRAINT = "inventory";
    static final String INVENTORY_CONTROL_CONSTRAINT = "inventoryControl";
    static final String EXPORT_CONSTRAINT = "export";
    static final String ACC_MANAGEMENT_CONSTRAINT = "accManagement";
    static final String NOTIFICATION_CONSTRAINT = "notification";
    static final String CHANGE_INFORMATION_CONSTRAINT = "changeInformation";

    // create data for column Names
    static final String[] columnNamesPRODUCT = {"Serial Number", "ProductID", "Product Name", "Quantity", "Unit Price", "Type of Device", "Brand",
            "Operating System", "CPU", "Storage", "RAM", "Made In", "Status", "Disk", "Weight", "Monitor", "Card"};
    static final String[] columnNamesSUPPLIER = {"Serial Number", "Supplier ID:", "Supplier Name:", "Email:", "Phone number:", "Address:", "Contract Start Date:"};
    static final String[] columnNamesCUSTOMER = {"Customer ID:", "Customer Name:","Phone Number:", "Email:", "Address:", "Date of Birth:"};
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
        JComboBox<String> sortComboBox;

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

        public static void deletedProduct(int id) {
            productController.setDeleteRow(id, false);
        }

        public static boolean changeStatus(int id, String status) {
            return productController.setStatus(id, status);
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
                findText = TextFieldConfig.createTextFieldWithPlaceHolder("Search by Name",new Font("Arial", Font.PLAIN, 24),Color.GRAY,new Dimension(280, 50));
                findText.addActionListener(e -> searchBt.doClick());

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
                sortComboBox = new JComboBox<>(sortOptions);
                sortComboBox.setPreferredSize(new Dimension(80, 50));
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
                sortLabel = new JLabel("Sort",SwingConstants.CENTER);
                sortLabel.setFont(Style.FONT_SIZE_MIN_PRODUCT);
                sortPanel.add(sortComboBox, BorderLayout.CENTER);
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
                            JOptionPane.showMessageDialog(tablePanel, "Product not found in the List!");
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
        JButton addBt, modifyBt, deleteBt, exportExcelBt, importExcelBt, reloadBt, searchBt;
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
                    ButtonConfig.setStyleButton(addBt, Style.FONT_BUTTON_CUSTOMER, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(80, 80));
                    ButtonConfig.addButtonHoverEffect(addBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                    ButtonConfig.setIconBigButton("src/main/java/Icon/database-add-icon.png", addBt);
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
                    ButtonConfig.setStyleButton(deleteBt, Style.FONT_BUTTON_CUSTOMER, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(80, 80));
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
                    ButtonConfig.setStyleButton(importExcelBt, Style.FONT_BUTTON_CUSTOMER, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(80, 80));
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
                                ArrayList<Supplier> importedSuppliers = ExcelConfig.importFromExcel(selectedFile, Supplier.class);
                                suppliers.clear();
                                suppliers.addAll(importedSuppliers);
                                updateSuppliers(selectedOption);
                            }
                        }
                    });

                }

                // Reload button
                {
                    reloadBt = new JButton("Reload");
                    ButtonConfig.setStyleButton(reloadBt, Style.FONT_BUTTON_CUSTOMER, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(80, 80));
                    ButtonConfig.addButtonHoverEffect(reloadBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                    ButtonConfig.setIconBigButton("src/main/java/Icon/reload-icon.png", reloadBt);
                    reloadBt.setHorizontalTextPosition(SwingConstants.CENTER);
                    reloadBt.setVerticalTextPosition(SwingConstants.BOTTOM);
                    reloadBt.addActionListener(e -> updateSuppliers(selectedOption));
                }
                findText = TextFieldConfig.createTextFieldWithPlaceHolder("Search by Name",new Font("Arial", Font.PLAIN, 24),Color.GRAY,new Dimension(280, 50));
                findText.addActionListener(e -> searchBt.doClick());

                // Search Button
                searchBt = new JButton();
                ButtonConfig.setStyleButton(searchBt, Style.FONT_SIZE, Color.BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(40, 45));
                ButtonConfig.setIconSmallButton("src/main/java/Icon/106236_search_icon.png", searchBt);
                ButtonConfig.addButtonHoverEffect(searchBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                searchBt.addActionListener(e -> {
                    if (!findText.getText().isBlank()) {
                        String text = findText.getText().toLowerCase().trim();
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
                sortComboBox.setPreferredSize(new Dimension(100, 59));
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
                applicationPanel.add(reloadBt);
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
                    ToastNotification.showToast("Please select a row to modify.", 3000, 50, -1, -1);
                }
            }

            private void deleteHandle() {
                int selectedRow = tableSupplier.getSelectedRow();

                if (selectedRow != -1) {
                    int supplierId = Integer.parseInt((String) modelSupplier.getValueAt(selectedRow, 1));

                    supplierController.setDeleteRow(supplierId, false);

                    // Remove the row from the table model
                    modelSupplier.removeRow(selectedRow);

                    ToastNotification.showToast("Supplier marked as deleted successfully.", 3000, 50, -1, -1);
                } else {
                    ToastNotification.showToast("Please select a row to delete.", 3000, 50, -1, -1);
                }
            }
        }


        public class TablePanel extends JPanel {
            public TablePanel() {
                setLayout(new BorderLayout());
                setBackground(Style.WORD_COLOR_WHITE);

                tableSupplier = createTable(modelSupplier, columnNamesSUPPLIER);
                tableSupplier.setRowHeight(40);
                resizeColumnWidth(tableSupplier, 300);
                tableSupplier.getColumnModel().getColumn(tableSupplier.getColumnCount() - 1).setPreferredWidth(400);
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
        private JTextField findCustomerField;

        private TextDisplayPanel billTextDisplayPanal;
        private int index = 0;
        private final int TAB_DATA_CUSTOMER = 0;
        private final int TAB_SCHEMAS = 1;
        private final int TAB_BILL = 2;


        private static CustomerController customerController = new CustomerController();
        private static ArrayList<Customer> customers = new ArrayList<>();
        private ArrayList<CustomerOrderDTO> bills = new ArrayList<>();

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
                        int fullName = 2;
                        if (selectedRow != -1) {
                            Object value = tableCustomer.getValueAt(selectedRow, columnIndex);
                            int customerId = Integer.parseInt(value.toString());
                            String customername = (String) tableCustomer.getValueAt(selectedRow, fullName);
                            if (customername.contains("*")) {
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
                ButtonConfig.setIconBigButton("src/main/java/Icon/bill.png", writeToFileTXT);
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


                findCustomerField = TextFieldConfig.createTextFieldWithPlaceHolder("Search by Name",new Font("Arial", Font.PLAIN, 24),Color.GRAY,new Dimension(280, 50));
                findCustomerField.addActionListener(e -> searchCustomerBt.doClick());

                searchCustomerBt = new JButton();
                ButtonConfig.addButtonHoverEffect(searchCustomerBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                setStyleButton(searchCustomerBt, Style.FONT_SIZE_MIN_PRODUCT, Color.BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(40, 45));
                ButtonConfig.setIconSmallButton("src/main/java/Icon/106236_search_icon.png", searchCustomerBt);
                searchCustomerBt.addActionListener(
                        new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {

                                switch (getIndexSelectedTab()) {

                                    case TAB_DATA_CUSTOMER: {
                                        if (findCustomerField.getText().trim().isEmpty())
                                            return;
                                        ArrayList<Customer> cuss = customerController.find(findCustomerField.getText().trim());
                                        if (cuss.isEmpty()) {
                                            JOptionPane.showMessageDialog(tableCustomerPanel, "có caái ni");
                                            return;
                                        }
                                        upDataTable(cuss, modelCustomer, tableCustomer);
                                        break;
                                    }
                                    case TAB_BILL: {
                                        if (findCustomerField.getText().trim().isEmpty())
                                            return;
                                        try {
                                            int customerId = Integer.parseInt(findCustomerField.getText());
                                            bills = customerController.findCustomerOrderById(customerId);
                                            if (bills.isEmpty())
                                                JOptionPane.showMessageDialog(null, "No information available");
                                            billTextDisplayPanal.setText(toBillsString(bills));
                                            billTextDisplayPanal.setTextEditable(false);

                                        } catch (Exception ex) {
                                            JOptionPane.showMessageDialog(null, "You must enter the ID Customer");
                                            findCustomerField.setText("");
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
                searchPanel.add(findCustomerField);
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
                tabbedPaneCustomer.add("Sales Chart", new Schemas());

                billTextDisplayPanal = new TextDisplayPanel();
                tabbedPaneCustomer.add("Customer Bill", billTextDisplayPanal);
                add(tabbedPaneCustomer, BorderLayout.CENTER);


            }
        }

        private void reload() {
            customers = customerController.getAll();
            upDataTable(customers, modelCustomer, tableCustomer);
            billTextDisplayPanal.setText("You should continue to find the customer Id!!!");
        }

        public int getIndexSelectedTab() {
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

        public static void upDataTable(ArrayList<Customer> customers, DefaultTableModel modelCustomerTable, JTable tableCustomer) {
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
        ExportPanel exportPanel;
        CardLayout cardLayoutInventory;

        private DefaultTableModel modelInventory, modelImport, modelExport;
        private ArrayList<Product> products;

        InventoryPanel() {
            cardLayoutInventory = new CardLayout();
            setLayout(cardLayoutInventory);

            exportPanel = new ExportPanel();
            inventoryControlPanel = new InventoryControlPanel();

            add(inventoryControlPanel, INVENTORY_CONTROL_CONSTRAINT);
            add(exportPanel, EXPORT_CONSTRAINT);

            cardLayoutInventory.show(this, INVENTORY_CONTROL_CONSTRAINT);
        }

        // panel chứa các chức năng tương tác của inventory
        public class InventoryControlPanel extends JPanel {
            private JTabbedPane tabbedPaneMain;
            private ToolsPanel toolsPanel;
            private JTextField searchTextField;
            private JButton searchBt;

            public InventoryControlPanel() {
                setLayout(new BorderLayout(5, 5));
                setBackground(Style.WORD_COLOR_WHITE);

                toolsPanel = new ToolsPanel();
                add(toolsPanel, BorderLayout.NORTH);

                TablePanel tablePanel = new TablePanel();
                add(tablePanel, BorderLayout.CENTER);
            }

            private class ToolsPanel extends JPanel {
                ToolsPanel() {
                    setLayout(new GridBagLayout());
                    setBorder(BorderFactory.createTitledBorder("Tools"));
                    setBackground(Style.WORD_COLOR_WHITE);

                    GridBagConstraints gbc = new GridBagConstraints();
                    JPanel buttonPanel = new JPanel(new FlowLayout());
                    buttonPanel.setBackground(Style.WORD_COLOR_WHITE);
                    // Set for Sale
                    {
                        JButton setForSaleBt = new JButton("Sale");
                        ButtonConfig.setStyleButton(setForSaleBt, Style.FONT_SIZE_MIN_PRODUCT, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(80, 80));
                        ButtonConfig.addButtonHoverEffect(setForSaleBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                        ButtonConfig.setIconBigButton("src/main/java/Icon/product-selling.png", setForSaleBt);
                        setForSaleBt.setHorizontalTextPosition(SwingConstants.CENTER);
                        setForSaleBt.setVerticalTextPosition(SwingConstants.BOTTOM);
                        setForSaleBt.addActionListener(e -> setStatusHandle(Product.AVAILABLE));
                        buttonPanel.add(setForSaleBt);
                    }

                    // Set Re-stock
                    {
                        JButton setForSaleBt = new JButton("Re-stock");
                        ButtonConfig.setStyleButton(setForSaleBt, Style.FONT_SIZE_MIN_PRODUCT, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(90, 80));
                        ButtonConfig.addButtonHoverEffect(setForSaleBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                        ButtonConfig.setIconBigButton("src/main/java/Icon/product-restock.png", setForSaleBt);
                        setForSaleBt.setHorizontalTextPosition(SwingConstants.CENTER);
                        setForSaleBt.setVerticalTextPosition(SwingConstants.BOTTOM);
                        setForSaleBt.addActionListener(e -> setStatusHandle(Product.IN_STOCK));
                        buttonPanel.add(setForSaleBt);
                    }

                    buttonPanel.add(ButtonConfig.createVerticalSeparator());

                    //Export Product
                    {
                        JButton exportProduct = new JButton("Export Product");
                        ButtonConfig.setStyleButton(exportProduct, Style.FONT_SIZE_MIN_PRODUCT, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(120, 80));
                        ButtonConfig.addButtonHoverEffect(exportProduct, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                        ButtonConfig.setIconBigButton("src/main/java/Icon/delivery-box.png", exportProduct);
                        exportProduct.setHorizontalTextPosition(SwingConstants.CENTER);
                        exportProduct.setVerticalTextPosition(SwingConstants.BOTTOM);
                        exportProduct.addActionListener(e -> showPanelInInventory(EXPORT_CONSTRAINT));
                        buttonPanel.add(exportProduct);
                    }

                    buttonPanel.add(ButtonConfig.createVerticalSeparator());

                    //Delete
                    {
                        JButton deleteBt = new JButton("Delete");
                        ButtonConfig.setStyleButton(deleteBt, Style.FONT_SIZE_MIN_PRODUCT, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(80, 80));
                        ButtonConfig.addButtonHoverEffect(deleteBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                        ButtonConfig.setIconBigButton("src/main/java/Icon/delete-icon-removebg-preview.png", deleteBt);
                        deleteBt.setHorizontalTextPosition(SwingConstants.CENTER);
                        deleteBt.setVerticalTextPosition(SwingConstants.BOTTOM);
                        deleteBt.addActionListener(e -> deletedHandle());
                        buttonPanel.add(deleteBt);
                    }

                    //Modify
                    {
                        JButton modifyBt = new JButton("Modify");
                        ButtonConfig.setStyleButton(modifyBt, Style.FONT_SIZE_MIN_PRODUCT, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(80, 80));
                        ButtonConfig.addButtonHoverEffect(modifyBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                        ButtonConfig.setIconBigButton("src/main/java/Icon/modify.png", modifyBt);
                        modifyBt.setHorizontalTextPosition(SwingConstants.CENTER);
                        modifyBt.setVerticalTextPosition(SwingConstants.BOTTOM);
                        modifyBt.addActionListener(e -> modifyHandle());
                        buttonPanel.add(modifyBt);
                    }

                    //Export Excel
                    {
                        JButton exportExcelBt = new JButton("Export");
                        ButtonConfig.setStyleButton(exportExcelBt, Style.FONT_SIZE_MIN_PRODUCT, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(80, 80));
                        ButtonConfig.addButtonHoverEffect(exportExcelBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                        ButtonConfig.setIconBigButton("src/main/java/Icon/icons8-file-excel-32.png", exportExcelBt);
                        exportExcelBt.setHorizontalTextPosition(SwingConstants.CENTER);
                        exportExcelBt.setVerticalTextPosition(SwingConstants.BOTTOM);
                        exportExcelBt.addActionListener(e -> {
                            String fileName = JOptionPane.showInputDialog("Enter the name of the Excel file:");
                            if (fileName != null && !fileName.trim().isEmpty()) {
                                fileName = fileName.trim().endsWith(".xlsx") ? fileName.trim() : fileName.trim() + ".xlsx";
                                getSelectedTable();
                                ExcelConfig.exportToExcel(products, fileName, columnNamesPRODUCT);
                                JOptionPane.showMessageDialog(null, "Exported to " + fileName);
                            } else {
                                JOptionPane.showMessageDialog(null, "File name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        });
                        buttonPanel.add(exportExcelBt);
                    }

                    //Reload
                    {
                        JButton reloadBt = new JButton("Reload");
                        ButtonConfig.setStyleButton(reloadBt, Style.FONT_SIZE_MIN_PRODUCT, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(80, 80));
                        ButtonConfig.addButtonHoverEffect(reloadBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                        ButtonConfig.setIconBigButton("src/main/java/Icon/reload-icon.png", reloadBt);
                        reloadBt.setHorizontalTextPosition(SwingConstants.CENTER);
                        reloadBt.setVerticalTextPosition(SwingConstants.BOTTOM);
                        reloadBt.addActionListener(e -> updateProduct());
                        buttonPanel.add(reloadBt);
                    }

                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    gbc.weightx = 1;
                    gbc.anchor = GridBagConstraints.WEST;
                    add(buttonPanel, gbc);

                    JPanel searchPanel = createSearchPanel();
                    gbc.gridx = 1;
                    gbc.gridy = 0;
                    gbc.weightx = 0;
                    gbc.anchor = GridBagConstraints.EAST;
                    add(searchPanel, gbc);
                }

                private JTable getSelectedTable() {
                    int index = tabbedPaneMain.getSelectedIndex();

                    return switch (index) {
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
                        default -> throw new IllegalStateException("Unexpected value: " + index);
                    };
                }

                private void setStatusHandle(String status) {
                    String[] messages = switch (status) {
                        case (Product.AVAILABLE) -> new String[]{"Available for sale", "add product for sale."};
                        case (Product.IN_STOCK) -> new String[]{"Re-stocked", "re-stock the product."};
                        default -> null;
                    };
                    JTable selectedTable = getSelectedTable();

                    int[] selectedRows = selectedTable.getSelectedRows();
                    if (selectedRows.length > 0 && messages != null) {
                        int y = -1, duration = 3000;
                        for (int row : selectedRows) {
                            int productId = Integer.parseInt(selectedTable.getValueAt(row, 1).toString());
                            String productName = (String) selectedTable.getValueAt(row, 2);
                            if (changeStatus(productId, status)) {
                                ToastNotification.showToast("Successfully set product " + productName + " to " + messages[0], duration, 50, -1, y++);
                            } else
                                ToastNotification.showToast("Failed to set product " + productName + " to " + messages[0], duration, 50, -1, y++);
                            duration += 100;
                        }
                        updateProduct();
                    } else {
                        ToastNotification.showToast("Please select a row to " + messages[1], 3000, 50, -1, -1);
                    }
                }

                private void modifyHandle() {
                    JTable selectedTable = getSelectedTable();

                    if (selectedTable != null && selectedTable.getSelectedRow() != -1) {
                        int selectedRow = selectedTable.getSelectedRow();
                        SwingUtilities.invokeLater(() -> {
                            new ProductModifyForm(products.get(selectedRow), InventoryPanel.this::updateProduct).setVisible(true);
                        });
                    } else {
                        ToastNotification.showToast("Please select a row to modify.", 3000, 50, -1, -1);
                    }
                }

                private void deletedHandle() {
                    JTable selectedTable = getSelectedTable();

                    if (selectedTable != null && selectedTable.getSelectedRow() != -1) {
                        int selectedRow = selectedTable.getSelectedRow();
                        int productId = Integer.parseInt(selectedTable.getValueAt(selectedRow, 1).toString());
                        String productName = (String) selectedTable.getValueAt(selectedRow, 2);
                        deletedProduct(productId);
                        updateProduct();
                        ToastNotification.showToast("Successfully delete product " + productName, 3000, 50, -1, -1);
                    } else {
                        ToastNotification.showToast("Please select a row to deleted.", 3000, 50, -1, -1);
                    }
                }
            }

            private class TablePanel extends JPanel {
                public TablePanel() {
                    setLayout(new BorderLayout());
                    setBackground(Style.WORD_COLOR_WHITE);

                    // Create tables
                    tableInventory = createTable(modelInventory, columnNamesPRODUCT);
                    tableImport = createTable(modelImport, columnNamesPRODUCT);
                    tableExport = createTable(modelExport, columnNamesPRODUCT);

                    modelInventory = (DefaultTableModel) tableInventory.getModel();
                    upDataProducts(modelInventory, null);
                    modelImport = (DefaultTableModel) tableImport.getModel();
                    upDataProducts(modelImport, Product.IN_STOCK);
                    modelExport = (DefaultTableModel) tableExport.getModel();
                    upDataProducts(modelExport, Product.AVAILABLE);

                    // Add tables to tabbed pane
                    tabbedPaneMain = new JTabbedPane();
                    tabbedPaneMain.setFont(Style.FONT_HEADER_ROW_TABLE);
                    tabbedPaneMain.addTab("Inventory", new JScrollPane(tableInventory));
                    tabbedPaneMain.addTab("In Stock", new JScrollPane(tableImport));
                    tabbedPaneMain.addTab("Available for Sale", new JScrollPane(tableExport));

                    add(tabbedPaneMain, BorderLayout.CENTER);
                }
            }

            private JPanel createSearchPanel() {
                JPanel searchPanel = new JPanel(new GridBagLayout());
                searchPanel.setBackground(Style.WORD_COLOR_WHITE);
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(5, 5, 5, 5);

                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.weightx = 1.0;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                searchTextField = TextFieldConfig.createTextFieldWithPlaceHolder("Search by Name",new Font("Arial", Font.PLAIN, 24),Color.GRAY,new Dimension(280, 50));
                searchTextField.addActionListener(e -> searchBt.doClick());
                searchPanel.add(searchTextField, gbc);

                gbc.gridx = 1;
                gbc.weightx = 0;
                searchPanel.add(createSearchButton(), gbc);

                return searchPanel;
            }

            private void deletedProduct(int id) {
                ProductPanel.deletedProduct(id);
            }

            private boolean changeStatus(int id, String status) {
                return ProductPanel.changeStatus(id, status);
            }

            private JButton createSearchButton() {
                searchBt = new JButton();
                ButtonConfig.setStyleButton(searchBt, Style.FONT_SIZE, Color.BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(40, 45));
                ButtonConfig.addButtonHoverEffect(searchBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                ButtonConfig.setIconSmallButton("src/main/java/Icon/106236_search_icon.png", searchBt);
                searchBt.addActionListener(e -> searchHandle());
                return searchBt;
            }

            private void searchHandle() {
                if (!searchTextField.getText().isBlank()) {
                    String searchText = searchTextField.getText().toLowerCase().trim();
                    searchProduct(searchText);
                } else updateProduct();
            }
        }

        class ExportPanel extends JPanel {
            LeftPn leftPn;
            RightPn rightPn;

            private JTextField customerIDTF, customerNameTF, customerPhoneNumberTF, customerEmailTF, customerAddressTF, orderDateTF;
            private final JTextField[] customerTFArray = {customerIDTF, customerNameTF, customerPhoneNumberTF, customerEmailTF, customerAddressTF, orderDateTF};

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


                public void showPanelAddCustomer(String panelName) {
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

                        JTextField searchTF = new JTextField("Search");
                        add(searchTF, gbc);

                        // Thiết lập GridBagConstraints cho các label và text field
                        gbc.fill = GridBagConstraints.HORIZONTAL;
                        gbc.gridwidth = 1;
                        gbc.weightx = 1.0; // Cân bằng không gian theo chiều ngang
                        gbc.weighty = 0.1;
                        gbc.gridy = 1;

                        for (int i = 0; i < columnNamesCUSTOMER.length; i++) {
                            gbc.gridx = 0; // Cột 1: Label
                            // Bắt đầu từ dòng thứ 2
                            gbc.gridwidth = 1;
                            gbc.fill = GridBagConstraints.BOTH;
                            gbc.anchor = GridBagConstraints.WEST;// cân nhắc

                            JLabel lb = new JLabel(columnNamesCUSTOMER[i]);
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
                                showPanelAddCustomer(ADD_NEW_CUSTOMER_CONSTRAINT);
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

                    add(productList, PRODUCT_LIST);

                    cardLayout.show(this, PRODUCT_LIST);
                }

                public void showPanelImportProduct(String panelName) {
                    cardLayout.show(this, panelName); // method chuyển đổi giữa các panel
                }


                class ProductListPn extends JPanel {
                    JTable tableImportProductList;
                    DefaultTableModel modelImportProductList;
                    JScrollPane scrollPaneImportProductList;
                    JTabbedPane tabbedPaneImportProductList;

                    ProductListPn() {
                        setLayout(new GridBagLayout());
                        GridBagConstraints gbc = new GridBagConstraints();
                        gbc.insets = new Insets(5, 5, 5, 5);

                        tableImportProductList = createTable(modelImportProductList, columnNamesPRODUCT);
                        resizeColumnWidth(tableImportProductList, 100);
                        scrollPaneImportProductList = new JScrollPane(tableImportProductList);

                        tabbedPaneImportProductList = createTabbedPane(scrollPaneImportProductList, "Inventory Import List", Style.FONT_HEADER_ROW_TABLE);
                        gbc.gridx = 0; // Quay lại cột đầu tiên
                        gbc.gridy = 0; // Dòng thứ hai
                        gbc.gridwidth = 3; // JTable chiếm cả 2 cột
                        gbc.fill = GridBagConstraints.BOTH; // JTable lấp đầy cả chiều rộng và chiều cao
                        gbc.weightx = 1.0;
                        gbc.weighty = 1.0;
                        add(tabbedPaneImportProductList, gbc);
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

        private void upDataProducts(DefaultTableModel tableModel, String status) {
            if (status == null || status.isEmpty()) reloadProducts();
            else reloadProducts(status);
            ProductPanel.upDataProducts(products, tableModel);
        }

        private void upDataProducts(DefaultTableModel tableModel, String status, String searchText) {
            if (status == null || status.isEmpty()) reloadProducts();
            else reloadProducts(status);
            if (searchText != null && !searchText.isEmpty()) {
                products.removeIf(product -> !product.getName().toLowerCase().contains(searchText.toLowerCase()));
            }
            ProductPanel.upDataProducts(products, tableModel);
        }

        private void updateProduct() {
            upDataProducts(modelInventory, null);
            upDataProducts(modelImport, Product.IN_STOCK);
            upDataProducts(modelExport, Product.AVAILABLE);
        }

        private void searchProduct(String searchText) {
            upDataProducts(modelInventory, null, searchText);
            upDataProducts(modelImport, Product.IN_STOCK, searchText);
            upDataProducts(modelExport, Product.AVAILABLE, searchText);
        }
    }

    class AccManagementPanel extends JPanel {
        private final String[] accountColumnNames = {"Serial Number", "ManagerID", "Fullname", "Address", "Birth Day", "Phone Number", "AccountID", " User Name", "Password", "Email", "Account creation date", "Avata"};
        private final int informationPanel = 0;
        private final int addOrModify = 1;
        private JTable tableAccManager;
        private DefaultTableModel modelAccManager;
        private JScrollPane scrollPaneAccManager;
        private JTabbedPane tabbedPaneAccManager;
        private ToolPanel toolPanel = new ToolPanel();
        private TableCustomerPanel tableAccManagerPanel = new TableCustomerPanel();

        private JButton addAccBt, modifyAccBt, exportAccExcelBt, searchAccBt, reloadAccBt, blockCustomer;
        private JTextField accManagerField;


        private JPanel searchPanel, applicationPanel, mainPanel;

        // add
        private JTextField txtFullName, txtAddress, txtBirthday, txtPhoneNumber;
        private JTextField usernameField, emailField;
        private JPasswordField passwordField;
        private Date selectedDate;
        private JLabel label;
        private String contextPath = "";
        private int modifyId = -1;
        private static boolean btnModifyStutus = false;
        private static TableStatus tableStatus = TableStatus.NONE;
        private static ArrayList<ManagerInforDTO> managerInfors = new ArrayList<>();
        private ManagerController managerController = new ManagerController();
        private AccountController accountController = new AccountController();

        public AccManagementPanel() {
            setLayout(new BorderLayout());
            toolPanel.setBorder(BorderFactory.createTitledBorder("Tool"));
            add(toolPanel, BorderLayout.NORTH);
            add(tableAccManagerPanel, BorderLayout.CENTER);
        }

        private Account getAcc() {
            Account account = new Account();
            account.setUsername(usernameField.getText());
            account.setEmail(emailField.getText());
            account.setPassword(new String(passwordField.getPassword()));
            account.setAvataImg(contextPath);
            account.setCreateDate(getCurrentDate());
            return account;
        }

        private Manager getManager() {
            Manager manager = new Manager();
            try {
                manager.setFullName(txtFullName.getText());
                manager.setAddress(txtAddress.getText());

                manager.setPhoneNumber(txtPhoneNumber.getText());
                manager.setBirthDay(selectedDate);

            } catch (NullPointerException nullPointerException) {
                System.out.println(nullPointerException.toString());
            }
            return manager;
        }

        private boolean verifier() {
            return txtFullName.getInputVerifier().verify(txtFullName) &&
                    txtAddress.getInputVerifier().verify(txtAddress) &&
                    txtBirthday.getInputVerifier().verify(txtBirthday) &&
                    txtPhoneNumber.getInputVerifier().verify(txtPhoneNumber) &&
                    usernameField.getInputVerifier().verify(usernameField) &&
                    passwordField.getInputVerifier().verify(passwordField) &&
                    emailField.getInputVerifier().verify(emailField);
        }

        private void removeInfor() {
            txtFullName.setText("");
            txtAddress.setText("");
            txtBirthday.setText("");
            txtPhoneNumber.setText("");
            usernameField.setText("");
            passwordField.setText("");
            emailField.setText("");
            label.setIcon(null);
            label.setText("Drop your image here");
            btnModifyStutus = false;
            addAccBt.setEnabled(true);
        }

        private int getIndexTableSelectedTab() {
            return tabbedPaneAccManager.getSelectedIndex();
        }

        private void setVisiblePanel(int panel) {
            tabbedPaneAccManager.setSelectedIndex(panel);
        }

        private void setDataToModify(ManagerInforDTO manager) {
            txtFullName.setText(manager.getFullName());
            txtAddress.setText(manager.getAddress());
            txtBirthday.setText(manager.getBirthDay().toString());
            txtPhoneNumber.setText(manager.getPhoneNumber());
            usernameField.setText(manager.getUsername());
//             passwordField.setText("");
            emailField.setText(manager.getEmail());
            contextPath = manager.getAvataImg();
            System.out.println("contextPart " + contextPath);
            try {
                Path targetPath = Paths.get(contextPath);
                ImageIcon avatarIcon = new ImageIcon(targetPath.toString());
                Image scaledImage = avatarIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(scaledImage));
                label.setText("");

            } catch (NullPointerException npe) {
                System.out.println("meo cos hinhf ");
            }
//             Files.createDirectories(targetPath.getParent());
//             Files.copy(file.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);


        }

        public class ToolPanel extends JPanel {
            public ToolPanel() {
                setLayout(new BorderLayout());
                setBackground(Style.WORD_COLOR_WHITE);
                addAccBt = new JButton("Add");
                ButtonConfig.addButtonHoverEffect(addAccBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                setStyleButton(addAccBt, Style.FONT_SIZE_MIN_PRODUCT, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(80, 80));
                ButtonConfig.setIconBigButton("src/main/java/Icon/database-add-icon.png", addAccBt);
                addAccBt.setHorizontalTextPosition(SwingConstants.CENTER);
                addAccBt.setVerticalTextPosition(SwingConstants.BOTTOM);
                addAccBt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        setVisiblePanel(addOrModify);
//                        switch (getIndexTableSelectedTab()){
                        System.out.println(" modify btn ");
//                            case 1 ->{
                        tableStatus = TableStatus.ADD;
                        try {
                            if (!verifier()) {
                                ToastNotification.showToast("Verifier False ", 2500, 50, -1, -1);
                                return;
                            }
                            if (getAcc().getAvataImg().isEmpty()) {
                                Object[] options = {"Push image", "No avata", "Cancel"};
                                int status = JOptionPane.showOptionDialog(
                                        null,
                                        "You haven't uploaded an image!",
                                        "Warning",
                                        JOptionPane.YES_NO_CANCEL_OPTION,
                                        JOptionPane.WARNING_MESSAGE,
                                        null,
                                        options, // Nút tùy chỉnh
                                        options[0] // Nút mặc định
                                );
                                System.out.println(status);
                                switch (status) {
                                    //YES
                                    case (0) -> {
                                        return;
                                    }
                                    //NO
                                    case (1) -> {

                                    }
                                    //CANCEL
                                    case (2) -> {
                                        return;
                                    }

                                }
                            }
                            System.out.println(getAcc());
                            System.out.println(getManager());
                            managerController.createManager(getManager(), getAcc());
                            removeInfor();
                            ToastNotification.showToast("The image has been saved!", 2500, 50, -1, -1);
                        } catch (Exception exception) {
                            ToastNotification.showToast("Please, upload your image again!", 2500, 50, -1, -1);
                        }
//                            }
//                        }
                    }
                });

                modifyAccBt = new JButton("Modify");
                ButtonConfig.addButtonHoverEffect(modifyAccBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                setStyleButton(modifyAccBt, Style.FONT_SIZE_MIN_PRODUCT, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(80, 80));
                ButtonConfig.setIconBigButton("src/main/java/Icon/modify.png", modifyAccBt);
                modifyAccBt.setHorizontalTextPosition(SwingConstants.CENTER);
                modifyAccBt.setVerticalTextPosition(SwingConstants.BOTTOM);
                modifyAccBt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // nhấn zô chua )
                        int selectedRow = tableAccManager.getSelectedRow();
                        System.out.println(" modify btn ");
                        if (btnModifyStutus == false && selectedRow != -1) {

                            tableStatus = TableStatus.MODIFY;


                            int columnIndex = 0;

                            modifyId = (int) tableAccManager.getValueAt(selectedRow, columnIndex) - 1;
//                            System.out.println(id);
                            setDataToModify(managerInfors.get(modifyId));
                            setVisiblePanel(addOrModify);
                            addAccBt.setEnabled(false);
                            btnModifyStutus = true;


                        } else {
                            Account account = getAcc();
                            Manager manager = getManager();
                            account.setId(managerInfors.get(modifyId).getAccountId());
                            manager.setId(managerInfors.get(modifyId).getManagerId());

                            if (manager.birthDayIsNull()) {
                                manager.setBirthDay((Date) managerInfors.get(modifyId).getBirthDay());
                            }
                            System.out.println(manager);
                            System.out.println(account);
                            managerController.update(manager);
                            accountController.update(account);
                            btnModifyStutus = false;
                            reload();
                            removeInfor();
                            ToastNotification.showToast("Your information has been updated successfully.", 2500, 50, -1, -1);

                            addAccBt.setEnabled(true);
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
                        int selectedRow = tableAccManager.getSelectedRow();
                        int columnIndex = 6;
                        int fullnameIndex = 2;
                        if (selectedRow != -1) {
                            Object value = tableAccManager.getValueAt(selectedRow, columnIndex);
                            String name = (String) tableAccManager.getValueAt(selectedRow, fullnameIndex);
                            boolean blocked = !name.contains("*");
                            int id = Integer.parseInt(value.toString());
                            accountController.updateBlock(blocked, id);
                            reload();
                            ToastNotification.showToast(name + (blocked ? " is blocked !!!" : " is unblocked !!!"), 2500, 50, -1, -1);
                        }
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
                        String fileName = JOptionPane.showInputDialog(null, "Enter file name excel:", "Input file", JOptionPane.QUESTION_MESSAGE);
                        if (fileName != null && !fileName.trim().isEmpty()) {
                            reload();
                            ExcelConfig.writeManagersToExcel(managerInfors, fileName);
                            ToastNotification.showToast(fileName + " is created!", 2500, 50, -1, -1);
                        } else {
                            ToastNotification.showToast("Failed to export Excel file!", 2500, 50, -1, -1);
                        }

                    }
                });
                accManagerField = TextFieldConfig.createTextFieldWithPlaceHolder("Search by Name",new Font("Arial", Font.PLAIN, 24),Color.GRAY,new Dimension(280, 50));
                accManagerField.addActionListener(e -> searchAccBt.doClick());

                searchAccBt = new JButton();
                ButtonConfig.addButtonHoverEffect(searchAccBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                setStyleButton(searchAccBt, Style.FONT_SIZE_MIN_PRODUCT, Color.BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(40, 45));
                ButtonConfig.setIconSmallButton("src/main/java/Icon/106236_search_icon.png", searchAccBt);
                searchAccBt.addActionListener(
                        new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                reload();
                                String find = accManagerField.getText().toLowerCase().trim();
                                ArrayList<ManagerInforDTO> managerInforDTOS = (ArrayList<ManagerInforDTO>) managerInfors.stream()
                                        .filter(p -> p.getFullnameLowerCase().contains(find))
                                        .collect(Collectors.toList());
                                upDataTable(managerInforDTOS, modelAccManager, tableAccManager);

                                System.out.println(managerInfors);

                            }
                        }
                );

                reloadAccBt = new JButton("reload");
                ButtonConfig.addButtonHoverEffect(reloadAccBt, Style.BUTTON_COLOR_HOVER, Style.WORD_COLOR_WHITE);
                setStyleButton(reloadAccBt, Style.FONT_SIZE_MIN_PRODUCT, Style.WORD_COLOR_BLACK, Style.WORD_COLOR_WHITE, SwingConstants.CENTER, new Dimension(80, 80));
                ButtonConfig.setIconBigButton("src/main/java/Icon/reload-icon.png", reloadAccBt);
                reloadAccBt.setHorizontalTextPosition(SwingConstants.CENTER);
                reloadAccBt.setVerticalTextPosition(SwingConstants.BOTTOM);
                reloadAccBt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        reload();
                    }
                });

                searchPanel = new JPanel(new FlowLayout());
                searchPanel.add(accManagerField);
                searchPanel.add(searchAccBt);
                searchPanel.setBackground(Style.WORD_COLOR_WHITE);

                applicationPanel = new JPanel(new FlowLayout());
                applicationPanel.add(addAccBt);

                applicationPanel.add(ButtonConfig.createVerticalSeparator());
                applicationPanel.add(modifyAccBt);
                applicationPanel.add(blockCustomer);

                JLabel none = new JLabel("");
                none.setFont(Style.FONT_SIZE_MIN_PRODUCT);
                none.setHorizontalAlignment(SwingConstants.CENTER);
                none.setVerticalAlignment(SwingConstants.CENTER);


                applicationPanel.add(ButtonConfig.createVerticalSeparator());
                applicationPanel.add(exportAccExcelBt);

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
            AddManager addManager;

            public TableCustomerPanel() {
                setLayout(new BorderLayout());
                setBackground(Style.WORD_COLOR_WHITE);
                tableAccManager = createTable(modelAccManager, accountColumnNames);
                // Thiết lập renderer cho cột ảnh
                tableAccManager.getColumnModel().getColumn(accountColumnNames.length - 1).setCellRenderer(new ImageInJTable.ImageRenderer());
                tableAccManager.setRowHeight(100);
                resizeColumnWidth(tableAccManager, 219);
                modelAccManager = (DefaultTableModel) tableAccManager.getModel();
                managerController = new ManagerController();
                reload();

                scrollPaneAccManager = new JScrollPane(tableAccManager);
                tabbedPaneAccManager = createTabbedPane(scrollPaneAccManager, "Information", Style.FONT_HEADER_ROW_TABLE);
                addManager = new AddManager();
                tabbedPaneAccManager.add(tableStatus.getMessage(), addManager);

                add(tabbedPaneAccManager, BorderLayout.CENTER);

            }
        }

        class AddManager extends JPanel {
            ChangeInfo changeInfo;
            Avatar avatar;

            AddManager() {
                setLayout(new GridLayout(2, 1));
                changeInfo = new ChangeInfo();
                avatar = new Avatar();
                add(changeInfo);
                add(avatar);
            }

            public static void addFocusListenerForTextField(JTextField textField) {
                textField.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        textField.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 4));
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        textField.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1));
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
                    LeftPn() {
                        setLayout(new GridBagLayout());
                        setBackground(Color.WHITE);
                        setPreferredSize(new Dimension(400, 150));
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
                        txtFullName = TextFieldConfig.createStyledTextField(Style.FONT_PLAIN_16, Color.BLACK, Style.MEDIUM_BLUE, new Dimension(285, 35));
                        txtFullName.setInputVerifier(new NotNullVerifier());
                        addFocusListenerForTextField(txtFullName);


                        JLabel lblAddress = new JLabel("Address:");
                        txtAddress = TextFieldConfig.createStyledTextField(Style.FONT_PLAIN_16, Color.BLACK, Style.MEDIUM_BLUE, new Dimension(285, 35));
                        txtAddress.setInputVerifier(new NotNullVerifier());
                        addFocusListenerForTextField(txtAddress);

                        JLabel lblBirthday = new JLabel("Birthday:");
                        txtBirthday = TextFieldConfig.createStyledTextField(Style.FONT_PLAIN_16, Color.BLACK, Style.MEDIUM_BLUE, new Dimension(250, 35));
                        txtBirthday.setInputVerifier(new BirthDayVerifier());
                        addFocusListenerForTextField(txtBirthday);
                        txtBirthday.setEditable(false);
                        JButton btnCalendar = new JButton();
                        btnCalendar.setPreferredSize(new Dimension(35, 35));
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
                                selectedDate = new Date(calendar.getDate().getTime());

                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                txtBirthday.setText(dateFormat.format(selectedDate));
                                calendarDialog.setVisible(false);
                            }
                        });

                        JLabel lblPhoneNumber = new JLabel("Phone Number:");
                        txtPhoneNumber = TextFieldConfig.createStyledTextField(Style.FONT_PLAIN_16, Color.BLACK, Style.MEDIUM_BLUE, new Dimension(285, 35));
                        txtPhoneNumber.setInputVerifier(new PhoneNumberVerifier());
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

                    RightPn() {
                        setLayout(new GridBagLayout());
                        setBackground(Color.WHITE);
                        setPreferredSize(new Dimension(400, 150));
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
                        gbc.insets = new Insets(5, 5, 5, 5);

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
                        usernameField = TextFieldConfig.createStyledTextField(Style.FONT_PLAIN_16, Color.BLACK, Style.MEDIUM_BLUE, new Dimension(295, 35));
                        usernameField.setInputVerifier(new UserNameAccountVerifier());
                        addFocusListenerForTextField(usernameField);
                        add(usernameField, gbc);

                        gbc.gridy = 1;
                        JPanel passwdPanel = new JPanel(new BorderLayout());
                        passwordField = TextFieldConfig.createStyledJPasswordField(Style.FONT_PLAIN_16, Style.MEDIUM_BLUE, new Dimension(250, 35));
                        passwordField.setInputVerifier(new NotNullVerifier());
                        passwordField.addFocusListener(new FocusListener() {
                            @Override
                            public void focusGained(FocusEvent e) {
                                passwordField.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 4));
                            }

                            @Override
                            public void focusLost(FocusEvent e) {
                                passwordField.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1));
                            }
                        });

                        JButton togglePasswordButton = ButtonConfig.createShowPasswdButton(passwordField);
                        togglePasswordButton.setPreferredSize(new Dimension(45, 35));
                        passwdPanel.setBackground(Color.WHITE);
                        passwdPanel.add(passwordField, BorderLayout.CENTER);
                        passwdPanel.add(togglePasswordButton, BorderLayout.EAST);

                        add(passwdPanel, gbc);

                        gbc.gridy = 2;
                        emailField = TextFieldConfig.createStyledTextField(Style.FONT_PLAIN_16, Color.BLACK, Style.MEDIUM_BLUE, new Dimension(295, 35));
                        emailField.setInputVerifier(new EmailVerifier());
                        addFocusListenerForTextField(emailField);
                        add(emailField, gbc);

                    }
                }
            }


            class Avatar extends JPanel {
                CustomButton importImage, undoBt, cancelBt;


                Avatar() {
                    setLayout(new BorderLayout());
                    setBackground(Color.WHITE);
                    label = new JLabel("Drop your image here", SwingConstants.CENTER);
                    label.setBackground(Color.WHITE);
                    Border dashedBorder = BorderFactory.createDashedBorder(Style.CONFIRM_BUTTON_COLOR_GREEN, 2, 10, 20, true);
                    Border margin = BorderFactory.createEmptyBorder(5, 10, 5, 10);
                    Border compoundBorder = BorderFactory.createCompoundBorder(margin, dashedBorder);
                    label.setBorder(compoundBorder);
                    label.setPreferredSize(new Dimension(400, 300));

                    JPanel uploadImagePn = new JPanel();
                    uploadImagePn.setBackground(Color.WHITE);
                    importImage = new CustomButton("Upload Image from your computer");
                    importImage.setDrawBorder(false);
                    importImage.setPreferredSize(new Dimension(300, 40));
                    importImage.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Tạo JFileChooser
                            JFileChooser fileChooser = new JFileChooser();
                            fileChooser.setDialogTitle("Chọn hình ảnh");

                            // Lọc file chỉ cho phép chọn hình ảnh
                            fileChooser.setFileFilter(new FileNameExtensionFilter(
                                    "Hình ảnh (JPG, PNG, GIF)", "jpg", "png", "gif"));

                            // Hiển thị hộp thoại và lấy kết quả
                            int result = fileChooser.showOpenDialog(null);
                            if (result == JFileChooser.APPROVE_OPTION) {
                                // Lấy file được chọn
                                File selectedFile = fileChooser.getSelectedFile();
                                contextPath = selectedFile.getAbsolutePath();

                                // Hiển thị hình ảnh được chọn
                                ImageIcon imageIcon = new ImageIcon(contextPath);
                                Image scaledImage = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                                label.setIcon(new ImageIcon(scaledImage));
                            }
                        }
                    });


                    label.setTransferHandler(new ImageTransferHandler());
                    label.setInputVerifier(new NotNullVerifier());

                    undoBt = new CustomButton("Undo");
                    undoBt.setPreferredSize(new Dimension(100, 40));
                    undoBt.setDrawBorder(false);
                    undoBt.addActionListener(e -> {
                        System.out.println(btnModifyStutus);
                        if (btnModifyStutus == true)
                            setDataToModify(managerInfors.get(modifyId));
                    });

                    cancelBt = new CustomButton("cancel");
                    cancelBt.setPreferredSize(new Dimension(100, 40));
                    cancelBt.setDrawBorder(false);
                    cancelBt.addActionListener(e -> {
                        removeInfor();
                    });

                    uploadImagePn.add(undoBt);
                    uploadImagePn.add(importImage);
                    uploadImagePn.add(cancelBt);

                    add(label, BorderLayout.CENTER);
                    add(uploadImagePn, BorderLayout.SOUTH);
                }

                private class ImageTransferHandler extends TransferHandler {
                    @Override
                    public boolean canImport(TransferSupport support) {
                        return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
                    }

                    @Override
                    public boolean importData(TransferSupport support) {
                        if (!canImport(support)) return false;

                        try {
                            Transferable transferable = support.getTransferable();
                            List<File> files = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
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
                                label.setIcon(new ImageIcon(scaledImage));
                                label.setText("");

                                return true;
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        return false;
                    }
                }
            }

        }

        private void reload() {
            managerInfors = managerController.getManagerInforDTO();
            upDataTable(managerInfors, modelAccManager, tableAccManager);
//            billTextDisplayPanal.setText("You should continue to find the customer Id!!!");
        }

        public int getIndexSelectedTab() {
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


        public static void upDataTable(ArrayList<ManagerInforDTO> managerInforDTOS, DefaultTableModel modelCustomerTable, JTable tableCustomer) {
            Object[][] rowData = ManagerInforDTO.getDataOnTable(managerInforDTOS);
            ProductPanel.TablePanel.removeDataTable(modelCustomerTable);
            for (int i = 0; i < rowData.length; i++) {
                modelCustomerTable.addRow(rowData[i]);
            }
        }

    }

    class NotificationPanel extends JPanel {
        private NotificationMainPanel notificationMainPanel;
        private JScrollPane scrollPane;
        private JTextField searchField;
        private JButton searchButton;
        private CustomButton allNotify, managerNotify, customerNotify;

        public NotificationPanel() {
            setLayout(new BorderLayout());
            JPanel toolPn = new JPanel(new FlowLayout(FlowLayout.CENTER));
            toolPn.setBackground(Color.WHITE);

            allNotify = createCustomButton("All", Style.FONT_SIZE_MENU_BUTTON, Color.white, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Style.LIGHT_BlUE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 2, 15, new Dimension(120, 40));
            allNotify.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setColorSelectedButton(allNotify, customerNotify, managerNotify);


                }
            });
            managerNotify = createCustomButton("Manager", Style.FONT_SIZE_MENU_BUTTON, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Color.white, Style.LIGHT_BlUE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 2, 15, new Dimension(120, 40));
            managerNotify.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setColorSelectedButton(managerNotify, customerNotify, allNotify);


                }
            });

            customerNotify = createCustomButton("Customer", Style.FONT_SIZE_MENU_BUTTON, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Color.white, Style.LIGHT_BlUE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 2, 15, new Dimension(120, 40));
            customerNotify.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setColorSelectedButton(customerNotify, managerNotify, allNotify);

                }
            });

            toolPn.add(allNotify);
            toolPn.add(managerNotify);
            toolPn.add(customerNotify);


            searchField = createTextFieldWithPlaceholder("Search Notification", Style.FONT_TEXT_CUSTOMER, new Dimension(320, 40));
            // Thêm FocusListener để kiểm soát khi người dùng nhấn và rời khỏi JTextField
            searchField.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    // Khi người dùng nhấn vào JTextField, nếu vẫn là chữ "Search", nó sẽ biến mất
                    if (searchField.getText().equals("Search Notification")) {
                        searchField.setText("");
                        searchField.setForeground(Color.BLACK);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    // Khi người dùng rời khỏi JTextField mà chưa nhập gì, sẽ hiển thị lại chữ "Search"
                    if (searchField.getText().isEmpty()) {
                        searchField.setForeground(Color.GRAY);
                        searchField.setText("Search Notification");
                    }
                }
            });
            searchButton = new JButton();
            setStyleButton(searchButton, Style.FONT_TEXT_CUSTOMER, Style.WORD_COLOR_WHITE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, SwingConstants.CENTER, new Dimension(50, 40));
            ButtonConfig.setIconSmallButton("src/main/java/Icon/search_Icon.png", searchButton);


            toolPn.add(searchField);
            toolPn.add(searchButton);

            notificationMainPanel = new NotificationMainPanel();
            add(toolPn, BorderLayout.NORTH);
            add(notificationMainPanel, BorderLayout.CENTER);
        }

        public void setColorSelectedButton(CustomButton selected, CustomButton notSelected1, CustomButton notSelected2) {
            selected.setBackgroundColor(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
            selected.setForeground(Color.WHITE);
            notSelected1.setBackgroundColor(Color.WHITE);
            notSelected1.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
            notSelected2.setBackgroundColor(Color.WHITE);
            notSelected2.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
        }

        class NotificationMainPanel extends JPanel {

            NotificationMainPanel() {
                setLayout(new BorderLayout());

                notificationContainer = new JPanel();
                notificationContainer.setBackground(Color.WHITE);
                notificationContainer.setLayout(new BoxLayout(notificationContainer, BoxLayout.Y_AXIS));

                scrollPane = new JScrollPane(notificationContainer);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                setColorScrollPane(scrollPane, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Style.LIGHT_BlUE);

                add(scrollPane, BorderLayout.CENTER);

            }
        }

    }

    class ChangeInformationPanel extends JPanel {
        JTextField emailField, fullNameField, addressField, phoneNumField, dateOfBirthField, createDateField;
        CircularImage avatar;
        ChangeAvatarPanel changeAvatarPanel = new ChangeAvatarPanel();
        ChangeInfo changeInfo = new ChangeInfo();
        CustomButton updateBt, cancelBt, changePassBt, changeAvaBt;

        AccountController accountController = new AccountController();
        ManagerController managerController = new ManagerController();

        public ChangeInformationPanel() {
            setLayout(new BorderLayout());

            JPanel ChangePn = new JPanel(new GridLayout(1, 2));
            ChangePn.add(changeAvatarPanel);
            ChangePn.add(changeInfo);
            add(ChangePn, BorderLayout.CENTER);

            cancelBt = ButtonConfig.createCustomButton("Cancel");
            cancelBt.addActionListener(e -> cancelHandle());
            updateBt = ButtonConfig.createCustomButton("Update");
            updateBt.addActionListener(e -> updateHandle());
            changePassBt = ButtonConfig.createCustomButton("Change Password");
            changePassBt.addActionListener(e -> new ChangePasswordFrame("Manager").showVisible());

            JPanel updatePn = new JPanel(new FlowLayout(FlowLayout.CENTER));
            updatePn.add(cancelBt);
            updatePn.add(updateBt);
            updatePn.add(changePassBt);
            add(updatePn, BorderLayout.SOUTH);
            reloadData();
        }

        class ChangeAvatarPanel extends JPanel {

            public ChangeAvatarPanel() {
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                setBorder(BorderFactory.createEmptyBorder(20, 50, 30, 50));
                avatar = new CircularImage(CurrentUser.URL, 300, 300, false);
                avatar.setAlignmentX(Component.CENTER_ALIGNMENT);


                changeAvaBt = new CustomButton("Upload new image from Computer");
                changeAvaBt.setGradientColors(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Color.GREEN);
                changeAvaBt.setBackgroundColor(Style.LIGHT_BlUE);
                changeAvaBt.setBorderRadius(20);
                changeAvaBt.setPreferredSize(new Dimension(450, 40));
                changeAvaBt.setFont(Style.FONT_BUTTON_CUSTOMER);
                changeAvaBt.setHorizontalAlignment(SwingConstants.CENTER);
                changeAvaBt.setAlignmentX(Component.CENTER_ALIGNMENT);
                changeAvaBt.addActionListener(e -> {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Select an Image");

                    fileChooser.setAcceptAllFileFilterUsed(false);
                    fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif"));

                    int result = fileChooser.showOpenDialog(null);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        if (selectedFile != null && isImageFile(selectedFile)) {
                            avatar.setImage(selectedFile.getAbsolutePath());
                            JOptionPane.showMessageDialog(null, "Avatar updated successfully!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Please select a valid image file!", "Invalid File", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                });

                add(Box.createVerticalGlue());
                add(avatar);
                add(Box.createRigidArea(new Dimension(0, 50)));
                add(changeAvaBt);
                add(Box.createVerticalGlue());
            }

            private boolean isImageFile(File file) {
                String[] allowedExtensions = {"jpg", "jpeg", "png", "gif"};
                String fileName = file.getName().toLowerCase();
                for (String ext : allowedExtensions) {
                    if (fileName.endsWith("." + ext)) {
                        return true;
                    }
                }
                return false;
            }
        }

        class ChangeInfo extends JPanel {
            private final String[] labels = {
                    "Email",
                    "Name",
                    "Address",
                    "Phone Number",
                    "Date of Birth",
                    "Create Date"
            };
            private final JTextField[] editableFields = new JTextField[labels.length];
            JButton[] editButtons = new JButton[labels.length];

            public ChangeInfo() {
                setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(5, 5, 5, 5);
                gbc.fill = GridBagConstraints.HORIZONTAL;

                addTitle(gbc);
                initializeFields();

                for (int i = 0; i < labels.length; i++) {
                    gbc.gridx = 0;
                    gbc.gridy = 2 * i + 1;
                    gbc.gridwidth = 2;
                    add(new JLabel(labels[i] + ": "), gbc);

                    gbc.gridx = 0;
                    gbc.gridy = 2 * i + 2;
                    gbc.gridwidth = 1;
                    add(editableFields[i], gbc);

                    if (i != 0) {
                        gbc.gridx = 1;
                        JButton editButton = ButtonConfig.createEditFieldButton(editableFields[i]);
                        editButtons[i] = editButton;
                        add(editButton, gbc);
                    }
                }
            }

            private void initializeFields() {
                editableFields[0] = emailField = TextFieldConfig.createUneditableTextField(labels[0]);
                editableFields[1] = fullNameField = TextFieldConfig.createUneditableTextField(labels[1]);
                editableFields[2] = addressField = TextFieldConfig.createUneditableTextField(labels[2]);
                editableFields[3] = phoneNumField = TextFieldConfig.createUneditableTextField(labels[3]);
                editableFields[4] = dateOfBirthField = TextFieldConfig.createUneditableTextField(labels[4]);
                editableFields[5] = createDateField = TextFieldConfig.createUneditableTextField(labels[5]);
            }

            private void addTitle(GridBagConstraints gbc) {
                JLabel title = new JLabel("Change Your Information", SwingConstants.CENTER);
                title.setFont(Style.FONT_TITLE_PRODUCT);
                title.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.gridwidth = 2;
                add(title, gbc);
            }
        }

        private void reloadData() {
            try {
                emailField.setText(CurrentUser.MANAGER_INFOR.getEmail());
                fullNameField.setText(CurrentUser.MANAGER_INFOR.getFullName());
                addressField.setText(CurrentUser.MANAGER_INFOR.getAddress());
                phoneNumField.setText(CurrentUser.MANAGER_INFOR.getPhoneNumber());
                dateOfBirthField.setText(CurrentUser.MANAGER_INFOR.getBirthDay().toString());
                createDateField.setText(CurrentUser.MANAGER_INFOR.getCreateDate().toString());
                avatar.setImage(CurrentUser.URL);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private boolean hasNotChanged() {
            return emailField.getText().trim().equals(CurrentUser.MANAGER_INFOR.getEmail()) &&
                    fullNameField.getText().trim().equals(CurrentUser.MANAGER_INFOR.getFullName()) &&
                    addressField.getText().trim().equals(CurrentUser.MANAGER_INFOR.getAddress()) &&
                    phoneNumField.getText().trim().equals(CurrentUser.MANAGER_INFOR.getPhoneNumber()) &&
                    dateOfBirthField.getText().trim().equals(CurrentUser.MANAGER_INFOR.getBirthDay().toString()) &&
                    createDateField.getText().trim().equals(CurrentUser.MANAGER_INFOR.getCreateDate().toString()) &&
                    this.avatar.equals(new CircularImage(CurrentUser.URL, avatar.getWidth(), avatar.getHeight(), false));
        }

        private void cancelHandle() {
            if (!hasNotChanged()) {
                int response = JOptionPane.showConfirmDialog(
                        null,
                        "You have unsaved changes. Are you sure you want to cancel?",
                        "Confirm Cancel",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (response == JOptionPane.YES_OPTION) {
                    reloadData();
                    JOptionPane.showMessageDialog(null, "Changes have been canceled.", "Action Canceled", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Continue editing your changes.", "Action Resumed", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No changes to cancel.", "Action Canceled", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        private void updateHandle() {
            if (hasNotChanged()) {
                JOptionPane.showMessageDialog(
                        this,
                        "No changes detected. Please modify your information before updating.",
                        "No Updates Made",
                        JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }
            Map<JTextField, InputVerifier[]> fieldVerifierMap = new HashMap<>();
            fieldVerifierMap.put(emailField, new InputVerifier[]{new NotNullVerifier(), new EmailVerifier()});
            fieldVerifierMap.put(fullNameField, new InputVerifier[]{new NotNullVerifier(), new UserNameAccountVerifier()});
            fieldVerifierMap.put(addressField, new InputVerifier[]{new NotNullVerifier()});
            fieldVerifierMap.put(phoneNumField, new InputVerifier[]{new NotNullVerifier(), new PhoneNumberVerifier()});
            fieldVerifierMap.put(dateOfBirthField, new InputVerifier[]{new NotNullVerifier(), new BirthDayVerifier()});
            fieldVerifierMap.put(createDateField, new InputVerifier[]{new NotNullVerifier()});

            for (Map.Entry<JTextField, InputVerifier[]> entry : fieldVerifierMap.entrySet()) {
                JTextField field = entry.getKey();
                InputVerifier[] verifiers = entry.getValue();

                for (InputVerifier verifier : verifiers) {
                    if (!verifier.verify(field)) {
                        field.requestFocus();
                        return;
                    }
                }
            }

            JOptionPane.showMessageDialog(
                    this,
                    "All fields are valid. Proceeding with update...",
                    "Validation Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

            performUpdate();
        }

        private void performUpdate() {
            Account account = accountController.findById(CurrentUser.MANAGER_INFOR.getAccountId());
            account.setAvataImg(avatar.getImagePath());
            account.setEmail(emailField.getText().trim());
            account.setCreateDate(Date.valueOf(createDateField.getText().trim()));
            Manager manager = managerController.findById(CurrentUser.MANAGER_INFOR.getManagerId());
            manager.setFullName(fullNameField.getText().trim());
            manager.setAddress(addressField.getText().trim());
            manager.setBirthDay(Date.valueOf(dateOfBirthField.getText().trim()));
            manager.setPhoneNumber(phoneNumField.getText().trim());

            accountController.update(account);
            managerController.update(manager);

            ToastNotification.showToast("Your information has been successfully updated.", 2500, 50, -1, -1);
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

    public JTextField createTextFieldWithPlaceholder(String placeholderText, Font font, Dimension size) {
        JTextField field = new JTextField(placeholderText);
        field.setFont(font);
        field.setForeground(Color.GRAY);
        field.setPreferredSize(size);
        field.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1));
        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholderText)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
                field.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 4));
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(placeholderText);
                }
                field.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1));
            }
        });
        return field;
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

    // chỉnh màu cho scrollbar
    private static void setColorScrollPane(JScrollPane scrollPane, Color thumbColor, Color trackColor) {
        setColorScrollBar(scrollPane.getVerticalScrollBar(), thumbColor, trackColor);
        setColorScrollBar(scrollPane.getHorizontalScrollBar(), thumbColor, trackColor);
    }

    private static void setColorScrollBar(JScrollBar scrollBar, Color scrollBarColor, Color trackBackGroundColor) {
        scrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = scrollBarColor;
                this.trackColor = trackBackGroundColor;
            }
        });
    }

    private static CustomButton createCustomButton(String title, Font font, Color textColor, Color backgroundColor, Color hoverColor, Color borderColor, int thickness, int radius, Dimension size) {
        CustomButton bt = new CustomButton(title);
        bt.setFont(font);
        bt.setTextColor(textColor);
        bt.setBackgroundColor(backgroundColor);
        bt.setHoverColor(hoverColor);
        bt.setBorderColor(borderColor);
        bt.setBorderThickness(thickness);
        bt.setBorderRadius(radius);
        bt.setPreferredSize(size);
        return bt;
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
            table.getColumnModel().getColumn(2).setPreferredWidth(300);
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

    //
    public void addCustomerNotification(Customer customer, String text) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        JLabel timeLabel = new JLabel("<html>" + now.format(timeFormatter) + "<br>" + now.format(dateFormatter) + "</html>");

        CircularImage avatar = new CircularImage(customer.getAvataImg(), 80, 80, true);

        JLabel customerName = new JLabel(customer.getFullName());
        customerName.setFont(Style.FONT_PLAIN_25);

        JTextArea message = new JTextArea(text);
        message.setBackground(Color.WHITE);
        message.setForeground(Color.BLACK);
        message.setFont(new Font("Arial", Font.PLAIN, 16));
        message.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(20, 2, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE),
                BorderFactory.createEmptyBorder(3, 3, 3, 8)
        ));
        message.setLineWrap(true);
        message.setWrapStyleWord(true);
        message.setEditable(false);
        message.setOpaque(true);

        // Cố định chiều rộng và tính toán chiều cao phù hợp
        int width = 600;
        message.setSize(new Dimension(width, Short.MAX_VALUE));
        int preferredHeight = message.getPreferredSize().height;
        message.setPreferredSize(new Dimension(width, preferredHeight));

        // Bọc JTextArea trong JScrollPane nhưng tắt thanh cuộn ngang
        JScrollPane scrollPane = new JScrollPane(message);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JPanel textAreaPanel = new JPanel();
        textAreaPanel.setLayout(new BoxLayout(textAreaPanel, BoxLayout.Y_AXIS));
        textAreaPanel.add(scrollPane);
        textAreaPanel.setBackground(Color.WHITE);

        JPanel main = new JPanel(new GridBagLayout());
        main.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 15, 5, 15);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        main.add(avatar, gbc);

        gbc.gridx = 1;
        main.add(customerName, gbc);

        gbc.gridy = 1;
        main.add(textAreaPanel, gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        main.add(timeLabel, gbc);

        notificationContainer.add(main);
        notificationContainer.revalidate();
        notificationContainer.repaint();
    }

}
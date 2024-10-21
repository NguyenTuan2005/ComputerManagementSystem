package view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

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
    static final String[] columnNamesPRODUCT = {"Serial Number", "Product Code", "Product Name", "Quantity", "Unit Price", "Type of Device", "Brand",
            "Operating System", "CPU", "Storage", "RAM", "Origin"};
    static final String[] columnNamesSUPPLIER ={"Supplier ID:", "Name Supplier:", "Address", "Phone number:", "Email:", "Cooperation Day:"};


    //main constructor
    public ManagerMainPanel(LoginFrame loginFrame) {
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
            welcomeLabel = new JLabel();
            welcomeLabel.setFont(new Font("Arial", Font.BOLD, 60));
            welcomeLabel.setForeground(Style.BACKGROUND_COLOR);

            add(welcomeLabel, BorderLayout.CENTER);
        }
    }

    //Hoang's code
    class ProductPanel extends JPanel {
        ToolPanel toolPanel = new ToolPanel();
        JButton addBt, modifyBt, deleteBt, sortBt, exportExcelBt, importExcelBt, searchBt;
        JTextField findText;

        TablePanel tablePanel = new TablePanel();
        JTable tableProduct;
        DefaultTableModel modelProductTable;
        JTableHeader tableProductHeader;
        JScrollPane scrollPaneProductTable;
        JTabbedPane tabbedPaneProductTable;

        public ProductPanel() {
            setLayout(new BorderLayout());
            add(toolPanel, BorderLayout.NORTH);
            add(tablePanel, BorderLayout.CENTER);
        }

        public class ToolPanel extends JPanel {

            public ToolPanel() {
                setLayout(new FlowLayout());
                setLayout(new FlowLayout(FlowLayout.CENTER));
                setBackground(Style.BACKGROUND_COLOR);
                addBt = new JButton("+ Add");
                setStyleButton(addBt, Style.FONT_SIZE, Color.white, Style.ADD_BUTTON_COLOR_GREEN, SwingConstants.LEFT, new Dimension(80, 30));

                modifyBt = new JButton("Modify");
                setStyleButton(modifyBt, Style.FONT_SIZE, Color.white, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, SwingConstants.LEFT, new Dimension(80, 30));

                deleteBt = new JButton("- Delete");
                setStyleButton(deleteBt, Style.FONT_SIZE, Color.white, Style.DELETE_BUTTON_COLOR_RED, SwingConstants.LEFT, new Dimension(90, 30));

                sortBt = new JButton("Sort Asc");
                setStyleButton(sortBt, Style.FONT_SIZE, Color.white, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, SwingConstants.LEFT, new Dimension(100, 30));


                exportExcelBt = new JButton("Export Excel");
                setStyleButton(exportExcelBt, Style.FONT_SIZE, Color.white, Style.ADD_BUTTON_COLOR_GREEN, SwingConstants.LEFT, new Dimension(120, 30));

                importExcelBt = new JButton("Import Excel");
                setStyleButton(importExcelBt, Style.FONT_SIZE, Color.white, Style.ADD_BUTTON_COLOR_GREEN, SwingConstants.LEFT, new Dimension(120, 30));

                findText = new JTextField();
                findText.setPreferredSize(new Dimension(180, 30));
                findText.setFont(Style.FONT_SIZE);

                searchBt = new JButton("Search");
                setStyleButton(searchBt, Style.FONT_SIZE, Color.white, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, SwingConstants.LEFT, new Dimension(85, 30));

                add(addBt);
                add(modifyBt);
                add(deleteBt);
                add(sortBt);
                add(exportExcelBt);
                add(importExcelBt);
                add(findText);
                add(searchBt);
            }
        }

        public class TablePanel extends JPanel {
            public TablePanel() {
                setLayout(new BorderLayout());
                setBackground(Style.BACKGROUND_COLOR);

                String[] rowData = {"1", "LP20", "Laptop ASUS TUF Gaming FX516PE", String.valueOf(650), String.valueOf(23990000L), "Laptop", "China", "Window", "Intel Core i5 12450H", String.valueOf(512), "8 GB", "Vietnam"};

                tableProduct = new JTable(modelProductTable);
                tableProduct.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                tableProduct.getTableHeader().setResizingAllowed(true);
                tableProduct.setRowHeight(30);
                tableProduct.setFont(Style.FONT_TEXT_TABLE);
                // thêm model lưu dữ liệu cho bảng
                modelProductTable = new DefaultTableModel(columnNamesPRODUCT,0) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false; // Không cho phép chỉnh sửa bất kỳ ô nào
                    }
                };
                tableProduct.setModel(modelProductTable);
                resizeColumnWidth(tableProduct, 150);

                modelProductTable.addRow(rowData);

                tableProductHeader = tableProduct.getTableHeader();// chỉnh tên các cột
                tableProductHeader.setBackground(Color.LIGHT_GRAY);
                tableProductHeader.setForeground(Color.BLACK);
                tableProductHeader.setReorderingAllowed(false); // Không cho di chuyển cột khi giữ và kéo
                tableProductHeader.setFont(Style.FONT_HEADER_ROW_TABLE);
                scrollPaneProductTable = new JScrollPane(tableProduct);


                tabbedPaneProductTable = new JTabbedPane();
                tabbedPaneProductTable.setFont(Style.FONT_HEADER_ROW_TABLE);
                tabbedPaneProductTable.addTab("Products for Sale", scrollPaneProductTable);
                add(tabbedPaneProductTable, BorderLayout.CENTER);
            }

        }
    }

    // Hoang's code
    class SupplierPanel extends JPanel {

        JButton addBt, modifyBt, deleteBt, sortBt, exportExcelBt, importExcelBt, searchBt;
        JTextField findText;
        JTable table;
        DefaultTableModel model;
        JScrollPane scrollBar;

        ToolPanel toolPanel = new ToolPanel();
        TablePanel tablePanel = new TablePanel();

        public SupplierPanel() {
            setLayout(new BorderLayout());
            add(toolPanel, BorderLayout.NORTH);
            add(tablePanel, BorderLayout.CENTER);
        }

        public class ToolPanel extends JPanel {

            public ToolPanel() {
                setLayout(new FlowLayout(FlowLayout.CENTER));
                setBackground(Style.BACKGROUND_COLOR);
                addBt = new JButton("+ Add");
                setStyleButton(addBt, Style.FONT_SIZE, Color.white, Style.ADD_BUTTON_COLOR_GREEN, SwingConstants.LEFT, new Dimension(80, 30));

                modifyBt = new JButton("Modify");
                setStyleButton(modifyBt, Style.FONT_SIZE, Color.white, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, SwingConstants.LEFT, new Dimension(80, 30));

                deleteBt = new JButton("- Delete");
                setStyleButton(deleteBt, Style.FONT_SIZE, Color.white, Style.DELETE_BUTTON_COLOR_RED, SwingConstants.LEFT, new Dimension(90, 30));

                sortBt = new JButton("Sort Asc");
                setStyleButton(sortBt, Style.FONT_SIZE, Color.white, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, SwingConstants.LEFT, new Dimension(100, 30));


                exportExcelBt = new JButton("Export Excel");
                setStyleButton(exportExcelBt, Style.FONT_SIZE, Color.white, Style.ADD_BUTTON_COLOR_GREEN, SwingConstants.LEFT, new Dimension(120, 30));

                importExcelBt = new JButton("Import Excel");
                setStyleButton(importExcelBt, Style.FONT_SIZE, Color.white, Style.ADD_BUTTON_COLOR_GREEN, SwingConstants.LEFT, new Dimension(120, 30));

                findText = new JTextField();
                findText.setPreferredSize(new Dimension(180, 30));
                findText.setFont(Style.FONT_SIZE);

                searchBt = new JButton("Search");
                setStyleButton(searchBt, Style.FONT_SIZE, Color.white, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, SwingConstants.LEFT, new Dimension(85, 30));

                add(addBt);
                add(modifyBt);
                add(deleteBt);
                add(sortBt);
                add(exportExcelBt);
                add(importExcelBt);
                add(findText);
                add(searchBt);
            }
        }

        public class TablePanel extends JPanel {
            public TablePanel() {
                setBackground(Style.BACKGROUND_COLOR);
                // tao bang du lieu
                String[] header = {"STT", "Mã Nhà Cung Cấp", "Tên Nhà Cung Cấp", "Email", "Số Điện Thoại", "Địa chỉ", "Người Liên Hệ",
                        "Số Điện Thoại Người Liên Hệ", "Mã Số Thuế", "Ngày Hợp Tác"};
                model = new DefaultTableModel(header, 0);
                table = new JTable(model);


                scrollBar = new JScrollPane(table);
                scrollBar.setPreferredSize(new Dimension(1000, 550));
                this.add(scrollBar);
            }
        }
    }

    // Duy's Code
    class CustomerPanel extends JPanel {
        JButton addBt, removeBt, editBt, createExcelBt, searchBt;
        JTextField findText;
        JLabel orders;

        public CustomerPanel() {


        }
    }


    // Hoang's Code
    class InventoryPanel extends JPanel { // m
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
            ToolPanel toolPanel;
            TablePanel tablePanel;

            InventoryControlPanel() {
                setLayout(new BorderLayout());
                toolPanel = new ToolPanel();
                tablePanel = new TablePanel();


                add(toolPanel, BorderLayout.NORTH);
                add(tablePanel, BorderLayout.CENTER);
            }

            class ToolPanel extends JPanel {
                JButton importProduct, exportProduct, searchBt;
                JTextField searchTextField;

                public ToolPanel() {
                    importProduct = new JButton("Import Product");
                    importProduct.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            showPanelInInventory(IMPORT_CONSTRAINT);
                        }
                    });
                    exportProduct = new JButton("Export Product");
                    exportProduct.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            showPanelInInventory(EXPORT_CONSTRAINT);
                        }
                    });
                    add(importProduct);
                    add(exportProduct);
                    searchTextField = new JTextField("Search");
                    searchTextField.setPreferredSize(new Dimension(250, 30));
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
                    add(searchTextField);
                    searchBt = new JButton();
                    setStyleButton(searchBt, Style.FONT_TEXT_CUSTOMER, Style.WORD_COLOR_WHITE, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, SwingConstants.CENTER, new Dimension(68, 30));
                    setIconSmallButton("src/main/java/Icon/search_Icon.png", searchBt);
                    add(searchBt);
                }
            }

            class TablePanel extends JPanel {
                JTabbedPane tabbedPaneMain;

                JTable tableInventory, tableImport, tableExport;
                DefaultTableModel modelInventory, modelImport, modelExport;
                JScrollPane scrollPaneInventory, scrollPaneImport, scrollPaneExport;

                TablePanel() {
                    setLayout(new BorderLayout());
                    // tạo bảng lưu dữ liệu cho inventory
                    String[] rowData = {"1", "LP20", "Laptop ASUS TUF Gaming FX516PE", String.valueOf(650), String.valueOf(23990000L), "Laptop", "China", "Window", "Intel Core i5 12450H", String.valueOf(512), "8 GB", "Vietnam"};

                    tableInventory = new JTable();
                    scrollPaneInventory = createScrollPaneForTable(tableInventory, columnNamesPRODUCT);
                    modelInventory = (DefaultTableModel) tableInventory.getModel();
                    modelInventory.addRow(rowData);

                    tableImport = new JTable();
                    scrollPaneImport = createScrollPaneForTable(tableImport, columnNamesPRODUCT);
                    modelImport = (DefaultTableModel) tableImport.getModel();


                    tableExport = new JTable();
                    scrollPaneExport = createScrollPaneForTable(tableExport, columnNamesPRODUCT);
                    modelExport = (DefaultTableModel) tableExport.getModel();


                    tabbedPaneMain = new JTabbedPane();
                    tabbedPaneMain.setFont(Style.FONT_HEADER_ROW_TABLE);
                    tabbedPaneMain.addTab("Inventory", scrollPaneInventory);
                    tabbedPaneMain.addTab("Import Product List", scrollPaneImport);
                    tabbedPaneMain.addTab("Export Product List", scrollPaneExport);
                    add(tabbedPaneMain);
                }

                public JScrollPane createScrollPaneForTable(JTable tableThat, String[] columnNames) {
                    tableThat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                    tableThat.getTableHeader().setResizingAllowed(true);
                    tableThat.setRowHeight(30);
                    tableThat.setFont(Style.FONT_TEXT_TABLE);
                    tableThat.setModel(new DefaultTableModel(columnNames,0) {
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false; // Không cho phép chỉnh sửa
                        }
                    });
                    resizeColumnWidth(tableThat, 150);

                    JTableHeader tableHeader = tableThat.getTableHeader();// chỉnh tên các cột
                    tableHeader.setBackground(Color.LIGHT_GRAY);
                    tableHeader.setForeground(Color.BLACK);
                    tableHeader.setReorderingAllowed(false); // Không cho di chuyển cột khi giữ và kéo
                    tableHeader.setFont(Style.FONT_HEADER_ROW_TABLE);
                    return new JScrollPane(tableThat);
                }


            }
        }

        class ImportPanel extends JPanel {
            LeftPn leftPn;
            RightPn rightPn;

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

                JTextField SupplierIDTF, nameSupplierTF, addressSupplierTF, phoneSupplierTF, emailSupplierTF, cooperationDayTF,
                        productIDTF, productNameTF, amountTF, priceTF, typeTF, brandTF, operatingSystemTF, cpuTF, memoryTF, ramTF, madeInTF;

                JTextField[] supplierTFArray = {SupplierIDTF, nameSupplierTF, addressSupplierTF, phoneSupplierTF, emailSupplierTF, cooperationDayTF};
                JTextField[] productTFArray = {productIDTF, productNameTF, amountTF, priceTF, typeTF, brandTF, operatingSystemTF, cpuTF, memoryTF, ramTF, madeInTF};
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
                        gbc.insets = new Insets(5, 5, 5, 5);

                        // Dòng đầu tiên: Thêm hai nút bấm
                        clearAllBt = new JButton("Clear All");
                        clearAllBt.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                for (int i = 0; i < supplierTFArray.length; i++) {
                                    supplierTFArray[i].setText("");
                                }
                            }
                        });

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

                        // Danh sách các label
                        String[] labels = {"Supplier ID:", "Name Supplier:", "Address", "Phone number:", "Email:", "Cooperation Day:"};

                        for (int i = 0; i < labels.length; i++) {
                            gbc.gridx = 0; // Cột 1: Label
                            gbc.gridy = i + 1; // Bắt đầu từ dòng thứ 2
                            gbc.gridwidth = 1;
                            gbc.fill = GridBagConstraints.BOTH;
                            gbc.anchor = GridBagConstraints.WEST;
                            JLabel lb =new JLabel(labels[i]);
                            lb.setFont(Style.FONT_TEXT_LOGIN_FRAME);
                            lb.setPreferredSize(new Dimension(180, 40));
                            add(new JLabel(labels[i]), gbc);


                            gbc.gridx = 1; // Cột 2: TextField
                            gbc.anchor = GridBagConstraints.EAST;
                            supplierTFArray[i] = new JTextField(20);
                            supplierTFArray[i].setPreferredSize(new Dimension(300,40));
                            supplierTFArray[i].setFont(Style.FONT_TEXT_LOGIN_FRAME);
                            add(supplierTFArray[i], gbc);
                        }


                    }
                }

                class AddSupplierFromListPn extends JPanel {
                    JTable tableGetDataSupplier;
                    DefaultTableModel modelGetDataSupplier;
                    JTableHeader tableHeaderGetDataSupplier;
                    JScrollPane scrollPaneGetDataSupplierTable;
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

                        // thêm bảng chứa dữ liệu cảu supplier vào để trích xuất ra form

                        tableGetDataSupplier = new JTable();
                        tableGetDataSupplier.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                        tableGetDataSupplier.getTableHeader().setResizingAllowed(true);
                        tableGetDataSupplier.setRowHeight(30);
                        tableGetDataSupplier.setFont(Style.FONT_TEXT_TABLE);
                        resizeColumnWidth(tableGetDataSupplier, 120);
                        // tạo model lưu dữ liệu cho bảng
                        modelGetDataSupplier = new DefaultTableModel(columnNamesSUPPLIER,0) {
                            @Override
                            public boolean isCellEditable(int row, int column) {
                                return false; // Không cho phép chỉnh sửa bất kỳ ô nào
                            }
                        };
                        tableGetDataSupplier.setModel(modelGetDataSupplier);

                        tableHeaderGetDataSupplier = tableGetDataSupplier.getTableHeader();// chỉnh tên các cột
                        tableHeaderGetDataSupplier.setBackground(Color.LIGHT_GRAY);
                        tableHeaderGetDataSupplier.setForeground(Color.BLACK);
                        tableHeaderGetDataSupplier.setReorderingAllowed(false); // Không cho di chuyển cột khi giữ và kéo
                        tableHeaderGetDataSupplier.setFont(Style.FONT_HEADER_ROW_TABLE);
                        scrollPaneGetDataSupplierTable = new JScrollPane(tableGetDataSupplier);
                        gbc.gridx = 0; // Quay lại cột đầu tiên
                        gbc.gridy = 1; // Dòng thứ hai
                        gbc.gridwidth = 2; // JTable chiếm cả 2 cột
                        gbc.fill = GridBagConstraints.BOTH; // JTable lấp đầy cả chiều rộng và chiều cao
                        gbc.weightx = 1.0; // Phân bổ chiều rộng cho JTable
                        gbc.weighty = 1.0;
                        add(scrollPaneGetDataSupplierTable, gbc);

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
                ImportProductList importProductList;
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
                    setLayout(new BorderLayout());
                    importProductList = new ImportProductList();
                    add(importProductList, BorderLayout.CENTER);

                }
                class ImportProductList extends JPanel{
                    JTable tableImportProductList;
                    DefaultTableModel modelImportProductList;
                    JTableHeader tableHeaderImportProductList;
                    JScrollPane scrollPaneImportProductList;
                    ImportProductList(){
                        tableImportProductList = new JTable();
                        tableImportProductList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                        tableImportProductList.getTableHeader().setResizingAllowed(true);
                        tableImportProductList.setRowHeight(20);
                        tableImportProductList.setFont(Style.FONT_TEXT_TABLE);
                        resizeColumnWidth(tableImportProductList, 120);
                        // tạo model lưu dữ liệu cho bảng
                        modelImportProductList = new DefaultTableModel(columnNamesSUPPLIER,0) {
                            @Override
                            public boolean isCellEditable(int row, int column) {
                                return false; // Không cho phép chỉnh sửa bất kỳ ô nào
                            }
                        };
                        tableImportProductList.setModel(modelImportProductList);

                        tableHeaderImportProductList = tableImportProductList.getTableHeader();// chỉnh tên các cột
                        tableHeaderImportProductList.setBackground(Color.LIGHT_GRAY);
                        tableHeaderImportProductList.setForeground(Color.BLACK);
                        tableHeaderImportProductList.setReorderingAllowed(false); // Không cho di chuyển cột khi giữ và kéo
                        tableHeaderImportProductList.setFont(Style.FONT_HEADER_ROW_TABLE);
                        scrollPaneImportProductList = new JScrollPane(tableImportProductList);
                        add(scrollPaneImportProductList, BorderLayout.CENTER);

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

        public AccManagementPanel() {
            setLayout(new BorderLayout());

            ButtonPanel buttonPanel = new ButtonPanel();
            TablePanel tablePanel = new TablePanel();

            add(buttonPanel, BorderLayout.NORTH);
            add(tablePanel, BorderLayout.CENTER);
        }

        // Inner class for button panel
        class ButtonPanel extends JPanel {
            JButton addButton, editButton, deleteButton;
            JButton sortButton, exportButton, importButton;
            JTextField searchField;
            JComboBox<String> filterCombo;

            public ButtonPanel() {
                setBackground(Style.BACKGROUND_COLOR);
                setBorder(BorderFactory.createLineBorder(Style.WORD_COLOR_WHITE, 10));
                setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
                setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

                addButton = new JButton("Add");
                deleteButton = new JButton("Delete");
                editButton = new JButton("Edit");
                sortButton = new JButton("Sort");
                exportButton = new JButton("Export Excel");
                importButton = new JButton("Import Excel");

                add(addButton);
                add(deleteButton);
                add(editButton);
                add(Box.createHorizontalStrut(10)); // Add some spacing
                add(sortButton);
                add(Box.createHorizontalStrut(10));
                add(exportButton);
                add(importButton);

                filterCombo = new JComboBox<>(new String[]{"All"});
                filterCombo.setBackground(Style.WORD_COLOR_WHITE);
                filterCombo.setPreferredSize(new Dimension(60, 30));
                searchField = new JTextField(15);
                searchField.setPreferredSize(new Dimension(130, 30));

                JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                rightPanel.setBackground(Style.BACKGROUND_COLOR);
                rightPanel.add(filterCombo);
                rightPanel.add(searchField);

                add(Box.createHorizontalGlue());
                add(rightPanel);
            }
        }

        // Inner class for table panel
        class TablePanel extends JPanel {
            JTable table;
            JScrollPane scrollPane;

            public TablePanel() {
                setLayout(new BorderLayout());

                String[] columnNames = {"Username", "Role", "Phone Number", "Email", "Created Date"};
                DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false; // Make table non-editable
                    }
                };

                table = new JTable(model);
                table.setFillsViewportHeight(true);
                table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

                TableColumnModel columnModel = table.getColumnModel();
                columnModel.getColumn(0).setPreferredWidth(150); // Username
                columnModel.getColumn(1).setPreferredWidth(100); // Role
                columnModel.getColumn(2).setPreferredWidth(120); // Phone
                columnModel.getColumn(3).setPreferredWidth(200); // Email
                columnModel.getColumn(4).setPreferredWidth(150); // Created Date

                scrollPane = new JScrollPane(table);
                scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                add(scrollPane, BorderLayout.CENTER);
            }
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
        }
    }

    private void setIconSmallButton(String url, JButton that) {
        ImageIcon iconButton = new ImageIcon(url);
        Image image = iconButton.getImage(); // Lấy Image từ ImageIcon
        Dimension buttonSize = that.getPreferredSize();
        Image resizedImage = image.getScaledInstance(buttonSize.height - 10, buttonSize.height - 10, java.awt.Image.SCALE_SMOOTH); // Resize
        that.setIcon(new ImageIcon(resizedImage));
    }
}

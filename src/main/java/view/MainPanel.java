package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainPanel extends JPanel {
    CardLayout cardLayout = new CardLayout();
    WelcomePanel welcomePanel = new WelcomePanel();
    ProductPanel productPanel = new ProductPanel();
    SupplierPanel supplierPanel = new SupplierPanel();
    CustomerPanel customerPanel = new CustomerPanel();
    ImportPanel importPanel = new ImportPanel();
    ExportPanel exportPanel = new ExportPanel();
    AccManagePanel accManagePanel = new AccManagePanel();
    NotificationPanel notificationPanel = new NotificationPanel();
    ChangeInformationPanel changeInformationPanel = new ChangeInformationPanel();
    LogOutPanel logOutPanel = new LogOutPanel();

    static final String WELCOME_CONSTRAINT = "welcome";
    static final String PRODUCT_CONSTRAINT = "product";
    static final String SUPPLIER_CONSTRAINT = "supplier";
    static final String CUSTOMER_CONSTRAINT = "customer";
    static final String IMPORT_CONSTRAINT = "import";
    static final String EXPORT_CONSTRAINT = "export";
    static final String ACC_MANAGEMENT_CONSTRAINT = "accManagement";
    static final String NOTIFICATION_CONSTRAINT = "notification";
    static final String CHANGE_INFORMATION_CONSTRAINT ="changeInformation";
    static final String LOG_OUT_CONSTRAINT ="logOut";
    public MainPanel() {
        setLayout(cardLayout);
        add(welcomePanel, WELCOME_CONSTRAINT);
        add(productPanel, PRODUCT_CONSTRAINT);
        add(supplierPanel, SUPPLIER_CONSTRAINT);
        add(customerPanel, CUSTOMER_CONSTRAINT);
        add(importPanel, IMPORT_CONSTRAINT);
        add(exportPanel, EXPORT_CONSTRAINT);
        add(accManagePanel, ACC_MANAGEMENT_CONSTRAINT);
        add(notificationPanel, NOTIFICATION_CONSTRAINT);
        add(changeInformationPanel,CHANGE_INFORMATION_CONSTRAINT);
        add(logOutPanel,LOG_OUT_CONSTRAINT);
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

            welcomeLabel = new JLabel("Welcome Manager :)", SwingConstants.CENTER);
            welcomeLabel.setFont(new Font("Arial", Font.BOLD, 60));
            welcomeLabel.setForeground(Style.BACKGROUND_COLOR);

            add(welcomeLabel, BorderLayout.CENTER);
        }
    }

    class ProductPanel extends JPanel {
        JLabel product = new JLabel("Sản phẩm");
        JButton addBt, modifyBt, deleteBt, sortAscBt, sortDecBt, exportExcelBt, importExcelBt, findBt;
        JTextField findText;
        JTable table;
        DefaultTableModel model;
        JScrollPane scrollBar;

        ToolPanel toolPanel = new ToolPanel();
        TablePanel tablePanel = new TablePanel();

        public ProductPanel() {
            setLayout(new BorderLayout());
            add(toolPanel, BorderLayout.NORTH);
            add(tablePanel, BorderLayout.CENTER);
        }

        public class ToolPanel extends JPanel {

            public ToolPanel() {
                setLayout(new FlowLayout());
                addBt = new JButton("Thêm");

                modifyBt = new JButton("Sửa");
                deleteBt = new JButton("Xóa");
                exportExcelBt = new JButton("Xuất Excel");
                importExcelBt = new JButton("Nhập Excel");
                findBt = new JButton("Tìm");

                findText = new JTextField();

                findText.setPreferredSize(new Dimension(200, 30));

                add(addBt);
                add(modifyBt);
                add(deleteBt);
                add(exportExcelBt);
                add(importExcelBt);
                add(findBt);
                add(findText);

            }
        }

        public class TablePanel extends JPanel {
            public TablePanel() {
                // tao bang du lieu
                String[] header = {"STT", "Mã Sản Phẩm", "Tên Sản Phẩm", "Số Lượng", " Đơn Giá", "Loại Máy", "Thương Hiệu",
                        "Hệ Điều Hành", "CPU", "Bộ Nhớ", "RAM", "Xuất Xứ"};
                model = new DefaultTableModel(header, 0);
                table = new JTable(model);
                scrollBar = new JScrollPane(table);
                scrollBar.setPreferredSize(new Dimension(1000, 550));
                this.add(scrollBar);
            }
        }
    }

    class SupplierPanel extends JPanel {
        JLabel supplierLabel = new JLabel("Quản lý nhà cung cấp");

        public SupplierPanel() {
            setLayout(new BorderLayout());
            supplierLabel.setBackground(Color.GRAY);

            add(supplierLabel, BorderLayout.CENTER);
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

    class ImportPanel extends JPanel {

        JButton addBt;
        JButton deleteBt;
        JButton editBt;
        JButton detailsBt;
        JButton exportBt;
        JButton searchBt;
        JButton importBt;
        JTextField findText;

        private static void setStyleButton(JButton that, Color color, Dimension size ) {
            that.setBackground(color);
            that.setFont(Style.FONT_SIZE);
            that.setHorizontalAlignment(SwingConstants.LEFT);
            that.setBorderPainted(false);
            that.setForeground(Color.white);
            that.setFocusable(false);
            that.setPreferredSize(size);
        }

        public ImportPanel() {
            // Set layout for the panel
            setLayout(new BorderLayout());

            // Panel for buttons
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            buttonPanel.setBackground(Style.BACKGROUND_COLOR);
            addBt = new JButton("+ Thêm Mới");
            setStyleButton(addBt,Color.GREEN , new Dimension(120, 30));

            deleteBt = new JButton("- Xóa Đơn");
            setStyleButton(deleteBt, Color.red,new Dimension(120, 30));

            editBt = new JButton("Sửa Đơn");
            setStyleButton(editBt , Color.CYAN ,new Dimension(100, 30));

            detailsBt = new JButton("Thông tin chi tiết");
            setStyleButton(detailsBt , Color.CYAN,new Dimension(150, 30));

            exportBt = new JButton("Export Excel");
            setStyleButton(exportBt, new Color(0,0,0),new Dimension(150, 30));

            searchBt = new JButton("Search");
            setStyleButton(searchBt, new Color(0,0,0),new Dimension(100, 30));

            importBt = new JButton("Import From Excel");
            setStyleButton(importBt, new Color(0,0,0), new Dimension(170, 30));

            findText = new JTextField("search", 20);
            findText.setPreferredSize(new Dimension(200, 30)); // chiều rộng 200, chiều cao 30

            // Đặt căn chỉnh văn bản (có thể là SwingConstants.LEFT, SwingConstants.CENTER, SwingConstants.RIGHT)
            findText.setHorizontalAlignment(JTextField.LEFT); // Căn giữa văn bản

            // Add buttons to the button panel
            buttonPanel.add(addBt);
            buttonPanel.add(deleteBt);
            buttonPanel.add(editBt);
            buttonPanel.add(detailsBt);
            buttonPanel.add(importBt);
            buttonPanel.add(exportBt);
            buttonPanel.add(searchBt);
            buttonPanel.add(findText);
//            buttonPanel.setBorder(BorderFactory.createTitledBorder("APPLICATION"));

            // Add button panel to the top of the main panel
            add(buttonPanel, BorderLayout.NORTH);

            // Table to display order list
            String[] columnNames = {
                    "Mã Phiếu Nhập", "Tên Đơn Hàng", "Mã Nhà Cung Cấp",
                    "Tên Nhà Cung Cấp", "Người Nhập", "Tình Trạng"
            };

            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
            JTable orderTable = new JTable(tableModel);

            // Add sample rows (optional)
            // Object[] rowData = { "001", "Order1", "NC001", "Supplier1", "User1", "Pending" };
            // tableModel.addRow(rowData);

            // ScrollPane for the table
            JScrollPane scrollPane = new JScrollPane(orderTable);

            // Label for the order list
//            JLabel orderListLabel = new JLabel("Danh Sách Đơn Hàng", JLabel.CENTER);
//            orderListLabel.setFont(new Font("Arial", Font.BOLD, 14));

            // Add components to the main panel
//            add(orderListLabel, BorderLayout.CENTER);
            add(scrollPane, BorderLayout.CENTER);
        }
    }

    // Tuan's Code
    class ExportPanel extends JPanel {
        JLabel orderIdLabel, staffLabel, customerIdLabel, customerNameLabel, totalLabel;
        JTextField orderIdField, staffField, customerIdField, customerNameField;
        JButton exportButton;

        public ExportPanel() {
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createLineBorder(Color.GRAY));

            JPanel rightContainer = new JPanel(new BorderLayout());
            rightContainer.setPreferredSize(new Dimension(300, 400)); // Adjust size as needed

            InputPanel inputPanel = new InputPanel();
            BottomPanel bottomPanel = new BottomPanel();

            rightContainer.add(inputPanel, BorderLayout.CENTER);
            rightContainer.add(bottomPanel, BorderLayout.SOUTH);

            JSeparator separator = new JSeparator(JSeparator.VERTICAL);
            separator.setPreferredSize(new Dimension(1, getHeight()));

            JPanel centerPanel = new JPanel(new BorderLayout());
            centerPanel.add(separator, BorderLayout.EAST);

            add(centerPanel, BorderLayout.CENTER);
            add(rightContainer, BorderLayout.EAST);
        }

        // Inner class for the input fields
        class InputPanel extends JPanel {

            public InputPanel() {
                setLayout(new GridLayout(8, 1, 10, 10));
                setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                // Initialize components
                orderIdLabel = new JLabel("Mã phiếu xuất");
                staffLabel = new JLabel("Người xuất hàng");
                customerIdLabel = new JLabel("Mã khách hàng");
                customerNameLabel = new JLabel("Tên Khách Hàng");

                orderIdField = new JTextField();
                staffField = new JTextField();
                customerIdField = new JTextField();
                customerNameField = new JTextField();

                JPanel orderLabelPanel = new JPanel(new BorderLayout());
                orderLabelPanel.add(orderIdLabel, BorderLayout.SOUTH);
                orderLabelPanel.setOpaque(false);

                JPanel staffLabelPanel = new JPanel(new BorderLayout());
                staffLabelPanel.add(staffLabel, BorderLayout.SOUTH);
                staffLabelPanel.setOpaque(false);

                JPanel customerIdLabelPanel = new JPanel(new BorderLayout());
                customerIdLabelPanel.add(customerIdLabel, BorderLayout.SOUTH);
                customerIdLabelPanel.setOpaque(false);

                JPanel customerNameLabelPanel = new JPanel(new BorderLayout());
                customerNameLabelPanel.add(customerNameLabel, BorderLayout.SOUTH);
                customerNameLabelPanel.setOpaque(false);

                // Add components to panel
                add(orderLabelPanel);
                add(orderIdField);
                add(staffLabelPanel);
                add(staffField);
                add(customerIdLabelPanel);
                add(customerIdField);
                add(customerNameLabelPanel);
                add(customerNameField);
            }
        }

        class BottomPanel extends JPanel {

            public BottomPanel() {
                setPreferredSize(new Dimension(300, 200));
                setLayout(new GridLayout(2, 1, 5, 5));
                setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                totalLabel = new JLabel("Tổng Tiền: +10,000,000,000 VNĐ");
                totalLabel.setForeground(new Color(0, 150, 200));

                exportButton = new JButton("XUẤT HÀNG");
                exportButton.setBackground(new Color(0, 128, 0));
                exportButton.setForeground(Color.WHITE);

                JPanel totalLabelPanel = new JPanel(new BorderLayout());
                totalLabelPanel.add(totalLabel, BorderLayout.SOUTH);
                totalLabelPanel.setOpaque(false);

                add(totalLabelPanel);
                add(exportButton);
            }
        }
    }

    class AccManagePanel extends JPanel {
        public AccManagePanel() {
        }
    }

    class NotificationPanel extends JPanel {
        public NotificationPanel() {
        }
    }

    //nut cuoi
    class ChangeInformationPanel extends JPanel{
        public ChangeInformationPanel() {
        }
    }
    class LogOutPanel extends JPanel{
        public LogOutPanel() {
        }
    }
}

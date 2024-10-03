package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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

    public MainPanel() {
        setLayout(cardLayout);
        add(welcomePanel, "welcome");
        add(productPanel, "product");
        add(supplierPanel, "supplier");
        add(customerPanel, "customer");
        add(importPanel, "import");
        add(exportPanel, "export");
        add(accManagePanel, "accManage");
        add(notificationPanel, "notification");
        cardLayout.show(this, "welcome");
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
            welcomeLabel.setForeground(new Color(0, 128, 255, 150));

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
        public CustomerPanel() {
        }
    }

    class ImportPanel extends JPanel {
        public ImportPanel() {
        }
    }

    // Tuan's Code
    class ExportPanel extends JPanel {
        public ExportPanel() {
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
}

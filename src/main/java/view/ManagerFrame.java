package view;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerFrame extends JFrame {
    ManagerMainPanel managerMainPanel;
    ManagerMenuPanel managerMenuPanel;

    public ManagerFrame(LoginFrame loginFrame) {
        setTitle("Computer Management");
        setSize(1200, 650);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setIconImage(new ImageIcon("src/main/java/Icon/logo.png").getImage());

        managerMenuPanel =new ManagerMenuPanel(loginFrame,this);
        managerMainPanel = new ManagerMainPanel(loginFrame);

        add(managerMenuPanel, BorderLayout.WEST);
        add(managerMainPanel, BorderLayout.CENTER);
        // thêm sự kiện cho các nút để chuyển màn hình tương tác ở menu
        managerMenuPanel.setProductButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                managerMainPanel.showPanel(ManagerMainPanel.PRODUCT_CONSTRAINT);
                setHover(ManagerMainPanel.PRODUCT_CONSTRAINT);
            }
        });
        managerMenuPanel.setSupplierButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                managerMainPanel.showPanel(ManagerMainPanel.SUPPLIER_CONSTRAINT);
                setHover(ManagerMainPanel.SUPPLIER_CONSTRAINT);
            }
        });

        managerMenuPanel.setImportButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                managerMainPanel.showPanel(ManagerMainPanel.IMPORT_CONSTRAINT);
                setHover(ManagerMainPanel.IMPORT_CONSTRAINT);
            }
        });

        managerMenuPanel.setCustomerButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                managerMainPanel.showPanel(ManagerMainPanel.CUSTOMER_CONSTRAINT);
                setHover(ManagerMainPanel.CUSTOMER_CONSTRAINT);
            }
        });

        managerMenuPanel.setStockExportButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                managerMainPanel.showPanel(ManagerMainPanel.EXPORT_CONSTRAINT);
                setHover(ManagerMainPanel.EXPORT_CONSTRAINT);

            }
        });

        managerMenuPanel.setAccountManagementButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                managerMainPanel.showPanel(ManagerMainPanel.ACC_MANAGEMENT_CONSTRAINT);
                setHover(ManagerMainPanel.ACC_MANAGEMENT_CONSTRAINT);
            }
        });

        managerMenuPanel.setNotificationButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                managerMainPanel.showPanel(ManagerMainPanel.NOTIFICATION_CONSTRAINT);
                setHover(ManagerMainPanel.NOTIFICATION_CONSTRAINT);
            }
        });

        managerMenuPanel.setChangeInformButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                managerMainPanel.showPanel(ManagerMainPanel.CHANGE_INFORMATION_CONSTRAINT);
                setHover(ManagerMainPanel.CHANGE_INFORMATION_CONSTRAINT);
            }
        });
        managerMenuPanel.setLogoutButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int confirmed = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to logout?", "Logout Confirmation",
                        JOptionPane.YES_NO_OPTION);

                if (confirmed == JOptionPane.YES_OPTION) {
                    setVisible(false);
                    loginFrame.setVisible(true);
                }

            }


        });

        setVisible(true);
    }
    // setHover tạo hiệu ứng đổi màu cho nút khi nhấn chuột vào
    private void setHover(String panelName){
        managerMenuPanel.productBt.setBackground( panelName.equals(ManagerMainPanel.PRODUCT_CONSTRAINT) ?Style.MENU_BUTTON_COLOR_GREEN:Style.BACKGROUND_COLOR);
        managerMenuPanel.supplierBt.setBackground(panelName.equals(ManagerMainPanel.SUPPLIER_CONSTRAINT) ?Style.MENU_BUTTON_COLOR_GREEN:Style.BACKGROUND_COLOR);
        managerMenuPanel.customerBt.setBackground(panelName.equals(ManagerMainPanel.CUSTOMER_CONSTRAINT) ?Style.MENU_BUTTON_COLOR_GREEN:Style.BACKGROUND_COLOR);
        managerMenuPanel.stockImportBt.setBackground(panelName.equals(ManagerMainPanel.IMPORT_CONSTRAINT) ?Style.MENU_BUTTON_COLOR_GREEN:Style.BACKGROUND_COLOR);
        managerMenuPanel.stockExportBt.setBackground(panelName.equals(ManagerMainPanel.EXPORT_CONSTRAINT) ?Style.MENU_BUTTON_COLOR_GREEN:Style.BACKGROUND_COLOR);
        managerMenuPanel.accountManagementBt.setBackground(panelName.equals(ManagerMainPanel.ACC_MANAGEMENT_CONSTRAINT) ?Style.MENU_BUTTON_COLOR_GREEN:Style.BACKGROUND_COLOR);
        managerMenuPanel.notificationBt.setBackground(panelName.equals(ManagerMainPanel.NOTIFICATION_CONSTRAINT) ?Style.MENU_BUTTON_COLOR_GREEN:Style.BACKGROUND_COLOR);
        managerMenuPanel.changeInformBt.setBackground(panelName.equals(ManagerMainPanel.CHANGE_INFORMATION_CONSTRAINT) ?Style.MENU_BUTTON_COLOR_GREEN:Style.BACKGROUND_COLOR);

    }
//    public static void main(String[] args) {
//        new ManagerFrame(new LoginFrame());
//    }
}

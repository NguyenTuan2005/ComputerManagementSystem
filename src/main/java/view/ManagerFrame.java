package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerFrame extends JFrame {
    ManagerMainPanel managerMainPanel;
    ManagerMenuPanel managerMenuPanel;

    public ManagerFrame() {
        setTitle("Computer Management");
        setSize(1200, 650);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setIconImage(new ImageIcon("src/main/java/Icon/logo.png").getImage());


        managerMenuPanel = new ManagerMenuPanel();
        managerMainPanel = new ManagerMainPanel();
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

        managerMenuPanel.setCustomerButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                managerMainPanel.showPanel(ManagerMainPanel.CUSTOMER_CONSTRAINT);
                setHover(ManagerMainPanel.CUSTOMER_CONSTRAINT);
            }
        });

        managerMenuPanel.setOrderButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                managerMainPanel.showPanel(ManagerMainPanel.ORDER_CONSTRAINT);
                setHover(ManagerMainPanel.ORDER_CONSTRAINT);
            }
        });

        managerMenuPanel.setInventoryButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                managerMainPanel.showPanel(ManagerMainPanel.INVENTORY_CONSTRAINT);
                setHover(ManagerMainPanel.INVENTORY_CONSTRAINT);
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
                managerMainPanel.reloadNotification();
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

        managerMenuPanel.setswitchToLoginListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to switch to Login Screen?", "Switch to Login Screen",
                        JOptionPane.YES_NO_OPTION);

                if (confirmed == JOptionPane.YES_OPTION) {
                    new LoginFrame();
                }
            }
        });

        managerMenuPanel.setLogoutButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to logout?", "Logout Confirmation",
                        JOptionPane.YES_NO_OPTION);

                if (confirmed == JOptionPane.YES_OPTION) {
                    dispose();
                    new LoginFrame();
                }
            }
        });
        setVisible(true);
    }
    // setHover tạo hiệu ứng đổi màu cho nút khi nhấn chuột vào
    private void setHover(String panelName) {
        JButton[] buttons = {
                managerMenuPanel.productBt,
                managerMenuPanel.supplierBt,
                managerMenuPanel.customerBt,
                managerMenuPanel.orderBt,
                managerMenuPanel.inventoryBt,
                managerMenuPanel.accountManagementBt,
                managerMenuPanel.notificationBt,
                managerMenuPanel.changeInformBt
        };
        String[] constraints = {
                ManagerMainPanel.PRODUCT_CONSTRAINT,
                ManagerMainPanel.SUPPLIER_CONSTRAINT,
                ManagerMainPanel.CUSTOMER_CONSTRAINT,
                ManagerMainPanel.ORDER_CONSTRAINT,
                ManagerMainPanel.INVENTORY_CONSTRAINT,
                ManagerMainPanel.ACC_MANAGEMENT_CONSTRAINT,
                ManagerMainPanel.NOTIFICATION_CONSTRAINT,
                ManagerMainPanel.CHANGE_INFORMATION_CONSTRAINT
        };
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setBackground(panelName.equals(constraints[i]) ? Style.MENU_BUTTON_COLOR_GREEN : Style.BACKGROUND_COLOR);
        }
    }

}

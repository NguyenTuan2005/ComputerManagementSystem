package view;

import Config.CurrentUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CustomerFrame extends JFrame {
    CustomerMainPanel customerMainPanel;
    CustomerMenuPanel customerMenuPanel;

    public CustomerFrame() throws SQLException {
//    public CustomerFrame(LoginFrame loginFrame) {
        setTitle("Computer Management");
        setSize(1250, 650);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("src/main/java/Icon/logo.png").getImage());

        customerMenuPanel = new CustomerMenuPanel();
        customerMainPanel = new CustomerMainPanel();
        add(customerMenuPanel, BorderLayout.WEST);
        add(customerMainPanel, BorderLayout.CENTER);

        //thêm sự kiện cho các nút để chuyển màn hình tương tác ở menu
        customerMenuPanel.setProductCatalogBtListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customerMainPanel.showPanel(CustomerMainPanel.PRODUCT_CATALOG_CONSTRAINT);
                setHover(CustomerMainPanel.PRODUCT_CATALOG_CONSTRAINT);
            }
        });

        customerMenuPanel.setPurchasedBtListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customerMainPanel.showPanel(CustomerMainPanel.PURCHASED_CONSTRAINT);
                setHover(CustomerMainPanel.PURCHASED_CONSTRAINT);
            }
        });

        customerMenuPanel.setNotificationBtListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customerMainPanel.showPanel(CustomerMainPanel.NOTIFICATION_CONSTRAINT);
                setHover(CustomerMainPanel.NOTIFICATION_CONSTRAINT);
            }
        });

        customerMenuPanel.setChangeInfoBtListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customerMainPanel.showPanel(CustomerMainPanel.CHANGE_INFORMATION_CONSTRAINT);
                setHover(CustomerMainPanel.CHANGE_INFORMATION_CONSTRAINT);
            }
        });
        // thêm sự kiện cho nút logout
        customerMenuPanel.setLogoutBtListener(new ActionListener() {
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
                customerMenuPanel.productCatalogBt,
                customerMenuPanel.orderHistoryBt,
                customerMenuPanel.notificationBt,
                customerMenuPanel.changeInfoBt

        };
        String[] constraints = {
                CustomerMainPanel.PRODUCT_CATALOG_CONSTRAINT,
                CustomerMainPanel.PURCHASED_CONSTRAINT,
                CustomerMainPanel.NOTIFICATION_CONSTRAINT,
                CustomerMainPanel.CHANGE_INFORMATION_CONSTRAINT

        };
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setBackground(panelName.equals(constraints[i]) ? Style.MENU_BUTTON_COLOR_GREEN : Style.BACKGROUND_COLOR);
        }
    }

    public static void main(String[] args) throws SQLException {
        new CustomerFrame();
    }
}

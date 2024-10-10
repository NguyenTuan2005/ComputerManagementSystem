package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserFrame extends JFrame {
    UserMainPanel userMainPanel;
    UserMenuPanel userMenuPanel;
    public UserFrame(LoginFrame loginFrame){
        setTitle("Computer Management");
        setSize(1200, 650);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("src/main/java/Icon/logo.png").getImage());

        userMenuPanel =new UserMenuPanel(loginFrame,this);
        userMainPanel = new UserMainPanel(loginFrame);
        add(userMenuPanel, BorderLayout.WEST);
        add(userMainPanel, BorderLayout.CENTER);

        //thêm sự kiện cho các nút để chuyển màn hình tương tác ở menu
        userMenuPanel.setProductCatalogBtListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userMainPanel.showPanel(UserMainPanel.PRODUCT_CATALOG_CONSTRAINT);
                setHover(UserMainPanel.PRODUCT_CATALOG_CONSTRAINT);
            }
        });

        userMenuPanel.setPurchasedBtListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userMainPanel.showPanel(UserMainPanel.PURCHASED_CONSTRAINT);
                setHover(UserMainPanel.PURCHASED_CONSTRAINT);
            }
        });
        userMenuPanel.setNotificationBtListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userMainPanel.showPanel(UserMainPanel.NOTIFICATION_CONSTRAINT);
                setHover(UserMainPanel.NOTIFICATION_CONSTRAINT);
            }
        });
        // thêm sự kiện cho nút logout
        userMenuPanel.setLogoutBtListener(new ActionListener() {
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
    private void setHover(String panelName){
        userMenuPanel.productCatalogBt.setBackground( panelName.equals(UserMainPanel.PRODUCT_CATALOG_CONSTRAINT) ?Style.MENU_BUTTON_COLOR_GREEN:Style.BACKGROUND_COLOR);


    }
}

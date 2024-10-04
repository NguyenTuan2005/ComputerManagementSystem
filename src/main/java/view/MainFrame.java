package view;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    MainPanel mainPanel = new MainPanel();
    MenuPanel menuPanel = new MenuPanel();


    // setHover tạo hiệu ứng đổi màu cho nút khi nhấn chuột vào
    private void setHover(String panelName){
        menuPanel.productBt.setBackground( panelName.equals(MainPanel.PRODUCT_CONSTRAINT) ?Style.MENU_BUTTON_COLOR_GREEN:Style.BACKGROUND_COLOR);
        menuPanel.supplierBt.setBackground(panelName.equals(MainPanel.SUPPLIER_CONSTRAINT) ?Style.MENU_BUTTON_COLOR_GREEN:Style.BACKGROUND_COLOR);
        menuPanel.customerBt.setBackground(panelName.equals(MainPanel.CUSTOMER_CONSTRAINT) ?Style.MENU_BUTTON_COLOR_GREEN:Style.BACKGROUND_COLOR);
        menuPanel.stockImportBt.setBackground(panelName.equals(MainPanel.IMPORT_CONSTRAINT) ?Style.MENU_BUTTON_COLOR_GREEN:Style.BACKGROUND_COLOR);
        menuPanel.stockExportBt.setBackground(panelName.equals(MainPanel.EXPORT_CONSTRAINT) ?Style.MENU_BUTTON_COLOR_GREEN:Style.BACKGROUND_COLOR);
        menuPanel.accountManagementBt.setBackground(panelName.equals(MainPanel.ACCMANAGE_CONSTRAINT) ?Style.MENU_BUTTON_COLOR_GREEN:Style.BACKGROUND_COLOR);
        menuPanel.notificationBt.setBackground(panelName.equals(MainPanel.NOTIFICATION_CONSTRAINT) ?Style.MENU_BUTTON_COLOR_GREEN:Style.BACKGROUND_COLOR);
        menuPanel.changeInformBt.setBackground(panelName.equals(MainPanel.CHANGE_INFORMATION_CONSTRAINT) ?Style.MENU_BUTTON_COLOR_GREEN:Style.BACKGROUND_COLOR);
        menuPanel.logoutBt.setBackground(panelName.equals(MainPanel.LOG_OUT_CONSTRAINT) ?Style.MENU_BUTTON_COLOR_GREEN:Style.BACKGROUND_COLOR);
    }

    public MainFrame() {
        setTitle("Computer Management");
        setSize(1200, 650);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setIconImage(new ImageIcon("src/main/java/Icon/logo.png").getImage());
        menuPanel.setProductButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.showPanel(MainPanel.PRODUCT_CONSTRAINT);
                setHover(MainPanel.PRODUCT_CONSTRAINT);
            }
        });
        menuPanel.setSupplierButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.showPanel(MainPanel.SUPPLIER_CONSTRAINT);
                setHover(MainPanel.SUPPLIER_CONSTRAINT);
            }
        });

        menuPanel.setImportButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.showPanel(MainPanel.IMPORT_CONSTRAINT);
                setHover(MainPanel.IMPORT_CONSTRAINT);
            }
        });

        menuPanel.setCustomerButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.showPanel(MainPanel.CUSTOMER_CONSTRAINT);
                setHover(MainPanel.CUSTOMER_CONSTRAINT);
            }
        });

        menuPanel.setStockExportButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.showPanel(MainPanel.EXPORT_CONSTRAINT);
                setHover(MainPanel.EXPORT_CONSTRAINT);

            }
        });

        menuPanel.setAccountManagementButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.showPanel(MainPanel.ACCMANAGE_CONSTRAINT);
                setHover(MainPanel.ACCMANAGE_CONSTRAINT);
            }
        });

        menuPanel.setNotificationButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.showPanel(MainPanel.NOTIFICATION_CONSTRAINT);
                setHover(MainPanel.NOTIFICATION_CONSTRAINT);
            }
        });

        menuPanel.setChangeInformButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.showPanel(MainPanel.CHANGE_INFORMATION_CONSTRAINT);
                setHover(MainPanel.CHANGE_INFORMATION_CONSTRAINT);
            }
        });
        menuPanel.setLogoutButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.showPanel(MainPanel.LOG_OUT_CONSTRAINT);
                setHover(MainPanel.LOG_OUT_CONSTRAINT);
            }
        });

        add(menuPanel, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}

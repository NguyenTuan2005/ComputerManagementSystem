package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {
    JLabel role, name;
    JButton productBt, supplierBt, customerBt, stockImportBt, stockExportBt, accountManagementBt, notificationBt, logoutBt, changeInformBt;
    GridBagConstraints gbc;

    public MenuPanel() {
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        setBackground(Style.BACKGROUND_COLOR);

        ComponentTop componentTop = new ComponentTop();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weighty = 10;
        add(componentTop, gbc);

        ComponentButton componentButton = new ComponentButton();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weighty = 20;
        add(componentButton, gbc);

        ComponentBottom componentBottom = new ComponentBottom();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weighty = 30;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.PAGE_END;
        add(componentBottom, gbc);
    }

    class ComponentTop extends JPanel {
        public ComponentTop() {
            setLayout(new BorderLayout());
            setBackground(Style.BACKGROUND_COLOR_FUll_BLUR);
            setBorder(BorderFactory.createEmptyBorder());
            role = new JLabel("Manager");
            role.setFont(new Font("Arial", Font.PLAIN, 25));
            role.setHorizontalAlignment(JLabel.CENTER);
            role.setForeground(Color.GREEN);
            add(role, BorderLayout.NORTH);

            Circle circle = new Circle(22, 22, 70, new Color(211, 211, 211));
            add(circle, BorderLayout.CENTER);

            name = new JLabel("Huy Hoang");
            name.setHorizontalAlignment(JLabel.CENTER);
            name.setForeground(Color.GREEN);
            name.setFont(new Font("Arial", Font.PLAIN, 25));
            add(name, BorderLayout.SOUTH);
        }
    }

    class ComponentButton extends JPanel {
        public ComponentButton() {
            setLayout(new GridLayout(0, 1, 0, 20));
            setBackground(Style.BACKGROUND_COLOR_FUll_BLUR);

            productBt = new JButton("Product");
            productBt.setFocusable(false);
            productBt.setBackground(Color.WHITE);
            productBt.setFont(Style.FONT_SIZE);
            productBt.setHorizontalAlignment(SwingConstants.LEFT);
            productBt.setBorderPainted(false);
            add(productBt);

            supplierBt = new JButton("Supplier");
            supplierBt.setFocusable(false);
            supplierBt.setBackground(Color.WHITE);
            supplierBt.setFont(Style.FONT_SIZE);
            supplierBt.setHorizontalAlignment(SwingConstants.LEFT);
            supplierBt.setBorderPainted(false);
            add(supplierBt);

            customerBt = new JButton("Customer");
            customerBt.setFocusable(false);
            customerBt.setBackground(Color.WHITE);
            customerBt.setFont(Style.FONT_SIZE);
            customerBt.setHorizontalAlignment(SwingConstants.LEFT);
            customerBt.setBorderPainted(false);
            add(customerBt);

            stockImportBt = new JButton("Import");
            stockImportBt.setFocusable(false);
            stockImportBt.setBackground(Color.WHITE);
            stockImportBt.setFont(Style.FONT_SIZE);
            stockImportBt.setHorizontalAlignment(SwingConstants.LEFT);
            stockImportBt.setBorderPainted(false);
            add(stockImportBt);

            stockExportBt = new JButton("Export");
            stockExportBt.setFocusable(false);
            stockExportBt.setBackground(Color.WHITE);
            stockExportBt.setFont(Style.FONT_SIZE);
            stockExportBt.setHorizontalAlignment(SwingConstants.LEFT);
            stockExportBt.setBorderPainted(false);
            add(stockExportBt);

            accountManagementBt = new JButton("Account Management");
            accountManagementBt.setFocusable(false);
            accountManagementBt.setBackground(Color.WHITE);
            accountManagementBt.setFont(Style.FONT_SIZE);
            accountManagementBt.setHorizontalAlignment(SwingConstants.LEFT);
            accountManagementBt.setBorderPainted(false);
            add(accountManagementBt);

            notificationBt = new JButton("Notification");
            notificationBt.setFocusable(false);
            notificationBt.setBackground(Color.WHITE);
            notificationBt.setFont(Style.FONT_SIZE);
            notificationBt.setHorizontalAlignment(SwingConstants.LEFT);
            notificationBt.setBorderPainted(false);
            add(notificationBt);
        }
    }

    class ComponentBottom extends JPanel {
        public ComponentBottom() {
            setLayout(new GridLayout(2, 1, 0, 10));
            setBackground(Style.BACKGROUND_COLOR_FUll_BLUR);

            logoutBt = new JButton("Log Out");
            logoutBt.setFocusable(true);
            logoutBt.setBorderPainted(false);
            logoutBt.setBackground(Color.WHITE);
            logoutBt.setFont(Style.FONT_SIZE);
            logoutBt.setHorizontalAlignment(SwingConstants.LEFT);
            add(logoutBt);

            changeInformBt = new JButton("Change Information");
            changeInformBt.setFocusable(true);
            changeInformBt.setBorderPainted(false);
            changeInformBt.setBackground(Color.WHITE);
            changeInformBt.setFont(Style.FONT_SIZE);
            changeInformBt.setHorizontalAlignment(SwingConstants.LEFT);
            add(changeInformBt);
        }
    }

    // thiết lập ActionListener cho nút "Sản phẩm"
    public void setProductButtonListener(ActionListener listener) {
        productBt.addActionListener(listener);
    }

    // thiết lập ActionListener cho nút "Nhà cung cấp"
    public void setSupplierButtonListener(ActionListener listener) {
        supplierBt.addActionListener(listener);
    }

    // Clear all button colors
    public void setClearButtonColor() {
        productBt.setBackground(Style.BACKGROUND_COLOR_FUll_BLUR);
        supplierBt.setBackground(Style.BACKGROUND_COLOR_FUll_BLUR);
    }
}

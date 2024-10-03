package view;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MenuPanel extends JPanel {
    JLabel role, name, credit;
    JButton productBt, supplierBt, customerBt, stockImportBt, stockExportBt, accountManagementBt, notificationBt, logoutBt, changeInformBt;
    GridBagConstraints gbc;

    public MenuPanel() {
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        setBackground(Style.BACKGROUND_COLOR);


        ComponentTop componentTop = new ComponentTop();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weighty = 30;
        add(componentTop, gbc);

        ComponentButton componentButton = new ComponentButton();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weighty = 20;
        gbc.fill = GridBagConstraints.BOTH;
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
            setBackground(Style.BACKGROUND_COLOR);
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
            setBackground(Style.BACKGROUND_COLOR);

            productBt = new JButton("PRODUCT");
            productBt.setFocusable(false);
            productBt.setBackground(Style.BACKGROUND_COLOR);
            productBt.setForeground(Style.WORD_COLOR_WHITE);
            productBt.setFont(Style.FONT_SIZE);
            productBt.setHorizontalAlignment(SwingConstants.LEFT);
            productBt.setBorderPainted(false);

            add(productBt);

            supplierBt = new JButton("SUPPLIER");
            supplierBt.setFocusable(false);
            supplierBt.setBackground(Style.BACKGROUND_COLOR);
            supplierBt.setForeground(Style.WORD_COLOR_WHITE);
            supplierBt.setFont(Style.FONT_SIZE);
            supplierBt.setHorizontalAlignment(SwingConstants.LEFT);
            supplierBt.setBorderPainted(false);
            add(supplierBt);

            customerBt = new JButton("CUSTOMER");
            customerBt.setFocusable(false);
            customerBt.setBackground(Style.BACKGROUND_COLOR);
            customerBt.setForeground(Style.WORD_COLOR_WHITE);
            customerBt.setFont(Style.FONT_SIZE);
            customerBt.setHorizontalAlignment(SwingConstants.LEFT);
            customerBt.setBorderPainted(false);
            add(customerBt);

            stockImportBt = new JButton("IMPORT");
            stockImportBt.setFocusable(false);
            stockImportBt.setBackground(Style.BACKGROUND_COLOR);

            stockImportBt.setFont(Style.FONT_SIZE);
            stockImportBt.setHorizontalAlignment(SwingConstants.LEFT);
            stockImportBt.setBorderPainted(false);
            stockImportBt.setForeground(Style.WORD_COLOR_WHITE);
            add(stockImportBt);

            stockExportBt = new JButton("EXPORT");
            stockExportBt.setFocusable(false);
            stockExportBt.setBackground(Style.BACKGROUND_COLOR);
            stockExportBt.setForeground(Style.WORD_COLOR_WHITE);
            stockExportBt.setFont(Style.FONT_SIZE);
            stockExportBt.setHorizontalAlignment(SwingConstants.LEFT);
            stockExportBt.setBorderPainted(false);
            add(stockExportBt);

            accountManagementBt = new JButton("ACCOUNT");
            accountManagementBt.setFocusable(false);
            accountManagementBt.setBackground(Style.BACKGROUND_COLOR);
            accountManagementBt.setForeground(Style.WORD_COLOR_WHITE);
            accountManagementBt.setFont(Style.FONT_SIZE);
            accountManagementBt.setHorizontalAlignment(SwingConstants.LEFT);
            accountManagementBt.setBorderPainted(false);
            add(accountManagementBt);

            notificationBt = new JButton("NOTIFICATION");
            notificationBt.setFocusable(false);
            notificationBt.setBackground(Style.BACKGROUND_COLOR);
            notificationBt.setForeground(Style.WORD_COLOR_WHITE);
            notificationBt.setFont(Style.FONT_SIZE);
            notificationBt.setHorizontalAlignment(SwingConstants.LEFT);
            notificationBt.setBorderPainted(false);
            add(notificationBt);
        }
    }

    class ComponentBottom extends JPanel {
        public ComponentBottom() {
            setLayout(new GridLayout(3, 1, 0, 10));
            setBackground(Style.BACKGROUND_COLOR);

            changeInformBt = new JButton("CHANGE INFORMATION");
            changeInformBt.setFocusable(false);
            changeInformBt.setBackground(Style.BACKGROUND_COLOR);
            changeInformBt.setFont(Style.FONT_SIZE);
            changeInformBt.setHorizontalAlignment(SwingConstants.LEFT);
            changeInformBt.setBorderPainted(false);
            changeInformBt.setForeground(Style.WORD_COLOR_WHITE);
            add(changeInformBt);

            logoutBt = new JButton("LOGOUT");
            logoutBt.setFocusable(false);
            logoutBt.setBackground(Style.BACKGROUND_COLOR);
            logoutBt.setFont(Style.FONT_SIZE);
            logoutBt.setHorizontalAlignment(SwingConstants.LEFT);
            logoutBt.setBorderPainted(false);
            logoutBt.setForeground(Style.WORD_COLOR_WHITE);
            add(logoutBt);

            credit = new JLabel("     Group high!");
            add(credit);

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
}
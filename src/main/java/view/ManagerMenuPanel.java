package view;

import Config.CurrentUser;

import view.OverrideComponent.CircularImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ManagerMenuPanel extends JPanel {
    JLabel role, name, credit;
    CircularImage avatar;
    JButton productBt, supplierBt, customerBt,orderBt, inventoryBt, accountManagementBt, notificationBt, changeInformBt, logoutBt, switchToLoginBt;
    GridBagConstraints gbc;

    public ManagerMenuPanel() {

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
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBackground(Style.BACKGROUND_COLOR);
            setBorder(BorderFactory.createEmptyBorder());

            role = new JLabel("Customer");
            role.setFont(Style.FONT_PLAIN_25);
            role.setForeground(Color.GREEN);
            role.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(role);

            avatar = new CircularImage(CurrentUser.URL, 100, 100, true);
            avatar.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(Box.createRigidArea(new Dimension(0, 5)));
            add(avatar);

            name = new JLabel("<html>" + CurrentUser.USER_NAME + "<html>");
            name.setFont(Style.FONT_PLAIN_25);
            name.setForeground(Color.GREEN);
            name.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(Box.createRigidArea(new Dimension(0, 5)));
            add(name);
        }
    }

    class ComponentButton extends JPanel {
        public ComponentButton() {
            setLayout(new GridLayout(0, 1, 0, 10));
            setBackground(Style.BACKGROUND_COLOR);

            productBt = createButton("PRODUCT", Style.FONT_BOLD_15, Color.white, Style.BACKGROUND_COLOR);
            setIcon("src/main/java/Icon/productIcon.png", productBt);
            add(productBt);

            supplierBt = createButton("SUPPLIER", Style.FONT_BOLD_15, Color.white, Style.BACKGROUND_COLOR);
            setIcon("src/main/java/Icon/supplierIcon.png", supplierBt);
            add(supplierBt);

            customerBt = createButton("CUSTOMER", Style.FONT_BOLD_15, Color.white, Style.BACKGROUND_COLOR);
            setIcon("src/main/java/Icon/iconCustomer.png", customerBt);
            add(customerBt);

            orderBt = createButton("ORDER", Style.FONT_BOLD_15, Color.white, Style.BACKGROUND_COLOR);
            setIcon("src/main/java/Icon/orderIcon.png", orderBt);
            add(orderBt);

            inventoryBt = createButton("INVENTORY", Style.FONT_BOLD_15, Color.white, Style.BACKGROUND_COLOR);

            setIcon("src/main/java/Icon/inventoryIcon.png", inventoryBt);
            add(inventoryBt);

            accountManagementBt = createButton("ACCOUNT", Style.FONT_BOLD_15, Color.white, Style.BACKGROUND_COLOR);
            setIcon("src/main/java/Icon/user_15094854.png", accountManagementBt);
            add(accountManagementBt);

            notificationBt = createButton("NOTIFICATION", Style.FONT_BOLD_15, Color.white, Style.BACKGROUND_COLOR);
            setIcon("src/main/java/Icon/iconNotification.png", notificationBt);
            add(notificationBt);
        }
    }

    class ComponentBottom extends JPanel {
        public ComponentBottom() {
            setLayout(new GridLayout(4, 1, 0, 5));
            setBackground(Style.BACKGROUND_COLOR);

            changeInformBt = createButton("CHANGE INFORMATION", Style.FONT_BOLD_15, Color.white, Style.BACKGROUND_COLOR);
            setIcon("src/main/java/Icon/iconChangeInform.png", changeInformBt);
            add(changeInformBt);

            switchToLoginBt = createButton("SWITCH TO LOGIN", Style.FONT_BOLD_15, Color.white, Style.BACKGROUND_COLOR);
            setIcon("src/main/java/Icon/switchRole_Icon.png", switchToLoginBt);
            add(switchToLoginBt);

            logoutBt = createButton("LOGOUT", Style.FONT_BOLD_15, Style.CANCEL_BUTTON_COLOR_RED, Style.BACKGROUND_COLOR);
            setIcon("src/main/java/Icon/exit-sign.png", logoutBt);
            add(logoutBt);

            credit = new JLabel("     Group 2");
            add(credit);
        }
    }

    // set icon cho button duy đã tại conflict tại đây >>>
    public static void setIcon(String url, JButton that) {
        ImageIcon iconButton = new ImageIcon(url);
        Image image = iconButton.getImage(); // Lấy Image từ ImageIcon
        Dimension buttonSize = that.getPreferredSize();
        Image resizedImage = image.getScaledInstance(buttonSize.height - 5, buttonSize.height - 5, java.awt.Image.SCALE_SMOOTH); // Resize
        that.setIcon(new ImageIcon(resizedImage));
    }

    public JButton createButton(String title, Font font, Color textColor, Color backgroundColor) {
        JButton bt = new JButton(title);
        bt.setBackground(backgroundColor);
        bt.setForeground(textColor);
        bt.setFont(font);
        bt.setHorizontalAlignment(SwingConstants.LEFT);
        bt.setBorderPainted(false);
        bt.setFocusable(false);
        return bt;
    }

    // thiết lập ActionListener cho nút "Sản phẩm"
    public void setProductButtonListener(ActionListener listener) {
        productBt.addActionListener(listener);
    }

    // thiết lập ActionListener cho nút "Nhà cung cấp"
    public void setSupplierButtonListener(ActionListener listener) {
        supplierBt.addActionListener(listener);
    }

    public void setOrderButtonListener(ActionListener listener) {
        orderBt.addActionListener(listener);
    }

    // thiết lập ActionListener cho nút "inventory"
    public void setInventoryButtonListener(ActionListener listener) {
        inventoryBt.addActionListener(listener);
    }

    //thiết lập ActionListener cho nút "customer"
    public void setCustomerButtonListener(ActionListener listener) {
        customerBt.addActionListener(listener);
    }

    //thiết lập ActionListener cho nút "account manager"
    public void setAccountManagementButtonListener(ActionListener listener) {
        accountManagementBt.addActionListener(listener);
    }

    //thiết lập ActionListener cho nút "notification"
    public void setNotificationButtonListener(ActionListener listener) {
        notificationBt.addActionListener(listener);
    }

    //thiết lập ActionListener cho nút "change information"
    public void setChangeInformButtonListener(ActionListener listener) {
        changeInformBt.addActionListener(listener);
    }

    public void setswitchToLoginListener(ActionListener listener) {
        switchToLoginBt.addActionListener(listener);
    }
    //thiết lập ActionListener cho nút "log out"
    public void setLogoutButtonListener(ActionListener listener) {
        logoutBt.addActionListener(listener);
    }
}

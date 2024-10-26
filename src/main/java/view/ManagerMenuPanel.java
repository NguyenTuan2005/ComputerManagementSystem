package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ManagerMenuPanel extends JPanel {
    OldLoginFrame loginFrame;
    ManagerFrame managerFrame;
    JLabel role, name, credit;
    CircularImage avatar;
    JButton productBt, supplierBt, customerBt, inventoryBt, accountManagementBt, notificationBt, logoutBt, changeInformBt;
    GridBagConstraints gbc;

//    public ManagerMenuPanel(LoginFrame loginFrame, ManagerFrame managerFrame) {
    public ManagerMenuPanel() {
        this.loginFrame = loginFrame;
        this.managerFrame = managerFrame;

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
//            role = new JLabel(loginFrame.roleComboBox.getSelectedItem().toString());
//            role.setFont(new Font("Arial", Font.PLAIN, 25));
//            role.setHorizontalAlignment(JLabel.CENTER);
//            role.setForeground(Color.GREEN);
//            add(role, BorderLayout.NORTH);
            avatar = new CircularImage("src/main/java/Icon/dragon_Icon.png",60,60);
            avatar.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(avatar);

            name = new JLabel("Put your name in here");
            name.setAlignmentX(Component.CENTER_ALIGNMENT);
            name.setForeground(Color.GREEN);
            name.setFont(new Font("Arial", Font.PLAIN, 25));


            add(name);
        }
    }

    class ComponentButton extends JPanel {
        public ComponentButton() {
            setLayout(new GridLayout(0, 1, 0, 20));
            setBackground(Style.BACKGROUND_COLOR);

            productBt = new JButton("PRODUCT");
            setFormatButton(productBt);
            setIcon("src/main/java/Icon/productIcon.png", productBt);
            add(productBt);

            supplierBt = new JButton("SUPPLIER");
            setFormatButton(supplierBt);
            setIcon("src/main/java/Icon/supplierIcon.png", supplierBt);
            add(supplierBt);

            customerBt = new JButton("CUSTOMER");
            setFormatButton(customerBt);
            setIcon("src/main/java/Icon/iconCustomer.png", customerBt);
            add(customerBt);

            inventoryBt = new JButton("INVENTORY");
            setFormatButton(inventoryBt);
            setIcon("src/main/java/Icon/inventory_Icon.png", inventoryBt);
            add(inventoryBt);

            accountManagementBt = new JButton("ACCOUNT");
            setFormatButton(accountManagementBt);
            setIcon("src/main/java/Icon/user_15094854.png", accountManagementBt);
            add(accountManagementBt);

            notificationBt = new JButton("NOTIFICATION");
            setFormatButton(notificationBt);
            setIcon("src/main/java/Icon/iconNotification.png", notificationBt);
            add(notificationBt);
        }
    }

    class ComponentBottom extends JPanel {
        public ComponentBottom() {
            setLayout(new GridLayout(3, 1, 0, 10));
            setBackground(Style.BACKGROUND_COLOR);

            changeInformBt = new JButton("CHANGE INFORMATION");
            setFormatButton(changeInformBt);
            setIcon("src/main/java/Icon/iconChangeInform.png", changeInformBt);
            add(changeInformBt);

            logoutBt = new JButton("LOGOUT");
            setFormatButton(logoutBt);
            logoutBt.setForeground(new Color(176, 52, 52));
            logoutBt.setFont(new Font("Arial", Font.BOLD, 15));
            setIcon("src/main/java/Icon/exit-sign.png", logoutBt);
            add(logoutBt);
            credit = new JLabel("     Group high!");
            add(credit);

        }
    }

    // set icon cho button
    private void setIcon(String url, JButton that) {
        ImageIcon iconButton = new ImageIcon(url);
        Image image = iconButton.getImage(); // Lấy Image từ ImageIcon
        Dimension buttonSize = that.getPreferredSize();
        Image resizedImage = image.getScaledInstance(buttonSize.height - 5, buttonSize.height - 5, java.awt.Image.SCALE_SMOOTH); // Resize
        that.setIcon(new ImageIcon(resizedImage));
    }

    // format lai dinh dang button
    private void setFormatButton(JButton that) {
        that.setFocusable(false);
        that.setBackground(Style.BACKGROUND_COLOR);
        that.setForeground(Style.WORD_COLOR_WHITE);
        that.setFont(Style.FONT_SIZE);
        that.setHorizontalAlignment(SwingConstants.LEFT);
        that.setBorderPainted(false);
    }

    // thiết lập ActionListener cho nút "Sản phẩm"
    public void setProductButtonListener(ActionListener listener) {
        productBt.addActionListener(listener);
    }

    // thiết lập ActionListener cho nút "Nhà cung cấp"
    public void setSupplierButtonListener(ActionListener listener) {
        supplierBt.addActionListener(listener);
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

    //thiết lập ActionListener cho nút "log out"
    public void setLogoutButtonListener(ActionListener listener) {
        logoutBt.addActionListener(listener);
    }
}

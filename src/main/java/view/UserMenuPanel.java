package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class UserMenuPanel extends JPanel {
    LoginFrame loginFrame;
    UserFrame userFrame;
    JLabel role, name, credit;
    JButton productCatalogBt, purchasedBt, notificationBt, changeInformBt, logoutBt;
    GridBagConstraints gbc;

    public UserMenuPanel(LoginFrame loginFrame, UserFrame userFrame) {
        this.loginFrame = loginFrame;
        this.userFrame = userFrame;
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

    // panel cac nút trên cùng
    class ComponentTop extends JPanel {
        public ComponentTop() {
            setLayout(new BorderLayout());
            setBackground(Style.BACKGROUND_COLOR);
            setBorder(BorderFactory.createEmptyBorder());
            role = new JLabel(loginFrame.roleComboBox.getSelectedItem().toString());
            role.setFont(new Font("Arial", Font.PLAIN, 25));
            role.setHorizontalAlignment(JLabel.CENTER);
            role.setForeground(Color.GREEN);
            add(role, BorderLayout.NORTH);

            Circle circle = new Circle(18, 18, 70, new Color(211, 211, 211));
            add(circle, BorderLayout.CENTER);

            name = new JLabel(loginFrame.userNameField.getText());
            name.setHorizontalAlignment(JLabel.CENTER);
            name.setForeground(Color.GREEN);
            name.setFont(new Font("Arial", Font.PLAIN, 25));
            add(name, BorderLayout.SOUTH);
        }
    }

    // panel cho cac nút chức năng của người dùng ở giữa
    class ComponentButton extends JPanel {
        public ComponentButton() {
            setLayout(new GridLayout(0, 1, 0, 20));
            setBackground(Style.BACKGROUND_COLOR);
            productCatalogBt = new JButton("PRODUCT CATALOG");
            setFormatButton(productCatalogBt);
            setIcon("src/main/java/Icon/cartIcon.png", productCatalogBt);
            add(productCatalogBt);


            purchasedBt = new JButton("PURCHASED");
            setFormatButton(purchasedBt);
            add(purchasedBt);

            notificationBt = new JButton("CART");
            setFormatButton(notificationBt);
            setIcon("src/main/java/Icon/iconNotification.png", notificationBt);
            add(notificationBt);
        }
    }

    //panel cho cac nút dưới cùng
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
            logoutBt.setForeground(Color.RED);
            logoutBt.setFont(new Font("Arial", Font.BOLD, 15));
            setIcon("src/main/java/Icon/exit-sign.png", logoutBt);
            add(logoutBt);

            credit = new JLabel("     Group high!");
            add(credit);

        }
    }

    // set Icon cho button
    private void setIcon(String url, JButton that) {
        ImageIcon iconButton = new ImageIcon(url);
        Image image = iconButton.getImage(); // Lấy Image từ ImageIcon
        Dimension buttonSize = that.getPreferredSize();
        Image resizedImage = image.getScaledInstance(buttonSize.height - 10, buttonSize.height - 10, java.awt.Image.SCALE_SMOOTH); // Resize
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

    // thiết lập ActionListener cho nút "Mua Sắm":))
    public void setProductCatalogBtListener(ActionListener listener) {
        productCatalogBt.addActionListener(listener);
    }

    public void setNotificationBtListener(ActionListener listener) {
        notificationBt.addActionListener(listener);
    }

    public void setPurchasedBtListener(ActionListener listener) {
        purchasedBt.addActionListener(listener);
    }

    //thiết lập ActionListener cho nút "log out"
    public void setLogoutBtListener(ActionListener listener) {
        logoutBt.addActionListener(listener);
    }
}

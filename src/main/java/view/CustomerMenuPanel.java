package view;

import Config.CurrentUser;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import view.OverrideComponent.CircularImage;

public class CustomerMenuPanel extends JPanel {
  LoginFrame loginFrame;
  CustomerFrame userFrame;
  JLabel role, name, credit;
  CircularImage avatar;
  JButton productCatalogBt, orderHistoryBt, notificationBt, changeInfoBt, logoutBt;
  GridBagConstraints gbc;

  public CustomerMenuPanel() {
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

  // panel cho cac nút chức năng của người dùng ở giữa
  class ComponentButton extends JPanel {
    public ComponentButton() {
      setLayout(new GridLayout(0, 1, 0, 10));
      setBackground(Style.BACKGROUND_COLOR);

      productCatalogBt = new JButton("PRODUCT CATALOG");
      setFormatButton(productCatalogBt);
      productCatalogBt.setBackground(Style.MENU_BUTTON_COLOR_GREEN);
      setIcon("src/main/java/Icon/catalog_Icon.png", productCatalogBt);
      add(productCatalogBt);

      orderHistoryBt = new JButton("ORDER HISTORY");
      setFormatButton(orderHistoryBt);
      setIcon("src/main/java/Icon/purchasedList_Icon.png", orderHistoryBt);
      add(orderHistoryBt);

      notificationBt = new JButton("NOTIFICATION");
      setFormatButton(notificationBt);
      setIcon("src/main/java/Icon/iconNotification.png", notificationBt);
      add(notificationBt);
    }
  }

  // panel cho cac nút dưới cùng
  class ComponentBottom extends JPanel {
    public ComponentBottom() {
      setLayout(new GridLayout(3, 1, 0, 10));
      setBackground(Style.BACKGROUND_COLOR);

      changeInfoBt = new JButton("CHANGE INFORMATION");
      setFormatButton(changeInfoBt);
      setIcon("src/main/java/Icon/iconChangeInform.png", changeInfoBt);
      add(changeInfoBt);

      logoutBt = new JButton("LOGOUT");

      setFormatButton(logoutBt);
      logoutBt.setForeground(new Color(176, 52, 52));
      logoutBt.setFont(new Font("Arial", Font.BOLD, 15));
      setIcon("src/main/java/Icon/exit-sign.png", logoutBt);
      add(logoutBt);

      credit = new JLabel("     Group 2");
      add(credit);
    }
  }

  // set Icon cho button
  private void setIcon(String filePath, JButton that) {
    ImageIcon iconButton = new ImageIcon(filePath);
    Image image = iconButton.getImage(); // Lấy Image từ ImageIcon
    Dimension buttonSize = that.getPreferredSize();
    Image resizedImage =
        image.getScaledInstance(
            buttonSize.height, buttonSize.height, java.awt.Image.SCALE_SMOOTH); // Resize
    that.setIcon(new ImageIcon(resizedImage));
  }

  // format lai dinh dang button
  private void setFormatButton(JButton that) {
    that.setFocusable(false);
    that.setBackground(Style.BACKGROUND_COLOR);
    that.setForeground(Style.WORD_COLOR_WHITE);
    that.setFont(Style.FONT_BOLD_15);
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
    orderHistoryBt.addActionListener(listener);
  }

  public void setChangeInfoBtListener(ActionListener listener) {
    changeInfoBt.addActionListener(listener);
  }

  // thiết lập ActionListener cho nút "log out"
  public void setLogoutBtListener(ActionListener listener) {
    logoutBt.addActionListener(listener);
  }
}

package view;

import config.ButtonConfig;
import config.CurrentUser;
import config.LabelConfig;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import view.overrideComponent.CircularImage;
import view.overrideComponent.CustomButton;

public class ManagerMenuPanel extends JPanel {
  private JLabel role, name, credit;
  private CircularImage avatar;
  CustomButton productBt,
      supplierBt,
      customerBt,
      orderBt,
      inventoryBt,
      accountManagementBt,
      notificationBt,
      changeInformBt,
      logoutBt,
      switchToLoginBt;
  private GridBagConstraints gbc;

  public ManagerMenuPanel() {
    setLayout(new GridBagLayout());
    setPreferredSize(new Dimension(250, 900));
    gbc = new GridBagConstraints();
    gbc.gridx = 1;
    gbc.insets = new Insets(5, 15, 5, 15);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    setBackground(new Color(227, 242, 253));

    ComponentTop componentTop = new ComponentTop();

    gbc.gridy = 0;
    gbc.weighty = 0.4;
    add(componentTop, gbc);

    ComponentButton componentButton = new ComponentButton();
    gbc.gridy++;
    gbc.weighty = 0.4;
    gbc.fill = GridBagConstraints.BOTH;
    add(componentButton, gbc);

    ComponentBottom componentBottom = new ComponentBottom();
    gbc.gridy++;
    gbc.weighty = 0.4;

    gbc.anchor = GridBagConstraints.PAGE_END;
    add(componentBottom, gbc);
  }

  private class ComponentTop extends JPanel {
    public ComponentTop() {
      setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      setBackground(new Color(227, 242, 253)); // good
      setAlignmentX(Component.CENTER_ALIGNMENT);

      avatar = new CircularImage(CurrentUser.URL, 100, 100, true);
      avatar.setAlignmentX(Component.CENTER_ALIGNMENT);

      name =
          LabelConfig.createLabel(
              "<html>" + CurrentUser.CURRENT_USER_V2.getFullName() + "<html>",
              Style.FONT_BOLD_20,
              Color.BLACK,
              SwingConstants.CENTER);
      name.setPreferredSize(new Dimension(230, 50));
      name.setAlignmentX(Component.CENTER_ALIGNMENT);

      role =
          LabelConfig.createLabel("Manager", Style.FONT_PLAIN_15, Color.GRAY, SwingConstants.LEFT);
      role.setPreferredSize(new Dimension(100, 100));
      role.setAlignmentX(Component.CENTER_ALIGNMENT);

      JSeparator separatorTop = new JSeparator(SwingConstants.HORIZONTAL);
      separatorTop.setPreferredSize(new Dimension(320, 5));

      add(avatar);
      add(Box.createRigidArea(new Dimension(0, 10)));
      add(name);
      add(role);
      add(Box.createRigidArea(new Dimension(0, 5)));
      add(separatorTop);
    }
  }

  private class ComponentButton extends JPanel {
    public ComponentButton() {
      setLayout(new GridLayout(0, 1, 0, 5));
      setBackground(Style.MENU_BACKGROUND_COLOR);

      productBt =
          ButtonConfig.createCustomButton(
              " Product",
              Style.FONT_PLAIN_18,
              Color.BLACK,
              Style.MENU_BACKGROUND_COLOR,
              Style.LIGHT_BlUE,
              20,
              SwingConstants.LEFT,
              new Dimension(180, 25));
      ButtonConfig.setButtonIcon("src/main/java/Icon/productIcon.png", productBt, 5);
      add(productBt);

      supplierBt =
          ButtonConfig.createCustomButton(
              " Supplier",
              Style.FONT_PLAIN_18,
              Color.BLACK,
              Style.MENU_BACKGROUND_COLOR,
              Style.LIGHT_BlUE,
              20,
              SwingConstants.LEFT,
              new Dimension(180, 25));
      ButtonConfig.setButtonIcon("src/main/java/Icon/supplierIcon.png", supplierBt, 5);
      add(supplierBt);

      customerBt =
          ButtonConfig.createCustomButton(
              " Customer",
              Style.FONT_PLAIN_18,
              Color.BLACK,
              Style.MENU_BACKGROUND_COLOR,
              Style.LIGHT_BlUE,
              20,
              SwingConstants.LEFT,
              new Dimension(180, 25));
      ButtonConfig.setButtonIcon("src/main/java/Icon/catalog_Icon.png", customerBt, 5);
      add(customerBt);

      orderBt =
          ButtonConfig.createCustomButton(
              " Order",
              Style.FONT_PLAIN_18,
              Color.BLACK,
              Style.MENU_BACKGROUND_COLOR,
              Style.LIGHT_BlUE,
              20,
              SwingConstants.LEFT,
              new Dimension(180, 25));
      ButtonConfig.setButtonIcon("src/main/java/Icon/orderIcon.png", orderBt, 5);
      add(orderBt);

      inventoryBt =
          ButtonConfig.createCustomButton(
              " Inventory",
              Style.FONT_PLAIN_18,
              Color.BLACK,
              Style.MENU_BACKGROUND_COLOR,
              Style.LIGHT_BlUE,
              20,
              SwingConstants.LEFT,
              new Dimension(180, 25));
      ButtonConfig.setButtonIcon("src/main/java/Icon/inventoryIcon.png", inventoryBt, 5);
      add(inventoryBt);

      accountManagementBt =
          ButtonConfig.createCustomButton(
              " Account",
              Style.FONT_PLAIN_18,
              Color.BLACK,
              Style.MENU_BACKGROUND_COLOR,
              Style.LIGHT_BlUE,
              20,
              SwingConstants.LEFT,
              new Dimension(180, 25));
      ButtonConfig.setButtonIcon("src/main/java/Icon/user_15094854.png", accountManagementBt, 5);

      add(accountManagementBt);

      notificationBt =
          ButtonConfig.createCustomButton(
              " Notification",
              Style.FONT_PLAIN_18,
              Color.BLACK,
              Style.MENU_BACKGROUND_COLOR,
              Style.LIGHT_BlUE,
              20,
              SwingConstants.LEFT,
              new Dimension(180, 25));
      ButtonConfig.setButtonIcon("src/main/java/Icon/iconNotification.png", notificationBt, 5);
      add(notificationBt);

      changeInformBt =
          ButtonConfig.createCustomButton(
              " Change Information",
              Style.FONT_PLAIN_18,
              Color.BLACK,
              Style.MENU_BACKGROUND_COLOR,
              Style.LIGHT_BlUE,
              20,
              SwingConstants.LEFT,
              new Dimension(180, 25));
      ButtonConfig.setButtonIcon("src/main/java/Icon/iconChangeInform.png", changeInformBt, 5);
      add(changeInformBt);
    }
  }

  private class ComponentBottom extends JPanel {
    public ComponentBottom() {
      setLayout(new GridLayout(3, 1, 0, 2));
      setBackground(Style.MENU_BACKGROUND_COLOR);

      switchToLoginBt =
          ButtonConfig.createCustomButton(
              " Switch to Login",
              Style.FONT_PLAIN_18,
              Color.BLACK,
              Style.MENU_BACKGROUND_COLOR,
              Style.LIGHT_BlUE,
              20,
              SwingConstants.LEFT,
              new Dimension(180, 20));
      ButtonConfig.setButtonIcon("src/main/java/Icon/switchRole_Icon.png", switchToLoginBt, 5);
      add(switchToLoginBt);

      logoutBt =
          ButtonConfig.createCustomButton(
              " Logout",
              Style.FONT_PLAIN_18,
              Style.CANCEL_BUTTON_COLOR_RED,
              Style.MENU_BACKGROUND_COLOR,
              Style.LIGHT_RED,
              20,
              SwingConstants.LEFT,
              new Dimension(180, 20));
      ButtonConfig.setButtonIcon("src/main/java/Icon/exit-sign.png", logoutBt, 5);
      add(logoutBt);

      credit = new JLabel(" Group 2");
      add(credit);
    }
  }

  public static void setIcon(String url, JButton that) {
    ImageIcon iconButton = new ImageIcon(url);
    Image image = iconButton.getImage();
    Dimension buttonSize = that.getPreferredSize();
    Image resizedImage =
        image.getScaledInstance(
            buttonSize.height - 5, buttonSize.height - 5, java.awt.Image.SCALE_SMOOTH); // Resize
    that.setIcon(new ImageIcon(resizedImage));
  }

  public void setProductButtonListener(ActionListener listener) {
    productBt.addActionListener(listener);
  }

  public void setSupplierButtonListener(ActionListener listener) {
    supplierBt.addActionListener(listener);
  }

  public void setOrderButtonListener(ActionListener listener) {
    orderBt.addActionListener(listener);
  }

  public void setInventoryButtonListener(ActionListener listener) {
    inventoryBt.addActionListener(listener);
  }

  public void setCustomerButtonListener(ActionListener listener) {
    customerBt.addActionListener(listener);
  }

  public void setAccountManagementButtonListener(ActionListener listener) {
    accountManagementBt.addActionListener(listener);
  }

  public void setNotificationButtonListener(ActionListener listener) {
    notificationBt.addActionListener(listener);
  }

  public void setChangeInformButtonListener(ActionListener listener) {
    changeInformBt.addActionListener(listener);
  }

  public void setswitchToLoginListener(ActionListener listener) {
    switchToLoginBt.addActionListener(listener);
  }

  public void setLogoutButtonListener(ActionListener listener) {
    logoutBt.addActionListener(listener);
  }
}

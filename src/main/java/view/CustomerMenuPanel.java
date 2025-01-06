package view;

import config.ButtonConfig;
import config.CurrentUser;
import config.LabelConfig;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import view.overrideComponent.CircularImage;
import view.overrideComponent.CustomButton;

public class CustomerMenuPanel extends JPanel {
  private JLabel role, name, credit;
  private CircularImage avatar;
  public CustomButton productCatalogBt, orderHistoryBt, notificationBt, changeInfoBt, logoutBt;
  private GridBagConstraints gbc;

  public CustomerMenuPanel() {
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
          LabelConfig.createLabel("Customer", Style.FONT_PLAIN_15, Color.GRAY, SwingConstants.LEFT);
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
      setLayout(new GridLayout(5, 1, 5, 10));
      setBackground(Style.MENU_BACKGROUND_COLOR);

      productCatalogBt =
          ButtonConfig.createCustomButton(
              " Product Catalog",
              Style.FONT_PLAIN_18,
              Color.BLACK,
              Style.MENU_BACKGROUND_COLOR,
              Style.LIGHT_BlUE,
              20,
              SwingConstants.LEFT,
              new Dimension(180, 25));
      ButtonConfig.setButtonIcon("src/main/java/Icon/catalog_Icon.png", productCatalogBt, 5);
      add(productCatalogBt);

      orderHistoryBt =
          ButtonConfig.createCustomButton(
              " Order History",
              Style.FONT_PLAIN_18,
              Color.BLACK,
              Style.MENU_BACKGROUND_COLOR,
              Style.LIGHT_BlUE,
              20,
              SwingConstants.LEFT,
              new Dimension(180, 25));
      ButtonConfig.setButtonIcon("src/main/java/Icon/purchasedList_Icon.png", orderHistoryBt, 5);
      add(orderHistoryBt);

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

      changeInfoBt =
          ButtonConfig.createCustomButton(
              " Change Information",
              Style.FONT_PLAIN_18,
              Color.BLACK,
              Style.MENU_BACKGROUND_COLOR,
              Style.LIGHT_BlUE,
              20,
              SwingConstants.LEFT,
              new Dimension(180, 25));
      ButtonConfig.setButtonIcon("src/main/java/Icon/iconChangeInform.png", changeInfoBt, 5);
      add(changeInfoBt);
      add(Box.createRigidArea(new Dimension(0, 50)));
    }
  }

  private class ComponentBottom extends JPanel {
    public ComponentBottom() {
      setLayout(new GridLayout(2, 1, 2, 0));
      setBackground(new Color(227, 242, 253));
      logoutBt =
          ButtonConfig.createCustomButton(
              " Logout",
              Style.FONT_PLAIN_18,
              Style.CANCEL_BUTTON_COLOR_RED,
              Style.MENU_BACKGROUND_COLOR,
              Style.LIGHT_RED,
              20,
              SwingConstants.LEFT,
              new Dimension(180, 25));
      ButtonConfig.setButtonIcon("src/main/java/Icon/exit-sign.png", logoutBt, 5);
      add(logoutBt);

      credit = new JLabel(" Group 2");
      add(credit);
    }
  }

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

  public void setLogoutBtListener(ActionListener listener) {
    logoutBt.addActionListener(listener);
  }
}

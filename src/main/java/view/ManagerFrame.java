package view;

import java.awt.*;
import javax.swing.*;
import view.overrideComponent.CustomButton;

public class ManagerFrame extends JFrame {
  private ManagerMainPanel managerMainPanel;
  private ManagerMenuPanel managerMenuPanel;

  public ManagerFrame() {
    setTitle("Computer Management");
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setResizable(true);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());
    setIconImage(new ImageIcon("src/main/java/Icon/logo.png").getImage());

    managerMenuPanel = new ManagerMenuPanel();
    managerMainPanel = new ManagerMainPanel();
    add(managerMenuPanel, BorderLayout.WEST);
    add(managerMainPanel, BorderLayout.CENTER);

    managerMenuPanel.setDashBoardBtListener(
        e -> {
          managerMainPanel.showPanel(ManagerMainPanel.DASHBOARD_CONSTRAINT);
          setHover(ManagerMainPanel.DASHBOARD_CONSTRAINT);
        });

    managerMenuPanel.setProductButtonListener(
        e -> {
          managerMainPanel.showPanel(ManagerMainPanel.PRODUCT_CONSTRAINT);
          setHover(ManagerMainPanel.PRODUCT_CONSTRAINT);
        });

    managerMenuPanel.setSupplierButtonListener(
        e -> {
          managerMainPanel.showPanel(ManagerMainPanel.SUPPLIER_CONSTRAINT);
          setHover(ManagerMainPanel.SUPPLIER_CONSTRAINT);
        });

    managerMenuPanel.setCustomerButtonListener(
        e -> {
          managerMainPanel.showPanel(ManagerMainPanel.CUSTOMER_CONSTRAINT);
          setHover(ManagerMainPanel.CUSTOMER_CONSTRAINT);
        });

    managerMenuPanel.setOrderButtonListener(
        e -> {
          managerMainPanel.showPanel(ManagerMainPanel.ORDER_CONSTRAINT);
          setHover(ManagerMainPanel.ORDER_CONSTRAINT);
        });

    managerMenuPanel.setInventoryButtonListener(
        e -> {
          managerMainPanel.showPanel(ManagerMainPanel.INVENTORY_CONSTRAINT);
          setHover(ManagerMainPanel.INVENTORY_CONSTRAINT);
        });

    managerMenuPanel.setAccountManagementButtonListener(
        e -> {
          managerMainPanel.showPanel(ManagerMainPanel.ACC_MANAGEMENT_CONSTRAINT);
          setHover(ManagerMainPanel.ACC_MANAGEMENT_CONSTRAINT);
        });

    managerMenuPanel.setNotificationButtonListener(
        e -> {
          //            managerMainPanel.reloadNotification();
          managerMainPanel.showPanel(ManagerMainPanel.NOTIFICATION_CONSTRAINT);
          setHover(ManagerMainPanel.NOTIFICATION_CONSTRAINT);
        });

    managerMenuPanel.setChangeInformButtonListener(
        e -> {
          managerMainPanel.showPanel(ManagerMainPanel.CHANGE_INFORMATION_CONSTRAINT);
          setHover(ManagerMainPanel.CHANGE_INFORMATION_CONSTRAINT);
        });

    managerMenuPanel.setswitchToLoginListener(
        e -> {
          int confirmed =
              JOptionPane.showConfirmDialog(
                  null,
                  "Are you sure you want to switch to Login Screen?",
                  "Switch to Login Screen",
                  JOptionPane.YES_NO_OPTION);

          if (confirmed == JOptionPane.YES_OPTION) {
            new LoginFrame();
          }
        });

    managerMenuPanel.setLogoutButtonListener(
        e -> {
          int confirmed =
              JOptionPane.showConfirmDialog(
                  null,
                  "Are you sure you want to logout?",
                  "Logout Confirmation",
                  JOptionPane.YES_NO_OPTION);

          if (confirmed == JOptionPane.YES_OPTION) {
            dispose();
            new LoginFrame();
          }
        });

    managerMainPanel.setDashBoardProductBtListener(
        e -> {
          managerMainPanel.showPanel(ManagerMainPanel.PRODUCT_CONSTRAINT);
          setHover(ManagerMainPanel.PRODUCT_CONSTRAINT);
        });

    managerMainPanel.setDashBoardCustomerBtListener(
        e -> {
          managerMainPanel.showPanel(ManagerMainPanel.CUSTOMER_CONSTRAINT);
          setHover(ManagerMainPanel.CUSTOMER_CONSTRAINT);
        });

    managerMainPanel.setDashBoardSupplierListener(
        e -> {
          managerMainPanel.showPanel(ManagerMainPanel.SUPPLIER_CONSTRAINT);
          setHover(ManagerMainPanel.SUPPLIER_CONSTRAINT);
        });
    managerMainPanel.setDashBoardManagerBtListener(
        e -> {
          managerMainPanel.showPanel(ManagerMainPanel.ACC_MANAGEMENT_CONSTRAINT);
          setHover(ManagerMainPanel.ACC_MANAGEMENT_CONSTRAINT);
        });

    setVisible(true);
  }

  public void setHover(String panelName) {
    CustomButton[] buttons = {
      managerMenuPanel.dashBoardBt,
      managerMenuPanel.productBt,
      managerMenuPanel.supplierBt,
      managerMenuPanel.customerBt,
      managerMenuPanel.orderBt,
      managerMenuPanel.inventoryBt,
      managerMenuPanel.managerBt,
      managerMenuPanel.notificationBt,
      managerMenuPanel.changeInformBt
    };
    String[] constraints = {
      ManagerMainPanel.DASHBOARD_CONSTRAINT,
      ManagerMainPanel.PRODUCT_CONSTRAINT,
      ManagerMainPanel.SUPPLIER_CONSTRAINT,
      ManagerMainPanel.CUSTOMER_CONSTRAINT,
      ManagerMainPanel.ORDER_CONSTRAINT,
      ManagerMainPanel.INVENTORY_CONSTRAINT,
      ManagerMainPanel.ACC_MANAGEMENT_CONSTRAINT,
      ManagerMainPanel.NOTIFICATION_CONSTRAINT,
      ManagerMainPanel.CHANGE_INFORMATION_CONSTRAINT
    };
    for (int i = 0; i < buttons.length; i++) {
      buttons[i].setBackgroundColor(
          panelName.equals(constraints[i]) ? Style.MENU_BUTTON_COLOR : Style.MENU_BACKGROUND_COLOR);
    }
  }
}

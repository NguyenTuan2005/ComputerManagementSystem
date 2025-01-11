package view.otherComponent;

import static view.CustomerMainPanel.createImageForProduct;
import static view.CustomerMainPanel.formatCurrency;

import config.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import lombok.NoArgsConstructor;
import view.Style;
import view.overrideComponent.CustomButton;


public class ShowOrder extends JPanel {
  private int orderId;


  public ShowOrder() {
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    for (int i =0 ; i< 10; i++) {
      System.out.println(">>>>>>>>>>>>>");
      add(showOrder());
    }
  }

  public JPanel showOrder() {
    this.orderId = orderId;
//    this.customerOrderDTOs = customerOrderDTOS;
    JPanel main = new JPanel(new BorderLayout());
    main.setPreferredSize(new Dimension(1270, 400));

    JPanel titlePn = new JPanel(new FlowLayout(FlowLayout.LEFT));
    titlePn.setPreferredSize(new Dimension(100, 50));
    titlePn.setBackground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
    JLabel status = new JLabel("Order awaiting shipment");
    status.setFont(Style.FONT_BOLD_30);
    status.setForeground(Color.WHITE);
    titlePn.add(status);

    JPanel datePn = new JPanel(new BorderLayout());
    datePn.setBackground(Color.WHITE);
    JLabel orderDate =
        LabelConfig.createLabel(
            "    Order Date: " ,
            Style.FONT_BOLD_18,
            Color.BLACK,
            SwingConstants.LEFT);
    datePn.add(orderDate, BorderLayout.WEST);

    JPanel top = new JPanel(new GridLayout(2, 1));
    top.add(titlePn);
    top.add(datePn);
    main.add(top, BorderLayout.NORTH);

    JPanel mid = new JPanel(new GridLayout(0, 2));
    mid.setBackground(Color.WHITE);
    int totalPrice = 0;
//    for (var item : customerOrderDTOs) {
//      totalPrice += item.customerOrderDTO().getQuantity() * item.customerOrderDTO().getUnitPrice();
//      mid.add(productOrderPn(item));
//    }

    main.add(mid, BorderLayout.CENTER);

    JPanel bottomPn = new JPanel(new BorderLayout());
    bottomPn.setBackground(Color.WHITE);
    bottomPn.setPreferredSize(new Dimension(100, 60));
    MatteBorder botBorder = new MatteBorder(1, 0, 0, 0, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
    EmptyBorder margin = new EmptyBorder(5, 0, 0, 0);

    Border combinedBorder = new CompoundBorder(margin, botBorder);
    bottomPn.setBorder(combinedBorder);

    JPanel bottomLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
    bottomLeft.setBackground(Color.WHITE);
    CustomButton viewBill =
        ButtonConfig.createCustomButton(
            " View Bill",
            Style.FONT_BOLD_15,
            Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
            Color.white,
            Style.LIGHT_BlUE,
            Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
            1,
            10,
            SwingConstants.CENTER,
            new Dimension(150, 40));
    ButtonConfig.setButtonIcon("src/main/java/Icon/bill_Icon.png", viewBill, 15);
    viewBill.addActionListener(
        e -> {
//          var a = new BillFrame(order);
        });

    bottomLeft.add(viewBill);

    JPanel bottomRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    bottomRight.setBackground(Color.WHITE);
    JLabel items =
        LabelConfig.createLabel(
            "size" + " items:  ",
            Style.FONT_BOLD_25,
            Color.BLACK,
            SwingConstants.RIGHT);
    String priceFormated = formatCurrency.format(totalPrice);
    JLabel totalPriceLabel =
        LabelConfig.createLabel(
            priceFormated + "₫",
            Style.FONT_BOLD_25,
            Style.CONFIRM_BUTTON_COLOR_GREEN,
            SwingConstants.RIGHT);

    bottomRight.add(items);
    bottomRight.add(totalPriceLabel);

    bottomPn.add(bottomLeft, BorderLayout.CENTER);
    bottomPn.add(bottomRight, BorderLayout.EAST);
    main.add(bottomPn, BorderLayout.SOUTH);
    return main;
  }

  public JPanel productOrderPn( ) {

    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBackground(Color.WHITE);
    mainPanel.setPreferredSize(new Dimension(400, 160));
    mainPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

    JPanel imgPn = new JPanel();
    imgPn.setBackground(Color.WHITE);
    JLabel proImg =
        new JLabel(createImageForProduct("", 150, 150));
    imgPn.add(proImg);
    mainPanel.add(imgPn, BorderLayout.WEST);

    JPanel proDetails = new JPanel(new GridBagLayout());
    proDetails.setBackground(Color.WHITE);
    proDetails.setPreferredSize(new Dimension(500, 120));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1;
    gbc.insets = new Insets(5, 5, 5, 5);

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weighty = 0.4;
    JLabel proName =
        LabelConfig.createLabel(
           "name product",
            Style.FONT_BOLD_18,
            Color.BLACK,
            SwingConstants.LEFT);
    proDetails.add(proName, gbc);

    gbc.gridy++;
    gbc.weighty = 0.1;
    JLabel proID =
        LabelConfig.createLabel(
            "Product ID:  " ,
            Style.FONT_PLAIN_13,
            Color.BLACK,
            SwingConstants.LEFT);
    proDetails.add(proID, gbc);
    gbc.gridy++;
    JLabel proBrand =
        LabelConfig.createLabel(
            "Brand: "  ,
            Style.FONT_PLAIN_13,
            Color.BLACK,
            SwingConstants.LEFT);
    proDetails.add(proBrand, gbc);

    gbc.gridy++;
    JLabel proTechnicalDetail =
        LabelConfig.createLabel(
            "CPU "
                + "cpu"
                + " / "
                + "RAM "
                + "ram"
                + " / "
                + "Storage "
                + "memory",
            Style.FONT_PLAIN_13,
            Color.BLACK,
            SwingConstants.LEFT);
    proDetails.add(proTechnicalDetail, gbc);

    gbc.gridy++;
    JPanel bottomPn = new JPanel(new GridLayout(1, 2));

    JPanel pricePn = new JPanel(new FlowLayout(FlowLayout.LEFT)); // panel xem giá của 1 sản phẩm
    pricePn.setBackground(Color.WHITE);
    String unitPrice = formatCurrency.format(123113);
    JLabel proPrice =
        LabelConfig.createLabel(
            unitPrice + "₫",
            Style.FONT_BOLD_18,
            Style.CONFIRM_BUTTON_COLOR_GREEN,
            SwingConstants.LEFT);
    pricePn.add(proPrice);

    JPanel quantityPn =
        new JPanel(new FlowLayout(FlowLayout.RIGHT)); // panel xem số lượng mua của 1 sản phẩm
    quantityPn.setBackground(Color.WHITE);
    JLabel proQuantity =
        LabelConfig.createLabel(
            "x" + "quantity product",
            Style.FONT_BOLD_18,
            Color.BLACK,
            SwingConstants.RIGHT);
    quantityPn.add(proQuantity);

    bottomPn.add(pricePn);
    bottomPn.add(quantityPn);
    proDetails.add(bottomPn, gbc);

    mainPanel.add(proDetails, BorderLayout.CENTER);

    return mainPanel;
  }
}

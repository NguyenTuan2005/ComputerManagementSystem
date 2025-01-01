package view.OtherComponent;

import Config.BillConfig;
import Config.LabelConfig;
import dto.CustomerOrderDTO;
import dto.CustomerOrderDetailDTO;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.swing.*;
import view.Style;

public class BillFrame extends JFrame {

  private JTextArea billTextArea;
  private ArrayList<CustomerOrderDTO> customerOrderDTOS;

  public BillFrame(ArrayList<CustomerOrderDetailDTO> customerOrderDTOs) {
    this.customerOrderDTOS =
            (ArrayList<CustomerOrderDTO>)
                    customerOrderDTOs.stream().map(c -> c.customerOrderDTO()).collect(Collectors.toList());

    setTitle("Computer Management System");
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setSize(550, 600);
    setLocationRelativeTo(null);
    setIconImage(new ImageIcon("src/main/java/Icon/logo.png").getImage());

    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);

    JLabel billLabel =
            LabelConfig.createLabel(
                    "Bill", Style.FONT_BOLD_25_MONO, Color.white, SwingConstants.CENTER);
    panel.add(billLabel, BorderLayout.NORTH);

    billTextArea = new JTextArea();
    billTextArea.setFont(new Font("Monospaced", Font.PLAIN, 10));
    billTextArea.setEditable(false);

    billTextArea.setText(BillConfig.generateBill(this.customerOrderDTOS));

    JScrollPane scrollPane = new JScrollPane(billTextArea);
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    panel.add(scrollPane, BorderLayout.CENTER);

    add(panel);
    setVisible(true);
  }

}
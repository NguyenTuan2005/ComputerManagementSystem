package view.OtherComponent;

import Config.BillConfig;
import Config.LabelConfig;
import dto.CustomerOrderDTO;
import dto.CustomerOrderDetailDTO;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.swing.*;
import view.OverrideComponent.RoundedBorder;
import view.Style;

public class BillFrame extends JFrame {

  private JTextArea billTextArea;
  private ArrayList<CustomerOrderDTO> customerOrderDTOS;

  public BillFrame(ArrayList<CustomerOrderDetailDTO> customerOrderDTOs) {
    this.customerOrderDTOS =
        (ArrayList<CustomerOrderDTO>)
            customerOrderDTOs.stream().map(c -> c.customerOrderDTO()).collect(Collectors.toList());

    setTitle("Bill Viewer");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(500, 600);
    setLocationRelativeTo(null);

    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);

    JLabel billLabel =
        LabelConfig.createLabel(
            "Bill", Style.FONT_BOLD_25_MONO, Color.white, SwingConstants.CENTER);
    panel.add(billLabel, BorderLayout.NORTH);

    billTextArea = new JTextArea();
    billTextArea.setFont(new Font("Monospaced", Font.PLAIN, 10));
    billTextArea.setEditable(false);

    JScrollPane scrollPane = new JScrollPane(billTextArea);
    panel.add(scrollPane, BorderLayout.CENTER);
    add(panel);

    billTextArea.setBorder(
        BorderFactory.createCompoundBorder(
            new RoundedBorder(20, 2, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE),
            BorderFactory.createEmptyBorder(3, 3, 3, 8)));
    billTextArea.setLineWrap(true);
    //        billTextArea.setWrapStyleWord(true);
    billTextArea.setEditable(false);
    billTextArea.setOpaque(true);

    int width = 800;
    billTextArea.setSize(new Dimension(width, Short.MAX_VALUE));
    int preferredHeight = billTextArea.getPreferredSize().height;
    billTextArea.setPreferredSize(new Dimension(width, preferredHeight));

    setVisible(true);

    billTextArea.setText(BillConfig.generateBill(this.customerOrderDTOS));
  }

  public static void main(String[] args) {
    new BillFrame(null);
  }
}

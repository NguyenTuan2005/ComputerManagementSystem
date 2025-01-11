package view.otherComponent;

import config.BillConfig;
import config.LabelConfig;
import java.awt.*;
import javax.swing.*;
import model.Order;
import view.Style;

public class BillFrame extends JFrame {

  private JTextArea billTextArea;

  public BillFrame(Order order) {

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

    billTextArea.setText(BillConfig.generateBill(order));

    JScrollPane scrollPane = new JScrollPane(billTextArea);
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    panel.add(scrollPane, BorderLayout.CENTER);

    add(panel);
    setVisible(true);
  }
}

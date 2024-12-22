package view.OtherComponent;

import Config.LabelConfig;
import view.Style;

import javax.swing.*;
import java.awt.*;

public class BillFrame extends JFrame {
    private JTextArea billTextArea;
    BillFrame(){
        setTitle("Bill Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);

        JLabel billLabel = LabelConfig.createLabel("Bill",Style.FONT_BOLD_25_MONO,Color.white,SwingConstants.CENTER);
        panel.add(billLabel, BorderLayout.NORTH);

        billTextArea = new JTextArea();
        billTextArea.setFont(new Font("Monospaced", Font.PLAIN, 18));
        billTextArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(billTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        add(panel);

        setVisible(true);


        billTextArea.setText("========== Bill ==========\n"
                + "Product 1: $10\n"
                + "Product 2: $20\n"
                + "Product 3: $30\n"
                + "--------------------------\n"
                + "Total: $60\n"
                + "==========================");

    }

    public static void main(String[] args) {
        new BillFrame();
    }
}

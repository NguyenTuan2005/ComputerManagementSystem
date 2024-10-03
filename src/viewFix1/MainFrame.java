package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public class MainFrame extends JFrame {
    MainPanel mainPanel = new MainPanel();
    MenuPanel menuPanel = new MenuPanel();

    public MainFrame() {
        setTitle("Computer Management");
        setSize(1200, 650);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        menuPanel.setProductButtonListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.showPanel("product");
                menuPanel.productBt.setBackground(new Color(144, 238, 144));
                menuPanel.supplierBt.setBackground(Color.WHITE);
            }
        });
        menuPanel.setSupplierButtonListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.showPanel("supplier");
                menuPanel.supplierBt.setBackground(new Color(144, 238, 144));
                menuPanel.productBt.setBackground(Color.WHITE);
            }
        });

        add(menuPanel, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}

package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
                menuPanel.setClearButtonColor();
                menuPanel.supplierBt.setBackground(new Color(144, 238, 144));
            }
        });
        menuPanel.setSupplierButtonListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.showPanel("supplier");
                menuPanel.setClearButtonColor();
                menuPanel.supplierBt.setBackground(new Color(144, 238, 144));
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

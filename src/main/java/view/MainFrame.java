package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                menuPanel.productBt.setBackground(Style.MENU_BUTTON_COLOR_GREEN);
                menuPanel.supplierBt.setBackground(Style.BACKGROUND_COLOR);
            }
        });
        menuPanel.setSupplierButtonListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.showPanel("supplier");
                menuPanel.supplierBt.setBackground(Style.MENU_BUTTON_COLOR_GREEN);
                menuPanel.productBt.setBackground(Style.BACKGROUND_COLOR);
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

package view.OtherComponent;

import javax.swing.*;
import java.awt.*;

public class ProductInterface extends JFrame {

    public ProductInterface() {
        setTitle("Product Interface");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set main panel layout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        mainPanel.setBackground(Color.WHITE);

        // Sample Product Image
        JLabel productImage = new JLabel(new ImageIcon("path/to/your/image.png"));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(productImage, gbc);

        // Product title
        JLabel titleLabel = new JLabel("ACEMAGIC Laptop, Windows 11...");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        // Product price
        JLabel priceLabel = new JLabel("$1,299.99");
        priceLabel.setFont(new Font("Arial", Font.BOLD, 24));
        priceLabel.setForeground(Color.ORANGE);
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        mainPanel.add(priceLabel, gbc);

        // Shipping & Delivery info
        JLabel shippingLabel = new JLabel("$197.00 Shipping & Import Charges to Vietnam");
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        mainPanel.add(shippingLabel, gbc);

        JLabel deliveryLabel = new JLabel("Delivery: Tuesday, November 12");
        gbc.gridy = 4;
        mainPanel.add(deliveryLabel, gbc);

        // Stock status
        JLabel stockLabel = new JLabel("In Stock");
        stockLabel.setFont(new Font("Arial", Font.BOLD, 14));
        stockLabel.setForeground(Color.GREEN);
        gbc.gridy = 5;
        mainPanel.add(stockLabel, gbc);

        // Quantity selector
        JLabel quantityLabel = new JLabel("Quantity:");
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        mainPanel.add(quantityLabel, gbc);

        JComboBox<String> quantityComboBox = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});
        gbc.gridx = 1;
        mainPanel.add(quantityComboBox, gbc);

        // Add to Cart and Buy Now buttons
        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.setBackground(Color.YELLOW);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        mainPanel.add(addToCartButton, gbc);

        JButton buyNowButton = new JButton("Buy Now");
        buyNowButton.setBackground(Color.ORANGE);
        gbc.gridx = 1;
        mainPanel.add(buyNowButton, gbc);

        // Add main panel to frame
        add(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProductInterface frame = new ProductInterface();
            frame.setVisible(true);
        });
    }
}

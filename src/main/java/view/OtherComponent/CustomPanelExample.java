package view.OtherComponent;

import javax.swing.*;
import java.awt.*;

public class CustomPanelExample {
    public static void main(String[] args) {
        // Tạo JFrame
        JFrame frame = new JFrame("Custom Layout Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Tạo JPanel chính có kích thước cố định
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(300, 400));

        // Tạo JPanel con để đặt các thành phần theo chiều dọc
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Nút bấm ở góc trên bên trái
        JButton topButton = new JButton("Top Button");
        topButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(topButton);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Khoảng cách dưới nút bấm đầu tiên

        // Label chứa hình ảnh
        ImageIcon imageIcon = new ImageIcon("src/main/java/Icon/img.png"); // Thay bằng đường dẫn hình ảnh của bạn
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(imageLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Khoảng cách dưới hình ảnh

        // Tạo JPanel để chứa hai nút bấm
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0)); // Căn giữa với khoảng cách giữa các nút

        // Thêm hai nút bấm vào buttonPanel
        JButton button1 = new JButton("Button 1");
        JButton button2 = new JButton("Button 2");
        buttonPanel.add(button1);
        buttonPanel.add(button2);

        // Thêm buttonPanel vào contentPanel
        contentPanel.add(buttonPanel);

        // Thêm contentPanel vào mainPanel ở vị trí đầu (phần trên)
        mainPanel.add(contentPanel, BorderLayout.NORTH);

        // Thêm mainPanel vào frame và hiển thị
        frame.add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null); // Căn giữa màn hình
        frame.setVisible(true);
    }
}

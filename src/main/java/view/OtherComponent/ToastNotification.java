package view.OtherComponent;

import javax.swing.*;
import java.awt.*;

public class ToastNotification {

    public static void showToast(String message, int duration) {
        // Tạo một JWindow để hiển thị thông báo
        JWindow window = new JWindow();
        window.setLayout(new BorderLayout());
        window.setSize(350, 60);
        window.setLocationRelativeTo(null); // Đặt vị trí ở giữa màn hình

        // Tạo JLabel để chứa thông điệp
        JLabel label = new JLabel(message, SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(new Color(180, 231, 224));
        label.setForeground(new Color(173, 99, 34));
        label.setFont(new Font("Arial", Font.BOLD, 25));

        ImageIcon iconButton = new ImageIcon("src/main/java/Icon/iconNotification.png");
        Image image = iconButton.getImage(); // Lấy Image từ ImageIcon
        Image resizedImage = image.getScaledInstance(28,28 , java.awt.Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(resizedImage));
        label.setBorder(BorderFactory.createLineBorder(new Color(255, 165, 0), 2));
        window.add(label, BorderLayout.CENTER);

        // Hiển thị thông báo
        window.setVisible(true);

        // Thay đổi vị trí của thông báo
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(screenSize.width - window.getWidth() - 10, screenSize.height - window.getHeight() - 50);

        // Tạo một thread để tự động ẩn thông báo sau một khoảng thời gian nhất định
        new Timer(duration, e -> {
            window.setVisible(false);
            window.dispose(); // Giải phóng tài nguyên
        }).start();
    }
}

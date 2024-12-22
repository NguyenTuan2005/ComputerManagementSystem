package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SmoothScrollImageGallery {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Smooth Scroll Image Gallery");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);

            // Panel chứa ảnh lớn
            JLabel largeImageLabel = new JLabel();
            largeImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            largeImageLabel.setVerticalAlignment(SwingConstants.CENTER);
            largeImageLabel.setPreferredSize(new Dimension(400, 300));
            frame.add(largeImageLabel, BorderLayout.CENTER);

            // Panel chứa danh sách các nút ảnh nhỏ
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

            // Danh sách các ảnh
            String[] imagePaths = {
                    "src/main/java/img/hoangPb_Avatar.jpg",
                    "src/main/java/img/dieulinh.jpg",
                    "src/main/java/img/cus_huyen.jpg",
                    "src/main/java/img/cus_huyen.jpg",
                    "src/main/java/img/cus_huyen.jpg",
                    "src/main/java/img/cus_huyen.jpg",
                    "src/main/java/img/cus_huyen.jpg",
                    "src/main/java/img/cus_huyen.jpg",
                    "src/main/java/img/cus_huyen.jpg",
                    "src/main/java/img/dieulinh.jpg",
                    "src/main/java/img/dieulinh.jpg",
                    "src/main/java/img/dieulinh.jpg",
                    "src/main/java/img/dieulinh.jpg",
                    "src/main/java/img/dieulinh.jpg",
                    "src/main/java/img/dieulinh.jpg",
                    "src/main/java/img/dieulinh.jpg",
                    "src/main/java/img/dieulinh.jpg",
                    "src/main/java/img/dieulinh.jpg",
                    "src/main/java/img/dieulinh.jpg",
                    "src/main/java/img/dieulinh.jpg",
                    "src/main/java/img/dieulinh.jpg",
                    "src/main/java/img/dieulinh.jpg",

            };

            JScrollPane scrollPane = new JScrollPane(buttonPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setPreferredSize(new Dimension(600, 100));
            frame.add(scrollPane, BorderLayout.SOUTH);

            for (String imagePath : imagePaths) {
                // Tạo icon cho nút
                ImageIcon icon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
                JButton imageButton = new JButton(icon);
                imageButton.setPreferredSize(new Dimension(80, 80));

                // Thêm sự kiện khi bấm vào nút
                imageButton.addActionListener((ActionEvent e) -> {
                    // Hiển thị ảnh lớn
                    ImageIcon largeIcon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH));
                    largeImageLabel.setIcon(largeIcon);

                    // Tự động cuộn mượt
                    Rectangle bounds = imageButton.getBounds();
                    smoothScrollToCenter(scrollPane, bounds);
                });

                buttonPanel.add(imageButton);
            }

            frame.setVisible(true);
        });
    }

    /**
     * Cuộn mượt đến vị trí để nút ở giữa vùng hiển thị.
     */
    private static void smoothScrollToCenter(JScrollPane scrollPane, Rectangle targetBounds) {
        JViewport viewport = scrollPane.getViewport();
        Rectangle viewRect = viewport.getViewRect();
        int viewWidth = viewRect.width;

        // Tính vị trí trung tâm của nút trong vùng hiển thị
        int targetX = targetBounds.x - (viewWidth / 2) + (targetBounds.width / 2);

        // Lấy vị trí hiện tại
        int startX = viewport.getViewPosition().x;

        // Tính toán khoảng cách cần cuộn
        int distance = targetX - startX;

        // Tạo hiệu ứng cuộn mượt
        Timer timer = new Timer(5, null);
        timer.addActionListener(new ActionListener() {
            private int step = 0;
            private final int totalSteps = 20; // Số bước để hoàn thành cuộn
            private final int dx = distance / totalSteps;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (step < totalSteps) {
                    viewport.setViewPosition(new Point(startX + dx * step, 0));
                    step++;
                } else {
                    viewport.setViewPosition(new Point(targetX, 0)); // Đảm bảo chính xác vị trí cuối
                    timer.stop();
                }
            }
        });
        timer.start();
    }
}

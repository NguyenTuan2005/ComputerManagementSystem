package view.overrideComponent;

import java.awt.*;
import javax.swing.*;
import view.Style;

public class ToastNotification {

  public static void showToast(String message, int duration, int height, int x, int y) {
    JWindow window = new JWindow();
    window.setLayout(new BorderLayout());

    JLabel label = new JLabel(message, SwingConstants.CENTER);
    label.setOpaque(true);
    //        label.setBackground(new Color(180, 231, 224));
    //        label.setForeground(new Color(173, 99, 34));
    //        label.setFont(new Font("Arial", Font.BOLD, 20));

    label.setBackground(Style.LIGHT_BlUE);
    label.setForeground(new Color(173, 99, 34));
    label.setFont(new Font("Arial", Font.PLAIN, 20));

    ImageIcon iconButton = new ImageIcon("src/main/java/Icon/iconNotification.png");
    Image image = iconButton.getImage();
    Image resizedImage = image.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
    label.setIcon(new ImageIcon(resizedImage));
    label.setBorder(BorderFactory.createLineBorder(new Color(34, 139, 34)));

    window.add(label, BorderLayout.CENTER);

    label.setSize(label.getPreferredSize());
    int width = label.getPreferredSize().width + 20;
    window.setSize(width, height);

    if (x == -1 && y == -1) {
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      int screenWidth = screenSize.width;
      int screenHeight = screenSize.height;

      x = screenWidth - width - 10;
      y = screenHeight - height - 50;
    }

    if (x == -1) {
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      int screenWidth = screenSize.width;
      int screenHeight = screenSize.height;
      x = screenWidth - width - 10; // Đặt cách mép phải
      y = screenHeight - height - (50 * (y + 2));
    }

    window.setLocation(x, y);

    window.setVisible(true);

    new Timer(
            duration,
            e -> {
              window.setVisible(false);
              window.dispose();
            })
        .start();
  }
}

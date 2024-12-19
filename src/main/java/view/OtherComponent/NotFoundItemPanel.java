package view.OtherComponent;
import javax.swing.*;
import java.awt.*;

public class NotFoundItemPanel extends JPanel {
    public NotFoundItemPanel(String message) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1500, 1000));
        setBackground(new Color(255, 228, 225));
        JLabel iconLabel = new JLabel();
        iconLabel.setIcon(new ImageIcon("src/main/java/img/not-found-image.png"));
        JPanel panel = new JPanel();
        panel.add(iconLabel);
        add(panel, BorderLayout.CENTER);
    }
}

package Config;

import view.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonConfig {

    public static final Color BUTTON_HOVER_COLOR = new Color(52, 152, 219);
    public static final Color BUTTON_COLOR = new Color(41, 128, 185);

    public static void setIconSmallButton(String url, JButton that) {
        ImageIcon iconButton = new ImageIcon(url);
        Image image = iconButton.getImage(); // Lấy Image từ ImageIcon
        Dimension buttonSize = that.getPreferredSize();
        Image resizedImage = image.getScaledInstance(buttonSize.height - 10, buttonSize.height - 10, java.awt.Image.SCALE_SMOOTH); // Resize
        that.setIcon(new ImageIcon(resizedImage));
    }

    public static void setStyleButton(JButton that, Font font, Color textColor, Color backgroundColor, int textPosition, Dimension size) {
        that.setBackground(backgroundColor);
        that.setFont(font);
        that.setHorizontalAlignment(textPosition);
        that.setBorderPainted(false);
        that.setForeground(textColor);
        that.setFocusable(false);
        that.setPreferredSize(size);
    }

    public static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(BUTTON_COLOR);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    public static void setIconBigButton(String url, JButton that) {
        ImageIcon iconButton = new ImageIcon(url);
        Image image = iconButton.getImage(); // Lấy Image từ ImageIcon
        Dimension buttonSize = that.getPreferredSize();
        Image resizedImage = image.getScaledInstance(buttonSize.height - 35, buttonSize.height - 35, java.awt.Image.SCALE_SMOOTH); // Resize
        that.setIcon(new ImageIcon(resizedImage));
    }

    // set icon cho button duy đã tại conflict tại đây >>>
    public static  void setIcon(String url, JButton that) {
        ImageIcon iconButton = new ImageIcon(url);
        Image image = iconButton.getImage(); // Lấy Image từ ImageIcon
        Dimension buttonSize = that.getPreferredSize();
        Image resizedImage = image.getScaledInstance(buttonSize.height - 5, buttonSize.height - 5, java.awt.Image.SCALE_SMOOTH); // Resize
        that.setIcon(new ImageIcon(resizedImage));
    }

    // format lai dinh dang button
    public static void setFormatButton(JButton that) {
        that.setFocusable(false);
        that.setBackground(Style.BACKGROUND_COLOR);
        that.setForeground(Style.WORD_COLOR_WHITE);
        that.setFont(Style.FONT_SIZE);
        that.setHorizontalAlignment(SwingConstants.LEFT);
        that.setBorderPainted(false);
    }

    public static JSeparator createVerticalSeparator( int w , int h) {
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setPreferredSize(new Dimension(w, h)); // Điều chỉnh chiều cao của separator
        return separator;
    }
    public static JSeparator createVerticalSeparator() {
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setPreferredSize(new Dimension(2, 70)); // Điều chỉnh chiều cao của separator
        return separator;
    }

    public static void addButtonHoverEffect(JButton button , Color hover , Color def) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(def);
            }
        });
    }





    public static void main(String[] args) {
        JFrame frame = new JFrame("Cooldown Button");
        JButton btn = new JButton("Click Me");
        JLabel label = new JLabel("Ready", SwingConstants.CENTER);

        frame.setLayout(new java.awt.BorderLayout());
        frame.add(btn, java.awt.BorderLayout.CENTER);
        frame.add(label, java.awt.BorderLayout.SOUTH);


        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btn.setEnabled(false);
                int cooldown = 10;
                label.setText("Wait " + cooldown + " seconds...");

                Timer countdownTimer = new Timer(1000, new ActionListener() {
                    int secondsLeft = cooldown;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        secondsLeft--;
                        if (secondsLeft > 0) {
                            label.setText("Wait " + secondsLeft + " seconds...");
                        } else {
                            ((Timer) e.getSource()).stop();
                            btn.setEnabled(true);
                            label.setText("Ready");
                        }
                    }
                });

                countdownTimer.start();
            }
        });


        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

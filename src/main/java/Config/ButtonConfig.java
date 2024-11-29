package Config;

import view.OverrideComponent.CustomButton;
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

    public static JButton createEditFieldButton(JTextField textField) {
        JButton editBt = new JButton();
        editBt.setIcon(new ImageIcon("src/main/java/Icon/penIcon1.png"));
        editBt.setPreferredSize(new Dimension(45, 40));

        Color hoverBackground = new Color(130, 180, 230); // Màu sáng hơn khi hover

        editBt.setBackground(Style.LIGHT_BlUE);
        editBt.setFocusPainted(false);
        editBt.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 2));
        editBt.setContentAreaFilled(true); // Đảm bảo nền được vẽ

        // Thêm hiệu ứng hover
        editBt.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                editBt.setBackground(hoverBackground);
            }

            public void mouseExited(MouseEvent evt) {
                editBt.setBackground(Style.LIGHT_BlUE);
            }
        });

        // Thêm hiệu ứng click để làm sáng lên
        editBt.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                editBt.setBackground(hoverBackground.darker()); // Tối hơn khi nhấn
            }

            public void mouseReleased(MouseEvent evt) {
                editBt.setBackground(hoverBackground); // Quay lại màu hover sau khi thả chuột
            }
        });

        editBt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textField.setEditable(!textField.isEditable());
                textField.setForeground(textField.isEditable() ? Color.BLACK : Color.GRAY);
            }
        });

        return editBt;
    }

    // nút ẩn hiện cho mật khẩu
    public static JButton createShowPasswdButton(JPasswordField passwordField) {
        JButton toggleButton = new JButton();
        toggleButton.setBackground(Style.LIGHT_BlUE);
        toggleButton.setFocusPainted(false);
        toggleButton.setFocusable(false);
        toggleButton.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 2));
        ImageIcon showPasswd = new ImageIcon("src/main/java/Icon/showPasswd_Icon.png");
        ImageIcon hidePasswd = new ImageIcon("src/main/java/Icon/hidePasswd_Icon.png");

        toggleButton.setIcon(showPasswd);
        toggleButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                toggleButton.setBackground(new Color(130, 180, 230));
            }

            public void mouseExited(MouseEvent evt) {
                toggleButton.setBackground(Style.LIGHT_BlUE);
            }
        });

        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (passwordField.getEchoChar() != '\u0000') {
                    passwordField.setEchoChar('\u0000');
                    toggleButton.setIcon(hidePasswd);
                } else {
                    // Ẩn mật khẩu
                    passwordField.setEchoChar('*');
                    toggleButton.setIcon(showPasswd);
                }
            }
        });
        return toggleButton;
    }

    public static CustomButton createCustomButton(String text) {
        CustomButton button = new CustomButton(text);
        FontMetrics metrics = button.getFontMetrics(button.getFont());
        int textWidth = metrics.stringWidth(text);
        int padding = 40;
        button.setGradientColors(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Color.GRAY);
        button.setBackgroundColor(Style.LIGHT_BlUE);
        button.setBorderRadius(20);
        button.setPreferredSize(new Dimension(textWidth + padding, 50));

        return button;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Cooldown Button");
        JButton btn = new JButton("Click Me");
        JLabel label = new JLabel("Ready", SwingConstants.CENTER);
        CustomButton button = createCustomButton("Change Password");

        // Bố cục
        frame.setLayout(new java.awt.BorderLayout());
        frame.add(btn, java.awt.BorderLayout.CENTER);
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(button);
        frame.add(panel, java.awt.BorderLayout.NORTH);
        frame.add(label, java.awt.BorderLayout.SOUTH);

        // Lắng nghe sự kiện nhấn nút
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btn.setEnabled(false); // Vô hiệu hóa nút
                int cooldown = 10; // Thời gian chờ 10 giây
                label.setText("Wait " + cooldown + " seconds...");

                // Sử dụng Timer để đếm ngược và bật lại nút
                Timer countdownTimer = new Timer(1000, new ActionListener() {
                    int secondsLeft = cooldown; // Biến đếm ngược

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        secondsLeft--; // Giảm số giây
                        if (secondsLeft > 0) {
                            label.setText("Wait " + secondsLeft + " seconds...");
                        } else {
                            ((Timer) e.getSource()).stop(); // Dừng Timer
                            btn.setEnabled(true); // Bật lại nút
                            label.setText("Ready");
                        }
                    }
                });

                countdownTimer.start(); // Bắt đầu đếm ngược
            }
        });

        // Thiết lập JFrame
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

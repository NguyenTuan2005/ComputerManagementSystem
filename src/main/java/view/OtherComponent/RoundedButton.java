package view.OtherComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedButton extends JButton {
    private Color backgroundColor;
    private Color hoverColor;
    private String text;
    private Color textColor; // Màu chữ
    private Dimension buttonSize; // Kích thước nút
    private Color borderColor; // Màu viền
    private int borderWidth, radius; // Độ rộng của viền

    public RoundedButton(String text) {
        super(text);
        this.radius = 30;
        this.text = text;
        this.backgroundColor = Color.white; // Màu nền của nút
        this.hoverColor = Color.white; // Màu khi di chuột
        this.textColor = Color.BLACK; // Màu chữ mặc định
        this.buttonSize = new Dimension(150, 50); // Kích thước mặc định của nút
        this.borderColor = Color.white; // Màu viền mặc định
        this.borderWidth = 2; // Độ rộng viền mặc định

        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }

    // Phương thức vẽ nút bo tròn
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Đổi màu khi nút được nhấn
        if (getModel().isArmed()) {
            g2.setColor(hoverColor);
        } else {
            g2.setColor(backgroundColor);
        }

        // Vẽ hình chữ nhật bo tròn
        g2.fill(new RoundRectangle2D.Double(0, 0, buttonSize.getWidth(), buttonSize.getHeight(), radius, radius));

        // Vẽ đường viền
        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(borderWidth)); // Đặt độ rộng của viền
        g2.draw(new RoundRectangle2D.Double(0, 0, buttonSize.getWidth(), buttonSize.getHeight(), radius, radius));

        // Thiết lập màu chữ
        setForeground(textColor);

        // Vẽ chữ của nút
        super.paintComponent(g);
    }

    // Getter for text
    public String getText() {
        return this.text;
    }

    // Setter for text
    public void setText(String text) {
        this.text = text;
    }

    public void setButtonSize(Dimension size) {
        this.buttonSize = size; // Cập nhật kích thước
        setPreferredSize(size); // Đặt kích thước mới
        revalidate();
        repaint();
    }

    public void setCustomFont(Font font) {
        Font newFont = font;
        setFont(newFont);
        repaint();
    }

    public void setCustomAlignmentX(float alignment) {
        setAlignmentX(alignment); // Sử dụng setAlignmentX từ JComponent
        revalidate(); // Cập nhật lại bố cục
        repaint(); // Vẽ lại giao diện
    }
    // Phương thức để đổi màu chữ
    public void setTextColor(Color color) {
        this.textColor = color;
        repaint(); // Cập nhật giao diện sau khi đổi màu
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
        repaint();
    }

    public void setHoverColor(Color color) {
        this.hoverColor = color;
        repaint();
    }

    public void createLineBorder(Color color, int thickness) {
        this.borderColor = color;
        this.borderWidth = thickness;
        repaint();
    }
    public void setBorderRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public Dimension getPreferredSize() {
        return buttonSize; // Trả về kích thước đã thiết lập
    }
}





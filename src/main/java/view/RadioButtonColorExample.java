package view;

import javax.swing.*;
import java.awt.*;

public class RadioButtonColorExample {
    public static void main(String[] args) {
        // Tạo JFrame
        JFrame frame = new JFrame("Đổi màu Radio Button");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new FlowLayout());

        // Tạo JRadioButton
        JRadioButton radioButton = new JRadioButton("Lựa chọn 1");
        radioButton.setPreferredSize(new Dimension(100, 100));
        // Đổi màu nền và màu chữ
        radioButton.setBackground(Color.CYAN);
        radioButton.setForeground(Color.RED);

        // Thêm JRadioButton vào JFrame
        frame.add(radioButton);

        // Hiển thị JFrame
        frame.setVisible(true);
    }
}

package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GridBagExample {
    public static void main(String[] args) {
        // Tạo JFrame
        JFrame frame = new JFrame("GridBagLayout Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        // Tạo JPanel và đặt layout là GridBagLayout
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Cài đặt vị trí của các component trong GridBagLayout
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Cột 1 (JLabel)
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);
        
        gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);
        
        gbc.gridy = 2;
        panel.add(new JLabel("Email:"), gbc);

        // Cột 2 (JTextField, JPasswordField, JTextField)
        gbc.gridx = 1;
        gbc.gridy = 0;
        JTextField usernameField = new JTextField(15);
        panel.add(usernameField, gbc);
        
        gbc.gridy = 1;
        JPasswordField passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);
        
        gbc.gridy = 2;
        JTextField emailField = new JTextField(15);
        panel.add(emailField, gbc);
        
        // Cột 3 (Button to show/hide password)
        gbc.gridx = 2;
        gbc.gridy = 1;
        JButton togglePasswordButton = new JButton("Show");
        
        // Thêm hành động cho nút Show/Hide mật khẩu
        togglePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (passwordField.getEchoChar() == (char)0) {
                    passwordField.setEchoChar('*'); // Ẩn mật khẩu
                    togglePasswordButton.setText("Show"); // Thay đổi text thành "Show"
                } else {
                    passwordField.setEchoChar((char) 0); // Hiện mật khẩu
                    togglePasswordButton.setText("Hide"); // Thay đổi text thành "Hide"
                }
            }
        });
        
        panel.add(togglePasswordButton, gbc);

        // Thêm panel vào frame và hiển thị frame
        frame.add(panel);
        frame.setVisible(true);
    }
}

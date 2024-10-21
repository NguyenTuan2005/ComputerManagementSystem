package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.*;

public class LoginFrame extends JFrame {
    ManagerFrame managerFrame;
    UserFrame userFrame;
    LoginPanel loginPanel;
    TitlePanel titlePanel;
//    UserManager userManager = new UserManager();

    JLabel userRole, userName, userpw;
    JTextField userNameField;
    JPasswordField passwdField;
    JComboBox<String> roleComboBox;
    JButton loginBt,showHidePasswdBt,forgotPasswdBt;

    LoginFrame() {
        setLayout(new BorderLayout());
        setTitle("Computer Management");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("src/main/java/Icon/logo.png").getImage());

        titlePanel = new TitlePanel();
        loginPanel = new LoginPanel(this);
        add(loginPanel, BorderLayout.CENTER);
        add(titlePanel, BorderLayout.NORTH);

        setVisible(true);
    }

    public class LoginPanel extends JPanel {
        LoginFrame loginFrame;
        LoginPanel(LoginFrame loginFrame) {
            this.loginFrame = loginFrame;

            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(5, 5, 5, 5); // Tạo khoảng cách giữa các thành phần
            gbc.fill = GridBagConstraints.HORIZONTAL;

            userRole = new JLabel("User Role:");
            formatField(userRole);
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.gridy = 0;
            add(userRole, gbc);

            String[] roles = {"User", "Manager"};
            roleComboBox = new JComboBox<>(roles);
            formatField(roleComboBox);
            gbc.gridy = 1;
            add(roleComboBox, gbc);

            userName = new JLabel("User Name:");
            formatField(userName);
            gbc.gridy =2;
            add(userName, gbc);

            userNameField = new JTextField();
            formatField(userNameField);
            gbc.gridy =3;
            add(userNameField, gbc);

            userpw = new JLabel("Password:");
            formatField(userpw);
            gbc.gridy =4;
            add(userpw, gbc);

            passwdField = new JPasswordField();
            formatField(passwdField);
            passwdField.setEchoChar('*');
            gbc.gridy =5;
            add(passwdField, gbc);
            //nút hiện/ẩn mật khẩu
            showHidePasswdBt = new JButton("Show");
            formatButton(showHidePasswdBt,Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,Style.FONT_TEXT_LOGIN_FRAME,new Dimension(85, 30));

            showHidePasswdBt.addActionListener(new ActionListener() {
                private boolean isHidden = true;

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (isHidden) {
                        passwdField.setEchoChar((char) 0); // Hiện mật khẩu
                        showHidePasswdBt.setText("Hide");
                    } else {
                        passwdField.setEchoChar('*'); // Ẩn mật khẩu
                        showHidePasswdBt.setText("Show");
                    }
                    isHidden = !isHidden;
                }
            });

            add(showHidePasswdBt, gbc);

            forgotPasswdBt = new JButton("Forgot Password?");
            formatButton(forgotPasswdBt,Color.WHITE,new Font("Arial", Font.PLAIN, 12),new Dimension(100,22));
            forgotPasswdBt.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
            forgotPasswdBt.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(loginFrame, "Please check the UserManager Class or message the Manager!", "Xui:))", JOptionPane.INFORMATION_MESSAGE);
                }
            });
            gbc.gridy =6;
            add(forgotPasswdBt, gbc);


            loginBt = new JButton("Login");
            formatButton(loginBt,Style.CONFIRM_BUTTON_COLOR_GREEN,Style.FONT_BUTTON_LOGIN_FRAME,new Dimension(250, 42));

            loginBt.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    char[] passwordArray = passwdField.getPassword();//lấy mật khẩu ra và chuyển thành chuỗi
                    String passwd = new String(passwordArray);
                    //xét điều kiện để login
                    if(userNameField.getText().isEmpty() || passwd.isEmpty()) {
                        JOptionPane.showMessageDialog(loginFrame, "Please fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
//                    } else if (!userManager.isUserExists(String.valueOf(roleComboBox.getSelectedItem()), userNameField.getText(), passwd)) {
//                        JOptionPane.showMessageDialog(loginFrame, "You have entered the Wrong username or password, please re-enter!", "Error", JOptionPane.ERROR_MESSAGE);
//                    }else{
//                        if (Objects.equals(roleComboBox.getSelectedItem(), "Manager")) {
//                            loginFrame.setVisible(false);
//                            managerFrame = new ManagerFrame(loginFrame);
//                        } else if (Objects.equals(roleComboBox.getSelectedItem(), "User")) {
//                            loginFrame.setVisible(false);
//                            userFrame = new UserFrame(loginFrame);
//                        }
                    }
                }
            });
            gbc.gridy =7;
            add(loginBt,gbc);

        }
    }

    public static class TitlePanel extends JPanel {
        JLabel title,fitAva;
        ImageIcon fitIcon;

        public TitlePanel() {
            setBackground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
            setPreferredSize(new Dimension(600, 180));
            setLayout(new BorderLayout());
            //chèn ảnh FIT
            fitIcon = new ImageIcon("src/main/java/Icon/fit_nlu_logo.jpg");
            Image fitImage = fitIcon.getImage().getScaledInstance(180, 180, java.awt.Image.SCALE_SMOOTH);
            fitAva = new JLabel();
            fitAva.setIcon(new ImageIcon(fitImage));
            add(fitAva, BorderLayout.WEST);

            title = new JLabel("LOGIN", SwingConstants.CENTER);
            title.setForeground(Color.WHITE);
            title.setFont(new Font("Arial", Font.BOLD, 80));
            add(title, BorderLayout.CENTER);
        }
    }

    public void formatField(JComponent that){
        that.setPreferredSize(new Dimension(250, 35));
        that.setFont(Style.FONT_TEXT_LOGIN_FRAME);
    }
    public void formatButton(JButton button,Color background,Font font,Dimension size){
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setFont(font);
        button.setPreferredSize(size);
        button.setFocusable(false);
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}

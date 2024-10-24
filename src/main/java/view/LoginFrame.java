package view;

import controller.AccountController;
import controller.CustomerController;
import dao.AccountDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class LoginFrame extends JFrame {
    ManagerFrame managerFrame;
    CustomerFrame userFrame;
    LoginPanel loginPanel;
    TitlePanel titlePanel;

    JLabel userRole, userEmail, userpw;
    JTextField nameAndEmailInputField;
    JPasswordField passwdField;
    JComboBox<String> roleComboBox;
    JButton loginBt, forgotPasswdBt;
    JCheckBox showPasswdCB;
    final String manager = "Manager";
    final String customer = "Customer";

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
            gbc.insets = new Insets(2, 5, 2, 5);// căn chỉnh kích thước các thành phần cho vừa với khung (*top,bottom)
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.fill = GridBagConstraints.NONE;
            gbc.weightx = 0;

            // Tạo các thành phần
            userRole = new JLabel("User Role:");
            formatField(userRole);

            userEmail = new JLabel("User Email:");
            formatField(userEmail);
// data
            String[] roles = {customer, manager};
            roleComboBox = new JComboBox<>(roles);
            formatField(roleComboBox);
            roleComboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Cập nhật nội dung của JLabel khi chọn một mục trong JComboBox
                    String selectedItem = (String) roleComboBox.getSelectedItem();
                    if (selectedItem.equals("Manager")) {
                        userEmail.setText("User Name:");
                    } else {
                        userEmail.setText("User Email:");
                    }
                }
            });

            nameAndEmailInputField = new JTextField();
            formatField(nameAndEmailInputField);

            userpw = new JLabel("Password:");
            formatField(userpw);

            passwdField = new JPasswordField();
            formatField(passwdField);
            passwdField.setEchoChar('*');

            showPasswdCB = new JCheckBox("Show Password");
            showPasswdCB.setPreferredSize(new Dimension(300, 20));
            showPasswdCB.setFocusPainted(false);
            showPasswdCB.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (showPasswdCB.isSelected()) {
                        passwdField.setEchoChar((char) 0); // Hiện mật khẩu
                    } else {
                        passwdField.setEchoChar('*'); // Ẩn mật khẩu
                    }
                }
            });

            forgotPasswdBt = new JButton("Forgot Password?");
            formatButton(forgotPasswdBt, Color.WHITE, new Font("Arial", Font.PLAIN, 12), new Dimension(300, 22));
            forgotPasswdBt.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
            forgotPasswdBt.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(loginFrame, "Please check the UserManager Class or message the Manager!", "Xui:))", JOptionPane.INFORMATION_MESSAGE);
                }
            });

            loginBt = new JButton("Login");
            formatButton(loginBt, Style.CONFIRM_BUTTON_COLOR_GREEN, Style.FONT_BUTTON_LOGIN_FRAME, new Dimension(300, 42));
            loginBt.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    char[] passwordArray = passwdField.getPassword();
                    String password = new String(passwordArray);
                    String username = nameAndEmailInputField.getText();
                    String email =  nameAndEmailInputField.getText();
                    //xét điều kiện để login

                    String role = (String) roleComboBox.getSelectedItem();

                    switch (role) {
                        case manager: {
                            AccountController accountController = new AccountController();
                            System.out.println(accountController.isValidAccount(username, password));
                            if (accountController.isValidAccount(username, password)){
                                loginFrame.setVisible(false);
                                managerFrame = new ManagerFrame(loginFrame);
                            } else
                                sayError("You have entered the Wrong username or password, please try again!");

                            break;
                        }
                        case customer: {
                            CustomerController customerController = new CustomerController();
                            if (customerController.isValidAccount(email ,password)){
                                loginFrame.setVisible(false);
                                userFrame = new CustomerFrame(loginFrame);
                            } else
                                sayError("You have entered the Wrong email or password, please try again!");
                            break;
                        }

                    }


                }
                private void sayError( String message){
                    JOptionPane.showMessageDialog(loginFrame, message, "Error", JOptionPane.ERROR_MESSAGE);
                    nameAndEmailInputField.setText("");
                    passwdField.setText("");
                }
            });

            // Thêm các thành phần vào panel
            gbc.gridx = 0;
            gbc.gridy = 0;
            add(userRole, gbc);

            gbc.gridy = 1;
            add(roleComboBox, gbc);

            gbc.gridy = 2;
            add(userEmail, gbc);

            gbc.gridy = 3;
            add(nameAndEmailInputField, gbc);

            gbc.gridy = 4;
            add(userpw, gbc);

            gbc.gridy = 5;
            add(passwdField, gbc);

            gbc.gridy = 6;
            add(showPasswdCB, gbc);

            gbc.gridy = 7;
            add(forgotPasswdBt, gbc);

            gbc.gridy = 8;
            add(loginBt, gbc);
        }
    }

    public static class TitlePanel extends JPanel {
        JLabel title, fitAva;
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

    public void formatField(JComponent that) {
        that.setPreferredSize(new Dimension(300, 35));
        that.setFont(Style.FONT_TEXT_LOGIN_FRAME);
    }

    public void formatButton(JButton button, Color background, Font font, Dimension size) {
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

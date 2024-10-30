package view;

import controller.AccountController;
import controller.CustomerController;
import view.OtherComponent.CircularImage;
import view.OtherComponent.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;


public class LoginFrame extends JFrame {
    ManagerFrame managerFrame;
    CustomerFrame customerFrame;
    WelcomePanel welcomePanel = new WelcomePanel();
    InputFormPanel inputFormPanel;

    final String SignUpString = "<html>You don't have an Account?<br>Please Sign Up to connect with us!</html>";
    final String SignInString = "<html>Login with your personal info<br>to keep connected with us please!</html>";
    final String SignUpGreeting = "Hello!";
    final String SignInGreeting = "Welcome back!";


    static final String CUSTOMER_ROLE ="Customer";
    static final String MANAGER_ROLE ="Manager";


    LoginFrame() {
        setLayout(new BorderLayout());
        setTitle("Computer Management");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("src/main/java/Icon/logo.png").getImage());

        inputFormPanel = new InputFormPanel(this);

        add(welcomePanel, BorderLayout.WEST);
        add(inputFormPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    class WelcomePanel extends JPanel {
        JLabel welcomeLabel, subTextLabel;
        CircularImage fitAva;

        RoundedButton switchBt;

        ImageIcon fitIcon;
        public WelcomePanel() {
            setBackground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createEmptyBorder(0, 50, 50, 50));

            fitAva = new CircularImage("src/main/java/Icon/fit_nlu_logo.jpg",180,180,false);
            fitAva.setAlignmentX(Component.CENTER_ALIGNMENT);


            welcomeLabel = new JLabel(SignInGreeting);
            welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            welcomeLabel.setFont(Style.FONT_TITLE_LOGIN_FRAME);
            welcomeLabel.setForeground(Color.WHITE);


            subTextLabel = new JLabel(SignUpString);
            subTextLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            subTextLabel.setFont(Style.FONT_TEXT_LOGIN_FRAME);
            subTextLabel.setForeground(Color.WHITE);


            switchBt = new RoundedButton("Sign Up");
            formatRoundedButton(switchBt, Color.white, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, new Color(160, 231, 224), Style.FONT_BUTTON_LOGIN_FRAME, new Dimension(200, 40));
            switchBt.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (switchBt.getText().equals("Sign Up")) {
                        inputFormPanel.showPanel("signup");
                        switchBt.setText("Sign In");
                        welcomeLabel.setText(SignUpGreeting);
                        subTextLabel.setText(SignInString);
                    } else {
                        inputFormPanel.showPanel("signin");
                        switchBt.setText("Sign Up");
                        welcomeLabel.setText(SignInGreeting);
                        subTextLabel.setText(SignUpString);

                    }
                }
            });


            add(Box.createVerticalGlue());
            add(fitAva); // Thêm logo
            add(Box.createRigidArea(new Dimension(0, 50)));
            add(welcomeLabel);
            add(Box.createRigidArea(new Dimension(0, 10)));
            add(subTextLabel);
            add(Box.createRigidArea(new Dimension(0, 30)));
            add(switchBt);
            add(Box.createVerticalGlue());
        }


//        protected void paintComponent(Graphics grphcs) {
//            Graphics2D g2 = (Graphics2D) grphcs;
//            GradientPaint gra = new GradientPaint(0, 0, new Color(35, 166, 97), 0, getHeight(), new Color(22, 116, 66));
//            g2.setPaint(gra);
//            g2.fillRect(0, 0, getWidth(), getHeight());
//            super.paintComponent(grphcs);
//        }
    }

    class InputFormPanel extends JPanel {
        SignInPanel signInPanel;
        SignUpPanel signUpPanel;
        CardLayout cardLayout;
        LoginFrame loginFrame;
        InputFormPanel(LoginFrame loginFrame) {
            this.loginFrame = loginFrame;
            cardLayout = new CardLayout();
            setLayout(cardLayout);
            signInPanel = new SignInPanel(loginFrame);
            signUpPanel = new SignUpPanel(loginFrame);
            add(signInPanel, "signin");
            add(signUpPanel, "signup");
            cardLayout.show(this, "signin");
        }

        public void showPanel(String title) {
            cardLayout.show(this, title);
        }
    }

    class SignUpPanel extends JPanel {
        private JLabel createAccountLabel;
        private JTextField nameField, emailField, passwordField;
        private JPasswordField passwdFieldSignup;
        private RoundedButton signUpButton;
        private JCheckBox showPasswdCB;
        LoginFrame loginFrame;

        SignUpPanel(LoginFrame loginFrame) {
            this.loginFrame = loginFrame;
//            setBorder(BorderFactory.createEmptyBorder(100, 50, 50, 50));
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;


            createAccountLabel = new JLabel("Create Account");
            createAccountLabel.setFont(Style.FONT_TITLE_LOGIN_FRAME);
            createAccountLabel.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
            createAccountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            add(createAccountLabel, gbc);

            // Name Field with Icon
            gbc.gridwidth = 1;
            gbc.gridy++;
            gbc.gridx = 0;
            JLabel nameIcon = new JLabel(new ImageIcon("src/main/java/Icon/user_icon.png"));
            nameIcon.setPreferredSize(new Dimension(30, 30));
            add(nameIcon, gbc);

            gbc.gridx = 1;
            nameField = new JTextField("User Name");
            nameField.setForeground(Color.GRAY);
            nameField.setPreferredSize(new Dimension(250, 40));
            nameField.setFont(Style.FONT_TEXT_LOGIN_FRAME);
            addFocusListenerForTextField(nameField, "User Name");
            add(nameField, gbc);

            // Email Field with Icon
            gbc.gridy++;
            gbc.gridx = 0;
            JLabel emailIcon = new JLabel(new ImageIcon("src/main/java/Icon/email_icon.png"));
            emailIcon.setPreferredSize(new Dimension(30, 30));
            add(emailIcon, gbc);

            gbc.gridx = 1;
            emailField = new JTextField("User Email");
            emailField.setForeground(Color.GRAY);
            emailField.setPreferredSize(new Dimension(250, 40));
            emailField.setFont(Style.FONT_TEXT_LOGIN_FRAME);
            addFocusListenerForTextField(emailField, "User Email");
            add(emailField, gbc);

            // Password Field with Icon
            gbc.gridy++;
            gbc.gridx = 0;
            JLabel passwordIcon = new JLabel(new ImageIcon("src/main/java/Icon/lock_icon.png"));
            passwordIcon.setPreferredSize(new Dimension(30, 30));
            add(passwordIcon, gbc);

            gbc.gridx = 1;

            passwdFieldSignup = new JPasswordField("Password");
            passwdFieldSignup.setForeground(Color.GRAY);
            passwdFieldSignup.setPreferredSize(new Dimension(250, 40));
            passwdFieldSignup.setFont(Style.FONT_TEXT_LOGIN_FRAME);
            passwdFieldSignup.setEchoChar((char) 0);
            addFocusListenerForJPasswordField(passwdFieldSignup, "Password");
            add(passwdFieldSignup, gbc);

            gbc.gridy++;
            gbc.gridx = 1;
            showPasswdCB = new JCheckBox("Show Password");
            showPasswdCB.setPreferredSize(new Dimension(250, 15));
            showPasswdCB.setFocusPainted(false);
            showPasswdCB.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
            showPasswdCB.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String passwd = new String(passwdFieldSignup.getPassword());
                    if(passwd.equals("Password")){
                        showPasswdCB.setSelected(false);
                    }else{
                        if (showPasswdCB.isSelected()) {
                            passwdFieldSignup.setEchoChar((char) 0); // Hiện mật khẩu
                        } else {
                            passwdFieldSignup.setEchoChar('*'); // Ẩn mật khẩu
                        }
                    }
                }
            });
            add(showPasswdCB, gbc);

            // Sign Up button
            gbc.gridy++;
            gbc.gridx = 0;
            gbc.gridwidth = 2;
            signUpButton = new RoundedButton("Sign Up");
            formatRoundedButton(signUpButton, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Color.white, new Color(160, 231, 224), Style.FONT_BUTTON_LOGIN_FRAME, new Dimension(200, 40));
            signUpButton.setBackground(new Color(0, 153, 102));
            signUpButton.setForeground(Color.WHITE);
            signUpButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(loginFrame, "Em chưa làm:(((", "", JOptionPane.INFORMATION_MESSAGE);
                }
            });
            add(signUpButton, gbc);
        }
    }


    class SignInPanel extends JPanel {
        private LoginFrame loginFrame;

        private JLabel signInLabel;
        private JTextField nameField, emailField;
        private JPasswordField passwdFieldSignin;
        private JComboBox<String> roleComboBox;
        private RoundedButton signInButton;
        private JCheckBox showPasswdCB;

        SignInPanel(LoginFrame loginFrame) {
            this.loginFrame = loginFrame;
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;


            // Sign In label
            signInLabel = new JLabel("       Sign In");
            signInLabel.setFont(Style.FONT_TITLE_LOGIN_FRAME);
            signInLabel.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            add(signInLabel, gbc);

            //Role comboBox with Icon
            gbc.gridwidth = 1;
            gbc.gridy++;
            gbc.gridx = 0;
            JLabel roleIcon = new JLabel(new ImageIcon("src/main/java/Icon/role_icon.png"));
            roleIcon.setPreferredSize(new Dimension(30, 30));
            add(roleIcon, gbc);
            roleComboBox = new JComboBox<>(new String[]{CUSTOMER_ROLE, MANAGER_ROLE});
            roleComboBox.setPreferredSize(new Dimension(250, 40));
            roleComboBox.setFont(Style.FONT_TEXT_LOGIN_FRAME);
            gbc.gridx = 1;
            add(roleComboBox, gbc);

            // Name Field with Icon
            gbc.gridwidth = 1;
            gbc.gridy++;
            gbc.gridx = 0;
            JLabel nameIcon = new JLabel(new ImageIcon("src/main/java/Icon/user_icon.png"));
            nameIcon.setPreferredSize(new Dimension(30, 30));
            add(nameIcon, gbc);

            gbc.gridx = 1;
            nameField = new JTextField("User Name");
            nameField.setForeground(Color.GRAY);
            addFocusListenerForTextField(nameField, "User Name");
            nameField.setFont(Style.FONT_TEXT_LOGIN_FRAME);  // Tăng kích thước chữ của text field
            nameField.setPreferredSize(new Dimension(250, 40));  // Tăng kích thước text field
            add(nameField, gbc);

            // Password Field with Icon
            gbc.gridy++;
            gbc.gridx = 0;
            JLabel passwordIcon = new JLabel(new ImageIcon("src/main/java/Icon/lock_icon.png"));
            passwordIcon.setPreferredSize(new Dimension(30, 30));
            add(passwordIcon, gbc);

            gbc.gridx = 1;
            passwdFieldSignin = new JPasswordField("Password");
            passwdFieldSignin.setForeground(Color.GRAY);
            passwdFieldSignin.setEchoChar((char) 0);
            addFocusListenerForJPasswordField(passwdFieldSignin, "Password");
            passwdFieldSignin.setFont(Style.FONT_TEXT_LOGIN_FRAME);
            passwdFieldSignin.setPreferredSize(new Dimension(250, 40));
            add(passwdFieldSignin, gbc);

            gbc.gridy++;
            gbc.gridx = 1;
            showPasswdCB = new JCheckBox("Show Password");
            showPasswdCB.setPreferredSize(new Dimension(250, 15));
            showPasswdCB.setFocusPainted(false);
            showPasswdCB.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
            showPasswdCB.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String passwd = new String(passwdFieldSignin.getPassword());
                    if(passwd.equals("Password")){
                        showPasswdCB.setSelected(false);
                    }else{
                        if (showPasswdCB.isSelected()) {
                            passwdFieldSignin.setEchoChar((char) 0); // Hiện mật khẩu
                        } else {
                            passwdFieldSignin.setEchoChar('*'); // Ẩn mật khẩu
                        }
                    }
                }
            });
            add(showPasswdCB, gbc);

            // Sign In button
            gbc.gridy++;
            gbc.gridx = 0;
            gbc.gridwidth = 2;
            signInButton = new RoundedButton("Sign In");
            formatRoundedButton(signInButton, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Color.white, new Color(160, 231, 224), Style.FONT_BUTTON_LOGIN_FRAME, new Dimension(200, 40));
            signInButton.setBackground(new Color(0, 153, 102));
            signInButton.setForeground(Color.WHITE);
            signInButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String username= nameField.getText();
                    String password= new String( passwdFieldSignin.getPassword());
                    String email ="";
                    switch ((String) roleComboBox.getSelectedItem()) {
                        case MANAGER_ROLE: {

                            AccountController accountController = new AccountController();
                            System.out.println(accountController.isValidAccount(username, password));
                            if (accountController.isValidAccount(username, password)){
                                loginFrame.setVisible(false);
                                managerFrame = new ManagerFrame();
                            } else {
                                sayError("You have entered the Wrong username or password, please try again!");
                                loginFrame.setVisible(true);
                            }
                            break;
                        }
                        case CUSTOMER_ROLE: {
                            loginFrame.setVisible(false);
                            CustomerController customerController = new CustomerController();
                            if (customerController.isValidAccount(email ,password)){
                                loginFrame.setVisible(false);
                                managerFrame = new ManagerFrame();
//                                userFrame = new CustomerFrame(loginFrame);
                            } else
                                sayError("You have entered the Wrong email or password, please try again!");
                            break;
                        }

                    }
                }
                private void sayError( String message){
                    JOptionPane.showMessageDialog(loginFrame, message, "Error", JOptionPane.ERROR_MESSAGE);
                    passwdFieldSignin.setText("");
                    nameField.setText("");
                }
            });

            add(signInButton, gbc);

        }
    }

    public void addFocusListenerForTextField(JTextField field, String originText) {
        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // Khi người dùng nhấn vào JTextField, nếu vẫn là chữ giống originText, nó sẽ biến mất
                if (field.getText().equals(originText)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Khi người dùng rời khỏi JTextField mà chưa nhập gì, sẽ hiển thị lại chữ giống originText
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(originText);
                }
            }
        });
    }

    public void addFocusListenerForJPasswordField(JPasswordField passwdfield, String originText) {
        passwdfield.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                String passwd = new String(passwdfield.getPassword());
                // Khi người dùng nhấn vào JTextField, nếu vẫn là chữ giống originText, nó sẽ biến mất
                if (passwd.equals(originText)) {
                    passwdfield.setText("");
                    passwdfield.setEchoChar('*');
                    passwdfield.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                String passwd = new String(passwdfield.getPassword());
                // Khi người dùng rời khỏi JTextField mà chưa nhập gì, sẽ hiển thị lại chữ giống originText
                if (passwd.isEmpty()) {
                    passwdfield.setText(originText);
                    passwdfield.setEchoChar((char) 0);
                    passwdfield.setForeground(Color.GRAY);
                }
            }
        });
    }

    public void formatRoundedButton(RoundedButton button, Color background, Color textColor, Color hoverColor, Font font, Dimension size) {
        button.setCustomAlignmentX(Component.CENTER_ALIGNMENT);
        button.setButtonSize(size);
        button.setBackgroundColor(background);
        button.setTextColor(textColor);
        button.setHoverColor(hoverColor);
        button.setFont(font);
    }


    public static void main(String[] args) {
        new LoginFrame();
    }
}

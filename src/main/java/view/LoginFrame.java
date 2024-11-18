package view;

import Config.EmailConfig;
import Model.Account;
import Model.Customer;
import Model.Manager;
import Verifier.EmailVerifier;
import controller.AccountController;
import controller.CustomerController;
import controller.ManagerController;
import view.OverrideComponent.CircularImage;
import view.OverrideComponent.CustomButton;
import view.OverrideComponent.ToastNotification;
import Enum.*;

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class LoginFrame extends JFrame {
    ManagerFrame managerFrame;
    CustomerFrame customerFrame;
    WelcomePanel welcomePanel = new WelcomePanel();
    InputFormPanel inputFormPanel;

    final String SignUpString = "<html>You don't have an Account?<br>Please Sign Up to connect with us!</html>";
    final String SignInString = "<html>Login with your personal info<br>to keep connected with us please!</html>";
    final String SignUpGreeting = "Hello!";
    final String SignInGreeting = "Welcome back!";

    static final String CUSTOMER_ROLE = "Customer";
    static final String MANAGER_ROLE = "Manager";

    private static CustomerController customerController;
    private static AccountController accountController;

    private int id = 0;

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
        CustomButton switchBt;

        public WelcomePanel() {
            setPreferredSize(new Dimension(430, 600));
            setBackground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createEmptyBorder(0, 50, 50, 50));

            fitAva = new CircularImage("src/main/java/Icon/fit_nlu_logo.jpg", 200, 200, false);
            fitAva.setAlignmentX(Component.CENTER_ALIGNMENT);


            welcomeLabel = new JLabel(SignInGreeting);
            welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            welcomeLabel.setFont(Style.FONT_TITLE_BOLD_40);
            welcomeLabel.setForeground(Color.WHITE);


            subTextLabel = new JLabel(SignUpString);
            subTextLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            subTextLabel.setFont(Style.FONT_TEXT_LOGIN_FRAME);
            subTextLabel.setForeground(Color.WHITE);

            switchBt = createCustomButtonWithBorder("Sign Up", Style.FONT_BUTTON_LOGIN_FRAME, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Color.white, new Color(160, 231, 224), Color.white, 1, 20, new Dimension(150, 38));
            switchBt.setAlignmentX(Component.CENTER_ALIGNMENT);
            switchBt.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (switchBt.getText().equals("Sign Up")) {
                        inputFormPanel.showPanel("signUp");
                        switchBt.setText("Sign In");
                        welcomeLabel.setText(SignUpGreeting);
                        subTextLabel.setText(SignInString);
                    } else {
                        inputFormPanel.showPanel("signIn");
                        switchBt.setText("Sign Up");
                        welcomeLabel.setText(SignInGreeting);
                        subTextLabel.setText(SignUpString);

                    }
                }
            });
            JPanel buttonPanel = new JPanel();
            buttonPanel.setPreferredSize(new Dimension(150, 50));
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
            buttonPanel.setOpaque(false);
            buttonPanel.add(Box.createHorizontalGlue());
            buttonPanel.add(switchBt);
            buttonPanel.add(Box.createHorizontalGlue());


            add(Box.createVerticalGlue());
            add(fitAva);
            add(Box.createRigidArea(new Dimension(0, 50)));
            add(welcomeLabel);
            add(Box.createRigidArea(new Dimension(0, 10)));
            add(subTextLabel);
            add(Box.createRigidArea(new Dimension(0, 30)));
            add(buttonPanel);
            add(Box.createVerticalGlue());
        }

    }

    class InputFormPanel extends JPanel {
        SignInPanel signInPanel;
        SignUpPanel signUpPanel;
        ForgotPasswdPanel forgotPasswdPanel;
        CardLayout cardLayoutMain;
        LoginFrame loginFrame;

        InputFormPanel(LoginFrame loginFrame) {
            this.loginFrame = loginFrame;
            cardLayoutMain = new CardLayout();
            setLayout(cardLayoutMain);
            signInPanel = new SignInPanel(loginFrame);
            signUpPanel = new SignUpPanel(loginFrame);
            forgotPasswdPanel = new ForgotPasswdPanel();

            add(signInPanel, "signIn");
            add(signUpPanel, "signUp");
            add(forgotPasswdPanel, "forgotPasswd");
            cardLayoutMain.show(this, "signIn");
        }

        public void showPanel(String title) {
            cardLayoutMain.show(this, title);
        }
    }

    class SignUpPanel extends JPanel {
        private JLabel createAccountLabel;
        private JTextField nameField, emailField, passwordField;
        private JPasswordField passwdFieldSignup;
        private CustomButton signUpButton;
        private JCheckBox showPasswdCB;
        LoginFrame loginFrame;

        SignUpPanel(LoginFrame loginFrame) {
            this.loginFrame = loginFrame;
            setLayout(new GridBagLayout());
            setBackground(Color.WHITE);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;


            createAccountLabel = new JLabel("Create Account", SwingConstants.CENTER);
            createAccountLabel.setFont(Style.FONT_TITLE_BOLD_45);
            createAccountLabel.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            add(createAccountLabel, gbc);

            //create sign up Button
            signUpButton = createCustomButtonWithBorder("Sign Up", Style.FONT_BUTTON_LOGIN_FRAME, Color.white, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, new Color(160, 231, 224), Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1, 20, new Dimension(350, 45));
            signUpButton.setBackground(new Color(0, 153, 102));
            signUpButton.setForeground(Style.WORD_COLOR_WHITE);
            signUpButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String username = nameField.getText();
                    String password = new String(passwdFieldSignup.getPassword());
                    String email = emailField.getText();

                    if (username.isEmpty() || password.isEmpty() || username.equals("User Name") || password.equals("Password") || email.isEmpty() || email.equals("User Email")) {
                        if (username.isEmpty() || username.equals("User Name")) {
                            nameField.setBorder(BorderFactory.createLineBorder(Style.DELETE_BUTTON_COLOR_RED, 4));
                            nameField.setForeground(Style.DELETE_BUTTON_COLOR_RED);
                        }
                        if (password.isEmpty() || password.equals("Password")) {
                            passwdFieldSignup.setBorder(BorderFactory.createLineBorder(Style.DELETE_BUTTON_COLOR_RED, 4));
                            passwdFieldSignup.setForeground(Style.DELETE_BUTTON_COLOR_RED);
                        }
                        if (email.isEmpty() || email.equals("User Email")) {
                            emailField.setBorder(BorderFactory.createLineBorder(Style.DELETE_BUTTON_COLOR_RED, 4));
                            emailField.setForeground(Style.DELETE_BUTTON_COLOR_RED);
                        }
                    } else {
                        JOptionPane.showMessageDialog(loginFrame, "Em chưa làm:(((", "", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            });

            // Name Field with Icon
            gbc.gridwidth = 1;
            gbc.gridy++;
            gbc.gridx = 0;
            JLabel nameIcon = new JLabel(new ImageIcon("src/main/java/Icon/user_icon.png"));
            nameIcon.setPreferredSize(new Dimension(30, 30));
            add(nameIcon, gbc);

            gbc.gridx = 1;
            nameField = createTextField("User Name", Style.FONT_TEXT_LOGIN_FRAME, Color.GRAY, new Dimension(300, 45));
            nameField.addActionListener(e -> signUpButton.doClick());
            add(nameField, gbc);

            // Email Field with Icon
            gbc.gridy++;
            gbc.gridx = 0;
            JLabel emailIcon = new JLabel(new ImageIcon("src/main/java/Icon/email_icon.png"));
            emailIcon.setPreferredSize(new Dimension(30, 30));
            add(emailIcon, gbc);

            gbc.gridx = 1;
            emailField = createTextField("User Email", Style.FONT_TEXT_LOGIN_FRAME, Color.GRAY, new Dimension(300, 45));
            emailField.addActionListener(e -> signUpButton.doClick());
            add(emailField, gbc);

            // Password Field with Icon
            gbc.gridy++;
            gbc.gridx = 0;
            JLabel passwordIcon = new JLabel(new ImageIcon("src/main/java/Icon/lock_icon.png"));
            passwordIcon.setPreferredSize(new Dimension(30, 30));
            add(passwordIcon, gbc);

            gbc.gridx = 1;

            passwdFieldSignup = createPasswordField("Password", Style.FONT_TEXT_LOGIN_FRAME, Color.GRAY, new Dimension(300, 45));
            passwdFieldSignup.addActionListener(e -> signUpButton.doClick());
            add(passwdFieldSignup, gbc);

            gbc.gridy++;
            gbc.gridx = 1;
            showPasswdCB = new JCheckBox("Show Password");
            showPasswdCB.setPreferredSize(new Dimension(250, 15));
            showPasswdCB.setFocusPainted(false);
            showPasswdCB.setFocusable(false);
            showPasswdCB.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
            showPasswdCB.setBackground(Color.WHITE);
            showPasswdCB.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String passwd = new String(passwdFieldSignup.getPassword());
                    if (passwd.equals("Password")) {
                        showPasswdCB.setSelected(false);
                    } else {
                        if (showPasswdCB.isSelected()) {
                            passwdFieldSignup.setEchoChar((char) 0); // Hiện mật khẩu
                        } else {
                            passwdFieldSignup.setEchoChar('*'); // Ẩn mật khẩu
                        }
                    }
                }
            });
            add(showPasswdCB, gbc);

            //add Sign Up button
            gbc.gridy++;
            gbc.gridx = 0;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            add(signUpButton, gbc);
        }
    }

    class SignInPanel extends JPanel {
        private LoginFrame loginFrame;
        private JLabel signInLabel;
        private JTextField nameField, emailField;
        private JPasswordField passwdFieldSignin;
        private JComboBox<String> roleComboBox;
        private CustomButton signInButton;
        private JCheckBox showPasswdCB;
        private JButton forgotPasswdBt;

        SignInPanel(LoginFrame loginFrame) {
            this.loginFrame = loginFrame;
            setLayout(new GridBagLayout());
            setBackground(Color.WHITE);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // Sign In label
            signInLabel = new JLabel("Sign In", SwingConstants.CENTER);
            signInLabel.setFont(Style.FONT_TITLE_BOLD_45);
            signInLabel.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            add(signInLabel, gbc);

            // create  sign in Button
            signInButton = createCustomButtonWithBorder("Sign In", Style.FONT_BUTTON_LOGIN_FRAME, Color.white, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, new Color(160, 231, 224), Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1, 20, new Dimension(350, 45));
//            signInButton.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    String username = nameField.getText();
//                    String password = new String(passwdFieldSignin.getPassword());
//                    String email = "";
//
//                    if (username.isEmpty() || password.isEmpty() || username.equals("User Name") || password.equals("Password")) {
//                        if (username.isEmpty() || username.equals("User Name")) {
//                            nameField.setBorder(BorderFactory.createLineBorder(Style.DELETE_BUTTON_COLOR_RED, 4));
//                            nameField.setForeground(Style.DELETE_BUTTON_COLOR_RED);
//                        }
//                        if (password.isEmpty() || password.equals("Password")) {
//                            passwdFieldSignin.setBorder(BorderFactory.createLineBorder(Style.DELETE_BUTTON_COLOR_RED, 4));
//                            passwdFieldSignin.setForeground(Style.DELETE_BUTTON_COLOR_RED);
//                        }
//
//                    } else {
//                        switch ((String) roleComboBox.getSelectedItem()) {
//                            case MANAGER_ROLE: {
//                                accountController = new AccountController();
//                                System.out.println(accountController.isValidAccount(username, password));
//                                if (accountController.isValidAccount(username, password)) {
//                                    loginFrame.setVisible(false);
//                                    managerFrame = new ManagerFrame(loginFrame);
//                                } else {
//                                    sayError("You have entered the Wrong username or password, please try again!");
//                                }
//                                break;
//                            }
//                            case CUSTOMER_ROLE: {
//                                customerController = new CustomerController();
//                                if (customerController.isValidAccount(email, password)) {
//
////                                    loginFrame.setVisible(false);
////                                userFrame = new CustomerFrame(loginFrame);
//                                } else
//                                    sayError("You have entered the Wrong email or password, please try again!");
//                                break;
//                            }
//
//                        }
//                    }
//                }
//            });

            //Role comboBox with Icon
            gbc.gridwidth = 1;
            gbc.gridy++;
            gbc.gridx = 0;
            JLabel roleIcon = new JLabel(new ImageIcon("src/main/java/Icon/role_icon.png"));
            roleIcon.setPreferredSize(new Dimension(30, 30));
            add(roleIcon, gbc);
            roleComboBox = new JComboBox<>(new String[]{CUSTOMER_ROLE, MANAGER_ROLE});
            roleComboBox.setPreferredSize(new Dimension(300, 45));
            roleComboBox.setFont(Style.FONT_TEXT_LOGIN_FRAME);
            roleComboBox.setBackground(Color.WHITE);
            roleComboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (roleComboBox.getSelectedItem().equals("Customer")) {
                        nameField.setText("User Email");
                    } else {
                        nameField.setText("User Name");
                    }
                }
            });
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
            nameField = new JTextField("User Email");

            nameField = createTextField("User Email", Style.FONT_TEXT_LOGIN_FRAME, Color.GRAY, new Dimension(300, 45));
            nameField.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (nameField.getText().equals("User Email") || nameField.getText().equals("User Name")) {
                        nameField.setText("");
                        nameField.setForeground(Color.BLACK);
                    }
                    nameField.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 4));
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (nameField.getText().isEmpty()) {
                        nameField.setForeground(Color.GRAY);
                        if (roleComboBox.getSelectedItem().equals("Customer")) {
                            nameField.setText("User Email");
                        } else {
                            nameField.setText("User Name");
                        }
                    }
                    nameField.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1));
                }
            });
            nameField.addActionListener(e -> signInButton.doClick());
            add(nameField, gbc);

            // Password Field with Icon
            gbc.gridy++;
            gbc.gridx = 0;
            JLabel passwordIcon = new JLabel(new ImageIcon("src/main/java/Icon/lock_icon.png"));
            passwordIcon.setPreferredSize(new Dimension(30, 30));
            add(passwordIcon, gbc);

            gbc.gridx = 1;
            passwdFieldSignin = createPasswordField("Password", Style.FONT_TEXT_LOGIN_FRAME, Color.GRAY, new Dimension(300, 45));
            passwdFieldSignin.addActionListener(e -> signInButton.doClick());
            add(passwdFieldSignin, gbc);

            gbc.gridy++;
            gbc.gridx = 1;
            showPasswdCB = new JCheckBox("Show Password");
            showPasswdCB.setPreferredSize(new Dimension(300, 15));
            showPasswdCB.setFocusPainted(false);
            showPasswdCB.setFocusable(false);
            showPasswdCB.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
            showPasswdCB.setBackground(Color.WHITE);
            showPasswdCB.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String passwd = new String(passwdFieldSignin.getPassword());
                    if (passwd.equals("Password")) {
                        showPasswdCB.setSelected(false);
                    } else {
                        if (showPasswdCB.isSelected()) {
                            passwdFieldSignin.setEchoChar((char) 0); // Hiện mật khẩu
                        } else {
                            passwdFieldSignin.setEchoChar('*'); // Ẩn mật khẩu
                        }
                    }
                }
            });
            add(showPasswdCB, gbc);

            // add Sign In button
            gbc.gridy++;
            gbc.gridx = 0;
            gbc.gridwidth = 2;
            signInButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String username = nameField.getText();
                    String password = new String(passwdFieldSignin.getPassword());
                    String email = nameField.getText();// bug NullPointerException
                    if (username.isEmpty() || password.isEmpty() || username.equals("User Name") || password.equals("Password")) {
                        if (username.isEmpty() || username.equals("User Name")) {
                            nameField.setBorder(BorderFactory.createLineBorder(Style.DELETE_BUTTON_COLOR_RED, 4));
                            nameField.setForeground(Style.DELETE_BUTTON_COLOR_RED);
                        }
                        if (password.isEmpty() || password.equals("Password")) {
                            passwdFieldSignin.setBorder(BorderFactory.createLineBorder(Style.DELETE_BUTTON_COLOR_RED, 4));
                            passwdFieldSignin.setForeground(Style.DELETE_BUTTON_COLOR_RED);
                        }

                    } else {
                        switch ((String) roleComboBox.getSelectedItem()) {
                            case MANAGER_ROLE: {
                                AccountController accountController = new AccountController();
                                System.out.println(accountController.isValidAccount(username, password));
                                if (accountController.isValidAccount(username, password)) {
                                    loginFrame.setVisible(false);
                                    managerFrame = new ManagerFrame(loginFrame);
                                } else {
                                    sayError(AccountController.loginStatus.getMessager());
                                }
                                break;
                            }
                            case CUSTOMER_ROLE: {
                                CustomerController customerController = new CustomerController();
                                System.out.println(emailField.getText());
                                if (customerController.isValidAccount(email, password)) {
                                    loginFrame.setVisible(false);
                                    customerFrame = new CustomerFrame();
                                } else
                                    sayError(CustomerController.loginStatus.getMessager());
                                break;
                            }

                        }
                    }
                }
            });
            add(signInButton, gbc);

            forgotPasswdBt = new JButton("You forgot your Password?");
            forgotPasswdBt.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
            forgotPasswdBt.setBackground(Color.WHITE);
            forgotPasswdBt.setFocusable(false);
            forgotPasswdBt.setBorderPainted(false);
            forgotPasswdBt.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    forgotPasswdBt.setBackground(Style.LIGHT_BlUE);
                }

                public void mouseExited(MouseEvent evt) {
                    forgotPasswdBt.setBackground(Color.WHITE);
                }
            });
            forgotPasswdBt.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    inputFormPanel.showPanel("forgotPasswd");
                    inputFormPanel.forgotPasswdPanel.inputEmail.emailField.setForeground(Color.GRAY);
                    inputFormPanel.forgotPasswdPanel.inputEmail.emailField.setText("User Email");
                }
            });
            gbc.gridy++;
            gbc.gridx = 0;
            gbc.gridwidth = 2;
            add(forgotPasswdBt, gbc);

        }

        private void sayError(String message) {
            JOptionPane.showMessageDialog(loginFrame, message, "Error", JOptionPane.ERROR_MESSAGE);
            passwdFieldSignin.setText("");
            nameField.setText("");
        }
    }

    class ForgotPasswdPanel extends JPanel {
        InputEmail inputEmail;
        VerificationCodePanel verificationCodePanel;
        SetNewPasswdPanel setNewPasswdPanel;
        CardLayout cardLayoutForgotPass;
        private int otp = 0;
        private String to="";
        private String name="";
        private ForgotPasswordStatus forgotPasswordStatus;

        ForgotPasswdPanel() {
            setBackground(Color.WHITE);
            cardLayoutForgotPass = new CardLayout();
            setLayout(cardLayoutForgotPass);

            inputEmail = new InputEmail();
            verificationCodePanel = new VerificationCodePanel();
            setNewPasswdPanel = new SetNewPasswdPanel();
            add(inputEmail, "inputEmail");
            add(verificationCodePanel, "verificationCode");
            add(setNewPasswdPanel, "setNewPasswd");

            cardLayoutForgotPass.show(this, "inputEmail");
        }

        public void showInnerPanel(String message) {
            cardLayoutForgotPass.show(this, message);
        }

        private boolean sendOTP(String to, String name, int theOtp){
            EmailConfig emailConfig = new EmailConfig();
            return emailConfig.send(
                        to
                        , emailConfig.buildHeaderMessage()
                        , emailConfig.buildBodyMessage(name, theOtp)
                );
        }

        class InputEmail extends JPanel {
            JTextField emailField;
            CustomButton sendCodeBt, backBt;
            JCheckBox customerCBox, managerCBox;



            // fix ow day
            private void setTextForTextField() {
                if (customerCBox.isSelected()) {
                    emailField.setText("Email");
                } else {
                    emailField.setText("Username");
                }
//                emailField.setInputVerifier(new EmailVerifier());
            }
            private void setColorTextField(){
                emailField.setBorder(BorderFactory.createLineBorder(Style.DELETE_BUTTON_COLOR_RED, 4));
            }

            InputEmail() {
                setBackground(Color.WHITE);
                setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(20, 10, 20, 10);
                gbc.fill = GridBagConstraints.HORIZONTAL;


                // Sign In label
                JLabel resetPasswdLb = new JLabel("Reset Password", SwingConstants.CENTER);
                resetPasswdLb.setFont(Style.FONT_TITLE_BOLD_45);
                resetPasswdLb.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.gridwidth = 2;
                gbc.anchor = GridBagConstraints.CENTER;
                add(resetPasswdLb, gbc);


                //check customer or manager fix di a Hoang
                ButtonGroup group = new ButtonGroup();
                customerCBox = new JCheckBox("Customer");
                customerCBox.setBorderPainted(false);
                customerCBox.setBackground(Style.WORD_COLOR_WHITE);
                managerCBox = new JCheckBox("Manager");
                managerCBox.setBorderPainted(false);
                managerCBox.setBackground(Style.WORD_COLOR_WHITE);
                managerCBox.addActionListener(e -> setTextForTextField());
                customerCBox.addActionListener(e -> setTextForTextField());
                group.add(customerCBox);
                group.add(managerCBox);
                JPanel checkBoxPanel = new JPanel();
                checkBoxPanel.setLayout(new FlowLayout());
                checkBoxPanel.setBackground(Style.WORD_COLOR_WHITE);
                checkBoxPanel.add(managerCBox);
                checkBoxPanel.add(customerCBox);
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.gridwidth = 2;
                add(checkBoxPanel, gbc);

                // create send code button
                sendCodeBt = createCustomButtonWithBorder("Send Code", Style.FONT_BUTTON_LOGIN_FRAME, Color.white, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, new Color(160, 231, 224), Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1, 20, new Dimension(350, 50));
                sendCodeBt.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        EmailConfig emailConfig = new EmailConfig();
                        if (customerCBox.isSelected()) {
                            //customer
                            String email = emailField.getText().trim();
                            CustomerController customerController = new CustomerController();
                            Customer customer = customerController.findByEmail(email);
                            if (customer != null && customer.sameEmail(email)) {
                                otp = emailConfig.generateOTP();
                                to =email;
                                name = customer.getFullName();
                                id = customer.getId();
                                boolean sent = sendOTP(to,name,otp);
                                forgotPasswordStatus = ForgotPasswordStatus.CUSTOMER;
                                if (sent) {
                                    showInnerPanel("verificationCode");
                                }
                            }else {
                                setColorTextField();
                            }
                        } else if(managerCBox.isSelected()) {
                            //manager
                            AccountController accountController = new AccountController();
                            String username = emailField.getText().trim();
                            Account account = accountController.findByName(username);
                            if ( account != null && account.sameUsername(username) ){
                                otp = emailConfig.generateOTP();
                                System.out.println(account);
                                System.out.println(otp);
                                to= account.getEmail();
                                name = account.getEmail();
                                id = account.getId();
                                boolean sent =  sendOTP(to,name,otp);
                                forgotPasswordStatus = ForgotPasswordStatus.MANAGER;
                                 if(sent){
                                    showInnerPanel("verificationCode");
                                 }

                            }else {
                                setColorTextField();
                            }
                        }


                    }
                });

                // email field
                gbc.gridwidth = 1;
                gbc.gridy++;
                gbc.gridx = 0;
                JLabel emailIcon = new JLabel(new ImageIcon("src/main/java/Icon/email_icon.png"));
                emailIcon.setPreferredSize(new Dimension(30, 30));
                add(emailIcon, gbc);
                gbc.gridx = 1;
                emailField = createTextField("Customer or Manager", Style.FONT_TEXT_LOGIN_FRAME, Color.GRAY, new Dimension(300, 45));

                emailField.addActionListener(e -> {

                    sendCodeBt.doClick();

                });
                add(emailField, gbc);

                // add send code button to panel
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.gridy++;
                gbc.gridx = 0;
                gbc.gridwidth = 2;
                add(sendCodeBt, gbc);

                backBt = createCustomButtonWithBorder("Back", Style.FONT_BUTTON_LOGIN_FRAME, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Color.white, new Color(160, 231, 224), Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1, 20, new Dimension(350, 50));
                backBt.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        inputFormPanel.showPanel("signIn");
                    }
                });
                gbc.gridy++;
                add(backBt, gbc);
            }
        }


        class VerificationCodePanel extends JPanel {

            JTextField[] otpFields = new JTextField[4];
            CustomButton verifyBt, backBt;
            JButton resendCodeBt;
            final int RESET_OTP =0;

            private  void setColorTextField(){
                for (int i = 0; i < otpFields.length; i++) {
                    otpFields[i].setBorder(BorderFactory.createLineBorder(Style.DELETE_BUTTON_COLOR_RED, 4));
                }
            }

            VerificationCodePanel() {
                setBackground(Color.WHITE);
                setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.fill = GridBagConstraints.HORIZONTAL;

                // reset Password label
                JLabel resetPasswdLb = new JLabel("Enter Verification Code", SwingConstants.CENTER);
                resetPasswdLb.setFont(Style.FONT_TITLE_BOLD_45);
                resetPasswdLb.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.anchor = GridBagConstraints.CENTER;
                add(resetPasswdLb, gbc);

                // create verifyBt
                verifyBt = createCustomButtonWithBorder("Verify", Style.FONT_BUTTON_LOGIN_FRAME, Color.white, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, new Color(160, 231, 224), Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1, 20, new Dimension(350, 50));
                verifyBt.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        boolean isEmpty = true;
                        for (int i = 0; i < otpFields.length; i++) {
                            if (otpFields[i].getText().trim().isEmpty()) {
                                otpFields[i].setBorder(BorderFactory.createLineBorder(Style.DELETE_BUTTON_COLOR_RED, 4));
                                isEmpty = true;
                            } else {
                                isEmpty = false;
                            }
                        }

                    }


                });
                verifyBt.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        boolean isEmpty = false;
                        int otpInput = 0;
                        try {
                            for (int i = 0; i < otpFields.length; i++) {
                                int num = Integer.parseInt(otpFields[i].getText().trim());
                                otpInput += num;
                                if (i < otpFields.length - 1)
                                    otpInput *= 10;
                            }
                        }catch (NullPointerException exception){
                            System.out.println(exception);
                            setColorTextField();

                        }

                        System.out.println(otpInput + "   " + otp);
                        if (otp == otpInput) {
                            showInnerPanel("setNewPasswd");
                            otp = RESET_OTP;
                            for (int i = 0; i < otpFields.length; i++) {
                                otpFields[i].setText("");
                            }
                        } else {
                            System.out.println("sai roi ");
                            setColorTextField();
                            ToastNotification.showToast("sai opt",2500,200,100);

                        }
                    }

                    private static void resetPasswdField(JPasswordField passwordField, String placeholder) {
                        passwordField.setForeground(Color.GRAY);
                        passwordField.setText(placeholder);
                        passwordField.setEchoChar((char) 0);
                    }
                });

                //create re-send code button
                resendCodeBt = new JButton("Re-send Verify Code");
                resendCodeBt.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
                resendCodeBt.setBackground(Color.WHITE);
                resendCodeBt.setFont(Style.FONT_TEXT_TABLE);
                resendCodeBt.setFocusable(false);
                resendCodeBt.setBorderPainted(false);
                resendCodeBt.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent evt) {
                        resendCodeBt.setBackground(Style.LIGHT_BlUE);
                    }

                    public void mouseExited(MouseEvent evt) {
                        resendCodeBt.setBackground(Color.WHITE);
                    }
                });
                resendCodeBt.addActionListener(new ActionListener() {
                    private boolean isCooldown = false;
                    private Timer timer = new Timer();

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!isCooldown) {
                            isCooldown = true;
                            resendCodeBt.setEnabled(false);
                            startCooldown();
                        }
                        EmailConfig emailConfig = new EmailConfig();
                        otp = emailConfig.generateOTP();
                        System.out.println(otp +" new ");
                        boolean sent =  sendOTP(to,name,otp);
                        JOptionPane.showMessageDialog(null, "We have sent a new verification code to your email!");



                    }

                    private void startCooldown() {
                        TimerTask task = new TimerTask() {
                            int remainingTime = 25;

                            @Override
                            public void run() {
                                if (remainingTime > 0) {
                                    resendCodeBt.setText("Wait " + remainingTime + "s");
                                    remainingTime--;
                                } else {
                                    resendCodeBt.setText("Re-send Verify Code");
                                    resendCodeBt.setEnabled(true);
                                    isCooldown = false;
                                    cancel();
                                }
                            }
                        };
                        timer.scheduleAtFixedRate(task, 0, 1000);
                    }
                });

                // create backBt
                backBt = createCustomButtonWithBorder("Back", Style.FONT_BUTTON_LOGIN_FRAME, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Color.white, new Color(160, 231, 224), Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1, 20, new Dimension(350, 50));
                backBt.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        showInnerPanel("inputEmail");
                    }
                });

                // create verify field
                gbc.gridy++;
                JPanel otpPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                otpPanel.setBackground(Color.WHITE);
                for (int i = 0; i < 4; i++) {
                    otpFields[i] = new JTextField();
                    otpFields[i].setFont(new Font("Arial", Font.BOLD, 60));
                    otpFields[i].setHorizontalAlignment(JTextField.CENTER);
                    otpFields[i].setPreferredSize(new Dimension(80, 90));
                    otpFields[i].setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1));
                    otpFields[i].addActionListener(e -> verifyBt.doClick());
                    int index1 = i;
                    otpFields[i].addFocusListener(new FocusListener() {
                        @Override
                        public void focusGained(FocusEvent e) {
                            otpFields[index1].setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 4));
                        }

                        @Override
                        public void focusLost(FocusEvent e) {

                            otpFields[index1].setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1));
                        }
                    });


                    int index = i;
                    otpFields[i].addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            if (!Character.isDigit(e.getKeyChar())) {
                                e.consume(); // Ngăn chặn ký tự không phải là số
                            } else if (otpFields[index].getText().length() >= 1) {
                                e.consume(); // Ngăn chặn nhập thêm nếu đã có 1 ký tự
                            }
                        }

                        @Override
                        public void keyReleased(KeyEvent e) {
                            if (Character.isDigit(e.getKeyChar()) && otpFields[index].getText().length() == 1) {
                                if (index < 3) {
                                    SwingUtilities.invokeLater(() -> otpFields[index + 1].requestFocus());
                                }
                            } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                                if (index > 0 && otpFields[index].getText().isEmpty()) {
                                    otpFields[index - 1].setText(""); // Xóa ký tự ở trường trước đó
                                    otpFields[index - 1].requestFocus();
                                }
                            }
                        }
                    });
                    otpPanel.add(otpFields[i]);
                }

                add(otpPanel, gbc);

                // add re-send code button
                gbc.gridy++;
                JPanel resendCodePn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                resendCodePn.setBackground(Color.WHITE);
                resendCodePn.add(resendCodeBt);
                add(resendCodePn, gbc);

                // add 2 button
                gbc.insets = new Insets(5, 10, 5, 10);
                gbc.gridy++;
                JPanel verifyPn = new JPanel();
                verifyPn.setBackground(Color.WHITE);
                verifyPn.add(verifyBt);
                add(verifyPn, gbc);

                JPanel backPn = new JPanel();
                backPn.setBackground(Color.WHITE);
                backPn.add(backBt);
                gbc.gridy++;
                add(backPn, gbc);
            }
        }

        class SetNewPasswdPanel extends JPanel {
            JPasswordField newPasswdField, confirmPasswdField;
            CustomButton resetPasswdBt, backBt;
            JCheckBox showPasswd;

            private void resetTextPassword(){
                newPasswdField.setText("Enter your new password");
                confirmPasswdField.setText("Confirm your new password");
            }

            SetNewPasswdPanel() {
                setBackground(Color.WHITE);
                setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(15, 10, 15, 10);
                gbc.fill = GridBagConstraints.HORIZONTAL;
                // set New Password label
                JLabel setNewPasswdLb = new JLabel("New Password", SwingConstants.CENTER);
                setNewPasswdLb.setFont(Style.FONT_TITLE_BOLD_45);
                setNewPasswdLb.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.gridwidth = 2;
                gbc.anchor = GridBagConstraints.CENTER;
                add(setNewPasswdLb, gbc);

                // create send code button
                resetPasswdBt = createCustomButtonWithBorder("Reset Password", Style.FONT_BUTTON_LOGIN_FRAME, Color.white, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, new Color(160, 231, 224), Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1, 20, new Dimension(350, 50));
                resetPasswdBt.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String newPassword = new String(newPasswdField.getPassword());
                        String confirmPassword = new String(confirmPasswdField.getPassword());
                        // check if passwd field have any char or not
                        if ((!newPassword.equals(confirmPassword)) || newPassword.equals("Enter your new password") || confirmPassword.equals("Confirm your new password")) {
                            if (newPassword.equals("Enter your new password")) {
                                newPasswdField.setBorder(BorderFactory.createLineBorder(Style.DELETE_BUTTON_COLOR_RED, 4));
                                newPasswdField.setForeground(Style.DELETE_BUTTON_COLOR_RED);
                            }else
                            if (confirmPassword.equals("Confirm your new password")) {
                                confirmPasswdField.setBorder(BorderFactory.createLineBorder(Style.DELETE_BUTTON_COLOR_RED, 4));
                                confirmPasswdField.setForeground(Style.DELETE_BUTTON_COLOR_RED);
                            } else {
                                confirmPasswdField.setBorder(BorderFactory.createLineBorder(Style.DELETE_BUTTON_COLOR_RED, 4));
                                confirmPasswdField.setForeground(Style.DELETE_BUTTON_COLOR_RED);
                                newPasswdField.setBorder(BorderFactory.createLineBorder(Style.DELETE_BUTTON_COLOR_RED, 4));
                                newPasswdField.setForeground(Style.DELETE_BUTTON_COLOR_RED);
                            }
                        } else  {
                            if(forgotPasswordStatus == ForgotPasswordStatus.CUSTOMER){
                                CustomerController cusController = new CustomerController();
                                cusController.updatePassword(newPassword, id);
                                resetTextPassword();

                            }else if(forgotPasswordStatus == ForgotPasswordStatus.MANAGER){
                                AccountController accController = new AccountController();
                                accController.updatePassword(newPassword, id);
                                resetTextPassword();
                            }
                            JOptionPane.showMessageDialog(null, "Password reset successfully!");
                            showInnerPanel("inputEmail");
                            inputFormPanel.showPanel("signIn");

                        }
                    }
                });

                // Password Field with Icon
                gbc.gridwidth = 1;
                gbc.gridy++;
                gbc.gridx = 0;
                ImageIcon passwordIcon = new ImageIcon("src/main/java/Icon/lock_icon.png");
                JLabel passwordIconLb1 = new JLabel(passwordIcon);

                add(passwordIconLb1, gbc);
                gbc.gridx = 1;
                newPasswdField = createPasswordField("Enter your new password", Style.FONT_TEXT_LOGIN_FRAME, Color.GRAY, new Dimension(300, 45));
                newPasswdField.addActionListener(e -> resetPasswdBt.doClick());
                add(newPasswdField, gbc);

                JLabel passwordIconLb2 = new JLabel(passwordIcon);
                gbc.gridy++;
                gbc.gridx = 0;
                add(passwordIconLb2, gbc);
                gbc.gridx = 1;
                confirmPasswdField = createPasswordField("Confirm your new password", Style.FONT_TEXT_LOGIN_FRAME, Color.GRAY, new Dimension(300, 45));
                confirmPasswdField.addActionListener(e -> resetPasswdBt.doClick());
                add(confirmPasswdField, gbc);

                gbc.gridy++;
                gbc.gridx = 1;
                showPasswd = new JCheckBox("Show Password");
                showPasswd.setPreferredSize(new Dimension(300, 15));
                showPasswd.setFocusPainted(false);
                showPasswd.setFocusable(false);
                showPasswd.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
                showPasswd.setBackground(Color.WHITE);
                showPasswd.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String passwdNew = new String(newPasswdField.getPassword());
                        String passwdConfirm = new String(confirmPasswdField.getPassword());

                        if (passwdNew.equals("Enter your new password") && passwdConfirm.equals("Confirm your new password")) {
                            showPasswd.setSelected(false);
                        } else {
                            if (showPasswd.isSelected()) {
                                newPasswdField.setEchoChar((char) 0);
                                confirmPasswdField.setEchoChar((char) 0);
                            } else {
                                newPasswdField.setEchoChar('*');
                                confirmPasswdField.setEchoChar('*');
                            }
                        }
                    }
                });
                add(showPasswd, gbc);

                // create send code button
                backBt = createCustomButtonWithBorder("Back", Style.FONT_BUTTON_LOGIN_FRAME, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Color.white, new Color(160, 231, 224), Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1, 20, new Dimension(350, 50));
                backBt.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        showInnerPanel("verificationCode");
                    }
                });

                // add 2 button reset passwd and back to panel
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.gridy++;
                gbc.gridx = 0;
                gbc.gridwidth = 2;
                add(resetPasswdBt, gbc);
                gbc.gridy++;
                add(backBt, gbc);
            }
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
                field.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 4));
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Khi người dùng rời khỏi JTextField mà chưa nhập gì, sẽ hiển thị lại chữ giống originText
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(originText);
                }
                field.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1));
            }
        });
    }

    public void addFocusListenerForJPasswordField(JPasswordField passwdfield, String originText) {
        passwdfield.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                String passwd = new String(passwdfield.getPassword());
                // Khi người dùng nhấn vào JTextField, nếu vẫn là chữ giống originText, nó sẽ biến mất
                passwdfield.setForeground(Color.BLACK);
                if (passwd.equals(originText)) {
                    passwdfield.setText("");
                    passwdfield.setEchoChar('*');
                }
                passwdfield.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 4));

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
                passwdfield.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1));
            }
        });
    }

    private static CustomButton createCustomButtonWithBorder(String title, Font font, Color textColor, Color backgroundColor, Color hoverColor, Color borderColor, int thickness, int radius, Dimension size) {
        CustomButton bt = new CustomButton(title);
        bt.setFont(font);
        bt.setTextColor(textColor);
        bt.setBackgroundColor(backgroundColor);
        bt.setHoverColor(hoverColor);
        bt.setBorderColor(borderColor);
        bt.setBorderThickness(thickness);
        bt.setBorderRadius(radius);
        bt.setPreferredSize(size);
        return bt;
    }

    private JTextField createTextField(String text, Font font, Color textColor, Dimension size) {
        JTextField field = new JTextField(text);
        field.setForeground(textColor);
        field.setPreferredSize(size);
        field.setFont(font);
        field.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1));
        addFocusListenerForTextField(field, text);
        return field;
    }

    private JPasswordField createPasswordField(String text, Font font, Color textColor, Dimension size) {
        JPasswordField pwfield = new JPasswordField(text);
        pwfield.setForeground(textColor);
        pwfield.setPreferredSize(size);
        pwfield.setFont(font);
        pwfield.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1));
        pwfield.setEchoChar((char) 0);
        addFocusListenerForJPasswordField(pwfield, text);
        return pwfield;
    }


    public static void main(String[] args) {
        new LoginFrame();
    }
}

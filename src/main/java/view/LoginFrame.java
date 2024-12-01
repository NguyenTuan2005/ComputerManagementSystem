package view;

import Config.EmailConfig;
import Config.PasswordFieldConfig;
import Config.TextFieldConfig;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;


public class LoginFrame extends JFrame {
    ManagerFrame managerFrame;
    CustomerFrame customerFrame;
    WelcomePanel welcomePanel;
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
        setSize(1100, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("src/main/java/Icon/logo.png").getImage());
        try {
            // Tùy chỉnh giao diện cho JComboBox
//            UIManager.put("ComboBox.border", BorderFactory.createEmptyBorder()); // Loại bỏ viền
//            UIManager.put("ComboBox.focus", new Color(0,0,0,0));                 // Loại bỏ hiệu ứng focus
//            UIManager.put("ComboBox.buttonBackground", Color.PINK);            // Nền nút mũi tên
//            UIManager.put("ComboBox.buttonForeground", Color.BLUE);            // Màu mũi tên
            UIManager.put("ComboBox.focus", UIManager.get("ComboBox.background"));

            // Áp dụng Look and Feel của hệ thống
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

            // try FlatLaf
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        inputFormPanel = new InputFormPanel(this);
        welcomePanel = new WelcomePanel();
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
            setBorder(BorderFactory.createEmptyBorder(0, 60, 50, 50));

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
                        switchBt.setText("Sign In");
                        welcomeLabel.setText(SignUpGreeting);
                        subTextLabel.setText(SignInString);
                        // reset signup panel
                        inputFormPanel.showPanel("signUp");
                        TextFieldConfig.resetTextField(inputFormPanel.signUpPanel.signUpFormPanel.nameField,"User Name");
                        TextFieldConfig.resetTextField(inputFormPanel.signUpPanel.signUpFormPanel.emailField,"User Email");
                        TextFieldConfig.resetTextField(inputFormPanel.signUpPanel.signUpFormPanel.addressField,"User Address");
                        PasswordFieldConfig.resetPasswordField(inputFormPanel.signUpPanel.signUpFormPanel.passwdFieldSignup, "Password");
                        inputFormPanel.signUpPanel.showPanelSignUp("signUpForm");

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
        private LoginFrame loginFrame;
        private CardLayout cardLayoutSignUp;
        private SignUpFormPanel signUpFormPanel;
        private VerifyEmailPanel verifyEmailPanel;
        RegistrationSuccessPanel registrationSuccessPanel;
        SignUpPanel(LoginFrame loginFrame) {
            this.loginFrame = loginFrame;
            cardLayoutSignUp = new CardLayout();
            setLayout(cardLayoutSignUp);
            signUpFormPanel = new SignUpFormPanel();
            verifyEmailPanel = new VerifyEmailPanel();
            registrationSuccessPanel = new RegistrationSuccessPanel();
            add(signUpFormPanel, "signUpForm");
            add(verifyEmailPanel, "verifyEmail");
            add(registrationSuccessPanel, "registrationSuccess");
            cardLayoutSignUp.show(this, "signUpForm");
        }
        public void showPanelSignUp(String title) {
            cardLayoutSignUp.show(this, title);
        }
    }

    class SignUpFormPanel extends JPanel {
        private JLabel createAccountLabel;
        private JTextField nameField, emailField, addressField;
        private JPasswordField passwdFieldSignup;
        private CustomButton signUpButton;
        private JCheckBox showPasswdCB;

        SignUpFormPanel(){

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
                    Map<JTextField, String> fieldsWithPlaceholders = new HashMap<>();
                    fieldsWithPlaceholders.put(nameField, "User Name");
                    fieldsWithPlaceholders.put(emailField, "User Email");
                    fieldsWithPlaceholders.put(addressField, "User Address");

                    boolean allFieldsValid = true;

                    for (Map.Entry<JTextField, String> entry : fieldsWithPlaceholders.entrySet()) {
                        JTextField field = entry.getKey();
                        String placeholder = entry.getValue();
                        if (field.getText().trim().isEmpty() || field.getText().equals(placeholder)) {
                            highlightEmptyField(field);
                            allFieldsValid = false;
                        }
                    }
                    if (new String(passwdFieldSignup.getPassword()).equals("Password")) {
                        passwdFieldSignup.setBorder(BorderFactory.createLineBorder(Style.DELETE_BUTTON_COLOR_RED, 4));
                        passwdFieldSignup.setForeground(Style.DELETE_BUTTON_COLOR_RED);
                        allFieldsValid = false;
                    }

                    if (allFieldsValid) {
                        inputFormPanel.signUpPanel.showPanelSignUp("verifyEmail");
                    }

                    for (int i = 0; i < inputFormPanel.signUpPanel.verifyEmailPanel.otpFields.length; i++) {
                        inputFormPanel.signUpPanel.verifyEmailPanel.otpFields[i].setText("");
                    }
                }
                public void highlightEmptyField(JTextField field) {
                    field.setBorder(BorderFactory.createLineBorder(Style.DELETE_BUTTON_COLOR_RED, 4));
                    field.setForeground(Style.DELETE_BUTTON_COLOR_RED);
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
            nameField =  TextFieldConfig.createTextFieldWithPlaceHolder("User Name", Style.FONT_TEXT_LOGIN_FRAME, Color.GRAY, new Dimension(300, 45));
            nameField.addActionListener(e -> signUpButton.doClick());
            add(nameField, gbc);

            // Email Field with Icon
            gbc.gridy++;
            gbc.gridx = 0;
            JLabel emailIcon = new JLabel(new ImageIcon("src/main/java/Icon/email_icon.png"));
            emailIcon.setPreferredSize(new Dimension(30, 30));
            add(emailIcon, gbc);

            gbc.gridx = 1;
            emailField =  TextFieldConfig.createTextFieldWithPlaceHolder("User Email", Style.FONT_TEXT_LOGIN_FRAME, Color.GRAY, new Dimension(300, 45));
            emailField.addActionListener(e -> signUpButton.doClick());
            add(emailField, gbc);

            // Address Field with Icon
            gbc.gridy++;
            gbc.gridx = 0;
            JLabel addressIcon = new JLabel(new ImageIcon("src/main/java/Icon/address_Icon.png"));
            addressIcon.setPreferredSize(new Dimension(30, 30));
            add(addressIcon, gbc);

            gbc.gridx = 1;
            // create popup hint
            JWindow popup = new JWindow();

            JLabel hintLabel = new JLabel("<html>Providing your address helps us deliver your order securely and on time!<br>Rest assured, We take your privacy seriously!!</html>");
            hintLabel.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,2));
            hintLabel.setBackground(Color.WHITE);
            hintLabel.setFont(Style.FONT_SIZE_MIN_PRODUCT);
            hintLabel.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
            hintLabel.setOpaque(true);
            popup.add(hintLabel);
            popup.setSize(300, 70);

            addressField = TextFieldConfig.createTextFieldWithPlaceHolder("User Address", Style.FONT_TEXT_LOGIN_FRAME, Color.GRAY, new Dimension(300, 45));
            addressField.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    Point locationOnScreen = addressField.getLocationOnScreen();
                    popup.setLocation(locationOnScreen.x, locationOnScreen.y + addressField.getHeight()+1);
                    popup.setVisible(true);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    popup.setVisible(false);
                }
            });
            addressField.addActionListener(e -> signUpButton.doClick());
            add(addressField, gbc);

            // Password Field with Icon
            gbc.gridy++;
            gbc.gridx = 0;
            JLabel passwordIcon = new JLabel(new ImageIcon("src/main/java/Icon/lock_icon.png"));
            passwordIcon.setPreferredSize(new Dimension(30, 30));
            add(passwordIcon, gbc);

            gbc.gridx = 1;
            passwdFieldSignup = PasswordFieldConfig.createPasswordField("Password", Style.FONT_TEXT_LOGIN_FRAME, Color.GRAY, new Dimension(300, 45));
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

    class VerifyEmailPanel extends JPanel{
        JTextField[] otpFields = new JTextField[4];
        CustomButton verifyBt, backBt;
        JButton resendCodeBt;
        final int RESET_OTP =0;

        VerifyEmailPanel() {
            setBackground(Color.WHITE);
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // reset Password label
            JLabel verifyEmailLb = new JLabel("Email Verification", SwingConstants.CENTER);
            verifyEmailLb.setFont(Style.FONT_TITLE_BOLD_45);
            verifyEmailLb.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;
            add(verifyEmailLb, gbc);

            // create verifyBt
            verifyBt = createCustomButtonWithBorder("Verify my Email", Style.FONT_BUTTON_LOGIN_FRAME, Color.white, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, new Color(160, 231, 224), Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1, 20, new Dimension(350, 50));
            verifyBt.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    boolean isEmpty = false;
                    for (int i = 0; i < otpFields.length; i++) {
                        if (otpFields[i].getText().trim().isEmpty()) {
                            otpFields[i].setBorder(BorderFactory.createLineBorder(Style.DELETE_BUTTON_COLOR_RED, 4));
                            isEmpty = true;
                        }
                    }
                    if (!isEmpty){

                        // TODO




                        inputFormPanel.signUpPanel.showPanelSignUp("registrationSuccess");
                    }
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
//                    otp = emailConfig.generateOTP();
//                    System.out.println(otp +" new ");
//                    boolean sent =  sendOTP(to,name,otp);
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
                    inputFormPanel.signUpPanel.showPanelSignUp("signUpForm");
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

    class RegistrationSuccessPanel extends JPanel{
        private CustomButton goToSignIn;

        RegistrationSuccessPanel(){
            setBackground(Color.WHITE);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createEmptyBorder(0, 50, 50, 50));
            JLabel titleLabel = new JLabel("<html>Verification Successful</html>",SwingConstants.CENTER);
            titleLabel.setFont(Style.FONT_TITLE_BOLD_45);
            titleLabel.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel greetingLb = new JLabel("<html>You're all set!!!<br>Sign in with your new account and enjoy using the application.</html>");
            greetingLb.setFont(Style.FONT_TITLE_PRODUCT_30);
            greetingLb.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
            greetingLb.setAlignmentX(Component.CENTER_ALIGNMENT);


            ImageIcon congrats = new ImageIcon("src/main/java/img/congratulation_Image.jpg");
            JLabel congratsImage = new JLabel(congrats);
            congratsImage.setAlignmentX(Component.CENTER_ALIGNMENT);

            add(Box.createVerticalGlue());
            add(Box.createRigidArea(new Dimension(0, 20)));
            add(titleLabel);
            add(Box.createRigidArea(new Dimension(0, 40)));
            add(greetingLb);
            add(congratsImage);
            add(Box.createVerticalGlue());
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
            roleComboBox.setBorder(BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1));
            roleComboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    nameField.setForeground(Color.GRAY);
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

            nameField =  TextFieldConfig.createTextFieldWithPlaceHolder("User Email", Style.FONT_TEXT_LOGIN_FRAME, Color.GRAY, new Dimension(300, 45));
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
            passwdFieldSignin = PasswordFieldConfig.createPasswordField("Password", Style.FONT_TEXT_LOGIN_FRAME, Color.GRAY, new Dimension(300, 45));
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
                    if (username.isEmpty() || password.isEmpty() || username.equals("User Name") || username.equals("User Email") || password.equals("Password")) {
                        if (username.isEmpty() || username.equals("User Name") || username.equals("User Email")) {
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
                    TextFieldConfig.resetTextField(inputFormPanel.forgotPasswdPanel.inputEmail.emailField,"UserEmail");
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
            JRadioButton managerBt, customerBt;

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

                managerBt =  createRadioButton("Manager",Style.FONT_SIZE,Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,Style.WORD_COLOR_WHITE,new Dimension(120,40));
                customerBt = createRadioButton("Customer",Style.FONT_SIZE,Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,Style.WORD_COLOR_WHITE,new Dimension(120,40));
                customerBt.setSelected(true);
                group.add(customerBt);
                group.add(managerBt);


                JPanel checkBoxPanel = new JPanel();

                checkBoxPanel.setLayout(new FlowLayout());
                checkBoxPanel.setBackground(Style.WORD_COLOR_WHITE);
                checkBoxPanel.add(customerBt);
                checkBoxPanel.add(managerBt);
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.gridwidth = 2;
                add(checkBoxPanel, gbc);

                // create send code button
                sendCodeBt = createCustomButtonWithBorder("Send Code", Style.FONT_BUTTON_LOGIN_FRAME, Color.white, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, new Color(160, 231, 224), Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1, 20, new Dimension(350, 50));
                sendCodeBt.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        EmailConfig emailConfig = new EmailConfig();
                        if (customerBt.isSelected()) {
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
                        } else if(managerBt.isSelected()) {
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
                emailField =  TextFieldConfig.createTextFieldWithPlaceHolder("User Email", Style.FONT_TEXT_LOGIN_FRAME, Color.GRAY, new Dimension(300, 45));
                emailField.addActionListener(e -> {sendCodeBt.doClick();});
                add(emailField, gbc);

                // add send code button to panel
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
            // fix ow day
            private void setTextForTextField() {
                if (customerBt.isSelected()) {
                    emailField.setText("User Email");
                } else {
                    emailField.setText("User Name");
                }
            }
            private void setColorTextField(){
                emailField.setBorder(BorderFactory.createLineBorder(Style.DELETE_BUTTON_COLOR_RED, 4));
            }
            private JRadioButton createRadioButton(String title, Font font,Color textColor, Color backgroudColor,Dimension size){
                JRadioButton rdoBt = new JRadioButton(title);
                rdoBt.setBorderPainted(false);
                rdoBt.setFocusable(false);
                rdoBt.setFont(font);
                rdoBt.setBackground(backgroudColor);
                rdoBt.setForeground(textColor);
                rdoBt.setPreferredSize(size);
                return rdoBt;
            }
        }


        class VerificationCodePanel extends JPanel {
            JTextField[] otpFields = new JTextField[4];
            CustomButton verifyBt, backBt;
            JButton resendCodeBt;
            final int RESET_OTP =0;

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
                        boolean isEmpty = false;
                        for (int i = 0; i < otpFields.length; i++) {
                            if (otpFields[i].getText().trim().isEmpty()) {
                                otpFields[i].setBorder(BorderFactory.createLineBorder(Style.DELETE_BUTTON_COLOR_RED, 4));
                                isEmpty = true;
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
                            ToastNotification.showToast("Wrong OTP",2500,50,-1,-1);
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
            private  void setColorTextField(){
                for (int i = 0; i < otpFields.length; i++) {
                    otpFields[i].setBorder(BorderFactory.createLineBorder(Style.DELETE_BUTTON_COLOR_RED, 4));
                }
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
                newPasswdField = PasswordFieldConfig.createPasswordField("Enter your new password", Style.FONT_TEXT_LOGIN_FRAME, Color.GRAY, new Dimension(300, 45));
                newPasswdField.addActionListener(e -> resetPasswdBt.doClick());
                add(newPasswdField, gbc);

                JLabel passwordIconLb2 = new JLabel(passwordIcon);
                gbc.gridy++;
                gbc.gridx = 0;
                add(passwordIconLb2, gbc);
                gbc.gridx = 1;
                confirmPasswdField = PasswordFieldConfig.createPasswordField("Confirm your new password", Style.FONT_TEXT_LOGIN_FRAME, Color.GRAY, new Dimension(300, 45));
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
                field.setForeground(Color.BLACK);
                if (field.getText().equals(originText)) {
                    field.setText("");
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

    public static void main(String[] args) {
        new LoginFrame();
    }
}

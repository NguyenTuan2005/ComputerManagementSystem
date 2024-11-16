package view;

import controller.AccountController;
import controller.CustomerController;
import view.OverrideComponent.CircularImage;
import view.OverrideComponent.CustomButton;

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
            signUpButton = createCustomButtonWithBorder("Sign Up", Style.FONT_BUTTON_LOGIN_FRAME, Color.white, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, new Color(160, 231, 224), Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1, 20,new Dimension(350, 45));
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
            signInButton = createCustomButtonWithBorder("Sign In", Style.FONT_BUTTON_LOGIN_FRAME, Color.white, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, new Color(160, 231, 224), Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1, 20,new Dimension(350, 45));
            signInButton.setBackground(new Color(0, 153, 102));
            signInButton.setForeground(Color.WHITE);
            signInButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String username = nameField.getText();
                    String password = new String(passwdFieldSignin.getPassword());
                    String email = "";

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
                                if (customerController.isValidAccount(email, password)) {

//                                    loginFrame.setVisible(false);
//                                userFrame = new CustomerFrame(loginFrame);
                                } else
                                    sayError("You have entered the Wrong email or password, please try again!");
                                break;
                            }

                        }
                    }
                }
            });

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
            nameField = createTextField("User Name", Style.FONT_TEXT_LOGIN_FRAME, Color.GRAY, new Dimension(300, 45));
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

package view;

import view.OverrideComponent.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;


public class NewLoginFrame extends JFrame {
    WelcomePanel welcomePanel = new WelcomePanel();
    InputFormPanel inputFormPanel = new InputFormPanel();

    final String SignUpString = "<html>You don't have an Account?<br>Please Sign Up to connect with us!</html>";
    final String SignUpGreeting = "Hello!";
    final String SignInGreeting = "Welcome back!";


    final String SignInString = "<html>Login with your personal info<br>to keep connected with us please!</html>";

    NewLoginFrame() {
        setLayout(new BorderLayout());
        setTitle("Computer Management");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("src/main/java/Icon/logo.png").getImage());

        add(welcomePanel, BorderLayout.WEST);
        add(inputFormPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    class WelcomePanel extends JPanel {
        JLabel welcomeLabel, subTextLabel;
        RoundedButton switchBt;

        public WelcomePanel() {
            setBackground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

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
            add(welcomeLabel);
            add(Box.createRigidArea(new Dimension(0, 10)));
            add(subTextLabel);
            add(Box.createRigidArea(new Dimension(0, 30)));
            add(switchBt);
            add(Box.createVerticalGlue());
        }


        @Override
        protected void paintComponent(Graphics grphcs) {
            Graphics2D g2 = (Graphics2D) grphcs;
            GradientPaint gra = new GradientPaint(0, 0, new Color(35, 166, 97), 0, getHeight(), new Color(22, 116, 66));
            g2.setPaint(gra);
            g2.fillRect(0, 0, getWidth(), getHeight());
            super.paintComponent(grphcs);
        }


    }

    class InputFormPanel extends JPanel {
        SignInPanel signInPanel;
        SignUpPanel signUpPanel;
        CardLayout cardLayout;

        InputFormPanel() {
            cardLayout = new CardLayout();
            setLayout(cardLayout);
            signInPanel = new SignInPanel();
            signUpPanel = new SignUpPanel();
            add(signInPanel, "signin");
            add(signUpPanel, "signup");
            cardLayout.show(this, "signin");
        }

        public void showPanel(String title) {
            cardLayout.show(this, title);
        }
    }

    class SignUpPanel extends JPanel {
        JLabel createAccountLabel;
        JTextField nameField, emailField, passwordField;
        JPasswordField passwdFieldSignup;
        RoundedButton signUpButton;

        SignUpPanel() {
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
            nameField.setPreferredSize(new Dimension(200, 40));
            nameField.setFont(Style.FONT_TEXT_LOGIN_FRAME);
            addFocusListenerForTextField(nameField,"User Name");
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
            emailField.setPreferredSize(new Dimension(200, 40));
            emailField.setFont(Style.FONT_TEXT_LOGIN_FRAME);
            addFocusListenerForTextField(emailField,"User Email");
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
            passwdFieldSignup.setPreferredSize(new Dimension(200, 40));
            passwdFieldSignup.setFont(Style.FONT_TEXT_LOGIN_FRAME);
            passwdFieldSignup.setEchoChar((char) 0);
            addFocusListenerForJPasswordField(passwdFieldSignup,"Password");
            add(passwdFieldSignup, gbc);

            // Sign Up button
            gbc.gridy++;
            gbc.gridx = 0;
            gbc.gridwidth = 2;
            signUpButton = new RoundedButton("Sign Up");
            formatRoundedButton(signUpButton, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Color.white, new Color(160, 231, 224), Style.FONT_BUTTON_LOGIN_FRAME, new Dimension(200, 40));
            signUpButton.setBackground(new Color(0, 153, 102));
            signUpButton.setForeground(Color.WHITE);
            add(signUpButton, gbc);


        }
    }

    class SignInPanel extends JPanel {
        JLabel signInLabel;
        JTextField nameField, emailField;
        JPasswordField passwdFieldSignin;
        RoundedButton signInButton;

        SignInPanel() {
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

            // Email Field with Icon
            gbc.gridwidth = 1;
            gbc.gridy++;
            gbc.gridx = 0;
            JLabel nameIcon = new JLabel(new ImageIcon("src/main/java/Icon/user_icon.png"));
            nameIcon.setPreferredSize(new Dimension(30, 30));
            add(nameIcon, gbc);

            gbc.gridx = 1;
            nameField = new JTextField("User Name");
            nameField.setForeground(Color.GRAY);
            addFocusListenerForTextField(nameField,"User Name");
            nameField.setFont(new Font("Arial", Font.PLAIN, 18));  // Tăng kích thước chữ của text field
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
            addFocusListenerForJPasswordField(passwdFieldSignin,"Password");
            passwdFieldSignin.setFont(new Font("Arial", Font.PLAIN, 18));
            passwdFieldSignin.setPreferredSize(new Dimension(250, 40));
            add(passwdFieldSignin, gbc);

            // Sign In button
            gbc.gridy++;
            gbc.gridx = 0;
            gbc.gridwidth = 2;
            signInButton = new RoundedButton("Sign In");
            formatRoundedButton(signInButton, Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, Color.white, new Color(160, 231, 224), Style.FONT_BUTTON_LOGIN_FRAME, new Dimension(200, 40));
            signInButton.setBackground(new Color(0, 153, 102));
            signInButton.setForeground(Color.WHITE); // Tăng kích thước chữ của nút
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
        new NewLoginFrame();
    }
}

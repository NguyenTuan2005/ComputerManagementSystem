package view;

import config.ButtonConfig;
import config.EmailConfig;
import config.PasswordFieldConfig;
import config.TextFieldConfig;
import enums.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import model.ComputerShop;
import model.Customer;
import security.PasswordSecurity;
import verifier.EmailVerifier;
import view.overrideComponent.CircularImage;
import view.overrideComponent.CustomButton;
import view.overrideComponent.ToastNotification;

public class LoginFrame extends JFrame {
  private ManagerFrame managerFrame;
  private CustomerFrame customerFrame;
  private WelcomePanel welcomePanel;
  private InputFormPanel inputFormPanel;

  public static ComputerShop COMPUTER_SHOP = new ComputerShop();

  private final String SignUpString =
      "<html>You don't have an Account?<br>Please Sign Up to connect with us!</html>";
  private final String SignInString =
      "<html>Login with your personal info<br>to keep connected with us please!</html>";
  private final String SignUpGreeting = "Hello!";
  private final String SignInGreeting = "Welcome back!";

  private static final String CUSTOMER_ROLE = "Customer";
  private static final String MANAGER_ROLE = "Manager";

  private int id = 0;

  LoginFrame() {
    setLayout(new BorderLayout());
    setTitle("Computer Management");
    setSize(1100, 600);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setIconImage(new ImageIcon("src/main/java/Icon/logo.png").getImage());
    try {

      UIManager.put("ComboBox.focus", UIManager.get("ComboBox.background"));

      UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

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

  private class WelcomePanel extends JPanel {
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
      subTextLabel.setFont(Style.FONT_PLAIN_20);
      subTextLabel.setForeground(Color.WHITE);

      switchBt =
          ButtonConfig.createCustomButton(
              "Sign Up",
              Style.FONT_BOLD_24,
              Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
              Color.white,
              new Color(160, 231, 224),
              Color.white,
              1,
              20,
              SwingConstants.CENTER,
              new Dimension(150, 38));
      switchBt.setAlignmentX(Component.CENTER_ALIGNMENT);
      switchBt.addActionListener(
          new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              if (switchBt.getText().equals("Sign Up")) {
                switchBt.setText("Sign In");
                welcomeLabel.setText(SignUpGreeting);
                subTextLabel.setText(SignInString);
                // reset signup panel
                inputFormPanel.showPanel("signUp");
                TextFieldConfig.resetTextField(
                    inputFormPanel.signUpPanel.signUpFormPanel.nameField, "User Name");
                TextFieldConfig.resetTextField(
                    inputFormPanel.signUpPanel.signUpFormPanel.emailField, "User Email");
                TextFieldConfig.resetTextField(
                    inputFormPanel.signUpPanel.signUpFormPanel.addressField, "User Address");
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

  private class InputFormPanel extends JPanel {
    private SignInPanel signInPanel;
    private SignUpPanel signUpPanel;
    private ForgotPasswdPanel forgotPasswdPanel;
    private CardLayout cardLayoutMain;
    private LoginFrame loginFrame;

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

    public static boolean sendOTPForRegister(String to, int otp) {
      EmailConfig emailConfig = new EmailConfig();
      System.out.println(to);
      return emailConfig.send(
          to, emailConfig.buildHeaderMessage(), emailConfig.buildBodyMessageForRegister(to, otp));
    }
  }

  private class SignUpPanel extends JPanel {
    private LoginFrame loginFrame;
    private CardLayout cardLayoutSignUp;
    private SignUpFormPanel signUpFormPanel;
    private VerifyEmailPanel verifyEmailPanel;
    private SetPasswdPanel setPasswdPanel;
    private RegistrationSuccessPanel registrationSuccessPanel;

    SignUpPanel(LoginFrame loginFrame) {
      this.loginFrame = loginFrame;
      cardLayoutSignUp = new CardLayout();
      setLayout(cardLayoutSignUp);
      signUpFormPanel = new SignUpFormPanel();
      verifyEmailPanel = new VerifyEmailPanel();
      setPasswdPanel = new SetPasswdPanel();
      registrationSuccessPanel = new RegistrationSuccessPanel();
      add(signUpFormPanel, "signUpForm");
      add(verifyEmailPanel, "verifyEmail");
      add(setPasswdPanel, "setPasswd");
      add(registrationSuccessPanel, "registrationSuccess");
      cardLayoutSignUp.show(this, "signUpForm");
    }

    public void showPanelSignUp(String title) {
      cardLayoutSignUp.show(this, title);
    }
  }

  private class SignUpFormPanel extends JPanel {
    private JLabel createAccountLabel;
    private JTextField nameField, emailField, addressField;
    private CustomButton signUpButton;
    public static int otpRegister = new EmailConfig().generateOTP();
    public static String email;
    public static String userName;
    public static String address;

    SignUpFormPanel() {

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

      signUpButton =
          ButtonConfig.createCustomButton(
              "Sign Up",
              Style.FONT_BOLD_24,
              Color.white,
              Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
              new Color(160, 231, 224),
              Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
              1,
              20,
              SwingConstants.CENTER,
              new Dimension(350, 45));
      signUpButton.setBackground(new Color(0, 153, 102));
      signUpButton.setForeground(Style.WORD_COLOR_WHITE);
      signUpButton.addActionListener(
          new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              Map<JTextField, String> fieldsWithPlaceholders = new HashMap<>();
              fieldsWithPlaceholders.put(nameField, "User Name");
              fieldsWithPlaceholders.put(emailField, "User Email");
              fieldsWithPlaceholders.put(addressField, "User Address");

              boolean allFieldsValid = isAllFieldsValid(fieldsWithPlaceholders);

              if (allFieldsValid) {
                if (InputFormPanel.sendOTPForRegister(emailField.getText(), otpRegister)) {
                  System.out.println("---> 0tp: " + otpRegister);
                  inputFormPanel.signUpPanel.showPanelSignUp("verifyEmail");
                  email = emailField.getText();
                  address = addressField.getText();
                  userName = nameField.getText();
                } else {
                  System.out.println(" refill email");
                }
              } else System.out.println("sai cai j roi");

              for (int i = 0;
                  i < inputFormPanel.signUpPanel.verifyEmailPanel.otpFields.length;
                  i++) {
                inputFormPanel.signUpPanel.verifyEmailPanel.otpFields[i].setText("");
              }
            }

            private boolean isAllFieldsValid(Map<JTextField, String> fieldsWithPlaceholders) {
              boolean allFieldsValid = true;

              for (Map.Entry<JTextField, String> entry : fieldsWithPlaceholders.entrySet()) {
                JTextField field = entry.getKey();
                String placeholder = entry.getValue();
                if (field.getText().trim().isEmpty() || field.getText().equals(placeholder)) {
                  highlightEmptyField(field);
                  allFieldsValid = false;
                }
              }
              return allFieldsValid;
            }

            public void highlightEmptyField(JTextField field) {
              field.setBorder(BorderFactory.createLineBorder(Style.DELETE_BUTTON_COLOR_RED, 4));
              field.setForeground(Style.DELETE_BUTTON_COLOR_RED);
            }
          });

      gbc.gridwidth = 1;
      gbc.gridy++;
      gbc.gridx = 0;
      JLabel nameIcon = new JLabel(new ImageIcon("src/main/java/Icon/user_icon.png"));
      nameIcon.setPreferredSize(new Dimension(30, 30));
      add(nameIcon, gbc);

      gbc.gridx = 1;
      nameField =
          TextFieldConfig.createTextField(
              "User Name", Style.FONT_PLAIN_20, Color.GRAY, new Dimension(300, 45));
      nameField.addActionListener(e -> signUpButton.doClick());
      add(nameField, gbc);

      gbc.gridy++;
      gbc.gridx = 0;
      JLabel emailIcon = new JLabel(new ImageIcon("src/main/java/Icon/email_icon.png"));
      emailIcon.setPreferredSize(new Dimension(30, 30));
      add(emailIcon, gbc);

      gbc.gridx = 1;
      emailField =
          TextFieldConfig.createTextField(
              "User Email", Style.FONT_PLAIN_20, Color.GRAY, new Dimension(300, 45));
      emailField.addActionListener(e -> signUpButton.doClick());
      emailField.setInputVerifier(new EmailVerifier());
      add(emailField, gbc);

      gbc.gridy++;
      gbc.gridx = 0;
      JLabel addressIcon = new JLabel(new ImageIcon("src/main/java/Icon/address_Icon.png"));
      addressIcon.setPreferredSize(new Dimension(30, 30));
      add(addressIcon, gbc);

      gbc.gridx = 1;

      JWindow popup = new JWindow();

      JLabel hintLabel =
          new JLabel(
              "<html>Providing your address helps us deliver your order securely and on time!<br>Rest assured, We take your privacy seriously!!</html>");
      hintLabel.setBorder(
          BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 2));
      hintLabel.setBackground(Color.WHITE);
      hintLabel.setFont(Style.FONT_PLAIN_13);
      hintLabel.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
      hintLabel.setOpaque(true);
      popup.add(hintLabel);
      popup.setSize(300, 70);

      addressField =
          TextFieldConfig.createTextField(
              "User Address", Style.FONT_PLAIN_20, Color.GRAY, new Dimension(300, 45));
      addressField.addMouseListener(
          new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
              Point locationOnScreen = addressField.getLocationOnScreen();
              popup.setLocation(
                  locationOnScreen.x, locationOnScreen.y + addressField.getHeight() + 1);
              popup.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
              popup.setVisible(false);
            }
          });
      addressField.addActionListener(e -> signUpButton.doClick());
      add(addressField, gbc);

      gbc.gridy++;
      gbc.gridx = 0;
      gbc.gridwidth = 2;
      gbc.anchor = GridBagConstraints.CENTER;
      add(signUpButton, gbc);
    }
  }

  private class VerifyEmailPanel extends JPanel {
    private JTextField[] otpFields = new JTextField[4];
    private CustomButton verifyBt, backBt;
    private JButton resendCodeBt;

    VerifyEmailPanel() {
      setBackground(Color.WHITE);
      setLayout(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.insets = new Insets(10, 10, 10, 10);
      gbc.fill = GridBagConstraints.HORIZONTAL;

      JLabel verifyEmailLb = new JLabel("Email Verification", SwingConstants.CENTER);
      verifyEmailLb.setFont(Style.FONT_TITLE_BOLD_45);
      verifyEmailLb.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
      gbc.gridx = 0;
      gbc.gridy = 0;
      gbc.anchor = GridBagConstraints.CENTER;
      add(verifyEmailLb, gbc);

      verifyBt =
          ButtonConfig.createCustomButton(
              "Verify my Email",
              Style.FONT_BOLD_24,
              Color.white,
              Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
              new Color(160, 231, 224),
              Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
              1,
              20,
              SwingConstants.CENTER,
              new Dimension(350, 50));
      verifyBt.addActionListener(
          new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              boolean isEmpty = false;
              for (int i = 0; i < otpFields.length; i++) {
                if (otpFields[i].getText().trim().isEmpty()) {
                  otpFields[i].setBorder(
                      BorderFactory.createLineBorder(Style.DELETE_BUTTON_COLOR_RED, 4));
                  isEmpty = true;
                }
              }
              if (!isEmpty) {

                int otpInput = 0;
                try {
                  for (int i = 0; i < otpFields.length; i++) {
                    int num = Integer.parseInt(otpFields[i].getText().trim());
                    otpInput += num;
                    if (i < otpFields.length - 1) otpInput *= 10;
                  }
                } catch (NullPointerException exception) {
                  System.out.println(exception);
                }

                System.out.println(otpInput + "   " + SignUpFormPanel.otpRegister);
                if (SignUpFormPanel.otpRegister == otpInput) {

                  resetPasswdField(
                      inputFormPanel.signUpPanel.setPasswdPanel.passwdField, "Enter your password");
                  resetPasswdField(
                      inputFormPanel.signUpPanel.setPasswdPanel.confirmPasswdField,
                      "Confirm your password");
                  inputFormPanel.signUpPanel.showPanelSignUp("setPasswd");

                  for (int i = 0; i < otpFields.length; i++) {
                    otpFields[i].setText("");
                  }

                } else {
                  System.out.println("sai roi ");

                  ToastNotification.showToast("Wrong OTP", 2500, 50, -1, -1);
                }
              }
            }
          });

      resendCodeBt = new JButton("Re-send Verify Code");
      resendCodeBt.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
      resendCodeBt.setBackground(Color.WHITE);
      resendCodeBt.setFont(Style.FONT_PLAIN_16);
      resendCodeBt.setFocusable(false);
      resendCodeBt.setBorderPainted(false);
      resendCodeBt.addMouseListener(
          new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
              resendCodeBt.setBackground(Style.LIGHT_BlUE);
            }

            public void mouseExited(MouseEvent evt) {
              resendCodeBt.setBackground(Color.WHITE);
            }
          });
      resendCodeBt.addActionListener(
          new ActionListener() {
            private boolean isCooldown = false;
            private Timer timer = new Timer();

            @Override
            public void actionPerformed(ActionEvent e) {
              String email = SignUpFormPanel.email;
              SignUpFormPanel.otpRegister = new EmailConfig().generateOTP();
              System.out.println("resend OTP " + SignUpFormPanel.otpRegister);
              InputFormPanel.sendOTPForRegister(email, SignUpFormPanel.otpRegister);
              if (!isCooldown) {
                isCooldown = true;
                resendCodeBt.setEnabled(false);
                startCooldown();
              }

              JOptionPane.showMessageDialog(
                  null, "We have sent a new verification code to your email!");
            }

            private void startCooldown() {
              TimerTask task =
                  new TimerTask() {
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

      backBt =
          ButtonConfig.createCustomButton(
              "Back",
              Style.FONT_BOLD_24,
              Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
              Color.white,
              new Color(160, 231, 224),
              Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
              1,
              20,
              SwingConstants.CENTER,
              new Dimension(350, 50));
      backBt.addActionListener(
          new ActionListener() {
            public void actionPerformed(ActionEvent e) {

              inputFormPanel.signUpPanel.showPanelSignUp("signUpForm");
            }
          });

      gbc.gridy++;
      JPanel otpPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
      otpPanel.setBackground(Color.WHITE);
      for (int i = 0; i < 4; i++) {
        otpFields[i] = new JTextField();
        otpFields[i].setFont(new Font("Arial", Font.BOLD, 60));
        otpFields[i].setHorizontalAlignment(JTextField.CENTER);
        otpFields[i].setPreferredSize(new Dimension(80, 90));
        otpFields[i].setBorder(
            BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1));
        otpFields[i].addActionListener(e -> verifyBt.doClick());
        int index1 = i;
        otpFields[i].addFocusListener(
            new FocusListener() {
              @Override
              public void focusGained(FocusEvent e) {
                otpFields[index1].setBorder(
                    BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 4));
              }

              @Override
              public void focusLost(FocusEvent e) {

                otpFields[index1].setBorder(
                    BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1));
              }
            });

        int index = i;
        otpFields[i].addKeyListener(
            new KeyAdapter() {
              @Override
              public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) {
                  e.consume();
                } else if (otpFields[index].getText().length() >= 1) {
                  e.consume();
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
                    otpFields[index - 1].setText("");
                    otpFields[index - 1].requestFocus();
                  }
                }
              }
            });
        otpPanel.add(otpFields[i]);
      }

      add(otpPanel, gbc);

      gbc.gridy++;
      JPanel resendCodePn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      resendCodePn.setBackground(Color.WHITE);
      resendCodePn.add(resendCodeBt);
      add(resendCodePn, gbc);

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

    public void resetPasswdField(JPasswordField passwdField, String placeHolder) {
      passwdField.setText(placeHolder);
      passwdField.setForeground(Color.GRAY);
      passwdField.setEchoChar((char) 0);
    }
  }

  private class SetPasswdPanel extends JPanel {
    private JPasswordField passwdField, confirmPasswdField;
    private CustomButton confirmBt, showPasswdBt1, showPasswd2;

    SetPasswdPanel() {
      setBackground(Color.WHITE);
      setLayout(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.insets = new Insets(15, 10, 15, 10);
      gbc.fill = GridBagConstraints.HORIZONTAL;

      JLabel setNewPasswdLb =
          new JLabel(
              "<html><div style='text-align: center;'>Choose a Password<br>for Your Account</div></html>",
              SwingConstants.CENTER);
      setNewPasswdLb.setFont(Style.FONT_TITLE_BOLD_45);
      setNewPasswdLb.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
      gbc.gridx = 0;
      gbc.gridy = 0;
      gbc.gridwidth = 2;
      gbc.anchor = GridBagConstraints.CENTER;
      add(setNewPasswdLb, gbc);

      confirmBt =
          ButtonConfig.createCustomButton(
              "Confirm",
              Style.FONT_BOLD_24,
              Color.white,
              Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
              new Color(160, 231, 224),
              Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
              1,
              20,
              SwingConstants.CENTER,
              new Dimension(350, 50));
      confirmBt.addActionListener(
          new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              String newPassword = new String(passwdField.getPassword());
              String confirmPassword = new String(confirmPasswdField.getPassword());
              // TODO
              if (newPassword.equals(confirmPassword)) {
                String name = SignUpFormPanel.userName;
                String email = SignUpFormPanel.email;
                String address = SignUpFormPanel.address;
                inputFormPanel.signUpPanel.showPanelSignUp("registrationSuccess");

                Customer newCustomer =
                    Customer.builder()
                        .fullName(name)
                        .avatarImg("src/main/java/img/default-avt.png")
                        .address(address)
                        .email(email)
                        .password(new PasswordSecurity(newPassword).generatePassword())
                        .build();
                LoginFrame.COMPUTER_SHOP.addCustomer(newCustomer);
                System.out.println("ok");

              } else {
                JOptionPane.showConfirmDialog(null, "Wrong Password !!!");
                passwdField.setText("");
                confirmPasswdField.setText("");
              }
            }
          });

      gbc.gridwidth = 1;
      gbc.gridy++;
      gbc.gridx = 0;
      ImageIcon passwordIcon = new ImageIcon("src/main/java/Icon/lock_icon.png");
      JLabel passwordIconLb1 = new JLabel(passwordIcon);
      add(passwordIconLb1, gbc);

      JPanel passwdPn = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
      passwdPn.setBackground(Color.WHITE);

      gbc.gridx = 1;
      passwdField =
          PasswordFieldConfig.createPasswordFieldWithPlaceHolder(
              "Enter your password", Style.FONT_PLAIN_20, Color.GRAY, new Dimension(350, 45));
      passwdField.addActionListener(e -> confirmBt.doClick());
      passwdPn.add(passwdField);

      showPasswdBt1 = createShowPasswdBt(passwdField, "Enter your password");
      passwdPn.add(showPasswdBt1);
      add(passwdPn, gbc);

      JLabel passwordIconLb2 = new JLabel(passwordIcon);
      gbc.gridy++;
      gbc.gridx = 0;
      add(passwordIconLb2, gbc);

      JPanel confirmPasswdPn = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
      confirmPasswdPn.setBackground(Color.WHITE);
      gbc.gridx = 1;
      confirmPasswdField =
          PasswordFieldConfig.createPasswordFieldWithPlaceHolder(
              "Confirm your password", Style.FONT_PLAIN_20, Color.GRAY, new Dimension(350, 45));
      confirmPasswdField.addActionListener(e -> confirmBt.doClick());
      confirmPasswdPn.add(confirmPasswdField);

      showPasswd2 = createShowPasswdBt(confirmPasswdField, "Confirm your password");
      confirmPasswdPn.add(showPasswd2);
      add(confirmPasswdPn, gbc);

      gbc.insets = new Insets(10, 10, 10, 10);
      gbc.gridy++;
      gbc.gridx = 0;
      gbc.gridwidth = 2;
      add(confirmBt, gbc);
    }

    public CustomButton createShowPasswdBt(JPasswordField field, String placeHolder) {
      CustomButton showPasswdBt =
          ButtonConfig.createCustomButton(
              "",
              Style.FONT_BOLD_18,
              Color.white,
              Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
              Style.LIGHT_BlUE,
              0,
              SwingConstants.CENTER,
              new Dimension(45, 45));
      showPasswdBt.setIcon(new ImageIcon("src/main/java/Icon/showPasswd_Icon.png"));
      showPasswdBt.setFocusable(false);
      showPasswdBt.addActionListener(
          e -> {
            if (new String(field.getPassword()).equals(placeHolder)) {
            } else if (field.getEchoChar() == '*') {
              field.setEchoChar((char) 0);
              showPasswdBt.setIcon(new ImageIcon("src/main/java/Icon/hidePasswd_Icon.png"));
            } else {
              field.setEchoChar('*');
              showPasswdBt.setIcon(new ImageIcon("src/main/java/Icon/showPasswd_Icon.png"));
            }
          });
      return showPasswdBt;
    }
  }

  private class RegistrationSuccessPanel extends JPanel {

    RegistrationSuccessPanel() {
      setBackground(Color.WHITE);
      setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      setBorder(BorderFactory.createEmptyBorder(0, 50, 50, 50));
      JLabel titleLabel = new JLabel("<html>Verification Successful</html>", SwingConstants.CENTER);
      titleLabel.setFont(Style.FONT_TITLE_BOLD_45);
      titleLabel.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
      titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

      JLabel greetingLb =
          new JLabel(
              "<html>You're all set!!!<br>Sign in with your new account and enjoy using the application.</html>");
      greetingLb.setFont(Style.FONT_BOLD_30);
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
    private JTextField emailField;
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

      signInLabel = new JLabel("Sign In", SwingConstants.CENTER);
      signInLabel.setFont(Style.FONT_TITLE_BOLD_45);
      signInLabel.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
      gbc.gridx = 0;
      gbc.gridy = 0;
      gbc.gridwidth = 2;
      gbc.anchor = GridBagConstraints.CENTER;
      add(signInLabel, gbc);

      signInButton =
          ButtonConfig.createCustomButton(
              "Sign In",
              Style.FONT_BOLD_24,
              Color.white,
              Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
              new Color(160, 231, 224),
              Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
              1,
              20,
              SwingConstants.CENTER,
              new Dimension(350, 45));

      gbc.gridwidth = 1;
      gbc.gridy++;
      gbc.gridx = 0;
      JLabel roleIcon = new JLabel(new ImageIcon("src/main/java/Icon/role_icon.png"));
      roleIcon.setPreferredSize(new Dimension(30, 30));
      add(roleIcon, gbc);
      roleComboBox = new JComboBox<>(new String[] {CUSTOMER_ROLE, MANAGER_ROLE});
      roleComboBox.setPreferredSize(new Dimension(300, 45));
      roleComboBox.setFont(Style.FONT_PLAIN_20);
      roleComboBox.setBackground(Color.WHITE);

      roleComboBox.setBorder(
          BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1));
      gbc.gridx = 1;
      add(roleComboBox, gbc);

      gbc.gridwidth = 1;
      gbc.gridy++;
      gbc.gridx = 0;
      JLabel nameIcon = new JLabel(new ImageIcon("src/main/java/Icon/user_icon.png"));
      nameIcon.setPreferredSize(new Dimension(30, 30));
      add(nameIcon, gbc);

      gbc.gridx = 1;
      emailField =
          TextFieldConfig.createTextField(
              "User Email", Style.FONT_PLAIN_20, Color.GRAY, new Dimension(300, 45));
      emailField.addFocusListener(
          new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
              if (emailField.getText().equals("User Email")) {
                emailField.setText("");
                emailField.setForeground(Color.BLACK);
              }
              emailField.setBorder(
                  BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 4));
            }

            @Override
            public void focusLost(FocusEvent e) {
              if (emailField.getText().isEmpty()) {
                emailField.setForeground(Color.GRAY);
                emailField.setText("User Email");
              }
              emailField.setBorder(
                  BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1));
            }
          });
      emailField.addActionListener(e -> signInButton.doClick());
      add(emailField, gbc);

      gbc.gridy++;
      gbc.gridx = 0;
      JLabel passwordIcon = new JLabel(new ImageIcon("src/main/java/Icon/lock_icon.png"));
      passwordIcon.setPreferredSize(new Dimension(30, 30));
      add(passwordIcon, gbc);

      gbc.gridx = 1;
      passwdFieldSignin =
          PasswordFieldConfig.createPasswordFieldWithPlaceHolder(
              "Password", Style.FONT_PLAIN_20, Color.GRAY, new Dimension(300, 45));
      passwdFieldSignin.addActionListener(e -> signInButton.doClick());
      add(passwdFieldSignin, gbc);

      gbc.gridy++;
      gbc.gridx = 1;
      showPasswdCB = new JCheckBox("Show Password");
      showPasswdCB.setPreferredSize(new Dimension(300, 15));
      showPasswdCB.setFocusPainted(false);
      showPasswdCB.setFont(Style.FONT_PLAIN_13);
      showPasswdCB.setFocusable(false);
      showPasswdCB.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
      showPasswdCB.setBackground(Color.WHITE);
      showPasswdCB.addActionListener(
          new ActionListener() {
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

      gbc.gridy++;
      gbc.gridx = 0;
      gbc.gridwidth = 2;
      signInButton.addActionListener(
          new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              String username = emailField.getText();
              String password = new String(passwdFieldSignin.getPassword());
              if (username.isEmpty()
                  || password.isEmpty()
                  || username.equals("User Email")
                  || password.equals("Password")) {
                if (username.isEmpty() || username.equals("User Email")) {
                  emailField.setBorder(
                      BorderFactory.createLineBorder(Style.DELETE_BUTTON_COLOR_RED, 4));
                  emailField.setForeground(Style.DELETE_BUTTON_COLOR_RED);
                }
                if (password.isEmpty() || password.equals("Password")) {
                  passwdFieldSignin.setBorder(
                      BorderFactory.createLineBorder(Style.DELETE_BUTTON_COLOR_RED, 4));
                  passwdFieldSignin.setForeground(Style.DELETE_BUTTON_COLOR_RED);
                }

              } else {
                switch ((String) roleComboBox.getSelectedItem()) {
                  case MANAGER_ROLE:
                    {
                      if (COMPUTER_SHOP.login(username, password, LoginStatus.MANAGER)) {
                        loginFrame.setVisible(false);
                        managerFrame = new ManagerFrame();
                      } else {
                        sayError("Wrong login information!");
                      }
                      break;
                    }
                  case CUSTOMER_ROLE:
                    {
                      if (COMPUTER_SHOP.login(username, password, LoginStatus.CUSTOMER)) {
                        loginFrame.setVisible(false);
                        try {
                          customerFrame = new CustomerFrame();
                        } catch (SQLException ex) {
                          throw new RuntimeException(ex);
                        }
                      } else sayError("Wrong login information!");
                      break;
                    }
                }
              }
            }
          });
      add(signInButton, gbc);

      forgotPasswdBt = new JButton("You forgot your Password?");
      forgotPasswdBt.setFont(Style.FONT_PLAIN_13);
      forgotPasswdBt.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
      forgotPasswdBt.setBackground(Color.WHITE);
      forgotPasswdBt.setFocusable(false);
      forgotPasswdBt.setBorderPainted(false);
      forgotPasswdBt.addMouseListener(
          new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
              forgotPasswdBt.setBackground(Style.LIGHT_BlUE);
            }

            public void mouseExited(MouseEvent evt) {
              forgotPasswdBt.setBackground(Color.WHITE);
            }
          });
      forgotPasswdBt.addActionListener(
          e -> {
            inputFormPanel.showPanel("forgotPasswd");
            TextFieldConfig.resetTextField(
                inputFormPanel.forgotPasswdPanel.inputEmail.emailField, "User Email");
          });
      gbc.gridy++;
      gbc.gridx = 0;
      gbc.gridwidth = 2;
      add(forgotPasswdBt, gbc);
    }
  }

  private void sayError(String message) {
    JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  private class ForgotPasswdPanel extends JPanel {
    private InputEmail inputEmail;
    private VerificationCodePanel verificationCodePanel;
    private SetNewPasswdPanel setNewPasswdPanel;
    private CardLayout cardLayoutForgotPass;
    private int otp = 0;
    private String to = "";
    private String name = "";
    private static String email;
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

    private boolean sendOTP(String to, String name, int theOtp) {
      EmailConfig emailConfig = new EmailConfig();
      return emailConfig.send(
          to, emailConfig.buildHeaderMessage(), emailConfig.buildBodyMessage(name, theOtp));
    }

    class InputEmail extends JPanel {
      private JTextField emailField;

      private CustomButton sendCodeBt, backBt;
      private JRadioButton managerBt, customerBt;

      public InputEmail() {
        setBackground(Color.WHITE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 10, 20, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel resetPasswdLb = new JLabel("Reset Password", SwingConstants.CENTER);
        resetPasswdLb.setFont(Style.FONT_TITLE_BOLD_45);
        resetPasswdLb.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(resetPasswdLb, gbc);

        ButtonGroup group = new ButtonGroup();

        managerBt =
            createRadioButton(
                "Manager",
                Style.FONT_PLAIN_15,
                Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                Style.WORD_COLOR_WHITE,
                new Dimension(120, 40));
        customerBt =
            createRadioButton(
                "Customer",
                Style.FONT_PLAIN_15,
                Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                Style.WORD_COLOR_WHITE,
                new Dimension(120, 40));
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

        sendCodeBt =
            ButtonConfig.createCustomButton(
                "Send Code",
                Style.FONT_BOLD_24,
                Color.white,
                Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                new Color(160, 231, 224),
                Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                1,
                20,
                SwingConstants.CENTER,
                new Dimension(350, 50));
        sendCodeBt.addActionListener(
            e -> {
              email = emailField.getText();
              if (managerBt.isSelected()) {
                forgotPasswordStatus = ForgotPasswordStatus.MANAGER;
                var manager = COMPUTER_SHOP.findManagerByEmail(email);
                if (manager == null)
                  ToastNotification.showToast("Not found user", 3000, 30, -1, -1);
                name = manager.getFullName();

              } else {
                forgotPasswordStatus = ForgotPasswordStatus.CUSTOMER;
                var customer = COMPUTER_SHOP.findCustomerByEmail(email);
                if (customer == null)
                  ToastNotification.showToast("Not found user", 3000, 30, -1, -1);
                name = customer.getFullName();
              }
              otp = new EmailConfig().generateOTP();
              System.out.println("otp " + otp);
              sendOTP(email, name, otp);
              showInnerPanel("verificationCode");
            });

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel emailIcon = new JLabel(new ImageIcon("src/main/java/Icon/email_icon.png"));
        emailIcon.setPreferredSize(new Dimension(30, 30));
        add(emailIcon, gbc);
        gbc.gridx = 1;
        emailField =
            TextFieldConfig.createTextField(
                "User Email", Style.FONT_PLAIN_20, Color.GRAY, new Dimension(300, 45));
        emailField.addActionListener(
            e -> {
              sendCodeBt.doClick();
            });
        add(emailField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(sendCodeBt, gbc);

        backBt =
            ButtonConfig.createCustomButton(
                "Back",
                Style.FONT_BOLD_24,
                Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                Color.white,
                new Color(160, 231, 224),
                Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                1,
                20,
                SwingConstants.CENTER,
                new Dimension(350, 50));
        backBt.addActionListener(
            new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                inputFormPanel.showPanel("signIn");
              }
            });
        gbc.gridy++;
        add(backBt, gbc);
      }

      private void setTextForTextField() {
        if (customerBt.isSelected()) {
          emailField.setText("User Email");
        } else {
          emailField.setText("User Name");
        }
      }

      private void setColorTextField() {
        emailField.setBorder(BorderFactory.createLineBorder(Style.DELETE_BUTTON_COLOR_RED, 4));
      }

      private JRadioButton createRadioButton(
          String title, Font font, Color textColor, Color backgroudColor, Dimension size) {
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
      final int RESET_OTP = 0;

      VerificationCodePanel() {
        setBackground(Color.WHITE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel resetPasswdLb = new JLabel("Enter Verification Code", SwingConstants.CENTER);
        resetPasswdLb.setFont(Style.FONT_TITLE_BOLD_45);
        resetPasswdLb.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(resetPasswdLb, gbc);

        verifyBt =
            ButtonConfig.createCustomButton(
                "Verify",
                Style.FONT_BOLD_24,
                Color.white,
                Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                new Color(160, 231, 224),
                Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                1,
                20,
                SwingConstants.CENTER,
                new Dimension(350, 50));
        verifyBt.addActionListener(
            new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                boolean isEmpty = false;
                for (int i = 0; i < otpFields.length; i++) {
                  if (otpFields[i].getText().trim().isEmpty()) {
                    otpFields[i].setBorder(
                        BorderFactory.createLineBorder(Style.DELETE_BUTTON_COLOR_RED, 4));
                    isEmpty = true;
                  }
                }
              }
            });

        verifyBt.addActionListener(
            new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                boolean isEmpty = false;
                int otpInput = 0;
                try {
                  for (int i = 0; i < otpFields.length; i++) {
                    int num = Integer.parseInt(otpFields[i].getText().trim());
                    otpInput += num;
                    if (i < otpFields.length - 1) otpInput *= 10;
                  }
                } catch (NullPointerException exception) {
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
                  ToastNotification.showToast("Wrong OTP", 2500, 50, -1, -1);
                }
              }

              private static void resetPasswdField(
                  JPasswordField passwordField, String placeholder) {
                passwordField.setForeground(Color.GRAY);
                passwordField.setText(placeholder);
                passwordField.setEchoChar((char) 0);
              }
            });

        resendCodeBt = new JButton("Re-send Verify Code");
        resendCodeBt.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
        resendCodeBt.setBackground(Color.WHITE);
        resendCodeBt.setFont(Style.FONT_PLAIN_16);
        resendCodeBt.setFocusable(false);
        resendCodeBt.setBorderPainted(false);
        resendCodeBt.addMouseListener(
            new MouseAdapter() {
              public void mouseEntered(MouseEvent evt) {
                resendCodeBt.setBackground(Style.LIGHT_BlUE);
              }

              public void mouseExited(MouseEvent evt) {
                resendCodeBt.setBackground(Color.WHITE);
              }
            });
        resendCodeBt.addActionListener(
            new ActionListener() {
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
                System.out.println(otp + " new ");
                boolean sent = sendOTP(to, name, otp);
                JOptionPane.showMessageDialog(
                    null, "We have sent a new verification code to your email!");
              }

              private void startCooldown() {
                TimerTask task =
                    new TimerTask() {
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

        backBt =
            ButtonConfig.createCustomButton(
                "Back",
                Style.FONT_BOLD_24,
                Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                Color.white,
                new Color(160, 231, 224),
                Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                1,
                20,
                SwingConstants.CENTER,
                new Dimension(350, 50));
        backBt.addActionListener(
            new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                showInnerPanel("inputEmail");
              }
            });

        gbc.gridy++;
        JPanel otpPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        otpPanel.setBackground(Color.WHITE);
        for (int i = 0; i < 4; i++) {
          otpFields[i] = new JTextField();
          otpFields[i].setFont(new Font("Arial", Font.BOLD, 60));
          otpFields[i].setHorizontalAlignment(JTextField.CENTER);
          otpFields[i].setPreferredSize(new Dimension(80, 90));
          otpFields[i].setBorder(
              BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1));
          otpFields[i].addActionListener(e -> verifyBt.doClick());
          int index1 = i;
          otpFields[i].addFocusListener(
              new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                  otpFields[index1].setBorder(
                      BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 4));
                }

                @Override
                public void focusLost(FocusEvent e) {

                  otpFields[index1].setBorder(
                      BorderFactory.createLineBorder(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE, 1));
                }
              });

          int index = i;
          otpFields[i].addKeyListener(
              new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                  if (!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                  } else if (otpFields[index].getText().length() >= 1) {
                    e.consume();
                  }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                  if (Character.isDigit(e.getKeyChar())
                      && otpFields[index].getText().length() == 1) {
                    if (index < 3) {
                      SwingUtilities.invokeLater(() -> otpFields[index + 1].requestFocus());
                    }
                  } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    if (index > 0 && otpFields[index].getText().isEmpty()) {
                      otpFields[index - 1].setText("");
                      otpFields[index - 1].requestFocus();
                    }
                  }
                }
              });
          otpPanel.add(otpFields[i]);
        }

        add(otpPanel, gbc);

        gbc.gridy++;
        JPanel resendCodePn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        resendCodePn.setBackground(Color.WHITE);
        resendCodePn.add(resendCodeBt);
        add(resendCodePn, gbc);

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

      private void setColorTextField() {
        for (int i = 0; i < otpFields.length; i++) {
          otpFields[i].setBorder(BorderFactory.createLineBorder(Style.DELETE_BUTTON_COLOR_RED, 4));
        }
      }
    }

    class SetNewPasswdPanel extends JPanel {
      JPasswordField newPasswdField, confirmPasswdField;
      CustomButton resetPasswdBt, backBt;
      JCheckBox showPasswd;

      private void resetTextPassword() {
        newPasswdField.setText("Enter your new password");
        confirmPasswdField.setText("Confirm your new password");
      }

      SetNewPasswdPanel() {
        setBackground(Color.WHITE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel setNewPasswdLb = new JLabel("New Password", SwingConstants.CENTER);
        setNewPasswdLb.setFont(Style.FONT_TITLE_BOLD_45);
        setNewPasswdLb.setForeground(Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(setNewPasswdLb, gbc);

        resetPasswdBt =
            ButtonConfig.createCustomButton(
                "Reset Password",
                Style.FONT_BOLD_24,
                Color.white,
                Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                new Color(160, 231, 224),
                Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                1,
                20,
                SwingConstants.CENTER,
                new Dimension(350, 50));
        resetPasswdBt.addActionListener(
            new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                String newPassword = new String(newPasswdField.getPassword());
                String confirmPassword = new String(confirmPasswdField.getPassword());

                if ((!newPassword.equals(confirmPassword))
                    || newPassword.equals("Enter your new password")
                    || confirmPassword.equals("Confirm your new password")) {

                  if (newPassword.equals("Enter your new password")) {
                    newPasswdField.setBorder(
                        BorderFactory.createLineBorder(Style.DELETE_BUTTON_COLOR_RED, 4));
                    newPasswdField.setForeground(Style.DELETE_BUTTON_COLOR_RED);

                  } else if (confirmPassword.equals("Confirm your new password")) {
                    confirmPasswdField.setBorder(
                        BorderFactory.createLineBorder(Style.DELETE_BUTTON_COLOR_RED, 4));
                    confirmPasswdField.setForeground(Style.DELETE_BUTTON_COLOR_RED);

                  } else {
                    confirmPasswdField.setBorder(
                        BorderFactory.createLineBorder(Style.DELETE_BUTTON_COLOR_RED, 4));
                    confirmPasswdField.setForeground(Style.DELETE_BUTTON_COLOR_RED);
                    newPasswdField.setBorder(
                        BorderFactory.createLineBorder(Style.DELETE_BUTTON_COLOR_RED, 4));
                    newPasswdField.setForeground(Style.DELETE_BUTTON_COLOR_RED);
                  }

                } else {
                  if (forgotPasswordStatus == ForgotPasswordStatus.CUSTOMER) {
                    // set pass customer
                    //                    CustomerController cusController = new
                    // CustomerController();
                    //                    cusController.updatePassword(newPassword, id);
                    COMPUTER_SHOP.changePassword(UserType.CUSTOMER, email, newPassword);
                    resetTextPassword();

                  } else if (forgotPasswordStatus == ForgotPasswordStatus.MANAGER) {
                    // setPass manager
                    COMPUTER_SHOP.changePassword(UserType.MANAGER, email, newPassword);
                    //                    AccountController accController = new AccountController();
                    //                    accController.updatePassword(newPassword, id);
                    //                    resetTextPassword();
                  }
                  JOptionPane.showMessageDialog(null, "Password reset successfully!");
                  showInnerPanel("inputEmail");
                  inputFormPanel.showPanel("signIn");
                }
              }
            });

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        ImageIcon passwordIcon = new ImageIcon("src/main/java/Icon/lock_icon.png");
        JLabel passwordIconLb1 = new JLabel(passwordIcon);

        add(passwordIconLb1, gbc);
        gbc.gridx = 1;
        newPasswdField =
            PasswordFieldConfig.createPasswordFieldWithPlaceHolder(
                "Enter your new password", Style.FONT_PLAIN_20, Color.GRAY, new Dimension(300, 45));
        newPasswdField.addActionListener(e -> resetPasswdBt.doClick());
        add(newPasswdField, gbc);

        JLabel passwordIconLb2 = new JLabel(passwordIcon);
        gbc.gridy++;
        gbc.gridx = 0;
        add(passwordIconLb2, gbc);
        gbc.gridx = 1;
        confirmPasswdField =
            PasswordFieldConfig.createPasswordFieldWithPlaceHolder(
                "Confirm your new password",
                Style.FONT_PLAIN_20,
                Color.GRAY,
                new Dimension(300, 45));
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
        showPasswd.addActionListener(
            new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                String passwdNew = new String(newPasswdField.getPassword());
                String passwdConfirm = new String(confirmPasswdField.getPassword());

                if (passwdNew.equals("Enter your new password")
                    && passwdConfirm.equals("Confirm your new password")) {
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

        backBt =
            ButtonConfig.createCustomButton(
                "Back",
                Style.FONT_BOLD_24,
                Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                Color.white,
                new Color(160, 231, 224),
                Style.LOGIN_FRAME_BACKGROUND_COLOR_BLUE,
                1,
                20,
                SwingConstants.CENTER,
                new Dimension(350, 50));
        backBt.addActionListener(
            new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                showInnerPanel("verificationCode");
              }
            });

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

  public static void main(String[] args) {
    new LoginFrame();
  }
}

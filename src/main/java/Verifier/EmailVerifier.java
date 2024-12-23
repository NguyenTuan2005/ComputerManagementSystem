package Verifier;

import Model.Customer;
import dao.CustomerDAO;
import java.awt.*;
import javax.swing.*;
import view.OverrideComponent.ToastNotification;

public class EmailVerifier extends InputVerifier {
  private CustomerDAO customerDAO;

  @Override
  public boolean verify(JComponent input) {
    String email = ((JTextField) input).getText();
    boolean isEmail =
        email.contains("@") && email.indexOf("@") != 0 && email.indexOf("@") != email.length() - 1;
    customerDAO = new CustomerDAO();
    Customer found = customerDAO.findByEmail(email);
    if (found != null && found.sameEmail(email)) {
      ToastNotification.showToast("Email is existed !!", 3000, 40, -1, -1);
      input.setBackground(Color.PINK);
      return false;
    } else {
      input.setBackground(Color.WHITE);
    }

    if (!isEmail) {
      input.setBackground(Color.PINK);
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      JWindow window = new JWindow();
      ToastNotification.showToast(
          "SAi roi má",
          2500,
          50,
          screenSize.width - window.getWidth() - 10,
          screenSize.height - window.getHeight() - 50);
      return false;
    } else {
      input.setBackground(Color.WHITE);
    }
    return true;
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(
        () -> {
          JFrame frame = new JFrame("Demo Verifier JTextField");
          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          frame.setSize(400, 200);
          frame.setLayout(new FlowLayout());

          JLabel label = new JLabel("Nhập số email ): ");
          JTextField textField = new JTextField(15);

          // Gắn InputVerifier cho JTextField
          //            textField.setInputVerifier(new InputVerifier() {
          //                @Override
          //                public boolean verify(JComponent input) {
          //                    String text = ((JTextField) input).getText();
          //                    try {
          //                        int value = Integer.parseInt(text);
          //                        if (value >= 0 && value <= 100) {
          //                            input.setBackground(Color.WHITE); // Màu nền bình thường
          //                            return true;
          //                        } else {
          //                            input.setBackground(Color.PINK); // Màu nền khi lỗi
          //                            return false;
          //                        }
          //                    } catch (NumberFormatException e) {
          //                        input.setBackground(Color.PINK);
          //                        return false;
          //                    }
          //                }
          //            });
          textField.setInputVerifier(new UserNameAccountVerifier());

          JButton submitButton = new JButton("Submit");
          submitButton.addActionListener(
              e -> {
                //                if (textField.getInputVerifier().verify(textField)) {
                //                    JOptionPane.showMessageDialog(frame, "Giá trị hợp lệ: " +
                // textField.getText());
                //                } else {
                //                    JOptionPane.showMessageDialog(frame, "Giá trị không hợp lệ.
                // Hãy sửa lại!");
                //                }
                //                textField.getInputVerifier();
              });

          frame.add(label);
          frame.add(textField);
          frame.add(submitButton);

          frame.setVisible(true);
        });
  }
}

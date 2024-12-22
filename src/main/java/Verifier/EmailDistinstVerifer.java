package Verifier;

import Model.Customer;
import dao.CustomerDAO;
import java.awt.*;
import javax.swing.*;
import view.OverrideComponent.ToastNotification;

public class EmailDistinstVerifer extends InputVerifier {
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
}

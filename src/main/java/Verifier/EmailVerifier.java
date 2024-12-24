package Verifier;

import java.awt.*;
import javax.swing.*;
import view.OverrideComponent.ToastNotification;

public class EmailVerifier extends InputVerifier {

  @Override
  public boolean verify(JComponent input) {
    String email = ((JTextField) input).getText();
    boolean isEmail =
        email.contains("@") && email.indexOf("@") != 0 && email.indexOf("@") != email.length() - 1;

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

package verifier;

import java.awt.*;
import javax.swing.*;
import view.overrideComponent.ToastNotification;

public class NotNullVerifier extends InputVerifier {
  @Override
  public boolean verify(JComponent input) {
    String text = ((JTextField) input).getText().trim();
    if (text.isEmpty()) {
      input.setBackground(Color.pink);
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      JWindow window = new JWindow();
      ToastNotification.showToast(
          "SAi roi má",
          2500,
          50,
          screenSize.width - window.getWidth() - 10,
          screenSize.height - window.getHeight() - 50);
      return false;
    }
    input.setBackground(Color.white);
    return true;
  }
}

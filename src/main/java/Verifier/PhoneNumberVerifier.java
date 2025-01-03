package Verifier;

import java.awt.*;
import javax.swing.*;
import view.OverrideComponent.ToastNotification;

public class PhoneNumberVerifier extends InputVerifier {
  @Override
  public boolean verify(JComponent input) {
    String phonenum = ((JTextField) input).getText().trim();
    boolean isPhonenum =
        phonenum.length() == 10 && phonenum.charAt(0) == '0' && isNumeric(phonenum);
    if (!isPhonenum) {
      input.setBackground(Color.PINK);
      ToastNotification.showToast("Wrong phone number" + phonenum, 2500, 50, -1, -1);
      return false;
    } else {
      input.setBackground(Color.WHITE);
      return true;
    }
  }

  private boolean isNumeric(String str) {
    return str != null && str.matches("\\d+");
  }
}

package Verifier;

import controller.AccountController;
import java.awt.*;
import javax.swing.*;
import view.OverrideComponent.ToastNotification;

public class UserNameAccountVerifier extends InputVerifier {
  private AccountController accountController;

  @Override
  public boolean verify(JComponent input) {
    this.accountController = new AccountController();
    String name = ((JTextField) input).getText().trim();
    if (name.isEmpty()) {
      System.out.println("trim");
      input.setBackground(Color.PINK);
      return false;
    }

    var acc = accountController.findByName(name);
    if (acc == null) {
      input.setBackground(Color.white);
      return true;
    }
    System.out.println(acc);
    if (acc.sameUsername(name)) {
      System.out.println("ok nef");
      input.setBackground(Color.PINK);
      ToastNotification.showToast("Username used !!!", 2500, 50, -1, -1);
      return false;
    } else {
      input.setBackground(Color.white);
      return true;
    }
  }
}

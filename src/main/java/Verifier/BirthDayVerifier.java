package Verifier;

import static java.awt.SystemColor.window;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.*;
import view.OverrideComponent.ToastNotification;

public class BirthDayVerifier extends InputVerifier {
  private static final String[] DATE_FORMATS = {"yyyy-MM-dd", "dd/MM/yyyy"};

  @Override
  public boolean verify(JComponent input) {
    String birthday = ((JTextField) input).getText().trim();

    for (String format : DATE_FORMATS) {
      SimpleDateFormat dateFormat = new SimpleDateFormat(format);
      dateFormat.setLenient(false);
      try {
        dateFormat.parse(birthday);
        if (isFutureDate(birthday, dateFormat)) {
          input.setBackground(java.awt.Color.PINK);
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

        input.setBackground(java.awt.Color.WHITE);
        return true;
      } catch (ParseException e) {

        input.setBackground(java.awt.Color.PINK);
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
    }
    return false;
  }

  private boolean isFutureDate(String birthday, SimpleDateFormat dateFormat) {
    try {
      return dateFormat.parse(birthday).after(new java.util.Date());
    } catch (ParseException e) {
      return false;
    }
  }
}

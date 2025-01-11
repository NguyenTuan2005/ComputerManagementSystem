package verifier;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.border.Border;
import view.Style;

public class BirthDayVerifier extends InputVerifier {
  private static final String[] DATE_FORMATS = {"yyyy-MM-dd"};

  @Override
  public boolean verify(JComponent input) {
    JTextField birthDateField = (JTextField) input;
    Border defaultBorder = birthDateField.getBorder();
    String birthDay = birthDateField.getText();
    if (isValidDate(birthDay)) {
      birthDateField.setBorder(defaultBorder);
      return true;
    } else {
      birthDateField.setBorder(BorderFactory.createLineBorder(Style.DELETE_BUTTON_COLOR_RED, 3));
      return false;
    }
  }

  private static boolean isValidDate(String date) {
    try {
      for (String s : DATE_FORMATS) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(s);
        dateFormat.setLenient(false);

        dateFormat.parse(date);
        return true;
      }
    } catch (ParseException e) {
      return false;
    }
    return false;
  }
}

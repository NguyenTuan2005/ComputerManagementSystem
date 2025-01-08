package verifier;

import view.Style;
import view.overrideComponent.ToastNotification;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BirthDayVerifier extends InputVerifier {
    @Override
    public boolean verify(JComponent input) {
        JTextField birthDateField = (JTextField) input;
        Border defaultBorder = birthDateField.getBorder();
        String birthDay = birthDateField.getText();
        if (isValidDate(birthDay)) {
            birthDateField.setBorder(defaultBorder);
            return true;
        } else {
            birthDateField.setBorder( BorderFactory.createLineBorder(Style.DELETE_BUTTON_COLOR_RED, 3));
            return false;
        }
    }

    private static boolean isValidDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

}

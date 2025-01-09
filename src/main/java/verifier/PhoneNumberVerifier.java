package verifier;



import view.Style;
import view.overrideComponent.ToastNotification;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class PhoneNumberVerifier extends InputVerifier {
    @Override
    public boolean verify(JComponent input) {
        JTextField phoneField = (JTextField) input;
        Border defaultBorder = phoneField.getBorder();
        String phoneNumber = phoneField.getText();
        if (isPhoneNumber(phoneNumber)) {
            phoneField.setBorder(defaultBorder);
            return true;
        } else {
            ToastNotification.showToast( "Invalid phone number format",2500,50,-1,-1);
            phoneField.setBorder( BorderFactory.createLineBorder(Style.DELETE_BUTTON_COLOR_RED, 3));
            return false;
        }
    }
    private  boolean isPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("0\\d{9}");
    }
}

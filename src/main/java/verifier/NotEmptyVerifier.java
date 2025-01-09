package verifier;

import view.overrideComponent.ToastNotification;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class NotEmptyVerifier extends InputVerifier {
    @Override
    public boolean verify(JComponent input) {
        JTextField textField = (JTextField) input;
        String text = textField.getText();
        Border defaultBorder = textField.getBorder();
        if (text.isEmpty()) {
            textField.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
            ToastNotification.showToast( "Please re-enter the name",2500,50,-1,-1);
            return false;
        } else {
            textField.setBorder(defaultBorder);
            return true;
        }
    }
}

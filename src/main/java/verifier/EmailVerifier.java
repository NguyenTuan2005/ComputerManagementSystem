package verifier;


import view.overrideComponent.ToastNotification;

import javax.swing.*;
import java.awt.*;

public class EmailVerifier extends InputVerifier {
//    private CustomerDAO customerDAO;

    @Override
    public boolean verify(JComponent input) {
        JTextField textField = (JTextField) input;
        String email = (textField).getText().trim();

        if( !isEmail(email) ) {
            textField.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
            ToastNotification.showToast( "Wrong email, please re-enter the Email!",2500,50,-1,-1);
            return false;
        }else {
            input.setBackground(Color.WHITE);
        }
        return true;
    }

    public boolean isEmail(String email) {
        return email.contains("@") && email.indexOf("@")!= 0 && email.indexOf("@")!= email.length()-1;
    }

}

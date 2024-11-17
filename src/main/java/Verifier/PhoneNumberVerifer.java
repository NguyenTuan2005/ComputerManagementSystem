package Verifier;

import view.OverrideComponent.ToastNotification;

import javax.swing.*;
import java.awt.*;

public class PhoneNumberVerifer extends InputVerifier {
    @Override
    public boolean verify(JComponent input) {
        String phonenum = ((JTextField) input).getText().trim();
        boolean isPhonenum =phonenum.length() == 10 && phonenum.charAt(0) =='0'&& isNumeric(phonenum);
        if( !isPhonenum){
            input.setBackground(Color.PINK);
            ToastNotification.showToast( "Wrong "+ phonenum,2500,500,60);
            return false;
        }else {
            input.setBackground(Color.WHITE);
            return true;
        }
    }
    private  boolean isNumeric(String str) {
        return str != null && str.matches("\\d+");
    }
}

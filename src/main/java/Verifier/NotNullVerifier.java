package Verifier;

import view.OverrideComponent.ToastNotification;

import javax.swing.*;
import java.awt.*;

public class NotNullVerifier extends InputVerifier {
    @Override
    public boolean verify(JComponent input) {
        String text  = ((JTextField) input).getText().trim();
        if(text.isEmpty()){
            input.setBackground(Color.pink);
            ToastNotification.showToast("chua fill het thong tin ",2500,400,100);
            return false;
        }
        input.setBackground(Color.white);
        return true;
    }
}

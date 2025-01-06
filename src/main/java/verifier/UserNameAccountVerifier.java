package verifier;

import view.overrideComponent.ToastNotification;

import javax.swing.*;
import java.awt.*;

public class UserNameAccountVerifier extends InputVerifier {

    @Override
    public boolean verify(JComponent input) {

        String  name = ((JTextField) input).getText().trim();
        if(name.isEmpty())
        {
            System.out.println("trim");
            input.setBackground(Color.PINK);
            return false;
        }


     return true;

    }
}

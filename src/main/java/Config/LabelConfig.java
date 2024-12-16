package Config;
import javax.swing.*;
import java.awt.*;

public class LabelConfig {

    public static JLabel createLabel(String text, Font font, Color textColor, int textPosition){
        JLabel label = new JLabel(text);
        label.setForeground(textColor);
        label.setFont(font);
        label.setHorizontalAlignment(textPosition);
        return label;
    }

    public static JLabel createLabel(String text, Font font, Color textColor, int horizontal, int vertical ){
        JLabel label = new JLabel(text);
        label.setForeground(textColor);
        label.setFont(font);
        label.setHorizontalAlignment(horizontal);
        label.setVerticalAlignment(vertical);
        return label;
    }


}

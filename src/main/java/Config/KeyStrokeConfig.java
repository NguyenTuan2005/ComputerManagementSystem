package Config;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class KeyStrokeConfig {
    public static final String addKey = "control N";
    public static final String modifyKey = "control M";
    public static final String deleteKey = "DELETE";
    public static final String exportExcelKey = "control E";
    public static final String importExcelKey = "control I";
    public static final String reloadKey = "F5";


    public static void addKeyBindingButton(JPanel panel, String keyStroke, JButton button) {
        InputMap inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = panel.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(keyStroke), keyStroke);
        actionMap.put(keyStroke, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button.doClick();
            }
        });
    }

}

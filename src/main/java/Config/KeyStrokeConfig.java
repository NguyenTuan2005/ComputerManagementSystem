package Config;

import java.awt.event.ActionEvent;
import javax.swing.*;

public class KeyStrokeConfig {
  public static final String addKey = "control N";
  public static final String modifyKey = "control M";
  public static final String deleteKey = "DELETE";
  public static final String exportExcelKey = "control E";
  public static final String importExcelKey = "control I";
  public static final String blockUserKey = "control B";
  public static final String setSaleKey = "control S";
  public static final String restockKey = "control Z";
  public static final String toBillKey = "control T";
  public static final String deliveryKey = "control D";
  public static final String reloadKey = "F5";
  public static final String ENTER = "ENTER";

  public static void addKeyBindingButton(JPanel panel, String keyStroke, JButton button) {
    InputMap inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    ActionMap actionMap = panel.getActionMap();

    inputMap.put(KeyStroke.getKeyStroke(keyStroke), keyStroke);
    actionMap.put(
        keyStroke,
        new AbstractAction() {
          @Override
          public void actionPerformed(ActionEvent e) {
            button.doClick();
          }
        });
  }
}

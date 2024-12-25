package Config;

import java.awt.*;
import java.net.URI;
import view.OverrideComponent.ToastNotification;

public class OpenEmailConfig {

  private final String subject = "I want to feedback about ";
  private final String body = "";

  public OpenEmailConfig() {
    openEmail(EmailConfig.APP_EMAIL, subject, body);
  }

  private static void openEmail(String email, String subject, String body) {
    try {
      String url =
          String.format(
              "https://mail.google.com/mail/?view=cm&to=%s&su=%s&body=%s",
              encode(email), encode(subject), encode(body));

      if (Desktop.isDesktopSupported()) {
        Desktop.getDesktop().browse(new URI(url));
      } else {
        ToastNotification.showToast("\"Desktop unsupport on system.\"", 3000, 40, -1, -1);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static String encode(String text) {
    return text.replace(" ", "%20").replace("\n", "%0A");
  }
}

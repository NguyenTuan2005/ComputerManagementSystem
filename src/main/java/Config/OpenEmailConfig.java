package Config;

import view.OverrideComponent.ToastNotification;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class OpenEmailConfig {
    private final String body = "";
    private final String sub = "Feedback from customer about computers";

    public OpenEmailConfig() {
        String url = String.format(
                "https://mail.google.com/mail/?view=cm&fs=1&to=%s&su=%s&body=%s",
                EmailConfig.APP_EMAIL,
                encodeURIComponent(sub),
                encodeURIComponent(body)
        );

        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(url));
            } else {
                ToastNotification.showToast("Device unsupport", 3000, 30, -1, -1);
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
    public static String encodeURIComponent(String value) {
        try {
            return java.net.URLEncoder.encode(value, "UTF-8").replace("+", "%20");
        } catch (Exception e) {
            return value;
        }
    }
}

package Config;

import java.util.Random;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.mail.SimpleEmail;

@NoArgsConstructor
public class EmailConfig {

  static final String HOST_NAME = "smtp.gmail.com";

  static final int SSL_PORT = 465;

  final int TSL_PORT = 587;

  public static final String APP_EMAIL = "hduy09092005@gmail.com";

  private static final String APP_PASSWORD = "srwmlebtfrsfdxkl";

  @SneakyThrows
  public boolean send(String to, String subject, String messageContent) {
    SimpleEmail email = new SimpleEmail();
    email.setHostName(HOST_NAME);
    email.setSmtpPort(TSL_PORT);
    email.setAuthentication(APP_EMAIL, APP_PASSWORD);
    email.setStartTLSRequired(true);
    email.setStartTLSEnabled(true);

    email.setFrom(APP_EMAIL);
    email.setSubject(subject);
    email.setMsg(messageContent);
    email.addTo(to);
    email.send();

    return true;
  }

  public int generateOTP() {
    Random random = new Random();
    int otp = random.nextInt(4999) + random.nextInt(4999);
    return otp + (otp <= 999 ? 1000 : 0);
  }

  public String buildHeaderMessage() {
    return "OTP";
  }

  public String buildBodyMessage(String recipientName, int otpCode) {
    StringBuilder message = new StringBuilder();
    message.append("Dear ").append(recipientName).append(",\n\n");
    message
        .append("We have received a request to verify your account. ")
        .append("To proceed, please use the following OTP:\n\n");
    message.append("Your OTP Code: ").append(otpCode).append("\n\n");
    message
        .append("Please note that this OTP is valid for 10 minutes. ")
        .append("Do not share this code with anyone.\n\n");
    message.append("If you did not request this, please ignore this email or contact support.\n\n");
    message.append("Thank you,\n");
    message.append("The Support Team Of Computer Management !!!");
    return message.toString();
  }

  public String buildBodyMessageForRegister(String recipientName, int otp) {
    StringBuilder message = new StringBuilder();
    message.append("Dear ").append(recipientName).append(",\n\n");
    message.append("Welcome to our system! We are excited to have you on board.\n\n");
    message.append("Here are your account details to get started:\n");
    message.append("Your OTP Code: ").append(otp).append("\n\n");
    message.append(
        "If you encounter any issues or have any questions, feel free to contact our support team.\n\n");
    message.append("Thank you for joining us,\n");
    message.append("The Support Team Of Computer Management !!!");
    return message.toString();
  }
}

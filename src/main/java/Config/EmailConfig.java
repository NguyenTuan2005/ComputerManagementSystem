package Config;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

public class EmailConfig {

    private final String HOST_NAME = "smtp.gmail.com";

    private final int SSL_PORT = 465; // Port for SSL

    private final int TSL_PORT = 587; // Port for TLS/STARTTLS

    public  static  final String APP_EMAIL = "hduy09092005@gmail.com"; // your email

    private final String APP_PASSWORD = "srwmlebtfrsfdxkl"; // your password

    public static  final String TITLE_EMAIL="ORDER SUCCESSFULLY";


    public boolean send(String to, String subject, String messageContent) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", HOST_NAME);
        props.put("mail.smtp.socketFactory.port", SSL_PORT);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.port",SSL_PORT);

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication( APP_EMAIL,  APP_PASSWORD);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(messageContent);
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public int generateOTP(){
        Random random = new Random();
        return 100 + random.nextInt(1000);
    }

    public static void main(String[] args) {
        EmailConfig emailConfig = new EmailConfig();
        System.out.println(emailConfig.generateOTP());
    }


}

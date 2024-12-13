package Config;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

public class EmailConfig {

    static  final String HOST_NAME = "smtp.gmail.com";

    static final int SSL_PORT = 465; // Port for SSL

    final int TSL_PORT = 587; // Port for TLS/STARTTLS

    public static  final String APP_EMAIL = "hduy09092005@gmail.com"; // your email

    static  final String APP_PASSWORD = "srwmlebtfrsfdxkl"; // your password




    public  boolean send(String to, String subject, String messageContent) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", HOST_NAME);
        props.put("mail.smtp.socketFactory.port",  SSL_PORT);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.port",  SSL_PORT);

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
        int otp = random.nextInt(4999) + random.nextInt(4999);
        return  otp + (otp<=999?1000:0);
    }

    public String buildHeaderMessage(){
        return "OTP";
    }

    public  String buildBodyMessage( String recipientName,int otpCode) {
        StringBuilder message = new StringBuilder();
        message.append("Dear ").append(recipientName).append(",\n\n");
        message.append("We have received a request to verify your account. ")
                .append("To proceed, please use the following OTP:\n\n");
        message.append("Your OTP Code: ").append(otpCode).append("\n\n");
        message.append("Please note that this OTP is valid for 10 minutes. ")
                .append("Do not share this code with anyone.\n\n");
        message.append("If you did not request this, please ignore this email or contact support.\n\n");
        message.append("Thank you,\n");
        message.append("The Support Team Of Computer Management !!!");
        return message.toString();
    }

    public String buildBodyMessageForRegister(String recipientName,int otp) {
        StringBuilder message = new StringBuilder();
        message.append("Dear ").append(recipientName).append(",\n\n");
        message.append("Welcome to our system! We are excited to have you on board.\n\n");
        message.append("Here are your account details to get started:\n");
        message.append("Your OTP Code: ").append(otp).append("\n\n");
        message.append("If you encounter any issues or have any questions, feel free to contact our support team.\n\n");
        message.append("Thank you for joining us,\n");
        message.append("The Support Team Of Computer Management !!!");
        return message.toString();
    }




    public static void main(String[] args) {
        EmailConfig emailConfig = new EmailConfig();
//        System.out.println(emailConfig.buildBodyMessage("dfsdf",124,"894032"));
    }
}

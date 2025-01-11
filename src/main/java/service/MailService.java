package service;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class MailService {

    // Kirim OTP ke email
    public void sendOtpEmail(String recipient, String otp) throws MessagingException {
        String senderEmail = "rmdhniki18@gmail.com";
        String senderPassword = "ikbw lbpl sepo hjeu";

        // Setup properties untuk session email
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // Membuat session email
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        // Membuat pesan email
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        message.setSubject("Your OTP for Registration");
        message.setText("Your OTP is: " + otp);  // Pesan berisi OTP

        // Kirim email
        Transport.send(message);
    }
}
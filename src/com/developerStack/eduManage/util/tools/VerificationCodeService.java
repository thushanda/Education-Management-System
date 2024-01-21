package com.developerStack.eduManage.util.tools;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class VerificationCodeService {
    public static int generateVerificationCode(int length){
        return new VerificationCodeGenerator().getCode(length);
    }

    public static void sendVerificationCodeByEmail(String fromEmail, String toEmail, int verificationCode) throws MessagingException, MessagingException {
        String host = "smtp.gmail.com";

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, "smug nskb vlak epqw");
            }
        });
        session.setDebug(true);

        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress(fromEmail));
        mimeMessage.setSubject("New Verification Code!");
        mimeMessage.setText("New Verification Code: " + verificationCode);
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

        Transport.send(mimeMessage);
    }


     /* String host = "127.0.0.1";

            Properties properties = System.getProperties();
            properties.setProperty("mail.smtp.host",host); // => use node=> nodemailer, (sendGrid,twilio)
            Session session = Session.getDefaultInstance(properties);*/
}

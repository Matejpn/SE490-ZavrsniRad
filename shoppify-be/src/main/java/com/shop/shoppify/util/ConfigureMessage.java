package com.shop.shoppify.util;
import org.springframework.http.HttpStatus;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class ConfigureMessage {

    public static Message message (String addressTo, String addressCc, String addressBcc, String subject) throws MessagingException {
        
        //TO DO
        String email = "matejwebshop@gmail.com";
        String password = "matejwebshop97";

        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

            Authenticator auth = new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(email, password);
                }
            };

            Message message = new MimeMessage(Session.getInstance(props, auth));
            message.setFrom(new InternetAddress(email));
            InternetAddress[] toAdressArray = InternetAddress.parse(addressTo);
            InternetAddress[] ccAdressArray = InternetAddress.parse(addressCc);
            InternetAddress[] bccAdressArray = InternetAddress.parse(addressBcc);

            message.addRecipients(Message.RecipientType.TO, toAdressArray);
            message.addRecipients(Message.RecipientType.CC, ccAdressArray);
            message.addRecipients(Message.RecipientType.BCC, bccAdressArray);
            message.setSubject(subject);
            return message;
        } catch (MessagingException e) {
            throw new ShoppifyException(HttpStatus.INTERNAL_SERVER_ERROR, "Email send failed!");
        }

    }
}

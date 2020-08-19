package com.shop.shoppify.service.impl;


import com.shop.shoppify.util.ConfigureMessage;
import com.shop.shoppify.util.ConfigureTemplate;

import javax.mail.Message;
import java.util.HashMap;
import java.util.Map;

public class SendEmail {

    public static boolean sendEmail(String addressTo, String addressCc, String addressBcc, String subject, Map<String, String> paramMap){
        try {
            Message message = ConfigureMessage.message(addressTo, addressCc, addressBcc, subject);
            return ConfigureTemplate.template(message, SendEmail.class, "confirmRegistrationTemplate.ftl", paramMap);
        }catch (Exception e){
            return false;
        }
    }

    public static boolean sendPurchaseEmail(String addressTo, String addressCc, String addressBcc, String subject, Map<?, ?> paramMap){
        try {
            Message message = ConfigureMessage.message(addressTo, addressCc, addressBcc, subject);
            return ConfigureTemplate.template(message, SendEmail.class, "purchaseTemplate.ftl", paramMap);
        }catch (Exception e){
            return false;
        }
    }
}

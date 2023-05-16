package com.example.api_updated;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class SendSMS {
    private static final String ACCOUNT_SID = "SID";
//    ACaae5abda9a0b61d9bc88a9a2c3130fdc
    private static final String AUTH_TOKEN = "TOKEN";
//    cfad8bf984319e8c4f1df3ea3bb155fc
    public static String sendSms(String phoneNumber, String message){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message1 = Message.creator(
                new com.twilio.type.PhoneNumber(phoneNumber),
                new com.twilio.type.PhoneNumber("+###"),
                message).create();

        return message1.getSid();
    }
}

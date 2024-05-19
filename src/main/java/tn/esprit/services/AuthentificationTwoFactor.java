package tn.esprit.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


public class AuthentificationTwoFactor {

    public static final String ACCOUNT_SID = "ACe75f498f75719e106a4c2b0c40a2ff90";
    public static final String AUTH_TOKEN = "dacdda16dafb5f87f5a14e7afe5ff9d8";

    public static void TwoFactorSendVerification(int number, int code) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Recipient phone number
        String toPhoneNumber = "+216" + number;

        // Sender phone number (Twilio number)
        String fromPhoneNumber = "+13156057964";

        // SMS message content
        String message = "Your TrocTn verification code is: " + code;

        // Send the SMS message
        Message twilioMessage = Message.creator(
                new PhoneNumber(toPhoneNumber),
                new PhoneNumber(fromPhoneNumber),
                message).create();

        // Print the message SID (identifier) and status
        System.out.println("Message SID: " + twilioMessage.getSid());
        System.out.println("Message status: " + twilioMessage.getStatus());
    }
}


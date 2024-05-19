package tn.esprit.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioService {
    private final String accountSid;
    private final String authToken;
    private final String twilioPhoneNumber;

    public TwilioService(String accountSid, String authToken, String twilioPhoneNumber) {
        this.accountSid = "AC867a348117ea1fba09c5d3b2f4fcafde";
        this.authToken = "ef09d44e7999b6c9a43d231049d2ca28";
        this.twilioPhoneNumber = "+18289002810";
        Twilio.init(accountSid, authToken);
    }

    public void sendSms(String toPhoneNumber, String message) {
        Message.creator(
                        new PhoneNumber(toPhoneNumber),
                        new PhoneNumber(twilioPhoneNumber),
                        message)
                .create();
    }
}

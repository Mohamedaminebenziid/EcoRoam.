package tn.esprit.services;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.io.IOException;
import java.util.Properties;

public class Activationemail {


        private static final String SENDGRID_API_KEY = "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";

        // method to send activation email to user
        public static void sendActivationEmail(String email, String activationCode) {

            String smtpUsername = "apikey"; // Replace with your SendGrid SMTP username
            String smtpPassword = "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"; // Replace with your SendGrid SMTP password

            // Set up SMTP properties
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.sendgrid.net");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");

            // Set up SMTP session with authentication
            Authenticator auth = new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(smtpUsername, smtpPassword);
                }
            };
            Session session = Session.getInstance(props, auth);
            SendGrid sg = new SendGrid("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");
            Mail mail = new Mail();
            mail.setFrom(new Email("elbennanawrasse@gmail.com"));
            mail.setSubject("Activate Your Account");
            Personalization personalization = new Personalization();
            personalization.addTo(new Email(email));
            mail.addPersonalization(personalization);
            mail.addContent(new Content("text/plain", "Your code to activate your account: " + activationCode));
            try {
                HttpClient http = HttpClientBuilder.create().build();
                HttpPost post = new HttpPost("https://api.sendgrid.com/v3/mail/send");
                StringEntity entity = new StringEntity(mail.build());
                post.setEntity(entity);
                post.setHeader("Authorization", "Bearer " + "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");
                post.setHeader("Content-Type", "application/json");
                HttpResponse response = http.execute(post);
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 202) {
                    System.out.println("Activation email sent successfully.");
                } else {
                    System.out.println("Activation email failed to send.");
                }
            } catch (IOException ex) {
                System.err.println("Error sending activation email: " + ex.getMessage());
            }

        }

    }


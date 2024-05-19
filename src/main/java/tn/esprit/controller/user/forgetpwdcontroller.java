package tn.esprit.controller.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;

public class forgetpwdcontroller {

    @FXML
    private TextField emailId;

    @FXML
    private void resetPass(ActionEvent event) {
        if (!emailId.getText().isEmpty()) {
            if (!emailId.getText().contains("@")) {
                emailId.setStyle("-fx-border-color: #009213;");
                Alert email = new Alert(Alert.AlertType.ERROR, "Email doesn't contain @", ButtonType.CLOSE);
                email.showAndWait();
            } else {
                String newPassword = generatePassword(8);
                sendEmail(emailId.getText(), newPassword);
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Password sent to your email", ButtonType.OK);
                Optional<ButtonType> result = a.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    returnSignIn(event);
                }
            }
        } else {
            Alert name = new Alert(Alert.AlertType.ERROR, "Field is empty", ButtonType.CLOSE);
            name.showAndWait();
            emailId.setStyle("-fx-border-color: #f1e5e5;");
        }
    }

    @FXML
    private void returnSignIn(ActionEvent event) {
        Stage stage = (Stage) emailId.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("tn.esprit/ressources/login.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String generatePassword(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder password = new StringBuilder();
        Random rnd = new Random();
        while (password.length() < length) {
            int index = (int) (rnd.nextFloat() * chars.length());
            password.append(chars.charAt(index));
        }
        return password.toString();
    }

    private void sendEmail(String recipientEmail, String newPassword) {
        String host = "localhost"; // Remplacez-le par le serveur SMTP approprié
        String port = "1025"; // Remplacez-le par le port SMTP approprié
        String username = "votre_adresse_email";
        String password = "votre_mot_de_passe";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("New Password");
            message.setText("Your new password is: " + newPassword);
            Transport.send(message);
            System.out.println("Email sent successfully to " + recipientEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

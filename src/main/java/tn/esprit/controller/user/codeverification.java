package tn.esprit.controller.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

public class codeverification {

    @FXML
    private TextField useremail;

    @FXML
    private TextField code;

    private String verificationCode;

    @FXML
    void send(ActionEvent event) {
        // Récupérer l'e-mail de l'utilisateur à partir du champ de texte
        String recipientEmail = useremail.getText();

        // Vérifier si l'adresse e-mail est valide
        if (isValidEmail(recipientEmail)) {
            // Envoyer le code de vérification
            verificationCode = sendVerificationCode(recipientEmail);
            System.out.println("Code de vérification envoyé avec succès à " + recipientEmail + ". Le code est : " + verificationCode);
        } else {
            // Afficher un message d'erreur si l'adresse e-mail n'est pas valide
            System.out.println("Adresse e-mail invalide.");
        }
    }

    @FXML
    void verify(ActionEvent event) {
        // Récupérer le code saisi par l'utilisateur
        String enteredCode = code.getText();

        // Vérifier si le code saisi correspond au code de vérification
        if (enteredCode.equals(verificationCode)) {
            // Le code est correct, afficher une alerte de succès
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Le code de vérification est correct.");
        } else {
            // Le code est incorrect, afficher une alerte d'échec
            showAlert(Alert.AlertType.ERROR, "Échec", "Le code de vérification est incorrect.");
        }
    }

    private String sendVerificationCode(String recipientEmail) {
        // Générer un code de vérification aléatoire
        String verificationCode = generateVerificationCode();

        // Configuration des propriétés de l'e-mail
        Properties props = new Properties();
        props.put("mail.smtp.auth", "false");
        props.put("mail.smtp.starttls.enable", "false");
        props.put("mail.smtp.host", "localhost");
        props.put("mail.smtp.port", "1025");

        Session session = Session.getInstance(props);

        try {
            // Création du message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("elbennanawrasse@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Code de vérification");
            message.setText("Votre code de vérification est : " + verificationCode);

            // Envoi du message
            Transport.send(message);

            // Retourner le code de vérification
            return verificationCode;
        } catch (MessagingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    private boolean isValidEmail(String email) {
        return email.matches("[a-zA-Z0-9._%+-]+@(?:gmail\\.com|esprit\\.tn)");
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void back(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/menu.fxml"));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to go back to menu: " + e.getMessage());
        }
    }
}


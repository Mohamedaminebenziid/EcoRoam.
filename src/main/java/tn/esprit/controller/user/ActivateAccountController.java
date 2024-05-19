package tn.esprit.controller.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import tn.esprit.models.user;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class ActivateAccountController implements Initializable {

    @FXML
    private TextField codeId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Code d'initialisation si nécessaire
    }

    @FXML
    private void activateAcc(ActionEvent event) {
        if (!codeId.getText().isEmpty()) {
            // Générer un code d'activation (ici, une chaîne aléatoire)
            String activationCode = generateActivationCode();

            // Envoyer le code d'activation par e-mail
            sendActivationCodeByEmail("recipient@example.com", activationCode);

            // Votre code de traitement pour activer le compte ici...
        } else {
            Alert b = new Alert(Alert.AlertType.ERROR, "Field is empty", ButtonType.CLOSE);
            b.showAndWait();
        }
    }

    private String generateActivationCode() {
        // Implémentation de la génération du code d'activation (par exemple, utilisation de librairies de génération aléatoire)
        return "123456"; // Exemple de code d'activation généré
    }

    private void sendActivationCodeByEmail(String recipientEmail, String activationCode) {
        // Configuration des paramètres de serveur SMTP pour MailHog
        Properties props = new Properties();
        props.put("mail.smtp.host", "localhost"); // Adresse de votre serveur MailHog
        props.put("mail.smtp.port", "1025"); // Port par défaut de MailHog
        props.put("mail.smtp.auth", "false"); // Pas besoin d'authentification pour MailHog

        // Création d'une session JavaMail
        Session session = Session.getInstance(props);

        try {
            // Création d'un objet Message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("elbennanawrasse@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail)); // Adresse du destinataire
            message.setSubject("Activation Code");
            message.setText("Your activation code is: " + activationCode);

            // Envoi du message
            Transport.send(message);

            System.out.println("Activation code sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleButtonClick(ActionEvent actionEvent) {
        // Code à exécuter lorsque le bouton est cliqué
        System.out.println("Button clicked!");
    }
}



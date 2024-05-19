package tn.esprit.controller.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import tn.esprit.models.user;
import tn.esprit.services.UserCrud;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.sql.Connection;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class TwoFactorAuthentificationController implements Initializable {

    @FXML
    private TextField codeId;
    private user P = null;
    private Connection connection;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO: Initialisation du contrôleur
    }

    @FXML
    private void returnSignIn(ActionEvent event) {
        NavigationController.changeLoginPage(event, "LoginPage.fxml");
    }

    @FXML
    private void verifCodeUser(ActionEvent event) {
        Preferences prefs = Preferences.userNodeForPackage(TwoFactorAuthentificationController.class);
        try {
            if (!(codeId.getText().isEmpty())) {
                if (!codeId.getText().matches("\\d{6}")) {
                    codeId.setStyle("-fx-border-color: #009213;");
                    Alert b = new Alert(Alert.AlertType.ERROR, "Only 6 digits are allowed", ButtonType.CLOSE);
                    b.showAndWait();
                } else {
                    int verificationCode = Integer.parseInt(codeId.getText());
                    UserCrud userCrud = new UserCrud(connection);
                    if (UserCrud.verifCodeAuth(verificationCode, P.getId())) {
                        prefs.put("iduser", Integer.toString(P.getId()));
                        UserCrud userService = new UserCrud(connection);
                        userService.setuser(P);
                        NavigationController.changeFeedPage(event, P, "login.fxml");
                    } else {
                        Alert b = new Alert(Alert.AlertType.ERROR, "Incorrect code", ButtonType.CLOSE);
                        b.showAndWait();
                    }
                }
            } else {
                Alert name = new Alert(Alert.AlertType.ERROR, "Field is empty", ButtonType.CLOSE);
                name.showAndWait();
                codeId.setStyle("-fx-border-color: #009213;");
            }
        } catch (NumberFormatException e) {
            Alert b = new Alert(Alert.AlertType.ERROR, "Please enter only numbers in the field", ButtonType.CLOSE);
            b.showAndWait();
        }
    }

    public void setInformation(user userObject) {
        P = userObject;
    }

    // Méthode pour envoyer un email
    private void sendEmail() throws MessagingException {
        sendEmail(null, null, null);
    }

    // Méthode pour envoyer un email
    private void sendEmail(String recipientEmail, String subject, String body) throws MessagingException {
        String host = "localhost"; // L'adresse de votre serveur MailHog
        int port = 1025; // Le port SMTP de votre serveur MailHog

        // Propriétés du serveur SMTP
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // Création de la session SMTP
        Session session = Session.getInstance(props);

        try {
            // Création du message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("elbennanawrasse@gmail.com")); // Votre adresse email
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(body);

            // Envoi du message
            Transport.send(message);

            System.out.println("Email sent successfully !");
        } catch (MessagingException e) {
            System.out.println("Error sending email: " + e.getMessage());
            throw e;
        }
    }
}



package tn.esprit.controller.user;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EnvoyerEmail {
    public void envoyer(String recipientEmail, String userName, String phoneNumber) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "false"); // Pas besoin d'authentification pour MailHog
        props.put("mail.smtp.starttls.enable", "false");
        props.put("mail.smtp.host", "localhost"); // Serveur SMTP de MailHog
        props.put("mail.smtp.port", "1025"); // Port par défaut de MailHog

        Session session = Session.getInstance(props);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("elbennanawrasse@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(" email pour la verification ");

            // Personnalisation du contenu du message avec le nom et le numéro de téléphone
            String messageContent = "Bonjour " + userName + ",\n\n" +
                    "Merci de votre inscription. Voici vos informations :\n" +
                    "Nom : " + userName + "\n" +
                    "Numéro de téléphone : " + phoneNumber + "\n\n" +
                    "Cordialement,\n" +
                    "Votre application";

            message.setText(messageContent);

            Transport.send(message);
            System.out.println("Message envoyé à " + recipientEmail);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        // Vérifier les arguments de la ligne de commande
        if (args.length < 3) {
            System.out.println("Usage: java EnvoyerEmail <recipientEmail> <userName> <phoneNumber>");
            return;
        }

        // Récupérer les arguments de la ligne de commande
        String recipientEmail = args[0];
        String userName = args[1];
        String phoneNumber = args[2];

        // Créer une instance d'EnvoyerEmail et appeler la méthode envoyer
        EnvoyerEmail envoyeur = new EnvoyerEmail();
        envoyeur.envoyer(recipientEmail, userName, phoneNumber);
    }
}

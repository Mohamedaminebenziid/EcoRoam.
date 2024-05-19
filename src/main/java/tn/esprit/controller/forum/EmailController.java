package tn.esprit.controller.forum;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tn.esprit.models.Post;
import tn.esprit.services.ServicePost;


import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailController {

    @FXML
    private TextField txtMsgArea;

    @FXML
    private Button btnSend;
    ServicePost servicePost=new ServicePost();

    @FXML
    void SendEmail(ActionEvent event) throws MessagingException {
        String recipientEmail = txtMsgArea.getText();
        sendEmail(recipientEmail);


    }
    private void sendEmail(String recipientEmail) throws MessagingException {
        Properties  properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");
        String myAccountEmail = "islemcherni2019@gmail.com\n";
        String password = "gtal mcyh ygdq dhbj";
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });
        Post p=servicePost.getPostById(TemplatePost.idShare);
        String msg="Post: "+p.getDescriptionP()+"\n HashTag: "+p.getHashtagP();
        if (msg!=null){
            new Alert(Alert.AlertType.INFORMATION,"send Email Successfully").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"please try Again").show();
        }
        try{
            Message message=new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(txtMsgArea.getText()));
            message.setSubject("Post share");
            Multipart multipart=new MimeMultipart();
            BodyPart messageBodyPart=new MimeBodyPart();
            messageBodyPart.setText(msg);
            multipart.addBodyPart(messageBodyPart);
            messageBodyPart=new MimeBodyPart();
            DataSource source=new FileDataSource("D:\\EcoroamJAVA\\src\\main\\resources\\forum\\img\\"+p.getImageP());
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(new File("D:\\EcoroamJAVA\\src\\main\\resources\\forum\\img\\"+p.getImageP()).getName());
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            Transport.send(message);
        }catch (MessagingException ex){
            System.out.println(ex.getMessage());
        }





    }

    private Message prepareMessage(Session session, String myAccountEmail, String recipientEmail, String msg) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject("Messages");
            message.setText(msg);
            return message;
        } catch (Exception e) {
            Logger.getLogger(EmailController.class.getName()).log(Level.SEVERE, "Error preparing message", e);
            return null;
        }
    }
}

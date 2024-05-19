package tn.esprit.controller.certificate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import tn.esprit.models.Certificate;

import java.io.IOException;
import java.io.InputStream;

public class Certificatedetails {

    @FXML
    private Label courseTitle;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label participantName;

    @FXML
    private Label signature;

    @FXML
    void printpdf(ActionEvent event) {
        createPDF();

    }



    private Certificate certificate;
    @FXML
    public void initdata(Certificate certificate) {
        this.certificate = certificate;

        participantName.setText(certificate.getP_name());
        courseTitle.setText(certificate.getC_title());
        //certificateDate.setText(certificate.getDate().toString());
        signature.setText(certificate.getSignature());

        // Generate reservation details string
       /* String reservationDetails = "Destination: " + reservation.getDestination().getName() + "\n" +
                "Start Date: " + reservation.getStartDate() + "\n" +
                "End Date: " + reservation.getEndDate() + "\n" +
                "Number of Days: " + numberOfDays + "\n" +
                "Total Price: " + totalPrice + " DT";*/


    }
    private void createPDF() {
        try {
            // Create a PDF document
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            // Create a PDPageContentStream
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Load the font file
            InputStream fontStream = getClass().getResourceAsStream("/JetBrainsMono-VariableFont_wght.ttf");
            if (fontStream == null) {
                throw new IOException("Font file not found");
            }

            PDType0Font font = PDType0Font.load(document, fontStream);

            // Set the font for the content stream
            contentStream.setFont(font, 12);

            float yPosition = 700; // Initial vertical position

            // Add certificate details to the PDF
            contentStream.beginText();
            contentStream.newLineAtOffset(100, yPosition);
            contentStream.showText("Participant Name: " + participantName.getText());
            contentStream.newLine();
            contentStream.endText();
            yPosition -= 20; // Adjust vertical position

            contentStream.beginText();
            contentStream.newLineAtOffset(100, yPosition);
            contentStream.showText("Course Title: " + courseTitle.getText());
            contentStream.newLine();
            contentStream.endText();
            yPosition -= 20;

            contentStream.beginText();
            contentStream.newLineAtOffset(100, yPosition);
            contentStream.showText("Certificate Date: " + datePicker.getValue());
            contentStream.newLine();
            contentStream.endText();
            yPosition -= 20;

            contentStream.beginText();
            contentStream.newLineAtOffset(100, yPosition);
            contentStream.showText("Signature: " + signature.getText());
            contentStream.newLine();
            contentStream.endText();
            yPosition -= 20;

            contentStream.close();

            // Save the PDF
            String desktopPath = System.getProperty("user.home") + "/Desktop/";
            String fileName = "certificate_details.pdf";
            String filePath = desktopPath + fileName;
            document.save(filePath);

            System.out.println("PDF created successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

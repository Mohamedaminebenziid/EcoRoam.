package tn.esprit.controller.reservation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import tn.esprit.models.Reservation;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import java.awt.*;
import java.io.InputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import javax.imageio.ImageIO;
import java.util.HashMap;
import java.util.Map;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ReservationDetails {

    @FXML
    private Label destination;

    @FXML
    private DatePicker enddate;

    @FXML
    private Label numofdays;

    @FXML
    private DatePicker startdate;

    @FXML
    private Label totprice;
    @FXML
    private ImageView qrCodeImageView;

    @FXML
    void printpdf(ActionEvent event) {
        createPDF();

    }

    @FXML
    public void initdata(Reservation reservation) {
        this.reservation = reservation;

        destination.setText(reservation.getDestination().getName());
        startdate.setValue(reservation.getStartDate());
        enddate.setValue(reservation.getEndDate());

        long numberOfDays = java.time.temporal.ChronoUnit.DAYS.between(reservation.getStartDate(), reservation.getEndDate());
        numofdays.setText(String.valueOf(numberOfDays));

        double totalPrice = reservation.getDestination().getPrice() * numberOfDays;
        totprice.setText(String.valueOf(totalPrice) + " DT");

        // Generate reservation details string
        String reservationDetails = "Destination: " + reservation.getDestination().getName() + "\n" +
                "Start Date: " + reservation.getStartDate() + "\n" +
                "End Date: " + reservation.getEndDate() + "\n" +
                "Number of Days: " + numberOfDays + "\n" +
                "Total Price: " + totalPrice + " DT";

        // Generate QR code
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

            BitMatrix bitMatrix = qrCodeWriter.encode(reservationDetails, BarcodeFormat.QR_CODE, 200, 200, hints);

            // Convert BitMatrix to buffered image
            BufferedImage bufferedImage = new BufferedImage(bitMatrix.getWidth(), bitMatrix.getHeight(), BufferedImage.TYPE_INT_RGB);
            bufferedImage.createGraphics();
            Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, bitMatrix.getWidth(), bitMatrix.getHeight());
            graphics.setColor(Color.BLACK);

            for (int i = 0; i < bitMatrix.getWidth(); i++) {
                for (int j = 0; j < bitMatrix.getHeight(); j++) {
                    if (bitMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }

            // Convert buffered image to byte array
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);

            // Convert byte array to JavaFX Image
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            Image qrCodeImage = new Image(byteArrayInputStream);

            // Display QR code in ImageView
            qrCodeImageView.setImage(qrCodeImage);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }
    private Reservation reservation;


    private void createPDF() {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

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

            // Add reservation details to the PDF
            contentStream.beginText();
            contentStream.newLineAtOffset(100, yPosition);
            contentStream.showText("Destination: " + destination.getText());
            contentStream.newLine();
            contentStream.endText();
            yPosition -= 20; // Adjust vertical position

            contentStream.beginText();
            contentStream.newLineAtOffset(100, yPosition);
            contentStream.showText("Start Date: " + startdate.getValue());
            contentStream.newLine();
            contentStream.endText();
            yPosition -= 20;

            contentStream.beginText();
            contentStream.newLineAtOffset(100, yPosition);
            contentStream.showText("End Date: " + enddate.getValue());
            contentStream.newLine();
            contentStream.endText();
            yPosition -= 20;

            contentStream.beginText();
            contentStream.newLineAtOffset(100, yPosition);
            contentStream.showText("Number of Days: " + numofdays.getText());
            contentStream.newLine();
            contentStream.endText();
            yPosition -= 20;

            contentStream.beginText();
            contentStream.newLineAtOffset(100, yPosition);
            contentStream.showText("Total Price: " + totprice.getText());
            contentStream.newLine();
            contentStream.endText();

            contentStream.close();

            // Save the PDF
            String desktopPath = System.getProperty("user.home") + "/Desktop/";
            String fileName = "reservation_details.pdf";
            String filePath = desktopPath + fileName;
            document.save(filePath);

            System.out.println("PDF created successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}

package tn.esprit.controller.course;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import tn.esprit.models.Certificate;
import tn.esprit.services.CertificateService;

import java.time.LocalDate;
//import java.util.Date;
import java.sql.Date;

public class CertificateAdd {
    @FXML
    private TextField courseTitleField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField personNameField;

    @FXML
    private TextField signatureField;

    private final CertificateService certificateService = new CertificateService();

    @FXML
    void certificateaddonclick(ActionEvent event) {
        try {
            String personName = personNameField.getText();
            String signature = signatureField.getText();
            String courseTitle = courseTitleField.getText();
            LocalDate localDate = datePicker.getValue();
            Date date = (localDate != null) ? Date.valueOf(localDate) : null;

            Certificate certificate = new Certificate(personName, courseTitle, date, signature, null);
            certificateService.add(certificate);

            // Optionally, you can close the window after adding the certificate
            // Stage stage = (Stage) courseTitleField.getScene().getWindow();
            // stage.close();

        } catch (Exception e) {
            e.printStackTrace();
            // Handle error
        }
    }


}

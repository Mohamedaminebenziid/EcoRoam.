package tn.esprit.controller.certificate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Certificate;
import tn.esprit.services.CertificateService;
public class Certificateadd {
    @FXML
    private TextField dateTF;

    @FXML
    private TextField nameTF;

    @FXML
    private TextField signatureTF;

    @FXML
    private TextField titleTF;

    @FXML
    void certificateaddevent(ActionEvent event) {

    }

    @FXML
    void navigatetocertificatetable(ActionEvent event) {

    }
}

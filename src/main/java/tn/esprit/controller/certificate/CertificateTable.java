package tn.esprit.controller.certificate;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.esprit.models.Certificate;
import tn.esprit.services.CertificateService;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class CertificateTable {

    @FXML
    private TableColumn<Certificate, Integer> certificateid;

    @FXML
    private TableColumn<Certificate, Date> datecolnn;

    @FXML
    private TableColumn<Certificate, String> namecolnn;

    @FXML
    private TableColumn<Certificate, String> signaturecolnn;

    @FXML
    private TableColumn<Certificate, String> titlecolnn;

    @FXML
    private TableColumn<Certificate, Integer> courseidTF;

    @FXML
    private TableView<Certificate> certificatetableview;

    @FXML
    void addcertificate(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CertificateAdd.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    private final CertificateService certificateService = new CertificateService();

    @FXML
    void initialize() {
        try {
            List<Certificate> certificates = certificateService.getAllCertificate();
            ObservableList<Certificate> observableList = FXCollections.observableList(certificates);
            certificatetableview.setItems(observableList);
            certificateid.setCellValueFactory(new PropertyValueFactory<>("id"));
            namecolnn.setCellValueFactory(new PropertyValueFactory<>("p_name"));
            datecolnn.setCellValueFactory(new PropertyValueFactory<>("date"));
            signaturecolnn.setCellValueFactory(new PropertyValueFactory<>("signature"));

            // Utilisation d'une cell factory personnalisée pour afficher le titre du cours
            // Utilisation d'une PropertyValueFactory pour afficher le titre du cours
            titlecolnn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Certificate, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Certificate, String> data) {
                    Certificate certificate = data.getValue();
                    return new SimpleStringProperty(certificate.getCourse().getTitle());
                }
            });



        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }



}


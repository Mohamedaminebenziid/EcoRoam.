package tn.esprit.controller.destination;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Destination;
import tn.esprit.services.DestinationService;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

import java.io.IOException;
import java.util.List;

public class Destinationtable {




        @FXML
        private TableColumn<Destination,String > destadresscol;
        @FXML
        private TableColumn<?, ?> destidcol;
        @FXML
        private TableColumn<Destination, String> destnamecol;

        @FXML
        private TableColumn<Destination, Float> destpricecol;

        @FXML
        private TableColumn<Destination, String> deststatecol;

        @FXML
        private TableView<Destination> desttableveiw;


        @FXML
        void gotoaddpage(ActionEvent event) {
                try {
                        // Load the FXML file for the update destination page
                        Parent root = FXMLLoader.load(getClass().getResource("/backoffice/destinationadd.fxml"));

                        // Get the current stage from the ActionEvent's source
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                        // Create a new scene with the loaded FXML root and set it on the stage
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                } catch (IOException e) {
                        throw new RuntimeException(e);
                }

        }
        private final DestinationService dest =new DestinationService() ;
        @FXML
        void gotoupdatepage(ActionEvent event) {
                try {
                        // Load the FXML file for the update destination page
                        Parent root = FXMLLoader.load(getClass().getResource("/backoffice/updatedestination.fxml"));

                        // Get the current stage from the ActionEvent's source
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                        // Create a new scene with the loaded FXML root and set it on the stage
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                } catch (IOException e) {
                        throw new RuntimeException(e);
                }
        }

        @FXML
        void initialize() {
                try {
                        List<Destination> destinations = dest.getallDestination(); // Assuming this method name is corrected
                        ObservableList<Destination> observableList = FXCollections.observableList(destinations);

                        desttableveiw.setItems(observableList); // Corrected variable name
                        destidcol.setCellValueFactory(new PropertyValueFactory<>("id"));
                        destnamecol.setCellValueFactory(new PropertyValueFactory<>("name"));
                        destadresscol.setCellValueFactory(new PropertyValueFactory<>("address"));
                        deststatecol.setCellValueFactory(new PropertyValueFactory<>("state"));
                        destpricecol.setCellValueFactory(new PropertyValueFactory<>("prix"));
                } catch (IllegalArgumentException e) {
                        showErrorAlert("Error", e.getMessage());
                } catch (Exception e) {
                        // Handle any other unexpected exceptions
                        showErrorAlert("Error", "An unexpected error occurred.");
                        e.printStackTrace(); // Print stack trace for debugging
                }
        }

        private void showErrorAlert(String title, String message) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(title);
                alert.setContentText(message);
                alert.showAndWait();
        }



}

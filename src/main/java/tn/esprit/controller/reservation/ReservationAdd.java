package tn.esprit.controller.reservation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.controller.destination.DestinationDetails;
import tn.esprit.controller.destination.destinationcard;
import tn.esprit.models.Destination;
import tn.esprit.models.Reservation;
import tn.esprit.services.DestinationService;
import tn.esprit.services.ReservationService;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ReservationAdd {
    private Destination destination;
    @FXML
    private DatePicker enddatefield;

    @FXML
    private DatePicker startdatefield;

    public void setDestination(Destination destination) {
        this.destination = destination;
    }


    @FXML
    void reservationaddonclick(ActionEvent event) throws IOException {
        if (destination != null) {
            LocalDate startDate = startdatefield.getValue();
            LocalDate endDate = enddatefield.getValue();

            if (startDate != null && endDate != null && startDate.isBefore(endDate)) {
                long numberOfDays = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate);
                double totalPrice = destination.getPrice() * numberOfDays;

                // Afficher une boîte de dialogue de confirmation avec le prix total
                Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirmation de réservation");
                confirmationAlert.setHeaderText("Prix total de la réservation : " + totalPrice + " DT");
                confirmationAlert.setContentText("Confirmez-vous l'ajout de la réservation ?");

                // Option pour confirmer ou annuler
                ButtonType confirmButtonType = new ButtonType("Confirmer", ButtonBar.ButtonData.OK_DONE);
                confirmationAlert.getButtonTypes().setAll(confirmButtonType, ButtonType.CANCEL);

                // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
                Optional<ButtonType> result = confirmationAlert.showAndWait();
                if (result.isPresent() && result.get() == confirmButtonType) {
                    // L'utilisateur a confirmé, ajouter la réservation
                    Reservation reservation = new Reservation();
                    reservation.setDestination(destination);
                    reservation.setStartDate(startDate);
                    reservation.setEndDate(endDate);
                    reservation.setTotalPrice(totalPrice);
                    reservation.setNumberOfDays((int) numberOfDays);

                    ReservationService reservationService = new ReservationService();
                    boolean success = reservationService.addReservation(reservation);
                    // Load the FXML file for the reservation detail page

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontoffice/Reservation/reservationdetails.fxml"));
                    // Load the FXML file for the reservation detail page
                    Parent root = loader.load();

                    // Create a new Stage for the reservation detail page
                    Stage detailStage = new Stage();
                    detailStage.setScene(new Scene(root));

                    // Set the Reservation object **after** loading the FXML (assuming controller retrieves it from scene parameters)
                    detailStage.setUserData(reservation);
                    ReservationDetails controller = loader.getController();
                    controller.initdata(reservation);

                    // Show the detail stage
                    detailStage.show();

                    if (success) {
                        showAlert(AlertType.INFORMATION, "Réservation ajoutée avec succès !");
                    } else {
                        showAlert(AlertType.ERROR, "Erreur lors de l'ajout de la réservation.");
                    }
                } else {
                    // L'utilisateur a annulé
                    showAlert(AlertType.INFORMATION, "Ajout de la réservation annulé.");
                }
            } else {
                showAlert(AlertType.WARNING, "Veuillez sélectionner des dates valides.");
            }
        } else {
            showAlert(AlertType.ERROR, "La destination n'est pas définie.");
        }
    }


    public void showAlert(AlertType alertType, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    @FXML
    private void initialize() {
        // Load destination data
    }
}


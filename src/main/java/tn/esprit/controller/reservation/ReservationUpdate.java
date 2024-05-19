package tn.esprit.controller.reservation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Destination;
import tn.esprit.models.Reservation;
import tn.esprit.services.DestinationService;
import tn.esprit.services.ReservationService;

import java.io.IOException;
import java.time.LocalDate;

public class ReservationUpdate {

    @FXML
    private DatePicker enddatepick;

    @FXML
    private TextField idr;

    @FXML
    private Label imageURLLabel;

    @FXML
    private DatePicker startdatepick;



    @FXML
    private Label uperror;

    @FXML
    void findresbyid(ActionEvent event) {

            try {
                String sid = idr.getText();
                int reservationId = Integer.parseInt(sid);

                // Create an instance of ReservationService
                ReservationService reservationService = new ReservationService();

                // Call getReservationById to retrieve the reservation by ID
                Reservation reservation = reservationService.getOneReservation(reservationId);

                if (reservation == null) {
                    // Reservation with the given ID doesn't exist in the database
                    showErrorAlert("Error", "Reservation not found with ID " + reservationId);
                } else {
                    // Populate the UI elements with reservation details
                    startdatepick.setValue(reservation.getStartDate());
                    enddatepick.setValue(reservation.getEndDate());
                    // Assuming other fields are to be populated similarly

                    // Optionally, you can display additional details or handle other UI elements
                }

            } catch (NumberFormatException e) {
                // Handle invalid ID input
                showErrorAlert("Error", "Invalid reservation ID. Please enter a valid number.");
            } catch (Exception e) {
                // Handle unexpected errors
                showErrorAlert("Error", "An unexpected error occurred.");
                e.printStackTrace(); // Print the stack trace for debugging purposes
            }
        }


    @FXML
    void resdel(ActionEvent event) {
        try {
            String sid = idr.getText();
            int reservationId = Integer.parseInt(sid);

            // Call deleteReservation method to delete the reservation
            ReservationService reservationService = new ReservationService();
            reservationService.deleteReservation(reservationId);

            // Show a success message
            showAlert("Success", "Reservation deleted successfully.");
        } catch (NumberFormatException e) {
            // Handle invalid reservation id format
            showErrorAlert("Error", "Invalid reservation ID format. Please enter a valid number.");
        } catch (Exception e) {
            // Handle other exceptions
            showErrorAlert("Error", "An unexpected error occurred.");
            e.printStackTrace();
        }
    }


    @FXML
    void resupd(ActionEvent event) {
        try {
            String sid = idr.getText();
            int reservationId = Integer.parseInt(sid);

            LocalDate startDate = startdatepick.getValue();
            LocalDate endDate = enddatepick.getValue();

            if (startDate != null && endDate != null && startDate.isBefore(endDate)) {
                long numberOfDays = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate);

                // Retrieve the destination object from your data source
                DestinationService destinationService = new DestinationService();
                ReservationService reservationService = new ReservationService();
                Reservation reservation = reservationService.getOneReservation(reservationId);
                Destination destination = reservation.getDestination();

                // Calculate total price using destination's price and number of days
                double totalPrice = destination.getPrice() * numberOfDays;
                reservation.setStartDate(startDate);
                reservation.setEndDate(endDate);
                reservation.setTotalPrice(totalPrice);
                reservation.setNumberOfDays((int) numberOfDays);

                // Update reservation
                boolean success = reservationService.updateReservation(reservation);
                if (success) {
                    showAlert("Success", "Reservation updated successfully.");
                } else {
                    showAlert("Error", "Error updating reservation.");
                }
            } else {
                showAlert("Warning", "Please select valid dates.");
            }
        } catch (NumberFormatException e) {
            // Handle invalid reservation id format
            showErrorAlert("Error", "Invalid reservation ID format. Please enter a valid number.");
        } catch (IllegalArgumentException e) {
            // Handle other validation errors
            showErrorAlert("Error", e.getMessage());
        }
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void toreservationslist(ActionEvent event) {
        try {
            // Load the FXML file for the update destination page
            Parent root = FXMLLoader.load(getClass().getResource("/backoffice/resrvationtable.fxml"));

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
    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null); // Remove header text
        alert.setContentText(content);
        alert.showAndWait();
    }


}
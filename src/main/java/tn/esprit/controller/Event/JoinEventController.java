package tn.esprit.controller.Event;

import tn.esprit.services.TwilioService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JoinEventController {
    private final TwilioService twilioService = new TwilioService("AC867a348117ea1fba09c5d3b2f4fcafde", "ef09d44e7999b6c9a43d231049d2ca28", "+18289002810");
    public Button addButton;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField cinTextField;

    private String eventName; // Field to store the event name

    // Method to set the event name
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    @FXML
    private void submitForm() {
        String phoneNumber = phoneNumberTextField.getText();
        String name = nameTextField.getText();
        String cin = cinTextField.getText();

        // Construct message with event name
        String message = String.format("Thank you, %s, for joining %s! Your ID card number is %s.", name, eventName, cin);

        // Send SMS
        twilioService.sendSms(phoneNumber, message);

        // Show confirmation message
        showAlert("Success", "Thank you! Your participation has been submitted successfully.");
    }
    @FXML
    private void addToOutlookCalendar() {
        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Format the event details
        String eventDetails = "Event Name: " + eventName + "\n" +
                "Date and Time: " + now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        // Open the default mail application with the event details
        try {
            // Create a mailto URI with the event details
            URI mailtoUri = new URI("mailto:?subject=New Event&body=" + eventDetails);

            // Open the default email application
            Desktop.getDesktop().mail(mailtoUri);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            // Show an error message if opening the email application fails
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to open email application.");
            alert.showAndWait();
        }

        // Close the Join Event window
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

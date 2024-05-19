package tn.esprit.controller.destination;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import tn.esprit.models.Destination;
import tn.esprit.services.DestinationService;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Updatedestination {

    @FXML
    private TextField idadressedestu;

    @FXML
    private TextField iddescdestu;

    @FXML
    private TextField idimgdestu;

    @FXML
    private TextField idnamedestu;

    @FXML
    private TextField idpricedestu;

    @FXML
    private TextField idr;

    @FXML
    private TextField idstatedestu;

    @FXML
    private Label uperror;

    @FXML
    private ImageView detailsimage;

    private File selectedImageFile;

    @FXML
    private Label imageURLLabel;

    @FXML
    void finddestbyid(ActionEvent event) {
        try {
            String sid = idr.getText();
            int id = Integer.parseInt(sid);

            // Create an instance of DestinationService
            DestinationService destinationService = new DestinationService();

            // Call getoneDestination to retrieve the destination by ID
            Destination dest = destinationService.getoneDestination(id);

            if (dest == null) {
                // Destination with the given ID doesn't exist in the database
                showErrorAlert("Error", "Destination not found with ID " + id);
            } else {
                // Populate the UI elements with destination details
                idadressedestu.setText(dest.getAddress());
                iddescdestu.setText(dest.getDescription());
                idstatedestu.setText(dest.getState());
                idnamedestu.setText(dest.getName());
                idpricedestu.setText(String.valueOf(dest.getPrice())); // Assuming getPrice returns a float

                // Set image if idimgdestu is meant to display an image
                setImage(detailsimage, dest.getImg());
            }

        } catch (NumberFormatException e) {
            // Handle invalid ID input
            showErrorAlert("Error", "Invalid destination ID. Please enter a valid number.");
        } catch (Exception e) {
            // Handle unexpected errors
            showErrorAlert("Error", "An unexpected error occurred.");
            e.printStackTrace(); // Print the stack trace for debugging purposes
        }
    }

    private void setImage(ImageView imageView, String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            // Load the image and set it to the ImageView
            Image image = new Image(imagePath);
            imageView.setImage(image);
        } else {
            // Clear the ImageView if no image path is provided
            imageView.setImage(null);
        }
    }

    // Utility method to show error alert
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void handleSelectImage(ActionEvent event) {
        // Open file chooser to select image
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        selectedImageFile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

        // Optionally, you can display the selected image or its path in your UI
        if (selectedImageFile != null) {
            // Display the selected image or its path in your UI (e.g., in a label or text field)
            imageURLLabel.setText(selectedImageFile.getAbsolutePath());
        }
    }
    @FXML
    void destinationupd(ActionEvent event) {
        try {
            String sid = idr.getText();
            int id = Integer.parseInt(sid);

            // Validate inputs
            String name = idnamedestu.getText();
            String state = idstatedestu.getText();
            String address = idadressedestu.getText();
            String priceText = idpricedestu.getText();
            String description = iddescdestu.getText();

            // Check if any of the required fields are empty
            if (name.isEmpty() || state.isEmpty() || address.isEmpty() || priceText.isEmpty() || description.isEmpty()) {
                throw new IllegalArgumentException("Please fill in all fields.");
            }

            // Parse price
            float price = Float.parseFloat(priceText);

            // Check if an image file is selected
            if (selectedImageFile == null) {
                throw new IllegalArgumentException("Please select an image.");
            }

            // Get image URL or path
            String imageUrlOrPath = selectedImageFile.getAbsolutePath();

            // Update destination using DestinationService
            DestinationService destinationService = new DestinationService();
            destinationService.updateDestination(new Destination(id, name, imageUrlOrPath, state, address, price, description));

            // Optionally, show success message
            showAlert("Success", "Destination updated successfully.");

        } catch (NumberFormatException e) {
            // Handle invalid price format
            showErrorAlert("Error", "Invalid price format. Please enter a valid number.");
        } catch (IllegalArgumentException e) {
            // Display error message if validation fails or no image is selected
            showErrorAlert("Error", e.getMessage());
        }
    }
    @FXML
    void destinationdel(ActionEvent event) {
        try {
            String sid = idr.getText();
            int id = Integer.parseInt(sid);

            // Create an instance of DestinationService
            DestinationService destinationService = new DestinationService();

            // Call getoneDestination to retrieve the destination by ID
            Destination dest = destinationService.getoneDestination(id);

            if (dest == null) {
                // Destination with the given ID doesn't exist in the database
                showAlert("Error", "Destination not found with ID " + id);
            } else {
                // Check if the destination is set for any reservation
                if (destinationService.isDestinationInReservation(dest.getId())) {
                    // Destination is set for a reservation, cannot be deleted
                    showAlert("Error", "Destination is set for a reservation and cannot be deleted.");
                } else {
                    // Confirm deletion
                    Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmAlert.setTitle("Confirmation");
                    confirmAlert.setHeaderText(null);
                    confirmAlert.setContentText("Are you sure you want to delete the destination?");
                    confirmAlert.showAndWait()
                            .filter(response -> response == ButtonType.OK)
                            .ifPresent(response -> {
                                // Delete the destination from the database (you need to implement this method)
                                destinationService.deleteDestination(dest.getId());

                                // Show success message
                                showAlert("Success", "Destination deleted successfully.");
                            });
                }
            }

        } catch (NumberFormatException e) {
            // Handle invalid ID input
            showAlert("Error", "Invalid input. Please enter a valid number.");
        } catch (IllegalArgumentException e) {
            // Handle invalid destination ID or other exceptions
            showAlert("Error", e.getMessage());
        }
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void todestinationlist(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/backoffice/destinationtable.fxml"));
            iddescdestu.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

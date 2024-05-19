package tn.esprit.controller.destination;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Destination;
import tn.esprit.services.DestinationService;

import java.net.URL;
import java.nio.file.Files;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class Destinationadd implements Initializable {

    @FXML
    private TextField idadressedest;

    @FXML
    private TextField idimgdest;
    @FXML
    private ComboBox<String> idstatedest;


    @FXML
    private TextField idnamedest;
    @FXML
    private TextField iddescdest;
    @FXML
    private TextField idpricedest;

    private final DestinationService dest = new DestinationService();

    private File selectedImageFile; // To store the selected image file

    @FXML
    private Label imageURLLabel;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idstatedest.getItems().addAll(
                "Ariana",
                "Beja",
                "Ben Arous",
                "Bizerte",
                "Gabes",
                "Gafsa",
                "Jendouba",
                "Kairouan",
                "Kasserine",
                "Kebili",
                "Kef",
                "Mahdia",
                "Manouba",
                "Medenine",
                "Monastir",
                "Nabeul",
                "Sfax",
                "Sidi Bouzid",
                "Siliana",
                "Sousse",
                "Tataouine",
                "Tozeur",
                "Tunis",
                "Zaghouan"
                // Add more options as needed
        );
    }
    private String imageURL = "";

    @FXML
    void handleSelectImage(ActionEvent event) {
        // Open file chooser to select image
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        selectedImageFile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

        // Update the label text with the image file path
        if (selectedImageFile != null) {
            imageURLLabel.setText(selectedImageFile.getAbsolutePath());
            // Set the selected image file path as imageURL
            imageURL = selectedImageFile.getAbsolutePath();
        } else {
            // Clear the label text if no image file is selected
            imageURLLabel.setText("");
            imageURL = ""; // Clear imageURL
        }
    }

    @FXML
    void destinationadd(ActionEvent event) {
        try {
            // Validate inputs
            String name = idnamedest.getText();
            String state = idstatedest.getValue(); // Corrected line
            String address = idadressedest.getText();
            String priceText = idpricedest.getText();
            String description = iddescdest.getText();

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

            // Add destination using DestinationService
            DestinationService destinationService = new DestinationService();
            // Pass imageURL instead of imageData
            destinationService.addDestination(new Destination(name, imageURL, state, address, price, description));

            // Optionally, show success message
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setContentText("Destination added successfully.");
            successAlert.showAndWait();
            Files.readAllBytes(selectedImageFile.toPath());

        } catch (NumberFormatException e) {
            // Handle invalid price format
            showErrorAlert("Error", "Invalid price format. Please enter a valid number.");
        } catch (IllegalArgumentException e) {
            // Display error message if validation fails or no image is selected
            showErrorAlert("Error", e.getMessage());
        } catch (IOException e) {
            // Handle IOException when reading image file
            showErrorAlert("Error", "Error reading image file.");
        }
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }



    @FXML
    void todestinationlist(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/backoffice/destinationtable.fxml"));
            idadressedest.getScene().setRoot(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private Button SelectImage;


}

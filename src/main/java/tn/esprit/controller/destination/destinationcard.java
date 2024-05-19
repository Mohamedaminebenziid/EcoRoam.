package tn.esprit.controller.destination;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.models.Destination;
import javafx.scene.input.MouseEvent;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class destinationcard  {

    @FXML
    private AnchorPane card_form;

    @FXML
    private Label dest_name;

    @FXML
    private ImageView dest_photo;

    @FXML
    private Label dest_price;


    public void setDestination(Destination destination) {
        this.destination = destination;
        dest_name.setText(destination.getName());
        dest_price.setText(String.valueOf(destination.getPrice()));
        // Set destination details in the card components
        String imageURL = destination.getImg(); // Assuming getImg() returns a String representing the image URL or file path
        if (imageURL != null && !imageURL.isEmpty()) {
            try {
                // Convert file path to file URL
                File file = new File(imageURL);
                String fileURL = file.toURI().toURL().toString();

                // Load and set the image
                Image image = new Image(fileURL);
                dest_photo.setImage(image);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                // Handle malformed URL exception
            }
        } else {
            // Set a default image or handle the case where no image URL is available
            // For example:
            // Image defaultImage = new Image(getClass().getResourceAsStream("default_image.png"));
            // dest_photo.setImage(defaultImage);
        }
    }

    private Destination destination;
    @FXML
    private void handleImageClick(MouseEvent event) {
        event.consume();
            navigateToDestinationDetails(); // Call the method to navigate to destination details

    }


    // Event handler method for handling name click
    @FXML
    private void handleNameClick(MouseEvent event) {
        event.consume();

            navigateToDestinationDetails(); // Call the method to navigate to destination details

    }
    @FXML
    private void handleCardClick(MouseEvent event) {
        event.consume();
        navigateToDestinationDetails(); // Pass the destination to the details controller
    }

    // Method to navigate to destination details
    private void navigateToDestinationDetails() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontoffice/destinationdetails.fxml"));
            AnchorPane root = loader.load();

            // Get the controller for the destination details page
            DestinationDetails controller = loader.getController();
            controller.initData(destination); // Pass the destination to the details controller

            // Show the destination details stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
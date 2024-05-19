package tn.esprit.controller.destination;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.controller.reservation.ReservationAdd;
import tn.esprit.models.Destination;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DestinationDetails  {




        @FXML
        private Label destNameLabel;

        @FXML
        private Label destdescLabel;

        @FXML
        private Label destpriceLabel;

        @FXML
        private ImageView detailsimage;
        private Destination destination;

        @FXML
        void reserver(ActionEvent event) {
                try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontoffice/Reservation/reservationadd.fxml"));
                        AnchorPane root = loader.load();

                        // Get the controller for the reservation add page
                        ReservationAdd controller = loader.getController();

                        // Set the destination data in the reservation add controller
                        controller.setDestination(destination);

                        // Get the current window (Stage)
                        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                        // Close the current window and all other stages
                        Stage[] stages = Stage.getWindows().toArray(new Stage[0]);
                        for (Stage stage : stages) {
                                if (stage != currentStage) {
                                        stage.close();
                                }
                        }

                        // Show the reservation add stage
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.show();
                } catch (IOException e) {
                        e.printStackTrace();
                        // Add more specific error handling if needed
                }
        }



        @FXML
        private void initialize() {
                // Initialization code
        }

        // Method to initialize the controller
        public void initData(Destination destination) {
                this.destination = destination;
                destNameLabel.setText(destination.getName());
                destdescLabel.setText(destination.getDescription());
                destpriceLabel.setText(String.valueOf(destination.getPrice()));

                // Load and set the image
                String imageURL = destination.getImg();
                if (imageURL != null && !imageURL.isEmpty()) {
                        try {
                                Image image = new Image(imageURL);
                                detailsimage.setImage(image);
                        } catch (Exception e) {
                                // Handle invalid URL or other exceptions
                                e.printStackTrace();
                                // Provide a default image or handle the error case
                                // For example:
                                // Image defaultImage = new Image(getClass().getResourceAsStream("default_image.png"));
                                // detailsimage.setImage(defaultImage);
                        }
                } else {
                        // Set a default image or handle the case where no image URL is available
                        // For example:
                        // Image defaultImage = new Image(getClass().getResourceAsStream("default_image.png"));
                        // detailsimage.setImage(defaultImage);
                }

                // Set other details as needed
        }


        // Set other destination details accordingly

    }


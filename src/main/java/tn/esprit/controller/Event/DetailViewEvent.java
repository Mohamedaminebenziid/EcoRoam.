package tn.esprit.controller.Event;

import tn.esprit.models.Activities;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import javafx.embed.swing.SwingFXUtils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DetailViewEvent {

    @FXML
    private Label nameLabel;

    @FXML
    private Button joinButton;

    @FXML
    private Label stateLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label priceLabel;
    @FXML
    private Label dateLabel;


    @FXML
    private ImageView imageView;

    private String eventName; // Event name field

    // Add more FXML elements and corresponding fields as needed

    public void initialize(String name, String description, String state, Float price, String imagePath) {
        nameLabel.setText(name);
        descriptionLabel.setText(description);
        stateLabel.setText(state);
        priceLabel.setText(String.valueOf(price));// Initialize other elements with data

        if (imagePath != null && !imagePath.isEmpty()) {
            imageView.setImage(new javafx.scene.image.Image(imagePath));
        }
    }

    @FXML
    private Label activitiesLabel;

    public void initialize(String name, String description, String state, Float price, String imagePath, List<Activities> activities ) {
        nameLabel.setText(name);
        descriptionLabel.setText(description);
        stateLabel.setText(state);
        priceLabel.setText(String.valueOf(price));// Initialize other elements with data


        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                // Convert the file path to a URL using the file: protocol
                URL imageUrl = new File(imagePath).toURI().toURL();
                imageView.setImage(new javafx.scene.image.Image(imageUrl.toString()));
            } catch (MalformedURLException e) {
                // Handle the exception (e.g., log it, show an error message)
                e.printStackTrace();
            }
        }


        // Display activities
        if (activities != null && !activities.isEmpty()) {
            StringBuilder activitiesText = new StringBuilder();
            for (Activities activity : activities) {
                activitiesText.append(activity.getName()).append(", ");
            }
            activitiesText.deleteCharAt(activitiesText.length() - 1); // Remove the last comma
            activitiesLabel.setText(activitiesText.toString());
        } else {
            activitiesLabel.setText("None");
        }
    }

    @FXML
    private void joinnow() {
        try {
            // Load the joinevent.fxml file and set it as the scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontoffice/eve/joinevent.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            JoinEventController controller = loader.getController();

            // Pass the event name to the JoinEventController
            controller.setEventName(nameLabel.getText()); // Pass the name from nameLabel

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any potential exceptions
        }
    }

    @FXML
    private void shareOnFacebook(ActionEvent event) {
        try {
            // Take a screenshot of the interface
            WritableImage screenshot = nameLabel.getScene().snapshot(null);

            // Convert the screenshot to a file
            File file = new File("screenshot.png");
            ImageIO.write(SwingFXUtils.fromFXImage(screenshot, null), "png", file);

            // Upload the image to Cloudinary
            String imageUrl = uploadToCloudinary(file);

            // Share the image on Facebook
            shareOnFacebook(imageUrl);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to share on Facebook.");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private String uploadToCloudinary(File imageFile) throws IOException {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dvteamdfh",
                "api_key", "816844927945851",
                "api_secret", "Pky1_CnwWFvBhUpsZOT93mifvI4"));
        Map<?, ?> uploadResult = cloudinary.uploader().upload(imageFile, ObjectUtils.emptyMap());
        return (String) uploadResult.get("url");
    }

    private void shareOnFacebook(String imageUrl) throws IOException, URISyntaxException {
        String message = "Join me in this adventure... see you there!"; // Your desired phrase
        // Append the image URL to the message
        String facebookShareUrl = "https://www.facebook.com/sharer/sharer.php?u=" + imageUrl + "&quote=" + URLEncoder.encode(message, "UTF-8");
        java.awt.Desktop.getDesktop().browse(new java.net.URI(facebookShareUrl));
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}

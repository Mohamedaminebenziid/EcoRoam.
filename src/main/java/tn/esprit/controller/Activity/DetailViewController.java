package tn.esprit.controller.Activity;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DetailViewController {
    @FXML
    private Label nameLabel;

    @FXML
    private Label stateLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private ImageView imageView;

    // Add more FXML elements and corresponding fields as needed

    public void initialize(String name, String description, String state, Float price, String imagePath) {
        nameLabel.setText(name);
        descriptionLabel.setText(description);
        stateLabel.setText(state);
        priceLabel.setText(String.valueOf(price));// Initialize other elements with data

        if (imagePath != null && !imagePath.isEmpty()) {
            // Convert the imagePath to a file URL
            String fileUrl = "file:///" + imagePath.replace("\\", "/");
            Image image = new Image(fileUrl);
            imageView.setImage(image);
        }
    }

}

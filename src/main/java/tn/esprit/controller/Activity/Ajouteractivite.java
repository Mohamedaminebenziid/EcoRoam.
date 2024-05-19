package tn.esprit.controller.Activity;

import tn.esprit.models.Activities;
import tn.esprit.services.ActivitiesService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class Ajouteractivite {

    @FXML
    private TextField descriptionact;

    @FXML
    private ComboBox<String> stateComboBox;


    @FXML
    private ImageView imageView;

    @FXML
    private TextField nameact;

    @FXML
    private TextField priceact;

    @FXML
    private Button selectImageButton;

    @FXML
    private TextField stateact;

    @FXML
    private TextField imgact;

    @FXML
    private Button addButton;

    private final ActivitiesService AS = new ActivitiesService();
    private Stage stage;

    @FXML
    void addButton(ActionEvent event) {
        if (validateInput()) {
            try {
                AS.add(new Activities(nameact.getText(), imgact.getText(), stateComboBox.getValue(), descriptionact.getText(), Float.parseFloat(priceact.getText())));
                clearFields();
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
            }
        }
    }


    @FXML
    void selectImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            // Set the path of the selected image file to the image text field
            imgact.setText(selectedFile.getAbsolutePath());
            // Display the selected image in the ImageView
            try {
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                Image image = new Image(fileInputStream);
                imageView.setImage(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validateInput() {
        if (nameact.getText().isEmpty() || priceact.getText().isEmpty() || stateComboBox.getValue() == null || imgact.getText().isEmpty() || descriptionact.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please fill in all fields.");
            return false;
        }

        try {
            Float.parseFloat(priceact.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid price format. Please enter a valid number.");
            return false;
        }

        return true;
    }

    private void clearFields() {
        nameact.clear();
        priceact.clear();
        stateComboBox.getSelectionModel().clearSelection(); // Clear the ComboBox selection
        imgact.clear();
        descriptionact.clear();
        imageView.setImage(null);
    }


    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void goToStartPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/startpage.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void go_to_table_destinations(ActionEvent event) {
        try {
            // Load the FXML file for the destination table page
            Parent root = FXMLLoader.load(getClass().getResource("/backoffice/destinationtable.fxml"));

            // Get the current stage from the ActionEvent's source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create a new scene with the loaded FXML root and set it on the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle IOException more gracefully (e.g., display an error message to the user)
        }
    }

    @FXML
    void go_to_table_reservations(ActionEvent event) {
        try {
            // Load the FXML file for the reservation table page
            Parent root = FXMLLoader.load(getClass().getResource("/backoffice/resrvationtable.fxml"));

            // Get the current stage from the ActionEvent's source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create a new scene with the loaded FXML root and set it on the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle IOException more gracefully (e.g., display an error message to the user)
        }
    }

    @FXML
    void act(ActionEvent event) {
        try {
            // Load the FXML file for the reservation table page
            Parent root = FXMLLoader.load(getClass().getResource("/backoffice/act/afficheractivite.fxml"));

            // Get the current stage from the ActionEvent's source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create a new scene with the loaded FXML root and set it on the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle IOException more gracefully (e.g., display an error message to the user)
        }
    }

    @FXML
    void course(ActionEvent event) {
        try {
            // Load the FXML file for the reservation table page
            Parent root = FXMLLoader.load(getClass().getResource("/course/AfficherCourse.fxml"));

            // Get the current stage from the ActionEvent's source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create a new scene with the loaded FXML root and set it on the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle IOException more gracefully (e.g., display an error message to the user)
        }
    }



    @FXML
    void shop(ActionEvent event) {
        try {
            // Load the FXML file for the reservation table page
            Parent root = FXMLLoader.load(getClass().getResource("/backoffice/evet/showevent.fxml"));

            // Get the current stage from the ActionEvent's source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create a new scene with the loaded FXML root and set it on the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle IOException more gracefully (e.g., display an error message to the user)
        }
    }

    @FXML
    void user(ActionEvent event) {
        try {
            // Load the FXML file for the reservation table page
            Parent root = FXMLLoader.load(getClass().getResource("/user/afficheruser.fxml"));

            // Get the current stage from the ActionEvent's source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create a new scene with the loaded FXML root and set it on the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle IOException more gracefully (e.g., display an error message to the user)
        }
    }

    @FXML
    void event(ActionEvent  event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/backoffice/act/ajouteractivite.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

}

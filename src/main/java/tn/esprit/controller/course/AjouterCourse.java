package tn.esprit.controller.course;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.Course;
import tn.esprit.services.CourseService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AjouterCourse {
    @FXML
    private TextField categoryTF;

    @FXML
    private TextField descriptionTF;

    @FXML
    private TextField difficultyTF;

    @FXML
    private TextField durationTF;

    @FXML
    private ImageView imageIV; // Changer le type de l'image à ImageView

    @FXML
    private TextField titleTF;

    private final CourseService cs = new CourseService();

    /*@FXML
    void AjouterC(ActionEvent event) {
        try {
            cs.add(new Course(categoryTF.getText(), descriptionTF.getText(), difficultyTF.getText(), durationTF.getText(), imageTF.getText(), titleTF.getText()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/
    @FXML
    void AjouterC(ActionEvent event) {
        try {
            // Vérifier si les champs obligatoires ne sont pas vides
            if (titleTF.getText().isEmpty() || descriptionTF.getText().isEmpty() || durationTF.getText().isEmpty() || difficultyTF.getText().isEmpty() || categoryTF.getText().isEmpty() || imageIV.getImage() == null) {
                throw new Exception("Veuillez remplir tous les champs!");
            }
            // Récupérer le chemin de l'image
            String imagePath = imageIV.getImage().getUrl();

            cs.addc(new Course(titleTF.getText(), descriptionTF.getText(), difficultyTF.getText(), durationTF.getText(), categoryTF.getText(), imagePath));

            // Afficher une fenêtre de confirmation
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("L'ajout a été effectué avec succès!");

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(okButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == okButton){
                // Naviguer vers la page d'affichage
                naviguer(event);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    void naviguer(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/course/AfficherCourse.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    void uploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            imageIV.setImage(image);
        }
    }



}

package tn.esprit.controller.user;

import javafx.scene.control.CheckBox;
import tn.esprit.models.user;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import tn.esprit.services.UserCrud;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class signin {

    @FXML
    private TextField usereamiltextfiled;
    @FXML
    private CheckBox showpassword;
    @FXML
    private TextField usernametextfiled;

    @FXML
    private TextField userpasswordtextfiled;

    @FXML
    private TextField userphonetextfiled;

    @FXML
    private TextField userrolesextfiled;

    private Connection connection;
    private final UserCrud usercrud = new UserCrud(connection);

    @FXML
    void singuser(ActionEvent event) {
        // Vérification des champs vides
        if (usernametextfiled.getText().isEmpty() || userpasswordtextfiled.getText().isEmpty() || usereamiltextfiled.getText().isEmpty() || userphonetextfiled.getText().isEmpty()) {
            showErrorAlert("Please fill in all fields.");
            return; // Arrêter l'exécution de la méthode si des champs sont vides
        }

        // Validation de la longueur du mot de passe
        if (userpasswordtextfiled.getText().length() < 8) {
            showErrorAlert("Password must be at least 8 characters long.");
            return; // Arrêter l'exécution de la méthode si le mot de passe est trop court
        }

        // Validation de l'adresse e-mail
        String email = usereamiltextfiled.getText();
        if (!isValidEmail(email)) {
            showErrorAlert("Please enter a valid email address.");
            return; // Arrêter l'exécution de la méthode si l'adresse e-mail est invalide
        }

        // Validation du numéro de téléphone
        String tel_number = userphonetextfiled.getText();
        if (!isValidPhoneNumber(tel_number)) {
            showErrorAlert("Please enter a valid 8-digit phone number.");
            return; // Arrêter l'exécution de la méthode si les numéros de téléphone ou de CIN sont invalides
        }

        // Ajout de l'utilisateur si toutes les validations passent

        try {
            // Create a new user object with role set to "USER"
            user newUser = new user(
                    "", // Empty string for address (consider retrieving address from UI)
                    usereamiltextfiled.getText(),
                    usernametextfiled.getText(),
                    userpasswordtextfiled.getText(),
                    "[]", // Set role to "USER"
                    userphonetextfiled.getText(),
                    false // Default value for is_banned (you might need to adjust this)
            );

            // Call the method to add the user (assuming usercrud is properly initialized)
            usercrud.ajouterUser(newUser);

            // Afficher une alerte de succès si l'ajout de l'utilisateur est réussi
            showSuccessAlert("User added successfully!");
        } catch (SQLException e) {
            // Handle the SQLException here
            e.printStackTrace(); // For demonstration purposes; you might want to show an error message to the user
        }
    }


    // Méthode pour afficher une alerte d'erreur
    private void showErrorAlert(String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

    // Méthode pour afficher une alerte de succès
    private void showSuccessAlert(String message) {
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Success");
        successAlert.setHeaderText(null);
        successAlert.setContentText(message);
        successAlert.showAndWait();
    }

    // Méthode pour valider une adresse e-mail
    private boolean isValidEmail(String email) {
        // Utilisez une expression régulière pour valider l'adresse e-mail
        return email.matches("^[a-zA-Z0-9._%+-]+@(?:esprit\\.tn|gmail\\.com)$");
    }

    // Méthode pour valider un numéro de téléphone
    private boolean isValidPhoneNumber(String tel_number) {
        // Validez la longueur et le format du numéro de téléphone
        return tel_number.length() == 8 && tel_number.matches("[0-9]+") && !tel_number.startsWith("+") && !tel_number.startsWith("-") && !tel_number.startsWith("/") && !tel_number.startsWith("*");
    }


        // Afficher une alerte de succès si l'ajout de l'utilisateur est réussi


    @FXML
    void back(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/user/login.fxml"));
            Stage stage = (Stage) userphonetextfiled.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


        @FXML
        public void showpassword(ActionEvent actionEvent) {
            if (showpassword.isSelected()) {
                userpasswordtextfiled.setText(userpasswordtextfiled.getText());
            } else {
                userpasswordtextfiled.setText("");
            }
        }

    }




package tn.esprit.controller.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.user;
import tn.esprit.services.UserCrud;

import java.io.IOException;
import java.sql.SQLException;

public class ajouterusercontroller {

    @FXML
    private Button ok;
    @FXML
    private Button back;
    @FXML
    private Button logout;

    @FXML
    private TextField usereamiltextfiled;

    @FXML
    private TextField usernametextfiled;

    @FXML
    private TextField userpasswordtextfiled;

    @FXML
    private TextField usertel_numbertextfiled;

    @FXML
    private TextField userroletextfiled;

    private final UserCrud usercrud = new UserCrud();

    @FXML
    void ajouter(ActionEvent event) {
        String username = usernametextfiled.getText();
        String password = userpasswordtextfiled.getText();
        String email = usereamiltextfiled.getText();
        String telNumber = usertel_numbertextfiled.getText();
        String role = userroletextfiled.getText();
        try {
        //usercrud.ajouteruser(new user(username, password, email, role, telNumber, false, false));
        usercrud.ajouterUser(new user("adresse",
                username,
                password,
                email,
                role,
                telNumber,
                false // Assuming is_banned is set to false by default
        ));
        } catch (SQLException e) {
            // Handle the SQLException here
            e.printStackTrace(); // For demonstration purposes; you might want to show an error message to the user
        }
        showAlert(Alert.AlertType.INFORMATION, "Success", "User added successfully!");
    }

    @FXML
    void naviguer(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/afficheruser.fxml"));
            usernametextfiled.getScene().setRoot(root);
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load user display: " + e.getMessage());
        }
    }

    @FXML
    void back(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/menu.fxml"));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to go back to menu: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

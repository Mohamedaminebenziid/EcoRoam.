package tn.esprit.controller.user;

import javafx.event.ActionEvent;
import tn.esprit.models.user;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.services.UserCrud;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;

public class Userinfo {
    @FXML
    private Button back;
    @FXML
    private Button Disable;

    @FXML
    private Button update;

    @FXML
    private TextField usereamiltextfiled;

    @FXML
    private TextField usernametextfiled;

    @FXML
    private TextField userpasswordtextfiled;

    @FXML
    private TextField userphonetextfiled;

    @FXML
    private TextField userrolesextfiled;

    private Connection connection;
    private final UserCrud up = new UserCrud(connection);
    private char adresse;

    public void initData(String name, String email, String password, String cin, String phone, String roles) {
        usernametextfiled.setText(name);
        usereamiltextfiled.setText(email);
        userpasswordtextfiled.setText(password);
        userphonetextfiled.setText(phone);
        userrolesextfiled.setText(roles);
    }

    public void update(ActionEvent event) {
        try {
            String email = usereamiltextfiled.getText();
            String name = usernametextfiled.getText();
            String tel_number = userphonetextfiled.getText();
            String roles = userrolesextfiled.getText();
            String password = userpasswordtextfiled.getText();

            if (email.isEmpty() || name.isEmpty() || roles.isEmpty() || password.isEmpty()) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Veuillez remplir tous les champs.");
                errorAlert.showAndWait();
                return;
            }

            if (!email.matches("^[a-zA-Z0-9._%+-]+@(?:esprit\\.tn|gmail\\.com)$")) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Please enter a valid email address (example@esprit.tn or example@gmail.com).");
                errorAlert.showAndWait();
                return;
            }

            if (password.length() < 8) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Password must be at least 8 characters long.");
                errorAlert.showAndWait();
                return;
            }

            String phoneNumber = userphonetextfiled.getText();
            if (phoneNumber.length() != 8 || !phoneNumber.matches("[0-9]+") || phoneNumber.startsWith("+") || phoneNumber.startsWith("-") || phoneNumber.startsWith("/") || phoneNumber.startsWith("*")) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Please enter a valid 8-digit phone number that does not start with +, -, /, or *.");
                errorAlert.showAndWait();
                return;
            }

            UIManager rs = null;
            user modifiedUser = new user(rs.getInt("id"), rs.getString("adresse"), rs.getString("email"), rs.getString("username"), rs.getString("password"), rs.getString("roles"),rs.getString("tel_number"), rs.getBoolean("is_banned"));
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            up.updatefront(modifiedUser);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/menufront.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage loginStage = new Stage();
            loginStage.setScene(scene);
            loginStage.show();
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Utilisateur modifié avec succès.");
            successAlert.showAndWait();
        } catch (NumberFormatException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Veuillez saisir des valeurs numériques valides pour le téléphone.");
            errorAlert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void back(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/menufront.fxml"));
            Stage stage = (Stage) back.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void Disable(ActionEvent event) {
/*
        String email = usereamiltextfiled.getText();
        String password = null;
        String username = null;
        String roles = null;
        user currentUser = new user(adresse, email, password, username, roles);
        currentUser.setEmail(email);

        try {
            up.deletefront(currentUser);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage loginStage = new Stage();
            loginStage.setScene(scene);
            loginStage.show();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de suppression");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur s'est produite lors de la suppression de l'utilisateur.");
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
    }

    public void initData(String name, String email, String password, String phone, String roles) {
    }
}

package tn.esprit.services;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.util.MyConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class forgetpwd {

    @FXML
    private TextField newpassword;
    @FXML
    private TextField useremail;

    @FXML
    private TextField verifypassword;

    @FXML
    private Button reset;

    @FXML
    void newpassword(ActionEvent event) {

    }

    @FXML
    void reset(ActionEvent event) {
        String newPassword = newpassword.getText();
        String verifyPassword = verifypassword.getText();
        String userEmail = useremail.getText();

        // Vérifier si les deux champs de mot de passe correspondent
        if (newPassword.equals(verifyPassword)) {
            // Vérifier si le champ de mot de passe contient au moins 8 caractères
            if (newPassword.length() >= 8) {
                try {
                    // Vérifier si l'e-mail saisi existe dans la base de données et est valide
                    if (isValidEmail(userEmail) && isCorrectDomain(userEmail)) {
                        // Mettre à jour le mot de passe dans la base de données
                        updatePasswordByEmail(userEmail, newPassword);

                        // Afficher une alerte de succès
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Succès");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("Le mot de passe a été mis à jour avec succès.");

                        // Fermer la fenêtre actuelle
                        Stage stage = (Stage) reset.getScene().getWindow();
                        stage.close();

                        // Charger l'interface de connexion
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
                        Parent root = loader.load();
                        Stage loginStage = new Stage();
                        loginStage.setScene(new Scene(root));
                        loginStage.show();

                        successAlert.showAndWait();
                    } else {
                        // Aucun utilisateur avec cet e-mail trouvé, afficher une alerte d'erreur
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Erreur");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("Aucun utilisateur avec cet e-mail trouvé.");

                        errorAlert.showAndWait();
                    }
                } catch (SQLException | IOException e) {
                    // Gérer les erreurs de mise à jour du mot de passe
                    e.printStackTrace();
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Erreur");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Une erreur est survenue lors de la mise à jour du mot de passe.");

                    errorAlert.showAndWait();
                }
            } else {
                // Afficher une alerte d'erreur si le mot de passe ne contient pas assez de caractères
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Le mot de passe doit contenir au moins 8 caractères.");

                errorAlert.showAndWait();
            }
        } else {
            // Les mots de passe ne correspondent pas, afficher une alerte d'erreur
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Les mots de passe ne correspondent pas.");

            errorAlert.showAndWait();
        }
    }

    // Méthode pour vérifier le domaine de l'e-mail
    private boolean isCorrectDomain(String email) {
        return email.endsWith("@gmail.com") || email.endsWith("@esprit.tn");
    }



    @FXML
    void verifypassword(ActionEvent event) {

    }
    public void updatePasswordByEmail( String recipientEmail, String newPassword) throws SQLException {
        // Établir la connexion à la base de données en utilisant MyBD
        Connection connection = MyConnection.getInstance().getCnx();
        PreparedStatement statement = null;

        try {

            // Préparer la requête SQL pour mettre à jour le mot de passe de l'utilisateur avec l'e-mail spécifié
            String sql = "UPDATE user SET password = ? WHERE email = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, newPassword);
            statement.setString(2, recipientEmail);

            // Exécuter la requête SQL
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                // Aucun utilisateur avec cet e-mail trouvé, afficher un message d'erreur
                System.out.println("Aucun utilisateur avec cet e-mail trouvé.");
            } else {
                // Mot de passe mis à jour avec succès
                System.out.println("Mot de passe mis à jour avec succès pour l'utilisateur avec l'e-mail : " + recipientEmail);
            }
        } finally {
            // Fermer les ressources
            if (statement != null) {
                statement.close();
            }
            // Notez que la connexion n'est pas fermée ici car elle est gérée par MyBD
        }
    }
    @FXML
    void useremail(ActionEvent event) {

    }
    private boolean isValidEmail(String email) {
        // Expression régulière pour vérifier si l'adresse email se termine par "@gmail.com" ou "@esprit.tn"
        return email.matches("[a-zA-Z0-9._%+-]+@(?:gmail\\.com|esprit\\.tn)");
    }


}


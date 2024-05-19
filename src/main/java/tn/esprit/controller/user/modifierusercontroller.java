package tn.esprit.controller.user;

import tn.esprit.models.user;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.services.UserCrud;

import java.io.IOException;
import java.sql.Connection;

public class modifierusercontroller {

        @FXML
        private AnchorPane Ap_modifier;
        @FXML
        private Button back;
        @FXML
        private TextField iduser;

        @FXML
        private Button ok;


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
    private final UserCrud ps=new UserCrud(connection);
        private int ID;
    private String username;

    public void setID(int id,String email ,String name ,int phone ,String roles ,String password)
        {
            this.iduser.setText(Integer.toString(id));
            this.usereamiltextfiled.setText(email);
            this.usernametextfiled.setText(name);
            this.userphonetextfiled.setText(Integer.toString(phone));
            this.userpasswordtextfiled.setText(password);


            // Do something with the variable if needed
        }


        @FXML
        void modifieruser(ActionEvent event) {
            try {
                // Récupérer les valeurs des champs de texte
                int id = Integer.parseInt(iduser.getText());
                String email = usereamiltextfiled.getText();
                String name = usernametextfiled.getText();
                String tel_number = String.valueOf(Integer.parseInt(userphonetextfiled.getText()));
                String roles = userrolesextfiled.getText();
                String password = userpasswordtextfiled.getText();

                // Valider les données saisies

                // Vérification si les champs ne sont pas vides
                if (email.isEmpty() || name.isEmpty() || roles.isEmpty() || password.isEmpty()) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Veuillez remplir tous les champs.");
                    errorAlert.showAndWait();
                    return; // Arrêter l'exécution de la méthode si des champs sont vides
                }
                // Validation de l'adresse e-mail

                if (!email.matches("^[a-zA-Z0-9._%+-]+@(?:esprit\\.tn|gmail\\.com)$")) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Please enter a valid email address (example@esprit.tn or example@gmail.com).");
                    errorAlert.showAndWait();
                    return; // Arrêter l'exécution de la méthode si l'adresse e-mail est invalide
                }

                if (userpasswordtextfiled.getText().length() < 8) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Password must be at least 8 characters long.");
                    errorAlert.showAndWait();
                    return; // Arrêter l'exécution de la méthode si le mot de passe est trop court
                }

                // Validation du numéro de téléphone et du numéro de CIN
                String phoneNumber = userphonetextfiled.getText();
                if (phoneNumber.length() != 8 ||  !phoneNumber.matches("[0-9]+")  || phoneNumber.startsWith("+") || phoneNumber.startsWith("-") || phoneNumber.startsWith("/") || phoneNumber.startsWith("*")) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Please enter a valid 8-digit phone number that does not start with +, -, /, or *.");
                    errorAlert.showAndWait();
                    return; // Arrêter l'exécution de la méthode si les numéros de téléphone ou de CIN sont invalides
                }

                // Appeler la méthode pour modifier l'utilisateur
                user modifiedUser = new user("",email,username, password, roles, tel_number, false);
                modifiedUser.setId(id);

                ps.modifieruser(modifiedUser);
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("SUCESSCE");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Utilisateur modifié avec succès.");
                errorAlert.showAndWait();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();

                // Ouvrir la nouvelle fenêtre d'affichage des utilisateurs
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficheruser.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage newStage = new Stage();
                newStage.setScene(scene);
                newStage.show();
            } catch (NumberFormatException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Veuillez saisir des valeurs numériques valides pour le téléphone ");
                errorAlert.showAndWait();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @FXML
        void back(ActionEvent event) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/afficheruser.fxml"));
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }




    }


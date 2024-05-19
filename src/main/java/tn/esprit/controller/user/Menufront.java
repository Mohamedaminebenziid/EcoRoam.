package tn.esprit.controller.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class Menufront {

        @FXML
        private Button profil;
        @FXML
        private Button logout;

        @FXML
        private Button singin;

        @FXML
        void profil(ActionEvent event) {

            try {
                // Charger l'interface UserInfo
                FXMLLoader userInfoLoader = new FXMLLoader(getClass().getResource("/userinfo.fxml"));
                Parent userInfoRoot = userInfoLoader.load();

                // Récupérer le contrôleur de l'interface UserInfo
                Userinfo userInfoController = userInfoLoader.getController();

                // Passer les informations de l'utilisateur au contrôleur de l'interface UserInfo
                String name = ""; // Récupérer le nom d'utilisateur depuis la base de données

                String  phone ="" ; // Récupérer le nom d'utilisateur depuis la base de données
                String roles = "";
                String email = "";
                String password="";

                // Appeler la méthode initData sur le contrôleur de l'interface UserInfo
                userInfoController.initData(name, email, password, phone, roles, roles);

                // Afficher l'interface UserInfo dans une nouvelle fenêtre ou un nouveau volet
                Stage userInfoStage = new Stage();
                userInfoStage.setScene(new Scene(userInfoRoot));
                userInfoStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




        @FXML
        void singin(ActionEvent event) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/signin.fxml"));
                Stage stage = (Stage) singin.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        @FXML
        void logout(ActionEvent event) throws IOException {
            // Fermer la fenêtre du menu
            Stage stage = (Stage) logout.getScene().getWindow();
            stage.close();
            // Afficher à nouveau l'écran de connexion
            FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent loginRoot = loginLoader.load();
            Stage loginStage = new Stage();
            loginStage.setScene(new Scene(loginRoot));
            loginStage.show();

        }
    }



package tn.esprit.controller.user;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.esprit.models.user;

public class NavigationController {

    public static void changeActivatePage(ActionEvent event, String fxmlFile){
        try {
            FXMLLoader loader = new FXMLLoader(NavigationController.class.getResource(fxmlFile));
            Parent root = loader.load();


            Stage primaryStage = (Stage)((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("ActivateAccount");
            primaryStage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void changeLoginPage(ActionEvent event, String fxmlFile){
        try {
            FXMLLoader loader = new FXMLLoader(NavigationController.class.getResource(fxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage)((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.setTitle("LoginPage");
            primaryStage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void changeFeedPage(ActionEvent event, user p, String s) {

    }
}

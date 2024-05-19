package tn.esprit.javaapplicationalinfo5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Ecoroam extends Application {

    public static void main(String[] args) {
        launch(args);
    }
@Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file for the first window
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/backoffice/destinationtable.fxml"));
            Parent root1 = loader1.load();
            Scene scene1 = new Scene(root1);
            Stage stage1 = new Stage(); // Create a new stage
            stage1.setTitle("Ecoroam Client");
            stage1.setScene(scene1);
            stage1.show();

            // Load the FXML file for the second window
            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/backoffice/evet/showevent.fxml"));
            Parent root2 = loader2.load();
            Scene scene2 = new Scene(root2);
            Stage stage2 = new Stage(); // Create another new stage
            stage2.setTitle("Ecoroam");
            stage2.setScene(scene2);
            stage2.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

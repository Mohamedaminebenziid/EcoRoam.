package tn.esprit.controller.Event;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import tn.esprit.models.Events;
import tn.esprit.services.EventsService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class FrontEvent  {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox BoxAct;

    @FXML
    private BorderPane FrontOfficeeve;







    private EventsService eventsService = new EventsService();



    private int column = 0 ;
    private int row = 1 ;

    private void loadArticles() throws SQLException {
        List<Events> events = eventsService.getAll();


        for (Events event : events) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontoffice/eve/CardEvent.fxml"));
                HBox box = loader.load();
                CardEvent controller = loader.getController();

                controller.setService(event);

                // Add event handler for card click
                box.setOnMouseClicked(event1 -> handleCardClick(event));

                BoxAct.getChildren().add(box);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void handleCardClick(Events events) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontoffice/eve/DetailViewEvent.fxml"));
            Parent root = loader.load();

            // Pass data to the DetailViewEvent controller
            DetailViewEvent controller = loader.getController();
            controller.initialize(events.getName(), events.getDescription(), events.getState(), events.getPrice(), events.getImg(), events.getActivities());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    public void initialize() {
        try {
            loadArticles();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void toact(ActionEvent event) {
        try {
            // Load the FXML file for the update destination page
            Parent root = FXMLLoader.load(getClass().getResource("/frontoffice/actv/AfficherActivityFront.fxml"));

            // Get the current stage from the ActionEvent's source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create a new scene with the loaded FXML root and set it on the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void tocorse(ActionEvent event) {
        try {
            // Load the FXML file for the update destination page
            Parent root = FXMLLoader.load(getClass().getResource("/course/frontoffice/coursedesplay.fxml"));

            // Get the current stage from the ActionEvent's source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create a new scene with the loaded FXML root and set it on the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void todest(ActionEvent event) {
        try {
            // Load the FXML file for the update destination page
            Parent root = FXMLLoader.load(getClass().getResource("frontoffice/destinationdesplay.fxml"));

            // Get the current stage from the ActionEvent's source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create a new scene with the loaded FXML root and set it on the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void toevt(ActionEvent event) {
        try {
            // Load the FXML file for the update destination page
            Parent root = FXMLLoader.load(getClass().getResource("/frontoffice/eve/AfficheEventFront.fxml"));

            // Get the current stage from the ActionEvent's source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create a new scene with the loaded FXML root and set it on the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void toforum(ActionEvent event) {
        try {
            // Load the FXML file for the update destination page
            Parent root = FXMLLoader.load(getClass().getResource("/forum/ecoroampost.fxml"));

            // Get the current stage from the ActionEvent's source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create a new scene with the loaded FXML root and set it on the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void toshop(ActionEvent event) {
        try {
            // Load the FXML file for the update destination page
            Parent root = FXMLLoader.load(getClass().getResource("/projet/home.fxml"));

            // Get the current stage from the ActionEvent's source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create a new scene with the loaded FXML root and set it on the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
package tn.esprit.controller.Activity;

import tn.esprit.models.Activities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class CardController {
    @FXML
    private Label DescriptionAct;

    @FXML
    private HBox box;

    @FXML
    private ImageView img;

    @FXML
    private Label nomAct;

    @FXML
    private Label priceact;

    @FXML
    private Label stateAct;


    private Activities activities;

    private String[] Colors = {"#abb3ff", "#bfc5ff", "#b0beff"};



    @FXML
    private void handleCardClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontoffice/actv/DetailView.fxml"));
            Parent root = loader.load();

            // Pass data to the DetailViewController
            DetailViewController controller = loader.getController();
            controller.initialize(activities.getName(), activities.getDescription(), activities.getState(), activities.getPrice(), activities.getImg());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setService(Activities activities) {
        this.activities = activities;
        if (activities != null) {
            nomAct.setText(activities.getName());
            Image imageFile = new Image(new File(activities.getImg()).toURI().toString());

            img.setImage(imageFile);
            stateAct.setText(activities.getState());

            DescriptionAct.setText(activities.getDescription());


        }
        String randomColor = Colors[(int) (Math.random() * Colors.length)];
        box.setStyle("-fx-background-color: " + randomColor +
                "; -fx-background-radius: 15;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 10);");
    }




}
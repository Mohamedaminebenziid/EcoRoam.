package tn.esprit.controller.Event;

import tn.esprit.models.Events;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class CardEvent {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox box;

    @FXML
    private ImageView imgeve;

    @FXML
    private Label nomeve;

    @FXML
    private Label stateeve;
    private Events events;
    private String[] Colors = {"#abb3ff", "#bfc5ff", "#b0beff"};

    @FXML
    void initialize() {
        assert box != null : "fx:id=\"box\" was not injected: check your FXML file 'CardEvent.fxml'.";
        assert imgeve != null : "fx:id=\"imgeve\" was not injected: check your FXML file 'CardEvent.fxml'.";
        assert nomeve != null : "fx:id=\"nomeve\" was not injected: check your FXML file 'CardEvent.fxml'.";
        assert stateeve != null : "fx:id=\"stateeve\" was not injected: check your FXML file 'CardEvent.fxml'.";

    }

    public void setService(Events event) {
        this.events = event;
        if (event != null) {
            nomeve.setText(event.getName());
            Image imageFile = new Image(new File(event.getImg()).toURI().toString());

            imgeve.setImage(imageFile);
            stateeve.setText(event.getState());




        }
        String randomColor = Colors[(int) (Math.random() * Colors.length)];
        box.setStyle("-fx-background-color: " + randomColor +
                "; -fx-background-radius: 15;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 10);");
    }
}

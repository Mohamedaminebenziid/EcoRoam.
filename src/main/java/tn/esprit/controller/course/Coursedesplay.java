package tn.esprit.controller.course;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.esprit.models.Course;
import tn.esprit.services.CourseService;

import java.io.IOException;
import java.util.List;

public class Coursedesplay {

    @FXML
    private ScrollPane menu_scrollpane;

    private final CourseService courseService = new CourseService();

    @FXML
    private void initialize() {
        try {
            List<Course> courses = courseService.getAllc();
            VBox cardContainer = new VBox();
            cardContainer.setSpacing(20);

            HBox row = new HBox();
            int cardsInRow = 0;
            row.setSpacing(20);
            for (Course course : courses) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/course/frontoffice/coursecard.fxml"));
                    AnchorPane card = loader.load();

                    // Get the controller and set course details
                    coursecard controller = loader.getController();
                    controller.setCourse(course);

                    // Add the card to the current row
                    row.getChildren().add(card);
                    cardsInRow++;

                    // If we have added three cards, start a new row
                    if (cardsInRow == 3) {
                        cardContainer.getChildren().add(row);
                        row = new HBox();
                        cardsInRow = 0;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // If there are any remaining cards in the last row, add the row to the cardContainer
            if (!row.getChildren().isEmpty()) {
                cardContainer.getChildren().add(row);
            }

            // Set the VBox as the content of the ScrollPane
            menu_scrollpane.setContent(cardContainer);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}


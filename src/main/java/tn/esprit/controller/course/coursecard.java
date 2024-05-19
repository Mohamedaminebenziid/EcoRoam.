package tn.esprit.controller.course;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.models.Course;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class coursecard {



    @FXML
    private Label course_title;

    @FXML
    private ImageView course_image;


    private Course course;

    public void setCourse(Course course) {
        this.course = course;

        // Set course details in the card components
        course_title.setText(course.getTitle());
        try {
            Image imageFile = new Image(new File(course.getImage()).toURI().toString());
            course_image.setImage(imageFile);
        } catch (Exception e) {
            e.printStackTrace();
            // Set a default image in case of error
           // course_image.setImage(new Image("/resources/img/image.png"));
            // Set course_image to null in case of error
            course_image.setImage(null);
        }
    }


    @FXML
    private void handleImageClick(MouseEvent event) {
        event.consume();
        navigateToCourseDetails(); // Call the method to navigate to course details
    }

    // Event handler method for handling title click
    @FXML
    private void handleNameClick(MouseEvent event) {
        event.consume();
        navigateToCourseDetails(); // Call the method to navigate to course details
    }

    @FXML
    private void handleCardClick(MouseEvent event) {
        event.consume();
        navigateToCourseDetails(); // Pass the course to the details controller
    }

    // Method to navigate to course details
    private void navigateToCourseDetails() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/course/frontoffice/coursedetails.fxml"));
            Parent root = loader.load();

            // Get the controller for the course details page
            Coursedetails controller = loader.getController();
            controller.initData(course); // Pass the course to the details controller

            // Show the course details stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


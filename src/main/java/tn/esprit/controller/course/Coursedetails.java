package tn.esprit.controller.course;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.models.Course;

import java.io.ByteArrayInputStream;
import java.io.File;

public class Coursedetails {
    @FXML
    private Label courseNameLabel;

    @FXML
    private Label courseDescriptionLabel;

    @FXML
    private Label courseDurationLabel;

    @FXML
    private Label courseDifficultyLabel;

    @FXML
    private Label courseCategoryLabel;

    @FXML
    private ImageView courseImageView;

    private Course course;

    public void initData(Course course) {
        this.course = course;
        courseNameLabel.setText(course.getTitle());
        courseDescriptionLabel.setText(course.getDescription());
        courseDurationLabel.setText(course.getDuration());
        courseDifficultyLabel.setText(course.getDifficulty());
        courseCategoryLabel.setText(course.getCategory());

        // Load and set the image
        String imagePath = course.getImage();
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                Image imageFile = new Image(new File(imagePath).toURI().toString());
                courseImageView.setImage(imageFile);
            } catch (Exception e) {
                e.printStackTrace();
                // Set a default image in case of error
                courseImageView.setImage(new Image("/resources/img/image.png"));
            }
        } else {
            // Set a default image if no image is available
            courseImageView.setImage(new Image("/resources/img/image.png"));
        }
    }
}


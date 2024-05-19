package tn.esprit.controller.course;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Course;
import tn.esprit.services.CourseService;

import java.io.IOException;

    public class UpdateCourse {
        @FXML
        private TextField categoryTF;

        @FXML
        private TextField descriptionTF;

        @FXML
        private TextField difficultyTF;

        @FXML
        private TextField durationTF;

        @FXML
        private TextField imageTF;

        @FXML
        private TextField titleTf;
        @FXML
        private TextField idr;

        @FXML
        void coursedelete(ActionEvent event) {
            try {
                String sid = idr.getText();
                int id = Integer.parseInt(sid);

                // Create an instance of CourseService
                CourseService courseService = new CourseService();

                // Call getOne to retrieve the course by ID
                Course course = courseService.getOnec(id);

                if (course == null) {
                    // Course with the given ID doesn't exist in the database
                    showErrorAlert("Error", "Course not found with ID " + id);
                } else {
                    // Delete the course
                    courseService.deleteCourse(id);
                    showInformationAlert("Success", "Course deleted successfully!");
                }
            } catch (NumberFormatException e) {
                // Handle invalid ID input
                showErrorAlert("Error", "Invalid input. Please enter a valid number.");
            } catch (IllegalArgumentException e) {
                // Handle invalid course ID or other exceptions
                showErrorAlert("Error", e.getMessage());
            }
        }
        private void showInformationAlert(String title, String content) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
        }

      /*2 @FXML
       void delete(ActionEvent event) {
           try {
               String sid = idr.getText();
               int id = Integer.parseInt(sid);

               // Create an instance of CourseService
               CourseService courseService = new CourseService();

               // Call deleteCourse to delete the course by ID
               boolean deleted = courseService.deleteCourse(id);

               if (deleted) {
                   showAlert("Success", "Course with ID " + id + " has been deleted successfully.");
               } else {
                   showAlert("Error", "Course not found with ID " + id);
               }
           } catch (NumberFormatException e) {
               // Handle invalid ID input
               showAlert("Error", "Invalid input. Please enter a valid number.");
           } catch (IllegalArgumentException e) {
               // Handle invalid course ID or other exceptions
               showAlert("Error", e.getMessage());
           }
       }*/

        @FXML
        void courseupdate (ActionEvent event){
            try {
                String sid = idr.getText();
                int id = Integer.parseInt(sid);

                // Create an instance of CourseService
                CourseService courseService = new CourseService();

                // Call getOne to retrieve the course by ID
                Course course = courseService.getOnec(id);

                if (course == null) {
                    // Course with the given ID doesn't exist in the database
                    showErrorAlert("Error", "Course not found with ID " + id);
                } else {
                    // Update the course details
                    course.setTitle(titleTf.getText());
                    course.setDescription(descriptionTF.getText());
                    course.setDuration(durationTF.getText());
                    course.setDifficulty(difficultyTF.getText());
                    course.setCategory(categoryTF.getText());
                    course.setImage(imageTF.getText());

                    // Call update method from CourseService to update the course in the database
                    courseService.updatec(course);

                    showInformationAlert("Success", "Course updated successfully!");
                }
            } catch (NumberFormatException e) {
                // Handle invalid ID input
                showErrorAlert("Error", "Invalid input. Please enter a valid number.");
            } catch (IllegalArgumentException e) {
                // Handle invalid course ID or other exceptions
                showErrorAlert("Error", e.getMessage());
            }

                }
        @FXML
        void findcoursebyid(ActionEvent event) {
            try {
                String sid = idr.getText();
                int id = Integer.parseInt(sid);

                // Create an instance of CourseService
                CourseService courseService = new CourseService();

                // Call getOne to retrieve the course by ID
                Course course = courseService.getOnec(id);

                if (course == null) {
                    // Course with the given ID doesn't exist in the database
                    showErrorAlert("Error", "Course not found with ID " + id);
                } else {
                    // Populate the UI elements with course details
                    titleTf.setText(course.getTitle());
                    descriptionTF.setText(course.getDescription());
                    durationTF.setText(course.getDuration());
                    difficultyTF.setText(course.getDifficulty());
                    categoryTF.setText(course.getCategory());
                    imageTF.setText(course.getImage());
                }

            } catch (NumberFormatException e) {
                // Handle invalid ID input
                showErrorAlert("Error", "Invalid course ID. Please enter a valid number.");
            } catch (Exception e) {
                // Handle unexpected errors
                showErrorAlert("Error", "An unexpected error occurred.");
                e.printStackTrace(); // Print the stack trace for debugging purposes
            }
        }



        @FXML
        void tocourselist (ActionEvent event){
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/course/AfficherCourse.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Utility method to show error alert
        private void showErrorAlert(String title, String message) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setContentText(message);
            alert.showAndWait();
        }

       /* public static void main(String[] args) {
            CourseService courseService = new CourseService();

            // Création d'un objet Course avec les nouvelles informations
            Course updatedCourse = new Course();
            updatedCourse.setId(1); // ID du cours à mettre à jour
            updatedCourse.setTitle("Nouveau titre");
            updatedCourse.setDescription("Nouvelle description");
            updatedCourse.setDuration("Nouvelle durée");
            updatedCourse.setDifficulty("Nouvelle difficulté");
            updatedCourse.setCategory("Nouvelle catégorie");
            updatedCourse.setImage("Nouvelle image");

            // Appel de la méthode update de CourseService pour mettre à jour le cours
            courseService.update(updatedCourse);
        }*/
            }
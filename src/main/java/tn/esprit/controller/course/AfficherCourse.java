package tn.esprit.controller.course;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.models.Course;
import javafx.event.ActionEvent;
/*import javax.swing.table.TableColumn;
import javax.swing.text.TableView;*/
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import tn.esprit.services.CourseService;

import java.io.IOException;
import java.sql.Time;
import java.util.List;

public class AfficherCourse {
    @FXML
    private TableColumn<Course, String> categoryColnn;

    @FXML
    private TableColumn<Course, String> descriptionColnn;

    @FXML
    private TableColumn<Course,String > difficultyColnn;
    @FXML
    private TableColumn<Course, String> durationColnn;

    @FXML
    private TableColumn<Course, String> imagecolnn;
    @FXML
    private TableView<Course> tableview;

    @FXML
    private TableColumn<Course, String> titleColnn;
    @FXML
    private TableColumn<?, ?> courseid;
    @FXML
    private final CourseService cs = new CourseService();
    @FXML
    void navigatetoajoutercourse(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/course/AjouterCourse.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void navigatetoupdate(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/course/UpdateCourse.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    void initialize() {
        try {
            List<Course> courses = cs.getAllc();
            ObservableList<Course> observableList = FXCollections.observableArrayList(courses);
            tableview.setItems(observableList);
            titleColnn.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionColnn.setCellValueFactory(new PropertyValueFactory<>("description"));
            durationColnn.setCellValueFactory(new PropertyValueFactory<>("duration"));
            difficultyColnn.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
            categoryColnn.setCellValueFactory(new PropertyValueFactory<>("category"));
            imagecolnn.setCellValueFactory(new PropertyValueFactory<>("image"));
            courseid.setCellValueFactory(new PropertyValueFactory<>("id"));

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /*@FXML
    void initialize() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCourse.fxml"));
            loader.setController(this);
            Parent root = loader.load();
            List<Course> courses = cs.getAll();
            ObservableList<Course> observableList = FXCollections.observableList(courses);
            tableview.setItems(observableList);
            titleColnn.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionColnn.setCellValueFactory(new PropertyValueFactory<>("description"));
            durationColnn.setCellValueFactory(new PropertyValueFactory<>("duration"));
            difficultyColnn.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
            categoryColnn.setCellValueFactory(new PropertyValueFactory<>("category"));
            imagecolnn.setCellValueFactory(new PropertyValueFactory<>("image"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}

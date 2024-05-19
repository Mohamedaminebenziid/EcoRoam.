package tn.esprit.controller.Event;

import tn.esprit.models.Activities;
import tn.esprit.models.Events;
import tn.esprit.services.ActivitiesService;
import tn.esprit.services.EventsService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Updateeve {
    private Events selectedEvent;
    private EventsService eventsService;

    @FXML
    private DatePicker datepickerr;

    @FXML
    private TextField descriptioneven;

    @FXML
    private TextField imgeven;

    @FXML
    private TextField nameevn;

    @FXML
    private TextField priceeven;

    @FXML
    private ListView<Activities> selectactivityy;

    @FXML
    private TextField stateeven;

    private Stage stage;

    public Updateeve() {
        eventsService = new EventsService();
    }

    public void initData(Events event) {
        selectedEvent = event;
        // Set the fields with the selected event's data
        nameevn.setText(selectedEvent.getName());
        priceeven.setText(String.valueOf(selectedEvent.getPrice()));
        stateeven.setText(selectedEvent.getState());
        imgeven.setText(selectedEvent.getImg());
        descriptioneven.setText(selectedEvent.getDescription());
        //datepickerr.setValue(selectedEvent.getDate().toLocalDate());

        populateActivitiesListView();

        // You need to populate the ListView with available activities
        // You can use the eventsService to fetch available activities
    }

    @FXML
    void pickImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            // Set the path of the selected image file to the image text field
            imgeven.setText(file.getAbsolutePath());
        }
    }

    @FXML
    void pickdatee(ActionEvent event) {
        LocalDate selectedDate = datepickerr.getValue();
        System.out.println("Selected date: " + selectedDate);
    }


    @FXML
    void saveevent(ActionEvent event) {
        // Validate input fields
        if (validateFields() && validateDate()) {
            // Update the selected event with the new data
            selectedEvent.setName(nameevn.getText());
            selectedEvent.setPrice(Float.parseFloat(priceeven.getText()));
            selectedEvent.setState(stateeven.getText());
            selectedEvent.setImg(imgeven.getText());
            selectedEvent.setDescription(descriptioneven.getText());

            try {
                // Call the update method in the EventsService to update the event in the database
                eventsService.update(selectedEvent);
                // Close the update form after saving
                // You can implement this part based on your application's flow
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the exception appropriately
            }
        }
    }

    private boolean validateFields() {
        String name = nameevn.getText();
        String price = priceeven.getText();
        String state = stateeven.getText();
        String img = imgeven.getText();
        String description = descriptioneven.getText();

        if (name.isEmpty() || price.isEmpty() || state.isEmpty() || img.isEmpty() || description.isEmpty()) {
            showAlert("Please fill in all fields.");
            return false;
        }

        try {
            Float.parseFloat(price);
        } catch (NumberFormatException e) {
            showAlert("Invalid price format. Please enter a valid number.");
            return false;
        }

        return true;
    }

    private boolean validateDate() {
        return true;
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void populateActivitiesListView() {
        try {
            // Retrieve available activities from the database
            ActivitiesService activitiesService = new ActivitiesService();
            List<Activities> allActivities = activitiesService.getAll();

            // Display available activities in the ListView
            ObservableList<Activities> activitiesObservableList = FXCollections.observableArrayList(allActivities);
            selectactivityy.setItems(activitiesObservableList);

            // Pre-select activities that are associated with the event
            // For example:
            // selectactivityy.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            // selectactivityy.getSelectionModel().select(...);
            // selectactivityy.getSelectionModel().select(...);
            // ...
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goToStartPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/startpage.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void go_to_table_destinations(ActionEvent event) {
        try {
            // Load the FXML file for the destination table page
            Parent root = FXMLLoader.load(getClass().getResource("/backoffice/destinationtable.fxml"));

            // Get the current stage from the ActionEvent's source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create a new scene with the loaded FXML root and set it on the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle IOException more gracefully (e.g., display an error message to the user)
        }
    }

    @FXML
    void go_to_table_reservations(ActionEvent event) {
        try {
            // Load the FXML file for the reservation table page
            Parent root = FXMLLoader.load(getClass().getResource("/backoffice/resrvationtable.fxml"));

            // Get the current stage from the ActionEvent's source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create a new scene with the loaded FXML root and set it on the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle IOException more gracefully (e.g., display an error message to the user)
        }
    }

    @FXML
    void act(ActionEvent event) {
        try {
            // Load the FXML file for the reservation table page
            Parent root = FXMLLoader.load(getClass().getResource("/backoffice/act/afficheractivite.fxml"));

            // Get the current stage from the ActionEvent's source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create a new scene with the loaded FXML root and set it on the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle IOException more gracefully (e.g., display an error message to the user)
        }
    }

    @FXML
    void course(ActionEvent event) {
        try {
            // Load the FXML file for the reservation table page
            Parent root = FXMLLoader.load(getClass().getResource("/course/AfficherCourse.fxml"));

            // Get the current stage from the ActionEvent's source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create a new scene with the loaded FXML root and set it on the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle IOException more gracefully (e.g., display an error message to the user)
        }
    }



    @FXML
    void shop(ActionEvent event) {
        try {
            // Load the FXML file for the reservation table page
            Parent root = FXMLLoader.load(getClass().getResource("/backoffice/evet/showevent.fxml"));

            // Get the current stage from the ActionEvent's source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create a new scene with the loaded FXML root and set it on the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle IOException more gracefully (e.g., display an error message to the user)
        }
    }

    @FXML
    void user(ActionEvent event) {
        try {
            // Load the FXML file for the reservation table page
            Parent root = FXMLLoader.load(getClass().getResource("/user/afficheruser.fxml"));

            // Get the current stage from the ActionEvent's source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create a new scene with the loaded FXML root and set it on the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle IOException more gracefully (e.g., display an error message to the user)
        }
    }

    @FXML
    void event(ActionEvent  event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/backoffice/act/ajouteractivite.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

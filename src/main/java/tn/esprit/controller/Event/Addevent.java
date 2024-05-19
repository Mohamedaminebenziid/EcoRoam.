package tn.esprit.controller.Event;

import tn.esprit.models.Activities;
import tn.esprit.services.ActivitiesService;
import tn.esprit.models.Events;
import tn.esprit.services.EventsService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Addevent {

    @FXML
    private ListView<HBox> selectactivity;

    @FXML
    private TextField descriptioneve;

    @FXML
    private TextField imgeve;

    @FXML
    private TextField nameev;

    @FXML
    private TextField priceeve;

    @FXML
    private TextField stateeve;

    @FXML
    private DatePicker datepicker;

    private EventsService eventsService;

    private Stage stage;
    /*private List<String> governorates = List.of(
            "Ariana", "Béja", "Ben Arous", "Bizerte", "Gabès",
            "Gafsa", "Jendouba", "Kairouan", "Kasserine", "Kébili",
            "Kef", "Mahdia", "Manouba", "Medenine", "Monastir",
            "Nabeul", "Sfax", "Sidi Bouzid", "Siliana", "Sousse",
            "Tataouine", "Tozeur", "Tunis", "Zaghouan"
    );*/

    public Addevent() {
        eventsService = new EventsService();
    }

    @FXML
    void initialize() {
        try {

            //TextFields.bindAutoCompletion(stateeve, governorates);
            // Populate the selectactivity ListView with existing activities
            ActivitiesService activitiesService = new ActivitiesService();
            List<Activities> allActivities = activitiesService.getAll();

            // Create a new ObservableList to hold the modified list items
            ObservableList<HBox> items = FXCollections.observableArrayList();

            // Add each activity to the ListView with a checkbox
            for (Activities activity : allActivities) {
                // Create a CheckBox for each activity
                CheckBox checkBox = new CheckBox();
                checkBox.setText(activity.getName());
                checkBox.setUserData(activity); // Store the activity object as user data

                // Create an HBox to hold the checkbox
                HBox hbox = new HBox(checkBox);
                hbox.setSpacing(10); // Set spacing between elements

                // Add the HBox to the items list
                items.add(hbox);
            }

            // Set the modified items list to the ListView
            selectactivity.setItems(items);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addevent(ActionEvent event) {
        // Retrieve input values from text fields and other UI elements
        String name = nameev.getText();
        String price = priceeve.getText();
        String state = stateeve.getText();
        String img = imgeve.getText();
        String description = descriptioneve.getText();


        // Perform input validation
        if (name.isEmpty() || price.isEmpty() || state.isEmpty() || img.isEmpty() || description.isEmpty()) {
            // Display an alert if any of the fields are empty
            showAlert("Please fill in all fields.");
            return;
        }

        // Validate price format
        try {
            Float.parseFloat(price);
        } catch (NumberFormatException e) {
            showAlert("Invalid price format. Please enter a valid number.");
            return;
        }

        // Check if the selected date is in the past



        // Retrieve selected activities from the ListView
        List<CheckBox> selectedCheckBoxes = getSelectedCheckBoxes();

        if (selectedCheckBoxes.isEmpty()) {
            showAlert("Please select at least one activity.");
            return;
        }

        // Once validated, you can use these values to create a new event object
        Events newEvent = new Events(name, state, description, img, Float.parseFloat(price));

        try {
            // Add the new event to the database
            eventsService.add(newEvent);

            // Retrieve the ID of the newly added event
            int eventId = newEvent.getId();

            // Associate the selected activities with the event by adding entries to the events_activities table
            for (CheckBox checkBox : selectedCheckBoxes) {
                Activities activity = (Activities) checkBox.getUserData();
                eventsService.addEventActivity(eventId, activity.getId());
            }

            System.out.println("Event added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQL exception
        }
    }

    private List<CheckBox> getSelectedCheckBoxes() {
        ObservableList<HBox> items = selectactivity.getItems();
        ObservableList<CheckBox> selectedCheckBoxes = FXCollections.observableArrayList();

        for (HBox hbox : items) {
            CheckBox checkBox = (CheckBox) hbox.getChildren().get(0);
            if (checkBox.isSelected()) {
                selectedCheckBoxes.add(checkBox);
            }
        }

        return selectedCheckBoxes;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Input Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void pickdate(ActionEvent event) {
        System.out.println("Selected date: " + datepicker.getValue());
    }


    @FXML
    void pickImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            // Set the path of the selected image file to the image text field
            imgeve.setText(file.getAbsolutePath());
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/backoffice/act/showevent.fxml"));
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

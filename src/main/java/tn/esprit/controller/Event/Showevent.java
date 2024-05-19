package tn.esprit.controller.Event;

import tn.esprit.models.Events;
import tn.esprit.services.EventsService;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.StringJoiner;

public class Showevent {
    private EventsService eventsService;

    public Showevent() {
        eventsService = new EventsService();
    }

    @FXML
    private TableColumn<Events, String> acteve;

    @FXML
    private TableColumn<Events, String> dateeve;

    @FXML
    private TableColumn<Events, String> descriptioneve;

    @FXML
    private TableColumn<Events, String> imgeve;

    @FXML
    private TableColumn<Events, String> nameeve;

    @FXML
    private TableColumn<Events, String> priceeve;

    @FXML
    private TableColumn<Events, String> stateeve;

    @FXML
    private TableColumn<Events, Void> actionseve;

    @FXML
    private AnchorPane tableeve;

    @FXML
    private TableView<Events> tableevents;

    @FXML
    void initialize() {
        // Bind each TableColumn to the corresponding property of Events class
        nameeve.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceeve.setCellValueFactory(new PropertyValueFactory<>("price"));
        stateeve.setCellValueFactory(new PropertyValueFactory<>("state"));
        imgeve.setCellValueFactory(new PropertyValueFactory<>("img"));

        descriptioneve.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Set up the cellValueFactory for the acteve column to concatenate the activity names
        acteve.setCellValueFactory(cellData -> {
            List<String> activityNames = cellData.getValue().getActivityNames();
            if (activityNames != null && !activityNames.isEmpty()) {
                // Concatenate the names of all activities into a single string
                StringJoiner activityNamesString = new StringJoiner(", ");
                for (String activityName : activityNames) {
                    activityNamesString.add(activityName);
                }
                return new SimpleStringProperty(activityNamesString.toString());
            } else {
                return new SimpleStringProperty("");
            }
        });

        // Set up the cell factory for the actionseve column to display buttons for delete and update actions
        actionseve.setCellFactory(new Callback<TableColumn<Events, Void>, TableCell<Events, Void>>() {
            @Override
            public TableCell<Events, Void> call(final TableColumn<Events, Void> param) {
                return new TableCell<Events, Void>() {
                    private final Button deleteButton = new Button("Delete");
                    private final Button updateButton = new Button("Update");

                    {
                        deleteButton.setOnAction((ActionEvent event) -> {
                            Events selectedEvent = getTableView().getItems().get(getIndex());
                            deleteEvent(selectedEvent);
                        });

                        updateButton.setOnAction((ActionEvent event) -> {
                            Events selectedEvent = getTableView().getItems().get(getIndex());
                            updateEvent(selectedEvent);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(new HBox(deleteButton, updateButton));
                        }
                    }
                };
            }
        });

        // Populate the table with events data
        loadEventsData();
    }

    private void loadEventsData() {
        try {
            // Retrieve events data from the database
            List<Events> eventsList = eventsService.getAll();

            // Clear existing items in the table
            tableevents.getItems().clear();

            // Add events data to the table
            tableevents.getItems().addAll(eventsList);

            // Debug prints to check activities for each event
            for (Events event : eventsList) {
                List<String> activityNames = event.getActivityNames();
                System.out.println("Event: " + event.getName() + ", Activity Names: " + activityNames);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteeve(ActionEvent event) {
        // Retrieve the selected event from the table
        Events selectedEvent = tableevents.getSelectionModel().getSelectedItem();

        if (selectedEvent != null) {
            try {
                // Delete the selected event from the database
                eventsService.delete(selectedEvent.getId());

                // Reload events data after deletion
                loadEventsData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void newevent(ActionEvent event) {
        // Open the add new event form

        // Load the AddEvent.fxml file and create a new stage to display the form
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/backoffice/evet/addevent.fxml"));
        Parent root;
        try {
            root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add New Event");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void updateeve(ActionEvent event) {
        // Retrieve the selected event from the table
        Events selectedEvent = tableevents.getSelectionModel().getSelectedItem();

        if (selectedEvent != null) {
            // Open the update event form with the selected event for update
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/backoffice/evet/updateeve.fxml"));
            Parent root;
            try {
                root = loader.load();
                Updateeve controller = loader.getController();
                controller.initData(selectedEvent); // Pass the selected event to the update form
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Update Event");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteEvent(Events event) {
        try {
            // Delete the selected event from the database
            eventsService.delete(event.getId());

            // Reload events data after deletion
            loadEventsData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateEvent(Events event) {
        // Open the update event form with the selected event for update
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/backoffice/evet/updateeve.fxml"));
        Parent root;
        try {
            root = loader.load();
            Updateeve controller = loader.getController();
            controller.initData(event); // Pass the selected event to the update form
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Update Event");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showSeasonStatistics(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/backoffice/evet/statistics.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Season Statistics");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/backoffice/evet/addevent.fxml"));
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

}

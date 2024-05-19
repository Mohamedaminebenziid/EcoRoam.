package tn.esprit.controller.Activity;

import javafx.scene.Node;
import tn.esprit.models.Activities;
import tn.esprit.services.ActivitiesService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FrontActivites {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox BoxAct;

    @FXML
    private CheckBox ascendingPriceCheckBox;

    @FXML
    private CheckBox descendingPriceCheckBox;

    @FXML
    private ComboBox<String> stateFilterComboBox;

    private ActivitiesService activitiesService = new ActivitiesService();

    private void loadArticles() throws SQLException {
        List<Activities> activites = activitiesService.getAll();

        for (Activities activite : activites) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontoffice/actv/Card.fxml"));
                HBox box = loader.load();
                CardController controller = loader.getController();
                controller.setService(activite);
                box.setOnMouseClicked(event -> handleCardClick(activite));
                BoxAct.getChildren().add(box);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleCardClick(Activities activities) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontoffice/actv/DetailView.fxml"));
            Parent root = loader.load();
            DetailViewController controller = loader.getController();
            controller.initialize(activities.getName(), activities.getDescription(), activities.getState(), activities.getPrice(), activities.getImg());
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void applyFilters(ActionEvent event) throws SQLException {
        String selectedState = stateFilterComboBox.getValue();
        boolean ascendingPrice = ascendingPriceCheckBox.isSelected();
        boolean descendingPrice = descendingPriceCheckBox.isSelected();
        List<Activities> filteredActivities = activitiesService.getAll();

        if (selectedState != null && !selectedState.isEmpty()) {
            filteredActivities = filteredActivities.stream()
                    .filter(activity -> activity.getState().equalsIgnoreCase(selectedState))
                    .collect(Collectors.toList());
        }

        if (ascendingPrice && !descendingPrice) {
            filteredActivities.sort(Comparator.comparingDouble(Activities::getPrice));
        } else if (descendingPrice && !ascendingPrice) {
            filteredActivities.sort(Comparator.comparingDouble(Activities::getPrice).reversed());
        }

        BoxAct.getChildren().clear();

        for (Activities activity : filteredActivities) {
            loadCard(activity);
        }
    }

    private void loadCard(Activities activite) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontoffice/actv/Card.fxml"));
            HBox box = loader.load();
            CardController controller = loader.getController();
            controller.setService(activite);
            box.setOnMouseClicked(event -> handleCardClick(activite));
            BoxAct.getChildren().add(box);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        try {
            loadArticles();
            initializeFilters();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeFilters() {
        ObservableList<String> statesList = FXCollections.observableArrayList(
                "Ariana", "Beja", "Ben Arous", "Bizerte", "Gabes", "Gafsa", "Jendouba",
                "Kairouan", "Kasserine", "Kebili", "Kef", "Mahdia", "Manouba", "Medenine",
                "Monastir", "Nabeul", "Sfax", "Sidi Bouzid", "Siliana", "Sousse", "Tataouine",
                "Tozeur", "Tunis", "Zaghouan"
        );
        stateFilterComboBox.setItems(statesList);
    }
    @FXML
    void toact(ActionEvent event) {
        try {
            // Load the FXML file for the update destination page
            Parent root = FXMLLoader.load(getClass().getResource("/frontoffice/actv/AfficherActivityFront.fxml"));

            // Get the current stage from the ActionEvent's source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create a new scene with the loaded FXML root and set it on the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void tocorse(ActionEvent event) {
        try {
            // Load the FXML file for the update destination page
            Parent root = FXMLLoader.load(getClass().getResource("/course/frontoffice/coursedesplay.fxml"));

            // Get the current stage from the ActionEvent's source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create a new scene with the loaded FXML root and set it on the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void todest(ActionEvent event) {
        try {
            // Load the FXML file for the update destination page
            Parent root = FXMLLoader.load(getClass().getResource("/frontoffice/destinationdesplay.fxml"));

            // Get the current stage from the ActionEvent's source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create a new scene with the loaded FXML root and set it on the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void toevt(ActionEvent event) {
        try {
            // Load the FXML file for the update destination page
            Parent root = FXMLLoader.load(getClass().getResource("/frontoffice/eve/AfficheEventFront.fxml"));

            // Get the current stage from the ActionEvent's source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create a new scene with the loaded FXML root and set it on the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void toforum(ActionEvent event) {
        try {
            // Load the FXML file for the update destination page
            Parent root = FXMLLoader.load(getClass().getResource("/forum/ecoroampost.fxml"));

            // Get the current stage from the ActionEvent's source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create a new scene with the loaded FXML root and set it on the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void toshop(ActionEvent event) {
        try {
            // Load the FXML file for the update destination page
            Parent root = FXMLLoader.load(getClass().getResource("/projet/home.fxml"));

            // Get the current stage from the ActionEvent's source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create a new scene with the loaded FXML root and set it on the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

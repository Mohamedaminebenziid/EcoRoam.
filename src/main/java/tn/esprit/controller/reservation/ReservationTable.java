package tn.esprit.controller.reservation;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.models.Destination;
import tn.esprit.models.Reservation;
import tn.esprit.services.DestinationService;
import tn.esprit.services.ReservationService;

public class ReservationTable {

    @FXML
    private ListView<Reservation> listviewreservation;
    @FXML
    void goto_update_reserv(ActionEvent event) {
        try {
            // Load the FXML file for the update destination page
            Parent root = FXMLLoader.load(getClass().getResource("/backoffice/reservationupdate.fxml"));

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

    private final ReservationService ss = new ReservationService();


    @FXML
    void initialize() {
        try {
            List<Reservation> reservations = ss.getAllReservations(); // Assuming this method name is correct
            ObservableList<Reservation> observableList = FXCollections.observableList(reservations);

            // Assign the observableList to the ListView
            listviewreservation.setItems(observableList);

            listviewreservation.setCellFactory(param -> new ListCell<Reservation>() {
                @Override
                protected void updateItem(Reservation item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null); // Reset the graphic representation of the cell
                    } else {
                        // Build a string with all the information of the Reservation object
                        StringBuilder sb = new StringBuilder();
                        sb.append("id : ").append(item.getReservationId()).append("\n")
                                .append("start date : ").append(item.getStartDate()).append("\n")
                                .append("end date : ").append(item.getEndDate()).append("\n")
                                .append("price : ").append(item.getTotalPrice()).append("\n")
                                .append("number of days: ").append(item.getNumberOfDays()).append("\n");

                        // Create a delete button
                        Button deleteButton = new Button("Delete");
                        deleteButton.setOnAction(event -> {
                            // Handle delete action here
                            deleteReservation(item);
                        });

                        // Add the delete button to the cell
                        setGraphic(deleteButton);

                        // Set the string representation to the cell's text
                        setText(sb.toString());
                    }
                }
            });
        } catch (Exception e) {
            // Handle the exception here
            e.printStackTrace(); // Print the stack trace for debugging purposes
            // You can also show an error message to the user or log the exception
        }
    }

    private void deleteReservation(Reservation reservation) {
        try {
            // Confirm deletion
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirmation");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Are you sure you want to delete the reservation?");

            // Add "Yes" and "No" buttons to the dialog
            ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
            confirmAlert.getButtonTypes().setAll(yesButton, noButton);

            // Show the confirmation dialog
            Optional<ButtonType> result = confirmAlert.showAndWait();

            // If user chooses "Yes", proceed with deletion
            if (result.isPresent() && result.get() == yesButton) {
                // Call your delete method from the service
                ss.deleteReservation(reservation.getReservationId());
                // Refresh the list view after deletion
                listviewreservation.getItems().remove(reservation);
            }
        } catch (Exception e) {
            // Handle exception
            e.printStackTrace();
            // Show error message to the user or log the exception
        }


}}



/*java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import entities.categorie_service;
import entities.service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.AnchorPane;
import services.ServiceService;

public class AfficherServiceController {

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private TableColumn<service, String> datestf;

        @FXML
        private TableColumn<service, String> descriptionstf;

        @FXML
        private TableColumn<service, String> locationstf;

        @FXML
        private TableColumn<service, String> namestf;

        @FXML
        private TableColumn<service, String> statestf;

        @FXML
    private TableColumn<categorie_service, Integer> catidtf;



    @FXML
    private TableColumn<service, Image> imageCol;

    @FXML
    private Button BBBB;

    @FXML
    private Button BBBB1;

    @FXML
    private Button BBBB11;

    @FXML
    private Button BBBB111;

    @FXML
    private Button BBBB1111;

    @FXML
    private Button BBBB11111;

    @FXML
    private Button BBBB2;

    @FXML
    private Button addBTN;

    @FXML
    private ButtonBar bar;

    @FXML
    private ButtonBar bar1;

    @FXML
    private ButtonBar bar11;

    @FXML
    private ButtonBar bar111;

    @FXML
    private ButtonBar bar1111;

    @FXML
    private ButtonBar bar11111;

    @FXML
    private ButtonBar bar2;


    @FXML
    private Button deleteBTN;
    @FXML
    private Pane inner_pane;

    @FXML
    private Pane most_inner_pane;

    @FXML
    private HBox root;

    @FXML
    private TextField searchtxt;

    @FXML
    private AnchorPane side_ankerpane;

    @FXML
    private Button updateBTN;
    @FXML
    private Label welcomeLBL;


@FXML
private ListView<service> listView;

    @FXML
    private ImageView img1;

    @FXML
    private ImageView img10;

    @FXML
    private ImageView img11;

    @FXML
    private ImageView img2;

    @FXML
    private ImageView img3;

    @FXML
    private ImageView img4;

    @FXML
    private ImageView img5;

    @FXML
    private ImageView img6;

    @FXML
    private ImageView img7;

    @FXML
    private ImageView img8;

    @FXML
    private ImageView img9;


    private final ServiceService ss = new ServiceService();
    ObservableList<service> observableList;

    @FXML
    void initialize() {
        try {
            List<service> serviceList = ss.afficher();

            observableList = FXCollections.observableList(serviceList);

            // Assignez l'observableList à la ListView
            listView.setItems(observableList);

            listView.setCellFactory(param -> new ListCell<service>() {
                @Override
                protected void updateItem(service item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null); // Assurez-vous de réinitialiser la représentation graphique de la cellule
                    } else {
                        // Construire une chaîne avec toutes les informations de l'objet service
                        StringBuilder sb = new StringBuilder();
                        sb.append("Name : ").append(item.getName_s()).append("\n")
                                .append("Description : ").append(item.getDescription_s()).append("\n")
                                .append("Location : ").append(item.getLocalisation()).append("\n")
                                .append("State : ").append(item.getState()).append("\n")
                                .append("Availability Date: ").append(item.getDispo_date()).append("\n")
                                .append("Category Id : ").append(item.getCat_id()).append("\n")
                                .append("Image : ").append(item.getImageFile()).append("\n");


                        // Créer une ImageView pour afficher l'image à partir du chemin d'accès
                        ImageView imageView = new ImageView();
                        imageView.setFitWidth(50); // Définissez la largeur de l'ImageView selon vos besoins
                        imageView.setFitHeight(50); // Définissez la hauteur de l'ImageView selon vos besoins

                        // Chargement de l'image à partir du chemin d'accès
                        try {
                            Image image = new Image(new FileInputStream(item.getImageFile())); // item.getImageFile() est le chemin d'accès à l'image
                            imageView.setImage(image);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        // Afficher la chaîne et l'ImageView dans la cellule de la ListView
                        setText(sb.toString());
                        setGraphic(imageView);
                    }
                }
            });

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }




    void setData(String param) {
        welcomeLBL.setText("Welcome " + param);
    }

}
*/

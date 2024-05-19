package tn.esprit;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Destination;
import tn.esprit.models.Reservation;
import tn.esprit.services.DestinationService;
import tn.esprit.services.ReservationService;
import tn.esprit.util.MyConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.IOException;
import java.time.LocalDate;

public class Main extends Application {
    public static void main(String[] args) {

        MyConnection connection = MyConnection.getInstance();
        ReservationService reservationService = new ReservationService();
        IService ps = new DestinationService();
       /* Destination dest = new Destination(7,"name","img",  "state",  "address",  15,  "description");
        Reservation reservation = new Reservation(
                // Reservation ID
                dest, // Destination object
                LocalDate.now(), // Start date (replace with your actual start date)
                LocalDate.now().plusDays(3), // End date (replace with your actual end date)
                200.0 // Total price (replace with your actual total price)
        );        reservationService.addReservation(reservation);

    */}
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/backoffice/destinationtable.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Ecoroam");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


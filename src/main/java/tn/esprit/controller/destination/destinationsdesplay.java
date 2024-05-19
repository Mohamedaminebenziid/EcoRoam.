package tn.esprit.controller.destination;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import tn.esprit.models.Destination;
import tn.esprit.services.DestinationService;
import javafx.scene.layout.HBox; // Import HBox

import java.io.IOException;
import java.util.List;

public class destinationsdesplay {




        @FXML
        private AnchorPane menu_form;

        @FXML
        private GridPane menu_gridpane;

        @FXML
        private ScrollPane menu_scrollpane;

        @FXML
        private void initialize() {
                // Load destination data
                DestinationService destinationService = new DestinationService();
                List<Destination> destinations = destinationService.getallDestination();

                // Create a VBox to contain the destination cards
                VBox cardContainer = new VBox();
                cardContainer.setSpacing(20); // Set vertical spacing between rows of cards

                // Dynamically create and add destination cards
                HBox row = new HBox(); // Create a new HBox for each row of cards
                int cardsInRow = 0;
                row.setSpacing(20);
                for (Destination destination : destinations) {
                        try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontoffice/destinationcard.fxml"));
                                AnchorPane card = loader.load();

                                // Get the controller and set destination details
                                destinationcard controller = loader.getController();
                                controller.setDestination(destination);

                                // Add the card to the current row
                                row.getChildren().add(card);
                                cardsInRow++;

                                // If we have added three cards, start a new row
                                if (cardsInRow == 3) {
                                        cardContainer.getChildren().add(row); // Add the completed row to the cardContainer
                                        row = new HBox(); // Create a new row
                                        cardsInRow = 0; // Reset the count
                                }
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                }

                // If there are any remaining cards in the last row, add the row to the cardContainer
                if (!row.getChildren().isEmpty()) {
                        cardContainer.getChildren().add(row);
                }

                // Set the VBox as the content of the ScrollPane
                menu_scrollpane.setContent(cardContainer);
        }



}



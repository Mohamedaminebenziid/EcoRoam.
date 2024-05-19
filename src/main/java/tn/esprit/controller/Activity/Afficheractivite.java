package tn.esprit.controller.Activity;

import tn.esprit.models.Activities;
import tn.esprit.services.ActivitiesService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class Afficheractivite {

    @FXML
    private ListView<HBox> listView;

    @FXML
    private Button deleteSelectedButton;

    private final ActivitiesService AS = new ActivitiesService();

    private ObservableList<HBox> items;

    @FXML
    void initialize() {
        // Initialize the items list
        items = FXCollections.observableArrayList();

        try {
            List<Activities> activities = AS.getAll();

            if (activities.isEmpty()) {
                // Handle empty data scenario
                // You might want to display a message or disable the ListView
                return;
            }

            for (Activities activity : activities) {
                HBox hbox = createItemBox(activity);
                items.add(hbox);
            }

            listView.setItems(items);
        } catch (SQLException e) {
            // Handle SQLException
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML
    void deleteSelectedItems(ActionEvent event) {
        try {
            // Count the number of selected checkboxes
            int selectedCount = 0;
            for (HBox hbox : items) {
                VBox vbox = (VBox) hbox.getChildren().get(0);
                CheckBox checkBox = (CheckBox) vbox.getChildren().get(0);
                if (checkBox.isSelected()) {
                    selectedCount++;
                }
            }

            // If more than one checkbox is selected, proceed with deletion
            if (selectedCount > 1) {
                Iterator<HBox> iterator = items.iterator();
                while (iterator.hasNext()) {
                    HBox hbox = iterator.next();
                    VBox vbox = (VBox) hbox.getChildren().get(0);
                    CheckBox checkBox = (CheckBox) vbox.getChildren().get(0);
                    if (checkBox.isSelected()) {
                        iterator.remove();
                        // Perform deletion operation here
                        Activities activity = (Activities) hbox.getUserData();
                        if (AS.delete(activity.getId())) {
                            showAlert(Alert.AlertType.INFORMATION, "Success", "Activity deleted successfully.");
                        } else {
                            showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete activity. Activity not found.");
                        }
                    }
                }
            } else {
                // Show alert that more than one checkbox needs to be selected
                showAlert(Alert.AlertType.WARNING, "Warning", "Please select more than one item to delete.");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error occurred while deleting activity: " + e.getMessage());
        }
    }



    // Inside the Afficheractivite class
    private HBox createItemBox(Activities activity) {
        HBox hbox = new HBox();
        hbox.setSpacing(10); // Set spacing between elements

        // Create a VBox to contain the name, description, state, and price
        VBox vbox = new VBox();
        vbox.setSpacing(5); // Set spacing between labels
        vbox.setPadding(new Insets(5)); // Set padding for the VBox

        // Create labels to display activity information
        Label nameLabel = new Label("Name: " + activity.getName());
        Label descriptionLabel = new Label("Description: " + activity.getDescription());
        Label stateLabel = new Label("State: " + activity.getState());
        Label priceLabel = new Label("Price: " + activity.getPrice());

        // Create checkbox
        CheckBox checkBox = new CheckBox();
        checkBox.setOnAction(this::handleCheckBoxSelection);

        // Create buttons with icons for delete and update
        Button deleteButton = createIconButton("delete_icon.png");
        deleteButton.setOnAction(event -> deleteUpdate(event, activity));

        Button updateButton = createIconButton("update_icon.png");
        updateButton.setOnAction(event -> updateActivity(event, activity));

        // Add labels and buttons to the VBox
        vbox.getChildren().addAll(checkBox, nameLabel, descriptionLabel, stateLabel, priceLabel);

        // Add the VBox and buttons to the HBox
        hbox.getChildren().addAll(vbox, deleteButton, updateButton);

        return hbox;
    }


    private Button createIconButton(String imageName) {
        String imagePath = "file:///C:/Users/tpc/Desktop/imgjava/" + imageName; // Construct the full file path
        Image image = new Image(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(60); // Set width of the icon
        imageView.setFitHeight(60); // Set height of the icon

        Button button = new Button();
        button.setGraphic(imageView); // Set the icon as the button's graphic
        button.setStyle("-fx-background-color: transparent;"); // Make the button background transparent

        return button;
    }

    @FXML
    void updateActivity(ActionEvent event, Activities activity) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/backoffice/act/deleteupdateact.fxml"));
            Parent root = loader.load();
            Deleteupdateact controller = loader.getController();
            controller.initData(activity);

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML
    void deleteUpdate(ActionEvent event, Activities activity) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/backoffice/act/deleteupdateact.fxml"));
            Parent root = loader.load();
            Deleteupdateact controller = loader.getController();
            controller.initData(activity);

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
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

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void goToAddActivity(ActionEvent event) {
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

    // Add this method to handle checkbox selection/deselection
    @FXML
    void handleCheckBoxSelection(ActionEvent event) {
        // Count the number of selected checkboxes
        int selectedCount = 0;
        for (HBox hbox : items) {
            VBox vbox = (VBox) hbox.getChildren().get(0);
            CheckBox checkBox = (CheckBox) vbox.getChildren().get(0);
            if (checkBox.isSelected()) {
                selectedCount++;
            }
        }

        // Enable deleteSelectedButton if more than one checkbox is selected
        deleteSelectedButton.setDisable(selectedCount <= 1);
    }

    @FXML
    void viewStatistics(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/backoffice/act/ActivityStatistics.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            ActivityStatisticsController controller = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    private void generatePDF() throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        float yPos = 700f; // Initialize y-position

        // Add image to the right top corner
        PDImageXObject logoImage = PDImageXObject.createFromFile("C:/Users/tpc/Desktop/imgjava/logo-black.png", document);
        contentStream.drawImage(logoImage, page.getMediaBox().getWidth() - 100, page.getMediaBox().getHeight() - 100, 100, 100);

        for (HBox hbox : listView.getItems()) {
            VBox vbox = (VBox) hbox.getChildren().get(0);
            Label nameLabel = (Label) vbox.getChildren().get(1);
            Label descriptionLabel = (Label) vbox.getChildren().get(2);
            Label stateLabel = (Label) vbox.getChildren().get(3);
            Label priceLabel = (Label) vbox.getChildren().get(4);

            // Add Name field
            contentStream.beginText();
            contentStream.newLineAtOffset(100, yPos);
            contentStream.showText(nameLabel.getText());
            contentStream.endText();

            // Add Description field with word wrapping
            String description = descriptionLabel.getText();
            float textWidth = PDType1Font.HELVETICA.getStringWidth(description) / 1000 * 12;
            int numberOfLines = (int) Math.ceil(textWidth / 300); // Adjust based on page width
            String[] descriptionLines = wordWrap(description, 300); // Adjust based on page width
            for (int i = 0; i < numberOfLines; i++) {
                contentStream.beginText();
                contentStream.newLineAtOffset(100, yPos - (i + 1) * 15); // Move to the next line
                contentStream.showText(descriptionLines[i]);
                contentStream.endText();
            }

            // Add State field
            contentStream.beginText();
            contentStream.newLineAtOffset(100, yPos - (numberOfLines + 1) * 15); // Move to the next line
            contentStream.showText(stateLabel.getText());
            contentStream.endText();

            // Add Price field
            contentStream.beginText();
            contentStream.newLineAtOffset(100, yPos - (numberOfLines + 2) * 15); // Move to the next line
            contentStream.showText(priceLabel.getText());
            contentStream.endText();

            // Draw a solid line after the Price
            contentStream.moveTo(100, yPos - (numberOfLines + 3) * 15); // Move to the starting point for the line
            contentStream.lineTo(400, yPos - (numberOfLines + 3) * 15); // Draw a line
            contentStream.stroke();

            yPos -= (numberOfLines + 4) * 15; // Decrease y-position for the next item
        }

        contentStream.close();
        document.save("C:/Users/tpc/Desktop/pdf pi/ListViewContent.pdf");
        document.close();
    }

    private String[] wordWrap(String text, float width) throws IOException {
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        StringBuilder wrappedText = new StringBuilder();

        for (String word : words) {
            if (PDType1Font.HELVETICA.getStringWidth(line.toString() + " " + word) / 1000 * 12 > width) {
                wrappedText.append(line).append("\n");
                line = new StringBuilder();
            }
            line.append(word).append(" ");
        }

        wrappedText.append(line);
        return wrappedText.toString().split("\n");
    }


    @FXML
    void downloadAsPDF(ActionEvent event) {
        try {
            // Call the generatePDF method to create the PDF with ListView content
            generatePDF();

            showAlert(Alert.AlertType.INFORMATION, "Success", "PDF saved successfully.");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to save PDF: " + e.getMessage());
        }
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


}

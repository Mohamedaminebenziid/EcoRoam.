package tn.esprit.controller.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import tn.esprit.models.user;
import tn.esprit.services.UserCrud;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class afficherusercontroller  {

    @FXML
    private TextField usernametextfiled;

    @FXML
    private Button back;

    @FXML
    private Button ban;

    @FXML
    private Button excel;

    @FXML
    private TableColumn<user, Boolean> IS_banned;

    @FXML
    private AnchorPane tableauafficher;

    @FXML
    private TableColumn<user, String> Role;

    @FXML
    private TableColumn<user, String> email;

    @FXML
    private TableColumn<user, Integer> id;

    @FXML
    private TableColumn<user, String> username;

    @FXML
    private TableColumn<user, String> password;

    @FXML
    private TableColumn<user, Integer> tel_number;

    @FXML
    private Button supprimer;



    @FXML
    private TableView<user> tableview;

    private final UserCrud ps = new UserCrud();

    @FXML
    void initialize() {

        try {
            List<user> users = ps.afficherUser();
            ObservableList<user> userList = FXCollections.observableArrayList(users);
            tableview.setItems(userList);

            username.setCellValueFactory(new PropertyValueFactory<>("username"));
            email.setCellValueFactory(new PropertyValueFactory<>("email"));
            tel_number.setCellValueFactory(new PropertyValueFactory<>("tel_number"));
            Role.setCellValueFactory(new PropertyValueFactory<>("roles"));
            IS_banned.setCellValueFactory(new PropertyValueFactory<>("is_banned"));
        } catch (SQLException e) {
            // Handle the SQLException here
            e.printStackTrace(); // You can change this to appropriate error handling
        }
    }




    @FXML
    void excel(ActionEvent event) throws SQLException {
        List<user> users = ps.afficherUser();
        ExcelService excelService = new ExcelService();
        excelService.generateExcel(users);
    }

    @FXML
    void supprimer(ActionEvent event) {
        user selectedItem = tableview.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                int userId = selectedItem.getId();
                ps.supprimer(userId);
                tableview.getItems().remove(selectedItem);
                showAlert(Alert.AlertType.INFORMATION, "Success", "User deleted successfully.");
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete user: " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select a user to delete.");
        }
    }

    @FXML
    void ban(ActionEvent event) {
        // Implementation de la méthode ban
    }

    @FXML
    void back(ActionEvent event) {
        // Implementation de la méthode back
    }

    @FXML
    void naviguer(ActionEvent event) {
        // Implementation de la méthode naviguer
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

 /*   private void filterTableData(String filter) {
        if (filter == null || filter.isEmpty()) {
            try {
                loadTableData();  // Recharger toutes les données si le filtre est effacé
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to load table data: " + e.getMessage());
            }
            return;
        }

        try {
            List<user> allUsers = ps.afficherUser();
            ArrayList<user> filteredUsers = new ArrayList<>();

            String lowerCaseFilter = filter.toLowerCase();

            for (user user : allUsers) {
                if (user.getEmail().toLowerCase().contains(lowerCaseFilter) ||
                        user.getUsername().toLowerCase().contains(lowerCaseFilter) ||
                        String.valueOf(user.getTelNumber()).contains(lowerCaseFilter) ||
                        (user.getresetToken() != null && user.getresetToken().toLowerCase().contains(lowerCaseFilter)) ||
                        String.valueOf(user.isVerified()).toLowerCase().contains(lowerCaseFilter) ||
                        String.valueOf(user.isBanned()).toLowerCase().contains(lowerCaseFilter) ||
                        user.getRole().toLowerCase().contains(lowerCaseFilter) ||
                        user.getPassword().toLowerCase().contains(lowerCaseFilter)) {
                    filteredUsers.add(user);
                }
            }

            tableview.setItems(FXCollections.observableArrayList(filteredUsers)); // Mettre à jour la vue du tableau
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de base de données");
            alert.setContentText("Erreur lors de l'accès à la base de données pour le filtrage : " + e.getMessage());
            alert.showAndWait();
        }
    }*/
}

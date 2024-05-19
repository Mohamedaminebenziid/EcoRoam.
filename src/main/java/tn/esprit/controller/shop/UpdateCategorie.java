package tn.esprit.controller.shop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Categories;
import tn.esprit.services.ServiceCategorie;

import java.sql.SQLException;

public class UpdateCategorie {
    @FXML
    private Button cancel_btn;

    @FXML
    private TextField intituleField;

    @FXML
    private Button update_btn;
    private Categories categories;
    public  void  setCategories(Categories categorie){
        this.categories = categorie;
        intituleField.setText(categorie.getIntitule());
    }
    @FXML
    private void handleUpdateButtonClick(ActionEvent event) throws SQLException {
        String newIntitule = intituleField.getText();

        // Vérifier si l'intitulé ne contient que des chiffres
        if (newIntitule.matches("^\\d+$")) {
            // Afficher une alerte pour informer l'utilisateur que l'intitulé ne peut pas être composé uniquement de chiffres
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Intitulé invalide");
            alert.setHeaderText(null);
            alert.setContentText("L'intitulé ne peut pas être composé uniquement de chiffres. Veuillez entrer un intitulé valide.");
            alert.showAndWait();
            return;
        }

        // Vérifier si un intitulé similaire existe déjà dans la base de données
        ServiceCategorie categorieService = new ServiceCategorie();
        if (categorieService.intituleExists(newIntitule)) {
            // Afficher une alerte pour informer l'utilisateur que l'intitulé existe déjà
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Intitulé déjà existant");
            alert.setHeaderText(null);
            alert.setContentText("L'intitulé spécifié existe déjà. Veuillez en choisir un autre.");
            alert.showAndWait();
            return;
        }

        // Si la catégorie existe et que l'intitulé est unique, procéder à la mise à jour
        if (categories != null) {
            categories.setIntitule(newIntitule);

            // Modifier la catégorie dans la base de données
            categorieService.modifier(categories);

            // Fermer la fenêtre
            Stage stage = (Stage) update_btn.getScene().getWindow();
            stage.close();
        } else {
            System.out.println("Aucune Catégorie sélectionnée pour la mise à jour.");
        }
    }

    @FXML
    private void handleClearButtonClick(ActionEvent event) throws SQLException {
        Stage stage = (Stage) cancel_btn.getScene().getWindow();
        stage.close();

    }
}

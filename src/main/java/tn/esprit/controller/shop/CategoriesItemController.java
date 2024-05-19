package tn.esprit.controller.shop;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tn.esprit.models.Categories;
import tn.esprit.services.ServiceCategorie;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

public class CategoriesItemController implements Initializable {
    ServiceCategorie categorieService = new ServiceCategorie();
    Set<Categories> categorieSet =  categorieService.getAll();
    @FXML
    private Label id_Categories;

    @FXML
    private Label intitule_Categorie;
    private Categories categories;

    public CategoriesItemController() throws SQLException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void  setData(Categories categories, int Count)
    {

        this.categories = categories;
        id_Categories.setText(String.valueOf(Count));
        intitule_Categorie.setText(categories.getIntitule());

    } @FXML
    private void handleDeleteButtonClick() {
        // Obtenez le numéro de ligne depuis votre champ id_Categories
        String rowNumber = id_Categories.getText();
        int categorieIndex = Integer.parseInt(rowNumber) - 1; // Convertissez le numéro de ligne en index de liste (soustrayez 1 car les indices commencent à 0)

        if (categorieIndex >= 0 && categorieIndex < categorieSet.size()) {
            // Récupérez la catégorie correspondante à l'index de liste
            Categories selectedCategorie = categorieSet.stream().skip(categorieIndex).findFirst().orElse(null);

            if (selectedCategorie != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Supprimer la catégorie ?");
                alert.setContentText("Êtes-vous sûr de vouloir supprimer "+selectedCategorie.getIntitule());
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    try {
                        // Supprimer la catégorie en utilisant son identifiant
                        categorieService.supprimer(selectedCategorie.getId());
                        // Actualiser l'affichage après la suppression
                        System.out.println("supprimé.");

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("Numéro de ligne invalide.");
        }
    }

    @FXML
    private void handleUpdateButtonClick() {
        // Vérifiez si une catégorie est sélectionnée
        String rowNumber = id_Categories.getText(); // Obtenez le numéro de ligne depuis votre champ id_Categories
        int categoryIndex = Integer.parseInt(rowNumber) - 1; // Convertissez le numéro de ligne en index de liste (soustrayez 1 car les indices commencent à 0)

        if (categoryIndex >= 0 && categoryIndex < categorieSet.size()) {
            // Récupérez la catégorie correspondant à l'index de liste
            Categories selectedCategory = null;
            int currentIndex = 0;
            for (Categories categorie : categorieSet) {
                if (currentIndex == categoryIndex) {
                    selectedCategory = categorie;
                    break;
                }
                currentIndex++;
            }

            if (selectedCategory != null) {
                try {
                    // Chargez le fichier FXML pour afficher la fenêtre de mise à jour de la catégorie
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/projet/updateCategorie.fxml"));
                    Parent updateCategoryParent = loader.load();

                    // Obtenez le contrôleur de la vue de mise à jour de la catégorie
                    UpdateCategorie updateCategoryController = loader.getController();

                    // Passez la catégorie sélectionnée au contrôleur de mise à jour de la catégorie
                    updateCategoryController.setCategories(selectedCategory);

                    // Créez une nouvelle fenêtre de dialogue pour la mise à jour de la catégorie
                    Stage updateCategoryStage = new Stage();
                    updateCategoryStage.initModality(Modality.APPLICATION_MODAL);
                    updateCategoryStage.setTitle("Mise à jour de la catégorie");
                    updateCategoryStage.setScene(new Scene(updateCategoryParent));
                    updateCategoryStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Aucune catégorie sélectionnée.");
            }
        } else {
            System.out.println("Numéro de ligne invalide.");
        }
    }
}

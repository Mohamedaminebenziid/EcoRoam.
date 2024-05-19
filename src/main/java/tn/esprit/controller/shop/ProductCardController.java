package tn.esprit.controller.shop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tn.esprit.models.Categories;
import tn.esprit.models.Produit;
import tn.esprit.services.ServiceCategorie;
import tn.esprit.services.ServiceProduit;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

public class ProductCardController implements Initializable {
    @FXML
    private Label row_intitule;
    private Produit produit;
    @FXML
    private Label row_count;
    ServiceProduit produitService= new ServiceProduit();
    Set<Produit> produitSetSet =  produitService.getAll();
    @FXML
    private Button delete_btn;

    @FXML
    private Label row_categorie;

    @FXML
    private Label row_description;

    @FXML
    private ImageView row_image;


    @FXML
    private Label row_prix;

    @FXML
    private Label row_stock;

    @FXML
    private Button update_btn;


    public ProductCardController() throws SQLException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setProduitData(Produit produit, int number) {

        this.produit = produit;
        row_count.setText(String.valueOf(number));
        row_intitule.setText(produit.getIntitule());
        if (produit.getImage() != null) {
            String imagePath = "C:\\PIDEV\\productImages\\" + produit.getImage();
            Image image = new Image("file:///" + imagePath);
            if (image != null) {
                row_image.setImage(image);

            }
        }
        row_description.setText(produit.getDescription());

        ServiceCategorie categorieService = new ServiceCategorie();
        try {
            Categories categorie = categorieService.getOneById(produit.getCategorie_id());
            if (categorie != null) {
                row_categorie.setText(categorie.getIntitule());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        row_prix.setText(String.valueOf(produit.getPrix()));
        row_stock.setText(String.valueOf(produit.getStock()));

    }
    @FXML
    private void handleDeleteButtonClick() {
        // Obtenez le numéro de ligne depuis votre champ row_count
        String rowNumber = row_count.getText();
        int categorieIndex = Integer.parseInt(rowNumber) - 1; // Convertissez le numéro de ligne en index de liste (soustrayez 1 car les indices commencent à 0)

        if (categorieIndex >= 0 && categorieIndex < produitSetSet.size()) {
            // Récupérez la catégorie correspondante à l'index de liste
            Produit selectedProduct = produitSetSet.stream().skip(categorieIndex).findFirst().orElse(null);

            if (selectedProduct != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Supprimer Ce produit ?");
                alert.setContentText("Êtes-vous sûr de vouloir supprimer "+selectedProduct.getIntitule());
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    try {
                        // Supprimer la catégorie en utilisant son identifiant
                        produitService.supprimer(selectedProduct.getId());
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
        String rowNumber = row_count.getText(); // Obtenez le numéro de ligne depuis votre champ row_count
        int categoryIndex = Integer.parseInt(rowNumber) - 1; // Convertissez le numéro de ligne en index de liste (soustrayez 1 car les indices commencent à 0)

        if (categoryIndex >= 0 && categoryIndex < produitSetSet.size()) {
            // Récupérez la catégorie correspondant à l'index de liste
            Produit selectedProduct = null;
            int currentIndex = 0;
            for (Produit produit : produitSetSet) {
                if (currentIndex == categoryIndex) {
                    selectedProduct = produit;
                    break;
                }
                currentIndex++;
            }

            if (selectedProduct != null) {
                try {
                    // Chargez le fichier FXML pour afficher la fenêtre de mise à jour de la catégorie
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/projet/updateProduit.fxml"));
                    Parent updateCategoryParent = loader.load();

                    // Obtenez le contrôleur de la vue de mise à jour de la catégorie
                    UpdateProductController updateProductController = loader.getController();

                    // Passez la catégorie sélectionnée au contrôleur de mise à jour de la catégorie
                    updateProductController.setProduct(selectedProduct);

                    // Créez une nouvelle fenêtre de dialogue pour la mise à jour de la catégorie
                    Stage updateCategoryStage = new Stage();
                    updateCategoryStage.initModality(Modality.APPLICATION_MODAL);
                    updateCategoryStage.setTitle("Mise à jour du produit");
                    updateCategoryStage.setScene(new Scene(updateCategoryParent));
                    updateCategoryStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Aucun produit sélectionné.");
            }
        } else {
            System.out.println("Numéro de ligne invalide.");
        }
    }

}


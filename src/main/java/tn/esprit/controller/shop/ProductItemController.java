package tn.esprit.controller.shop;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.models.Produit;
import tn.esprit.services.CartManager;

import java.net.URL;
import java.util.ResourceBundle;

import static tn.esprit.services.CartManager.addToCart;
import static tn.esprit.services.CartManager.isProductInCart;


public class ProductItemController implements Initializable {

    @FXML
    private Button addToCart_btn;

    @FXML
    private ImageView imageField;

    @FXML
    private Label intutileProduitField;

    @FXML
    private Label prixFied;
    private Produit produit;
    @FXML
    private Spinner<Integer> quantiteField;
    private  static String productImagepath;
    private Image image;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0);
        quantiteField.setValueFactory(valueFactory);
    }
    public void setProduit(Produit produit) {
        this.produit = produit;
        if (produit != null) {
            // Afficher les détails du produit
            intutileProduitField.setText(produit.getIntitule());
            prixFied.setText(String.valueOf(produit.getPrix()));

            // Limiter le Spinner à ne pas dépasser le stock du produit
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, produit.getStock(), 0);
            quantiteField.setValueFactory(valueFactory);
            // Charger et afficher l'image du produit
            if (produit.getImage() != null) {
                String imagePath = "C:\\PIDEV\\productImages\\" + produit.getImage();
                Image image = new Image("file:///" + imagePath);
                imageField.setImage(image);
            }
        }
    }

    @FXML
    void addToCartClicked(ActionEvent event) {
        if (produit != null) {
            // Récupérer la quantité sélectionnée dans le Spinner
            int quantite = quantiteField.getValue();

            // Vérifier si la quantité sélectionnée est valide
            if (quantite > 0 && quantite <= produit.getStock()) {
                // Vérifier si le produit existe déjà dans le panier
                if (!isProductInCart(produit)) {
                    // Ajouter le produit au panier
                    addToCart(produit, quantite);

                    // Mettre à jour l'affichage du nombre d'articles dans le panier
                    updateCartItemCountLabel();

                    // Afficher un message de succès
                    showAlert(Alert.AlertType.INFORMATION, "Produit ajouté au panier",
                            "Le produit a été ajouté au panier avec succès.");
                } else {
                    // Afficher un message d'erreur si le produit existe déjà dans le panier
                    showAlert(Alert.AlertType.ERROR, "Produit déjà dans le panier",
                            "Le produit sélectionné existe déjà dans le panier.");
                }
            } else {
                // Afficher un message d'erreur si la quantité sélectionnée n'est pas valide
                showAlert(Alert.AlertType.ERROR, "Quantité invalide",
                        "La quantité sélectionnée n'est pas valide.");
            }
        }
    }

    private void updateCartItemCountLabel() {
        // Mettre à jour le label pour afficher le nombre d'articles dans le panier
        int count = CartManager.getCartItems().size();
        // cartItemCountLabel.setText(Integer.toString(count));
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}


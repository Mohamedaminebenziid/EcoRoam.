package tn.esprit.controller.shop;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.billingportal.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import tn.esprit.models.CartItem;
import tn.esprit.models.Produit;


import tn.esprit.services.CartManager;
import tn.esprit.services.ServiceProduit;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;

import static com.sun.glass.ui.Cursor.setVisible;

public class FrontController implements Initializable {

    //********************************************** GESTION DU PRODUIT******************************
    @FXML
    private TableView<CartItem> cartTableView;

    @FXML
    private TableColumn<CartItem, String> cart_product_intutile;

    @FXML
    private TableColumn<CartItem, Double> cart_product_price;

    @FXML
    private TableColumn<CartItem, Integer> cart_product_quantity;
    @FXML
    private GridPane productItemLayout;
    @FXML
    private TextField search_product;
    @FXML
    private Button payage,valide;

    @FXML
    private AnchorPane boutique_page, paiement;

    @FXML
    private TextField cardNumber;

    @FXML
    private TextField mois;

    @FXML
    private TextField annee;

    @FXML
    private TextField ccv;

    @FXML
    void swichtForm(ActionEvent event) {

        if (event.getSource() == payage  ) {
            paiement.setVisible(true);
            boutique_page.setVisible(false);


        } else if (event.getSource() == valide  ) {
            paiement.setVisible(false);
            boutique_page.setVisible(true);


        }
    }
    private boolean validateCardNumber() {
        String cardNumberText = cardNumber.getText().trim();
        return Pattern.matches("^\\d{16}$", cardNumberText); // Vérifie si le numéro de carte contient exactement 16 chiffres
    }

    // Méthode pour valider la saisie du mois d'expiration
    private boolean validateMonth() {
        String monthText = mois.getText().trim();
        int month = Integer.parseInt(monthText);
        return month >= 1 && month <= 12; // Vérifie si le mois est compris entre 1 et 12
    }

    // Méthode pour valider la saisie de l'année d'expiration
    private boolean validateYear() {
        String yearText = annee.getText().trim();
        int year = Integer.parseInt(yearText);
        return year >= 2024 && year <= 2100; // Vérifie si l'année est comprise entre 2023 et 2100
    }

    // Méthode pour valider la saisie du code CCV
    private boolean validateCCV() {
        String ccvText = ccv.getText().trim();
        return Pattern.matches("^\\d{3}$", ccvText); // Vérifie si le CCV contient exactement 3 chiffres
    }

    private void afficherProduits() {
        try {
            // Récupérer tous les produits depuis le service
            ServiceProduit produitService = new ServiceProduit();
            Set<Produit> produits = produitService.getAll();

            // Charger le fichier FXML pour chaque produit et l'ajouter à la grille
            int columnIndex = 0;
            int rowIndex = 0;

            // Créer le conteneur de produits avec un espace horizontal et vertical

            productItemLayout.setHgap(20); // Espace horizontal entre les produits
            productItemLayout.setVgap(20); // Espace vertical entre les produits

            for (Produit produit : produits) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/projet/productItem.fxml"));
                AnchorPane productItem = loader.load();

                // Accéder au contrôleur du fichier FXML chargé
                ProductItemController productItemController = loader.getController();
                // Passer le produit au contrôleur du produit
                productItemController.setProduit(produit);

                productItemLayout.add(productItem, columnIndex, rowIndex);
                columnIndex++;
                if (columnIndex == 3) {
                    columnIndex = 0;
                    rowIndex++;
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    private void searchProduct(String searchTerm) {
        // Effacer les produits précédemment affichés
        productItemLayout.getChildren().clear();

        try {
            // Récupérer tous les produits depuis le service
            ServiceProduit produitService = new ServiceProduit();
            Set<Produit> produits = produitService.getAll();

            // Charger le fichier FXML pour chaque produit correspondant au terme de recherche et l'ajouter à la grille
            int columnIndex = 0;
            int rowIndex = 0;

            // Créer le conteneur de produits avec un espace horizontal et vertical
            productItemLayout.setHgap(20); // Espace horizontal entre les produits
            productItemLayout.setVgap(20); // Espace vertical entre les produits

            for (Produit produit : produits) {
                // Vérifier si le terme de recherche correspond à l'intitulé du produit
                if (produit.getIntitule().toLowerCase().contains(searchTerm.toLowerCase())) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/projet/productItem.fxml"));
                    AnchorPane productItem = loader.load();

                    // Accéder au contrôleur du fichier FXML chargé
                    ProductItemController productItemController = loader.getController();
                    // Passer le produit au contrôleur du produit
                    productItemController.setProduit(produit);

                    productItemLayout.add(productItem, columnIndex, rowIndex);
                    columnIndex++;
                    if (columnIndex == 3) {
                        columnIndex = 0;
                        rowIndex++;
                    }
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onSearch(ActionEvent event) {
        String searchTerm = search_product.getText();
        searchProduct(searchTerm);
    }

    private void initializeSearchListener() {
        search_product.setOnKeyReleased(event -> {
            String searchTerm = search_product.getText();
            searchProduct(searchTerm);
            updateCartTableView();
        });
    }
    private void updateCartTableView() {
        // Efface les éléments existants dans le TableView
        cartTableView.getItems().clear();

        // Récupère les éléments du panier
        List<CartItem> cartItems = CartManager.getCartItems();

        // Ajoute les éléments du panier au TableView
        cartTableView.getItems().addAll(cartItems);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficherProduits();
        initializeSearchListener();
        // Initialise les colonnes du TableView
        cart_product_intutile.setCellValueFactory(new PropertyValueFactory<>("produitIntitule"));
        cart_product_price.setCellValueFactory(new PropertyValueFactory<>("produitPrix"));
        cart_product_quantity.setCellValueFactory(new PropertyValueFactory<>("quantite"));

        // Met à jour le TableView avec les produits du panier
        updateCartTableView();

        paiement.setVisible(false);
        boutique_page.setVisible(true);
    }
@FXML
public void processPayment() {
    // Valider les informations de la carte de crédit
    if (!validateCreditCardInfo()) {
        // Afficher une alerte si les informations de la carte de crédit ne sont pas valides
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez vérifier les informations de votre carte de crédit !");
        alert.showAndWait();
        return;
    }

    // Définir votre clé d'API Stripe
    Stripe.apiKey = "sk_test_51PE9z02NOLGQMWyI8Pu8hPwqEdo3FMg2KX3M3b9chD7CaJmCi5IOtOpozcMtDpA6RsHUJyJiyVCKszK3EgyruD3w000OOvNFY0";

    // Récupérer les éléments actuels du panier
    List<CartItem> cartItems = CartManager.getCartItems();

    // Calculer le montant total du panier en centimes
    int totalEnCentimes = calculateTotal(cartItems);

    // Configurer les paramètres de la charge Stripe
    Map<String, Object> params = new HashMap<>();
    params.put("amount", totalEnCentimes); // Montant en centimes
    params.put("currency", "usd"); // Devise
    params.put("source", "tok_visa"); // Source du paiement (ID du token de la carte)
    params.put("description", "Paiement de test");

    try {
        // Effectuer la charge
        Charge charge = Charge.create(params);
        System.out.println("Paiement réussi, ID : " + charge.getId());
    } catch (StripeException e) {
        // Gérer les erreurs Stripe
        e.printStackTrace();
    }
}

    // Méthode pour valider les informations de la carte de crédit
    private boolean validateCreditCardInfo() {
        return validateCardNumber() && validateMonth() && validateYear() && validateCCV();
    }

    // Méthode pour calculer le montant total du panier en centimes
    private static int calculateTotal(List<CartItem> cartItems) {
        double totalDollar = cartItems.stream()
                .mapToDouble(item -> item.getProduit().getPrix() * item.getQuantite())
                .sum();
        return (int) (totalDollar * 100); // Convertir le montant total en centimes
    }

    // Méthode pour obtenir le taux de change actuel (à implémenter)
    private static double obtenirTauxDeChange() {
        // Implémentez cette méthode pour récupérer le taux de change actuel depuis une source appropriée
        return 2.5; // C'est juste un exemple, vous devez remplacer cela par une logique réelle
    }
    //********************************************** GESTION DU PRODUIT FIN******************************
}

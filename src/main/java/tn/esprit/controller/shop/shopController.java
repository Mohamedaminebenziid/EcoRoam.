package tn.esprit.controller.shop;


import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.Categories;
import tn.esprit.models.Produit;
import tn.esprit.services.ServiceCategorie;
import tn.esprit.services.ServiceProduit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.*;


public class shopController implements Initializable {

    @FXML
    private AnchorPane addProduct_Page;

    @FXML
    private Button addCategorie_btn1;

    @FXML
    private TextField intituleField;

    @FXML
    private Button addProduct_btn;

    @FXML
    private TextField descriptionField;

    @FXML
    private ComboBox<Categories> categorieField;

    @FXML
    private TextField prixField;

    @FXML
    private TextField stockField;

    @FXML
    private ImageView productImage;

    @FXML
    private Button importation_btn;

    @FXML
    private Button display_btn2;

    @FXML
    private Button cancelProducts;

    @FXML
    private AnchorPane displayProduct_Page;

    @FXML
    private Button addProduct_btn1,addProduct_btn12;

    @FXML
    private Button addCategorie_btn2;

    @FXML
    private Button addCategorie_btn21;

    @FXML
    private AnchorPane addCategorie_Page;

    @FXML
    private TextField row_col_id;

    @FXML
    private Button addCategorie;

    @FXML
    private TextField intitule;

    @FXML
    private Button DisplayCategories_btn;

    @FXML
    private AnchorPane displayCategories_Page;

    @FXML
    private Button addCategorie_btn12;

    @FXML
    private VBox categories_display;
    private final ServiceCategorie serviceCategorie; // Injection du service

    public shopController() {
        this.serviceCategorie = new ServiceCategorie(); // Instanciation du service
    }

public void Reset(){
    intitule.setText("");
}


    @FXML
    void back(ActionEvent event) {
        try {
            // Load the FXML file for the update destination page
            Parent root = FXMLLoader.load(getClass().getResource("/backoffice/destinationtable.fxml"));

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

    @FXML
    void ajouterCategorie(ActionEvent event) {
        String intituleV = intitule.getText();
        if (intituleV.isEmpty()) {
            // Afficher une alerte si le champ est vide
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Champ vide");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir un intitulé pour la catégorie !");
            alert.showAndWait();
        } else {
            try {
                // Vérifier si la catégorie existe déjà dans la base de données
                Categories existingCategory = serviceCategorie.getCategoryByName(intituleV);
                if (existingCategory != null) {
                    // Afficher une alerte si la catégorie existe déjà
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Catégorie existante");
                    alert.setHeaderText(null);
                    alert.setContentText("La catégorie que vous essayez d'ajouter existe déjà !");
                    alert.showAndWait();
                } else {
                    Categories categorie = new Categories(intituleV);
                    // Appeler la méthode ajouter du service pour ajouter la nouvelle catégorie
                    serviceCategorie.ajouter(categorie);
                    Reset();
                    // Afficher une alerte pour informer que la catégorie a été ajoutée avec succès
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Catégorie ajoutée");
                    alert.setHeaderText(null);
                    alert.setContentText("La catégorie a été ajoutée avec succès !");
                    alert.showAndWait();
                }
            } catch (SQLException e) {
                // Gérer les exceptions liées à la base de données
                e.printStackTrace();
            }
        }
    }


    @FXML
    void swichtForm(ActionEvent event) {

        if (event.getSource() == addCategorie_btn1 || event.getSource() == addCategorie_btn2 || event.getSource() == addCategorie_btn12) {
            addCategorie_Page.setVisible(true);
            addProduct_Page.setVisible(false);
            displayProduct_Page.setVisible(false);
            displayCategories_Page.setVisible(false);


        } else if (event.getSource() == addProduct_btn1 || event.getSource() == addProduct_btn12) {
            addCategorie_Page.setVisible(false);
            addProduct_Page.setVisible(true);
            displayProduct_Page.setVisible(false);
            displayCategories_Page.setVisible(false);
        } else if (event.getSource() == DisplayCategories_btn) {
            addCategorie_Page.setVisible(false);
            addProduct_Page.setVisible(false);
            displayProduct_Page.setVisible(false);
            displayCategories_Page.setVisible(true);
        } else if (event.getSource() == display_btn2) {
            addCategorie_Page.setVisible(false);
            addProduct_Page.setVisible(false);
            displayProduct_Page.setVisible(true);
            displayCategories_Page.setVisible(false);


        }
    }



    private void listCategories() throws SQLException, IOException {
        ServiceCategorie  serviceCategorie = new ServiceCategorie();
        Set<Categories> CategoriesSet = serviceCategorie.getAll();

        int userCount = 1; // Initialiser le compteur d'utilisateurs

        for (Categories categories : CategoriesSet) {
            CategoriesItemController categoriesItemController = new CategoriesItemController();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projet/CategoriesItem.fxml"));
            loader.setController(categoriesItemController);
            Parent userCard = loader.load();
            categoriesItemController.setData(categories, userCount); // Passer le numéro de l'utilisateur
            categories_display.getChildren().add(userCard);

            userCount++; // Incrémenter le compteur d'utilisateurs
        }
    }
    @FXML
    private VBox product_display;

    private void listProducts() throws SQLException, IOException {
        ServiceProduit  produitService= new ServiceProduit();
        Set<Produit> produitSetSet =  produitService.getAll();
        int Cumpter= 1;
        for (Produit produit : produitSetSet) {
            ProductCardController productCardController = new ProductCardController();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projet/ProductCard.fxml"));
            loader.setController(productCardController);
            Parent Card = loader.load();
            productCardController.setProduitData(produit,Cumpter);
            product_display.getChildren().add(Card);
            Cumpter++;
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addCategorie_Page.setVisible(true);
        addProduct_Page.setVisible(false);
        displayProduct_Page.setVisible(false);
        displayCategories_Page.setVisible(false);
        try {
            listCategories();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Load categories into TableView
//********************************
        // Récupérer toutes les catégories disponibles à partir du service de catégories
        ServiceCategorie categorieService = new ServiceCategorie();
        Set<Categories> categories;
        try {
            categories = categorieService.getAll();
        } catch (SQLException e) {
            // Gérer l'exception en cas d'erreur lors de la récupération des catégories
            e.printStackTrace();
            return;
        }

        // Ajouter toutes les catégories à la ComboBox
        categorieField.getItems().addAll(categories);

        // Définir la première catégorie comme sélection par défaut dans la ComboBox
        if (!categories.isEmpty()) {
            categorieField.setValue(categories.iterator().next());
        }
        try {
            listProducts();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private Image image;
    private boolean estUniquementChiffres(String str) {
        return str.matches("\\d+");
    }

    private  static String productImagepath;
    public void InsertImage() {
        FileChooser open = new FileChooser();
        File file = open.showOpenDialog(addProduct_Page.getScene().getWindow());

        if (file != null) {
            productImagepath = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 101, 127, false, true);
            productImage.setImage(image);
        }
    }
    @FXML
    private void ajouterProduitClicked() {
        String intitule = intituleField.getText();
        String description = descriptionField.getText();
        String prixStr = prixField.getText();
        String stockStr = stockField.getText();
        Categories categorie = categorieField.getValue();

        // Liste pour stocker les messages d'erreur
        List<String> erreurs = new ArrayList<>();

        // Validation de l'intitulé
        if (intitule.isEmpty()) {
            erreurs.add("Le champ intitulé est vide.");
        } else if (estUniquementChiffres(intitule)) {
            erreurs.add("L'intitulé ne doit pas contenir uniquement des chiffres.");
        }

        // Validation de la description
        if (description.isEmpty()) {
            erreurs.add("Le champ description est vide.");
        }

        // Validation du prix
        double prix = 0.0;
        try {
            prix = Double.parseDouble(prixStr);
            if (prix <= 0) {
                erreurs.add("Le champ prix doit être un nombre valide et supérieur à 0.");
            }
        } catch (NumberFormatException e) {
            erreurs.add("Le champ prix doit être un nombre valide.");
        }

        // Validation du stock
        int stock = 0;
        try {
            stock = Integer.parseInt(stockStr);
            if (stock < 1) {
                erreurs.add("Le champ stock doit être un nombre entier valide et supérieur ou égal à 1.");
            }
        } catch (NumberFormatException e) {
            erreurs.add("Le champ stock doit être un nombre entier valide.");
        }

        // Validation de la catégorie
        if (categorie == null) {
            erreurs.add("Veuillez sélectionner une catégorie.");
        }

        // Vérifier s'il y a des erreurs
        if (!erreurs.isEmpty()) {
            // Construction du message d'erreur
            StringBuilder messageErreur = new StringBuilder("Veuillez corriger les erreurs suivantes :\n");
            for (String erreur : erreurs) {
                messageErreur.append("- ").append(erreur).append("\n");
            }

            // Afficher l'alerte avec les messages d'erreur
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs invalides");
            alert.setHeaderText(null);
            alert.setContentText(messageErreur.toString());
            alert.showAndWait();
            return;
        }

        // IMAGE

        String uri = productImagepath;

        // Hasher le mot de passe

        uri = uri.replace("\\", "\\\\");

        // Générer un nom de fichier unique
        String imageFileName = generateUniqueFileName();

        // Enregistrer l'image dans le dossier profile du projet
        String imagePath = "C:\\Users\\tpc\\Desktop\\ya ali 5edma\\EcoroamJAVA\\src\\main\\images" + imageFileName;

        File imageFile = new File(imagePath);

        Produit produit = new Produit(intitule, description, prix, stock, imageFileName, categorie.getId());

        ServiceProduit produitService = new ServiceProduit();
        try {
            produitService.ajouter(produit);
            // Enregistrer l'image sur le disque
            saveImageToDisk(uri, imageFile);
            Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
            confirmation.setTitle("Produit ajouté");
            confirmation.setHeaderText(null);
            confirmation.setContentText("Le produit a été ajouté avec succès.");
            confirmation.showAndWait();
            resetProductFields();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur s'est produite lors de l'ajout du produit. Veuillez réessayer.");
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void resetProductFields() {

        intituleField.setText("");
        descriptionField.setText("");
        prixField.setText("");
        stockField.setText("");
        categorieField.setValue(null);
        productImage.setImage(null);
    }
    @FXML
    private void resetActionProductFields() {

        intituleField.setText("");
        descriptionField.setText("");
        prixField.setText("");
        stockField.setText("");
        categorieField.setValue(null);
        productImage.setImage(null);
    }
    private void saveImageToDisk(String sourceImagePath, File destinationImageFile) throws IOException {
        byte[] imageData = Files.readAllBytes(Paths.get(sourceImagePath));
        try (FileOutputStream fos = new FileOutputStream(destinationImageFile)) {
            fos.write(imageData);
        }
    }

    private String generateUniqueFileName() {
        String uniqueID = UUID.randomUUID().toString(); // Générer un identifiant unique
        return "product_" + uniqueID + ".png"; // Ajouter un préfixe au nom de fichier et utiliser l'extension .png
    }

}
package tn.esprit.controller.shop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.Categories;
import tn.esprit.models.Produit;
import tn.esprit.services.ServiceCategorie;
import tn.esprit.services.ServiceProduit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.*;

public class UpdateProductController {
    @FXML
    private Button cancel_btn;

    @FXML
    private ComboBox<String> categorieField;

    @FXML
    private TextField descriptionField;

    @FXML
    private ImageView imageField;

    @FXML
    private Button import_btn;

    @FXML
    private TextField intituleField;

    @FXML
    private TextField prixField;

    @FXML
    private TextField stockField;

    @FXML
    private Button update_btn;

    @FXML
    private AnchorPane main_form;
    private  Produit produit;
    public void setProduct(Produit produit){
        this.produit = produit;
        intituleField.setText(produit.getIntitule());
        descriptionField.setText(produit.getDescription());
        prixField.setText(String.valueOf(produit.getPrix()));
        stockField.setText(String.valueOf(produit.getStock()));

        // Charger toutes les catégories disponibles
        ServiceCategorie categorieService = new ServiceCategorie();
        Set<Categories> categories = new HashSet<>(); // Utiliser un HashSet pour stocker les catégories

        try {
            categories = categorieService.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Ajouter toutes les catégories à la liste déroulante
        for (Categories categorie : categories) {
            categorieField.getItems().add(categorie.getIntitule());
        }

        // Sélectionner la catégorie du produit par défaut

        for (Categories categorie : categories) {
            if (categorie.getId() == produit.getCategorie_id()) {
                categorieField.setValue(categorie.getIntitule());
                break;
            }
        }


        // Afficher l'image du produit s'il en possède une
        if (produit.getImage() != null) {
            String imagePath = "C:\\PIDEV\\productImages\\" + produit.getImage();
            Image image = new Image("file:///" + imagePath);
            if (image != null) {
                imageField.setImage(image);
            }
        }
    }
    @FXML
    private void handleUpdateButtonClick(ActionEvent event) throws SQLException, IOException {
        if (produit != null) {
            // Récupérer les valeurs des champs
            String newIntitule = intituleField.getText();
            String newDescription = descriptionField.getText();
            String selectedCategorie = categorieField.getValue();
            String prixStr = prixField.getText();
            String stockStr = stockField.getText();

            // Liste pour stocker les messages d'erreur
            List<String> erreurs = new ArrayList<>();

            // Validation de l'intitulé
            if (newIntitule.isEmpty()) {
                erreurs.add("Le champ intitulé est vide.");
            } else if (estUniquementChiffres(newIntitule)) {
                erreurs.add("L'intitulé ne doit pas contenir uniquement des chiffres.");
            }


            // Validation de la description
            if (newDescription.isEmpty()) {
                erreurs.add("Le champ description est vide.");
            }

            // Validation du prix
            double prix = 0.0;
            try {
                prix = Double.parseDouble(prixStr);
                if (prix <= 0) {
                    erreurs.add("Le champ prix doit être un nombre positif.");
                }
            } catch (NumberFormatException e) {
                erreurs.add("Le champ prix doit être un nombre valide.");
            }

            // Validation du stock
            int stock = 0;
            try {
                stock = Integer.parseInt(stockStr);
                if (stock < 1) {
                    erreurs.add("Le champ stock doit être un entier positif.");
                }
            } catch (NumberFormatException e) {
                erreurs.add("Le champ stock doit être un nombre entier valide.");
            }

            // Validation de la catégorie
            if (selectedCategorie == null || selectedCategorie.isEmpty()) {
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

            // Si toutes les validations sont passées avec succès, mettre à jour le produit
            ServiceCategorie categorieService = new ServiceCategorie();
            Categories categorie = categorieService.getOneByIntitule(selectedCategorie);
            if (categorie != null) {
                produit.setIntitule(newIntitule);
                produit.setDescription(newDescription);
                produit.setCategorie_id(categorie.getId());
                produit.setPrix(prix);
                produit.setStock(stock);

                String uri;
                String imageFileName;
                if (productImagepath != null) {
                    uri = productImagepath.replace("\\", "\\\\");
                    imageFileName = generateUniqueFileName();
                } else {
                    // Conserver le chemin de l'ancienne image
                    uri = "C:\\PIDEV\\productImages\\" + produit.getImage();
                    imageFileName = produit.getImage();
                }
                String imagePath = "C:\\PIDEV\\productImages\\" + imageFileName;
                File imageFile = new File(imagePath);
                produit.setImage(imageFileName);
                // Autres modifications éventuelles
                ServiceProduit produitService = new ServiceProduit();
                produitService.modifier(produit);

                // Sauvegarder l'image sur le disque


                saveImageToDisk(uri, imageFile);

                // Fermer la fenêtre
                Stage stage = (Stage) update_btn.getScene().getWindow();
                stage.close();
            } else {
                System.out.println("Catégorie non trouvée.");
            }
        } else {
            System.out.println("Aucun produit sélectionné pour la mise à jour.");
        }
    }
    private boolean estUniquementChiffres(String str) {
        return str.matches("\\d+");
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
    @FXML
    private void handleClearButtonClick(ActionEvent event) throws SQLException {
        Stage stage = (Stage) cancel_btn.getScene().getWindow();
        stage.close();

    }

    private  static String productImagepath;
    private Image image;
    public void InsertImage() {
        FileChooser open = new FileChooser();
        File file = open.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {
            productImagepath = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 101, 127, false, true);
            imageField.setImage(image);
        }
    }
}

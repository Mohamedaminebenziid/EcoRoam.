package tn.esprit.models;

import java.util.Objects;

public class Produit {
    private int id;
    private String intitule;
    private String description;
    private double prix;
    private int stock;
    private String image;
    private int categorie_id;

    // Constructeur
    public Produit(int id, String intitule, String description, double prix, int stock, String image, int categorie_id) {
        this.id = id;
        this.intitule = intitule;
        this.description = description;
        this.prix = prix;
        this.stock = stock;
        this.image = image;
        this.categorie_id = categorie_id;
    }

    public Produit(String intitule, String description, double prix, int stock, String image, int categorie_id) {
        this.intitule = intitule;
        this.description = description;
        this.prix = prix;
        this.stock = stock;
        this.image = image;
        this.categorie_id = categorie_id;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCategorie_id() {
        return categorie_id;
    }

    public void setCategorie_id(int categorie_id) {
        this.categorie_id = categorie_id;
    }

    // Méthode toString pour l'affichage
    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", intitule='" + intitule + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                ", stock=" + stock +
                ", image='" + image + '\'' +
                ", categorie_id=" + categorie_id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produit produit)) return false;
        return getId() == produit.getId() && Double.compare(getPrix(), produit.getPrix()) == 0 && getStock() == produit.getStock() && getCategorie_id() == produit.getCategorie_id() && Objects.equals(getIntitule(), produit.getIntitule()) && Objects.equals(getDescription(), produit.getDescription()) && Objects.equals(getImage(), produit.getImage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getIntitule(), getDescription(), getPrix(), getStock(), getImage(), getCategorie_id());
    }
}

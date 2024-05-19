package tn.esprit.models;

import java.util.Objects;

public class CartItem {
    private Produit produit;
    private int quantite;

    public CartItem(Produit produit, int quantite) {
        this.produit = produit;
        this.quantite = quantite;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public double getProduitPrix() {
        // Retourner le prix du produit
        return produit.getPrix()*getQuantite();
    }
    public String getProduitIntitule() {
        if (produit != null) {
            return produit.getIntitule();
        } else {
            return "";
        }
    }
    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartItem cartItem)) return false;
        return getQuantite() == cartItem.getQuantite() && Objects.equals(getProduit(), cartItem.getProduit());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProduit(), getQuantite());
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "produit=" + produit +
                ", quantite=" + quantite +
                '}';
    }
}

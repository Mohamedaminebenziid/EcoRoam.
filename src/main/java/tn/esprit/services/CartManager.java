package tn.esprit.services;


import tn.esprit.models.CartItem;
import tn.esprit.models.Produit;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static List<CartItem> cartItems = new ArrayList<>();

    public static void addToCart(Produit produit, int quantite) {
        // Vérifier si le produit existe déjà dans le panier
        for (CartItem item : cartItems) {
            if (item.getProduit().equals(produit)) {
                // Si le produit existe déjà, mettre à jour la quantité
                item.setQuantite(item.getQuantite() + quantite);
                return;
            }
        }
        // Si le produit n'existe pas dans le panier, l'ajouter
        cartItems.add(new CartItem(produit, quantite));
    }

    public static List<CartItem> getCartItems() {
        return cartItems;
    }

    public static boolean isProductInCart(Produit produit) {
        // Vérifier si le produit existe déjà dans le panier
        for (CartItem item : cartItems) {
            if (item.getProduit().equals(produit)) {
                return true;
            }
        }
        return false;
    }
}

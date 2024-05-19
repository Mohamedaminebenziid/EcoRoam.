package tn.esprit.services;

import tn.esprit.models.Produit;
import tn.esprit.util.DataSource;
import tn.esprit.util.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ServiceProduit implements Iservice<Produit>{
    Connection connection ;
    public ServiceProduit() {
        connection = MyConnection.getInstance().getCnx();}

    @Override
    public void ajouter(Produit produit) throws SQLException {
        String sql = "INSERT INTO produit (intitule, description, prix, stock, image, categorie_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, produit.getIntitule());
            statement.setString(2, produit.getDescription());
            statement.setDouble(3, produit.getPrix());
            statement.setInt(4, produit.getStock());
            statement.setString(5, produit.getImage());
            statement.setInt(6, produit.getCategorie_id());
            statement.executeUpdate();
        }
    }


    @Override
    public void modifier(Produit produit) throws SQLException {
        String sql = "UPDATE produit SET intitule = ?, description = ?, prix = ?, stock = ?, image = ?, categorie_id = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, produit.getIntitule());
            statement.setString(2, produit.getDescription());
            statement.setDouble(3, produit.getPrix());
            statement.setInt(4, produit.getStock());
            statement.setString(5, produit.getImage());
            statement.setInt(6, produit.getCategorie_id());
            statement.setInt(7, produit.getId());
            statement.executeUpdate();
        }
    }


    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM produit WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }


    @Override
    public Produit getOneById(int id) throws SQLException {
        String sql = "SELECT * FROM produit WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractProduitFromResultSet(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public Set<Produit> getAll() throws SQLException {
        Set<Produit> produits = new HashSet<>();
        String sql = "SELECT * FROM produit ORDER BY id DESC";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String intitule = resultSet.getString("intitule");
                String description = resultSet.getString("description");
                double prix = resultSet.getDouble("prix");
                int stock = resultSet.getInt("stock");
                String image = resultSet.getString("image");
                int categorieId = resultSet.getInt("categorie_id");

                Produit produit = new Produit(id, intitule, description, prix, stock, image, categorieId);
                produits.add(produit);
            }
        }
        return produits;
    }



    private Produit extractProduitFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String intitule = resultSet.getString("intitule");
        String description = resultSet.getString("description");
        double prix = resultSet.getDouble("prix");
        int stock = resultSet.getInt("stock");
        String image = resultSet.getString("image");
        int categorieId = resultSet.getInt("categorie_id");
        return new Produit(id, intitule, description, prix, stock, image, categorieId);
    }

}

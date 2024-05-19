package tn.esprit.services;

import tn.esprit.models.Categories;
import tn.esprit.util.MyConnection;


import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceCategorie implements Iservice<Categories> {

    private Connection connection = MyConnection.getInstance().getCnx();

    public ServiceCategorie() {
        this.connection = connection;
    }

    @Override
    public void ajouter(Categories categorie) throws SQLException {
        String query = "INSERT INTO categorie (intitule) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, categorie.getIntitule());
            statement.executeUpdate();
        }
    }

    @Override
        public void modifier(Categories categorie) throws SQLException {
        String query = "UPDATE categorie SET intitule = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, categorie.getIntitule());
            statement.setInt(2, categorie.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String query = "DELETE FROM categorie WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public Categories getOneById(int id) throws SQLException {
        String query = "SELECT * FROM categorie WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return extractFromResultSet(resultSet);
            }
        }
        return null;
    }


    @Override
    public Set<Categories> getAll() throws SQLException {
        String query = "SELECT * FROM categorie";
        Set<Categories> categorieSet = new HashSet<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String intitule = resultSet.getString("intitule");
                categorieSet.add(extractFromResultSet(resultSet));
            }
        }
        return categorieSet;
    }
    private Categories extractFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String intitule = resultSet.getString("intitule");
        return new Categories(intitule, id);
    }
    public Categories getCategoryByName(String intitule) throws SQLException {
        String query = "SELECT * FROM categorie WHERE intitule = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, intitule);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                return new Categories(intitule, id);
            }
        }
        return null;
    }

    public boolean intituleExists(String intitule) throws SQLException {
        try {
            // Utiliser la méthode getOneByIntitule pour vérifier si une catégorie avec le même intitulé existe
            Categories existingCategorie = getOneByIntitule(intitule);
            // Si existingCategorie n'est pas null, cela signifie qu'une catégorie avec le même intitulé existe déjà
            return existingCategorie != null;
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les exceptions SQLException...
            return false;
        }
    }
    public Categories getOneByIntitule(String intitule) throws SQLException {
        Categories categorie = null;
        String sql = "SELECT * FROM categorie WHERE intitule = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, intitule);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                categorie = extractFromResultSet(resultSet);
            }
        }
        return categorie;
    }

}

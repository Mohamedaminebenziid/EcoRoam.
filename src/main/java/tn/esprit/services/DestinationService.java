package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Destination;
import tn.esprit.util.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DestinationService implements IService<Destination> {

    Connection cnx = MyConnection.getInstance().getCnx();


    // Constructor

    @Override
    public void addDestination(Destination destination) {
        // Implement logic to add a destination to the database
        try {
            PreparedStatement stmt = cnx.prepareStatement("INSERT INTO destination (name, img, state, address, prix, description) VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, destination.getName());
            stmt.setString(2, destination.getImg()); // Set image data as bytes
            stmt.setString(3, destination.getState());
            stmt.setString(4, destination.getAddress());
            stmt.setFloat(5, destination.getPrice());
            stmt.setString(6, destination.getDescription());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }

    @Override
    public void updateDestination(Destination destination) {
        try {
            PreparedStatement stmt = cnx.prepareStatement("UPDATE destination SET name = ?, img = ?, state = ?, address = ?, prix = ?, description = ? WHERE id = ?");
            stmt.setString(1, destination.getName());
            stmt.setString(2, destination.getImg()); // Set image URL or path as String
            stmt.setString(3, destination.getState());
            stmt.setString(4, destination.getAddress());
            stmt.setFloat(5, destination.getPrice());
            stmt.setString(6, destination.getDescription());
            stmt.setInt(7, destination.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }



    @Override
    public List<Destination> getallDestination() {
        List<Destination> destinations = new ArrayList<>();
        try {
            PreparedStatement stmt = cnx.prepareStatement("SELECT * FROM destination");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // Retrieve image URL or path as String
                String imgUrl = rs.getString("img");

                // Create Destination object with image URL or path as String
                Destination destination = new Destination(
                        rs.getInt("id"),
                        rs.getString("name"),
                        imgUrl, // Use String for img URL or path
                        rs.getString("state"),
                        rs.getString("address"),
                        rs.getFloat("prix"),
                        rs.getString("description")
                );
                destinations.add(destination);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }
        return destinations;
    }




    @Override
    public void deleteDestination(int id) {
        // Implement logic to delete a destination from the database
        // You may need to use the destination ID for deletion
        try {
            PreparedStatement stmt = cnx.prepareStatement("DELETE FROM destination WHERE id = ?");
            // Set the destination ID as a parameter in the prepared statement
            stmt.setInt(1,id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }

    @Override
    public Destination getoneDestination(int id) {
        // Implement logic to fetch a specific destination from the database based on its ID
        // Return the destination object
        Destination destination = null;
        try {
            PreparedStatement stmt = cnx.prepareStatement("SELECT * FROM destination WHERE id = ?");
            // Set the destination ID as a parameter in the prepared statement
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                destination = new Destination(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("img"),
                        rs.getString("state"),
                        rs.getString("address"),
                        rs.getFloat("prix"),
                        rs.getString("description")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }
        return destination;
    }
    // Method to check if the destination is set for any reservation
    public boolean isDestinationInReservation(int destinationId) {
        boolean destinationInReservation = false;
        String query = "SELECT COUNT(*) FROM reservation WHERE destination_id = ?";

        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, destinationId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    destinationInReservation = count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        }

        return destinationInReservation;
    }

}

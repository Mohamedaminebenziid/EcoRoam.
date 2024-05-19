package tn.esprit.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class isBanned {
    private Connection connection; // Assuming you have a connection object

    public isBanned(Connection connection) {
        this.connection = connection;
    }

    public void updateActivation(int userId, int newActivationState) throws SQLException {
        try {
            String req = "UPDATE user SET active = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(req);
            statement.setInt(1, newActivationState);
            statement.setInt(2, userId);
            statement.executeUpdate();
            System.out.println("User activation status updated");
        } catch (SQLException ex) {
            System.out.println("Error while updating user activation status: " + ex.getMessage());
            throw ex;
        }
    }
}

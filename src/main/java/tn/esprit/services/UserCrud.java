package tn.esprit.services;

import org.mindrot.jbcrypt.BCrypt;
import tn.esprit.models.user;
import tn.esprit.util.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserCrud {
     static Connection connection;
    private tn.esprit.models.user user;

    public UserCrud(Connection connection) {
        this.connection = MyConnection.getInstance().getCnx();
    }

    public UserCrud() {

    }



    public void updateBannedStatus() throws SQLException {
        updateBannedStatus(0, false);
    }

    public void updateBannedStatus(int userId, boolean isBanned) throws SQLException {
        String req = "UPDATE user SET is_banned=? WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setBoolean(1, isBanned);
        preparedStatement.setInt(2, userId);
        preparedStatement.executeUpdate();
        System.out.println("User banned status updated");
    }



    public void ajouterUser(user user) throws SQLException {
        this.user = user; // Set the user object

        // SQL query to insert user data into the database
        String insertData = "INSERT INTO `user`(`adresse`, `email`, `password`, `username`, `roles`, `tel_number`, `is_banned`) VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(insertData)) {
            // Set values for the prepared statement
            statement.setString(1, user.getAdresse());
            statement.setString(2, user.getEmail());

            // Hash the password before inserting
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            statement.setString(3, hashedPassword);

            statement.setString(4, user.getUsername());
            statement.setString(5, user.getRoles());
            statement.setString(6, user.getTel_number());
            statement.setBoolean(7, user.isIs_banned());

            // Execute the update
            statement.executeUpdate();
        }
    }

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private boolean isHashed(String password) {
        // In Symfony, bcrypt hashes start with "$2y$"
        return password.startsWith("$2y$");
    }

    public boolean isValidLogin(String email, String password) {
        String query = "SELECT * FROM user WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String storedPassword = resultSet.getString("password");
                    if (isHashed(storedPassword)) {
                        // If password is hashed, verify using BCryptPasswordEncoder
                        return passwordEncoder.matches(password, storedPassword);
                    } else {
                        // If not hashed, compare directly
                        return password.equals(storedPassword);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exception appropriately
        }
        return false;
    }


    public user authenticateUser(String email, String password) {
        user user = null; // Initialize user as null
        try {
            String query = "SELECT * FROM `user` WHERE email = ? AND password = ?"; // Query to retrieve user by email and password
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, email); // Set email parameter safely
                preparedStatement.setString(2, password); // Set password parameter safely
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        if (resultSet.getBoolean("activated")) { // Check if the account is activated
                            // Create User object with retrieved user information
                            user = new user(
                                    resultSet.getInt("id"),
                                    "", // Empty string for address (consider retrieving address from the database)
                                    resultSet.getString("email"),
                                    resultSet.getString("username"),
                                    null, // Avoid passing password here
                                    resultSet.getString("roles"),
                                    resultSet.getString("tel_number"),
                                    resultSet.getBoolean("is_banned")

                                    );
                        } else {
                            System.out.println("Le compte est désactivé"); // Account is deactivated
                        }
                    } else {
                        System.out.println("Combinaison email/mot de passe incorrecte"); // Incorrect email/password combination
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e); // Rethrow SQL exceptions as runtime exceptions
        }
        return user; // Return User object (null if authentication fails)
    }





    public void modifierUser(user user) throws SQLException {
        String updateData = "UPDATE `user` SET `adresse`=?, `email`=?, `password`=?, `username`=?, `roles`=?, `tel_number`=?, `is_banned`=? WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(updateData)) {
            statement.setString(1, user.getAdresse());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getUsername());
            statement.setString(5, user.getRoles());
            statement.setString(6, user.getTel_number());
            statement.setBoolean(7, user.isIs_banned());
            statement.setInt(8, user.getId());

            statement.executeUpdate();
        }
    }

    public void supprimer(int userId) throws SQLException {
        String query = "DELETE FROM user WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Utilisateur supprimé avec succès.");
            } else {
                System.out.println("Aucun utilisateur n'a été supprimé. ID d'utilisateur non valide.");
            }
        }
    }


    public static boolean verifCodeAuth(int code, int id) {
        try {
            String req = "SELECT * FROM `user` WHERE idUser = " + id;
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                System.out.println("codeBase "+rs.getInt(12));
                if (rs.getInt(12) == code) {
                    return true;
                } else {
                    return false;

                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public boolean existeEmail() throws SQLException {
        return existeEmail(null);
    }

    public boolean existeEmail(String email) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean existe = false;

        try {
            String query = "SELECT COUNT(*) AS count FROM user WHERE email = ?";
            stmt = connection.prepareStatement(query);
            stmt.setString(1, email);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt("count");
                existe = count > 0;
            }
        } finally {
            // Fermeture des ressources
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }

        return existe;
    }

    public List<user> afficherUser() throws SQLException {
        String sql = "SELECT * FROM user";
        try (Connection connection = MyConnection.getInstance().getCnx();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            List<user> users = new ArrayList<>();
            while (resultSet.next()) {
                user user = new user(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("email"),


                        "",
                        resultSet.getString("password"),
                        resultSet.getString("roles"),
                        resultSet.getString("tel_number"),

                        resultSet.getBoolean("is_banned")

                        );
                users.add(user);
            }
            return users;
        }
    }


    public void setuser(tn.esprit.models.user p) {
    }

    public List<tn.esprit.models.user> getAllUsers() {
        return null;
    }

    public void modifieruser(tn.esprit.models.user modifiedUser) {
    }

    public void modifieruser(int id, String email, String username, String telNumber, String role, String password) {
    }

    public void updatefront(tn.esprit.models.user modifiedUser) {
    }

    public tn.esprit.models.user getUser() {
        return user;
    }

    public void setUser(tn.esprit.models.user user) {
        this.user = user;
    }


    // Other methods omitted for brevity...
}

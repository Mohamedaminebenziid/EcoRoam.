package tn.esprit.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {

    private static final String URL = "jdbc:mysql://localhost:3306/ecoroam";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private static DataSource instance;
    private Connection connection;

    private DataSource() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connexion à la base de données réussie.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        }
    }

    public static synchronized DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connexion à la base de données fermée.");
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de la fermeture de la connexion à la base de données : " + e.getMessage());
            }
        }
    }
}

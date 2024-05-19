package tn.esprit.services;

import tn.esprit.interfaces.IIService;
import tn.esprit.interfaces.IIService;
import tn.esprit.models.Activities;
import tn.esprit.util.MyConnection;

import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ActivitiesService implements IIService<Activities> {

    Connection cnx ;
    public ActivitiesService() {
        cnx = MyConnection.getInstance().getCnx();}




    @Override
    public void add(Activities activities) throws SQLException {
        String qry = "INSERT INTO `activities` (name, img, state, description, price) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = cnx.prepareStatement(qry)) {
            // Set parameter values using setter methods
            statement.setString(1, activities.getName());
            statement.setString(2, activities.getImg());
            statement.setString(3, activities.getState());
            statement.setString(4, activities.getDescription());
            statement.setDouble(5, activities.getPrice());

            // Execute the query
            statement.executeUpdate();
        }

        System.out.println("Activity added successfully");
    }


    @Override
    public void update(Activities activities) throws SQLException {
        String req = "UPDATE activities SET name = ?, img = ?, state = ?, description = ?, price = ? WHERE id = ?";
        PreparedStatement pstmt = cnx.prepareStatement(req); {
            pstmt.setString(1, activities.getName());
            pstmt.setString(2, activities.getImg());
            pstmt.setString(3, activities.getState());
            pstmt.setString(4, activities.getDescription());
            pstmt.setFloat(5, activities.getPrice());
            pstmt.setInt(6, activities.getId());


            pstmt.executeUpdate();
        }

    }

    @Override
    public List<Activities> getAll() throws SQLException {
        List<Activities> activate = new ArrayList<>();
        String req = "SELECT * FROM activities";
        Statement stmt = cnx.createStatement(); {
            ResultSet rs = stmt.executeQuery(req);
            while (rs.next()) {
                Activities activities=new Activities();
                activities.setId(rs.getInt("id"));
                activities.setName(rs.getString("name"));
                activities.setImg(rs.getString("img"));
                activities.setState(rs.getString("state"));
                activities.setDescription(rs.getString("description"));
                activities.setPrice(rs.getFloat("PRICE"));

                activate.add(activities);
            }
        }

        return activate;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String req = "DELETE FROM activities WHERE id = ?";
        try (PreparedStatement pstmt = cnx.prepareStatement(req)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if rows were affected
        }
    }

    public Activities getById(int id) throws SQLException {
        String req = "SELECT * FROM activities WHERE id = ?";
        try (PreparedStatement pstmt = cnx.prepareStatement(req)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Activities activity = new Activities();
                activity.setId(rs.getInt("id"));
                activity.setName(rs.getString("name"));
                activity.setImg(rs.getString("img"));
                activity.setState(rs.getString("state"));
                activity.setDescription(rs.getString("description"));
                activity.setPrice(rs.getFloat("price"));
                return activity;
            }
        }
        return null;
    }
    public Map<String, Integer> getActivityStateStatistics() throws SQLException {
        Map<String, Integer> statistics = new HashMap<>();
        String query = "SELECT state, COUNT(*) AS count FROM activities GROUP BY state";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String state = resultSet.getString("state");
                int count = resultSet.getInt("count");
                statistics.put(state, count);
            }
        }
        return statistics;
    }



}





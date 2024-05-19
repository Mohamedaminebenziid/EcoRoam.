package tn.esprit.services;

import tn.esprit.interfaces.IIService;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Activities;
import tn.esprit.models.Events;
import tn.esprit.util.MyConnection;
import java.util.Date;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventsService implements IIService<Events> {
    Connection cnx = MyConnection.getInstance().getCnx();

    @Override
    public void add(Events events) throws SQLException {
        String qry = "INSERT INTO `events` (name, price, state, img, description) " +
                "VALUES (?, ?, ?, ?, ?)";

        PreparedStatement pstmt = cnx.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, events.getName());
        pstmt.setFloat(2, events.getPrice());
        pstmt.setString(3, events.getState());
        pstmt.setString(4, events.getImg());
        pstmt.setString(5, events.getDescription());

        pstmt.executeUpdate();

        ResultSet generatedKeys = pstmt.getGeneratedKeys();
        if (generatedKeys.next()) {
            events.setId(generatedKeys.getInt(1));
        }

        System.out.println("Event added successfully!");
    }

    @Override
    public void update(Events events) throws SQLException {
        String req = "UPDATE events SET name = ?, price = ?, state = ?, img = ?, description = ? WHERE id = ?";
        PreparedStatement pstmt = cnx.prepareStatement(req);
        pstmt.setString(1, events.getName());
        pstmt.setFloat(2, events.getPrice());
        pstmt.setString(3, events.getState());
        pstmt.setString(4, events.getImg());

        pstmt.setString(5, events.getDescription());
        pstmt.setInt(6, events.getId());

        pstmt.executeUpdate();
        System.out.println("Event updated successfully!");
    }

    @Override
    public List<Events> getAll() throws SQLException {
        List<Events> evt = new ArrayList<>();
        String req = "SELECT * FROM events";
        Statement stmt = cnx.createStatement();
        ResultSet rs = stmt.executeQuery(req);
        while (rs.next()) {
            Events events = new Events();
            events.setId(rs.getInt("id"));
            events.setName(rs.getString("name"));
            events.setImg(rs.getString("img"));
            events.setState(rs.getString("state"));
            events.setDescription(rs.getString("description"));
            events.setPrice(rs.getFloat("price"));


            // Fetch activity IDs associated with this event
            List<Integer> activityIds = getActivityIdsForEvent(events.getId());

            // Fetch activity names based on IDs
            List<String> activityNames = getActivityNamesByIds(activityIds);

            // Create Activity objects and add them to the event
            List<Activities> activities = new ArrayList<>();
            for (String activityName : activityNames) {
                activities.add(new Activities(activityName));
            }
            events.setActivities(activities);

            evt.add(events);
        }

        return evt;
    }


    @Override
    public boolean delete(int id) throws SQLException {
        String req = "DELETE FROM events WHERE id = ?";
        PreparedStatement pstmt = cnx.prepareStatement(req);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
        System.out.println("Event deleted successfully!");
        return true;
    }

    // Method to add an activity to an event
    public void addEventActivity(int eventId, int activityId) throws SQLException {
        String qry = "INSERT INTO events_activities (events_id, activities_id) VALUES (?, ?)";
        PreparedStatement pstmt = cnx.prepareStatement(qry);
        pstmt.setInt(1, eventId);
        pstmt.setInt(2, activityId);
        pstmt.executeUpdate();
        System.out.println("Activity added to event successfully!");
    }

    // Method to fetch activity IDs associated with an event
    private List<Integer> getActivityIdsForEvent(int eventId) throws SQLException {
        List<Integer> activityIds = new ArrayList<>();
        String req = "SELECT activities_id FROM events_activities WHERE events_id = ?";
        PreparedStatement pstmt = cnx.prepareStatement(req);
        pstmt.setInt(1, eventId);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            activityIds.add(rs.getInt("activities_id"));
        }
        return activityIds;
    }

    // Method to fetch activity names based on IDs
    private List<String> getActivityNamesByIds(List<Integer> activityIds) throws SQLException {
        List<String> activityNames = new ArrayList<>();
        String req = "SELECT name FROM activities WHERE id = ?";
        PreparedStatement pstmt = cnx.prepareStatement(req);
        for (int activityId : activityIds) {
            pstmt.setInt(1, activityId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                activityNames.add(rs.getString("name"));
            }
        }
        return activityNames;
    }

    public Map<String, Integer> getSeasonStatistics() throws SQLException {
        Map<String, Integer> seasonCounts = new HashMap<>();
        List<Events> eventsList = getAll(); // Assuming you have a method to get all events

        // Iterate through the events and count occurrences for each season
        for (Events event : eventsList) {
            String season = getSeasonFromDate(event.getDate()); // Assuming you have a method to get the season from the event date
            seasonCounts.put(season, seasonCounts.getOrDefault(season, 0) + 1);
        }

        return seasonCounts;
    }

    private String getSeasonFromDate(Date date) {
        // Extract the month from the date
        int month = date.getMonth() + 1; // Month starts from 0, so we add 1 to get the actual month

        // Determine the season based on the month
        if (month >= 3 && month <= 5) {
            return "Spring";
        } else if (month >= 6 && month <= 8) {
            return "Summer";
        } else if (month >= 9 && month <= 11) {
            return "Autumn";
        } else {
            return "Winter";
        }
    }

}

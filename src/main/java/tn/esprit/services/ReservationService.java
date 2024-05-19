package tn.esprit.services;

import tn.esprit.interfaces.ResService;
import tn.esprit.models.Destination;
import tn.esprit.models.Reservation;
import tn.esprit.util.MyConnection;
import java.time.temporal.ChronoUnit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationService implements ResService<Reservation> {

    Connection cnx = MyConnection.getInstance().getCnx();

    @Override
    public boolean addReservation(Reservation reservation) {
        try {
            // Calculate the number of days between the start date and end date
            long daysDifference = ChronoUnit.DAYS.between(reservation.getStartDate(), reservation.getEndDate());

            PreparedStatement stmt = cnx.prepareStatement("INSERT INTO reservation (startdate, enddate, totalprice, number, destination_id) VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, reservation.getStartDate().toString());
            stmt.setString(2, reservation.getEndDate().toString());
            stmt.setDouble(3, reservation.getTotalPrice());
            stmt.setLong(4, daysDifference); // Set the days difference as numberOfDays
            stmt.setInt(5, reservation.getDestination().getId());
            int rowsAffected = stmt.executeUpdate();

            // Return true if at least one row was affected (i.e., the reservation was successfully added)
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
            return false; // Return false in case of failure
        }
    }

    @Override
    public boolean updateReservation(Reservation reservation) {
        try {
            // Calculate the number of days between the updated start date and end date
            long daysDifference = ChronoUnit.DAYS.between(reservation.getStartDate(), reservation.getEndDate());

            PreparedStatement stmt = cnx.prepareStatement("UPDATE reservation SET startdate = ?, enddate = ?, totalprice = ?, number = ?, destination_id = ? WHERE id = ?");
            stmt.setString(1, reservation.getStartDate().toString());
            stmt.setString(2, reservation.getEndDate().toString());
            stmt.setDouble(3, reservation.getTotalPrice());
            stmt.setLong(4, daysDifference); // Use daysDifference for the number of days
            stmt.setInt(5, reservation.getDestination().getId());
            stmt.setInt(6, reservation.getReservationId());
            stmt.executeUpdate();

            // If the update is successful, return true
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
            return false;
        }
    }

    @Override
    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        try {
            PreparedStatement stmt = cnx.prepareStatement("SELECT * FROM reservation");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Reservation reservation = new Reservation();
                reservation.setReservationId(rs.getInt("id"));
                // Assuming destinationId is a foreign key referencing the destination table
                int destinationId = rs.getInt("destination_id");
                DestinationService destinationService = new DestinationService(); // Assuming you have a DestinationService
                Destination destination = destinationService.getoneDestination(destinationId);
                reservation.setDestination(destination);
                reservation.setStartDate(rs.getDate("startdate").toLocalDate());
                reservation.setEndDate(rs.getDate("enddate").toLocalDate());
                reservation.setTotalPrice(rs.getDouble("totalprice"));
                reservation.setNumberOfDays(rs.getInt("number"));
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }
        return reservations;
    }

    @Override
    public void deleteReservation(int reservationId) {
        try {
            PreparedStatement stmt = cnx.prepareStatement("DELETE FROM reservation WHERE id = ?");
            stmt.setInt(1, reservationId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }

    @Override
    public Reservation getOneReservation(int reservationId) {
        Reservation reservation = null;
        try {
            PreparedStatement stmt = cnx.prepareStatement("SELECT * FROM reservation WHERE id = ?");
            stmt.setInt(1, reservationId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Construct Reservation object from ResultSet
                reservation = new Reservation();
                reservation.setReservationId(rs.getInt("id"));
                int destinationId = rs.getInt("destination_id");

                // Assuming you have methods in Reservation to set other properties
                DestinationService destinationService = new DestinationService(); // Assuming you have a DestinationService
                Destination destination = destinationService.getoneDestination(destinationId);
                reservation.setDestination(destination);
                reservation.setStartDate(rs.getDate("startdate").toLocalDate());
                reservation.setEndDate(rs.getDate("enddate").toLocalDate());
                reservation.setTotalPrice(rs.getDouble("totalprice"));
                reservation.setNumberOfDays(rs.getInt("number"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }
        return reservation;
    }

}

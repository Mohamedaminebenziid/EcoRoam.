package tn.esprit.interfaces;

public interface ResService <Reservation> {
    boolean addReservation(Reservation reservation);

    boolean updateReservation(Reservation reservation);

    java.util.List<Reservation> getAllReservations();

    void deleteReservation(int reservationId);

    Reservation getOneReservation(int reservationId);
}

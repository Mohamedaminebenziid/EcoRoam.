package tn.esprit.models;
import java.time.LocalDate;
import java.util.Objects;
import java.time.temporal.ChronoUnit;

public class Reservation {

        private int reservationId;
        private Destination destination;
        private LocalDate startDate;
        private LocalDate endDate;
        private double totalPrice;
        private int numberOfDays;

    public Reservation() {
    }

    public Reservation(int reservationId, Destination destination, LocalDate startDate, LocalDate endDate, double totalPrice) {
        this.reservationId = reservationId;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.numberOfDays = calculateDaysNumber(startDate, endDate);;
    }
    public Reservation( Destination destination, LocalDate startDate, LocalDate endDate, double totalPrice) {
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.numberOfDays = calculateDaysNumber(startDate, endDate);;
    }
    private int calculateDaysNumber(LocalDate startDate, LocalDate endDate) {
        return (int) ChronoUnit.DAYS.between(startDate, endDate);
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", destination=" + destination +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", totalPrice=" + totalPrice +
                ", numberOfDays=" + numberOfDays +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation that)) return false;
        return reservationId == that.reservationId && Double.compare(totalPrice, that.totalPrice) == 0 && numberOfDays == that.numberOfDays && Objects.equals(destination, that.destination) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationId, destination, startDate, endDate, totalPrice, numberOfDays);
    }
}

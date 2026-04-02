import java.util.ArrayList;
import java.util.List;

class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public void displayReservationDetails() {
        System.out.println("Reservation ID : " + reservationId);
        System.out.println("Guest Name     : " + guestName);
        System.out.println("Room Type      : " + roomType);
        System.out.println("Room ID        : " + roomId);
    }
}

class BookingHistory {
    private List<Reservation> confirmedReservations;

    public BookingHistory() {
        confirmedReservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        confirmedReservations.add(reservation);
    }

    public List<Reservation> getConfirmedReservations() {
        return confirmedReservations;
    }
}

class BookingReportService {

    public void displayBookingHistory(List<Reservation> reservations) {
        System.out.println("========================================");
        System.out.println("         BOOKING HISTORY");
        System.out.println("========================================");

        if (reservations.isEmpty()) {
            System.out.println("No confirmed bookings found.");
        } else {
            for (Reservation reservation : reservations) {
                reservation.displayReservationDetails();
                System.out.println("----------------------------------------");
            }
        }

        System.out.println("========================================");
    }

    public void generateSummaryReport(List<Reservation> reservations) {
        int totalBookings = reservations.size();
        int singleRoomBookings = 0;
        int doubleRoomBookings = 0;
        int suiteRoomBookings = 0;

        for (Reservation reservation : reservations) {
            if (reservation.getRoomType().equals("Single Room")) {
                singleRoomBookings++;
            } else if (reservation.getRoomType().equals("Double Room")) {
                doubleRoomBookings++;
            } else if (reservation.getRoomType().equals("Suite Room")) {
                suiteRoomBookings++;
            }
        }

        System.out.println("========================================");
        System.out.println("         BOOKING SUMMARY REPORT");
        System.out.println("========================================");
        System.out.println("Total Confirmed Bookings : " + totalBookings);
        System.out.println("Single Room Bookings     : " + singleRoomBookings);
        System.out.println("Double Room Bookings     : " + doubleRoomBookings);
        System.out.println("Suite Room Bookings      : " + suiteRoomBookings);
        System.out.println("========================================");
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        BookingHistory bookingHistory = new BookingHistory();

        Reservation reservation1 =
                new Reservation("RES101", "Amit", "Single Room", "S-1");

        Reservation reservation2 =
                new Reservation("RES102", "Priya", "Double Room", "D-1");

        Reservation reservation3 =
                new Reservation("RES103", "Rahul", "Suite Room", "SU-1");

        Reservation reservation4 =
                new Reservation("RES104", "Sneha", "Single Room", "S-2");

        bookingHistory.addReservation(reservation1);
        bookingHistory.addReservation(reservation2);
        bookingHistory.addReservation(reservation3);
        bookingHistory.addReservation(reservation4);

        BookingReportService reportService = new BookingReportService();

        reportService.displayBookingHistory(
                bookingHistory.getConfirmedReservations()
        );

        System.out.println();

        reportService.generateSummaryReport(
                bookingHistory.getConfirmedReservations()
        );
    }
}
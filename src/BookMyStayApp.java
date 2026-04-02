import java.util.LinkedList;
import java.util.Queue;

class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayReservation() {
        System.out.println("Guest Name : " + guestName);
        System.out.println("Room Type  : " + roomType);
    }
}

class BookingRequestQueue {
    private Queue<Reservation> bookingQueue;

    public BookingRequestQueue() {
        bookingQueue = new LinkedList<>();
    }

    public void addBookingRequest(Reservation reservation) {
        bookingQueue.offer(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    public void displayQueuedRequests() {
        System.out.println("========================================");
        System.out.println("        BOOKING REQUEST QUEUE");
        System.out.println("========================================");

        if (bookingQueue.isEmpty()) {
            System.out.println("No booking requests in queue.");
        } else {
            int position = 1;

            for (Reservation reservation : bookingQueue) {
                System.out.println("Request Position : " + position);
                reservation.displayReservation();
                System.out.println("----------------------------------------");
                position++;
            }
        }

        System.out.println("========================================");
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        Reservation reservation1 = new Reservation("Amit", "Single Room");
        Reservation reservation2 = new Reservation("Priya", "Double Room");
        Reservation reservation3 = new Reservation("Rahul", "Suite Room");

        bookingQueue.addBookingRequest(reservation1);
        bookingQueue.addBookingRequest(reservation2);
        bookingQueue.addBookingRequest(reservation3);

        System.out.println();

        bookingQueue.displayQueuedRequests();
    }
}
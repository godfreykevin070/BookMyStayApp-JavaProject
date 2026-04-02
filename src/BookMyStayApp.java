import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;
    private boolean cancelled;

    public Reservation(String reservationId, String guestName,
                       String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.cancelled = false;
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

    public boolean isCancelled() {
        return cancelled;
    }

    public void cancelReservation() {
        cancelled = true;
    }

    public void displayReservationDetails() {
        System.out.println("Reservation ID : " + reservationId);
        System.out.println("Guest Name     : " + guestName);
        System.out.println("Room Type      : " + roomType);
        System.out.println("Room ID        : " + roomId);
        System.out.println("Cancelled      : " + cancelled);
    }
}

class RoomInventory {
    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();

        roomAvailability.put("Single Room", 1);
        roomAvailability.put("Double Room", 0);
        roomAvailability.put("Suite Room", 2);
    }

    public void increaseAvailability(String roomType) {
        int currentAvailability = roomAvailability.get(roomType);
        roomAvailability.put(roomType, currentAvailability + 1);
    }

    public void displayInventory() {
        System.out.println("========================================");
        System.out.println("         CURRENT INVENTORY");
        System.out.println("========================================");

        for (Map.Entry<String, Integer> entry : roomAvailability.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        System.out.println("========================================");
    }
}

class BookingHistory {
    private Map<String, Reservation> confirmedReservations;

    public BookingHistory() {
        confirmedReservations = new HashMap<>();
    }

    public void addReservation(Reservation reservation) {
        confirmedReservations.put(
                reservation.getReservationId(),
                reservation
        );
    }

    public Reservation getReservation(String reservationId) {
        return confirmedReservations.get(reservationId);
    }
}

class CancellationService {
    private RoomInventory inventory;
    private BookingHistory bookingHistory;
    private Stack<String> releasedRoomIds;

    public CancellationService(RoomInventory inventory,
                               BookingHistory bookingHistory) {
        this.inventory = inventory;
        this.bookingHistory = bookingHistory;
        releasedRoomIds = new Stack<>();
    }

    public void cancelBooking(String reservationId) {
        Reservation reservation =
                bookingHistory.getReservation(reservationId);

        if (reservation == null) {
            System.out.println("Cancellation failed.");
            System.out.println("Reason : Reservation ID does not exist.");
            System.out.println("----------------------------------------");
            return;
        }

        if (reservation.isCancelled()) {
            System.out.println("Cancellation failed.");
            System.out.println("Reason : Reservation is already cancelled.");
            System.out.println("----------------------------------------");
            return;
        }

        releasedRoomIds.push(reservation.getRoomId());

        inventory.increaseAvailability(reservation.getRoomType());

        reservation.cancelReservation();

        System.out.println("Cancellation successful.");
        System.out.println("Reservation ID : " +
                reservation.getReservationId());
        System.out.println("Released Room ID : " +
                reservation.getRoomId());
        System.out.println("----------------------------------------");
    }

    public void displayReleasedRoomIds() {
        System.out.println("========================================");
        System.out.println("         RELEASED ROOM IDS");
        System.out.println("========================================");

        if (releasedRoomIds.isEmpty()) {
            System.out.println("No released room IDs found.");
        } else {
            for (String roomId : releasedRoomIds) {
                System.out.println(roomId);
            }
        }

        System.out.println("========================================");
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        BookingHistory bookingHistory = new BookingHistory();

        Reservation reservation1 =
                new Reservation("RES101", "Amit",
                        "Double Room", "D-1");

        Reservation reservation2 =
                new Reservation("RES102", "Priya",
                        "Suite Room", "SU-1");

        bookingHistory.addReservation(reservation1);
        bookingHistory.addReservation(reservation2);

        CancellationService cancellationService =
                new CancellationService(inventory, bookingHistory);

        cancellationService.cancelBooking("RES101");
        cancellationService.cancelBooking("RES101");
        cancellationService.cancelBooking("RES999");

        System.out.println();

        inventory.displayInventory();

        System.out.println();

        cancellationService.displayReleasedRoomIds();
    }
}
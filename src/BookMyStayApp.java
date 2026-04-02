import java.util.HashMap;
import java.util.Map;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

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
}

class RoomInventory {
    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();

        roomAvailability.put("Single Room", 2);
        roomAvailability.put("Double Room", 1);
        roomAvailability.put("Suite Room", 0);
    }

    public boolean isValidRoomType(String roomType) {
        return roomAvailability.containsKey(roomType);
    }

    public int getAvailability(String roomType) {
        return roomAvailability.getOrDefault(roomType, -1);
    }

    public void decreaseAvailability(String roomType) throws InvalidBookingException {
        int currentAvailability = getAvailability(roomType);

        if (currentAvailability <= 0) {
            throw new InvalidBookingException(
                    "No available rooms for room type: " + roomType
            );
        }

        roomAvailability.put(roomType, currentAvailability - 1);
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

class BookingService {
    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void processBooking(Reservation reservation) {
        try {
            validateReservation(reservation);

            inventory.decreaseAvailability(reservation.getRoomType());

            System.out.println("Booking confirmed for " +
                    reservation.getGuestName());
            System.out.println("Room Type : " +
                    reservation.getRoomType());
            System.out.println("----------------------------------------");

        } catch (InvalidBookingException exception) {
            System.out.println("Booking failed for " +
                    reservation.getGuestName());
            System.out.println("Reason : " + exception.getMessage());
            System.out.println("----------------------------------------");
        }
    }

    private void validateReservation(Reservation reservation)
            throws InvalidBookingException {

        if (reservation.getGuestName() == null ||
                reservation.getGuestName().trim().isEmpty()) {
            throw new InvalidBookingException(
                    "Guest name cannot be empty."
            );
        }

        if (reservation.getRoomType() == null ||
                reservation.getRoomType().trim().isEmpty()) {
            throw new InvalidBookingException(
                    "Room type cannot be empty."
            );
        }

        if (!inventory.isValidRoomType(reservation.getRoomType())) {
            throw new InvalidBookingException(
                    "Invalid room type selected: " +
                            reservation.getRoomType()
            );
        }

        if (inventory.getAvailability(reservation.getRoomType()) <= 0) {
            throw new InvalidBookingException(
                    "Requested room type is currently unavailable."
            );
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        BookingService bookingService = new BookingService(inventory);

        Reservation reservation1 =
                new Reservation("Amit", "Single Room");

        Reservation reservation2 =
                new Reservation("Priya", "Luxury Room");

        Reservation reservation3 =
                new Reservation("", "Double Room");

        Reservation reservation4 =
                new Reservation("Rahul", "Suite Room");

        bookingService.processBooking(reservation1);
        bookingService.processBooking(reservation2);
        bookingService.processBooking(reservation3);
        bookingService.processBooking(reservation4);

        System.out.println();
        inventory.displayInventory();
    }
}
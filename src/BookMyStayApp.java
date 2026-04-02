import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Reservation implements Serializable {
    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String guestName,
                       String roomType, String roomId) {
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

    public void displayReservation() {
        System.out.println("Reservation ID : " + reservationId);
        System.out.println("Guest Name     : " + guestName);
        System.out.println("Room Type      : " + roomType);
        System.out.println("Room ID        : " + roomId);
    }
}

class RoomInventory implements Serializable {
    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
    }

    public void addRoomType(String roomType, int availableCount) {
        roomAvailability.put(roomType, availableCount);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
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

class HotelSystemData implements Serializable {
    private RoomInventory inventory;
    private List<Reservation> bookingHistory;

    public HotelSystemData(RoomInventory inventory,
                           List<Reservation> bookingHistory) {
        this.inventory = inventory;
        this.bookingHistory = bookingHistory;
    }

    public RoomInventory getInventory() {
        return inventory;
    }

    public List<Reservation> getBookingHistory() {
        return bookingHistory;
    }
}

class PersistenceService {
    private static final String FILE_NAME = "hotel_system_data.ser";

    public void saveSystemData(HotelSystemData systemData) {
        try {
            FileOutputStream fileOutputStream =
                    new FileOutputStream(FILE_NAME);

            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(systemData);

            objectOutputStream.close();
            fileOutputStream.close();

            System.out.println("System data saved successfully.");
        } catch (IOException exception) {
            System.out.println("Failed to save system data.");
            System.out.println("Reason : " + exception.getMessage());
        }
    }

    public HotelSystemData loadSystemData() {
        try {
            FileInputStream fileInputStream =
                    new FileInputStream(FILE_NAME);

            ObjectInputStream objectInputStream =
                    new ObjectInputStream(fileInputStream);

            HotelSystemData systemData =
                    (HotelSystemData) objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();

            System.out.println("System data restored successfully.");

            return systemData;

        } catch (FileNotFoundException exception) {
            System.out.println("Persistence file not found.");
            System.out.println("Starting system with default data.");
        } catch (IOException exception) {
            System.out.println("Persistence file is corrupted.");
            System.out.println("Starting system with default data.");
        } catch (ClassNotFoundException exception) {
            System.out.println("Unable to restore saved system state.");
            System.out.println("Starting system with default data.");
        }

        RoomInventory defaultInventory = new RoomInventory();
        defaultInventory.addRoomType("Single Room", 5);
        defaultInventory.addRoomType("Double Room", 3);
        defaultInventory.addRoomType("Suite Room", 2);

        List<Reservation> defaultBookingHistory =
                new ArrayList<>();

        return new HotelSystemData(
                defaultInventory,
                defaultBookingHistory
        );
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        PersistenceService persistenceService =
                new PersistenceService();

        HotelSystemData systemData =
                persistenceService.loadSystemData();

        RoomInventory inventory = systemData.getInventory();
        List<Reservation> bookingHistory =
                systemData.getBookingHistory();

        System.out.println();
        inventory.displayInventory();

        System.out.println();

        if (bookingHistory.isEmpty()) {
            System.out.println("No booking history found.");
        } else {
            System.out.println("========================================");
            System.out.println("         BOOKING HISTORY");
            System.out.println("========================================");

            for (Reservation reservation : bookingHistory) {
                reservation.displayReservation();
                System.out.println("----------------------------------------");
            }
        }

        bookingHistory.add(
                new Reservation(
                        "RES101",
                        "Amit",
                        "Single Room",
                        "S-1"
                )
        );

        bookingHistory.add(
                new Reservation(
                        "RES102",
                        "Priya",
                        "Double Room",
                        "D-1"
                )
        );

        persistenceService.saveSystemData(
                new HotelSystemData(inventory, bookingHistory)
        );
    }
}
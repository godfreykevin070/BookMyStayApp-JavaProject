import java.util.HashMap;
import java.util.Map;

abstract class Room {
    private String roomType;
    private int numberOfBeds;
    private double roomSize;
    private double pricePerNight;

    public Room(String roomType, int numberOfBeds, double roomSize, double pricePerNight) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.roomSize = roomSize;
        this.pricePerNight = pricePerNight;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public double getRoomSize() {
        return roomSize;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type       : " + roomType);
        System.out.println("Beds            : " + numberOfBeds);
        System.out.println("Room Size       : " + roomSize + " sq.ft");
        System.out.println("Price Per Night : Rs." + pricePerNight);
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 180.0, 2500.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 300.0, 4500.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 500.0, 8000.0);
    }
}

/**
 * RoomInventory class acts as a centralized inventory manager.
 * It stores and manages room availability using a HashMap.
 */
class RoomInventory {

    private HashMap<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();

        // Initial room availability
        roomAvailability.put("Single Room", 10);
        roomAvailability.put("Double Room", 5);
        roomAvailability.put("Suite Room", 2);
    }

    // Retrieve availability for a room type
    public int getAvailability(String roomType) {
        return roomAvailability.getOrDefault(roomType, 0);
    }

    // Update room availability
    public void updateAvailability(String roomType, int newCount) {
        if (roomAvailability.containsKey(roomType)) {
            roomAvailability.put(roomType, newCount);
            System.out.println(roomType + " availability updated to " + newCount);
        } else {
            System.out.println("Room type not found in inventory.");
        }
    }

    // Display complete inventory
    public void displayInventory() {
        System.out.println("========================================");
        System.out.println("      CURRENT ROOM INVENTORY");
        System.out.println("========================================");

        for (Map.Entry<String, Integer> entry : roomAvailability.entrySet()) {
            System.out.println(entry.getKey() + " -> Available Rooms: " + entry.getValue());
        }

        System.out.println("========================================");
    }
}


public class BookMyStayApp {

    public static void main(String[] args) {

        // Creating room objects
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Initializing centralized inventory
        RoomInventory inventory = new RoomInventory();

        // Display room details
        System.out.println("\nSingle Room Details:");
        singleRoom.displayRoomDetails();
        System.out.println("Available Rooms : " +
                inventory.getAvailability(singleRoom.getRoomType()));

        System.out.println("\n----------------------------------------");

        System.out.println("\nDouble Room Details:");
        doubleRoom.displayRoomDetails();
        System.out.println("Available Rooms : " +
                inventory.getAvailability(doubleRoom.getRoomType()));

        System.out.println("\n----------------------------------------");

        System.out.println("\nSuite Room Details:");
        suiteRoom.displayRoomDetails();
        System.out.println("Available Rooms : " +
                inventory.getAvailability(suiteRoom.getRoomType()));

        System.out.println("\n----------------------------------------");

        // Display complete inventory
        inventory.displayInventory();

        // Example update
        inventory.updateAvailability("Single Room", 8);

        System.out.println("\nUpdated Inventory:");
        inventory.displayInventory();
    }
}
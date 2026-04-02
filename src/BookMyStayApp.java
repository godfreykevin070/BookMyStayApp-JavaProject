import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

class RoomInventory {

    private HashMap<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();

        roomAvailability.put("Single Room", 10);
        roomAvailability.put("Double Room", 5);
        roomAvailability.put("Suite Room", 0);
    }

    public int getAvailability(String roomType) {
        return roomAvailability.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int newCount) {
        roomAvailability.put(roomType, newCount);
    }

    public Map<String, Integer> getAllAvailability() {
        return roomAvailability;
    }
}

class RoomSearchService {

    private RoomInventory inventory;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void displayAvailableRooms(List<Room> rooms) {

        System.out.println("========================================");
        System.out.println("       AVAILABLE ROOM OPTIONS");
        System.out.println("========================================");

        boolean roomFound = false;

        for (Room room : rooms) {
            int availableCount = inventory.getAvailability(room.getRoomType());

            // Show only rooms with availability greater than zero
            if (availableCount > 0) {
                room.displayRoomDetails();
                System.out.println("Available Rooms : " + availableCount);
                System.out.println("----------------------------------------");
                roomFound = true;
            }
        }

        if (!roomFound) {
            System.out.println("No rooms are currently available.");
        }

        System.out.println("========================================");
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        // Create room objects
        List<Room> rooms = new ArrayList<>();

        rooms.add(new SingleRoom());
        rooms.add(new DoubleRoom());
        rooms.add(new SuiteRoom());

        // Create inventory
        RoomInventory inventory = new RoomInventory();

        // Create search service
        RoomSearchService searchService = new RoomSearchService(inventory);

        // Display available rooms
        searchService.displayAvailableRooms(rooms);
    }
}
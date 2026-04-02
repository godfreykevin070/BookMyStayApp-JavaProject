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

public class BookMyStayApp {

    public static void main(String[] args) {

        // Creating room objects using polymorphism
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Static availability variables
        int singleRoomAvailable = 10;
        int doubleRoomAvailable = 5;
        int suiteRoomAvailable = 2;

        System.out.println("========================================");
        System.out.println("      HOTEL ROOM AVAILABILITY");
        System.out.println("========================================");

        System.out.println("\nSingle Room Details:");
        singleRoom.displayRoomDetails();
        System.out.println("Available Rooms : " + singleRoomAvailable);

        System.out.println("\n----------------------------------------");

        System.out.println("\nDouble Room Details:");
        doubleRoom.displayRoomDetails();
        System.out.println("Available Rooms : " + doubleRoomAvailable);

        System.out.println("\n----------------------------------------");

        System.out.println("\nSuite Room Details:");
        suiteRoom.displayRoomDetails();
        System.out.println("Available Rooms : " + suiteRoomAvailable);

        System.out.println("\n========================================");
        System.out.println("Room information displayed successfully.");
        System.out.println("========================================");
    }
}
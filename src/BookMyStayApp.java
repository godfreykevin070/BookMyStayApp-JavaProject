import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

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
    private HashMap<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();

        roomAvailability.put("Single Room", 2);
        roomAvailability.put("Double Room", 1);
        roomAvailability.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return roomAvailability.getOrDefault(roomType, 0);
    }

    public void decreaseAvailability(String roomType) {
        int currentAvailability = roomAvailability.get(roomType);
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

class BookingRequestQueue {
    private Queue<Reservation> bookingQueue;

    public BookingRequestQueue() {
        bookingQueue = new LinkedList<>();
    }

    public void addBookingRequest(Reservation reservation) {
        bookingQueue.offer(reservation);
    }

    public Reservation getNextRequest() {
        return bookingQueue.poll();
    }

    public boolean isEmpty() {
        return bookingQueue.isEmpty();
    }
}

class BookingService {
    private RoomInventory inventory;
    private Set<String> allocatedRoomIds;
    private HashMap<String, Set<String>> allocatedRoomsByType;
    private int singleRoomCounter;
    private int doubleRoomCounter;
    private int suiteRoomCounter;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        allocatedRoomIds = new HashSet<>();
        allocatedRoomsByType = new HashMap<>();
        singleRoomCounter = 1;
        doubleRoomCounter = 1;
        suiteRoomCounter = 1;
    }

    public void processBookingRequest(Reservation reservation) {
        String roomType = reservation.getRoomType();

        if (inventory.getAvailability(roomType) <= 0) {
            System.out.println("Booking failed for " + reservation.getGuestName());
            System.out.println("Requested room type is unavailable.");
            System.out.println("----------------------------------------");
            return;
        }

        String roomId = generateRoomId(roomType);

        allocatedRoomIds.add(roomId);

        allocatedRoomsByType.putIfAbsent(roomType, new HashSet<>());
        allocatedRoomsByType.get(roomType).add(roomId);

        inventory.decreaseAvailability(roomType);

        System.out.println("Reservation Confirmed");
        System.out.println("Guest Name : " + reservation.getGuestName());
        System.out.println("Room Type  : " + roomType);
        System.out.println("Assigned Room ID : " + roomId);
        System.out.println("----------------------------------------");
    }

    private String generateRoomId(String roomType) {
        String roomId = "";

        if (roomType.equals("Single Room")) {
            roomId = "S-" + singleRoomCounter;
            singleRoomCounter++;
        } else if (roomType.equals("Double Room")) {
            roomId = "D-" + doubleRoomCounter;
            doubleRoomCounter++;
        } else if (roomType.equals("Suite Room")) {
            roomId = "SU-" + suiteRoomCounter;
            suiteRoomCounter++;
        }

        while (allocatedRoomIds.contains(roomId)) {
            if (roomType.equals("Single Room")) {
                roomId = "S-" + singleRoomCounter;
                singleRoomCounter++;
            } else if (roomType.equals("Double Room")) {
                roomId = "D-" + doubleRoomCounter;
                doubleRoomCounter++;
            } else if (roomType.equals("Suite Room")) {
                roomId = "SU-" + suiteRoomCounter;
                suiteRoomCounter++;
            }
        }

        return roomId;
    }

    public void displayAllocatedRooms() {
        System.out.println("========================================");
        System.out.println("         ALLOCATED ROOM IDS");
        System.out.println("========================================");

        for (Map.Entry<String, Set<String>> entry : allocatedRoomsByType.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        System.out.println("========================================");
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        bookingQueue.addBookingRequest(new Reservation("Amit", "Single Room"));
        bookingQueue.addBookingRequest(new Reservation("Priya", "Double Room"));
        bookingQueue.addBookingRequest(new Reservation("Rahul", "Single Room"));
        bookingQueue.addBookingRequest(new Reservation("Sneha", "Suite Room"));
        bookingQueue.addBookingRequest(new Reservation("Karan", "Double Room"));

        BookingService bookingService = new BookingService(inventory);

        while (!bookingQueue.isEmpty()) {
            Reservation reservation = bookingQueue.getNextRequest();
            bookingService.processBookingRequest(reservation);
        }

        System.out.println();
        inventory.displayInventory();

        System.out.println();
        bookingService.displayAllocatedRooms();
    }
}
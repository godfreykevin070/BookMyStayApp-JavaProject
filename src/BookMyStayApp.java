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
}

class BookingQueue {
    private Queue<Reservation> bookingRequests;

    public BookingQueue() {
        bookingRequests = new LinkedList<>();
    }

    public synchronized void addBookingRequest(Reservation reservation) {
        bookingRequests.offer(reservation);

        System.out.println(Thread.currentThread().getName() +
                " added booking request for " +
                reservation.getGuestName());
    }

    public synchronized Reservation getNextBookingRequest() {
        return bookingRequests.poll();
    }

    public synchronized boolean isEmpty() {
        return bookingRequests.isEmpty();
    }
}

class RoomInventory {
    private int singleRoomAvailability;
    private int doubleRoomAvailability;
    private int suiteRoomAvailability;

    public RoomInventory(int singleRoomAvailability,
                         int doubleRoomAvailability,
                         int suiteRoomAvailability) {
        this.singleRoomAvailability = singleRoomAvailability;
        this.doubleRoomAvailability = doubleRoomAvailability;
        this.suiteRoomAvailability = suiteRoomAvailability;
    }

    public synchronized boolean allocateRoom(String roomType) {
        if (roomType.equals("Single Room")) {
            if (singleRoomAvailability > 0) {
                singleRoomAvailability--;
                return true;
            }
        } else if (roomType.equals("Double Room")) {
            if (doubleRoomAvailability > 0) {
                doubleRoomAvailability--;
                return true;
            }
        } else if (roomType.equals("Suite Room")) {
            if (suiteRoomAvailability > 0) {
                suiteRoomAvailability--;
                return true;
            }
        }

        return false;
    }

    public synchronized void displayInventory() {
        System.out.println("========================================");
        System.out.println("         FINAL INVENTORY");
        System.out.println("========================================");
        System.out.println("Single Room : " + singleRoomAvailability);
        System.out.println("Double Room : " + doubleRoomAvailability);
        System.out.println("Suite Room  : " + suiteRoomAvailability);
        System.out.println("========================================");
    }
}

class ConcurrentBookingProcessor implements Runnable {
    private BookingQueue bookingQueue;
    private RoomInventory inventory;

    public ConcurrentBookingProcessor(BookingQueue bookingQueue,
                                      RoomInventory inventory) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
    }

    @Override
    public void run() {
        while (true) {
            Reservation reservation;

            synchronized (bookingQueue) {
                if (bookingQueue.isEmpty()) {
                    break;
                }

                reservation = bookingQueue.getNextBookingRequest();
            }

            if (reservation != null) {
                boolean bookingSuccess =
                        inventory.allocateRoom(reservation.getRoomType());

                synchronized (System.out) {
                    if (bookingSuccess) {
                        System.out.println(Thread.currentThread().getName() +
                                " confirmed booking for " +
                                reservation.getGuestName() +
                                " - " + reservation.getRoomType());
                    } else {
                        System.out.println(Thread.currentThread().getName() +
                                " failed booking for " +
                                reservation.getGuestName() +
                                " - " + reservation.getRoomType() +
                                " not available");
                    }
                }
            }
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        BookingQueue bookingQueue = new BookingQueue();

        bookingQueue.addBookingRequest(
                new Reservation("Amit", "Single Room"));

        bookingQueue.addBookingRequest(
                new Reservation("Priya", "Single Room"));

        bookingQueue.addBookingRequest(
                new Reservation("Rahul", "Single Room"));

        bookingQueue.addBookingRequest(
                new Reservation("Sneha", "Double Room"));

        bookingQueue.addBookingRequest(
                new Reservation("Karan", "Suite Room"));

        RoomInventory inventory = new RoomInventory(2, 1, 1);

        Thread processor1 = new Thread(
                new ConcurrentBookingProcessor(bookingQueue, inventory),
                "Processor-1"
        );

        Thread processor2 = new Thread(
                new ConcurrentBookingProcessor(bookingQueue, inventory),
                "Processor-2"
        );

        Thread processor3 = new Thread(
                new ConcurrentBookingProcessor(bookingQueue, inventory),
                "Processor-3"
        );

        processor1.start();
        processor2.start();
        processor3.start();

        try {
            processor1.join();
            processor2.join();
            processor3.join();
        } catch (InterruptedException exception) {
            System.out.println("Thread execution interrupted.");
        }

        System.out.println();

        inventory.displayInventory();
    }
}
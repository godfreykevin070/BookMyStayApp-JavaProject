import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class AddOnService {
    private String serviceName;
    private double serviceCost;

    public AddOnService(String serviceName, double serviceCost) {
        this.serviceName = serviceName;
        this.serviceCost = serviceCost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getServiceCost() {
        return serviceCost;
    }
}

class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
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
}

class AddOnServiceManager {
    private Map<String, List<AddOnService>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    public void addServiceToReservation(String reservationId, AddOnService service) {
        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        reservationServices.get(reservationId).add(service);

        System.out.println(service.getServiceName() +
                " added to Reservation ID " + reservationId);
    }

    public double calculateAdditionalCost(String reservationId) {
        double totalCost = 0;

        List<AddOnService> services = reservationServices.get(reservationId);

        if (services != null) {
            for (AddOnService service : services) {
                totalCost += service.getServiceCost();
            }
        }

        return totalCost;
    }

    public void displayServicesForReservation(String reservationId) {
        System.out.println("========================================");
        System.out.println("Reservation ID : " + reservationId);
        System.out.println("Selected Add-On Services");
        System.out.println("========================================");

        List<AddOnService> services = reservationServices.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services selected.");
        } else {
            for (AddOnService service : services) {
                System.out.println("Service Name : " + service.getServiceName());
                System.out.println("Service Cost : Rs." + service.getServiceCost());
                System.out.println("----------------------------------------");
            }

            System.out.println("Total Additional Cost : Rs." +
                    calculateAdditionalCost(reservationId));
        }

        System.out.println("========================================");
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        Reservation reservation1 =
                new Reservation("RES101", "Amit", "Single Room");

        AddOnService breakfast =
                new AddOnService("Breakfast", 500);

        AddOnService airportPickup =
                new AddOnService("Airport Pickup", 1200);

        AddOnService spaAccess =
                new AddOnService("Spa Access", 2000);

        AddOnServiceManager serviceManager =
                new AddOnServiceManager();

        serviceManager.addServiceToReservation(
                reservation1.getReservationId(), breakfast);

        serviceManager.addServiceToReservation(
                reservation1.getReservationId(), airportPickup);

        serviceManager.addServiceToReservation(
                reservation1.getReservationId(), spaAccess);

        System.out.println();

        serviceManager.displayServicesForReservation(
                reservation1.getReservationId());
    }
}
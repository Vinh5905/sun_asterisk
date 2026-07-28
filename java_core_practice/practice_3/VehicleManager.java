import java.util.ArrayList;
import java.util.Iterator;

public class VehicleManager {
    private ArrayList<Vehicle> vehicles;

    public VehicleManager() {
        vehicles = new ArrayList<Vehicle>();
    }

    // Requirement 2.1: Add a vehicle only when its vehicle number is unique.
    public boolean addVehicle(Vehicle vehicle) {
        if (vehicle == null || containsVehicleNumber(vehicle.getVehicleNumber())) {
            return false;
        }
        vehicles.add(vehicle);
        return true;
    }

    public boolean containsVehicleNumber(String vehicleNumber) {
        if (vehicleNumber == null) {
            return false;
        }
        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getVehicleNumber().equalsIgnoreCase(vehicleNumber.trim())) {
                return true;
            }
        }
        return false;
    }

    // Requirement 2.2: Search for a vehicle by its vehicle number.
    public Vehicle findByVehicleNumber(String vehicleNumber) {
        if (vehicleNumber == null) {
            return null;
        }
        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getVehicleNumber().equalsIgnoreCase(vehicleNumber.trim())) {
                return vehicles.get(i);
            }
        }
        return null;
    }

    // Requirement 2.3: Find all vehicles belonging to an owner by CMND number.
    public ArrayList<Vehicle> findVehiclesByOwnerCmnd(String cmndNumber) {
        ArrayList<Vehicle> result = new ArrayList<Vehicle>();
        if (cmndNumber == null) {
            return result;
        }
        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getOwner().getCmndNumber().equals(cmndNumber.trim())) {
                result.add(vehicles.get(i));
            }
        }
        return result;
    }

    // Requirement 2.4: Delete all vehicles belonging to a manufacturer.
    public int deleteByManufacturer(String manufacturer) {
        if (manufacturer == null) {
            return 0;
        }

        int deletedCount = 0;
        Iterator<Vehicle> iterator = vehicles.iterator();
        while (iterator.hasNext()) {
            Vehicle vehicle = iterator.next();
            if (vehicle.getManufacturer().equalsIgnoreCase(manufacturer.trim())) {
                iterator.remove();
                deletedCount++;
            }
        }
        return deletedCount;
    }

    // Requirement 2.5: Determine the manufacturer with the most managed vehicles.
    public String findManufacturerWithMostVehicles() {
        if (vehicles.isEmpty()) {
            return "No vehicles are being managed.";
        }

        String[] manufacturers = {"Honda", "Yamaha", "Toyota", "Suzuki"};
        int[] counts = countVehiclesByManufacturers(manufacturers);
        int highestCount = counts[0];

        for (int i = 1; i < counts.length; i++) {
            if (counts[i] > highestCount) {
                highestCount = counts[i];
            }
        }

        String result = "Manufacturer(s) with the most vehicles: ";
        boolean firstManufacturer = true;

        for (int i = 0; i < manufacturers.length; i++) {
            if (counts[i] == highestCount) {
                if (!firstManufacturer) {
                    result += ", ";
                }
                result += manufacturers[i] + " (" + counts[i] + ")";
                firstManufacturer = false;
            }
        }

        return result;
    }

    // Requirement 2.6: Sort manufacturers by vehicle count in descending order.
    public void displayManufacturersSortedByVehicleCount() {
        String[] manufacturers = {"Honda", "Yamaha", "Toyota", "Suzuki"};
        int[] counts = countVehiclesByManufacturers(manufacturers);

        for (int i = 0; i < manufacturers.length - 1; i++) {
            for (int j = 0; j < manufacturers.length - 1 - i; j++) {
                if (counts[j] < counts[j + 1]
                        || (counts[j] == counts[j + 1] && manufacturers[j].compareTo(manufacturers[j + 1]) > 0)) {
                    int temporaryCount = counts[j];
                    counts[j] = counts[j + 1];
                    counts[j + 1] = temporaryCount;

                    String temporaryManufacturer = manufacturers[j];
                    manufacturers[j] = manufacturers[j + 1];
                    manufacturers[j + 1] = temporaryManufacturer;
                }
            }
        }

        for (int i = 0; i < manufacturers.length; i++) {
            System.out.println(manufacturers[i] + ": " + counts[i]);
        }
    }

    // Requirement 2.7: Display the number of vehicles of each type.
    public void displayVehicleTypeStatistics() {
        int carCount = 0;
        int motorbikeCount = 0;
        int truckCount = 0;

        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i) instanceof Car) {
                carCount++;
            } else if (vehicles.get(i) instanceof Motorbike) {
                motorbikeCount++;
            } else if (vehicles.get(i) instanceof Truck) {
                truckCount++;
            }
        }

        System.out.println("Cars: " + carCount);
        System.out.println("Motorbikes: " + motorbikeCount);
        System.out.println("Trucks: " + truckCount);
    }

    // Display all managed vehicles by using polymorphism.
    public void displayAllVehicles() {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles are being managed.");
            return;
        }

        for (int i = 0; i < vehicles.size(); i++) {
            System.out.println("Vehicle " + (i + 1));
            vehicles.get(i).displayInfo();
            System.out.println();
        }
    }

    public VehicleOwner findExistingOwnerByCmnd(String cmndNumber) {
        if (cmndNumber == null) {
            return null;
        }
        for (int i = 0; i < vehicles.size(); i++) {
            VehicleOwner owner = vehicles.get(i).getOwner();
            if (owner.getCmndNumber().equals(cmndNumber.trim())) {
                return owner;
            }
        }
        return null;
    }

    private int[] countVehiclesByManufacturers(String[] manufacturers) {
        int[] counts = new int[manufacturers.length];

        for (int i = 0; i < vehicles.size(); i++) {
            for (int j = 0; j < manufacturers.length; j++) {
                if (vehicles.get(i).getManufacturer().equalsIgnoreCase(manufacturers[j])) {
                    counts[j]++;
                }
            }
        }

        return counts;
    }
}

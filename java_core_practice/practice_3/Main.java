import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    // Run the police vehicle management program.
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        VehicleManager manager = new VehicleManager();
        boolean running = true;

        // Display the vehicle management menu until the user chooses to exit.
        while (running) {
            displayMenu();
            int option = readInt(scanner, "Choose an option: ");

            // Process the function selected by the user.
            switch (option) {
                case 1:
                    addCar(scanner, manager);
                    break;
                case 2:
                    addMotorbike(scanner, manager);
                    break;
                case 3:
                    addTruck(scanner, manager);
                    break;
                case 4:
                    searchByVehicleNumber(scanner, manager);
                    break;
                case 5:
                    findByOwnerCmnd(scanner, manager);
                    break;
                case 6:
                    deleteByManufacturer(scanner, manager);
                    break;
                case 7:
                    System.out.println(manager.findManufacturerWithMostVehicles());
                    break;
                case 8:
                    manager.displayManufacturersSortedByVehicleCount();
                    break;
                case 9:
                    manager.displayVehicleTypeStatistics();
                    break;
                case 10:
                    manager.displayAllVehicles();
                    break;
                case 0:
                    running = false;
                    System.out.println("Program ended.");
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
                    break;
            }
        }

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println();
        System.out.println("POLICE VEHICLE MANAGEMENT");
        System.out.println("1. Add a car");
        System.out.println("2. Add a motorbike");
        System.out.println("3. Add a truck");
        System.out.println("4. Search for a vehicle by vehicle number");
        System.out.println("5. Find vehicles by owner CMND number");
        System.out.println("6. Delete vehicles by manufacturer");
        System.out.println("7. Show the manufacturer with the most vehicles");
        System.out.println("8. Sort manufacturers by vehicle count");
        System.out.println("9. Display vehicle type statistics");
        System.out.println("10. Display all vehicles");
        System.out.println("0. Exit");
    }

    // Requirement 2.1: Read information and add a new car.
    private static void addCar(Scanner scanner, VehicleManager manager) {
        try {
            String vehicleNumber = readLine(scanner, "Vehicle number: ");
            String manufacturer = readLine(scanner, "Manufacturer: ");
            int manufactureYear = readInt(scanner, "Year of manufacture: ");
            String color = readLine(scanner, "Vehicle color: ");
            VehicleOwner owner = readOwner(scanner, manager);
            int numberOfSeats = readInt(scanner, "Number of seats: ");
            String engineType = readLine(scanner, "Engine type: ");

            Car car = new Car(vehicleNumber, manufacturer, manufactureYear, color, owner, numberOfSeats, engineType);
            addVehicleToManager(manager, car);
        } catch (IllegalArgumentException e) {
            System.out.println("Cannot add car: " + e.getMessage());
        }
    }

    // Requirement 2.1: Read information and add a new motorbike.
    private static void addMotorbike(Scanner scanner, VehicleManager manager) {
        try {
            String vehicleNumber = readLine(scanner, "Vehicle number: ");
            String manufacturer = readLine(scanner, "Manufacturer: ");
            int manufactureYear = readInt(scanner, "Year of manufacture: ");
            String color = readLine(scanner, "Vehicle color: ");
            VehicleOwner owner = readOwner(scanner, manager);
            double capacity = readDouble(scanner, "Capacity: ");

            Motorbike motorbike = new Motorbike(vehicleNumber, manufacturer, manufactureYear, color, owner, capacity);
            addVehicleToManager(manager, motorbike);
        } catch (IllegalArgumentException e) {
            System.out.println("Cannot add motorbike: " + e.getMessage());
        }
    }

    // Requirement 2.1: Read information and add a new truck.
    private static void addTruck(Scanner scanner, VehicleManager manager) {
        try {
            String vehicleNumber = readLine(scanner, "Vehicle number: ");
            String manufacturer = readLine(scanner, "Manufacturer: ");
            int manufactureYear = readInt(scanner, "Year of manufacture: ");
            String color = readLine(scanner, "Vehicle color: ");
            VehicleOwner owner = readOwner(scanner, manager);
            double tonnage = readDouble(scanner, "Tonnage: ");

            Truck truck = new Truck(vehicleNumber, manufacturer, manufactureYear, color, owner, tonnage);
            addVehicleToManager(manager, truck);
        } catch (IllegalArgumentException e) {
            System.out.println("Cannot add truck: " + e.getMessage());
        }
    }

    private static VehicleOwner readOwner(Scanner scanner, VehicleManager manager) {
        String cmndNumber = readLine(scanner, "Owner CMND number: ");
        VehicleOwner existingOwner = manager.findExistingOwnerByCmnd(cmndNumber);

        if (existingOwner != null) {
            System.out.println("Existing owner information will be reused.");
            return existingOwner;
        }

        String fullName = readLine(scanner, "Owner full name: ");
        String email = readLine(scanner, "Owner email: ");
        return new VehicleOwner(cmndNumber, fullName, email);
    }

    private static void addVehicleToManager(VehicleManager manager, Vehicle vehicle) {
        if (manager.addVehicle(vehicle)) {
            System.out.println("Vehicle added successfully.");
        } else {
            System.out.println("Cannot add vehicle. The vehicle number already exists.");
        }
    }

    private static void searchByVehicleNumber(Scanner scanner, VehicleManager manager) {
        String vehicleNumber = readLine(scanner, "Enter vehicle number: ");
        Vehicle vehicle = manager.findByVehicleNumber(vehicleNumber);

        if (vehicle == null) {
            System.out.println("Vehicle not found.");
        } else {
            vehicle.displayInfo();
        }
    }

    private static void findByOwnerCmnd(Scanner scanner, VehicleManager manager) {
        String cmndNumber = readLine(scanner, "Enter owner CMND number: ");
        ArrayList<Vehicle> result = manager.findVehiclesByOwnerCmnd(cmndNumber);

        if (result.isEmpty()) {
            System.out.println("No vehicles found for this owner.");
            return;
        }

        for (int i = 0; i < result.size(); i++) {
            System.out.println("Vehicle " + (i + 1));
            result.get(i).displayInfo();
            System.out.println();
        }
    }

    private static void deleteByManufacturer(Scanner scanner, VehicleManager manager) {
        String manufacturer = readLine(scanner, "Enter manufacturer: ");
        int deletedCount = manager.deleteByManufacturer(manufacturer);

        if (deletedCount == 0) {
            System.out.println("No vehicles found for this manufacturer.");
        } else {
            System.out.println("Deleted vehicles: " + deletedCount);
        }
    }

    private static String readLine(Scanner scanner, String message) {
        System.out.print(message);
        if (!scanner.hasNextLine()) {
            System.out.println();
            System.out.println("Program ended.");
            System.exit(0);
        }
        return scanner.nextLine();
    }

    private static int readInt(Scanner scanner, String message) {
        while (true) {
            String input = readLine(scanner, message);
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer. Please enter a valid integer.");
            }
        }
    }

    private static double readDouble(Scanner scanner, String message) {
        while (true) {
            String input = readLine(scanner, message);
            try {
                return Double.parseDouble(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid number.");
            }
        }
    }
}

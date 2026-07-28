// Requirement 1: Create the Car subclass with seat and engine information.
public class Car extends Vehicle {
    private int numberOfSeats;
    private String engineType;

    public Car(String vehicleNumber, String manufacturer, int manufactureYear, String color, VehicleOwner owner,
            int numberOfSeats, String engineType) {
        super(vehicleNumber, manufacturer, manufactureYear, color, owner);
        setNumberOfSeats(numberOfSeats);
        setEngineType(engineType);
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        if (numberOfSeats <= 0) {
            throw new IllegalArgumentException("Number of seats must be greater than 0.");
        }
        this.numberOfSeats = numberOfSeats;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        if (engineType == null || engineType.trim().isEmpty()) {
            throw new IllegalArgumentException("Engine type must not be blank.");
        }
        this.engineType = engineType.trim();
    }

    // Identify this vehicle as a car.
    @Override
    public String getVehicleType() {
        return "Car";
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Number of seats: " + getNumberOfSeats());
        System.out.println("Engine type: " + getEngineType());
        System.out.println("Owner information:");
        getOwner().displayInfo();
    }
}

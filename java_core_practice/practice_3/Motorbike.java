// Requirement 1: Create the Motorbike subclass with capacity information.
public class Motorbike extends Vehicle {
    private double capacity;

    public Motorbike(String vehicleNumber, String manufacturer, int manufactureYear, String color, VehicleOwner owner,
            double capacity) {
        super(vehicleNumber, manufacturer, manufactureYear, color, owner);
        setCapacity(capacity);
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0.");
        }
        this.capacity = capacity;
    }

    // Identify this vehicle as a motorbike.
    @Override
    public String getVehicleType() {
        return "Motorbike";
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Capacity: " + getCapacity());
        System.out.println("Owner information:");
        getOwner().displayInfo();
    }
}

// Requirement 1: Create the Truck subclass with tonnage information.
public class Truck extends Vehicle {
    private double tonnage;

    public Truck(String vehicleNumber, String manufacturer, int manufactureYear, String color, VehicleOwner owner,
            double tonnage) {
        super(vehicleNumber, manufacturer, manufactureYear, color, owner);
        setTonnage(tonnage);
    }

    public double getTonnage() {
        return tonnage;
    }

    public void setTonnage(double tonnage) {
        if (tonnage <= 0) {
            throw new IllegalArgumentException("Tonnage must be greater than 0.");
        }
        this.tonnage = tonnage;
    }

    // Identify this vehicle as a truck.
    @Override
    public String getVehicleType() {
        return "Truck";
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Tonnage: " + getTonnage());
        System.out.println("Owner information:");
        getOwner().displayInfo();
    }
}

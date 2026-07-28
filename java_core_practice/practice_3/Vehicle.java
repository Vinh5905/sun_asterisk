import java.time.Year;

// Requirement 1: Define the abstract parent class for all vehicle types.
public abstract class Vehicle {
    private String vehicleNumber;
    private String manufacturer;
    private int manufactureYear;
    private String color;
    private VehicleOwner owner;

    public Vehicle(String vehicleNumber, String manufacturer, int manufactureYear, String color, VehicleOwner owner) {
        setVehicleNumber(vehicleNumber);
        setManufacturer(manufacturer);
        setManufactureYear(manufactureYear);
        setColor(color);
        setOwner(owner);
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        if (!isValidVehicleNumber(vehicleNumber)) {
            throw new IllegalArgumentException("Vehicle number must contain exactly 5 characters.");
        }
        this.vehicleNumber = vehicleNumber.trim();
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = validateAndFormatManufacturer(manufacturer);
    }

    public int getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(int manufactureYear) {
        if (!isValidManufactureYear(manufactureYear)) {
            throw new IllegalArgumentException("Manufacture year must be greater than 2000 and less than or equal to the current year.");
        }
        this.manufactureYear = manufactureYear;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        if (color == null || color.trim().isEmpty()) {
            throw new IllegalArgumentException("Vehicle color must not be blank.");
        }
        this.color = color.trim();
    }

    public VehicleOwner getOwner() {
        return owner;
    }

    public void setOwner(VehicleOwner owner) {
        if (owner == null) {
            throw new IllegalArgumentException("Vehicle owner must not be null.");
        }
        this.owner = owner;
    }

    // Validate that the vehicle number contains exactly five characters.
    private boolean isValidVehicleNumber(String vehicleNumber) {
        return vehicleNumber != null && vehicleNumber.trim().length() == 5;
    }

    // Validate that the manufacturer is supported by the program.
    private String validateAndFormatManufacturer(String manufacturer) {
        if (manufacturer == null || manufacturer.trim().isEmpty()) {
            throw new IllegalArgumentException("Manufacturer must not be blank.");
        }

        String value = manufacturer.trim();
        if (value.equalsIgnoreCase("Honda")) {
            return "Honda";
        }
        if (value.equalsIgnoreCase("Yamaha")) {
            return "Yamaha";
        }
        if (value.equalsIgnoreCase("Toyota")) {
            return "Toyota";
        }
        if (value.equalsIgnoreCase("Suzuki")) {
            return "Suzuki";
        }

        throw new IllegalArgumentException("Manufacturer must be Honda, Yamaha, Toyota, or Suzuki.");
    }

    // Validate that the manufacture year is within the allowed range.
    private boolean isValidManufactureYear(int manufactureYear) {
        int currentYear = Year.now().getValue();
        return manufactureYear > 2000 && manufactureYear <= currentYear;
    }

    // Return the specific type of the vehicle.
    public abstract String getVehicleType();

    public void displayInfo() {
        System.out.println("Vehicle type: " + getVehicleType());
        System.out.println("Vehicle number: " + getVehicleNumber());
        System.out.println("Manufacturer: " + getManufacturer());
        System.out.println("Year of manufacture: " + getManufactureYear());
        System.out.println("Vehicle color: " + getColor());
    }
}

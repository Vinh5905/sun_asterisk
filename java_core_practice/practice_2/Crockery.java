import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

// Requirement 1: Create the Crockery concrete class that inherits from Goods.
public class Crockery extends Goods {
    private String manufacturer;
    private LocalDate arrivalDate;

    public Crockery(String productCode, String productName, int inventoryQuantity, double unitPrice,
            String manufacturer, LocalDate arrivalDate) {
        super(productCode, productName, inventoryQuantity, unitPrice);
        setManufacturer(manufacturer);
        setArrivalDate(arrivalDate);
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        if (manufacturer == null || manufacturer.trim().isEmpty()) {
            throw new IllegalArgumentException("Manufacturer must not be blank.");
        }
        this.manufacturer = manufacturer.trim();
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        if (arrivalDate == null) {
            throw new IllegalArgumentException("Arrival date must not be null.");
        }
        if (arrivalDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Arrival date must not be after the current date.");
        }
        this.arrivalDate = arrivalDate;
    }

    // Calculate the storage time from the arrival date to the current date.
    public long getStorageDays() {
        return ChronoUnit.DAYS.between(getArrivalDate(), LocalDate.now());
    }

    // Crockery has a VAT rate of 10%.
    @Override
    public double calculateVAT() {
        return getInventoryQuantity() * getUnitPrice() * 0.10;
    }

    // Requirement 2: Crockery with high inventory and long storage time is considered slow sale.
    @Override
    public String evaluateConsumption() {
        if (getInventoryQuantity() > 50 && getStorageDays() > 10) {
            return "Slow sale";
        }
        return "Not evaluated";
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Manufacturer: " + getManufacturer());
        System.out.println("Date of arrival: " + getArrivalDate());
        System.out.println("Storage time: " + getStorageDays() + " days");
        System.out.printf("Total inventory value: %.2f%n", calculateTotalValue());
        System.out.printf("VAT amount: %.2f%n", calculateVAT());
        System.out.println("Consumption status: " + evaluateConsumption());
    }
}

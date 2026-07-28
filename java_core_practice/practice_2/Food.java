import java.time.LocalDate;

// Requirement 1: Create the Food concrete class that inherits from Goods.
public class Food extends Goods {
    private LocalDate manufactureDate;
    private LocalDate expirationDate;
    private String supplier;

    public Food(String productCode, String productName, int inventoryQuantity, double unitPrice,
            LocalDate manufactureDate, LocalDate expirationDate, String supplier) {
        super(productCode, productName, inventoryQuantity, unitPrice);
        setManufactureDate(manufactureDate);
        setExpirationDate(expirationDate);
        setSupplier(supplier);
    }

    public LocalDate getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(LocalDate manufactureDate) {
        if (manufactureDate == null) {
            throw new IllegalArgumentException("Manufacture date must not be null.");
        }
        if (expirationDate != null && !expirationDate.isAfter(manufactureDate)) {
            throw new IllegalArgumentException("Expiration date must be after manufacture date.");
        }
        this.manufactureDate = manufactureDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        if (expirationDate == null) {
            throw new IllegalArgumentException("Expiration date must not be null.");
        }
        if (manufactureDate != null && !expirationDate.isAfter(manufactureDate)) {
            throw new IllegalArgumentException("Expiration date must be after manufacture date.");
        }
        this.expirationDate = expirationDate;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        if (supplier == null || supplier.trim().isEmpty()) {
            throw new IllegalArgumentException("Supplier must not be blank.");
        }
        this.supplier = supplier.trim();
    }

    // Food products have a VAT rate of 5%.
    @Override
    public double calculateVAT() {
        return getInventoryQuantity() * getUnitPrice() * 0.05;
    }

    // Requirement 2: Expired food that is still in stock is hard to sell.
    @Override
    public String evaluateConsumption() {
        if (getInventoryQuantity() > 0 && getExpirationDate().isBefore(LocalDate.now())) {
            return "Hard to sell";
        }
        return "Not evaluated";
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Manufacture date: " + getManufactureDate());
        System.out.println("Expiration date: " + getExpirationDate());
        System.out.println("Supplier: " + getSupplier());
        System.out.printf("Total inventory value: %.2f%n", calculateTotalValue());
        System.out.printf("VAT amount: %.2f%n", calculateVAT());
        System.out.println("Consumption status: " + evaluateConsumption());
    }
}

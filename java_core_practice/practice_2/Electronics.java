// Requirement 1: Create the Electronics concrete class that inherits from Goods.
public class Electronics extends Goods {
    private int warrantyMonths;
    private double capacityKW;

    public Electronics(String productCode, String productName, int inventoryQuantity, double unitPrice,
            int warrantyMonths, double capacityKW) {
        super(productCode, productName, inventoryQuantity, unitPrice);
        setWarrantyMonths(warrantyMonths);
        setCapacityKW(capacityKW);
    }

    public int getWarrantyMonths() {
        return warrantyMonths;
    }

    public void setWarrantyMonths(int warrantyMonths) {
        if (warrantyMonths < 0) {
            throw new IllegalArgumentException("Warranty period must be greater than or equal to 0.");
        }
        this.warrantyMonths = warrantyMonths;
    }

    public double getCapacityKW() {
        return capacityKW;
    }

    public void setCapacityKW(double capacityKW) {
        if (capacityKW < 0) {
            throw new IllegalArgumentException("Capacity must be greater than or equal to 0.");
        }
        this.capacityKW = capacityKW;
    }

    // Electronic goods have a VAT rate of 10%.
    @Override
    public double calculateVAT() {
        return getInventoryQuantity() * getUnitPrice() * 0.10;
    }

    // Requirement 2: Electronic goods with fewer than three items are considered sold well.
    @Override
    public String evaluateConsumption() {
        if (getInventoryQuantity() < 3) {
            return "Sold well";
        }
        return "Not evaluated";
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Warranty period: " + getWarrantyMonths() + " months");
        System.out.println("Capacity: " + getCapacityKW() + " KW");
        System.out.printf("Total inventory value: %.2f%n", calculateTotalValue());
        System.out.printf("VAT amount: %.2f%n", calculateVAT());
        System.out.println("Consumption status: " + evaluateConsumption());
    }
}

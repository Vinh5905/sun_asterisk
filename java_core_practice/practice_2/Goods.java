// Requirement 1: Define the abstract parent class for all types of goods.
public abstract class Goods {
    private String productCode;
    private String productName;
    private int inventoryQuantity;
    private double unitPrice;

    public Goods(String productCode, String productName, int inventoryQuantity, double unitPrice) {
        setProductCode(productCode);
        setProductName(productName);
        setInventoryQuantity(inventoryQuantity);
        setUnitPrice(unitPrice);
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        if (productCode == null || productCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Product code must not be blank.");
        }
        this.productCode = productCode.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        if (productName == null || productName.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name must not be blank.");
        }
        this.productName = productName.trim();
    }

    public int getInventoryQuantity() {
        return inventoryQuantity;
    }

    public void setInventoryQuantity(int inventoryQuantity) {
        if (inventoryQuantity < 0) {
            throw new IllegalArgumentException("Inventory quantity must be greater than or equal to 0.");
        }
        this.inventoryQuantity = inventoryQuantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        if (unitPrice < 0) {
            throw new IllegalArgumentException("Unit price must be greater than or equal to 0.");
        }
        this.unitPrice = unitPrice;
    }

    // Calculate the total inventory value of this product.
    public double calculateTotalValue() {
        return inventoryQuantity * unitPrice;
    }

    // Calculate VAT according to the product type.
    public abstract double calculateVAT();

    // Evaluate the consumption status according to the product type.
    public abstract String evaluateConsumption();

    public void displayInfo() {
        System.out.println("Product code: " + getProductCode());
        System.out.println("Product name: " + getProductName());
        System.out.println("Inventory quantity: " + getInventoryQuantity());
        System.out.printf("Unit price: %.2f%n", getUnitPrice());
    }
}

// Requirement 3: Manage the goods list by using an array.
public class InventoryManager {
    private Goods[] goodsList;
    private int size;

    public InventoryManager(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0.");
        }
        goodsList = new Goods[capacity];
        size = 0;
    }

    // Requirement 3: Add a product only when its product code is not duplicated.
    public boolean addGoods(Goods goods) {
        if (goods == null || size == goodsList.length || containsProductCode(goods.getProductCode())) {
            return false;
        }
        goodsList[size] = goods;
        size++;
        return true;
    }

    public boolean containsProductCode(String productCode) {
        if (productCode == null) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (goodsList[i].getProductCode().equalsIgnoreCase(productCode.trim())) {
                return true;
            }
        }
        return false;
    }

    // Display all products by using polymorphism.
    public void displayAllGoods() {
        if (size == 0) {
            System.out.println("No goods in the inventory.");
            return;
        }
        for (int i = 0; i < size; i++) {
            System.out.println("Product " + (i + 1));
            goodsList[i].displayInfo();
            System.out.println();
        }
    }

    // Calculate the total inventory quantity of the three product types.
    public void displayInventoryQuantityByType() {
        int foodQuantity = 0;
        int electronicsQuantity = 0;
        int crockeryQuantity = 0;

        for (int i = 0; i < size; i++) {
            if (goodsList[i] instanceof Food) {
                foodQuantity += goodsList[i].getInventoryQuantity();
            } else if (goodsList[i] instanceof Electronics) {
                electronicsQuantity += goodsList[i].getInventoryQuantity();
            } else if (goodsList[i] instanceof Crockery) {
                crockeryQuantity += goodsList[i].getInventoryQuantity();
            }
        }

        System.out.println("Food inventory quantity: " + foodQuantity);
        System.out.println("Electronics inventory quantity: " + electronicsQuantity);
        System.out.println("Crockery inventory quantity: " + crockeryQuantity);
    }

    // Calculate the total VAT amount of the three product types.
    public void displayVATByType() {
        double foodVAT = 0;
        double electronicsVAT = 0;
        double crockeryVAT = 0;

        for (int i = 0; i < size; i++) {
            if (goodsList[i] instanceof Food) {
                foodVAT += goodsList[i].calculateVAT();
            } else if (goodsList[i] instanceof Electronics) {
                electronicsVAT += goodsList[i].calculateVAT();
            } else if (goodsList[i] instanceof Crockery) {
                crockeryVAT += goodsList[i].calculateVAT();
            }
        }

        System.out.printf("Food VAT amount: %.2f%n", foodVAT);
        System.out.printf("Electronics VAT amount: %.2f%n", electronicsVAT);
        System.out.printf("Crockery VAT amount: %.2f%n", crockeryVAT);
    }

    public Goods findByProductCode(String productCode) {
        if (productCode == null) {
            return null;
        }
        for (int i = 0; i < size; i++) {
            if (goodsList[i].getProductCode().equalsIgnoreCase(productCode.trim())) {
                return goodsList[i];
            }
        }
        return null;
    }
}

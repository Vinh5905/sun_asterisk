import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Requirement 3: Allow the user to choose the type of goods to add.
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InventoryManager manager = new InventoryManager(100);
        boolean running = true;

        // Display the supermarket inventory management menu.
        while (running) {
            displayMenu();
            int option = readInt(scanner, "Choose an option: ");

            // Process the option selected by the user.
            switch (option) {
                case 1:
                    addFood(scanner, manager);
                    break;
                case 2:
                    addElectronics(scanner, manager);
                    break;
                case 3:
                    addCrockery(scanner, manager);
                    break;
                case 4:
                    manager.displayAllGoods();
                    break;
                case 5:
                    manager.displayInventoryQuantityByType();
                    break;
                case 6:
                    manager.displayVATByType();
                    break;
                case 7:
                    findProduct(scanner, manager);
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
        System.out.println("SUPERMARKET INVENTORY MANAGEMENT");
        System.out.println("1. Add food");
        System.out.println("2. Add electronics");
        System.out.println("3. Add crockery");
        System.out.println("4. Display all goods");
        System.out.println("5. Display inventory quantity by type");
        System.out.println("6. Display VAT amount by type");
        System.out.println("7. Find a product by product code");
        System.out.println("0. Exit");
    }

    private static void addFood(Scanner scanner, InventoryManager manager) {
        String productCode = readLine(scanner, "Product code: ");
        String productName = readLine(scanner, "Product name: ");
        int inventoryQuantity = readInt(scanner, "Inventory quantity: ");
        double unitPrice = readDouble(scanner, "Unit price: ");
        LocalDate manufactureDate = readDate(scanner, "Manufacture date (yyyy-MM-dd): ");
        LocalDate expirationDate = readDate(scanner, "Expiration date (yyyy-MM-dd): ");
        String supplier = readLine(scanner, "Supplier: ");

        try {
            Food food = new Food(productCode, productName, inventoryQuantity, unitPrice,
                    manufactureDate, expirationDate, supplier);
            addGoodsToManager(manager, food);
        } catch (IllegalArgumentException e) {
            System.out.println("Cannot add food: " + e.getMessage());
        }
    }

    private static void addElectronics(Scanner scanner, InventoryManager manager) {
        String productCode = readLine(scanner, "Product code: ");
        String productName = readLine(scanner, "Product name: ");
        int inventoryQuantity = readInt(scanner, "Inventory quantity: ");
        double unitPrice = readDouble(scanner, "Unit price: ");
        int warrantyMonths = readInt(scanner, "Warranty period in months: ");
        double capacityKW = readDouble(scanner, "Capacity in KW: ");

        try {
            Electronics electronics = new Electronics(productCode, productName, inventoryQuantity, unitPrice,
                    warrantyMonths, capacityKW);
            addGoodsToManager(manager, electronics);
        } catch (IllegalArgumentException e) {
            System.out.println("Cannot add electronics: " + e.getMessage());
        }
    }

    private static void addCrockery(Scanner scanner, InventoryManager manager) {
        String productCode = readLine(scanner, "Product code: ");
        String productName = readLine(scanner, "Product name: ");
        int inventoryQuantity = readInt(scanner, "Inventory quantity: ");
        double unitPrice = readDouble(scanner, "Unit price: ");
        String manufacturer = readLine(scanner, "Manufacturer: ");
        LocalDate arrivalDate = readDate(scanner, "Date of arrival (yyyy-MM-dd): ");

        try {
            Crockery crockery = new Crockery(productCode, productName, inventoryQuantity, unitPrice,
                    manufacturer, arrivalDate);
            addGoodsToManager(manager, crockery);
        } catch (IllegalArgumentException e) {
            System.out.println("Cannot add crockery: " + e.getMessage());
        }
    }

    private static void addGoodsToManager(InventoryManager manager, Goods goods) {
        if (manager.addGoods(goods)) {
            System.out.println("Product added successfully.");
        } else {
            System.out.println("Cannot add product. The product code may already exist, or the inventory array may be full.");
        }
    }

    private static void findProduct(Scanner scanner, InventoryManager manager) {
        String productCode = readLine(scanner, "Enter product code: ");
        Goods goods = manager.findByProductCode(productCode);

        if (goods == null) {
            System.out.println("Product not found.");
        } else {
            goods.displayInfo();
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

    private static LocalDate readDate(Scanner scanner, String message) {
        while (true) {
            String input = readLine(scanner, message);
            try {
                return LocalDate.parse(input.trim(), DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Please use the format yyyy-MM-dd.");
            }
        }
    }
}

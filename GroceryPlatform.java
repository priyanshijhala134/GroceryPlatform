import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

// Abstract Class for a Product
abstract class Product {
    protected String name;
    protected double price;
    protected String category;

    public Product(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public abstract void displayInfo();

    public double getPrice() {
        return price;
    }
}

// Class for Grocery Items
class GroceryItem extends Product {
    public GroceryItem(String name, double price, String category) {
        super(name, price, category);
    }

    @Override
    public void displayInfo() {
        System.out.println(category + ": " + name + " - ₹" + price);
    }
}

// Interface for Order Management
interface Order {
    void addProduct(Product product, int quantity);
    void displayOrderDetails();
}
// Class for Delivery Options
class Delivery {
    private String address;
    private String modeOfPayment;
    private String deliveryTime;
    private String deliveryPartner;

    private static final String[] DELIVERY_PARTNERS = {"Hitesh", "Pravin", "Krish", "Kiran"};

    public Delivery(String address, String modeOfPayment, String deliveryTime) {
        this.address = address;
        this.modeOfPayment = modeOfPayment;
        this.deliveryTime = deliveryTime;
        this.deliveryPartner = assignRandomPartner();
    }

    private String assignRandomPartner() {
        Random random = new Random();
        return DELIVERY_PARTNERS[random.nextInt(DELIVERY_PARTNERS.length)];
    }

    public void displayDeliveryInfo() {
        System.out.println("\nDelivery Details:");
        System.out.println("Address: " + address);
        System.out.println("Payment Method: " + modeOfPayment);
        System.out.println("Expected Delivery Time: " + deliveryTime);
        System.out.println("Delivery Partner: " + deliveryPartner);
    }
}

// Class implementing Order interface for Grocery Order
class GroceryOrder implements Order {
    private ArrayList<String> orderSummary = new ArrayList<>();
    private Delivery delivery;
    private double totalPrice = 0;

    public GroceryOrder(Delivery delivery) {
        this.delivery = delivery;
    }

    @Override
    public void addProduct(Product product, int quantity) {
        double itemTotal = product.getPrice() * quantity;
        totalPrice += itemTotal;
        orderSummary.add(product.category + ": " + product.name + " x" + quantity + " - ₹" + itemTotal);
        System.out.println(product.name + " x" + quantity + " added to the order at ₹" + itemTotal);
    }
    @Override
    public void displayOrderDetails() {
        System.out.println("\nOrder Summary:");
        for (String item : orderSummary) {
            System.out.println(item);
        }
        System.out.println("\nTotal Payment: ₹" + totalPrice);
        delivery.displayDeliveryInfo();
    }
}
// Main Class
public class GroceryPlatform {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create Delivery information
        System.out.println("Enter delivery address:");
        String address = scanner.nextLine();

        System.out.println("Enter mode of payment (Cash, Credit Card, UPI):");
        String modeOfPayment = scanner.nextLine();

        // Delivery Time Options
        String[] deliveryTimes = {"10:00 AM", "12:00 PM", "2:00 PM", "4:00 PM", "6:00 PM"};
        System.out.println("Choose delivery time:");
        for (int i = 0; i < deliveryTimes.length; i++) {
            System.out.println((i + 1) + ". " + deliveryTimes[i]);
        }

        int deliveryChoice;
        while (true) {
            System.out.print("Enter the number for your preferred delivery time: ");
            deliveryChoice = scanner.nextInt();
            if (deliveryChoice >= 1 && deliveryChoice <= deliveryTimes.length) {
                break;
            } else {
                System.out.println("Invalid choice, please try again.");
            }
        }
        String deliveryTime = deliveryTimes[deliveryChoice - 1];
        Delivery delivery = new Delivery(address, modeOfPayment, deliveryTime);
        GroceryOrder order = new GroceryOrder(delivery);

        // Categories and Products
        String[] categoryNames = {"Vegetables", "Fruits", "Dairy Products", "Bakery", "Snacks", "Tea and Coffee"};
        Product[][] categories = {
            {new GroceryItem("Potato", 20, "Vegetable"),
             new GroceryItem("Cabbage", 30, "Vegetable"),
             new GroceryItem("Bhendi", 40, "Vegetable"),
             new GroceryItem("Palak", 15, "Vegetable")},
             
            {new GroceryItem("Coconut", 60, "Fruit"),
             new GroceryItem("Strawberries", 60, "Fruit"),
             new GroceryItem("Chikoo", 120, "Fruit"),
             new GroceryItem("Watermelon", 90, "Fruit")},

            {new GroceryItem("Milk(AMUL)", 50, "Dairy"),
             new GroceryItem("Cheese(AMUL)", 120, "Dairy"),
             new GroceryItem("Yogurt(MILKYMIST)", 40, "Dairy"),
             new GroceryItem("Butter(AMUL)", 80, "Dairy")},

            {new GroceryItem("Bread (MAIDA)", 30, "Bakery"),
             new GroceryItem("Cake(100GM)", 150, "Bakery"),
             new GroceryItem("Cookies(BRITANIA)", 60, "Bakery")},

            {new GroceryItem("Chips(LAYS)", 20, "Snack"),
             new GroceryItem("Namkeen(BIKAJI)", 50, "Snack"),
             new GroceryItem("Popcorn", 30, "Snack")},

            {new GroceryItem("Tea(WAGHBAKRI)", 40, "Beverage"),
             new GroceryItem("Coffee(NESCAFE)", 80, "Beverage")}
        };

        // Display Categories
        System.out.println("\nCategories:");
        for (int i = 0; i < categoryNames.length; i++) {
            System.out.println((i + 1) + ". " + categoryNames[i]);
        }

        // Select Category and Show Items
        while (true) {
            System.out.print("\nEnter the category number to view items (or 0 to checkout): ");
            int categoryChoice = scanner.nextInt();
            if (categoryChoice == 0) {
                break;
            } else if (categoryChoice > 0 && categoryChoice <= categoryNames.length) {
                Product[] productsInCategory = categories[categoryChoice - 1];

                System.out.println("\nAvailable " + categoryNames[categoryChoice - 1] + ":");
                for (int j = 0; j < productsInCategory.length; j++) {
                    System.out.print((j + 1) + ". ");
                    productsInCategory[j].displayInfo();
                }

                // Select Product
                System.out.print("Enter the product number to add to the order (or 0 to go back): ");
                int productChoice = scanner.nextInt();
                if (productChoice > 0 && productChoice <= productsInCategory.length) {
                    Product selectedProduct = productsInCategory[productChoice - 1];

                    // Select Quantity
                    System.out.print("Enter quantity (1 to 5): ");
                    int quantity = scanner.nextInt();
                    while (quantity < 1 || quantity > 5) {
                        System.out.print("Invalid quantity. Enter a value between 1 and 5: ");
                        quantity = scanner.nextInt();
                    }

                    order.addProduct(selectedProduct, quantity);
                }
            } else {
                System.out.println("Invalid category choice, please try again.");
            }
        }

        // Display Order Details
        order.displayOrderDetails();
        scanner.close();
    }
}



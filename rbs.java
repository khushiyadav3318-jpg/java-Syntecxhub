import java.util.*;

class MenuItem {
    String name;
    double price;

    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
					    }
}

class OrderItem {
    MenuItem item;
    int quantity;

    public OrderItem(MenuItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return item.price * quantity;
    }
}

public class rbs {
    private static Map<Integer, MenuItem> menu = new HashMap<>();
    private static List<OrderItem> currentOrder = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);
    private static final double GST_RATE = 0.05; 

    public static void main(String[] args) {
        
        menu.put(1, new MenuItem("Burger", 150.0));
        menu.put(2, new MenuItem("Pizza", 300.0));
        menu.put(3, new MenuItem("Pasta", 250.0));
        menu.put(4, new MenuItem("Coke", 50.0));

        while (true) {
            System.out.println("\n--- RESTAURANT BILLING SYSTEM ---");
            System.out.println("1. View Menu\n2. Add Item to Order\n3. Generate Bill\n4. Manage Menu (Admin)\n5. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> showMenu();
                case 2 -> addToOrder();
                case 3 -> generateBill();
                case 4 -> manageMenu();
                case 5 -> System.exit(0);
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\nID | Item Name | Price");
        menu.forEach((id, item) -> System.out.printf("%d  | %-9s | %.2f\n", id, item.name, item.price));
    }

    private static void addToOrder() {
        showMenu();
        System.out.print("Enter Item ID to add: ");
        int id = sc.nextInt();
        if (menu.containsKey(id)) {
            System.out.print("Enter Quantity: ");
            int qty = sc.nextInt();
            currentOrder.add(new OrderItem(menu.get(id), qty));
            System.out.println("Added to order.");
        } else {
            System.out.println("Item not found!");
        }
    }

    private static void generateBill() {
        if (currentOrder.isEmpty()) {
            System.out.println("Order is empty!");
            return;
        }

        System.out.println("\n********** ITEMISED RECEIPT **********");
        System.out.printf("%-15s %-5s %-10s %-10s\n", "Item", "Qty", "Price", "Total");
        double subtotal = 0;

        for (OrderItem oi : currentOrder) {
            double lineTotal = oi.getTotalPrice();
            System.out.printf("%-15s %-5d %-10.2f %-10.2f\n", oi.item.name, oi.quantity, oi.item.price, lineTotal);
            subtotal += lineTotal;
        }

        double gst = subtotal * GST_RATE;
        double grandTotal = subtotal + gst;

        System.out.println("--------------------------------------");
        System.out.printf("Subtotal:       %.2f\n", subtotal);
        System.out.printf("GST (5%%):       %.2f\n", gst);
        System.out.printf("GRAND TOTAL:    %.2f\n", grandTotal);
        System.out.println("**************************************");
        currentOrder.clear();
    }

    private static void manageMenu() {
        System.out.println("1. Add Menu Item\n2. Remove Menu Item");
        int subChoice = sc.nextInt();
        sc.nextLine(); 

        if (subChoice == 1) {
            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Price: ");
            double price = sc.nextDouble();
            int nextId = menu.keySet().stream().max(Integer::compare).orElse(0) + 1;
            menu.put(nextId, new MenuItem(name, price));
        } else if (subChoice == 2) {
            System.out.print("Enter ID to remove: ");
            int id = sc.nextInt();
            menu.remove(id);
        }
    }
}

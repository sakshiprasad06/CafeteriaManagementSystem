import java.util.*;

class Item {
    String name;
    float rating;
    float price;
    int foodId;
    Item next;
    Item prev;

    public Item(String name, float price, int foodId) {
        this.name = name;
        this.rating = 4.0f;
        this.price = price;
        this.foodId = foodId;
        this.next = null;
        this.prev = null;
    }
}

class OrderList {
    int customerId;
    int[][] items = new int[10][2];
    float amount;
    String date;
    OrderList next;
    OrderList prev;

    public OrderList() {
        this.next = null;
        this.prev = null;
    }
}

public class  CafeteriaManagementSystem  {
    private Item head = null;
    private Item last = null;
    private OrderList headOrder = null;
    private OrderList lastOrder = null;
    private int custId = 1;
    private int todayCustomer = 0;
    private float totalIncome = 0;
    
    Scanner sc = new Scanner(System.in);

    public void insertItem(String name, float price, int foodId) {
        Item newItem = new Item(name, price, foodId);
        if (head == null) {
            head = last = newItem;
        } else {
            last.next = newItem;
            newItem.prev = last;
            last = newItem;
        }
    }

    public void displayMenu() {
        System.out.println("--------------------------------OUR MENU---------------------------------------------");
        System.out.println("INDEX      ITEMNAME      PRICE        RATING");
        Item temp = head;
        while (temp != null) {
            System.out.printf("%d\t%s\t%.2f\t%.2f\n", temp.foodId, temp.name, temp.price, temp.rating);
            temp = temp.next;
        }
        System.out.println("-------------------------------------------------------------------------------------");
    }

    public void deleteItemById(int foodId) {
        Item current = head;
        while (current != null && current.foodId != foodId) {
            current = current.next;
        }

        if (current == null) {
            System.out.println("Item not found");
            return;
        }

        if (current.prev != null) {
            current.prev.next = current.next;
        } else {
            head = current.next;
        }

        if (current.next != null) {
            current.next.prev = current.prev;
        }

        System.out.println("Item with food ID " + foodId + " deleted successfully");
    }

    public void modifyItemById(int foodId, float newPrice) {
        Item current = head;
        while (current != null && current.foodId != foodId) {
            current = current.next;
        }

        if (current == null) {
            System.out.println("Item not found");
            return;
        }

        current.price = newPrice;
        System.out.println("Item with food ID " + foodId + " modified successfully");
    }

    public void order() {
        int[][] items = new int[10][2];
        int j = 0;

        while (true) {
            System.out.print("Please Enter the FOOD ID NUMBER OF ITEM AND ITS QUANTITY: ");
            for (int i = 0; i < 2; i++) {
                items[j][i] = sc.nextInt();
            }
            j++;

            System.out.print("Do you want to add more items? (1. yes, 2. no): ");
            int n = sc.nextInt();
            if (n != 1) break;
        }

        System.out.print("Do you want to modify or delete any items before creating the bill? (1. Modify, 2. Delete, 3. Continue): ");
        int option = sc.nextInt();
        switch (option) {
            case 1:
                for (int i = 0; i < j; i++) {
                    System.out.print("Do you want to modify the quantity of item with food ID " + items[i][0] + "? (1. yes, 2. no): ");
                    int modifyOption = sc.nextInt();
                    if (modifyOption == 1) {
                        System.out.print("Enter the new quantity for item with food ID " + items[i][0] + ": ");
                        items[i][1] = sc.nextInt();
                    }
                }
                break;
            case 2:
                for (int i = 0; i < j; i++) {
                    System.out.print("Do you want to delete the item with food ID " + items[i][0] + "? (1. yes, 2. no): ");
                    int deleteOption = sc.nextInt();
                    if (deleteOption == 1) {
                        deleteItemById(items[i][0]);
                    }
                }
                break;
            default:
                break;
        }

        float totalAmount = 0;
        System.out.print("Enter your name: ");
        sc.nextLine();
        String name = sc.nextLine();
        System.out.print("Enter Today's Date: ");
        String date = sc.nextLine();

        System.out.println("\n--------------------------------YOUR BILL---------------------------------------------");
        System.out.println("Name: " + name);
        System.out.println("Date: " + date);

        for (int k = 0; k < j; k++) {
            Item temp = head;
            while (temp != null && temp.foodId != items[k][0]) {
                temp = temp.next;
            }
            if (temp == null) {
                System.out.println("Item with food ID " + items[k][0] + " not found");
            } else {
                System.out.printf("%d\t%s\t%d\t\t%.2f\n", temp.foodId, temp.name, items[k][1], (items[k][1] * temp.price));
                totalAmount += items[k][1] * temp.price;
            }
        }
        System.out.println("Total Payable amount is: " + totalAmount);

        OrderList newOrder = new OrderList();
        newOrder.amount = totalAmount;
        newOrder.customerId = custId++;
        newOrder.date = date;
        newOrder.items = items;

        if (headOrder == null) {
            headOrder = lastOrder = newOrder;
        } else {
            lastOrder.next = newOrder;
            newOrder.prev = lastOrder;
            lastOrder = newOrder;
        }

        todayCustomer++;
        totalIncome += totalAmount;
    }

    public void displayOrderHistory() {
        System.out.println("\n----------------------------ORDER HISTORY---------------------------------------");
        System.out.println("SR_NO      DATE            TOTAL AMOUNT");
        OrderList temp = headOrder;
        while (temp != null) {
            System.out.printf("%d\t%s\t%.2f\n", temp.customerId, temp.date, temp.amount);
            temp = temp.next;
        }
    }

    public static void main(String[] args) {
        CafeteriaManagementSystem cms = new CafeteriaManagementSystem();
        cms.insertItem("Burger", 70.23f, 1);
        cms.insertItem("Pizza", 230.67f, 2);
        cms.insertItem("Hot Cake", 150.83f, 3);
        cms.insertItem("Coffee", 70.23f, 4);
        cms.insertItem("Ice-Cream", 50.23f, 5);
        cms.insertItem("Sandwich", 60.23f, 6);
        cms.insertItem("Grill", 52.29f, 7);
        cms.insertItem("Naan-Bread", 35.13f, 8);
        cms.insertItem("Cold Drinks", 20.13f, 9);

        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n------------------------------------- CAFETERIA MANAGEMENT SYSTEM -------------------------------------");
            System.out.println("1. FOOD Items");
            System.out.println("2. Admin Panel");
            System.out.println("3. EXIT");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    cms.displayMenu();
                    cms.order();
                    break;
                case 2:
                    System.out.println("Admin Panel - Today\'s Income: " + cms.totalIncome);
                    cms.displayOrderHistory();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 3);

        sc.close();
    }
}

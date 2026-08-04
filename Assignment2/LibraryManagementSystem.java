import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class LibraryManagementSystem {
    private ArrayList<LibraryItem> items;
    private ArrayList<Member> members;
    private Scanner scanner;

    public LibraryManagementSystem() {
        this.items = new ArrayList<>();
        this.members = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        LibraryManagementSystem system = new LibraryManagementSystem();
        system.run();
    }

    public void run() {
        boolean running = true;

        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice (1-8): ");

            switch (choice) {
                case 1: addItem(); break;
                case 2: addMember(); break;
                case 3: borrowItem(); break;
                case 4: returnItem(); break;
                case 5: displayAllItems(); break;
                case 6: displayAllMembers(); break;
                case 7: searchItem(); break;
                case 8:
                    running = false;
                    System.out.println("\nThank you for using the Library Management System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n================================================================");
        System.out.println("          LIBRARY MANAGEMENT SYSTEM");
        System.out.println("================================================================");
        System.out.println("1. Add Library Item");
        System.out.println("2. Add Member");
        System.out.println("3. Borrow Item");
        System.out.println("4. Return Item");
        System.out.println("5. Display All Items");
        System.out.println("6. Display All Members");
        System.out.println("7. Search Item by Title/ID");
        System.out.println("8. Exit");
    }

    private void addItem() {
        System.out.println("\n--- Add New Library Item ---");
        System.out.println("Select item type:");
        System.out.println("1. Book");
        System.out.println("2. DVD");
        System.out.println("3. Magazine");
        int type = getIntInput("Enter choice (1-3): ");

        System.out.print("Enter item ID: ");
        String itemId = scanner.nextLine();
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();

        LibraryItem item = null;

        switch (type) {
            case 1:
                System.out.print("Enter ISBN: ");
                String isbn = scanner.nextLine();
                int pages = getIntInput("Enter number of pages: ");
                item = new Book(itemId, title, author, isbn, pages);
                break;
            case 2:
                System.out.print("Enter director: ");
                String director = scanner.nextLine();
                int runtime = getIntInput("Enter runtime (minutes): ");
                item = new DVD(itemId, title, author, director, runtime);
                break;
            case 3:
                int issue = getIntInput("Enter issue number: ");
                item = new Magazine(itemId, title, author, issue);
                break;
            default:
                System.out.println("Invalid type.");
                return;
        }

        if (item != null) {
            items.add(item);
            System.out.println("✓ " + item.getItemType() + " added successfully!");
        }
    }

    private void addMember() {
        System.out.println("\n--- Add New Member ---");
        System.out.print("Enter member ID: ");
        String memberId = scanner.nextLine();
        System.out.print("Enter member name: ");
        String name = scanner.nextLine();
        System.out.print("Enter contact number: ");
        String contact = scanner.nextLine();

        Member member = new Member(memberId, name, contact);
        members.add(member);
        System.out.println("✓ Member added successfully!");
    }

    private void borrowItem() {
        System.out.println("\n--- Borrow Item ---");
        if (members.isEmpty() || items.isEmpty()) {
            System.out.println("Please add members and items first.");
            return;
        }

        System.out.print("Enter member ID: ");
        String memberId = scanner.nextLine();
        Member member = findMemberById(memberId);
        if (member == null) {
            System.out.println("Member not found.");
            return;
        }

        System.out.print("Enter item ID: ");
        String itemId = scanner.nextLine();
        LibraryItem item = findItemById(itemId);
        if (item == null) {
            System.out.println("Item not found.");
            return;
        }

        if (!item.isAvailable()) {
            System.out.println("Item is currently not available.");
            return;
        }

        if (member.borrowItem(item)) {
            System.out.println("✓ " + item.getTitle() + " borrowed successfully.");
        } else {
            System.out.println("Borrowing failed. Please check member limit or item status.");
        }
    }

    private void returnItem() {
        System.out.println("\n--- Return Item ---");
        if (members.isEmpty()) {
            System.out.println("No members found.");
            return;
        }

        System.out.print("Enter member ID: ");
        String memberId = scanner.nextLine();
        Member member = findMemberById(memberId);
        if (member == null) {
            System.out.println("Member not found.");
            return;
        }

        if (member.getBorrowedItems().isEmpty()) {
            System.out.println("This member has no borrowed items.");
            return;
        }

        System.out.print("Enter item ID: ");
        String itemId = scanner.nextLine();
        LibraryItem returnedItem = member.returnItem(itemId);
        if (returnedItem == null) {
            System.out.println("Item not found in member's borrowed list.");
            return;
        }

        int daysLate = getIntInput("Enter number of days late (0 if on time): ");
        double lateFee = 0.0;
        if (daysLate > 0) {
            lateFee = returnedItem.getLateFee(daysLate);
        }

        System.out.printf("Item returned successfully. Late fee: RM%.2f%n", lateFee);
    }

    private void displayAllItems() {
        System.out.println("\n================================================================");
        System.out.println("                    LIBRARY ITEMS");
        System.out.println("================================================================");

        if (items.isEmpty()) {
            System.out.println("No items in the library.");
            return;
        }

        int count = 1;
        for (LibraryItem item : items) {
            System.out.println("\n--- Item " + count + " ---");
            item.displayInfo();
            count++;
        }
    }

    private void displayAllMembers() {
        System.out.println("\n================================================================");
        System.out.println("                    MEMBER LIST");
        System.out.println("================================================================");

        if (members.isEmpty()) {
            System.out.println("No members found.");
            return;
        }

        int count = 1;
        for (Member member : members) {
            System.out.println("\n--- Member " + count + " ---");
            member.displayInfo();
            count++;
        }
    }

    private void searchItem() {
        System.out.println("\n--- Search Item ---");
        System.out.print("Enter title or ID to search: ");
        String query = scanner.nextLine();

        ArrayList<LibraryItem> results = new ArrayList<>();
        for (LibraryItem item : items) {
            if (item.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                item.getItemId().equalsIgnoreCase(query)) {
                results.add(item);
            }
        }

        if (results.isEmpty()) {
            System.out.println("No items found.");
            return;
        }

        System.out.println("\nSearch Results:");
        int count = 1;
        for (LibraryItem item : results) {
            System.out.println("\n--- Result " + count + " ---");
            item.displayInfo();
            count++;
        }
    }

    private Member findMemberById(String memberId) {
        for (Member member : members) {
            if (member.getMemberId().equalsIgnoreCase(memberId)) {
                return member;
            }
        }
        return null;
    }

    private LibraryItem findItemById(String itemId) {
        for (LibraryItem item : items) {
            if (item.getItemId().equalsIgnoreCase(itemId)) {
                return item;
            }
        }
        return null;
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = scanner.nextInt();
                scanner.nextLine(); // consume newline
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // clear invalid input
            }
        }
    }
}

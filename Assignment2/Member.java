package librarymanagement;

import java.util.ArrayList;


public class Member {
    private String memberId;
    private String name;
    private String contact;
    private ArrayList<LibraryItem> borrowedItems;


    public Member(String memberId, String name, String contact) {
        this.memberId = memberId;
        this.name = name;
        this.contact = contact;
        this.borrowedItems = new ArrayList<>();
    }


    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public String getContact() { return contact; }
    public ArrayList<LibraryItem> getBorrowedItems() { return borrowedItems; }


    public void setContact(String contact) {
        if (contact != null && !contact.trim().isEmpty()) {
            this.contact = contact;
        }
    }


    public boolean borrowItem(LibraryItem item) {
        if (item == null || !item.isAvailable()) {
            return false;
        }
        if (borrowedItems.size() >= 5) {  
            System.out.println("Member has reached maximum borrow limit (5 items).");
            return false;
        }
        borrowedItems.add(item);
        item.setAvailable(false);
        return true;
    }


    public LibraryItem returnItem(String itemId) {
        for (LibraryItem item : borrowedItems) {
            if (item.getItemId().equals(itemId)) {
                borrowedItems.remove(item);
                item.setAvailable(true);
                return item;
            }
        }
        return null;
    }


    public void displayInfo() {
        System.out.println("Member ID: " + memberId);
        System.out.println("Name: " + name);
        System.out.println("Contact: " + contact);
        System.out.println("Borrowed Items: " + borrowedItems.size());
        if (!borrowedItems.isEmpty()) {
            System.out.println("  Borrowed items:");
            for (LibraryItem item : borrowedItems) {
                System.out.println("    - " + item.getTitle() + " (" + item.getItemId() + ")");
            }
        }
    }

    @Override
    public String toString() {
        return String.format("Member[%s, %s]", memberId, name);
    }
}

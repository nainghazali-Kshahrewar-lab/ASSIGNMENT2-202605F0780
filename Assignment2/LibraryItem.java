import java.util.ArrayList;

public abstract class LibraryItem {
    private String itemId;
    private String title;
    private String author;
    private boolean isAvailable;

    public LibraryItem(String itemId, String title, String author) {
        this.itemId = itemId;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }

    public String getItemId() { return itemId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isAvailable() { return isAvailable; }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void displayInfo() {
        System.out.println("Item ID: " + itemId);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Available: " + (isAvailable ? "Yes" : "No"));
    }

    public abstract double getLateFee(int daysLate);
    public abstract String getItemType();

    @Override
    public String toString() {
        return String.format("%s: %s by %s", itemId, title, author);
    }
}

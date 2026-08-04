package librarymanagement;

public class Book extends LibraryItem {
    private String isbn;
    private int pages;


    public Book(String itemId, String title, String author, String isbn, int pages) {
        super(itemId, title, author);
        this.isbn = isbn;
        this.pages = pages;
    }


    public String getIsbn() { return isbn; }
    public int getPages() { return pages; }

    public void setIsbn(String isbn) {
        if (isbn != null && !isbn.trim().isEmpty()) {
            this.isbn = isbn;
        }
    }

    public void setPages(int pages) {
        if (pages > 0) {
            this.pages = pages;
        }
    }


    @Override
    public void displayInfo() {
        super.displayInfo();  
        System.out.println("Type: Book");
        System.out.println("ISBN: " + isbn);
        System.out.println("Pages: " + pages);
    }


    @Override
    public double getLateFee(int daysLate) {
        return daysLate * 0.50;
    }

    @Override
    public String getItemType() {
        return "Book";
    }

    @Override
    public String toString() {
        return super.toString() + " (Book)";
    }
}

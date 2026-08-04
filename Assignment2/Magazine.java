public class Magazine extends LibraryItem {
    private int issueNumber;

    public Magazine(String itemId, String title, String author, int issueNumber) {
        super(itemId, title, author);
        this.issueNumber = issueNumber;
    }

    public int getIssueNumber() { return issueNumber; }

    public void setIssueNumber(int issueNumber) {
        if (issueNumber > 0) {
            this.issueNumber = issueNumber;
        }
    }

    public void displayInfo() {
        super.displayInfo();
        System.out.println("Type: Magazine");
        System.out.println("Issue Number: " + issueNumber);
    }

    public double getLateFee(int daysLate) {
        return daysLate * 0.30;
    }

    public String getItemType() {
        return "Magazine";
    }

    public String toString() {
        return super.toString() + " (Magazine)";
    }
}

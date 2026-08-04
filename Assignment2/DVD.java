public class DVD extends LibraryItem {
    private String director;
    private int runtime;

    public DVD(String itemId, String title, String author, String director, int runtime) {
        super(itemId, title, author);
        this.director = director;
        this.runtime = runtime;
    }

    public String getDirector() { return director; }
    public int getRuntime() { return runtime; }

    public void setDirector(String director) {
        if (director != null && !director.trim().isEmpty()) {
            this.director = director;
        }
    }

    public void setRuntime(int runtime) {
        if (runtime > 0) {
            this.runtime = runtime;
        }
    }

    public void displayInfo() {
        super.displayInfo();
        System.out.println("Type: DVD");
        System.out.println("Director: " + director);
        System.out.println("Runtime: " + runtime + " minutes");
    }

    public double getLateFee(int daysLate) {
        return daysLate * 1.00;
    }

    public String getItemType() {
        return "DVD";
    }

    public String toString() {
        return super.toString() + " (DVD)";
    }
}

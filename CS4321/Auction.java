package CS4321;

public class Auction {
    private String title;
    private Category category;

    // Assign category to auction to make it more visible
    public void assignCategory(Category category){
        this.category = category;
    }

    public void displayAuction(){
        String categoryName = (category != null) ? category.getName() : "No category";
        System.out.println("CS4321.Auction: " + title + " | CS4321.Category: " + categoryName);
    }
}

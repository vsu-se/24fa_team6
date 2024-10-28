package CS4321;

public class Item {
    private String name;
    private double startingPrice;
    private boolean isActive;

    public Item(String name, double startingPrice, boolean isActive){
        this.name = name;
        this.startingPrice = startingPrice;
        this.isActive = true;
    }

    public String getName(){
        return name;
    }

    public double getStartingPrice(){
        return startingPrice;
    }

    public boolean isActive(){
        return isActive;
    }

    public void endAuction(){
        this.isActive = false;
    }
}

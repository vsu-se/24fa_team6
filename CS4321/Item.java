package CS4321;

import java.time.LocalDate;

public class Item {
    private String name;
    private double startingPrice;
    private LocalDate endDate;
    private double shippingCost;
    private boolean isActive;
    private Bid currentBid;

    public Item(String name, double startingPrice, LocalDate endDate, double shippingCost) {
        this.name = name;
        this.startingPrice = startingPrice;
        this.endDate = endDate;
        this.shippingCost = shippingCost;
        this.isActive = true;
        this.currentBid = null;
    }

    public String getName() {
        return name;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public double getShippingCost() {
        return shippingCost;
    }

    public boolean isActive() {
        return isActive;
    }

    public void endAuction() {
        this.isActive = false;
    }

    public Bid getCurrentBid(){
        return currentBid;
    }

    public boolean placeBid(Bid bid){
        if(!isActive || (currentBid != null && bid.getAmount() <= currentBid.getAmount())){
            return false;
        }
        this.currentBid = bid;
        return true;
    }
}


package CS4321;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Item {
    private String name;
    private double startingPrice;
    private LocalDate endDate;
    private double shippingCost;
    private boolean isActive;
    private String category;
    private LinkedList<Bid> bids;

    public Item(String name, double startingPrice, LocalDate endDate, double shippingCost) {
        this.name = name;
        this.startingPrice = startingPrice;
        this.endDate = endDate;
        this.shippingCost = shippingCost;
        this.isActive = true;
        this.bids = new LinkedList<>();
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

    public String getCategory(){
        return category;
    }

    public boolean isActive() {
        return isActive;
    }

    public void endAuction() {
        this.isActive = false;
    }

    public Bid getCurrentBid() {
        return bids.peek();
    }

    public String getCurrentBidder() {
        return bids.peek() != null ? bids.peek().getBidderName() : "";
    }

    public boolean hasBids() {
        return bids.peek() != null;
    }

    public double getHighestBid() {
        return bids.peek() != null ? bids.peek().getAmount() : startingPrice; // Use starting price if no bids exist
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public Period getTimeRemaining() {
        LocalDate currentDate = LocalDate.now();
        return Period.between(currentDate, endDate);
    }

    public List<Bid> getBidHistory() {
        return new ArrayList<>(bids); // Assuming `bids` is a list of Bid objects
    }

    public double getSellerCommission(double commissionRate) {
        return getHighestBid() * (commissionRate / 100);
    }

    public boolean placeBid(Bid bid) {
        if (!isActive || (bid.getAmount() <= getHighestBid())) {
            return false;
        }
        this.bids.push(bid);
        return true;
    }

    public boolean hasBidFromUser(String bidderName) {
        for (Bid bid : bids) {
            if (bid.getBidderName().equalsIgnoreCase(bidderName)) {
                return true;
            }
        }
        return false;
    }
}

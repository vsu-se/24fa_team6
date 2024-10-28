package CS4321;

import java.time.LocalDate;

public class Item {
    private String name;
    private double startingPrice;
    private LocalDate endDate;
    private double shippingCost;
    private boolean isActive;

    public Item(String name, double startingPrice, LocalDate endDate, double shippingCost) {
        this.name = name;
        this.startingPrice = startingPrice;
        this.endDate = endDate;
        this.shippingCost = shippingCost;
        this.isActive = true;
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
}


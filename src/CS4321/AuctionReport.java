package CS4321;

import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

public class AuctionReport {
    private CommissionController commissionController;
    private BuyerPremiumController buyerPremiumController;
    private ItemController itemController;
    private AuctionController auctionController;

    public AuctionReport(CommissionController commissionController, BuyerPremiumController buyerPremiumController, ItemController itemController, AuctionController auctionController) {
        this.commissionController = commissionController;
        this.buyerPremiumController = buyerPremiumController;
        this.itemController = itemController;
        this.auctionController = auctionController;
    }

    public String generateSellerReport() {
        List<Item> soldItems = itemController.getItems().stream()
                .filter(item -> !item.isActive()) // Only get concluded auctions
                .sorted(Comparator.comparing(Item::getEndDate).reversed()) // Sort by end date
                .collect(Collectors.toList());

        StringBuilder report = new StringBuilder();
        double totalWinningBids = 0;
        double totalShippingCosts = 0;
        double totalSellerCommissions = 0;

        report.append(String.format("%-20s %-10s %-10s %-10s %-10s\n", "Name", "Price", "Commission", "Shipping", "Date"));
        report.append("-------------------------------------------------------------\n");

        for (Item item : soldItems) {
            double highestBid = item.getHighestBid();
            double sellerCommission = item.getSellerCommission(commissionController.getSellerCommission());
            double shippingCost = item.getShippingCost();

            report.append(String.format("%-20s %-10.2f %-10.2f %-10.2f %-10s\n",
                    item.getName(), highestBid, sellerCommission, shippingCost, item.getEndDate()));

            totalWinningBids += highestBid;
            totalShippingCosts += shippingCost;
            totalSellerCommissions += sellerCommission;
        }

        double totalProfits = totalWinningBids - totalSellerCommissions - totalShippingCosts;

        report.append("\nSummary:\n");
        report.append(String.format("Total Winning Bids: %.2f\n", totalWinningBids));
        report.append(String.format("Total Shipping Costs: %.2f\n", totalShippingCosts));
        report.append(String.format("Total Seller Commissions: %.2f\n", totalSellerCommissions));
        report.append(String.format("Total Profits: %.2f\n", totalProfits));

        return report.toString();
    }

    public String generateBuyerReport(String currentUser) {
        auctionController.checkAndEndAuctions(); // Ensure auctions are checked and ended
        List<Item> purchasedItems = auctionController.getConcludedAuctions().stream()
                .filter(item -> item.hasBidFromUser(currentUser)) // Filter items that current user won
                .sorted(Comparator.comparing(Item::getEndDate).reversed()) // Sort by end date
                .collect(Collectors.toList());

        StringBuilder report = new StringBuilder();
        double totalAmountBought = 0;
        double totalBuyersPremiums = 0;
        double totalShippingCosts = 0;

        report.append(String.format("%-20s %-10s %-10s %-10s %-10s\n", "Name", "Price", "Buyer's Premium", "Shipping", "Date"));
        report.append("-------------------------------------------------------------\n");

        for (Item item : purchasedItems) {
            double highestBid = item.getHighestBid();
            double buyerPremium = highestBid * (buyerPremiumController.getBuyerPremium() / 100);
            double shippingCost = item.getShippingCost();

            report.append(String.format("%-20s %-10.2f %-10.2f %-10.2f %-10s\n",
                    item.getName(), highestBid, buyerPremium, shippingCost, item.getEndDate()));

            totalAmountBought += highestBid;
            totalBuyersPremiums += buyerPremium;
            totalShippingCosts += shippingCost;
        }

        report.append("\nSummary:\n");
        report.append(String.format("Total Amount Bought: %.2f\n", totalAmountBought));
        report.append(String.format("Total Buyer's Premiums: %.2f\n", totalBuyersPremiums));
        report.append(String.format("Total Shipping Costs: %.2f\n", totalShippingCosts));

        return report.toString();
    }
}

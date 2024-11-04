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
        List<Item> items = itemController.getMyAuctions();
        items = items.stream()
                .filter(item -> !item.isActive())
                .sorted(Comparator.comparing(Item::getEndDate).reversed())
                .collect(Collectors.toList());

        StringBuilder report = new StringBuilder();
        double totalWinningBids = 0;
        double totalShippingCosts = 0;
        double totalSellerCommissions = 0;

        report.append(String.format("%-20s %-10s %-10s %-10s %-10s\n", "Name", "Price", "Commission", "Shipping", "Date"));
        report.append("-------------------------------------------------------------\n");

        for (Item item : items) {
            report.append(String.format("%-20s %-10.2f %-10.2f %-10.2f %-10s\n",
                    item.getName(), item.getHighestBid(), item.getSellerCommission(commissionController.getSellerCommission()), item.getShippingCost(), item.getEndDate()));

            totalWinningBids += item.getHighestBid();
            totalShippingCosts += item.getShippingCost();
            totalSellerCommissions += item.getSellerCommission(commissionController.getSellerCommission());
        }

        double totalProfits = totalWinningBids - totalSellerCommissions;

        report.append("\nSummary:\n");
        report.append(String.format("Total Winning Bids: %.2f\n", totalWinningBids));
        report.append(String.format("Total Shipping Costs: %.2f\n", totalShippingCosts));
        report.append(String.format("Total Seller Commissions: %.2f\n", totalSellerCommissions));
        report.append(String.format("Total Profits: %.2f\n", totalProfits));

        return report.toString();
    }

    public String generateBuyerReport(String currentUser) {
        auctionController.checkAndEndAuctions();
        List<Item> items = auctionController.getConcludedAuctions();

        items = items.stream()
                .filter(item -> item.getCurrentBidder().equals(currentUser))
                .sorted(Comparator.comparing(Item::getEndDate).reversed())
                .collect(Collectors.toList());

        StringBuilder report = new StringBuilder();
        double totalAmountBought = 0;
        double totalBuyersPremiums = 0;
        double totalShippingCosts = 0;

        report.append(String.format("%-20s %-10s %-10s %-10s %-10s\n", "Name", "Price", "Buyer's Premium", "Shipping", "Date"));
        report.append("-------------------------------------------------------------\n");

        for (Item item : items) {
            double buyersPremium = item.getHighestBid() * (buyerPremiumController.getBuyerPremium() / 100);
            report.append(String.format("%-20s %-10.2f %-10.2f %-10.2f %-10s\n",
                    item.getName(), item.getHighestBid(), buyersPremium, item.getShippingCost(), item.getEndDate()));

            totalAmountBought += item.getHighestBid();
            totalBuyersPremiums += buyersPremium;
            totalShippingCosts += item.getShippingCost();
        }

        report.append("\nSummary:\n");
        report.append(String.format("Total Amount Bought: %.2f\n", totalAmountBought));
        report.append(String.format("Total Buyer's Premiums: %.2f\n", totalBuyersPremiums));
        report.append(String.format("Total Shipping Costs: %.2f\n", totalShippingCosts));

        return report.toString();
    }
}

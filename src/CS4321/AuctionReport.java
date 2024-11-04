package CS4321;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

public class AuctionReport {
    private CommissionController commissionController;
    private BuyerPremiumController buyerPremiumController;
    private ItemController itemController;

    public AuctionReport(CommissionController commissionController, BuyerPremiumController buyerPremiumController, ItemController itemController) {
        this.commissionController = commissionController;
        this.buyerPremiumController = buyerPremiumController;
        this.itemController = itemController;
    }

    public String generateReport() {
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
}
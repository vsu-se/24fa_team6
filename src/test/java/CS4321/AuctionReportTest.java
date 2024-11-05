package test.java.CS4321;

import CS4321.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuctionReportTest {
    private Commission commission;
    private CommissionController commissionController;
    private BuyerPremium buyerPremium;
    private BuyerPremiumController buyerPremiumController;
    private ItemController itemController;
    private AuctionController auctionController;
    private AuctionReport auctionReport;
    private List<Item> items;

    @BeforeEach
    public void setUp() {
        // Initialize necessary objects for testing
        commission = new Commission(10.0);
        commission.setSellerCommission(10.0);
        commissionController = new CommissionController(commission);

        buyerPremium = new BuyerPremium(5.0);
        buyerPremium.setBuyerPremium(5.0);
        buyerPremiumController = new BuyerPremiumController(buyerPremium);

        items = new ArrayList<>();
        itemController = new ItemController(items);
        auctionController = new AuctionController(items);
        auctionReport = new AuctionReport(commissionController, buyerPremiumController, itemController, auctionController);
    }
    //user story 11
    @Test
    @DisplayName("Generate Seller Report Test")
    public void testGenerateSellerReport() {
        // Prepare test data
        Item item1 = new Item("Item1", 100.0, LocalDate.now().plusDays(5), 10.0);
        item1.placeBid(new Bid("Bidder1", 120.0));
        item1.endAuction();

        Item item2 = new Item("Item2", 50.0, LocalDate.now().plusDays(4), 5.0);
        item2.placeBid(new Bid("Bidder2", 60.0));
        item2.endAuction();

        // Add to items list directly
        items.add(item1);
        items.add(item2);

        // Generate report
        String report = auctionReport.generateSellerReport();

        // Updated expected report string
        String expected = String.format(
                "%-20s %-10s %-10s %-10s %-10s\n" +
                        "-------------------------------------------------------------\n" +
                        "%-20s %-10.2f %-10.2f %-10.2f %-10s\n" +
                        "%-20s %-10.2f %-10.2f %-10.2f %-10s\n\n" +
                        "Summary:\n" +
                        "Total Winning Bids: %.2f\n" +
                        "Total Shipping Costs: %.2f\n" +
                        "Total Seller Commissions: %.2f\n" +
                        "Total Profits: %.2f\n",
                "Name", "Price", "Commission", "Shipping", "Date",
                "Item1", 120.0, 12.0, 10.0, item1.getEndDate(),
                "Item2", 60.0, 6.00, 5.0, item2.getEndDate(),
                180.0, 15.0, 18.0, 147.0 // Adjusted Total Profits to include shipping costs
        );

        assertEquals(expected, report);
    }

    //user story 12
    @Test
    @DisplayName("Generate Buyer Report Test")
    public void testGenerateBuyerReport() {
        // Prepare test data
        Item item1 = new Item("Item1", 100.0, LocalDate.now().minusDays(1), 10.0); // Auction ended yesterday
        item1.placeBid(new Bid("Buyer1", 120.0)); // Buyer1 wins this item
        item1.endAuction();

        Item item2 = new Item("Item2", 50.0, LocalDate.now().minusDays(2), 5.0); // Auction ended two days ago
        item2.placeBid(new Bid("Buyer1", 60.0)); // Buyer1 wins this item
        item2.endAuction();

        // Add to items list directly
        items.add(item1);
        items.add(item2);

        // Ensure auctions are checked and ended
        auctionController.checkAndEndAuctions();

        // Generate report for Buyer1
        String report = auctionReport.generateBuyerReport("Buyer1");

        // Expected report string
        String expected = String.format(
                "%-20s %-10s %-10s %-10s %-10s\n" +
                        "-------------------------------------------------------------\n" +
                        "%-20s %-10.2f %-10.2f %-10.2f %-10s\n" +
                        "%-20s %-10.2f %-10.2f %-10.2f %-10s\n\n" +
                        "Summary:\n" +
                        "Total Amount Bought: %.2f\n" +
                        "Total Buyer's Premiums: %.2f\n" +
                        "Total Shipping Costs: %.2f\n",
                "Name", "Price", "Buyer's Premium", "Shipping", "Date",
                "Item1", 120.0, 6.00, 10.0, item1.getEndDate(),
                "Item2", 60.0, 3.00, 5.0, item2.getEndDate(),
                180.0, 9.00, 15.00
        );

        assertEquals(expected, report);
    }

}

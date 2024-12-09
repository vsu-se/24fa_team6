package src.CS4321;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuctionControllerTest {

    private AuctionController auctionController;
    private List<Item> items;

    @BeforeEach
    void setUp() {
        items = new ArrayList<>();

        items.add(new Item("Item 1", 100.0, LocalDate.of(2024, 12, 15), 5.0)); // Active
        items.add(new Item("Item 2", 150.0, LocalDate.of(2024, 12, 10), 10.0)); // Active
        items.add(new Item("Item 3", 200.0, LocalDate.of(2024, 1, 5), 15.0)); // Past end date
        items.add(new Item("Item 4", 120.0, LocalDate.of(2024, 12, 1), 8.0)); // Inactive
        items.add(new Item("Item 5", 80.0, LocalDate.of(2024, 12, 25), 6.0)); // Active

        items.get(3).endAuction(); // Set Item 4 as inactive

        auctionController = new AuctionController(items);
    }

    @DisplayName("Filter and sort active items by end date")
    @Test
    void testGetActiveAuctions() {
        auctionController.checkAndEndAuctions();
        List<Item> activeAuctions = auctionController.getActiveAuctions();
        List<Item> expectedItems = List.of(items.get(1), items.get(0), items.get(4));

        assertEquals(expectedItems.size(), activeAuctions.size(),
                "Active auctions should match the expected count.");
        assertIterableEquals(expectedItems, activeAuctions,
                "Active auctions should be sorted by end date.");
    }

    @DisplayName("Return empty list if no items are active")
    @Test
    void testGetActiveAuctions_emptyListWhenNoActiveItems() {
        items.forEach(Item::endAuction); // Set all items to inactive

        List<Item> activeAuctions = auctionController.getActiveAuctions();
        assertTrue(activeAuctions.isEmpty(),
                "getActiveAuctions should return an empty list if no items are active.");
    }
    // User Story 9
    @DisplayName("End auctions with past end dates")
    @Test
    void testCheckAndEndAuctions() {
        auctionController.checkAndEndAuctions();

        assertFalse(items.get(2).isActive(),
                "Item 3's auction should be concluded as its end date has passed.");
        assertTrue(items.get(0).isActive(),
                "Item 1's auction should still be active as its end date is in the future.");
    }
    // User Story 10
    @DisplayName("Test getConcludedAuctions retrieves concluded auctions in reverse order")
    @Test
    void testGetConcludedAuctions() {
        auctionController.checkAndEndAuctions(); //This line Ensure auctions with past end dates are concluded
        List<Item> concludedAuctions = auctionController.getConcludedAuctions();
        List<Item> expectedItems = List.of(items.get(3), items.get(2)); // Items set as inactive or past end date
        assertEquals(expectedItems.size(), concludedAuctions.size(), "Concluded auctions should match expected count");
        for (int i = 0; i < expectedItems.size(); i++) {
            assertEquals(expectedItems.get(i), concludedAuctions.get(i), "Concluded auctions should be in reverse end date order");
        }
    }
    // User Story 14
    @DisplayName("Set custom system date")
    @Test
    void testSetTimeSetting() {
        auctionController.setTimeSetting("2024-11-05");

        assertEquals(LocalDate.of(2024, 11, 5), auctionController.getCurrentDate(),
                "System date should match the set date '2024-11-05'.");
    }
    // User Story 15
    @DisplayName("Resume real-time mode after setting custom date")
    @Test
    void testResumeRealTimeMode() {
        auctionController.setTimeSetting("2024-11-05");
        auctionController.setTimeSetting("live");

        assertEquals(LocalDate.now(), auctionController.getCurrentDate(),
                "System date should resume to the current real-time date.");
    }
}

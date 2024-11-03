package CS4321;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @DisplayName("Test Item constructor initializes fields correctly")
    @Test
    void testConstructor_initializesFieldsCorrectly() {
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        double shippingCost = 5.0;
        Item item = new Item("Test Item", 100.0, endDate, shippingCost);
        String expectedName = "Test Item";
        double expectedPrice = 100.0;
        boolean expectedActiveStatus = true;
        LocalDate expectedEndDate = endDate;
        double expectedShippingCost = shippingCost;
        assertEquals(expectedName, item.getName(), "Item name should match the provided value");
        assertEquals(expectedPrice, item.getStartingPrice(), "Starting price should match the provided value");
        assertEquals(expectedEndDate, item.getEndDate(), "End date should match the provided value");
        assertEquals(expectedShippingCost, item.getShippingCost(), "Shipping cost should match the provided value");
        assertEquals(expectedActiveStatus, item.isActive(), "Item should be active upon initialization");
    }

    @DisplayName("Test getName returns the correct name")
    @Test
    void testGetName() {
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        Item item = new Item("Test Item", 100.0, endDate, 5.0);
        String expectedName = "Test Item";
        String actualName = item.getName();
        assertEquals(expectedName, actualName, "getName should return the correct name");
    }

    @DisplayName("Test getStartingPrice returns the correct starting price")
    @Test
    void testGetStartingPrice() {
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        Item item = new Item("Test Item", 100.0, endDate, 5.0);
        double expectedPrice = 100.0;
        double actualPrice = item.getStartingPrice();
        assertEquals(expectedPrice, actualPrice, "getStartingPrice should return the correct starting price");
    }

    @DisplayName("Test getEndDate returns the correct end date")
    @Test
    void testGetEndDate() {
        LocalDate expectedEndDate = LocalDate.of(2024, 12, 31);
        Item item = new Item("Test Item", 100.0, expectedEndDate, 5.0);
        LocalDate actualEndDate = item.getEndDate();
        assertEquals(expectedEndDate, actualEndDate, "getEndDate should return the correct end date");
    }

    @DisplayName("Test getShippingCost returns the correct shipping cost")
    @Test
    void testGetShippingCost() {
        double expectedShippingCost = 5.0;
        Item item = new Item("Test Item", 100.0, LocalDate.of(2024, 12, 31), expectedShippingCost);
        double actualShippingCost = item.getShippingCost();
        assertEquals(expectedShippingCost, actualShippingCost, "getShippingCost should return the correct shipping cost");
    }

    @DisplayName("Test isActive returns true initially")
    @Test
    void testIsActive_initiallyTrue() {
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        Item item = new Item("Test Item", 100.0, endDate, 5.0);
        boolean actualActiveStatus = item.isActive();
        assertTrue(actualActiveStatus, "isActive should return true initially");
    }

    @DisplayName("Test endAuction sets isActive to false")
    @Test
    void testEndAuction_setsIsActiveToFalse() {
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        Item item = new Item("Test Item", 100.0, endDate, 5.0);
        item.endAuction();
        boolean actualActiveStatus = item.isActive();
        assertFalse(actualActiveStatus, "endAuction should set isActive to false");
    }

    //user story 7 Junit test

    @DisplayName("Test getCurrentBid returns null initially")
    @Test
    void testGetCurrentBid_initiallyNull() {
        Item item = new Item("Test Item", 100.0, LocalDate.of(2024, 12, 31), 5.0);
        assertNull(item.getCurrentBid(), "getCurrentBid should return null initially");
    }

    @DisplayName("Test placeBid updates currentBid when bid is higher")
    @Test
    void testPlaceBid_updatesCurrentBidWhenHigher() {
        Item item = new Item("Test Item", 100.0, LocalDate.of(2024, 12, 31), 5.0);
        Bid firstBid = new Bid("Alice", 120.0);
        Bid secondBid = new Bid("Bob", 150.0);

        assertTrue(item.placeBid(firstBid), "placeBid should accept a higher bid and return true");
        assertEquals(firstBid, item.getCurrentBid(), "Current bid should be updated to the first bid");

        assertTrue(item.placeBid(secondBid), "placeBid should accept a higher bid and return true");
        assertEquals(secondBid, item.getCurrentBid(), "Current bid should be updated to the higher bid");
    }

    @DisplayName("Test placeBid rejects bid when auction is inactive")
    @Test
    void testPlaceBid_rejectsBidWhenInactive() {
        Item item = new Item("Test Item", 100.0, LocalDate.of(2024, 12, 31), 5.0);
        item.endAuction();
        Bid bid = new Bid("Alice", 120.0);

        assertFalse(item.placeBid(bid), "placeBid should return false if the auction is inactive");
        assertNull(item.getCurrentBid(), "Current bid should remain null if the auction is inactive");
    }

    @DisplayName("Test placeBid rejects lower bid when a higher bid exists")
    @Test
    void testPlaceBid_rejectsLowerBidWhenHigherBidExists() {
        Item item = new Item("Test Item", 100.0, LocalDate.of(2024, 12, 31), 5.0);
        Bid firstBid = new Bid("Alice", 150.0);
        Bid lowerBid = new Bid("Bob", 120.0);

        item.placeBid(firstBid);
        assertFalse(item.placeBid(lowerBid), "placeBid should reject a lower bid when a higher bid exists");
        assertEquals(firstBid, item.getCurrentBid(), "Current bid should remain the highest bid placed");
    }

    @DisplayName("Test hasBids returns false initially")
    @Test
    void testHasBids_initiallyFalse() {
        Item item = new Item("Test Item", 100.0, LocalDate.of(2024, 12, 31), 5.0);
        assertFalse(item.hasBids(), "hasBids should return false if no bids are placed");
    }

    @DisplayName("Test hasBids returns true when a bid is placed")
    @Test
    void testHasBids_returnsTrueWhenBidPlaced() {
        Item item = new Item("Test Item", 100.0, LocalDate.of(2024, 12, 31), 5.0);
        Bid bid = new Bid("Alice", 120.0);
        item.placeBid(bid);
        assertTrue(item.hasBids(), "hasBids should return true if a bid is placed");
    }

    @DisplayName("Test getHighestBid returns starting price when no bids placed")
    @Test
    void testGetHighestBid_noBids() {
        Item item = new Item("Test Item", 100.0, LocalDate.of(2024, 12, 31), 5.0);
        assertEquals(100.0, item.getHighestBid(), "getHighestBid should return starting price if no bids are placed");
    }

    @DisplayName("Test getHighestBid returns highest bid amount when bids are placed")
    @Test
    void testGetHighestBid_withBids() {
        Item item = new Item("Test Item", 100.0, LocalDate.of(2024, 12, 31), 5.0);
        Bid bid = new Bid("Alice", 120.0);
        item.placeBid(bid);
        assertEquals(120.0, item.getHighestBid(), "getHighestBid should return the highest bid amount when bids are placed");
    }

}

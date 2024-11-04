package CS4321;

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
    void setUp(){
        items = new ArrayList<>();

        items.add(new Item("Item 1", 100.0, LocalDate.of(2024,12,25),5.0));
        items.add(new Item("Item 2", 150.0, LocalDate.of(2024,11,30),10.0));
        items.add(new Item("Item 3", 200.0, LocalDate.of(2024,10,31),15.0));
        items.add(new Item("Item 4", 120.0, LocalDate.of(2024,12,1),8.0));
        items.add(new Item("Item 5", 80.0, LocalDate.of(2024,11,15),6.0));

        items.get(3).endAuction(); //this line sets the item as inactive

        auctionController = new AuctionController(items);
    }

    @DisplayName("Test getActiveAuctions filters and sorts active items by end date")
    @Test
    void testGetActiveAuction(){
        List<Item> activeAuctions = auctionController.getActiveAuctions();
        List<Item> expectedItems = List.of(items.get(2), items.get(4), items.get(1), items.get(0));
        assertEquals(expectedItems.size(), activeAuctions.size(), "Active auctions should match expected count");
        for (int i = 0; i < expectedItems.size(); i++){
            assertEquals(expectedItems.get(i), activeAuctions.get(i), "Active auctions should be sorted by end date");
        }
        //the for loop should verify that each item is in the expected sorted order
    }

    @DisplayName("Test getActiveAuction returns an empty list if no items are active")
    @Test
    void testGetActiveAuctions_emptyListIfNoActiveItems(){
        items.forEach(Item::endAuction); //This line sets all items to inactive

        List<Item> activeAuctions = auctionController.getActiveAuctions();
        assertTrue(activeAuctions.isEmpty(), "getActiveAuctions should return an empty list if no items are active");
    }

    // User Story 9
    @DisplayName("Test checkAndEndAuctions correctly ends auctions with past end dates")
    @Test
    void testCheckAndEndAuctions() {
        // Simulate that today's date is after some items' end dates
        auctionController.checkAndEndAuctions();
        assertFalse(items.get(2).isActive(), "Item 3's auction should be concluded as its end date has passed");
        assertTrue(items.get(0).isActive(), "Item 1's auction should still be active as its end date is in the future");
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



}
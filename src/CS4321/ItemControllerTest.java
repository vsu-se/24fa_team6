package CS4321;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class ItemControllerTest {

    private ItemController itemController;
    private List<Item> itemList;

    @BeforeEach
    void setUp() {
        itemList = new ArrayList<>();
        itemController = new ItemController(itemList);
    }

    @DisplayName("Test constructor initializes items list correctly")
    @Test
    void testConstructor_initializesItemList() {
        assertEquals(itemList, itemController.getItems(), "Constructor should initialize the items list correctly");
        assertTrue(itemController.getItems().isEmpty(), "Items list should be empty initially");
    }

    @DisplayName("Test listItem adds a new item to the list with endDate and shippingCost")
    @Test
    void testListItem_addsNewItemWithDetails() {
        String name = "New Item";
        double startingPrice = 50.0;
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        double shippingCost = 5.0;

        itemController.listItem(name, startingPrice, endDate, shippingCost);

        assertEquals(1, itemController.getItems().size(), "Items list should contain one item after listing");
        Item listedItem = itemController.getItems().get(0);

        assertEquals(name, listedItem.getName(), "Listed item's name should match the input");
        assertEquals(startingPrice, listedItem.getStartingPrice(), "Listed item's starting price should match the input");
        assertEquals(endDate, listedItem.getEndDate(), "Listed item's end date should match the input");
        assertEquals(shippingCost, listedItem.getShippingCost(), "Listed item's shipping cost should match the input");
        assertTrue(listedItem.isActive(), "Listed item should be active initially");
    }

    @DisplayName("Test getItems returns the correct list of items")
    @Test
    void testGetItems_returnsCorrectList() {
        LocalDate endDate1 = LocalDate.of(2024, 12, 31);
        itemController.listItem("Item1", 20.0, endDate1, 2.0);
        LocalDate endDate2 = LocalDate.of(2025, 1, 15);
        itemController.listItem("Item2", 30.0, endDate2, 3.0);

        List<Item> items = itemController.getItems();
        assertEquals(2, items.size(), "getItems should return the correct number of items");

        assertEquals("Item1", items.get(0).getName(), "First item's name should match");
        assertEquals(20.0, items.get(0).getStartingPrice(), "First item's starting price should match");
        assertEquals(endDate1, items.get(0).getEndDate(), "First item's end date should match");
        assertEquals(2.0, items.get(0).getShippingCost(), "First item's shipping cost should match");

        assertEquals("Item2", items.get(1).getName(), "Second item's name should match");
        assertEquals(30.0, items.get(1).getStartingPrice(), "Second item's starting price should match");
        assertEquals(endDate2, items.get(1).getEndDate(), "Second item's end date should match");
        assertEquals(3.0, items.get(1).getShippingCost(), "Second item's shipping cost should match");
    }

    // user story 5 Junit test cases

    @DisplayName("Test getMyAuctions returns sorted list of items by endDate")
    @Test
    void testGetMyAuctions_sortsItemsByEndDate() {
        LocalDate endDate1 = LocalDate.of(2025, 1, 15);
        LocalDate endDate2 = LocalDate.of(2024, 12, 31);
        LocalDate endDate3 = LocalDate.of(2024, 11, 15);

        itemController.listItem("Item1", 20.0, endDate1, 2.0);
        itemController.listItem("Item2", 30.0, endDate2, 3.0);
        itemController.listItem("Item3", 25.0, endDate3, 4.0);

        List<Item> sortedItems = itemController.getMyAuctions();

        assertEquals(3, sortedItems.size(), "getMyAuctions should return the correct number of items");

        assertEquals("Item3", sortedItems.get(0).getName(), "First item should be the one with the earliest end date");
        assertEquals(endDate3, sortedItems.get(0).getEndDate(), "First item's end date should match the earliest date");

        assertEquals("Item2", sortedItems.get(1).getName(), "Second item should be the one with the middle end date");
        assertEquals(endDate2, sortedItems.get(1).getEndDate(), "Second item's end date should match the middle date");

        assertEquals("Item1", sortedItems.get(2).getName(), "Third item should be the one with the latest end date");
        assertEquals(endDate1, sortedItems.get(2).getEndDate(), "Third item's end date should match the latest date");
    }
    //user story 7 Junit test cases
    @DisplayName("Test placeBid successfully places bid on an item")
    @Test
    void testPlaceBid_successfulBid() {
        itemController.listItem("Item1", 100.0, LocalDate.of(2024, 12, 31), 5.0);
        boolean bidPlaced = itemController.placeBid("Item1", "Alice", 120.0);

        assertTrue(bidPlaced, "placeBid should return true when a valid bid is placed");
        Item item = itemController.getItems().get(0);
        assertNotNull(item.getCurrentBid(), "Current bid should be set after a successful bid");
        assertEquals("Alice", item.getCurrentBid().getBidderName(), "Bidder name should match the latest bid");
        assertEquals(120.0, item.getCurrentBid().getAmount(), "Bid amount should match the latest bid");
    }

    @DisplayName("Test placeBid rejects bid if bid amount is lower than current bid")
    @Test
    void testPlaceBid_rejectsLowerBid() {
        itemController.listItem("Item1", 100.0, LocalDate.of(2024, 12, 31), 5.0);
        itemController.placeBid("Item1", "Alice", 120.0);

        boolean bidRejected = itemController.placeBid("Item1", "Bob", 110.0);
        assertFalse(bidRejected, "placeBid should return false when a lower bid is placed");
        Item item = itemController.getItems().get(0);
        assertEquals(120.0, item.getCurrentBid().getAmount(), "Current bid should remain the highest bid placed");
    }

    @DisplayName("Test placeBid fails if item not found")
    @Test
    void testPlaceBid_itemNotFound() {
        boolean bidResult = itemController.placeBid("NonExistentItem", "Alice", 150.0);
        assertFalse(bidResult, "placeBid should return false when the item does not exist");
    }

    @DisplayName("Test placeBid fails if item auction is inactive")
    @Test
    void testPlaceBid_inactiveAuction() {
        itemController.listItem("Item1", 100.0, LocalDate.of(2024, 12, 31), 5.0);
        itemController.getItems().get(0).endAuction();

        boolean bidResult = itemController.placeBid("Item1", "Alice", 120.0);
        assertFalse(bidResult, "placeBid should return false when the auction is inactive");
    }
}


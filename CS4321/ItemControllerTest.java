package CS4321;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
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

    @DisplayName("Test listItem adds a new item to the list")
    @Test
    void testListItem_addsNewItem() {
        String name = "New Item";
        double startingPrice = 50.0;
        itemController.listItem(name, startingPrice);

        assertEquals(1, itemController.getItems().size(), "Items list should contain one item after listing");
        Item listedItem = itemController.getItems().get(0);

        assertEquals(name, listedItem.getName(), "Listed item's name should match the input");
        assertEquals(startingPrice, listedItem.getStartingPrice(), "Listed item's starting price should match the input");
        assertTrue(listedItem.isActive(), "Listed item should be active initially");
    }

    @DisplayName("Test getItems returns the correct list of items")
    @Test
    void testGetItems_returnsCorrectList() {
        itemController.listItem("Item1", 20.0);
        itemController.listItem("Item2", 30.0);

        List<Item> items = itemController.getItems();
        assertEquals(2, items.size(), "getItems should return the correct number of items");

        assertEquals("Item1", items.get(0).getName(), "First item's name should match");
        assertEquals(20.0, items.get(0).getStartingPrice(), "First item's starting price should match");

        assertEquals("Item2", items.get(1).getName(), "Second item's name should match");
        assertEquals(30.0, items.get(1).getStartingPrice(), "Second item's starting price should match");
    }
}

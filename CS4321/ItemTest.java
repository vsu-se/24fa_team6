package CS4321;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @DisplayName("Test Item constructor initializes fields correctly")
    @Test
    void testConstructor_initializesFieldsCorrectly() {
        Item item = new Item("Test Item", 100.0);
        String expectedName = "Test Item";
        double expectedPrice = 100.0;
        boolean expectedActiveStatus = true;

        assertEquals(expectedName, item.getName(), "Item name should match the provided value");
        assertEquals(expectedPrice, item.getStartingPrice(), "Starting price should match the provided value");
        assertEquals(expectedActiveStatus, item.isActive(), "Item should be active upon initialization");
    }

    @DisplayName("Test getName returns the correct name")
    @Test
    void testGetName() {
        Item item = new Item("Test Item", 100.0);
        String expectedName = "Test Item";
        String actualName = item.getName();

        assertEquals(expectedName, actualName, "getName should return the correct name");
    }

    @DisplayName("Test getStartingPrice returns the correct starting price")
    @Test
    void testGetStartingPrice() {
        Item item = new Item("Test Item", 100.0);
        double expectedPrice = 100.0;
        double actualPrice = item.getStartingPrice();

        assertEquals(expectedPrice, actualPrice, "getStartingPrice should return the correct starting price");
    }

    @DisplayName("Test isActive returns true initially")
    @Test
    void testIsActive_initiallyTrue() {
        Item item = new Item("Test Item", 100.0);
        boolean actualActiveStatus = item.isActive();

        assertTrue(actualActiveStatus, "isActive should return true initially");
    }

    @DisplayName("Test endAuction sets isActive to false")
    @Test
    void testEndAuction_setsIsActiveToFalse() {
        Item item = new Item("Test Item", 100.0);
        item.endAuction();
        boolean actualActiveStatus = item.isActive();

        assertFalse(actualActiveStatus, "endAuction should set isActive to false");
    }
}

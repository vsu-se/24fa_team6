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
}

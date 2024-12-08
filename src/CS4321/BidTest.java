package src.CS4321;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BidTest {

    //user story 7
    @DisplayName("Test Bid constructor initializes fields correctly")
    @Test
    void testConstructor_initializesFieldsCorrectly() {
        Bid bid = new Bid("John Doe", 500.0);

        String expectedBidderName = "John Doe";
        double expectedAmount = 500.0;

        assertEquals(expectedBidderName, bid.getBidderName());
        assertEquals(expectedAmount, bid.getAmount());
    }

    @DisplayName("Test getBidderName returns the correct bidder name")
    @Test
    void testGetBidderName() {
        Bid bid = new Bid("Alice Smith", 250.0);

        String expectedBidderName = "Alice Smith";
        String actualBidderName = bid.getBidderName();

        assertEquals(expectedBidderName, actualBidderName);
    }

    @DisplayName("Test getAmount returns the correct bid amount")
    @Test
    void testGetAmount() {
        Bid bid = new Bid("Bob Brown", 1000.0);

        double expectedAmount = 1000.0;
        double actualAmount = bid.getAmount();

        assertEquals(expectedAmount, actualAmount);
    }
}
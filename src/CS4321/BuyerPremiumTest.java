package CS4321;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuyerPremiumTest {

    @DisplayName("Test getting initial buyer premium")
    @Test
    void testGetBuyerPremium() {
        double initialPremium = 5.0;
        BuyerPremium buyerPremium = new BuyerPremium(initialPremium);
        double expectedPremium = 5.0;
        double actualPremium = buyerPremium.getBuyerPremium();
        assertEquals(expectedPremium, actualPremium);
    }

    @DisplayName("Test setting new buyer premium")
    @Test
    void testSetBuyerPremium() {
        BuyerPremium buyerPremium = new BuyerPremium(5.0);
        double newPremium = 7.5;
        buyerPremium.setBuyerPremium(newPremium);
        double actualPremium = buyerPremium.getBuyerPremium();
        assertEquals(newPremium, actualPremium);
    }
}
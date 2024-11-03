package CS4321;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BuyerPremiumControllerTest {

    private BuyerPremiumController buyerPremiumController;
    private BuyerPremium buyerPremium;

    @BeforeEach
    void setUp() {
        buyerPremium = new BuyerPremium(5.0); // Initialize with a buyer premium of 5.0
        buyerPremiumController = new BuyerPremiumController(buyerPremium);
    }

    @DisplayName("Test getting buyer premium from controller")
    @Test
    void testGetBuyerPremium_FromController() {
        double expectedPremium = 5.0;
        double actualPremium = buyerPremiumController.getBuyerPremium();
        assertEquals(expectedPremium, actualPremium);
    }

    @DisplayName("Test setting new buyer premium from controller")
    @Test
    void testSetBuyerPremium_FromController() {
        double newPremium = 8.0;
        buyerPremiumController.setBuyerPremium(newPremium);
        double actualPremium = buyerPremiumController.getBuyerPremium();
        assertEquals(newPremium, actualPremium);
    }
}
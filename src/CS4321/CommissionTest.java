package src.CS4321;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class CommissionTest {

    @DisplayName("Test getting initial seller commission")
    @Test
    void testGetSellerCommission() {
        double initialCommission = 10.0;
        Commission commission = new Commission(initialCommission);
        double expectedCommission = 10.0;
        double actualCommission = commission.getSellerCommission();
        assertEquals(expectedCommission, actualCommission);
    }

    @DisplayName("Test setting seller commission")
    @Test
    void testSetSellerCommission() {
        Commission commission = new Commission(10.0);
        double newCommission = 15.0;
        commission.setSellerCommission(newCommission);
        double actualCommission = commission.getSellerCommission();
        assertEquals(newCommission, actualCommission);
    }
}
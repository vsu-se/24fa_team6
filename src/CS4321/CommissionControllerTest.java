package src.CS4321;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommissionControllerTest {

    private CommissionController commissionController;
    private Commission commission;

    @BeforeEach
    void setUp() {
        commission = new Commission(10.0); // Initial seller commission
        commissionController = new CommissionController(commission);
    }

    @DisplayName("Test getting seller commission from controller")
    @Test
    void testGetSellerCommission_FromController() {
        double expectedCommission = 10.0;
        double actualCommission = commissionController.getSellerCommission();
        assertEquals(expectedCommission, actualCommission);
    }

    @DisplayName("Test setting new seller commission from controller")
    @Test
    void testSetSellerCommission_FromController() {
        double newCommission = 20.0;
        commissionController.setSellerCommission(newCommission);
        double actualCommission = commissionController.getSellerCommission();
        assertEquals(newCommission, actualCommission);
    }
}
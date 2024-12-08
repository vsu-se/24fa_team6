package src.CS4321;

public class CommissionController {
    private Commission commission;

    public CommissionController(Commission commission){
        this.commission = commission;
    }

    public void setSellerCommission(double newCommission) {
        commission.setSellerCommission(newCommission);
    }

    public double getSellerCommission() {
        return commission.getSellerCommission();
    }
}

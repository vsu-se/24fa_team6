package src.CS4321;

public class Commission {
    private double sellerCommission;

    public Commission (double sellerCommission){
        this.sellerCommission = sellerCommission;
    }

    public double getSellerCommission(){
        return sellerCommission;
    }

    public void setSellerCommission(double sellerCommission){
        this.sellerCommission = sellerCommission;
    }
}

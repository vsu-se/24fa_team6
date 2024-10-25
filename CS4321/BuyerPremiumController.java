package CS4321;

public class BuyerPremiumController {
    private BuyerPremium buyerPremium;

    public BuyerPremiumController(BuyerPremium buyerPremium){
        this.buyerPremium = buyerPremium;
    }

    public void setBuyerPremium(double newPremium){
        buyerPremium.setBuyerPremium(newPremium);
    }

    public double getBuyerPremium(){
        return buyerPremium.getBuyerPremium();
    }
}

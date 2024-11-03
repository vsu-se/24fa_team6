package CS4321;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AuctionController {
    private List<Item> items;

    public AuctionController(List<Item> items){
        this.items = items;
    }

    //This method shows active auctions sorted by the end date
    public List<Item> getActiveAuction(){
        return items.stream()
                .filter(Item::isActive)
                .sorted(Comparator.comparing(Item::getEndDate))
                .collect(Collectors.toList());
    }

    public boolean placeBid(String itemName, Bid bid){
        for(Item item: items){
            if(item.getName().equalsIgnoreCase(itemName)){
                return item.placeBid(bid);
            }
        }
        return false;
    }
}

package CS4321;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AuctionController {
    private List<Item> items;

    public AuctionController(List<Item> items){
        this.items = items;
    }

    //This method shows active auctions sorted by the end date
    public List<Item> getActiveAuctions(){
        return items.stream()
                .filter(Item::isActive)
                .sorted(Comparator.comparing(Item::getEndDate))
                .collect(Collectors.toList());
    }

    public boolean itemExists(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
    }

    public boolean placeBid(String itemName, Bid bid){
        for(Item item: items){
            if(item.getName().equalsIgnoreCase(itemName)){
                return item.placeBid(bid);
            }
        }
        return false;
    }

    public void endAuction(Item item) {
        item.setActive(false);
        // May need some more code here
    }

    public void checkAndEndAuctions() {
        List<Item> activeAuctions = getActiveAuctions();
        LocalDate now = LocalDate.now();
        for (Item item : activeAuctions) {
            if (item.getEndDate().isBefore(now) || item.getEndDate().isEqual(now)) {
                endAuction(item);
            }
        }
    }

    public List<Item> getConcludedAuctions() {
        return items.stream()
                .filter(item -> !item.isActive())
                .sorted(Comparator.comparing(Item::getEndDate).reversed())
                .collect(Collectors.toList());
    }
}

package src.CS4321;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AuctionController {
    private List<Item> items;
    private String timeSetting;

    public AuctionController(List<Item> items, String timeSetting){
        this.items = items;
        this.timeSetting = timeSetting;
    }

    public AuctionController(List<Item> items){
        this(items, "live");
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
        // May need some more code here later on
    }

    public LocalDate getCurrentDate(){
        if (timeSetting.equals("live")) {
            return LocalDate.now();
        }
        else {
            return LocalDate.parse(timeSetting);
        }
    }

    public void setTimeSetting(String timeSetting) {
        this.timeSetting = timeSetting;
    }

    public void checkAndEndAuctions() {
        List<Item> activeAuctions = getActiveAuctions();
        LocalDate now = getCurrentDate();
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

    public List<Bid> getBidHistory(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item.getBidHistory();
            }
        }
        return new ArrayList<>();
    }

}

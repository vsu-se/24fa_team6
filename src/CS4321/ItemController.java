package src.CS4321;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ItemController {
    private List<Item> items;

    public ItemController(List<Item> items) {
        this.items = items;
    }

    public void listItem(String name, double startingPrice, LocalDate endDate, double shippingCost) {
        Item newItem = new Item(name, startingPrice, endDate, shippingCost);
        items.add(newItem);
    }

    public List<Item> getItems() {
        return items;
    }

    public List<Item> getMyAuctions() {
        return items.stream()
                .sorted(Comparator.comparing(Item::getEndDate))
                .collect(Collectors.toList());
    }

    public boolean placeBid(String itemName, String bidderName, double bidAmount) {
        for (Item item : items) {
            if (item.getName().equals(itemName)) {
                Bid newBid = new Bid(bidderName, bidAmount); // Create a new Bid with the bidder's name and amount
                return item.placeBid(newBid); // Attempt to place the bid on the item
            }
        }
        return false; // If the item is not found or bid placement fails
    }

//    public void clearItems() {
//        items.clear();
//    }
//
//    public Item getItemByName(String name) {
//        return items.stream()
//                .filter(item -> item.getName().equalsIgnoreCase(name))
//                .findFirst()
//                .orElse(null);
//    }
}

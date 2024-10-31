package CS4321;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ItemController {
    private List <Item> items;

    public ItemController(List<Item> items){
        this.items = items;
    }

    public void listItem(String name, double startingPrice, LocalDate endDate, double shippingCost) {
        Item newItem = new Item(name, startingPrice, endDate, shippingCost);
        items.add(newItem);
    }

    public List<Item> getItems(){
        return items;
    }

    public List<Item> getMyAuctions(){
        return items.stream()
                .sorted(Comparator.comparing(Item::getEndDate))
                .collect(Collectors.toList());
    }
}

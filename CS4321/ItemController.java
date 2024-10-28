package CS4321;
import java.time.LocalDate;
import java.util.List;
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
}

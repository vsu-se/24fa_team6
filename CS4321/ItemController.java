package CS4321;
import java.util.List;
public class ItemController {
    private List <Item> items;

    public ItemController(List<Item> items){
        this.items = items;
    }

    public void listItem(String name, double startingPrice){
        Item newItem = new Item(name, startingPrice);
        items.add(newItem);
    }

    public List<Item> getItems(){
        return items;
    }
}

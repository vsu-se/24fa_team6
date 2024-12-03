package src.CS4321;

import java.util.List;

public class CategoryController {
    private List<Category> categories;

    public CategoryController(List<Category> categories){
        this.categories = categories;
    }

    public void addCategory (String categoryName){
        Category newCategory = new Category(categoryName);
        categories.add(newCategory);
    }

    public List<Category> getCategories(){
        return categories;
    }

//    public void clearCategories() {
//        categories.clear();
//    }
}

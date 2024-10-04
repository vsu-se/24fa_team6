package CS4321;

import java.util.ArrayList;
import java.util.List;

public class Admin {
    private List<Category> categories;

    public Admin() {
        this.categories = new ArrayList<>();
    }

    // Method to add a new category
    public void addCategory(String categoryName) {
        Category category = new Category(categoryName);
        categories.add(category);
        System.out.println("Category '" + categoryName + "' added.");
    }

    // Display all categories
    public void displayCategories() {
        if (categories.isEmpty()) {
            System.out.println("No categories available.");
        } else {
            System.out.println("Available Categories:");
            for (Category category : categories) {
                System.out.println("- " + category.getName());
            }
        }
    }

    // Get a category by name
    public Category getCategoryByName(String name) {
        for (Category category : categories) {
            if (category.getName().equalsIgnoreCase(name)) {
                return category;
            }
        }
        System.out.println("Category '" + name + "' not found.");
        return null;
    }
}



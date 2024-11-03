package CS4321;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryControllerTest {

    private CategoryController categoryController;
    private List<Category> categories;

    @BeforeEach
    void setUp() {
        categories = new ArrayList<>();
        categoryController = new CategoryController(categories);
    }

    @DisplayName("Test adding a valid category - Electronics")
    @Test
    void testAddValidCategory() {

        String ExpectedCategoryName = "Electronics";
        int expectedSize = 1;

        categoryController.addCategory(ExpectedCategoryName);
        int actualSize = categories.size();
        String actualName = categories.get(0).getName();

        assertEquals(expectedSize, actualSize);
        assertEquals(ExpectedCategoryName, actualName);
    }

    @DisplayName("Test adding multiple categories")
    @Test
    void testAddMultipleCategories() {
        String ExpectedCategoryName1 = "Electronics";
        String ExpectedCategoryName2 = "Home Appliances";
        int expectedSize = 2;

        categoryController.addCategory(ExpectedCategoryName1);
        categoryController.addCategory(ExpectedCategoryName2);
        int actualSize = categories.size();
        String actualName1 = categories.get(0).getName();
        String actualName2 = categories.get(1).getName();

        assertEquals(expectedSize, actualSize);
        assertEquals(ExpectedCategoryName1, actualName1);
        assertEquals(ExpectedCategoryName2, actualName2);
    }

    @DisplayName("Test retrieving categories")
    @Test
    void testGetCategories() {
        String ExpectedCategoryName = "ELectronics";
        categoryController.addCategory(ExpectedCategoryName);

        List<Category> retrievedCategories = categoryController.getCategories();
        String AcutalName = retrievedCategories.get(0).getName();

        assertEquals(1, retrievedCategories.size());
        assertEquals(ExpectedCategoryName, AcutalName);
    }

    @DisplayName("Test adding a null category name")
    @Test
    void testAddNullCategoryName() {

        String ExpectedCategoryName = null;

        categoryController.addCategory(ExpectedCategoryName);
        int actualSize = categories.size();

        assertEquals(1, actualSize);
        assertNull(categories.get(0).getName());
    }

    @DisplayName("Test adding an empty category name")
    @Test
    void testAddEmptyCategoryName() {

        String ExpectedCategoryName = "";

        categoryController.addCategory(ExpectedCategoryName);
        int actualSize = categories.size();
        String actualName = categories.get(0).getName();

        assertEquals(1, actualSize);
        assertEquals(ExpectedCategoryName, actualName); // Check if empty name is stored
    }
}




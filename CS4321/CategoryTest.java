package CS4321;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CategoryTest {

    @DisplayName("Category 1 - Name = Electronics")
    @Test
    void testGetName_Category1() {
        Category c = new Category("Electronics");
        String expectedName = "Electronics";
        String actualName = c.getName();
        assertEquals(expectedName, actualName);
    }

    @DisplayName("Category 2 - Set new name = Home Appliances")
    @Test
    void setName() {
        Category c = new Category("Electronics");
        String expectedName = "Home Appliances";
        c.setName(expectedName);
        String actualName = c.getName();
        assertEquals(expectedName, actualName);
    }

    @DisplayName("Category 3 - Default name after initialization")
    @Test
    void testDefaultName_Category3() {
        String expectedName = "Electronics";
        Category c = new Category(expectedName);
        String actualName = c.getName();
        assertEquals(expectedName, actualName);
    }

    @DisplayName("Category 4 - Set empty name")
    @Test
    void testSetEmptyName_Category4() {
        Category c = new Category("Electronics");
        String expectedName = "";
        c.setName(expectedName);
        String actualName = c.getName();
        assertEquals(expectedName, actualName);
    }

    @DisplayName("Category 5 - Set name to null")
    @Test
    void testSetNullName_Category5() {
        Category c = new Category("Electronics");
        c.setName(null);
        String actualName = c.getName();
        assertNull(actualName);  // Ensure the name is now null
    }

}
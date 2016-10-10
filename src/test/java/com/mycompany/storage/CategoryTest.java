package com.mycompany.storage;

import com.mycompany.domain.Category;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author Anton Fomin
 */
public class CategoryTest {

    @Test
    public void testEquals() {
        Category category = new Category();
        Category otherCategory = new Category();
        assertCategoryEqualsAndHashcode(category, otherCategory);

        category.setName("Category");
        assertCategoryNotEqualsAndHashcode(category, otherCategory);

        otherCategory.setName("Category");
        assertCategoryEqualsAndHashcode(category, otherCategory);

        category.setId((long) 15);
        assertCategoryNotEqualsAndHashcode(category, otherCategory);

        otherCategory.setId((long) 15);
        assertCategoryEqualsAndHashcode(category, otherCategory);

        category.setName(null);
        otherCategory.setName(null);
        assertCategoryEqualsAndHashcode(category, otherCategory);

        category.setId((long) 13);
        assertCategoryNotEqualsAndHashcode(category, otherCategory);
    }

    private void assertCategoryEqualsAndHashcode(Category category, Category otherCategory) {
        assertTrue(category.equals(otherCategory));
        assertEquals(category.hashCode(), otherCategory.hashCode());
    }

    private void assertCategoryNotEqualsAndHashcode(Category category, Category otherCategory) {
        assertFalse(category.equals(otherCategory));
        assertThat(category.hashCode(), not(equalTo(otherCategory.hashCode())));
    }
}

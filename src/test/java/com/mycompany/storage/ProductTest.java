package com.mycompany.storage;

import com.mycompany.domain.Product;
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
public class ProductTest {

    @Test
    public void testEquals() {
        Product product = new Product();
        Product otherProduct = new Product();
        assertProductEqualsAndHashcode(product, otherProduct);

        product.setName("Product");
        assertProductNotEqualsAndHashcode(product, otherProduct);

        otherProduct.setName("Product");
        assertProductEqualsAndHashcode(product, otherProduct);

        product.setId(23);
        assertProductNotEqualsAndHashcode(product, otherProduct);

        otherProduct.setId(23);
        assertProductEqualsAndHashcode(product, otherProduct);

        product.setName(null);
        otherProduct.setName(null);
        assertProductEqualsAndHashcode(product, otherProduct);

        product.setId(15);
        assertProductNotEqualsAndHashcode(product, otherProduct);
    }

    private void assertProductEqualsAndHashcode(Product product, Product otherProduct) {
        assertTrue(product.equals(otherProduct));
        assertEquals(product.hashCode(), otherProduct.hashCode());
    }

    private void assertProductNotEqualsAndHashcode(Product product, Product otherProduct) {
        assertFalse(product.equals(otherProduct));
        assertThat(product.hashCode(), not(equalTo(otherProduct.hashCode())));
    }
}

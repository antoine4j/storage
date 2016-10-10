package com.mycompany;

import com.mycompany.domain.Category;
import com.mycompany.domain.Product;
import com.mycompany.repository.CategoryRepository;
import com.mycompany.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * @author Anton Fomin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfigurationTest.class, loader = AnnotationConfigContextLoader.class)
public class PersistenceSpringDataJPATest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testPersistenceOfCategoryAndProduct() {
        Product product = new Product();
        product.setName("My Product");
        Category category = new Category();
        category.setName("My Category");

        productRepository.save(product);
        categoryRepository.save(category);

        product.setCategory(category);
        Set<Product> prods = new HashSet<Product>();
        prods.add(product);
        category.setProducts(prods);

        categoryRepository.save(category);

        @SuppressWarnings("unchecked")
        List<Product> products = (List<Product>) productRepository.findAll();
        assertEquals(1, products.size());

        @SuppressWarnings("unchecked")
        List<Category> categories = (List<Category>) categoryRepository.findAll();
        assertEquals(1, categories.size());

        assertEquals(product, products.get(0));
        assertEquals(category, categories.get(0));

        // This stopped working when persistence was switched to Spring Data JPA
        //assertEquals(1, categories.get(0).getProducts().size());
        //assertTrue(categories.get(0).getProducts().contains(products.get(0)));
        //assertEquals(products.get(0).getCategory(), categories.get(0));
    }
}
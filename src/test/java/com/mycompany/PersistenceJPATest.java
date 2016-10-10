package com.mycompany;

import com.mycompany.domain.Category;
import com.mycompany.domain.Product;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Anton Fomin
 */
public class PersistenceJPATest {

    private static EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    @BeforeClass
    public static void beforeClass() throws Exception {
        entityManagerFactory = Persistence.createEntityManagerFactory("com.mycompany");
    }

    @AfterClass
    public static void afterClass() {
        entityManagerFactory.close();
    }

    @Before
    public void before() {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
    }

    @After
    public void after() {
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Test
    public void testPersistenceOfCategoryAndProduct() {
        Product product = new Product();
        product.setName("My Product");
        Category category = new Category();
        category.setName("My Category");

        entityManager.persist(product);
        entityManager.persist(category);

        product.setCategory(category);
        Set<Product> prods = new HashSet<Product>();
        prods.add(product);
        category.setProducts(prods);

        entityManager.persist(category);

        @SuppressWarnings("unchecked")
        List<Product> products = entityManager.createQuery( "from Product" ).getResultList();
        assertEquals(1, products.size());

        @SuppressWarnings("unchecked")
        List<Category> categories = entityManager.createQuery( "from Category" ).getResultList();
        assertEquals(1, categories.size());

        assertEquals(product, products.get(0));
        assertEquals(category, categories.get(0));

        assertEquals(1, categories.get(0).getProducts().size());
        assertTrue(categories.get(0).getProducts().contains(products.get(0)));
        assertEquals(products.get(0).getCategory(), categories.get(0));
    }
}
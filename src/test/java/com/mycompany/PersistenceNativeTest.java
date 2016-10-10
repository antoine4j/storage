package com.mycompany;

import com.mycompany.domain.Category;
import com.mycompany.domain.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Anton Fomin
 */
public class PersistenceNativeTest {

    private static SessionFactory sessionFactory;

    private Session session;

    @BeforeClass
    public static void beforeClass() throws Exception {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy( registry );
            throw e;
        }
    }

    @AfterClass
    public static void afterClass() {
        sessionFactory.close();
    }

    @Before
    public void before() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @After
    public void after() {
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void testPersistenceOfCategoryAndProduct() {
        Product product = new Product();
        product.setName("My Product");
        Category category = new Category();
        category.setName("My Category");

        session.save(product);
        session.save(category);

        product.setCategory(category);
        Set<Product> prods = new HashSet<Product>();
        prods.add(product);
        category.setProducts(prods);

        session.save(category);

        @SuppressWarnings("unchecked")
        List<Product> products = session.createQuery( "from Product" ).list();
        assertEquals(1, products.size());

        @SuppressWarnings("unchecked")
        List<Category> categories = session.createQuery( "from Category" ).list();
        assertEquals(1, categories.size());

        assertEquals(product, products.get(0));
        assertEquals(category, categories.get(0));

        assertEquals(1, categories.get(0).getProducts().size());
        assertTrue(categories.get(0).getProducts().contains(products.get(0)));
        assertEquals(products.get(0).getCategory(), categories.get(0));
    }
}
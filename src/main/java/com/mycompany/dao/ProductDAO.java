package com.mycompany.dao;

import com.mycompany.domain.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Anton Fomin
 */
public class ProductDAO {

    private static SessionFactory sessionFactory;

    private Session session;

    public void save(Product product) throws Exception {
        if (sessionFactory == null) {
            initFactory();
        }

        session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(product);

        session.getTransaction().commit();
        session.close();
    }

    public Set<Product> getAll() throws Exception {
        if (sessionFactory == null) {
            initFactory();
        }

        session = sessionFactory.openSession();
        session.beginTransaction();

        List productList = session.createCriteria(Product.class).list();

        @SuppressWarnings("unchecked")
        Set<Product> productSet = new HashSet<Product>(productList);

        session.getTransaction().commit();
        session.close();

        return productSet;
    }

    public Product get(int id) throws Exception {
        if (sessionFactory == null) {
            initFactory();
        }

        session = sessionFactory.openSession();
        session.beginTransaction();

        Product product = session.get(Product.class, id);

        session.getTransaction().commit();
        session.close();

        return product;
    }

    public void delete(int id) throws Exception {
        if (sessionFactory == null) {
            initFactory();
        }

        session = sessionFactory.openSession();
        session.beginTransaction();

        Product product = session.get(Product.class, id);
        if (product != null) {
            session.delete(product);
        } else {
            throw new Exception("Product with Id: '" + id + "' doesn't exist, and therefore can't be deleted.");
        }

        session.getTransaction().commit();
        session.close();
    }

    private void initFactory() throws Exception {
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

    @Override
    public void finalize() throws Throwable {
        sessionFactory.close();
        super.finalize();
    }
}

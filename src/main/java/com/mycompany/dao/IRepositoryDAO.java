package com.mycompany.dao;

import com.mycompany.domain.Category;
import com.mycompany.domain.Product;

import java.util.Set;

/**
 * @author Anton Fomin
 */
public interface IRepositoryDAO {

    public Product saveProduct(Product product);

    public Product getProduct(Product/* Criteria */ product);

    public Set<Product> getProducts(Product/* Criteria */ product);

    public void deleteProduct(Product product);


    public Category saveCategory(Category category);

    public Category getCategory(Category/* Criteria */ category);

    public Set<Category> getCategories(Category/* Criteria */ category);

    public void deleteCategory(Category category);



}

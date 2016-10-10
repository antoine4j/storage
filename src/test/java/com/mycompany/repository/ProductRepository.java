package com.mycompany.repository;

import com.mycompany.domain.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * @author Anton Fomin
 */
@Component
public interface ProductRepository extends CrudRepository<Product, Long> {
}

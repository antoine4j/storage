package com.mycompany.repository;

import com.mycompany.domain.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * @author Anton Fomin
 */
@Component
public interface CategoryRepository extends CrudRepository<Category, Long> {
}

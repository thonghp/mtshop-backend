package com.mtshop.category;

import com.mtshop.common.entity.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

    List<Category> findAllByEnabledTrueOrderByNameAsc(); // select c from Category c where c.enabled = true order by c.name asc

    Category findAllByAliasAndEnabledTrue(String alias); // select c from Category c where c.enabled = true and c.alias = ?1
}

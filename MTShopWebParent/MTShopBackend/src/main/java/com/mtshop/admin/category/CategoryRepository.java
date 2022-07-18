package com.mtshop.admin.category;

import com.mtshop.common.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {
    @Query("UPDATE Category c SET c.enabled = ?2 WHERE c.id = ?1")
    @Modifying
    void updateEnabledStatus(Integer id, boolean enabled);

    List<Category> findByParentIsNull(Sort sort);

    Page<Category> findByParentIsNull(Pageable pageable);

    Page<Category> findByName(Pageable pageable, String keyword);

    Category findByName(String name);

    Category findByAlias(String alias);

    Long countById(Integer id);


}

package com.mtshop.category;

import com.mtshop.common.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class CategoryRepositoryTests {

    @Autowired
    private CategoryRepository repo;

    @Test
    public void testListEnabledCategories() {
        List<Category> categories = repo.findAllByEnabledTrueOrderByNameAsc();
        categories.forEach(category -> {
            System.out.println(category.getName() + "--" + category.isEnabled());
        });
    }

    @Test
    public void testFindCategoryByAlias() {
        String alias = "Laptop";
        Category category = repo.findAllByAliasAndEnabledTrue(alias);

        assertThat(category).isNotNull();
    }
}

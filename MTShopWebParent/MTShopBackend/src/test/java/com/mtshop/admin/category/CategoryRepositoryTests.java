package com.mtshop.admin.category;

import com.mtshop.common.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class CategoryRepositoryTests {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateRootCategory() {
        Category category = new Category();
        category.setAlias("Linh kiện");
        category.setName("Linh kiện");

        Category savedCategory = categoryRepository.save(category);

        assertThat(savedCategory.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateSubCategory() {
        Category parent = entityManager.find(Category.class, 2);

        Category subCategory = new Category();
        subCategory.setName("Intel Core i3");
        subCategory.setAlias("Intel Core i3");
        subCategory.setParent(parent);

        Category savedCategory = categoryRepository.save(subCategory);

        assertThat(savedCategory.getId()).isGreaterThan(0);
    }

    @Test
    public void testGetCategory() {
        Category category = categoryRepository.findById(1).get();
        System.out.println(category.getName());

        Set<Category> children = category.getChildren();

        assertThat(children.size()).isGreaterThan(0);
    }

    @Test
    public void testPrintHierarchicalCategories() {
        Iterable<Category> categories = categoryRepository.findAll();

        for (Category category : categories) {
            if (category.getParent() == null) {
                System.out.println(category.getName());

                Set<Category> children = category.getChildren();

                for (Category child : children) {
                    System.out.println("--" + child.getName());
                    printChildren(child, 1);
                }
            }
        }
    }

    private void printChildren(Category parent, int subLevel) {
        int newSubLevel = subLevel + 1;
        Set<Category> children = parent.getChildren();

        for (Category category : children) {
            for (int i = 0; i < newSubLevel; i++) {
                System.out.print("--");
            }
            System.out.println(category.getName());
            printChildren(category, newSubLevel);
        }
    }

    @Test
    public void testGetCategoryByName() {
        String name = "Mainboard";
        Category category = categoryRepository.findByName(name);

        assertThat(category).isNotNull();
    }

    @Test
    public void testListRootCategories() {
        List<Category> rootCategories = categoryRepository.findByParentIsNull(Sort.by("name").ascending());

        rootCategories.forEach(cat -> System.out.println(cat.getName()));
    }

    @Test
    public void testFindByName() {
        String name = "Linh kiện";
        Category category = categoryRepository.findByName(name);

        assertThat(category).isNotNull();
    }

//    @Test
//    public void testUpdateUserRoles() {
//        User user = categoryRepository.findById(2).get();
//        Role shipper = new Role(4);
//        Role admin = new Role(1);
//
//        user.getRoles().remove(shipper);
//        user.addRole(admin);
//
//        categoryRepository.save(user);
//    }
//
//    @Test
//    public void testDeleteUser() {
//        Integer id = 2;
//
//        categoryRepository.deleteById(id);
//    }
//


//    @Test
//    public void testCountById() {
//        Integer id = 1;
//        Long countById = categoryRepository.countById(id);
//
//        assertThat(countById).isNotNull().isGreaterThan(0);
//    }
//
//    @Test
//    public void testUpdateUserEnabledStatus() {
//        Integer id = 2;
//
//        categoryRepository.updateEnabledStatus(id, false);
//    }
//
//    @Test
//    public void testListFirstPage() {
//        int pageNumber = 0;
//        int pageSize = 4;
//
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
//        Page<User> page = categoryRepository.findAll(pageable);
//
//        List<User> listUsers = page.getContent();
//
//        listUsers.forEach(user -> System.out.println(user));
//
//        assertThat(listUsers.size()).isEqualTo(pageSize);
//    }
//
//    @Test
//    public void testSearchUser() {
//        String keyword = "thong";
//
//        int pageNumber = 0;
//        int pageSize = 4;
//
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
//        Page<User> page = categoryRepository.findAll(keyword, pageable);
//
//        List<User> listUsers = page.getContent();
//
//        listUsers.forEach(user -> System.out.println(user));
//
//        assertThat(listUsers.size()).isGreaterThan(0);
//    }
}

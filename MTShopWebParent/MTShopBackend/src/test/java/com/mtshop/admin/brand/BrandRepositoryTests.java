package com.mtshop.admin.brand;

import com.mtshop.common.entity.Brand;
import com.mtshop.common.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class BrandRepositoryTests {

    @Autowired
    private BrandRepository brandRepo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateBrand1() {
        Category laptop = entityManager.find(Category.class, 8);
        Brand dell = new Brand();
        dell.setName("Dell");
        dell.setLogo("default.jpg");
        dell.getCategories().add(laptop);

        Brand savedBrand = brandRepo.save(dell);

        assertThat(savedBrand.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateBrand2() {
        Category mouse = entityManager.find(Category.class, 11);
        Category keyboard = entityManager.find(Category.class, 12);

        Brand logitech = new Brand();
        logitech.setName("Logitech");
        logitech.setLogo("default.jpg");
        logitech.getCategories().add(mouse);
        logitech.getCategories().add(keyboard);

        Brand savedBrand = brandRepo.save(logitech);

        assertThat(savedBrand.getId()).isGreaterThan(0);
    }

    @Test
    public void testGetById() {
        Brand brand = brandRepo.findById(1).get();

        assertThat(brand.getName()).isEqualTo("Dell");
    }

    @Test
    public void testUpdateBrand() {
        String newName = "Ajazz";
        Brand ajazz = brandRepo.findById(1).get();
        ajazz.setName(newName);

        Brand savedBrand = brandRepo.save(ajazz);

        assertThat(savedBrand.getId()).isGreaterThan(0);
    }

    @Test
    public void testDeleteBrand() {
        Integer id = 2;
        brandRepo.deleteById(id);

        Optional<Brand> result = brandRepo.findById(id);

        assertThat(result.isEmpty());
    }

    @Test
    public void testFindAll() {
        Iterable<Brand> brands = brandRepo.findAll();
        brands.forEach(System.out::println); // brand -> System.out.println(brand)

        assertThat(brands).isNotEmpty();
    }
}

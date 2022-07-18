package com.mtshop.admin.product;

import com.mtshop.common.entity.Brand;
import com.mtshop.common.entity.Category;
import com.mtshop.common.entity.product.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateProduct() {
        Brand brand = entityManager.find(Brand.class, 2);
        Category category = entityManager.find(Category.class, 14);

        Product product = new Product();
        product.setName("Chuột logitech");
        product.setAlias("chuot-logitech");
        product.setShortDescription("chuột logitech giá rẻ");
        product.setFullDescription("chuột logitech giá rẻ ở thành phố hcm");
        product.setBrand(brand);
        product.setCategory(category);
        product.setPrice(450);
        product.setCreatedTime(new Date());
        product.setUpdatedTime(new Date());

        Product savedProduct = productRepo.save(product);

        assertThat(savedProduct.getId()).isGreaterThan(0);
    }

    @Test
    public void testGetById() {
        Product product = productRepo.findById(1).get();

        assertThat(product.getName()).isEqualTo("Chuột logitech");
    }

    @Test
    public void testUpdateProduct() {
        Product product = productRepo.findById(1).get();
        product.setPrice(600);

        Product savedProduct = productRepo.save(product);

        assertThat(savedProduct.getPrice()).isEqualTo(600);
    }

    @Test
    public void testDeleteProduct() {
        Integer id = 1;
        productRepo.deleteById(id);

        Optional<Product> result = productRepo.findById(id);

        assertThat(result.isEmpty());
    }
}

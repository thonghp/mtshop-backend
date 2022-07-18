package com.mtshop.product;

import com.mtshop.common.entity.product.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository repo;

    @Test
    public void testFindByAlias() {
        String alias = "Laptop-Acer-Spin-5-SP513-52N-556V";
        Product product = repo.findByAlias(alias);

        assertThat(product).isNotNull();
    }
}

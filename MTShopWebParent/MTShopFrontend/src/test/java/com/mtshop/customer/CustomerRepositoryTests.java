package com.mtshop.customer;

import com.mtshop.common.entity.Country;
import com.mtshop.common.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class CustomerRepositoryTests {

    @Autowired
    private CustomerRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateCustomer() {
        Integer countryId = 1;
        Country country = entityManager.find(Country.class, countryId);

        Customer customer = new Customer();
        customer.setEmail("thonghoang123@gmail.com");
        customer.setPassword("thong123");
        customer.setFirstName("thông");
        customer.setLastName("hoàng");
        customer.setCountry(country);
        customer.setCreatedTime(new Date());

        Customer savedCustomer = repo.save(customer);

        assertThat(savedCustomer).isNotNull();
    }
}

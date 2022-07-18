package com.mtshop.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.mtshop.admin.dao.RoleRepository;
import com.mtshop.common.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

/*
 * @DataJpaTest will configure an in-memory embedded database, scan for @Entity classes and configure Spring Data JPA repositories.
 * @AutoConfigureTestDatabase run against real database
 * @Rollback(false) by default roll back only virtual operations not directly saved in db but when specified false it
 * will manipulate directly into db
 * If the variable is not declared, it will be null and @Autowired will search for the corresponding bean object to bind
 */

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testCreateRole() {
        Role admin = new Role("Admin", "manage everything");
        Role savedRole = roleRepository.save(admin);

        assertThat(savedRole.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateMultipleRoles() {
        Role salesperson = new Role("Salesperson", "manage product price, customers, shipping, orders " +
                "and sales report");
        Role editor = new Role("Editor", "manage categories, brands, products, articles and menus");
        Role shipper = new Role("Shipper", "view products, view orders and update order status");
        Role assistant = new Role("Assistant", "manage questions and reviews");

        roleRepository.saveAll(List.of(assistant, salesperson, editor, shipper));
    }
}

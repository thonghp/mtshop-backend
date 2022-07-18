package com.mtshop.admin.user;

import com.mtshop.admin.dao.UserRepository;
import com.mtshop.common.entity.Role;
import com.mtshop.common.entity.User;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateNewUserWithOneRole() {
        Role admin = entityManager.find(Role.class, 1);
        User user = new User("thong@gmail.com", "thong123", "hoàng phạm", "thông");
        user.addRole(admin);

        User savedUser = userRepository.save(user);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateNewUserWithTwoRoles() {
        Role editor = new Role(3);
        Role shipper = new Role(4);
        User user = new User("vinh@gmail.com", "vinh1234", "nguyễn thái", "vinh");
        user.addRole(editor);
        user.addRole(shipper);

        User savedUser = userRepository.save(user);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers() {
        Iterable<User> listUsers = userRepository.findAll();
        listUsers.forEach(user -> System.out.println(user));
    }

    @Test
    public void testGetUserById() {
        User user = userRepository.findById(1).get();

        assertThat(user).isNotNull();
    }

    @Test
    public void testUpdateUserDetails() {
        User user = userRepository.findById(1).get();
        user.setEnabled(true);

        userRepository.save(user);
    }

    @Test
    public void testUpdateUserRoles() {
        User user = userRepository.findById(2).get();
        Role shipper = new Role(4);
        Role admin = new Role(1);

        user.getRoles().remove(shipper);
        user.addRole(admin);

        userRepository.save(user);
    }

    @Test
    public void testDeleteUser() {
        Integer id = 2;

        userRepository.deleteById(id);
    }

    @Test
    public void testGetUserByEmail() {
        String email = "thong@gmail.com";
        User user = userRepository.findByEmail(email);

        assertThat(user).isNotNull();
    }

    @Test
    public void testCountById() {
        Integer id = 1;
        Long countById = userRepository.countById(id);

        assertThat(countById).isNotNull().isGreaterThan(0);
    }

    @Test
    public void testUpdateUserEnabledStatus() {
        Integer id = 2;

        userRepository.updateEnabledStatus(id, false);
    }

    @Test
    public void testListFirstPage() {
        int pageNumber = 0;
        int pageSize = 4;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = userRepository.findAll(pageable);

        List<User> listUsers = page.getContent();

        listUsers.forEach(user -> System.out.println(user));

        assertThat(listUsers.size()).isEqualTo(pageSize);
    }

    @Test
    public void testSearchUser() {
        String keyword = "thong";

        int pageNumber = 0;
        int pageSize = 4;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = userRepository.findAll(keyword, pageable);

        List<User> listUsers = page.getContent();

        listUsers.forEach(user -> System.out.println(user));

        assertThat(listUsers.size()).isGreaterThan(0);
    }
}

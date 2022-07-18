package com.mtshop.admin.dao;

import com.mtshop.common.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/*
 * collectionResourceRel chứa tên của json entry,
 * path là đường dẫn http://localhost:8080/mtshop/users
 */
@RepositoryRestResource(collectionResourceRel = "user", path = "users")
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    Long countById(Integer id);

    @Query("UPDATE User u SET u.enabled = ?2 WHERE u.id = ?1") // 1 with 2 is specifying the order of the attribute
    @Modifying
    void updateEnabledStatus(Integer id, boolean enabled);

    @Query("SELECT u FROM User u WHERE CONCAT(u.id, ' ', u.email, ' ', u.firstName, ' ', u.lastName) LIKE %?1%")
    Page<User> findAll(String keyword, Pageable pageable);
}

package com.mtshop.customer;

import com.mtshop.common.entity.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    Customer findByEmail(String email);

    Customer findByVerificationCode(String verificationCode);

    @Query("update Customer c set c.enabled = true where c.id = ?1")
    @Modifying
    void enable(Integer id);
}

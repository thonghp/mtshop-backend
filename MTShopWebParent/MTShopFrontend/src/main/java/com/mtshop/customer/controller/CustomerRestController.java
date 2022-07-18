package com.mtshop.customer.controller;

import com.mtshop.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerRestController {

    @Autowired private CustomerService service;

    @PostMapping("/customers/check_unique_email")
    public String checkDuplicateEmail(String email) {
        return service.isEmailUnique(email) ? "OK" : "Duplicated";
    }
}

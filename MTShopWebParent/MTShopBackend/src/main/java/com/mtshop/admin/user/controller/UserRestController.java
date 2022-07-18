package com.mtshop.admin.user.controller;

import com.mtshop.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    @PostMapping("/users/check_email")
    public String checkDuplicateEmail(@RequestParam(value = "id", required = false) Integer id, @RequestParam("email") String email) {
        return userService.isEmailUnique(id, email) ? "OK" : "Duplicated";
    }
}

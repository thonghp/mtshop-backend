package com.mtshop.customer.controller;

import com.mtshop.common.entity.Country;
import com.mtshop.common.entity.Customer;
import com.mtshop.customer.CustomerService;
import com.mtshop.setting.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private SettingService settingService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        List<Country> listCountries = customerService.listAllCountries();

        model.addAttribute("listCountries", listCountries);
        model.addAttribute("pageTitle", "Đăng Ký Tài Khoản");
        model.addAttribute("customer", new Customer());

        return "register/register_form";
    }
}

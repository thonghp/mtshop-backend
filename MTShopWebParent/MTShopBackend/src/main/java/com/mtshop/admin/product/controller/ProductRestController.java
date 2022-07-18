package com.mtshop.admin.product.controller;

import com.mtshop.admin.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductRestController {

    @Autowired
    private ProductService productService;

    @PostMapping("/products/check_unique")
    public String checkUnique(@RequestParam(value = "id", required = false) Integer id,
                              @RequestParam("name") String name) {
        return productService.checkUnique(id, name);
    }
}

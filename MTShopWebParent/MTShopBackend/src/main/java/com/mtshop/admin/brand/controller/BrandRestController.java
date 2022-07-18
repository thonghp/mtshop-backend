package com.mtshop.admin.brand.controller;

import com.mtshop.admin.brand.BrandNotFoundException;
import com.mtshop.admin.brand.BrandNotFoundRestException;
import com.mtshop.admin.brand.BrandService;
import com.mtshop.admin.category.dto.CategoryDTO;
import com.mtshop.common.entity.Brand;
import com.mtshop.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class BrandRestController {

    @Autowired
    private BrandService brandService;

    @PostMapping("/brands/check_unique")
    public String checkUnique(@RequestParam(value = "id", required = false) Integer id,
                              @RequestParam("name") String name) {
        return brandService.checkUnique(id, name);
    }

    // tạo mới sản phẩm
    @GetMapping("/brands/{id}/categories")
    public List<CategoryDTO> listCategoriesByBrand(@PathVariable("id") Integer id) throws BrandNotFoundRestException {
        List<CategoryDTO> listCategories = new ArrayList<>();
        try {
            Brand brand = brandService.get(id);
            Set<Category> categories = brand.getCategories();

            for (Category category : categories) {
                CategoryDTO dto = new CategoryDTO(category.getId(), category.getName());
                listCategories.add(dto);
            }

            return listCategories;
        } catch (BrandNotFoundException e) {
            throw new BrandNotFoundRestException();
        }
    }
}

package com.mtshop.admin.category.controller;

import com.mtshop.admin.FileUploadUtil;
import com.mtshop.common.exception.CategoryNotFoundException;
import com.mtshop.admin.category.CategoryPageInfo;
import com.mtshop.admin.category.CategoryService;
import com.mtshop.admin.category.export.CategoryCSVExporter;
import com.mtshop.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String listFirstPage(@RequestParam(value = "sortType", required = false) String sortType, Model model) {
        return listByPage(1, model, sortType, null);
    }

    @GetMapping("/categories/page/{pageNumber}")
    public String listByPage(@PathVariable(name = "pageNumber") int pageNum, Model model,
                             @RequestParam(value = "sortType", required = false) String sortType,
                             @RequestParam(value = "keyword", required = false) String keyword) {
        if (sortType == null || sortType.isEmpty()) {
            sortType = "asc";
        }

        CategoryPageInfo pageInfo = new CategoryPageInfo();
        List<Category> listCategories = categoryService.listByPage(sortType, pageNum, pageInfo, keyword);

        long startElementOfPage = (pageNum - 1) * CategoryService.ROOT_CATEGORIES_PER_PAGE + 1;
        long endElementOfPage = startElementOfPage + CategoryService.ROOT_CATEGORIES_PER_PAGE - 1;

        if (endElementOfPage > pageInfo.getTotalElements()) {
            endElementOfPage = pageInfo.getTotalElements();
        }

        String reverseSortType = sortType.equals("asc") ? "desc" : "asc";

        model.addAttribute("totalPages", pageInfo.getTotalPages());
        model.addAttribute("totalItems", pageInfo.getTotalElements());
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("sortField", "name");
        model.addAttribute("sortType", sortType);
        model.addAttribute("keyword", keyword);
        model.addAttribute("startCount", startElementOfPage);
        model.addAttribute("endCount", endElementOfPage);
        model.addAttribute("listCategories", listCategories);
        model.addAttribute("reverseSortType", reverseSortType);

        return "categories/categories";
    }

    @GetMapping("/categories/new")
    public String newCategory(Model model) {
        Category category = new Category();
        category.setEnabled(true);
        List<Category> listCategories = categoryService.listCategoriesUsedInForm();

        model.addAttribute("category", category);
        model.addAttribute("pageTitle", "Tạo thể loại mới");
        model.addAttribute("listCategories", listCategories);

        return "categories/category_form";
    }

    @PostMapping("/categories/save")
    public String saveUser(Category category, RedirectAttributes redirectAttributes,
                           @RequestParam(value = "fileImage", required = false) MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            category.setImage(fileName);

            Category savedCategory = categoryService.save(category);

            String uploadDir = "images/category-images/" + savedCategory.getId();

            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            if (category.getImage().isEmpty())
                category.setImage(null);
            categoryService.save(category);
        }

        redirectAttributes.addFlashAttribute("message", "Thể loại đã được lưu thành công !");

        return "redirect:/categories";
    }

    @GetMapping("/categories/edit/{id}")
    public String editCategory(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Category category = categoryService.get(id);
            List<Category> listCategories = categoryService.listCategoriesUsedInForm();

            model.addAttribute("category", category);
            model.addAttribute("listCategories", listCategories);
            model.addAttribute("pageTitle", "Sửa thể loại (ID: " + id + ")");

            return "categories/category_form";
        } catch (CategoryNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());

            return "redirect:/categories";
        }
    }

    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            categoryService.delete(id);
            String categoryDir = "images/category-images/" + id;
            FileUploadUtil.removeDir(categoryDir);

            redirectAttributes.addFlashAttribute("message", "Thể loại có id  " + id +
                    " được xoá thành công !");
        } catch (CategoryNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/categories";
    }

    @GetMapping("/categories/{id}/enabled/{status}")
    public String updateCategoryEnabledStatus(@PathVariable(name = "id") Integer id,
                                              @PathVariable(name = "status") boolean enabled,
                                              RedirectAttributes redirectAttributes) {
        categoryService.updateCategoryEnabledStatus(id, enabled);

        String status = enabled ? "kích hoạt" : "vô hiệu hoá";
        String message = "Thể loại có id là " + id + " đã được " + status;

        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/categories";
    }

    @GetMapping("/categories/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        List<Category> listCategories = categoryService.listCategoriesUsedInForm();
        CategoryCSVExporter exporter = new CategoryCSVExporter();
        exporter.export(listCategories, response);
    }
}

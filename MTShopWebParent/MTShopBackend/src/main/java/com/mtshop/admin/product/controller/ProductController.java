package com.mtshop.admin.product.controller;

import com.mtshop.admin.FileUploadUtil;
import com.mtshop.admin.brand.BrandService;
import com.mtshop.admin.category.CategoryService;
import com.mtshop.admin.product.ProductSaveHelper;
import com.mtshop.admin.product.ProductService;
import com.mtshop.admin.security.MTShopUserDetails;
import com.mtshop.common.entity.Brand;
import com.mtshop.common.entity.Category;
import com.mtshop.common.entity.product.Product;
import com.mtshop.common.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/products")
    public String listFirstPage(Model model) {
        return listByPage(1, model, "name", "asc", null, 0);
    }

    @GetMapping("/products/page/{pageNumber}")
    public String listByPage(@PathVariable(name = "pageNumber") int pageNum, Model model,
                             @RequestParam(value = "sortField") String sortField,
                             @RequestParam(value = "sortType") String sortType,
                             @RequestParam(value = "keyword", required = false) String keyword,
                             @RequestParam(value = "categoryId", required = false) Integer categoryId) {

        Page<Product> page = productService.listByPage(pageNum, sortField, sortType, keyword, categoryId);
        List<Product> listProducts = page.getContent();

        List<Category> listCategories = categoryService.listCategoriesUsedInForm();

        long startElementOfPage = (pageNum - 1) * ProductService.PRODUCTS_PER_PAGE + 1;
        long endElementOfPage = startElementOfPage + ProductService.PRODUCTS_PER_PAGE - 1;

        if (endElementOfPage > page.getTotalElements()) {
            endElementOfPage = page.getTotalElements();
        }

        String reverseSortType = sortType.equals("asc") ? "desc" : "asc";

        if (categoryId != null) model.addAttribute("categoryId", categoryId);

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("startCount", startElementOfPage);
        model.addAttribute("endCount", endElementOfPage);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortType", sortType);
        model.addAttribute("reverseSortType", reverseSortType);
        model.addAttribute("keyword", keyword);
        model.addAttribute("listProducts", listProducts);
        model.addAttribute("listCategories", listCategories);

        return "products/products";
    }

    @GetMapping("/products/new")
    public String newProduct(Model model) {
        List<Brand> brands = brandService.listAll();

        Product product = new Product();
        product.setEnabled(true);
        product.setInStock(true);

        model.addAttribute("product", product);
        model.addAttribute("listBrands", brands);
        model.addAttribute("pageTitle", "Tạo sản phẩm mới");
        model.addAttribute("numberOfExistingExtraImages", 0);

        return "products/product_form";
    }

    @PostMapping("/products/save")
    public String saveProduct(Product product, RedirectAttributes redirectAttributes,
                              @RequestParam(value = "fileImage", required = false) MultipartFile mainImageMultipart,
                              @RequestParam(value = "extraImage", required = false) MultipartFile[] extraImageMultiparts,
                              @RequestParam(name = "detailIDs", required = false) String[] detailIDs,
                              @RequestParam(name = "detailNames", required = false) String[] detailNames,
                              @RequestParam(name = "detailValues", required = false) String[] detailValues,
                              @RequestParam(name = "imageIDs", required = false) String[] imageIDs,
                              @RequestParam(name = "imageNames", required = false) String[] imageNames,
                              @AuthenticationPrincipal MTShopUserDetails logger) throws IOException {
        if (logger.hasRole("Salesperson")) {
            productService.saveProductPrice(product);
            redirectAttributes.addFlashAttribute("message", "Sản phẩm đã được lưu thành công !");

            return "redirect:/products";
        }

        ProductSaveHelper.setMainImageName(mainImageMultipart, product);
        ProductSaveHelper.setExistingExtraImageNames(imageIDs, imageNames, product);
        ProductSaveHelper.setNewExtraImageName(extraImageMultiparts, product);
        ProductSaveHelper.setProductDetails(detailIDs, detailNames, detailValues, product);

        Product savedProduct = productService.save(product);

        ProductSaveHelper.saveUploadedImages(mainImageMultipart, extraImageMultiparts, savedProduct);

        ProductSaveHelper.deleteExtraImagesWeredRemovedOnForm(product);

        redirectAttributes.addFlashAttribute("message", "Sản phẩm đã được lưu thành công !");

        return "redirect:/products";
    }


    @GetMapping("/products/edit/{id}")
    public String editProduct(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes
            redirectAttributes) {
        try {
            Product product = productService.get(id);
            List<Brand> listBrands = brandService.listAll();
            Integer numberOfExistingExtraImages = product.getImages().size();

            model.addAttribute("product", product);
            model.addAttribute("listBrands", listBrands);
            model.addAttribute("pageTitle", "Sửa sản phẩm với (ID: " + id + ")");
            model.addAttribute("numberOfExistingExtraImages", numberOfExistingExtraImages);

            return "products/product_form";
        } catch (ProductNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());

            return "redirect:/products";
        }
    }

    @GetMapping("/products/detail/{id}")
    public String viewProductDetail(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes
            redirectAttributes) {
        try {
            Product product = productService.get(id);

            model.addAttribute("product", product);

            return "products/product_detail_modal";
        } catch (ProductNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());

            return "redirect:/products";
        }
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            productService.delete(id);

            String producImagesDir = "images/product-images/" + id;
            String productExtraImagesDir = "images/product-images/" + id + "/extras";
            FileUploadUtil.removeDir(productExtraImagesDir);
            FileUploadUtil.removeDir(producImagesDir);

            redirectAttributes.addFlashAttribute("message", "Nhãn hiệu có id  " + id +
                    " được xoá thành công !");
        } catch (ProductNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/products";
    }

    @GetMapping("/products/{id}/enabled/{status}")
    public String updateProductEnabledStatus(@PathVariable(name = "id") Integer id,
                                             @PathVariable(name = "status") boolean enabled,
                                             RedirectAttributes redirectAttributes) {
        productService.updateProductEnabledStatus(id, enabled);

        String status = enabled ? "kích hoạt" : "vô hiệu hoá";
        String message = "Sản phẩm có id là " + id + " đã được " + status;

        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/products";
    }
}

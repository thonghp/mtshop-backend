package com.mtshop.product;

import com.mtshop.common.entity.product.Product;
import com.mtshop.common.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    public static final int PRODUCTS_PER_PAGE = 12;

    @Autowired
    private ProductRepository productRepo;

    public Page<Product> listByCategory(int pageNum, Integer categoryId) {
        String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
        Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE);

        return productRepo.listByCategory(categoryId, categoryIdMatch, pageable);
    }

    public Product getProduct(String alias) throws ProductNotFoundException {
        Product product = productRepo.findByAlias(alias);
        if (product == null)
            throw new ProductNotFoundException("Could not find any product with alias " + alias);

        return product;
    }

    public Page<Product> search(String keyword, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE);

        return productRepo.search(keyword, pageable);
    }
}

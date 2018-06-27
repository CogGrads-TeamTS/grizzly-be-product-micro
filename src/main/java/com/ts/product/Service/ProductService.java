package com.ts.product.Service;

import com.ts.product.Model.Category;
import com.ts.product.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;

public interface ProductService {
    Page<Product> findBySearchTerm(String searchTerm, Pageable pageable);
    Page<Product> findBySearchTermCategory(String searchTerm, Pageable pageable, Long categoryId);
    Page<Product> assignCategories(Page<Product> products);
    Product assignCategory(Product product);
    ResponseEntity<List<String>> distinctBrandsFilter(String searchTerm);
    ResponseEntity<HashMap<Long, Category>> distinctCategoriesFilter(String searchTerm);
}

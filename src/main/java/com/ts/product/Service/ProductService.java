package com.ts.product.Service;

import com.ts.product.Model.Category;
import com.ts.product.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public interface ProductService {
    Page<Product> findBySearchTerm(String searchTerm, Pageable pageable);
    Page<Product> findBySearchTermCategory(String searchTerm, Pageable pageable, Long categoryId);
    Page<Product> assignCategories(Page<Product> products);
    ResponseEntity<HashMap<Long, Category>> distinctCategoriesFilter(String searchTerm);
}

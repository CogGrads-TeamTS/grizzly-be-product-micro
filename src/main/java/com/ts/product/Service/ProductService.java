package com.ts.product.Service;

import com.ts.product.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<Product> findBySearchTerm(String searchTerm, Pageable pageable);
}

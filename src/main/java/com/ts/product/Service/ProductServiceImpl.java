package com.ts.product.Service;

import com.ts.product.Model.Product;
import com.ts.product.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Page<Product> findBySearchTerm(String searchTerm, Pageable pageable){
        return productRepository.findBySearchTerm(searchTerm, pageable);
    }
}

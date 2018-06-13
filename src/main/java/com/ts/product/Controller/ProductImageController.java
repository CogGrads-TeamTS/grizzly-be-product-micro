package com.ts.product.Controller;

import com.ts.product.Model.ProductImage;
import com.ts.product.Repository.ProductImageRepository;
import com.ts.product.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class ProductImageController {

        @Autowired
        private ProductImageRepository productImageRepository;

        @Autowired
        private ProductRepository productRepository;

        @GetMapping("/{productId}/images")
        public List<ProductImage> getAllImagesByProductId(@PathVariable(value = "productId") Long productId) {
            return productImageRepository.findByProductId(productId);
        }

    }


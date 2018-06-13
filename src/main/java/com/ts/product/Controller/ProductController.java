package com.ts.product.Controller;


import com.ts.product.Client.CategoryClient;
import com.ts.product.Model.Product;
import com.ts.product.Model.ProductDetails;
import com.ts.product.Repository.ProductImageRepository;
import com.ts.product.Repository.ProductRepository;
import com.ts.product.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private CategoryClient categoryClient;


    @GetMapping
    public @ResponseBody
    ResponseEntity<Iterable<Product>> getAllUsers() {
        Iterable<Product> products = productRepository.findAll( );
        // This returns a JSON or XML with the users

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetails> getProduct(@PathVariable(value = "productId") Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            // create product model that will contain images, vendor, category etc etc...
            ProductDetails productDetails = new ProductDetails(product.get());

            // set the images
            productDetails.setImages(productImageRepository.findByProductId(productDetails.getId()));

            return new ResponseEntity<>(productDetails, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/")
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

}

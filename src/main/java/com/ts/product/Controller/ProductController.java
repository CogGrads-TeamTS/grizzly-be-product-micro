package com.ts.product.Controller;


import com.ts.product.Client.CategoryClient;
import com.ts.product.Repository.ProductRepository;
import com.ts.product.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryClient categoryClient;

}

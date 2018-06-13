package com.ts.product.Controller;


import com.ts.product.Client.CategoryClient;
import com.ts.product.Model.Product;
import com.ts.product.Repository.ProductRepository;
import com.ts.product.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryClient categoryClient;


    @GetMapping
    public @ResponseBody
    ResponseEntity<Iterable<Product>> getAllUsers() {
        Iterable<Product> products = productRepository.findAll();
        // This returns a JSON or XML with the users

        return new ResponseEntity<>(products, HttpStatus.OK);
    }


}

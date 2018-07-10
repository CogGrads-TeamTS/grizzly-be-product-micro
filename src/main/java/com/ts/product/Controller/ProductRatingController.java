package com.ts.product.Controller;

import com.ts.product.Model.ProductRating;
import com.ts.product.Repository.ProductRatingRepository;
import com.ts.product.Service.ProductRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://ts.ausgrads.academy"})
@RestController
@RequestMapping("/rating")
public class ProductRatingController {

    @Autowired
    private ProductRatingRepository productRatingRepository;

    @GetMapping("/{productId}")
    public List<ProductRating> getAllRatingsByProductId(@PathVariable Long productId){
        return productRatingRepository.findProductRatingByProductId(productId);
    }

    @PostMapping(path="/add", headers = "Content-Type=application/json") // Map ONLY GET Requests
    public ResponseEntity addNewProduct (@RequestBody ProductRating productRating, Principal principal) {
        productRating.setUserId(principal.getName());
        productRatingRepository.save(productRating);

        return new ResponseEntity<>(productRating, HttpStatus.CREATED);
    }
}



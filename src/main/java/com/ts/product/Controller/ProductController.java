package com.ts.product.Controller;


import com.ts.product.Client.CategoryClient;
import com.ts.product.Model.Category;
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

import javax.swing.text.html.Option;
import java.util.*;


@RestController
@CrossOrigin
public class ProductController {
    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageRepository productImageRepository;



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

            Optional<Category> cat = categoryClient.getCategory(productDetails.getCatId());
            if (cat.isPresent()) {
                productDetails.setCategory(cat.get());
            }
            return new ResponseEntity<>(productDetails, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/")
    public Page<Product> getAllProducts(Pageable pageable) {
        // fetch paginated products from repository
        Page<Product> products = productRepository.findAll(pageable);

        // create unique array of category ids
        Set<Long> uniqueCats = new HashSet<>();
        for (Product product : products) {
            uniqueCats.add(product.getCatId());
        }
        Long[] uniqueCatsArray = uniqueCats.toArray(new Long[uniqueCats.size()]);

        HashMap<Long, Category> categories = categoryClient.getCategoriesBatch(uniqueCatsArray);

        for (Product product : products) {
            if (categories.containsKey(product.getCatId())) {
                // add the category to the product if it exists
                product.setCatName(categories.get(product.getCatId()).getName());
            } else {
                product.setCatName("N/A");
            }
        }

        return products;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable long id) {
        productRepository.deleteById(id);

        return new ResponseEntity("Deleted Product@{" + id + "} successfully", HttpStatus.OK);
    }

    @GetMapping(value = "/getcatbyid")
    public ResponseEntity<Optional<Category>> getDistinctCategoryId() {

        List<Optional<Category>> categories = new ArrayList<>();
        List<Long> categoryId = productRepository.findDistinctCategoryId();

        for (Long var:categoryId){
            categories.add(categoryClient.getCategory(var));
        }

        return new ResponseEntity(categories, HttpStatus.OK);
    }
}

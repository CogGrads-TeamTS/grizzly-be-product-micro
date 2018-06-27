package com.ts.product.Controller;


import com.fasterxml.jackson.databind.util.JSONPObject;
import com.ts.product.Client.CategoryClient;
import com.ts.product.Model.Category;
import com.ts.product.Model.Product;
import com.ts.product.Model.ProductDetails;
import com.ts.product.Model.ProductPage;
import com.ts.product.Repository.ProductImageRepository;
import com.ts.product.Repository.ProductRepository;
import com.ts.product.Service.ProductService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@CrossOrigin
public class ProductController {
    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductImageRepository productImageRepository;


    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetails> getProduct(@PathVariable(value = "productId") Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            // create product model that will contain images, vendor, category etc etc...
            ProductDetails productDetails = new ProductDetails(product.get());

            // set the images
            productDetails.setImages(productImageRepository.findByProductId(productDetails.getId()));

            ResponseEntity<Category> catResponse = categoryClient.getCategory(productDetails.getCatId());
            if (catResponse.getStatusCodeValue() == 200) {
                productDetails.setCategory(catResponse.getBody());
                productDetails.setCatName(catResponse.getBody().getName());
            }

            return new ResponseEntity<>(productDetails, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(path="/add", headers = "Content-Type=application/json") // Map ONLY GET Requests
    public ResponseEntity addNewProduct (@RequestBody Product product) {
        productRepository.save(product);

        return new ResponseEntity<>(productService.assignCategory(product), HttpStatus.CREATED);
    }

    @PostMapping(path="/add")
    public ResponseEntity addNewProduct (@RequestParam String name, @RequestParam String description, @RequestParam String brand,
                                         @RequestParam Float price, @RequestParam int catId, @RequestParam int discount, @RequestParam int rating) {

        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setBrand(brand);
        product.setPrice(price);
        product.setCatId(catId);
        product.setDiscount(discount);
        product.setRating(rating);
        productRepository.save(product);

        return new ResponseEntity<>(productService.assignCategory(product), HttpStatus.CREATED);
    }


    //    Method used for the global search to find categories by name
    @GetMapping(value = "/allByLen")
    public List<Product> getAllProductsByLen(int size, String search){
        return productService.findNameBySearchTerm(search, new PageRequest(0, size)).getContent();
    }

    @GetMapping("/page")
    public ProductPage findBySearchTerm(@RequestParam("search") String searchTerm, Pageable pageable, @RequestParam("category") Optional<Long> categoryId) {
        Page<Product> products;

        if (categoryId.isPresent()) {
            // fetch paginated products from repository with filtered category
            products = productService.findBySearchTermCategory(searchTerm, pageable, categoryId.get());
        } else {
            products = productService.findBySearchTerm(searchTerm, pageable);
        }

        ProductPage page = new ProductPage(productService.assignCategories(products), productService.distinctCategoriesFilter(searchTerm).getBody());

        ResponseEntity<List<String>> brands = productService.distinctBrandsFilter(searchTerm);
        if (brands.getStatusCodeValue() == 200) {
            page.setFilterBrands(brands.getBody());
        }

        ResponseEntity<List<Integer>> ratings = productService.distinctRatingsFilter(searchTerm);
        if (ratings.getStatusCodeValue() == 200) {
            page.setFilterRatings(ratings.getBody());
        }

        return page;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable long id) {
        productRepository.deleteById(id);

        Path dir;
        String pattern = id + "-*";

        dir = Paths.get("data/images/");

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, pattern)) {
            for (Path path : stream) {
                Files.delete(path.toAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity("Deleted Product@{" + id + "} successfully", HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity editProduct(@PathVariable long id, @RequestBody Product product) {

        Optional<Product> productOptional = productRepository.findById(id);
        if (!productOptional.isPresent())
            return ResponseEntity.notFound().build();

        product.setId(id);

        productRepository.save(product);

        return new ResponseEntity("Updated Product @{" + id + "} successfully", HttpStatus.OK);
    }

    @GetMapping("/categories/count/{catId}")
    public ResponseEntity productCount(@PathVariable long catId) {
        // return the count of products in a category.
        Long count = productRepository.categoryProductCount(catId);

        JSONObject countObj = new JSONObject();
        countObj.put("count", count);

        JSONObject o = new JSONObject();
        o.put(catId, countObj);

        return new ResponseEntity(count, HttpStatus.OK);
    }
}

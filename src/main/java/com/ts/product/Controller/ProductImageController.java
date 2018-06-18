package com.ts.product.Controller;

import com.ts.product.Model.ProductImage;
import com.ts.product.Repository.ProductImageRepository;
import com.ts.product.Repository.ProductRepository;
import com.ts.product.ResourceNotFoundException;
import com.ts.product.Service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;


@RestController
public class ProductImageController {

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageService productImageService;

    @GetMapping("/{productId}/images")
    public List<ProductImage> getAllImagesByProductId(@PathVariable(value = "productId") Long productId) {
        return productImageRepository.findByProductId(productId);
    }

    @PostMapping("/{productId}/images/add")
    public ResponseEntity addNewProductImage(@PathVariable(value = "productId") Long productId, @Valid @RequestParam int sort, @RequestParam MultipartFile file ){

        // Attempt to save the image to disk
        ResponseEntity fileSavedResponse = productImageService.singleFileUploadSave(productId, file);
        if(fileSavedResponse.getStatusCode() != HttpStatus.CREATED) return fileSavedResponse;

        // Get + save the url
        String url = (String)fileSavedResponse.getBody();

        // Update the productImage
        ProductImage productImage = new ProductImage();
        productImage.setSort(sort);
        productImage.setUrl(url);

        // Save new productImage and link to the product by ID
        try {
            productRepository.findById(productId).map(product -> {
                productImage.setProduct(product);
                return productImageRepository.save(productImage);
            }).orElseThrow(() -> new ResourceNotFoundException("PostId " + productId + " not found"));
            } catch (ResourceNotFoundException rnfe) {
                return new ResponseEntity("Product Id not found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity("Image added to product: " + productId, HttpStatus.CREATED);
    }
}

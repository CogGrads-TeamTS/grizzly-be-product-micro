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
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@CrossOrigin
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

        // Attempt to save the image to disk and rename file via singleFileUploadSave
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

    @PutMapping("/edit/{productId}/image")
    public ResponseEntity editProductImage(@RequestParam ProductImage image) {

        /**
         * Check if the sort is the same
         * return and do nothing
         * else update sort value
         */

        ProductImage temp = productImageRepository.getOne(image.getId());

        /**
         * If Image is the same
         */
        if(temp.getSort() == image.getSort()) {
            return new ResponseEntity("Updated Product @{" + image.getId() + "} successfully", HttpStatus.OK);
        } else {
            /**
             * set the sort and save the image i think..
             */
            temp.setSort(image.getSort());
            productImageRepository.save(temp);
        }

        return new ResponseEntity("Updated Product @{" + image.getId() + "}  with sort @{" + image.getSort()+ "} successfully", HttpStatus.OK);
    }

    @DeleteMapping("/image/delete")
    public ResponseEntity deleteImage(@RequestParam long id, String url) {
        productImageRepository.deleteById(id);

        Path dir;
        String pattern = url;

        dir = Paths.get("data/images/");

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, pattern)) {
            for (Path path : stream) {
                Files.delete(path.toAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity("Deleted Product@{" + url + "} successfully", HttpStatus.OK);


    }
}

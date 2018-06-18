package com.ts.product.Controller;

import com.ts.product.Model.Product;
import com.ts.product.Model.ProductImage;
import com.ts.product.Repository.ProductImageRepository;
import com.ts.product.Repository.ProductRepository;
import com.ts.product.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;


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

    @PostMapping("/{productId}/images/add")
    public ResponseEntity addNewProductImage(@PathVariable(value = "productId") Long productId, @Valid @RequestParam int sort, @RequestParam MultipartFile file ){

        ProductImage productImage = new ProductImage();
        productImage.setSort(sort);

        // Attempt to save the image
        ResponseEntity fileSavedResponse = singleFileUploadSave(file);
        if(fileSavedResponse.getStatusCode() != HttpStatus.CREATED) return fileSavedResponse;

        // Get + save the url
        String url = (String)fileSavedResponse.getBody();
        productImage.setUrl(url);


        try {
            productRepository.findById(productId).map(product -> {
                productImage.setProduct(product);
                return productImageRepository.save(productImage);
            }).orElseThrow(() -> new ResourceNotFoundException("PostId " + productId + " not found"));
            } catch (ResourceNotFoundException rnfe) {
                return new ResponseEntity("Product Id not found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity("Image added to Product Id " + productId, HttpStatus.CREATED);
    }

    public ResponseEntity singleFileUploadSave(MultipartFile file) {

        if (file.isEmpty()) {
            return new ResponseEntity("The file is invalid. Please try again.", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }

        Path path;

        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            path = Paths.get("data/images/" + file.getOriginalFilename());
            Files.createFile(path);
            Files.write(path, bytes);

        } catch (FileAlreadyExistsException faee){
            System.out.println(faee);
            return new ResponseEntity("File already exists, please change name and try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            System.out.println(e);
            return new ResponseEntity("Image could not be added. Try Again at a later time.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(path.toAbsolutePath().toString(), HttpStatus.CREATED);
    }
}

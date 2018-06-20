package com.ts.product.Service;

import com.ts.product.Client.CategoryClient;
import com.ts.product.Model.Category;
import com.ts.product.Model.Product;
import com.ts.product.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryClient categoryClient;

    public Page<Product> findBySearchTerm(String searchTerm, Pageable pageable){
        return productRepository.findBySearchTerm(searchTerm, pageable);
    }

    public Page<Product> findBySearchTermCategory(String searchTerm, Pageable pageable, Long categoryId){
        return productRepository.findBySearchTermCategory(searchTerm, pageable, categoryId);
    }

    public Product assignCategory(Product product) {
        try {
            ResponseEntity<Category> categoryResponse = categoryClient.getCategory(product.getCatId());

            if (categoryResponse.getStatusCodeValue() == 200) {
                product.setCatName(categoryResponse.getBody().getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }

    public Page<Product> assignCategories(Page<Product> products) {
        // return if no products.
        if (products.getTotalElements() < 1) return products;

        // create unique array of category ids
        Set<Long> uniqueCats = new HashSet<>();
        for (Product product : products) {
            uniqueCats.add(product.getCatId());
        }
        Long[] uniqueCatsArray = uniqueCats.toArray(new Long[uniqueCats.size()]);

        try {
            ResponseEntity<HashMap<Long, Category>> batchResponse = categoryClient.getCategoriesBatch(uniqueCatsArray);
            if (batchResponse.getStatusCodeValue() == 200) {
                HashMap<Long, Category> categories = batchResponse.getBody();
                for (Product product : products) {
                    if (categories.containsKey(product.getCatId())) {
                        // add the category to the product if it exists
                        product.setCatName(categories.get(product.getCatId()).getName());
                    } else {
                        product.setCatName("N/A");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    public ResponseEntity<HashMap<Long, Category>> distinctCategoriesFilter(String searchTerm) {
        // used for filtering products by category on front end
        // only returns distinct categories that contain products
        List<Long> categoryIds = productRepository.findDistinctCategoryId(searchTerm);

        if (categoryIds.size() < 1) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        // fetch distinct categories in batch from categories service
        Long[] categoryIdsArray = categoryIds.toArray(new Long[categoryIds.size()]);
        try {
            ResponseEntity<HashMap<Long, Category>> batchResponse = categoryClient.getCategoriesBatch(categoryIdsArray);
            if (batchResponse.getStatusCodeValue() == 200) {
                HashMap<Long, Category> categories = batchResponse.getBody();
                return new ResponseEntity(categories, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

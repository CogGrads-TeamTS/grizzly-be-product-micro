package com.ts.product.Service;

import com.ts.product.Client.CategoryClient;
import com.ts.product.Model.Category;
import com.ts.product.Model.Product;
import com.ts.product.Model.ProductFilter;
import com.ts.product.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.ws.Response;
import java.util.*;

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

    public ResponseEntity<HashMap<String, Object>> distinctFilters(String searchTerm) {
        // DISTINCT BULK FILTER
        List<Object[]> filterQuery = productRepository.findDistinctFilters(searchTerm);
        List<String> brands = new ArrayList<>();
        List<Integer> ratings = new ArrayList<>();
        List<Long> categoryIds = new ArrayList<>();
        //System.out.println(filters);

        // add objects to individual lists
        for (Object[] filter: filterQuery) {
            String brand = (String) filter[0];
            long catId = (long) filter[1];
            int rating = (int) filter[2];
            if (!brands.contains(brand) && brand != null && !brand.equals("")) brands.add(brand);
            if (!ratings.contains(rating)) ratings.add(rating);
            if (!categoryIds.contains(catId)) categoryIds.add(catId);
        }

        // fetch category objects from category micro service
        Long[] categoryIdsArray = categoryIds.toArray(new Long[categoryIds.size()]);
        HashMap<Long, Category> categories = new HashMap<>();
        try {
            ResponseEntity<HashMap<Long, Category>> batchResponse = categoryClient.getCategoriesBatch(categoryIdsArray);
            if (batchResponse.getStatusCodeValue() == 200) {
                categories.putAll(batchResponse.getBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        HashMap<String, Object> filters = new HashMap<>();
        if (brands.size() > 0) filters.put("brands", brands);
        if (ratings.size() > 0) filters.put("ratings", ratings);
        if (categories.size() > 0) filters.put("categories", categories);

        return (filters.size() > 0) ?
                new ResponseEntity(filters, HttpStatus.OK): new ResponseEntity<>(HttpStatus.NOT_FOUND) ;
    }

    public Page<Product> findNameBySearchTerm(String searchTerm, Pageable pageable){
        return productRepository.findNameBySearchTerm(searchTerm, pageable);
    }
}

package com.ts.product.Client;

import com.ts.product.Model.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Repository
@FeignClient(name = "category-service")
public interface CategoryClient {

    @GetMapping(value = "categorylist")
    Iterable<Category> getCategoryList();

    @GetMapping(value = "/{id}")
    Optional<Category> getCategory(@PathVariable(value = "id") Long id);
}

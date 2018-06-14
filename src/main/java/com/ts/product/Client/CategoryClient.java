package com.ts.product.Client;

import com.ts.product.Model.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
@FeignClient(name = "category-service", decode404 = true)
public interface CategoryClient {

    @GetMapping(value = "categorylist")
    Iterable<Category> getCategoryList();

    @GetMapping(value = "/{id}")
    ResponseEntity<Category> getCategory(@PathVariable(value = "id") Long id);

    @PostMapping(value = "/getBatch")
    @ResponseBody
    HashMap<Long, Category> getCategoriesBatch(@RequestBody Long[] catIds);
}

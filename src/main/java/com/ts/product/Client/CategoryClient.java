package com.ts.product.Client;

import com.ts.product.Model.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

@Repository
@FeignClient(name = "category-service")
public interface CategoryClient {
    @GetMapping(value = "categorylist")
    Iterable<Category> getCategoryList();
}

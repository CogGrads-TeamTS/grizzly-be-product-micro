package com.ts.product.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonMerge;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.ts.product.Model.Category;
import com.ts.product.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.HashMap;

public class ProductPage {
    private Page<Product> products;
    private HashMap<Long, Category> filterCats;

    public ProductPage(Page<Product> products, HashMap<Long, Category> filterCats) {
        this.products = products;
        this.filterCats = filterCats;
    }

    public HashMap<Long, Category> getFilterCats() {
        return filterCats;
    }

    public Page<Product> getProducts() {
        return products;
    }
}

package com.ts.product.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonMerge;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.ts.product.Model.Category;
import com.ts.product.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.HashMap;
import java.util.List;

public class ProductPage {
    private Page<Product> products;
    private HashMap<Long, Category> filterCats;
    private List<String> filterBrands;

    public ProductPage(Page<Product> products, HashMap<Long, Category> filterCats) {
        this.products = products;
        this.filterCats = filterCats;
    }

    public void setFilterBrands(List<String> filterBrands) {
        this.filterBrands = filterBrands;
    }

    public List<String> getFilterBrands() {
        return filterBrands;
    }

    public HashMap<Long, Category> getFilterCats() {
        return filterCats;
    }

    public Page<Product> getProducts() {
        return products;
    }
}

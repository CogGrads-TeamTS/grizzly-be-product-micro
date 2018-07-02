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
    private List<Integer> filterRatings;
    private HashMap<String, List<Product>> productsByCategories;
//    private String productsByCategories;

    public void setFilterCats(HashMap<Long, Category> filterCats) {
        this.filterCats = filterCats;
    }

    public ProductPage(Page<Product> products) {
        this.products = products;

    }

    public List<Integer> getFilterRatings() {
        return filterRatings;
    }

    public void setFilterRatings(List<Integer> filterRatings) {
        this.filterRatings = filterRatings;
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

    public HashMap<String, List<Product>> getProductsByCategories() {
        return productsByCategories;
    }

    public void setProductsByCategories(HashMap<String, List<Product>> productsByCategories) {
        this.productsByCategories = productsByCategories;
    }

//    public String getProductsByCategories() {
//        return productsByCategories;
//    }
//
//    public void setProductsByCategories(String productsByCategories) {
//        this.productsByCategories = productsByCategories;
//    }
}

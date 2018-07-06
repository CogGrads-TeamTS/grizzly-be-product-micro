package com.ts.product.Model;

import java.util.List;

public class ProductDetails extends Product {

    // List of Categories
    Category category;

    public ProductDetails(Product product) {
        super(product);
    }


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

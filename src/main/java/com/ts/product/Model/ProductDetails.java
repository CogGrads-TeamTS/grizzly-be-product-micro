package com.ts.product.Model;

import java.util.List;

public class ProductDetails extends Product {
    List<ProductImage> images;
    // List of Categories
    Category category;

    public ProductDetails(Product product) {
        super(product);
    }

    public List<ProductImage> getImages() {
        return images;
    }

    public void setImages(List<ProductImage> images) {
        this.images = images;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

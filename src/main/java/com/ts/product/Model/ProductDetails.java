package com.ts.product.Model;

import java.util.List;

public class ProductDetails extends Product {
    List<ProductImage> images;
    // List of Categories


    public ProductDetails(Product product) {
        super(product);
    }

    public List<ProductImage> getImages() {
        return images;
    }

    public void setImages(List<ProductImage> images) {
        this.images = images;
    }
}

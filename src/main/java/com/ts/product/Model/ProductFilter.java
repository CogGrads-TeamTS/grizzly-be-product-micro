package com.ts.product.Model;

public class ProductFilter {
    private long catId;
    private int rating;
    private String brand;

    public long getCatId() {
        return catId;
    }

    public void setCatId(long catId) {
        this.catId = catId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public ProductFilter(long catId, int rating, String brand) {

        this.catId = catId;
        this.rating = rating;
        this.brand = brand;
    }
}

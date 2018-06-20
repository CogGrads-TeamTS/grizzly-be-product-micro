package com.ts.product.Model;

import com.ts.product.Repository.ProductImageRepository;
import com.ts.product.Model.ProductImage;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.List;



@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;
    private String brand;
    private Long price;
    private long catId;

    @Transient
    private String catName;
    private int discount;
    private long rating;

    public Product(Product product) {
        this.id = product.id;
        this.name = product.name;
        this.description = product.description;
        this.brand = product.brand;
        this.price = product.price;
        this.catId = product.catId;
        this.catName = product.catName;
        this.discount = product.discount;
        this.rating = product.rating;
    }

    public Product() {
        this.catName = "N/A";
    }

    @Transient
    public String getCatName() {
        return catName;
    }

    @Transient
    public void setCatName(String catName) {
        this.catName = catName;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() { return brand; }

    public void setBrand(String brand) { this.brand = brand; }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public long getCatId() {
        return catId;
    }

    public void setCatId(long catId) {
        this.catId = catId;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }
}

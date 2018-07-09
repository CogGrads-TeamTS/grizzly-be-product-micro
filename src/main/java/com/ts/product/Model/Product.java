package com.ts.product.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ts.product.Repository.ProductImageRepository;
import com.ts.product.Model.ProductImage;

import org.hibernate.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.util.List;
import java.util.Set;


@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    @Column(length = 1000, columnDefinition = "varchar(1000)")
    private String description;
    private String brand;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    @Fetch(FetchMode.SELECT)
    private Set<ProductImage> images;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Float price;

    private long catId;

    @Transient
    private String catName;
    private int discount;
    private int rating;

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
        this.images = product.images;
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

    public Set<ProductImage> getImages() {
        return images;
    }

    public void setImages(Set<ProductImage> images) {
        this.images = images;
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}

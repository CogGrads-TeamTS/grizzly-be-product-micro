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
public class Product extends ProductSuperClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    @Fetch(FetchMode.SELECT)
    protected List<ProductImage> images;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    @Fetch(FetchMode.SELECT)
    private List<ProductRating> ratings;

    @Column(length = 1000, columnDefinition = "varchar(1000)")
    protected String description;

    private long catId;

    @Transient
    private String catName;

    public Product(Product product) {
        super(product);
        this.id = product.id;
        this.description = product.description;
        this.catId = product.catId;
        this.catName = product.catName;
        this.images = product.images;
        this.ratings = product.ratings;
    }

    public Double getRating() {

        Double average = 0.00;
        Double total = 0.00;

        for( ProductRating rating : this.ratings){

            average += rating.getRating();
        }

        total = average/this.ratings.size();

        return Math.round(total*100.0)/100.0;

    }

    public void setRatings(List<ProductRating> ratings) {
        this.ratings = ratings;
    }

    public Product() {
        this.catName = "N/A";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCatId() {
        return catId;
    }

    public void setCatId(long catId) {
        this.catId = catId;
    }

    @Transient
    public String getCatName() {
        return catName;
    }

    @Transient
    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public List<ProductImage> getImages() {
        return images;
    }

    public void setImages(List<ProductImage> images) {
        this.images = images;
    }
}

package com.ts.product.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ProductRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    private int rating;
    private String ratingDescription;
    private long productId;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getId() {
        return Id;
    }

    public void setId(long Id) {
        this.Id = Id;
    }

    public long getProductId() { return productId; }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getRating() { return rating; }

    public void setRating( int rating ) { this.rating = rating; }

    public String getRatingDescription() { return ratingDescription; }

    public void setRatingDescription( String ratingDescription ) { this.ratingDescription = ratingDescription; }
}

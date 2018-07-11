package com.ts.product.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class ProductRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    private int rating;
    private String ratingDescription;
//    private long productId;
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id", nullable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Product product;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public long getId() {
        return Id;
    }

    public void setId(long Id) {
        this.Id = Id;
    }

//    public long getProductId() { return productId; }
//
//    public void setProductId(long productId) {
//        this.productId = productId;
//    }

    public int getRating() { return rating; }

    public void setRating( int rating ) { this.rating = rating; }

    public String getRatingDescription() { return ratingDescription; }

    public void setRatingDescription( String ratingDescription ) { this.ratingDescription = ratingDescription; }
}

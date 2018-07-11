package com.ts.product.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class OrderProduct extends ProductSuperClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int qty;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id", nullable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Order order;

    public OrderProduct() {

    }

    public OrderProduct(Product product, int qty) {
        super(product);
        this.qty = qty;
        if (!product.images.isEmpty()) {
            for (ProductImage image : product.images) {
                if (image.getSort() == 0) {
                    // set image url of order product to first image only.
                    this.imageUrl = image.getUrl();
                    break;
                }
            }
        }
    }

    public double getTotalPrice() {
        double number = this.qty * this.getPrice();
        return Math.round(number*100.0)/100.0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

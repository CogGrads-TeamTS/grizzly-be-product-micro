package com.ts.product.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class CartProduct extends Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int qty;

    public CartProduct(Product product) {
        super(product);
        this.qty = 1;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Cart cart;

    public long getTotalPrice() {
        return (long) (this.qty * this.getPrice());
    }

    public void incrementQty() {
        this.setQty(this.qty + 1);
    }

    public void decrementQty() {
        this.setQty(this.qty - 1);
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}

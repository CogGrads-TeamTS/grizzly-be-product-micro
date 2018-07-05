package com.ts.product.Model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.ts.product.Repository.ProductRepository;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    private HashMap<Long, CartProduct> items;

    @JoinColumn(name="cart_id")
    @OneToMany(targetEntity=CartProduct.class,fetch=FetchType.LAZY, cascade = CascadeType.REMOVE)
    @Fetch(FetchMode.SELECT)
    private List<CartProduct> products;

    public Cart() {
        this.items = new HashMap<>();
        this.products = new ArrayList<>();
    }

    public Collection<CartProduct> getItems() {
        //return items.values();
        return this.products;
    }

    public CartProduct getItem(long id) {
        CartProduct item = items.get(id);
        if (item == null) {
            return null;
        }
        return item;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonInclude
    public float getTotalPrice() {
        float total = 0;
        for (CartProduct product : this.items.values()) {
            total += product.getTotalPrice();
        }
        return total;
    }


    public boolean removeItem(long id) {
        if (!this.items.containsKey(id)) {
            return false;
        }
        this.items.remove(id);
        return true;
    }


    public boolean changeQuantity(long id, int qty) {
        CartProduct item = items.get(id);
        if (item == null) {
            return false;
        }
        item.setQty(qty);
        return true;
    }

    public boolean contains(long id) {
        return this.items.containsKey(id);
    }

    public void addItem(Product product) {
        CartProduct item;
        // if product already exists in cart, qty is incremented of that product
        if (!this.items.containsKey(product.getId())) {
            item = new CartProduct(product);
            items.put(item.getId(), item);
        } else {
            item = items.get(product.getId());
            item.incrementQty();
        }
    }
}

package com.ts.product.Model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.ts.product.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.*;


public class Cart {

    private HashMap<Long, CartProduct> items;

    public Cart() {
        this.items = new HashMap<>();
    }

    public Cart(HashMap<Long, CartProduct> items) {
        this.items = items;
    }

    public Collection<CartProduct> getItems() {
        return items.values();
    }

    public CartProduct getItem(long id) {
        CartProduct item = items.get(id);
        if (item == null) {
            return null;
        }
        return item;
    }

    @JsonInclude
    public double getTotalPrice() {
        float total = 0;
        for (CartProduct product : this.items.values()) {
            total += product.getTotalPrice();
        }
        return Math.round(total*100.0)/100.0;
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
        if (qty > 0) item.setQty(qty);
        return true;
    }

    public boolean contains(long pid) {
        return this.items.containsKey(pid);
    }

    public void addItem(Product product, int qty) {
        if (qty < 1) return;
        CartProduct item;
        // if product already exists in cart, qty is incremented of that product
        if (!this.items.containsKey(product.getId())) {
            item = new CartProduct(product, qty);
            items.put(item.getId(), item);
        } else {
            item = items.get(product.getId());
            int newQty = item.getQty() + qty;
            if (newQty > 0) item.setQty(newQty);
        }
    }
}

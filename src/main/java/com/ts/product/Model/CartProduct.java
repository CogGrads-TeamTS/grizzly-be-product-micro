package com.ts.product.Model;

import java.text.DecimalFormat;

public class CartProduct extends Product {
    private int qty;

    public CartProduct(Product product, int qty) {
        super(product);
        this.qty = qty;
    }

    public CartProduct() {

    }

    public double getTotalPrice() {
        double number = this.qty * this.getPrice();
        return Math.round(number*100.0)/100.0;
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

package com.ts.product.Model;

public class CartProduct extends Product {
    private int qty;

    public CartProduct(Product product, int qty) {
        super(product);
        this.qty = qty;
    }

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

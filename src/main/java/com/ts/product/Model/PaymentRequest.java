package com.ts.product.Model;

import java.util.HashMap;
import java.util.List;

public class PaymentRequest {
    private List<CartProduct> items;
    private String currency;
    private HashMap<String, String> checkout;

    public HashMap<String, String> getCheckout() {
        return checkout;
    }

    public void setCheckout(HashMap<String, String> checkout) {
        this.checkout = checkout;
    }

    public List<CartProduct> getItems() {
        return items;
    }

    public void setItems(List<CartProduct> items) {
        this.items = items;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}

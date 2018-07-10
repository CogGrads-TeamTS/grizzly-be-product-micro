package com.ts.product.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

@MappedSuperclass
public class ProductSuperClass {
    private String name;
    private String brand;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Float price;

    private int discount;

    public ProductSuperClass(Product product) {
        this.name = product.getName();
        this.brand = product.getBrand();
        this.price = product.getPrice();
        this.discount = product.getDiscount();
    }

    public ProductSuperClass() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() { return brand; }

    public void setBrand(String brand) { this.brand = brand; }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}

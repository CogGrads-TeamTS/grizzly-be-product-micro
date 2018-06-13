package com.ts.product.Model;

public class Category {
    public Category() {
    }

    public Category(Long id, String name, String description, int count) {
        this.id = id;
        this.name = name;
        this.count = count;
    }

    private Long id;

    private String name;

    private int count;

    public int getCount() {
        return 15; // FIXME Add method to calculate product
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

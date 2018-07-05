package com.ts.product.Controller;

import com.ts.product.Model.CartProduct;
import com.ts.product.Model.Product;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Component
@Scope("session")
@RestController
@RequestMapping("/cart")
public class CartController {
    private HashMap<Long, CartProduct> items;

    public CartController() {
        this.items = new HashMap<>();
    }

    // example: get the cart
    // GET : http://localhost:5555/cart/
    @GetMapping
    public HashMap<Long, CartProduct> getItems() {
        return items;
    }

    // example: get product with id 1
    // GET : http://localhost:5555/cart/1
    @GetMapping("/{id}")
    public ResponseEntity getItem(@PathVariable long id) {
        CartProduct item = items.get(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(item);
    }

    // example: delete product with id 1
    // DELETE : http://localhost:5555/cart/1
    @DeleteMapping("/{id}") public ResponseEntity removeItem(@PathVariable long id) {
        if (!this.items.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        this.items.remove(id);
        return ResponseEntity.ok(items);
    }

    // example: set qty to 10 for product id 10
    // PUT : http://localhost:5555/cart/1?qty=10
    @PutMapping("/{id}") public ResponseEntity changeQuantity(@PathVariable long id, @RequestParam int qty) {
        CartProduct item = items.get(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        item.setQty(qty);
        return ResponseEntity.ok(items);
    }

    // example: add a product to the cart
    // POST : http://localhost:5555/cart/add
    // BODY (product object): { id:1,name:'',price:10 .... }
    @PostMapping("/add") public ResponseEntity addItem(@RequestBody Product product) {
        CartProduct item;
        // if product already exists in cart, qty is incremented of that product
        if (!this.items.containsKey(product.getId())) {
            item = new CartProduct(product);
            items.put(item.getId(), item);
        } else {
            item = items.get(product.getId());
            item.incrementQty();
        }
        return ResponseEntity.ok(items);
    }
}

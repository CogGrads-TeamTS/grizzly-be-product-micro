package com.ts.product.Controller;

import com.ts.product.Model.Cart;
import com.ts.product.Model.CartProduct;
import com.ts.product.Model.Product;
import com.ts.product.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashMap;
import java.util.Optional;

@Component
@Scope("session")
@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = )
public class CartController {
    @Autowired
    private ProductRepository productRepository;

    private Cart cart;

    public CartController() {
        this.cart = new Cart();
    }

    // example: get the cart
    // GET : http://localhost:5555/cart/
    @GetMapping
    public ResponseEntity getCart(HttpSession session, Principal principal) {
        System.out.println(session.getId());
        if (principal == null) {
            // user not logged in
            System.out.println("user not logged in");
            return ResponseEntity.ok(this.cart);
        }
        return ResponseEntity.ok(this.cart);
    }

    // example: get product with id 1
    // GET : http://localhost:5555/cart/1
    @GetMapping("/{id}")
    public ResponseEntity getItem(@PathVariable long id) {
        CartProduct item = cart.getItem(id);
        if (item == null) {
            return null;
        }
        return ResponseEntity.ok(item);
    }

    // example: delete product with id 1
    // DELETE : http://localhost:5555/cart/1
    @DeleteMapping("/{id}") public ResponseEntity removeItem(@PathVariable long id) {
        boolean result = cart.removeItem(id);
        if (!result) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(this.cart);
    }

    // example: set qty to 10 for product id 10
    // PUT : http://localhost:5555/cart/1?qty=10
    @PutMapping("/{id}") public ResponseEntity changeQuantity(@PathVariable long id, @RequestParam int qty) {
        boolean result = cart.changeQuantity(id, qty);
        if (!result) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(this.cart);
    }

    // example: add product id 1 to the cart
    // POST : http://localhost:5555/cart/1
    @PostMapping("/{id}") public ResponseEntity addItem(@PathVariable long id) {
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        cart.addItem(product.get());
        return ResponseEntity.ok(this.cart);
    }
}

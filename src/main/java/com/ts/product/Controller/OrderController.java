package com.ts.product.Controller;


import com.ts.product.Model.CartProduct;
import com.ts.product.Model.Order;
import com.ts.product.Repository.OrderRepository;
import com.ts.product.Service.PaypalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping
    public ResponseEntity getUserOrders(Principal principal) {
        List<Order> orders = orderRepository.findByUsername(principal.getName());
        if (orders == null) {
            return null;
        }
        return ResponseEntity.ok(orders);
    }
}

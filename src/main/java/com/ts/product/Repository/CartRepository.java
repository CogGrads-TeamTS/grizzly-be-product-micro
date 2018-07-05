package com.ts.product.Repository;


import com.ts.product.Model.Cart;
import com.ts.product.Model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUsername(String username);
}

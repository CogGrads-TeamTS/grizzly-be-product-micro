package com.ts.product.Repository;

import com.ts.product.Model.Order;
import com.ts.product.Model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

}

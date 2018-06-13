package com.ts.product.Repository;

import com.ts.product.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, CrudRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {

}

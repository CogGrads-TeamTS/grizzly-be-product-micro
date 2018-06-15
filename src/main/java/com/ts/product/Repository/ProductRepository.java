package com.ts.product.Repository;

import com.ts.product.Model.Product;
import com.ts.product.Model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, CrudRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {

    @Query("SELECT DISTINCT c.catId from Product c")
    List<Long> findDistinctCategoryId();
}

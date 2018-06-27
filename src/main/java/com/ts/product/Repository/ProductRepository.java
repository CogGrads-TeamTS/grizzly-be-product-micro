package com.ts.product.Repository;


import com.ts.product.Model.Product;
import com.ts.product.Model.ProductFilter;
import com.ts.product.Model.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;


public interface ProductRepository extends CrudRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {

    @Query("SELECT DISTINCT p.brand, p.catId, p.rating from Product p WHERE " +
            "LOWER(p.id) = CONCAT('',:search,'') OR " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%',:search, '%')) OR " +
            "LOWER(p.brand) LIKE LOWER(CONCAT('%',:search, '%'))")
    List<Object[]> findDistinctFilters(@Param("search") String searchTerm);

    @Query("SELECT p FROM Product p WHERE " +
            "(LOWER(p.id) = CONCAT('',:search,'') OR " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%',:search, '%')) OR " +
            "LOWER(p.brand) LIKE LOWER(CONCAT('%',:search, '%'))) AND " +
            "LOWER(p.catId) = :catId")
    Page<Product> findBySearchTermCategory(@Param("search") String searchTerm, Pageable pageable, @Param("catId") Long categoryId);

    @Query("SELECT p FROM Product p WHERE " +
            "(LOWER(p.id) = CONCAT('',:search,'') OR " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%',:search, '%')) OR " +
            "LOWER(p.brand) LIKE LOWER(CONCAT('%',:search, '%')))")
    Page<Product> findBySearchTerm(@Param("search") String searchTerm, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Product> findNameBySearchTerm(@Param("search") String searchTerm, Pageable pageable);

    @Query("SELECT COUNT(p.id) FROM Product p WHERE p.catId = :catId")
    Long categoryProductCount(@Param("catId") Long catId);
}

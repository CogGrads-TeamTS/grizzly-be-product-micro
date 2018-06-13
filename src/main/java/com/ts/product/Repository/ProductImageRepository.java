package com.ts.product.Repository;


import com.ts.product.Model.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long>, PagingAndSortingRepository<ProductImage, Long> {
    Page<ProductImage> findByProductId(Long productId, Pageable pageable);
}

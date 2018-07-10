package com.ts.product.Service;

import com.ts.product.Model.ProductRating;

import java.util.List;

public interface ProductRatingService {
    public List<ProductRating> getAllRatingsByProductId(Long productId);
}

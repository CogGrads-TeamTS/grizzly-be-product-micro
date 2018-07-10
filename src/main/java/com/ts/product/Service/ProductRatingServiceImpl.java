package com.ts.product.Service;

import com.ts.product.Model.ProductRating;
import com.ts.product.Repository.ProductRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductRatingServiceImpl implements ProductRatingService {
    @Autowired
    private ProductRatingRepository productRatingRepository;

    public List<ProductRating> getAllRatingsByProductId(Long productId){
        return productRatingRepository.findProductRatingByProductId(productId);
    }


}

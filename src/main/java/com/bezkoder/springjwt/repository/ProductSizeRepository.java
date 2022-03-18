package com.bezkoder.springjwt.repository;

import java.util.List;

import com.bezkoder.springjwt.entity.ProductSize;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductSizeRepository  extends CrudRepository<ProductSize, Integer>{


    // @Query("SELECT ps FROM ProductSize ps JOIN ps.products p WHERE p.id = ?1")
    // List<ProductSize> findByProductId(Integer productId);

    ProductSize findBySize(int size);


    @Query()
    ProductSize checkProductSizeAvailable(Integer producId, int size);



    
}

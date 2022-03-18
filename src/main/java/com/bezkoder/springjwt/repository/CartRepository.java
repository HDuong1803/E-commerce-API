package com.bezkoder.springjwt.repository;

import java.util.List;

import com.bezkoder.springjwt.entity.Cart;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, Integer> {

  @Query("SELECT c FROM Cart c WHERE c.product_id = ?1 AND c.user_id = ?2")
  Cart findByProductAndUser(Integer productId, Integer customerId);

  @Modifying
  @Query("UPDATE Cart c SET c.quantity = ?2 WHERE c.product_id = ?1 AND c.user_id = ?3")
  void updateQuantity(Integer productId, Integer quantity, Integer customerId);

  @Modifying
  @Query("DELETE FROM Cart c WHERE c.product_id = ?1 AND c.user_id = ?2")
  void removeCart(Integer productId, Integer customerId);

}

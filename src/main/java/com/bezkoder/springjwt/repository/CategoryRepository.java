package com.bezkoder.springjwt.repository;
import java.util.List;

import com.bezkoder.springjwt.entity.Category;
import com.bezkoder.springjwt.entity.Order;
import com.bezkoder.springjwt.model.dto.OrderInfoDto;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Integer>{
    

    @Query(value = "SELECT * FROM orders AS o INNER JOIN products AS p ON o.product_id = p.id WHERE p.created_by = ?1", nativeQuery = true)
    List<Order> getOrderOfCustomer(Integer customerId);

    @Query(value = "SELECT orders.id, orders.total_price, products.name FROM orders AS o INNER JOIN products AS p ON o.product_id = p.id WHERE o.status = ?1 AND o.buyer = ?2 ", nativeQuery = true)
    List<OrderInfoDto> getListOrderOfPersonByStatus(Integer status, Integer userId);

    @Query("SELECT o FROM Order o WHERE o.id = ?1 AND o.buyer = ?2")
    Order getOrderDetailByUser(Integer id, Integer userId);
}

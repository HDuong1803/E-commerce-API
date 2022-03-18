package com.bezkoder.springjwt.repository;

import java.util.List;

import com.bezkoder.springjwt.entity.Order;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Integer> {

    @Query(value = "SELECT o FROM Order WHERE o.status = ?1 AND o.createdBy = ?2")
    public List<Order> getListOrderOfPersonByStatus(int status, Integer userId);


    @Query(value = "SELECT o FROM Order WHERE o.id = ?1 AND o.createdBy = ?2")
    public Order getOrderDetailByUser(Integer id, Integer userId);

}

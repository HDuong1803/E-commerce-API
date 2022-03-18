package com.bezkoder.springjwt.controllers;

import java.util.List;

import com.bezkoder.springjwt.entity.Order;
import com.bezkoder.springjwt.entity.User;
import com.bezkoder.springjwt.model.dto.OrderDetailsDto;
import com.bezkoder.springjwt.model.dto.OrderInfoDto;
import com.bezkoder.springjwt.model.request.CreateOrderReq;
import com.bezkoder.springjwt.repository.ProductRepository;
import com.bezkoder.springjwt.repository.ProductSizeRepository;
import com.bezkoder.springjwt.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/api/order")
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderReq orderReq, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        Order order = orderService.createOrder(orderReq, user.getId());

        return ResponseEntity.ok(order.getId());
    }

    @GetMapping(value = "/api/order/")
    public ResponseEntity<?> getListOrderOfPersonByStatus(@RequestParam(name = "status") int status,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        List<OrderInfoDto> dtos = orderService.getListOrderOfPersonByStatus(status, user.getId());
        return ResponseEntity.ok(dtos);

    }

    @GetMapping(value = "/api/order/{orderId}")
    public ResponseEntity<?> getOrderDetailsByUser(@PathVariable(name = "orderId") Integer orderId,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        OrderDetailsDto dto = orderService.getOrderDetailsByUser(orderId, user.getId());
        return ResponseEntity.ok(dto);

    }

    @GetMapping(value = "/api/order/{orderId}/cancelOrder")
    public ResponseEntity<?> userCancelOrder(@PathVariable(name = "orderId") Integer orderId,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        orderService.userCancelOrder(orderId, user.getId());
        return (ResponseEntity<?>) ResponseEntity.ok();

    }

}

package com.bezkoder.springjwt.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bezkoder.springjwt.entity.Order;
import com.bezkoder.springjwt.entity.Product;
import com.bezkoder.springjwt.entity.User;
import com.bezkoder.springjwt.exception.BadRequestException;
import com.bezkoder.springjwt.exception.NotFoundException;
import com.bezkoder.springjwt.model.dto.OrderDetailsDto;
import com.bezkoder.springjwt.model.dto.OrderInfoDto;
import com.bezkoder.springjwt.model.request.CreateOrderReq;
import com.bezkoder.springjwt.repository.OrderRepository;
import static com.bezkoder.springjwt.utils.Constant.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bezkoder.springjwt.repository.ProductRepository;
import com.bezkoder.springjwt.repository.ProductSizeRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductSizeRepository productSizeRepository;

    public Order createOrder(CreateOrderReq req, Integer userId) {

        Order order = new Order();

        Optional<Product> product = productRepository.findById(req.getProductId());

        if (product.isEmpty()) {
            throw new NotFoundException("Sản phẩm không tồn tại");
        }

        order.setCreatedAt(new Date());

        User createdBy = new User(userId);
        order.setCreatedBy(createdBy);
        order.setReceiverAddress(req.getReceiverAddress());
        order.setReceiverName(req.getReceiverName());
        order.setReceiverPhone(req.getReceiverPhone());

        order.setProduct(product.get());
        order.setSize(req.getSize());
        order.setProductPrice(req.getProductPrice());
        order.setProductName(product.get().getName());
        order.setStatus(ORDER_STATUS);

        orderRepository.save(order);

        return order;

    }

    public List<OrderInfoDto> getListOrderOfPersonByStatus(int status, Integer userId) {

        List<Order> order = orderRepository.getListOrderOfPersonByStatus(status, userId);

        List<OrderInfoDto> dtos = order.stream().map(o -> OrderInfoDto.builder()
                .id(o.getId())
                .productName(o.getProductName())
                .productPrice(o.getProductPrice())
                .size(o.getSize())
                .build()).collect(Collectors.toList());

        return dtos;

    }

    public OrderDetailsDto getOrderDetailsByUser(Integer id, Integer userId) {

        Order order = orderRepository.getOrderDetailByUser(id, userId);

        OrderDetailsDto dto = OrderDetailsDto.builder()
                .id(order.getId())
                .totalPrice(order.getTotalPrice())
                .productPrice(order.getProductPrice())
                .receiverName(order.getReceiverName())
                .receiverPhone(order.getReceiverPhone())
                .receiverAddress(order.getReceiverAddress())
                .status(order.getStatus())
                .productName(order.getProductName())
                .build();

        if (order.getStatus() == ORDER_STATUS) {
            dto.setStatusText("Cho lay hang");
        } else if (order.getStatus() == DELIVERY_STATUS) {
            dto.setStatusText("Dang giao hang");
        } else if (order.getStatus() == CANCELED_STATUS) {
            dto.setStatusText("Da Huy");
        } else if (order.getStatus() == RETURNED_STATUS) {
            dto.setStatusText("Da tra hang");
        }

        return dto;

    }

    public void userCancelOrder(Integer id, Integer userId) {

        Optional<Order> rs = orderRepository.findById(id);

        if (rs.isEmpty()) {
            throw new NotFoundException("Đơn hàng không tồn tại");

        }

        Order order = rs.get();

        if (order.getCreatedBy().getId() != userId) {
            throw new BadRequestException("Bạn không phải chủ nhân đơn hàng");

        }

        if (order.getStatus() != ORDER_STATUS) {
            throw new BadRequestException("Trạng thái đơn hàng không phù hợp để hủy");

        }

        order.setStatus(CANCELED_STATUS);

        orderRepository.save(order);

    }

}

package com.Shoply_Backend.services;

import com.Shoply_Backend.domain.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder(Long userId);
    List<OrderDTO> getAllUserOrders(Long userId);
    List<OrderDTO> getAllOrders();
    OrderDTO getOrder(Long orderId);
    OrderDTO updateOrderStatus(Long orderId, String status);
}

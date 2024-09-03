package com.Shoply_Backend.services;

import com.Shoply_Backend.domain.dto.OrderDTO;

public interface OrderService {
    OrderDTO createOrder(Long userId);
}

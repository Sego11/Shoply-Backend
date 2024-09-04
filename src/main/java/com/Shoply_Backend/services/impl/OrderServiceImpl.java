package com.Shoply_Backend.services.impl;

import com.Shoply_Backend.domain.dto.OrderDTO;
import com.Shoply_Backend.domain.entities.OrderEntity;
import com.Shoply_Backend.domain.entities.OrderItem;
import com.Shoply_Backend.exceptions.ResourceNotFoundException;
import com.Shoply_Backend.mappers.Mapper;
import com.Shoply_Backend.repositories.CartItemRepository;
import com.Shoply_Backend.repositories.OrderRepository;
import com.Shoply_Backend.repositories.UserRepository;
import com.Shoply_Backend.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final Mapper<OrderEntity, OrderDTO> mapper;

    @Override
    public OrderDTO createOrder(Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        var cartItems = cartItemRepository.findByUserId(userId);
        if (cartItems.isEmpty())
            throw new ResourceNotFoundException("No Items in the cart for user with id: " + userId);

        var orderItems = cartItems.stream().map(cartItem -> OrderItem.builder()
                .product(cartItem.getProductEntity())
                .quantity(cartItem.getQuantity())
                .price(cartItem.getProductEntity().getPrice())
                .build()).toList();

        var totalAmount = orderItems.stream()
                .mapToDouble(orderItem -> orderItem.getPrice() * orderItem.getQuantity())
                .sum();

        var order = OrderEntity.builder()
                .user(user)
                .items(orderItems)
                .orderDate(LocalDateTime.now())
                .status("PENDING")
                .totalAmount(totalAmount)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        var savedOrder = orderRepository.save(order);

        cartItemRepository.deleteAll(cartItems);

        return mapper.mapTo(savedOrder);
    }

    @Override
    public List<OrderDTO> getAllUserOrders(Long userId) {
        var userOrders = orderRepository.findOrdersByUserId(userId);
        if (userOrders.isEmpty())
            throw new ResourceNotFoundException("No orders found for user with id: " + userId);

        return userOrders.stream()
                .map(mapper::mapTo)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(mapper::mapTo)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrder(Long orderId) {
        var foundOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for id: " + orderId));
        return mapper.mapTo(foundOrder);
    }

    @Override
    public OrderDTO updateOrderStatus(Long orderId, String status) {
        var foundOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for id: " + orderId));

        foundOrder.setStatus(status);
        foundOrder.setUpdatedAt(LocalDateTime.now());

        var updatedOrder = orderRepository.save(foundOrder);
        return mapper.mapTo(updatedOrder);
    }
}

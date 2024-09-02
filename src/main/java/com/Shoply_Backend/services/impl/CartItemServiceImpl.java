package com.Shoply_Backend.services.impl;

import com.Shoply_Backend.domain.dto.CartItemDTO;
import com.Shoply_Backend.domain.entities.CartItemEntity;
import com.Shoply_Backend.exceptions.BadRequestException;
import com.Shoply_Backend.exceptions.ResourceNotFoundException;
import com.Shoply_Backend.mappers.Mapper;
import com.Shoply_Backend.repositories.CartItemRepository;
import com.Shoply_Backend.repositories.ProductRepository;
import com.Shoply_Backend.repositories.UserRepository;
import com.Shoply_Backend.services.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final Mapper<CartItemEntity, CartItemDTO> mapper;

    @Override
    public CartItemDTO addCartItem(CartItemDTO cartItemDTO) {
        var user = userRepository.findById(cartItemDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + cartItemDTO.getUserId()));

        var product = productRepository.findById(cartItemDTO.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with id: " + cartItemDTO.getProductId()));

        var cartItemEntity = CartItemEntity.builder()
                .user(user)
                .productEntity(product)
                .quantity(cartItemDTO.getQuantity())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return mapper.mapTo(cartItemRepository.save(cartItemEntity));
    }

    @Override
    public List<CartItemDTO> findAll() {
        return cartItemRepository.findAll()
                .stream()
                .map(mapper::mapTo)
                .collect(Collectors.toList());
    }

    @Override
    public CartItemDTO findById(Long id) {
        var foundcartItemEntity = cartItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem not found with id: " + id));
        return mapper.mapTo(foundcartItemEntity);
    }

    @Override
    public void delete(Long id) {
        var foundcartItemEntity = cartItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem not found with id: " + id));
        cartItemRepository.delete(foundcartItemEntity);
    }

    @Override
    public CartItemDTO update(Long id, Map<String, Object> fields) {
        var foundcartItemEntity = cartItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem not found with id: " + id));

        if (fields.isEmpty())
            throw new BadRequestException("No fields provided for update");

        fields.forEach((key, value) -> {
            var field = ReflectionUtils.findField(CartItemEntity.class, key);

            if (field == null)
                throw new BadRequestException("Invalid field: " + key);

            field.setAccessible(true);
            ReflectionUtils.setField(field, foundcartItemEntity, value);
        });

        foundcartItemEntity.setUpdatedAt(LocalDateTime.now());
        return mapper.mapTo(cartItemRepository.save(foundcartItemEntity));
    }
}

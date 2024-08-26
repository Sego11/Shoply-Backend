package com.Shoply_Backend.services;

import com.Shoply_Backend.domain.dto.CartItemDTO;

import java.util.List;
import java.util.Map;

public interface CartItemService {
    CartItemDTO addCartItem(CartItemDTO cartItemDTO);
    List<CartItemDTO> findAll();
    CartItemDTO findById(Long id);
    void delete(Long id);
    CartItemDTO update(Long id, Map<String,Object> fields);
}

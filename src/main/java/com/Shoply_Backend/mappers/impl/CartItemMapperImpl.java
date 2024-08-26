package com.Shoply_Backend.mappers.impl;

import com.Shoply_Backend.domain.dto.CartItemDTO;
import com.Shoply_Backend.domain.entities.CartItemEntity;
import com.Shoply_Backend.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartItemMapperImpl implements Mapper<CartItemEntity, CartItemDTO> {

    private final ModelMapper mapper;
    @Override
    public CartItemDTO mapTo(CartItemEntity cartItemEntity) {
        return mapper.map(cartItemEntity,CartItemDTO.class);
    }

    @Override
    public CartItemEntity mapFrom(CartItemDTO cartItemDTO) {
        return mapper.map(cartItemDTO,CartItemEntity.class);
    }
}

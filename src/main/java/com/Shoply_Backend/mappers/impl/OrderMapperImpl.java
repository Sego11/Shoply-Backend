package com.Shoply_Backend.mappers.impl;

import com.Shoply_Backend.domain.dto.OrderDTO;
import com.Shoply_Backend.domain.entities.OrderEntity;
import com.Shoply_Backend.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapperImpl  implements Mapper<OrderEntity, OrderDTO> {

    private final ModelMapper mapper;
    @Override
    public OrderDTO mapTo(OrderEntity orderEntity) {
        return mapper.map(orderEntity,OrderDTO.class);
    }

    @Override
    public OrderEntity mapFrom(OrderDTO orderDTO) {
        return mapper.map(orderDTO,OrderEntity.class);
    }
}

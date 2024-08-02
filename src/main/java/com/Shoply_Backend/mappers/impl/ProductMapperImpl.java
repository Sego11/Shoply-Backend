package com.Shoply_Backend.mappers.impl;

import com.Shoply_Backend.domain.dto.ProductDTO;
import com.Shoply_Backend.domain.entities.ProductEntity;
import com.Shoply_Backend.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapperImpl implements Mapper<ProductEntity, ProductDTO> {

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ProductDTO mapTo(ProductEntity productEntity) {
        return modelMapper.map(productEntity, ProductDTO.class);
    }

    @Override
    public ProductEntity mapFrom(ProductDTO productDTO) {
        return modelMapper.map(productDTO, ProductEntity.class);
    }
}

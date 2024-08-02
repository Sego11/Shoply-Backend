package com.Shoply_Backend.services.impl;

import com.Shoply_Backend.domain.dto.ProductDTO;
import com.Shoply_Backend.domain.entities.ProductEntity;
import com.Shoply_Backend.exceptions.BadRequestException;
import com.Shoply_Backend.exceptions.ResourceNotFoundException;
import com.Shoply_Backend.mappers.Mapper;
import com.Shoply_Backend.repositories.ProductRepository;
import com.Shoply_Backend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private Mapper<ProductEntity,ProductDTO> productMapper;

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        ProductEntity productEntity = productMapper.mapFrom(productDTO);
        productEntity.setCreatedAt(LocalDateTime.now());
        productEntity.setUpdatedAt(LocalDateTime.now());
        ProductEntity savedProductEntity = productRepository.save(productEntity);
        return productMapper.mapTo(savedProductEntity);
    }

    @Override
    public List<ProductDTO> findAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::mapTo)
                .collect(Collectors.toList());
    }


    @Override
    public ProductDTO findById(Long id) {
        ProductEntity productEntity =  productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product not found with id: " + id));
        return productMapper.mapTo(productEntity);

    }

    @Override
    public void delete(Long id) {
        ProductEntity foundProductEntity = productRepository
                        .findById(id)
                        .orElseThrow(()-> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(foundProductEntity);
    }

    @Override
    public ProductDTO update(Long id, Map<String, Object> fields) {
        ProductEntity foundProductEntity = productRepository
                            .findById(id)
                            .orElseThrow(()-> new ResourceNotFoundException("Product not found with id: " + id));

        if (fields.isEmpty())
            throw new BadRequestException("No fields provided for update");

        fields.forEach((key,value)->{
           Field field =  ReflectionUtils.findField(ProductEntity.class,key);
            if (field == null)
                throw new BadRequestException("Invalid field: " + key);

            field.setAccessible(true);
            ReflectionUtils.setField(field, foundProductEntity,value);
        });

        foundProductEntity.setUpdatedAt(LocalDateTime.now());
        ProductEntity updatedProductEntity = productRepository.save(foundProductEntity);
        return productMapper.mapTo(updatedProductEntity);
    }

}

package com.Shoply_Backend.services.impl;

import com.Shoply_Backend.entities.Product;
import com.Shoply_Backend.exceptions.BadRequestException;
import com.Shoply_Backend.exceptions.ResourceNotFoundException;
import com.Shoply_Backend.repositories.ProductRepository;
import com.Shoply_Backend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product createProduct(Product product) {
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }



    @Override
    public Product findById(Long id) {
        return productRepository.findById(id)
                                .orElseThrow(()-> new ResourceNotFoundException("Product not found with id: " + id));

    }

    @Override
    public void delete(Long id) {
        Product foundProduct = productRepository
                        .findById(id)
                        .orElseThrow(()-> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(foundProduct);
    }

    @Override
    public Product update(Long id, Map<String, Object> fields) {
        Product foundProduct = productRepository
                            .findById(id)
                            .orElseThrow(()-> new ResourceNotFoundException("Product not found with id: " + id));

        if (fields.isEmpty())
            throw new BadRequestException("No fields provided for update");

        fields.forEach((key,value)->{
           Field field =  ReflectionUtils.findField(Product.class,key);
            if (field == null)
                throw new BadRequestException("Invalid field: " + key);

            field.setAccessible(true);
            ReflectionUtils.setField(field,foundProduct,value);
        });

        foundProduct.setUpdatedAt(LocalDateTime.now());

        return productRepository.save(foundProduct);
    }

}

package com.Shoply_Backend.services;

import com.Shoply_Backend.entities.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {
    Product createProduct(Product product);
    List<Product> findAll();
    Product findById(Long id);
    void delete(Long id);
    Product update(Long id, Map<String,Object> fields);

}

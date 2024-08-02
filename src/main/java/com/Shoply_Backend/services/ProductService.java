package com.Shoply_Backend.services;

import com.Shoply_Backend.domain.dto.ProductDTO;

import java.util.List;
import java.util.Map;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO);
    List<ProductDTO> findAll();
    ProductDTO findById(Long id);
    void delete(Long id);
    ProductDTO update(Long id, Map<String,Object> fields);

}

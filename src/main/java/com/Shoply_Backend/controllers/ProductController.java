package com.Shoply_Backend.controllers;

import com.Shoply_Backend.domain.dto.ProductDTO;
import com.Shoply_Backend.domain.entities.ProductEntity;
import com.Shoply_Backend.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody @Valid ProductDTO productEntity){
         ProductDTO createdProduct =  productService.createProduct(productEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProducts(){
        List<ProductDTO> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id){
       ProductDTO foundProductEntity =  productService.findById(id);
       return ResponseEntity.ok(foundProductEntity);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody Map<String, Object> fields){
        ProductDTO updatedProduct = productService.update(id,fields);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

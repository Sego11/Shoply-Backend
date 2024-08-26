package com.Shoply_Backend.controllers;

import com.Shoply_Backend.domain.dto.CartItemDTO;
import com.Shoply_Backend.services.CartItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cartItems")
public class CartItemController {

    private final CartItemService cartItemService;

    @PostMapping
    public ResponseEntity<CartItemDTO> addCartItem(@RequestBody @Valid CartItemDTO cartItemDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemService.addCartItem(cartItemDTO));
    }

    @GetMapping
    public ResponseEntity<List<CartItemDTO>> getCartItems(){
        return ResponseEntity.ok(cartItemService.findAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CartItemDTO> getCartItem(@PathVariable Long id){
        return ResponseEntity.ok(cartItemService.findById(id));
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<CartItemDTO> updateCartItem(@PathVariable Long id, Map<String, Object> fields ){
        return ResponseEntity.ok(cartItemService.update(id,fields));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id){
        cartItemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

package com.Shoply_Backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long id;
    private Long userId;

    private List<OrderItemDTO> items;

    private LocalDateTime orderDate;

    private String status;

    private double totalAmount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
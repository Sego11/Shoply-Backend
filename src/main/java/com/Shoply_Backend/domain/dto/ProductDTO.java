package com.Shoply_Backend.domain.dto;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name cannot be longer than 255 characters")
    private String name;

    @Size(max = 1000,message = "Description cannot be longer than 1000 characters")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0",inclusive = false,message = "Price must be greater than zero")
    private double price;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock must be zero or positive")
    private Integer stock;


}

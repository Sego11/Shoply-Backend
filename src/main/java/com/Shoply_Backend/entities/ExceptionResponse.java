package com.Shoply_Backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponse{
    private String message;
    private Object details; // Map< String, String> details
    private int code;
}

package com.Shoply_Backend.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException (String message){
        super(message);

    }
}

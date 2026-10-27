package com.example.foodorder.exception;

/** Thrown when the requested food order does not exist. */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
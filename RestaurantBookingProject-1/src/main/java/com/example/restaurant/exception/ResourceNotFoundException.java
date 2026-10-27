package com.example.restaurant.exception;
/** Thrown when a requested booking cannot be found. */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) { super(message); }
}
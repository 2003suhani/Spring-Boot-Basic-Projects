package com.example.hospital.exception;
/** Thrown when an appointment ID does not exist. */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) { super(message); }
}
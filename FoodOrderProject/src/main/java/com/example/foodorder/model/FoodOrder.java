package com.example.foodorder.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Represents an order using the required assignment fields. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodOrder {
    private Long orderId;

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank(message = "Food item cannot be empty")
    private String foodItem;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price must be greater than zero")
    private Double price;

    // Additional field for tracking order progress.
    private String status;
}
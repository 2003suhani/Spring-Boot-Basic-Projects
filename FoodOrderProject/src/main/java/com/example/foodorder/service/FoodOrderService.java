package com.example.foodorder.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.foodorder.exception.ResourceNotFoundException;
import com.example.foodorder.model.FoodOrder;

/** Business logic for creating and managing food orders. */
@Service
public class FoodOrderService {
    private final List<FoodOrder> orders = new ArrayList<>();
    private final AtomicLong ids = new AtomicLong(100);

    public FoodOrderService() {
        orders.add(new FoodOrder(ids.incrementAndGet(), "Asha", "Veg Burger", 2, 180.0, "PLACED"));
        orders.add(new FoodOrder(ids.incrementAndGet(), "Ravi", "Pizza", 1, 299.0, "PLACED"));
    }

    public List<FoodOrder> getAllOrders() {
        return new ArrayList<>(orders);
    }

    public FoodOrder getOrderById(Long id) {
        return orders.stream()
                .filter(order -> order.getOrderId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + id));
    }

    public FoodOrder createOrder(FoodOrder order) {
        order.setOrderId(ids.incrementAndGet());
        if (order.getStatus() == null || order.getStatus().isBlank()) {
            order.setStatus("PLACED");
        } else {
            order.setStatus(normalizeStatus(order.getStatus()));
        }
        orders.add(order);
        return order;
    }

    public FoodOrder updateOrder(Long id, FoodOrder input) {
        FoodOrder existing = getOrderById(id);
        existing.setCustomerName(input.getCustomerName());
        existing.setFoodItem(input.getFoodItem());
        existing.setQuantity(input.getQuantity());
        existing.setPrice(input.getPrice());
        if (input.getStatus() != null && !input.getStatus().isBlank()) {
            existing.setStatus(normalizeStatus(input.getStatus()));
        }
        return existing;
    }

    public FoodOrder patchOrder(Long id, Map<String, Object> changes) {
        FoodOrder existing = getOrderById(id);

        if (changes.containsKey("customerName")) {
            Object value = changes.get("customerName");
            if (!(value instanceof String) || ((String) value).isBlank()) {
                throw new IllegalArgumentException("Customer name cannot be empty");
            }
            existing.setCustomerName((String) value);
        }
        if (changes.containsKey("foodItem")) {
            Object value = changes.get("foodItem");
            if (!(value instanceof String) || ((String) value).isBlank()) {
                throw new IllegalArgumentException("Food item cannot be empty");
            }
            existing.setFoodItem((String) value);
        }
        if (changes.containsKey("quantity")) {
            Object value = changes.get("quantity");
            if (!(value instanceof Number) || ((Number) value).intValue() < 1) {
                throw new IllegalArgumentException("Quantity must be at least 1");
            }
            existing.setQuantity(((Number) value).intValue());
        }
        if (changes.containsKey("price")) {
            Object value = changes.get("price");
            if (!(value instanceof Number) || ((Number) value).doubleValue() <= 0) {
                throw new IllegalArgumentException("Price must be greater than zero");
            }
            existing.setPrice(((Number) value).doubleValue());
        }
        if (changes.containsKey("status")) {
            existing.setStatus(normalizeStatus(String.valueOf(changes.get("status"))));
        }
        return existing;
    }

    public void deleteOrder(Long id) {
        orders.remove(getOrderById(id));
    }

    public List<FoodOrder> searchByCustomer(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Customer name is required");
        }
        return orders.stream()
                .filter(order -> order.getCustomerName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public long countOrders() {
        return orders.size();
    }

    public double calculateTotalValue() {
        return orders.stream().mapToDouble(order -> order.getPrice() * order.getQuantity()).sum();
    }

    private String normalizeStatus(String status) {
        String normalized = status.trim().toUpperCase();
        if (!List.of("PLACED", "PREPARING", "DELIVERED", "CANCELLED").contains(normalized)) {
            throw new IllegalArgumentException("Status must be PLACED, PREPARING, DELIVERED, or CANCELLED");
        }
        return normalized;
    }
}
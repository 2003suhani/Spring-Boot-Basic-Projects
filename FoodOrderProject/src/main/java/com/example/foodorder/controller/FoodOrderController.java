package com.example.foodorder.controller;

import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.foodorder.model.FoodOrder;
import com.example.foodorder.service.FoodOrderService;

/** Exposes REST endpoints for food-order operations. */
@RestController
@RequestMapping("/api/orders")
public class FoodOrderController {
    private final FoodOrderService service;

    public FoodOrderController(FoodOrderService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<FoodOrder>> getAllOrders() {
        return ResponseEntity.ok(service.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodOrder> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getOrderById(id));
    }

    @PostMapping
    public ResponseEntity<FoodOrder> createOrder(@Valid @RequestBody FoodOrder order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createOrder(order));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodOrder> updateOrder(
            @PathVariable Long id, @Valid @RequestBody FoodOrder order) {
        return ResponseEntity.ok(service.updateOrder(id, order));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FoodOrder> patchOrder(
            @PathVariable Long id, @RequestBody Map<String, Object> changes) {
        return ResponseEntity.ok(service.patchOrder(id, changes));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        service.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<FoodOrder>> searchByCustomer(@RequestParam String customerName) {
        return ResponseEntity.ok(service.searchByCustomer(customerName));
    }

    @GetMapping("/summary/count")
    public Map<String, Long> countOrders() {
        return Map.of("totalOrders", service.countOrders());
    }

    @GetMapping("/summary/value")
    public Map<String, Double> totalValue() {
        return Map.of("totalOrderValue", service.calculateTotalValue());
    }
}